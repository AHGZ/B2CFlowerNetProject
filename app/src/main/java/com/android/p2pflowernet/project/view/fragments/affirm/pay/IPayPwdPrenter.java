package com.android.p2pflowernet.project.view.fragments.affirm.pay;

import com.android.p2pflowernet.project.entity.PayPwdBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/11/2.
 * by--支付密码输入的逻辑层
 */

public class IPayPwdPrenter extends IPresenter<IPayPwdView> {

    private final PayPwdModel payPwdModel;

    @Override
    protected void cancel() {

    }

    public IPayPwdPrenter() {

        payPwdModel = new PayPwdModel();

    }

    //设置支付密码
    public void addPwd() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String pwd = getView().getPwd();
        String confirmPwd = getView().getConfirmPwd();

        /**
         * 设置支付密码的接口
         */
        getView().showDialog();
        payPwdModel.setPayPwd(pwd, confirmPwd, new IModelImpl<ApiResponse<PayPwdBean>, PayPwdBean>() {

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
}
