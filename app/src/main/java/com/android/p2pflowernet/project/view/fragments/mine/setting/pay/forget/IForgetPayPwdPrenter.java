package com.android.p2pflowernet.project.view.fragments.mine.setting.pay.forget;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.SendCodeBean;
import com.android.p2pflowernet.project.entity.ShowPersonInfo;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.utils.ParamMatchUtils;
import com.android.p2pflowernet.project.view.fragments.mine.setting.personal.PersonalModel;
import com.android.p2pflowernet.project.view.fragments.register.RegistModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/12/7.
 * by--
 */

public class IForgetPayPwdPrenter extends IPresenter<IForgetPayPwdView> {


    private final RegistModel registModel;
    private final PersonalModel personalModel;

    @Override
    protected void cancel() {

        registModel.cancel();
        personalModel.cancel();
    }

    public IForgetPayPwdPrenter() {

        registModel = new RegistModel();
        personalModel = new PersonalModel();

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

    //获取个人信息
    public void getPersonInfo() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        //加载进度条
        getView().showDialog();
        personalModel.showPersonalInfo(new IModelImpl<ApiResponse<ShowPersonInfo>, ShowPersonInfo>() {
            @Override
            protected void onSuccess(ShowPersonInfo data, String message) {
                getView().hideDialog();
                getView().setShowPersonInfo(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<ShowPersonInfo>> data, String message) {

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
