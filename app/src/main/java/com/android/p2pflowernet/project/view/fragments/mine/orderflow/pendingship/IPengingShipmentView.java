package com.android.p2pflowernet.project.view.fragments.mine.orderflow.pendingship;

import com.android.p2pflowernet.project.entity.AppTradeBean;
import com.android.p2pflowernet.project.entity.BanlanceBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.OrderListBean;
import com.android.p2pflowernet.project.entity.UnionPayEntity;
import com.android.p2pflowernet.project.event.UserBanclanceBean;

/**
 * @描述:待发货视图类
 * @创建人：zhangpeisen
 * @创建时间：2017/10/30 下午5:38
 * @修改人：zhangpeisen
 * @修改时间：2017/10/30 下午5:38
 * @修改备注：
 * @throws
 */

public interface IPengingShipmentView {
    int getPage();

    int getSeltype();

    int getOrderId();

    void onError(String errorMsg);

    void onSuccess(String message);

    void onSuccess(OrderListBean orderListBean);

    void showDialog();

    void hideDialog();

    void onSuccessCancleOrder(String message);

    void onSuccessAffirm(String message);

    void SuccessgetBanlance(UserBanclanceBean data);

    void onSuccessed(String message);

    void onSuccessCheck(CheckPwdBean data);

    void onSuccessAli(AppTradeBean data);

    String getPaySource();

    String getOrderNum();

    String getBody();

    void UnionPaySuccess(UnionPayEntity unionPayResult);

    void onSuccessYe(BanlanceBean data);

    String getSalePrice();

    void onDelOrderSuccess(String message);

    String getType();

    String is_father_order();

    void onPayYeError(String message);
}
