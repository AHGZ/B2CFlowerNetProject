package com.android.p2pflowernet.project.view.fragments.mine.orderflow.orderitemdetail;

import com.android.p2pflowernet.project.entity.AppTradeBean;
import com.android.p2pflowernet.project.entity.BanlanceBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.OrderDetailItemBean;
import com.android.p2pflowernet.project.entity.UnionPayEntity;
import com.android.p2pflowernet.project.event.UserBanclanceBean;

/**
 * Created by caishen on 2017/11/10.
 * by--
 */

public interface IOrderDetailItemView {


    String getOrderId();

    void onError(String s);

    void showDialog();

    void hideDialog();

    void SuccessDetail(OrderDetailItemBean data);

    void onSuccessCancleOrder(String message);

    void onSuccessAffirm(String message);

    void SuccessgetBanlance(UserBanclanceBean data);

    void UnionPaySuccess(UnionPayEntity unionPayResult);

    String getBody();

    String getOrderNum();

    String getPaySource();

    void onSuccessAli(AppTradeBean data);

    void onSuccessCheck(CheckPwdBean data);

    void onSuccessed(String message);

    String getSalePrice();

    void onSuccessYe(BanlanceBean data);

    String getType();

    String is_father_order();

    void onPayYeError(String message);

}
