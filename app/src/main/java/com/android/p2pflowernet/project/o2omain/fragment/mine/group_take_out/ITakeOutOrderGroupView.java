package com.android.p2pflowernet.project.o2omain.fragment.mine.group_take_out;

import com.android.p2pflowernet.project.entity.AppTradeBean;
import com.android.p2pflowernet.project.entity.BanlanceBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.TakeOutOrderGroupBean;
import com.android.p2pflowernet.project.event.UserBanclanceBean;

/**
 * Created by caishen on 2018/1/6.
 * by--
 */

interface ITakeOutOrderGroupView {
    void onError(String s);

    String getstate();

    int getpages();

    void showDialog();

    void hideDialog();

    void onSuccessData(TakeOutOrderGroupBean data);

    String getOrderNumber();//获取订单号

    void onSuccessCancelGroupOrder(String str);//取消团购订单成功

    void onSuccessRefundGroupOrder(String string);//团购退订单成功

    void SuccessgetBanlance(UserBanclanceBean data);

    String getSalePrice();//获取支付金额

    String is_father_order();

    String getType();

    void onSuccessYe(BanlanceBean data);

    void onPayYeError(String message);

    String getPaySource();

    void onSuccessAli(AppTradeBean data);

    void onSuccessCheck(CheckPwdBean data);

    void onSuccessed(String message);
}
