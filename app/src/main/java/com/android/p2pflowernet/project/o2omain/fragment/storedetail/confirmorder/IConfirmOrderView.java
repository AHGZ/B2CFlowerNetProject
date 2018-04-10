package com.android.p2pflowernet.project.o2omain.fragment.storedetail.confirmorder;

import com.android.p2pflowernet.project.entity.AppTradeBean;
import com.android.p2pflowernet.project.entity.BanlanceBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.GoPayBean;
import com.android.p2pflowernet.project.entity.O2oorderCommitBean;
import com.android.p2pflowernet.project.event.UserBanclanceBean;

/**
 * Created by heguozhong on 2018/1/10/010.
 * 确认订单视图层
 */

public interface IConfirmOrderView {
    // 商家ID
    String merchId();

    // 商品ID
    String goodslist();

    //是否到店自取
    String getis_self();

    //是否开发票
    String getis_invoice();

    //开票类型
    String getinvoice_type();

    //税号
    String getTaxNum();

    //发票抬头
    String getTitle();

    //自取电话
    String getphone();

    // 收货地址ID
    String addressid();

    // 用餐人数
    String dinnersetcount();

    // 预约时间
    String reachtime();

    //备注
    String getNote();

    // 收货地址的纬度
    String latitude();

    // 收货地址的经度
    String longitude();

    void onError(String s);

    void onSuccess(String message);

    void onSuccess(GoPayBean goPayBean);

    void showDialog();

    void hideDialog();

    void onSuccessAffirm(O2oorderCommitBean data);

    void SuccessgetBanlance(UserBanclanceBean data);

    String getOrderNum();

    String getPaySource();

    String getType();

    String is_father_order();

    void onSuccessAli(AppTradeBean data);

    void onSuccessCheck(CheckPwdBean data);

    String getSalePrice();

    void onSuccessYe(BanlanceBean data);

    void onPayYeError(String message);

}
