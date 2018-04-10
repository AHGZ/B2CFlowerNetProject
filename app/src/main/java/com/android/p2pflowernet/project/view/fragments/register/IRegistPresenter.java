package com.android.p2pflowernet.project.view.fragments.register;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.CheckMobileBean;
import com.android.p2pflowernet.project.entity.SendCodeBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.utils.ParamMatchUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/10/16.
 * by--
 */

public class IRegistPresenter extends IPresenter<IRegistView> {


    private final RegistModel registModel;


    public IRegistPresenter() {

        registModel = new RegistModel();

    }

    //探测手机号接口
    public void checkMoblie() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String phone = getView().getPhone();
        if (!check(phone)) {
            getView().onError("请填写正确的手机号");
            return;
        }

        getView().showDialog();

        registModel.CheckMobile(phone, new IModelImpl<ApiResponse<CheckMobileBean>, CheckMobileBean>() {
            @Override
            protected void onSuccess(CheckMobileBean checkMobileBean, String message) {
                getView().hideDialog();
                getView().sendCheckMobileSuccess(checkMobileBean);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<CheckMobileBean>> data, String message) {

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

    //发送验证码
    public void sendCode() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String phone = getView().getPhone();
        if (!check(phone)) {
            getView().onError("请填写正确的手机号");
            return;
        }
        getView().showDialog();

        registModel.sendCode(phone, new IModelImpl<ApiResponse<SendCodeBean>, SendCodeBean>() {
            @Override
            protected void onSuccess(SendCodeBean sendCodeBean, String message) {
                getView().hideDialog();
                getView().sendCodeSuccess(sendCodeBean);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<SendCodeBean>> data, String message) {

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

    @Override
    protected void cancel() {
        registModel.cancel();
    }
}
