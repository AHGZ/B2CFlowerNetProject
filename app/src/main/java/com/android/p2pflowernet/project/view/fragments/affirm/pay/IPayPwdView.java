package com.android.p2pflowernet.project.view.fragments.affirm.pay;

import com.android.p2pflowernet.project.entity.PayPwdBean;

/**
 * Created by caishen on 2017/11/2.
 * by--支付密码输入页面的视图层
 */

public interface IPayPwdView {


    void onError(String s);

    void showDialog();

    String getPwd();

    String getConfirmPwd();

    void hideDialog();

    void onSetSuccess(PayPwdBean data);

    void onSuccess(String message);
}
