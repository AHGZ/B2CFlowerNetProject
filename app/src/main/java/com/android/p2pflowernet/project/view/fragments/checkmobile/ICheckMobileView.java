package com.android.p2pflowernet.project.view.fragments.checkmobile;

/**
 * author: zhangpeisen
 * created on: 2017/7/25 上午10:21
 * description:验证手机号
 */
public interface ICheckMobileView {
    void onError(String s);

    String getPwd();

    void hideDialog();

    void onSetSuccess(String message);

    void showDialog();
}
