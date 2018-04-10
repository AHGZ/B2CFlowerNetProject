package com.android.p2pflowernet.project.o2omain.fragment.pay;

import com.android.p2pflowernet.project.entity.AppTradeBean;
import com.android.p2pflowernet.project.entity.BanlanceBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.MerchXqBean;
import com.android.p2pflowernet.project.entity.ScanOrderBean;

/**
 * Created by caishen on 2017/12/29.
 * by--
 */

interface IAffirmPayView {
    void onError(String s);

    void showDialog();

    void hideDialog();

    String merch_id();

    void successData(MerchXqBean data);

    void onSuccess(String message);

    String getMoney();

    void successCommit(ScanOrderBean data);

    String getOrderNum();

    String getPaySource();

    String getType();

    String is_father_order();

    void onSuccessAli(AppTradeBean data);

    void onSuccessCheck(CheckPwdBean data);

    void onSuccessed(String message);

    void onSuccessYe(BanlanceBean data);

    void onPayYeError(String message);
}
