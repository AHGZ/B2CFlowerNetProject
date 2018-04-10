package com.android.p2pflowernet.project.o2omain.fragment.mine.group_take_out.fulldetail;

import com.android.p2pflowernet.project.entity.AppTradeBean;
import com.android.p2pflowernet.project.entity.BanlanceBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.GroupFullDetailBean;
import com.android.p2pflowernet.project.event.UserBanclanceBean;

/**
 * Created by heguozhong on 2018/1/15/015.
 * 团购明细视图层
 */

public interface IGroupFullDetailView {
    void onError(String errorMsg);

    void onSuccess(GroupFullDetailBean groupFullDetailBean);

    void onSuccess(String message);

    void showDialog();

    void hideDialog();
    // 收货地址的纬度
    String latitude();

    // 收货地址的经度
    String longitude();

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
}
