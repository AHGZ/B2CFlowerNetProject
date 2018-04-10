package com.android.p2pflowernet.project.view.fragments.checkmobile;


import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * author: zhangpeisen
 * created on: 2017/7/25 上午10:21
 * description: 验证手机号逻辑处理层
 */
public class ICheckMobilePresenter extends IPresenter<ICheckMobileView> {

    private final ICheckMobileModel iCheckMobileModel;


    public ICheckMobilePresenter() {

        iCheckMobileModel = new ICheckMobileModel();

    }

    //检验手机号是否是银行卡留存的
    public void checkPhoneToBank() {


    }


    //检验输入的支付密码是否正确
    public void checkPayPwd() {
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        getView().showDialog();

        String pwd = getView().getPwd();
        iCheckMobileModel.checkPayPwd(pwd, new IModelImpl<ApiResponse<String>, String>() {
            @Override
            protected void onSuccess(String data, String message) {
                getView().hideDialog();
                getView().onSetSuccess(message);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<String>> data, String message) {
                getView().hideDialog();
                getView().onSetSuccess(message);
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
        iCheckMobileModel.cancel();
    }

}
