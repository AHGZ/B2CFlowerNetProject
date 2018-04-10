package com.android.p2pflowernet.project.view.fragments.mine.setting.login;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.ChangePhoneBean;
import com.android.p2pflowernet.project.entity.SendCodeBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.utils.ParamMatchUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/11/18.
 * by--
 */

public class IChangePhonePrenter extends IPresenter<IChangePhoneView> {


    private final ChangePhoneModel changePhoneModel;

    public IChangePhonePrenter() {

        changePhoneModel = new ChangePhoneModel();

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

        changePhoneModel.sendCode(phone, new IModelImpl<ApiResponse<SendCodeBean>, SendCodeBean>() {
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

        changePhoneModel.cancel();
    }

    //跟换手机号
    public void changePhone() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String phone = getView().getPhone();
        if (TextUtils.isEmpty(phone)) {
            getView().onError("请输入手机号");
            return;
        }

        String code = getView().getCode();
        if (TextUtils.isEmpty(code)) {
            getView().onError("请输入验证码");
            return;
        }

        getView().showDialog();
        changePhoneModel.changePhone(phone, code, new IModelImpl<ApiResponse<ChangePhoneBean>, ChangePhoneBean>() {
            @Override
            protected void onSuccess(ChangePhoneBean changePhoneBean, String message) {
                getView().hideDialog();
                getView().changePhoneSuccess(changePhoneBean);
                getView().onSuccess(message);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<ChangePhoneBean>> data, String message) {
                getView().hideDialog();
                getView().onSuccess(message);
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
