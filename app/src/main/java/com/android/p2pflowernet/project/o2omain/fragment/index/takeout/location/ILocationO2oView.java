package com.android.p2pflowernet.project.o2omain.fragment.index.takeout.location;

import com.android.p2pflowernet.project.entity.O2oAddressBean;

/**
 * Created by caishen on 2018/1/3.
 * by--
 */
public interface ILocationO2oView {
    String IsOrder();

    String longitude();

    String latitude();

    void onError(String message);

    void onSuccess(O2oAddressBean o2oAddressBean);

    void showDialog();

    void hideDialog();

    String getAdressId();

    void onSuccessMessage(String message);
}
