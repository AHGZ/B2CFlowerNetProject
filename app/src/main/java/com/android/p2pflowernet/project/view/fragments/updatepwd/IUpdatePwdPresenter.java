package com.android.p2pflowernet.project.view.fragments.updatepwd;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.CheckMobileBean;
import com.android.p2pflowernet.project.entity.SendCodeBean;
import com.android.p2pflowernet.project.entity.UpdatePwd;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.utils.ParamMatchUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by caishen on 2017/10/18.
 * by--修改密码的逻辑层
 */

public class IUpdatePwdPresenter extends IPresenter<IUpdatePwdView> {

    private final UpdatePwdModel updatePwdModel;

    @Override
    protected void cancel() {

        updatePwdModel.cancel();
    }

    public IUpdatePwdPresenter() {

        updatePwdModel = new UpdatePwdModel();

    }

    //提交修改密码
    public void commit() {

        String pwd = getView().getPwd();
        String affirmPwd = getView().getaffirmPwd();
        String phone = getView().getPhone();
        String sendCode = getView().getSendCode();

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        if (TextUtils.isEmpty(pwd)) {
            getView().onError("密码不能为空");
            return;
        }

        if (sendCode.equals("") || sendCode == null) {
            getView().onError("请先获取验证码");
            return;
        }

        //必须包含小写字母，数字，可以是字母数字下划线组成并且长度是6到20
        Pattern PwdCardRegex = Pattern.compile("^(?=.*?[a-z])(?=.*?[0-9])[a-zA-Z0-9_]{6,20}$");
        boolean matches = PwdCardRegex.matcher(pwd).matches();
        if (matches == false) {

            getView().onError("输入6-20位包含字母和数字组合");
            return;
        }

        /**
         * 提交修改密码的接口
         */
        getView().showDialog();
        updatePwdModel.updateModel(pwd, phone, sendCode, new IModelImpl<ApiResponse<UpdatePwd>, UpdatePwd>() {
            @Override
            protected void onSuccess(UpdatePwd data, String message) {
                getView().onSendCodeSuccess(message);
                getView().hideDialog();
                getView().onLoginSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<UpdatePwd>> data, String message) {

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
    public void checkMoblie() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String phone = getView().getPhone();

        if (!checkPhone(phone)) {

            getView().onError("请填写正确的手机号");
            return;
        }

        getView().showDialog();

        updatePwdModel.CheckMobile(phone, new IModelImpl<ApiResponse<CheckMobileBean>, CheckMobileBean>() {

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

    //检查密码是否合法
    private boolean check(String pwd) {

        if (TextUtils.isEmpty(pwd)) {

            getView().onError("密码不能为空");
            return false;
        }

//        if (!isAffirmPwd(pwd)) {
//            getView().onError("请输入6~20位英文字母、数字或符号");
//            return_ticket false;
//        }
        return false;
    }

    //获取发送接收到的验证码
    public void sendCode() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String phone = getView().getPhone();

        if (!checkPhone(phone)) {

            getView().onError("请填写正确的手机号");
            return;
        }

        getView().showDialog();

        updatePwdModel.sendCode(phone, new IModelImpl<ApiResponse<SendCodeBean>, SendCodeBean>() {
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


    //判断是否满足设置密码的正则
    private Boolean isAffirmPwd(String pwd) {

        //必须包含小写字母，数字，可以是字母数字下划线组成并且长度是6到20
        Pattern z1_ = Pattern.compile("^(?=.*?[a-z])(?=.*?[0-9])[a-zA-Z0-9_]{6,20}$");
        boolean p = z1_.matcher(pwd).matches();
        return p;
    }


    //检查手机号是否合规
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
}
