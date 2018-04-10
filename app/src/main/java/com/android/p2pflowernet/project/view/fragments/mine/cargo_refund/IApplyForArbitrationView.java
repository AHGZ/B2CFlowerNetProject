package com.android.p2pflowernet.project.view.fragments.mine.cargo_refund;

/**
 * Created by caishen on 2017/11/20.
 * by--申请仲裁的页面
 */

public interface IApplyForArbitrationView {

    String getRefundId();

    String getContent();

    void showDialog();

    void hideDialog();

    void onError(String errorMsg);

    void onSuccess(String message);
}
