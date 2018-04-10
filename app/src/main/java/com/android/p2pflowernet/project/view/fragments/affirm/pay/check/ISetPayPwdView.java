package com.android.p2pflowernet.project.view.fragments.affirm.pay.check;

import com.android.p2pflowernet.project.entity.PayPwdBean;

/**
 * Created by caishen on 2017/12/4.
 * by--
 */

interface ISetPayPwdView {
    String getFirstStr();

    String getLastStr();

    void onError(String s);

    void hideDialog();

    void onSetSuccess(PayPwdBean data);

    void onSuccess(String message);
}
