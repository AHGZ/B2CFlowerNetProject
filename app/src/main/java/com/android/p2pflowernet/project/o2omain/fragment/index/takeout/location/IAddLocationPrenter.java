package com.android.p2pflowernet.project.o2omain.fragment.index.takeout.location;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.AllPlaceDataBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.o2omain.fragment.O2oModel;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.utils.ParamMatchUtils;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.partner.ImproveGuaranteeModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2018/1/3.
 * by--
 */

class IAddLocationPrenter extends IPresenter<IAddLocationView> {
    private final O2oModel o2oModel;
    private final ImproveGuaranteeModel improveGuaranteeModel;

    public IAddLocationPrenter() {
        o2oModel = new O2oModel();
        improveGuaranteeModel = new ImproveGuaranteeModel();
    }

    /**
     * 添加收货地址
     */
    public void addAdderss() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String name = getView().getName();
        String sex = getView().getSex();
        String telephone = getView().getTelephone();
        String location = getView().getLocation();
        String address = getView().getAddress();
        String latitude = getView().getLatitude();
        String longitude = getView().getLongitude();
        String site_name = getView().getSiteName();
        if (TextUtils.isEmpty(name)) {
            getView().onError("请填写姓名");
            return;
        }
        if (TextUtils.isEmpty(sex)) {
            getView().onError("请选择性别");
            return;
        }
        if (!checkPhone(telephone)) {
            return;
        }
        if (TextUtils.isEmpty(location)) {
            getView().onError("请选择小区/大厦/学校");
            return;
        }
        if (TextUtils.isEmpty(address)) {
            getView().onError("请填写详细地址");
            return;
        }

        getView().showDialog();
        o2oModel.adduseraddre(name, sex, telephone, location,site_name, address, latitude, longitude, new IModelImpl<ApiResponse<String>, String>() {


            @Override
            protected void onSuccess(String data, String message) {
                getView().hideDialog();
                getView().onSuccess(data);
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

    private boolean checkPhone(String phone) {
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

    //编辑收货地址
    public void upuseradd(){
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String id = getView().getAddressId();
        String name = getView().getName();
        String sex = getView().getSex();
        String telephone = getView().getTelephone();
        String location = getView().getLocation();
        String address = getView().getAddress();
        String latitude = getView().getLatitude();
        String longitude = getView().getLongitude();
        if (TextUtils.isEmpty(name)) {
            getView().onError("请填写姓名");
            return;
        }
        if (TextUtils.isEmpty(sex)) {
            getView().onError("请选择性别");
            return;
        }
        if (!checkPhone(telephone)) {
            return;
        }
        if (TextUtils.isEmpty(location)) {
            getView().onError("请选择小区/大厦/学校");
            return;
        }
        if (TextUtils.isEmpty(address)) {
            getView().onError("请填写详细地址");
            return;
        }

        getView().showDialog();
        o2oModel.upuseradd(id, name, sex, telephone, location, address, latitude, longitude, new IModelImpl<ApiResponse<String>, String>() {
            @Override
            protected void onSuccess(String data, String message) {
                getView().hideDialog();
                getView().onSuccess(data);
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

    @Override
    protected void cancel() {
        o2oModel.cancel();
    }
}
