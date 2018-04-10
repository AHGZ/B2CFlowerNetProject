package com.android.p2pflowernet.project.view.fragments.mine.applyfor.partner;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.AddAuthInfoBean;
import com.android.p2pflowernet.project.entity.AllPlaceDataBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/11/20.
 * by--完善个人信息的逻辑层
 */

public class ImproveInfoFPrenter extends IPresenter<IImproveInfoView> {

    private final ImproveInfoModel improveInfoModel;
    private final ImproveGuaranteeModel improveGuaranteeModel;

    @Override
    protected void cancel() {

        improveInfoModel.cancel();
    }

    public ImproveInfoFPrenter() {

        improveInfoModel = new ImproveInfoModel();
        improveGuaranteeModel = new ImproveGuaranteeModel();
    }

    //完善个人信息
    public void addAuthInfo() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String userId = getView().getUserId();
        getView().showDialog();
        improveInfoModel.addAuthInfo(userId, new IModelImpl<ApiResponse<AddAuthInfoBean>, AddAuthInfoBean>() {
            @Override
            protected void onSuccess(AddAuthInfoBean data, String message) {

                getView().hideDialog();
                getView().setAddAuthInfoSuccess(data);

            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<AddAuthInfoBean>> data, String message) {
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

    //获取城市列表数据
    public void getcitydata() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

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
                getView().onSucceessAllData(data);
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

    //填写保单信息
    public void addInsuranceInfo() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String userId = getView().getUserId();
        String maritalState = getView().getmaritalState();
        String address = getView().getAddress();
        String provinceIid = getView().getprovinceIid();
        String cityId = getView().getcityId();
        String distictIid = getView().getdistictIid();

        if (TextUtils.isEmpty(cityId) || cityId.equals("") || TextUtils.isEmpty(provinceIid) || TextUtils.isEmpty(distictIid)) {
            getView().onError("请选择地址");
            return;
        }

        String locationAddress = getView().getlocationAddress();

        getView().showDialog();

        //保存个人信息
        improveInfoModel.addInsuranceInfo(userId, maritalState, address, provinceIid, cityId, distictIid, locationAddress,
                new IModelImpl<ApiResponse<String>, String>() {
                    @Override
                    protected void onSuccess(String data, String message) {
                        getView().onSuccess(message);
                        getView().hideDialog();
                    }

                    @Override
                    protected void onSuccess(ArrayList<ApiResponse<String>> data, String message) {
                        getView().onSuccess(message);
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
