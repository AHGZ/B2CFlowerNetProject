package com.android.p2pflowernet.project.view.fragments.mine.setting.adress;

import com.android.p2pflowernet.project.entity.AdressMangerBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.affirm.address.IAddAdressModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/11/23.
 * by--地址管理逻辑层
 */

public class IAdressMangerPrenter extends IPresenter<IAdressmangerView> {

    private final IAdressMangerModel iAdressMangerModel;
    private final IAddAdressModel iAddAdressModel;

    @Override
    protected void cancel() {

        iAdressMangerModel.cancel();
        iAdressMangerModel.cancel();
    }

    public IAdressMangerPrenter() {

        iAdressMangerModel = new IAdressMangerModel();
        iAddAdressModel = new IAddAdressModel();

    }

    //获取用户的地址列表
    public void getUserAddressList() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        getView().showDialog();

        iAdressMangerModel.getUserAddressList(new IModelImpl<ApiResponse<AdressMangerBean>, AdressMangerBean>() {
            @Override
            protected void onSuccess(AdressMangerBean data, String message) {
                getView().hideDialog();
                getView().setSuccessAdressList(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<AdressMangerBean>> data, String message) {
                getView().hideDialog();
            }

            @Override
            protected void onFailure(String code, String message) {

                getView().hideDialog();
                getView().onError(message);

            }

            @Override
            protected void onSuccess() {

            }
        });
    }

    //删除地址
    public void deleteAdress() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        getView().showDialog();
        String adressId = getView().getAdressId();

        iAdressMangerModel.deleteAddress(adressId, new IModelImpl<ApiResponse<String>, String>() {
            @Override
            protected void onSuccess(String data, String message) {
                getView().onSuccess(message);
                getView().hideDialog();
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

            }
        });
    }

    //设置位默认地址
    public void isDefult() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        getView().showDialog();
        String adressId = getView().getAdressId();
        String getaddress = getView().getaddress();
        String countyId = getView().getdistrictId();
        String location = getView().getLocation();
        String name = getView().getName();
        String telephone = getView().gettelephone();
        String provincedId = getView().getprovinceId();
        String cityId = getView().getcityId();

        iAddAdressModel.addUpdateAdress(adressId, name, telephone, provincedId, cityId, location, countyId,
                getaddress, "1", new IModelImpl<ApiResponse<String>, String>() {
                    @Override
                    protected void onSuccess(String data, String message) {
                        getView().onSuccess(message);
                        getView().hideDialog();
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

                    }
                });
    }
}
