package com.android.p2pflowernet.project.view.fragments.mine.setting.login;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.UdateUserLoginBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.MD5Utils;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by caishen on 2017/11/18.
 * by--修改登录密码的逻辑层
 */

public class IUpdateLoginPwdPrenter extends IPresenter<IUpdateLoginPwdView> {

    private final UpdateLoginPwdModel updateLoginPwdModel;

    @Override
    protected void cancel() {

        updateLoginPwdModel.cancel();
    }

    public IUpdateLoginPwdPrenter() {

        updateLoginPwdModel = new UpdateLoginPwdModel();

    }

    //确认修改密码
    public void updateUserPwd() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String aPwd = getView().getAPwd();
        if (TextUtils.isEmpty(aPwd)) {
            getView().onError("请输入原密码");
            return;
        }

        String bPwd = getView().getBPwd();
        if (TextUtils.isEmpty(bPwd)) {
            getView().onError("请输入新密码");
            return;
        }

        String cPwd = getView().getCPwd();
        if (TextUtils.isEmpty(cPwd)) {
            getView().onError("请输入确认密码");
            return;
        }

        //必须包含小写字母，数字，可以是字母数字下划线组成并且长度是6到20
        Pattern PwdCardRegex = Pattern.compile("^(?=.*?[a-z])(?=.*?[0-9])[a-zA-Z0-9_]{6,20}$");
        boolean matches = PwdCardRegex.matcher(bPwd).matches();
        if (matches == false) {

            getView().onError("输入6-20位包含字母和数字组合");
            return;
        }

        //必须包含小写字母，数字，可以是字母数字下划线组成并且长度是6到20
        boolean matches1 = PwdCardRegex.matcher(cPwd).matches();
        if (matches1 == false) {

            getView().onError("输入6-20位包含字母和数字组合");
            return;
        }


        getView().showDialog();

        //修改登录用户密码
        updateLoginPwdModel.updateUserPwd(MD5Utils.MD5To32(aPwd), MD5Utils.MD5To32(bPwd), MD5Utils.MD5To32(cPwd), new IModelImpl<ApiResponse<UdateUserLoginBean>, UdateUserLoginBean>() {
            @Override
            protected void onSuccess(UdateUserLoginBean sendCodeBean, String message) {
                getView().hideDialog();
                getView().sendCodeSuccess(sendCodeBean);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<UdateUserLoginBean>> data, String message) {
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
