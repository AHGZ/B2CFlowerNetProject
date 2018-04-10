package com.android.p2pflowernet.project.o2omain.fragment.index.takeout.location;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.O2oAddressBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.o2omain.fragment.O2oModel;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2018/1/3.
 * by--
 */

public class ILocationO2oPrenter extends IPresenter<ILocationO2oView> {
    private O2oModel o2oModel;

    public ILocationO2oPrenter() {
        o2oModel = new O2oModel();
    }

    //
    public void getAddress() {
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String isOrder = getView().IsOrder();
        String latitude = getView().latitude();
        String longitude = getView().longitude();

        if (TextUtils.isEmpty(isOrder) && isOrder.equals("")) {
            return;
        }

        if (TextUtils.isEmpty(latitude) && latitude.equals("")) {
            return;
        }

        if (TextUtils.isEmpty(longitude) && longitude.equals("")) {
            return;
        }

        getView().showDialog();
        o2oModel.getaddlist(isOrder, longitude, latitude, new IModelImpl<ApiResponse<O2oAddressBean>, O2oAddressBean>() {

            @Override
            protected void onSuccess(O2oAddressBean data, String message) {
                getView().hideDialog();
                getView().onSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<O2oAddressBean>> data, String message) {
                getView().hideDialog();
            }

            @Override
            protected void onFailure(String code, String message) {
                getView().hideDialog();
                getView().onError(message);
            }

            @Override
            protected void onSuccess() {
                getView().hideDialog();
            }
        });
    }

    @Override
    protected void cancel() {
        o2oModel.cancel();
    }

    //删除收货地址
    public void deleteAdress() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String adressId = getView().getAdressId();

        if (TextUtils.isEmpty(adressId)) {
            return;
        }

        getView().showDialog();
        o2oModel.deleteAdress(adressId, new IModelImpl<ApiResponse<String>, String>() {

            @Override
            protected void onSuccess(String data, String message) {
                getView().hideDialog();
                getView().onSuccessMessage(message);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<String>> data, String message) {
                getView().hideDialog();
            }

            @Override
            protected void onFailure(String code, String message) {
                getView().hideDialog();
                getView().onError(message);
            }

            @Override
            protected void onSuccess() {
                getView().hideDialog();
            }
        });
    }
}
