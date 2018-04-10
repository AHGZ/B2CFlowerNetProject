package com.android.p2pflowernet.project.view.fragments.mine.setting.adress;

import com.android.p2pflowernet.project.entity.AdressMangerBean;

/**
 * Created by caishen on 2017/11/23.
 * by--
 */

public interface IAdressmangerView {
    void showDialog();

    void hideDialog();

    void setSuccessAdressList(AdressMangerBean data);

    void onError(String message);

    String getAdressId();

    void onSuccess(String message);

    String getName();

    String gettelephone();

    String getprovinceId();

    String getcityId();

    String getdistrictId();

    String getaddress();

    String getisDefault();

    String getLocation();
}
