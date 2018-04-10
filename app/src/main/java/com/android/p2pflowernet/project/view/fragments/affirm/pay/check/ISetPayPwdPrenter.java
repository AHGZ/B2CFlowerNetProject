package com.android.p2pflowernet.project.view.fragments.affirm.pay.check;

import com.android.p2pflowernet.project.entity.PayPwdBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.affirm.pay.PayPwdModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/12/4.
 * by--设置支付密码的逻辑层
 */

public class ISetPayPwdPrenter extends IPresenter<ISetPayPwdView> {


    private final PayPwdModel payPwdModel;

    @Override
    protected void cancel() {

    }

    public ISetPayPwdPrenter() {

        payPwdModel = new PayPwdModel();
    }

    //设置支付密码
    public void setPayPwd() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String firstStr = getView().getFirstStr();
        String lastStr = getView().getLastStr();

        payPwdModel.setPayPwd(firstStr, lastStr, new IModelImpl<ApiResponse<PayPwdBean>, PayPwdBean>() {
            @Override
            protected void onSuccess(PayPwdBean data, String message) {
                getView().hideDialog();
                getView().onSetSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<PayPwdBean>> data, String message) {
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

    //修改支付密码
    public void updataPayPwd() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String firstStr = getView().getFirstStr();
        String lastStr = getView().getLastStr();

        payPwdModel.updataPayPwd(firstStr, lastStr, new IModelImpl<ApiResponse<String>, String>() {
            @Override
            protected void onSuccess(String data, String message) {
                getView().hideDialog();
                getView().onSuccess(message);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<String>> data, String message) {
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
