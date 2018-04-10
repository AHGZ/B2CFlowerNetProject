package com.android.p2pflowernet.project.view.fragments.affirm.address;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.AllPlaceDataBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.utils.ParamMatchUtils;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.partner.ImproveGuaranteeModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/11/7.
 * by--地址管理的逻辑层
 */

public class IAdressPrenter extends IPresenter<IAdressView> {


    private final IAddAdressModel iAddAdressModel;
    private final ImproveGuaranteeModel improveGuaranteeModel;

    @Override
    protected void cancel() {

        iAddAdressModel.cancel();
    }

    public IAdressPrenter() {

        iAddAdressModel = new IAddAdressModel();
        improveGuaranteeModel = new ImproveGuaranteeModel();
    }

    //添加收货地址
    public void addUpdateAdress() {

        String name = getView().getName();
        String gettelephone = getView().gettelephone();
        String provinceId = getView().getprovinceId();
        String cityId = getView().getcityId();
        String countyId = getView().getdistrictId();
        String getaddress = getView().getaddress();
        String isDefault = getView().getisDefault();
        String adressId = getView().getAdressId();
        String location = getView().getLocation();

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        if (TextUtils.isEmpty(name)) {
            getView().onError("姓名不能为空");
            return;
        }

        if (!check(gettelephone)) {
            return;
        }

        if (TextUtils.isEmpty(provinceId)) {
            getView().onError("现居地址不能为空");
            return;
        }

        if (TextUtils.isEmpty(getaddress)) {
            getView().onError("详细地址不能为空");
            return;
        }


        getView().showDialog();

        //添加/修改收货地址
        iAddAdressModel.addUpdateAdress(adressId, name, gettelephone, provinceId, cityId, location,
                countyId, getaddress, isDefault, new IModelImpl<ApiResponse<String>, String>() {
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

    private boolean check(String phone) {
        if (TextUtils.isEmpty(phone)) {
            getView().onError("请填写手机号");
            return false;
        }

        if (!ParamMatchUtils.isPhoneAvailable(phone)) {
            getView().onError("请填写正确的手机号");
            return false;
        }
        return true;
    }

    //获取城市列表数据
    public void getcitydata() {

        getView().showDialog();
        improveGuaranteeModel.getCityDatas(new IModelImpl<ApiResponse<AllPlaceDataBean>, AllPlaceDataBean>() {
            @Override
            protected void onSuccess(AllPlaceDataBean data, String message) {
                getView().hideDialog();
                getView().setVestAddressSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<AllPlaceDataBean>> data, String message) {

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
