package com.android.p2pflowernet.project.o2omain.fragment.index.groupbuying.shopdetails.submitorder;

import com.android.p2pflowernet.project.entity.AppTradeBean;
import com.android.p2pflowernet.project.entity.BanlanceBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.GroupPutOrderBean;
import com.android.p2pflowernet.project.event.UserBanclanceBean;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by heguozhong on 2018/1/5/005.
 * 提交订单视图层
 */

public interface ISubmitOrderView {
    void onError(String errorMsg);


    void onSuccess(String message);

    void showDialog();

    void hideDialog();

    void onSuccessData(ArrayList<ApiResponse<String>> data);

    void onSuccesses(GroupPutOrderBean groupPutOrderBean);

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
