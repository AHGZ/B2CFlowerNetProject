package com.android.p2pflowernet.project.o2omain.fragment.mine.take_out.all_order;

import com.android.p2pflowernet.project.entity.AppTradeBean;
import com.android.p2pflowernet.project.entity.BanlanceBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.EncoreBean;
import com.android.p2pflowernet.project.entity.TakeOutBean;
import com.android.p2pflowernet.project.entity.UnionPayEntity;
import com.android.p2pflowernet.project.event.UserBanclanceBean;

/**
 * Created by caishen on 2018/1/5.
 * by--
 */

interface ITakeOutOrderView {

    String getSate();

    void showDialog();

    void onError(String s);

    int getPage();

    void hideDialog();

    void onSuccessData(TakeOutBean data);

    void SuccessgetBanlance(UserBanclanceBean data);

    void UnionPaySuccess(UnionPayEntity unionPayResult);

    String getOrderNum();

    String getPaySource();

    String getType();

    String is_father_order();

    void onSuccessAli(AppTradeBean data);

    void onSuccessCheck(CheckPwdBean data);

    void onSuccessed(String message);

    void onSuccessYe(BanlanceBean data);

    void onPayYeError(String message);

    String getSalePrice();

    void onSuccessCancelOrder(String message);

    void onSuccessEncore(EncoreBean data);

    void onSuccessCancelRefund(String data);

    //确认收货成功回调
    void onSuccessConfirmGoods(String data);
}
