package com.android.p2pflowernet.project.view.customview;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.android.p2pflowernet.project.entity.MerchTypeBean;
import com.android.p2pflowernet.project.view.customview.actionsheet.WheelPicker;
import com.android.p2pflowernet.project.view.customview.actionsheet.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caishen on 2018/1/20.
 * by--
 */

public class SelectsMerchTypePicker extends WheelPicker {

    private OnCitySelectListener mOnCitySelectListener;

    private Activity mContext;

    private List<MerchTypeBean.CateBean> mSelectProviceEnities;

    private WheelView provinceView;
    private WheelView cityView;
    private WheelView countyView;

    public SelectsMerchTypePicker(Activity activity, List<MerchTypeBean.CateBean> selectProviceEnities) {
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
     * 获取一级名称列表
     *
     * @return
     */
    private List<String> getProvinceNames() {
        List<String> provinces = new ArrayList<>();
        for (MerchTypeBean.CateBean province : mSelectProviceEnities) {
            if (province == null) {
                provinces.add("");
            } else {
                provinces.add(province.getName());
            }
        }
        return provinces;
    }


    public SelectsMerchTypePicker setOnCitySelectListener(OnCitySelectListener listener) {
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
        // 一级
        provinceView = new WheelView(activity);
        provinceView.setLayoutParams(new LinearLayout.LayoutParams(screenWidthPixels / 3, WRAP_CONTENT));
        provinceView.setTextSize(textSize);
        provinceView.setTextColor(textColorNormal, Color.parseColor("#333333"));
        provinceView.setLineVisible(lineVisible);
        provinceView.setLineColor(Color.parseColor("#D7D7D7"));
        provinceView.setOffset(offset);
        layout.addView(provinceView);
        // 二级
        cityView = new WheelView(activity);
        cityView.setLayoutParams(new LinearLayout.LayoutParams(screenWidthPixels / 3, WRAP_CONTENT));
        cityView.setTextSize(textSize);
        cityView.setTextColor(textColorNormal, Color.parseColor("#333333"));
        cityView.setLineVisible(lineVisible);
        cityView.setLineColor(Color.parseColor("#D7D7D7"));
        cityView.setOffset(offset);
        layout.addView(cityView);
        // 三级
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

        if (mSelectProviceEnities.get(provincePos).getChild() != null
                && mSelectProviceEnities.get(provincePos).getChild().size() > 0) {

            for (MerchTypeBean.CateBean.ChildBeanX city : mSelectProviceEnities.get(provincePos).getChild()) {
                if (city == null) {
                    cities.add("");
                } else {
                    cities.add(city.getName());
                }
            }
        } else {

            cities.add("");
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

        if (mSelectProviceEnities.get(proviceId).getChild() != null) {

            if (mSelectProviceEnities.get(proviceId).getChild().get(cityId).getChild() != null
                    && (mSelectProviceEnities.get(proviceId).getChild().get(cityId).getChild().size() > 0)) {

                for (MerchTypeBean.CateBean.ChildBeanX.ChildBean county : mSelectProviceEnities.get(proviceId).
                        getChild().get(cityId).getChild()) {
                    counties.add(county.getName());
                }

            } else {

                counties.add("");
            }

        } else {

            counties.add("");
        }

        return counties;
    }

    @Override
    protected void onSubmit() {
        super.onSubmit();
        if (mOnCitySelectListener != null) {
            // 一级
            MerchTypeBean.CateBean province = mSelectProviceEnities.get(mSelectProviceEnities.size() == provinceView.getSelectedIndex()
                    ? provinceView.getSelectedIndex() - 1 : provinceView.getSelectedIndex());
            // 二级
            MerchTypeBean.CateBean.ChildBeanX city = (province.getChild().size() > 0) ? province.getChild().get(cityView.getSelectedIndex()) : null;
            String provinceName = province.getName();
            int provinceId = province.getId();

            if (province.getChild().isEmpty()) {
                mOnCitySelectListener.onCitySelect(provinceName, "", provinceId, 0, "", 0);
                return;
            }
            String cityName = city == null ? "" : city.getName();
            int cityId = city == null ? 0 : city.getId();
            mOnCitySelectListener.onCitySelect(provinceName, cityName, provinceId, cityId, "", 0);

            //区县
            MerchTypeBean.CateBean.ChildBeanX.ChildBean county = (city.getChild().size() > 0) ? city.getChild().get(countyView.getSelectedIndex()) : null;
            if (city.getChild().isEmpty()) {
                mOnCitySelectListener.onCitySelect(provinceName, "", provinceId, 0, "", 0);
                return;
            }
            String countyName = county.getName();
            int countyId = county == null ? 0 : county.getId();

            mOnCitySelectListener.onCitySelect(provinceName, cityName, provinceId, cityId, countyName, countyId);
        }
    }

    public interface OnCitySelectListener {

        //        void onCitySelect(String province, String city, int provinceid, int cityid);
        void onCitySelect(String province, String city, int provinceid, int cityid, String countyName, int countyId);
    }
}
