package com.android.p2pflowernet.project.view.customview;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.android.p2pflowernet.project.entity.AllPlaceDataBean;
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

public class SelectsCityPicker extends WheelPicker {

    private OnCitySelectListener mOnCitySelectListener;

    private Activity mContext;

    private List<AllPlaceDataBean.GrBean> mSelectProviceEnities;

    private WheelView provinceView;
    private WheelView cityView;
    private WheelView countyView;

    public SelectsCityPicker(Activity activity, List<AllPlaceDataBean.GrBean> selectProviceEnities) {
        super(activity);
        mContext = activity;
        mSelectProviceEnities = selectProviceEnities;
    }


    private void onProvinceWheelRoll(int position) {
        cityView.setItems(getCityNames(position));
        countyView.setItems(getCountyName(position, 0));
    }

    private void onCityWheelRoll(int position) {
        countyView.setItems(getCountyName(provinceView.getSelectedIndex(), position));
    }

    private void onCountyWheelRoll(int selectedIndex) {

    }

    /**
     * 获取省份名称列表
     *
     * @return
     */
    private List<String> getProvinceNames() {
        List<String> provinces = new ArrayList<>();
        for (AllPlaceDataBean.GrBean province : mSelectProviceEnities) {
            if (province == null) {
                provinces.add("");
            } else {
                provinces.add(province.getRegion_name());
            }
        }
        return provinces;
    }


    public SelectsCityPicker setOnCitySelectListener(OnCitySelectListener listener) {
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
        // 区县
        countyView = new WheelView(activity);
        countyView.setLayoutParams(new LinearLayout.LayoutParams(screenWidthPixels / 3, WRAP_CONTENT));
        countyView.setTextSize(textSize);
        countyView.setTextColor(textColorNormal, Color.parseColor("#333333"));
        countyView.setLineVisible(lineVisible);
        countyView.setLineColor(Color.parseColor("#D7D7D7"));
        countyView.setOffset(offset);
        layout.addView(countyView);
        provinceView.setItems(getProvinceNames(), provinceView.getSelectedIndex());
        cityView.setItems(getCityNames(0));
        countyView.setItems(getCountyName(0, 0));
        provinceView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(boolean isUserScroll, int selectedIndex, String item) {
                onProvinceWheelRoll(selectedIndex);
            }
        });
        cityView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(boolean isUserScroll, int selectedIndex, String item) {
                onCityWheelRoll(selectedIndex);
            }
        });

        countyView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(boolean isUserScroll, int selectedIndex, String item) {
                onCountyWheelRoll(selectedIndex);
            }
        });
        return layout;
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
        for (AllPlaceDataBean.GrBean.ChildBeanX city : mSelectProviceEnities.get(provincePos).getChild()) {
            if (city == null) {
                cities.add("");
            } else {
                cities.add(city.getRegion_name());
            }
        }
        return cities;
    }

    /**
     * 获取区县的名称
     *
     * @param
     * @return
     */
    private List<String> getCountyName(int proviceId, int cityId) {

        if (proviceId == mSelectProviceEnities.size()) {
            proviceId = mSelectProviceEnities.size() - 1;
        }
        List<String> counties = new ArrayList<>();
        if (mSelectProviceEnities.get(proviceId).getChild().size() > 0) {
            for (AllPlaceDataBean.GrBean.ChildBeanX.ChildBean county : mSelectProviceEnities.get(proviceId).
                    getChild().get(cityId).getChild()) {

                counties.add(county.getRegion_name());
            }
        }
        return counties;
    }

    @Override
    protected void onSubmit() {
        super.onSubmit();
        if (mOnCitySelectListener != null) {
            // 省
            AllPlaceDataBean.GrBean province = mSelectProviceEnities.get(mSelectProviceEnities.size() == provinceView.getSelectedIndex()
                    ? provinceView.getSelectedIndex() - 1 : provinceView.getSelectedIndex());
            // 市
            AllPlaceDataBean.GrBean.ChildBeanX city = (province.getChild().size() > 0) ? province.getChild().get(cityView.getSelectedIndex()) : null;
            String provinceName = province.getRegion_name();
            int provinceId = province.getId();

            if (province.getChild().isEmpty()) {
                mOnCitySelectListener.onCitySelect(provinceName, "", provinceId, 0, "", 0);
                return;
            }
            String cityName = city == null ? "" : city.getRegion_name();
            int cityId = city == null ? 0 : city.getId();
            mOnCitySelectListener.onCitySelect(provinceName, cityName, provinceId, cityId, "", 0);

            //区县
            AllPlaceDataBean.GrBean.ChildBeanX.ChildBean county = (city.getChild().size() > 0) ? city.getChild().get(countyView.getSelectedIndex()) : null;
            if (city.getChild().isEmpty()) {
                mOnCitySelectListener.onCitySelect(provinceName, "", provinceId, 0, "", 0);
                return;
            }
            String countyName = county.getRegion_name();
            int countyId = county == null ? 0 : county.getId();

            mOnCitySelectListener.onCitySelect(provinceName, cityName, provinceId, cityId, countyName, countyId);
        }
    }

    public interface OnCitySelectListener {

        //        void onCitySelect(String province, String city, int provinceid, int cityid);
        void onCitySelect(String province, String city, int provinceid, int cityid, String countyName, int countyId);
    }

}
