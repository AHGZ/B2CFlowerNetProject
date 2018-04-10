package com.android.p2pflowernet.project.view.customview;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.android.p2pflowernet.project.entity.DataBean;
import com.android.p2pflowernet.project.view.customview.actionsheet.WheelPicker;
import com.android.p2pflowernet.project.view.customview.actionsheet.WheelView;

import java.util.ArrayList;
import java.util.List;


/**
 * @描述: 省-市-区县自定义滚动选择器
 * @创建人：zhangpeisen
 * @创建时间：2017/7/3 下午10:08
 * @修改人：zhangpeisen
 * @修改时间：2017/7/3 下午10:08
 * @修改备注：
 * @throws
 */

public class SelectCityPicker extends WheelPicker {

    private OnCitySelectListener mOnCitySelectListener;

    private Activity mContext;

    private List<DataBean.RegionBean> mSelectProviceEnities;

    private WheelView provinceView;
    private WheelView cityView;

    public SelectCityPicker(Activity activity, List<DataBean.RegionBean> selectProviceEnities) {
        super(activity);
        mContext = activity;
        mSelectProviceEnities = selectProviceEnities;
    }


    private void onProvinceWheelRoll(int position) {
        if (getCityNames(position).equals("")) {
            return;
        }
        cityView.setItems(getCityNames(position));
    }


    /**
     * 获取省份名称列表
     *
     * @return
     */
    private List<String> getProvinceNames() {
        List<String> provinces = new ArrayList<>();
        for (DataBean.RegionBean province : mSelectProviceEnities) {
            if (province == null) {
                provinces.add("");
            } else {
                provinces.add(province.getRegion_name());
            }
        }
        return provinces;
    }

    /**
     * 获取某个省份的城市名称列表
     *
     * @param provincePos
     * @return
     */
    private List<String> getCityNames(int provincePos) {
        if (provincePos == mSelectProviceEnities.size()) {
            provincePos = mSelectProviceEnities.size() - 1;
        }
        List<String> cities = new ArrayList<>();
        if (mSelectProviceEnities.get(provincePos).getChild() == null) {
            cities.add("");
            return cities;
        }
        for (DataBean.RegionBean.ChildBean city : mSelectProviceEnities.get(provincePos).getChild()) {
            if (city == null) {
                cities.add("");
            } else {
                cities.add(city.getRegion_name());
            }
        }
        return cities;
    }

    public SelectCityPicker setOnCitySelectListener(OnCitySelectListener listener) {
        mOnCitySelectListener = listener;
        return this;
    }


    @NonNull
    @Override
    protected View makeCenterView() {
        //初始化选择器
        if (mSelectProviceEnities.size() == 0) {
            throw new IllegalArgumentException("please initial options at first, can't be empty");
        }
        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);
        // 省
        provinceView = new WheelView(activity);
        provinceView.setLayoutParams(new LinearLayout.LayoutParams(screenWidthPixels / 3, WRAP_CONTENT));
        provinceView.setTextSize(textSize);
        provinceView.setTextColor(textColorNormal, Color.parseColor("#333333"));
        provinceView.setLineVisible(lineVisible);
        provinceView.setLineColor(Color.parseColor("#D7D7D7"));
        provinceView.setOffset(offset);
        layout.addView(provinceView);
        // 市
        cityView = new WheelView(activity);
        cityView.setLayoutParams(new LinearLayout.LayoutParams(screenWidthPixels / 3, WRAP_CONTENT));
        cityView.setTextSize(textSize);
        cityView.setTextColor(textColorNormal, Color.parseColor("#333333"));
        cityView.setLineVisible(lineVisible);
        cityView.setLineColor(Color.parseColor("#D7D7D7"));
        cityView.setOffset(offset);
        layout.addView(cityView);
        provinceView.setItems(getProvinceNames(), provinceView.getSelectedIndex());
        cityView.setItems(getCityNames(0));
        provinceView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(boolean isUserScroll, int selectedIndex, String item) {
                onProvinceWheelRoll(selectedIndex);
            }
        });
        cityView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(boolean isUserScroll, int selectedIndex, String item) {
            }
        });
        return layout;
    }

    @Override
    protected void onSubmit() {
        super.onSubmit();
        if (mOnCitySelectListener != null) {
            // 省
            DataBean.RegionBean province = mSelectProviceEnities.get(mSelectProviceEnities.size() == provinceView.getSelectedIndex()
                    ? provinceView.getSelectedIndex() - 1 : provinceView.getSelectedIndex());
            String provinceName = province.getRegion_name();
            int provinceId = province.getId();
            if (province.getChild() == null) {
                mOnCitySelectListener.onCitySelect(provinceName, "", provinceId, 0);
                return;
            }
            // 市
            DataBean.RegionBean.ChildBean city = province.getChild().size() > 0 ? province.getChild().get(cityView.getSelectedIndex()) : null;
            String cityName = city == null ? "" : city.getRegion_name();
            int cityId = city == null ? 0 : city.getId();
            mOnCitySelectListener.onCitySelect(provinceName, cityName, provinceId, cityId);
        }

    }

    public interface OnCitySelectListener {
        void onCitySelect(String province, String city, int provinceid, int cityid);
    }

}
