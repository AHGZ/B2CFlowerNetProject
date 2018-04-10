package com.android.p2pflowernet.project.view.fragments.affirm;

import com.android.p2pflowernet.project.entity.AppTradeBean;
import com.android.p2pflowernet.project.entity.BanlanceBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.CommitAffirmBean;
import com.android.p2pflowernet.project.entity.OrderDetailBean;
import com.android.p2pflowernet.project.entity.UnionPayEntity;
import com.android.p2pflowernet.project.event.UserBanclanceBean;

/**
 * Created by caishen on 2017/10/28.
 * by--确认订单的视图层
 */
public interface IArrirmIndentView {
    // 支付失败监听类
    void onError(String errorMsg);

    // 支付宝
    void AlipaySuccess();

    // 银联
    void UnionPaySuccess(UnionPayEntity unionPayResult);

    // 微信支付
    void WeChatPaySuccess();

    void showDialog();

    void hideDialog();

    void onSuccessed(String message);

    void onSuccessCheck(CheckPwdBean data);

    void onSuccessYe(BanlanceBean data);

    String getSpecId();

    String getCount();

    String getSource();

    String getSelect();

    String getInvoice();

    String getTitle();

    String getTaxNum();

    String getTexture();

    String getUserType();

    void SuccessComit(CommitAffirmBean data);

    String getAddressId();

    String getSalePrice();

    String getBody();

    String getOrderNum();

    String getSubject();

    void onSuccessAli(AppTradeBean data);

    String getPaySource();

    void SuccessgetBanlance(UserBanclanceBean data);

    String getType();//订单类型支付类型 1，O2O  2，B2c 3,购买合伙人 4，代理人追加，5 代理人购买资质

    String is_father_order();//1为父2为否（在type为1,2时必填）

    void onPayYeError(String message);

    void SuccessOrder(OrderDetailBean data);
}
