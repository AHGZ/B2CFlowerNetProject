package com.android.p2pflowernet.project.view.fragments.mine.cargo_refund;

import com.android.p2pflowernet.project.entity.RefundBean;

/**
 * Created by caishen on 2017/11/17.
 * by--退换货的视图层
 */

public interface IRefundingView {
    // id
    String getRefundid();


    int getPage();

    void showDialog();

    void hideDialog();

    void onError(String errorMsg);

    void onSuccess(String message);

    void onCancelSuccess(String message);

    // 退换货列表
    void onSuccess(RefundBean refundBean);
}
