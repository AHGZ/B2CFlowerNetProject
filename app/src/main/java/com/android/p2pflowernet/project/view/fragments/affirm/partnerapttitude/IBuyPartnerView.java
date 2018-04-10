package com.android.p2pflowernet.project.view.fragments.affirm.partnerapttitude;

import com.android.p2pflowernet.project.entity.AppTradeBean;
import com.android.p2pflowernet.project.entity.ApplyFroPayBean;
import com.android.p2pflowernet.project.entity.BanlanceBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.UnionPayEntity;
import com.android.p2pflowernet.project.event.UserBanclanceBean;

/**
 * Created by caishen on 2017/11/9.
 * by--购买合伙人资质的视图层
 */

public interface IBuyPartnerView {


    void onError(String s);

    void hideDialog();

    void UnionPaySuccess(UnionPayEntity unionPayResult);

    void showDialog();

    void onSuccessCheck(CheckPwdBean data);

    void onSuccessed(String message);

    String getBody();

    String getOrderNum();

    String getPaySource();

    void onSuccessAli(AppTradeBean data);

    String getType();

    String is_father_order();

    String getnum();

    void onSuccessYe(BanlanceBean data);

    void onPayYeError(String message);

    void SuccessgetBanlance(UserBanclanceBean data);

    void onsuccessApplyFor(ApplyFroPayBean data);
}
