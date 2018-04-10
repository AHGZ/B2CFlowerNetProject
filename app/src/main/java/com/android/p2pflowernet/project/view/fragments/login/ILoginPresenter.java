package com.android.p2pflowernet.project.view.fragments.login;


import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.UserInfo;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.utils.ParamMatchUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * author: zhangpeisen
 * created on: 2017/7/25 上午10:21
 * description: 登录view与控制链接层
 */
public class ILoginPresenter extends IPresenter<ILoginView> {

    private ILoginModel mILoginModel;

    public ILoginPresenter() {

        mILoginModel = new ILoginModel();
    }

    public void login() {

        String phone = getView().getUser();
        String pwd = getView().getPwd();

        if (!check(phone, pwd))
            return;
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        if (pwd != null) {
            //必须包含小写字母，数字，可以是字母数字下划线组成并且长度是6到20
//            String IDCardRegex = "(\"^(?=.*?[a-z])(?=.*?[0-9])[a-zA-Z0-9_]{6,20}$\")";
            Pattern PwdCardRegex = Pattern.compile("^(?=.*?[a-z])(?=.*?[0-9])[a-zA-Z0-9_]{6,20}$");
            boolean matches = PwdCardRegex.matcher(pwd).matches();
            if (matches == false) {
                getView().onError("输入6-20位包含字母和数字组合");
                return;
            }
        }
        getView().showDialog();
        mILoginModel.login(phone, pwd, new IModelImpl<ApiResponse<UserInfo>, UserInfo>() {
            @Override
            protected void onSuccess(UserInfo data, String message) {
                getView().hideDialog();
                getView().onLoginSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<UserInfo>> data, String message) {

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

    private boolean check(String phone, String pwd) {
        if (TextUtils.isEmpty(phone)) {
            getView().onError("请填写手机号");
            return false;
        }

        if (!ParamMatchUtils.isPhoneAvailable(phone)) {
            getView().onError("请填写正确的手机号");
            return false;
        }

        if (TextUtils.isEmpty(pwd)) {
            getView().onError("请输入密码");
            return false;
        }

        return true;
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
        mILoginModel.cancel();
    }
}
