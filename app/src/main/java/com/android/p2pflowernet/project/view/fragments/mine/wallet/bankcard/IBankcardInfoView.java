package com.android.p2pflowernet.project.view.fragments.mine.wallet.bankcard;

/**
 * Created by caishen on 2017/11/11.
 * by--银行卡信息视图
 */

public interface IBankcardInfoView {
    // 银行卡号
    String getBankcardNo();

    // 姓名
    String getRealName();

    // 身份证号
    String getIdCard();

    // 进度条显示
    void showDialog();

    // 进度条隐藏
    void hideDialog();

    // 是否勾选协议
    boolean getProfile();


    void onError(String message);

    void onSuccess(String message);


}
