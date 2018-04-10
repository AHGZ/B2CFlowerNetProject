package com.android.p2pflowernet.project.o2omain.fragment.index.takeout.location;

import com.android.p2pflowernet.project.entity.AllPlaceDataBean;

/**
 * Created by caishen on 2018/1/3.
 * by--
 */

interface IAddLocationView {
    //获取姓名
    String getName();
    //获取性别
    String getSex();
    //获取电话
    String getTelephone();
    //获取省ID
    String getProvinceId();
    //获取市ID
    String getCityId();
    //获取区/县ID
    String getDistrictId();
    //获取省+市+区/县
    String getLocation();
    //获取详细地址
    String getAddress();
    //获取纬度
    String getLatitude();
    //获取经度
    String getLongitude();
    //获取地址ID
    String getAddressId();
    //获取商圈地址
    String getSiteName();

    void setVestAddressSuccess(AllPlaceDataBean data);

    void onSuccess(String data);

    void onError(String message);

    void showDialog();

    void hideDialog();
}
