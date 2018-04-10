package com.android.p2pflowernet.project.view.fragments.affirm.address;

import com.android.p2pflowernet.project.entity.AllPlaceDataBean;

/**
 * Created by caishen on 2017/11/7.
 * by--地址管理页面的视图层
 */

public interface IAdressView {
    String getName();

    String gettelephone();

    String getprovinceId();

    String getcityId();

    String getdistrictId();

    String getaddress();

    String getisDefault();

    String getAdressId();

    void hideDialog();

    void showDialog();

    void onError(String message);

    void setVestAddressSuccess(AllPlaceDataBean data);

    void onSuccess(String message);

    String getLocation();
}
