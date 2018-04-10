package com.android.p2pflowernet.project.o2omain.fragment.mine.take_out.takefulldetail;

import com.android.p2pflowernet.project.entity.AppTradeBean;
import com.android.p2pflowernet.project.entity.BanlanceBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.EncoreBean;
import com.android.p2pflowernet.project.entity.OrderDetailsBean;
import com.android.p2pflowernet.project.event.UserBanclanceBean;

/**
 * Created by heguozhong on 2018/1/15/015.
 * 外卖明细视图层
 */

public interface ITakeFullDetailView {
    void onError(String errorMsg);

    void onSuccess(OrderDetailsBean orderDetailsBean);

//    void onSuccessRefund(ReFundOrderBean reFundOrderBean);

    void onSuccess(String message);

    void showDialog();

    void hideDialog();

    void SuccessgetBanlance(UserBanclanceBean data);

    String getSalePrice();//获取支付金额

    String getType();

    void onSuccessYe(BanlanceBean data);

    void onPayYeError(String message);

    String getPaySource();

    void onSuccessAli(AppTradeBean data);

    void onSuccessCheck(CheckPwdBean data);

    void onSuccessed(String message);

    String is_father_order();

    String getOrderNumber();

    void onSuccessCancelOrder(String message);

    void onSuccessEncore(EncoreBean data);

    void onSuccessCancelRefund(String data);

    //确认收货成功回调
    void onSuccessConfirmGoods(String data);
}
