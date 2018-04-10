package com.android.p2pflowernet.project.view.fragments.mine.setting.login;

import com.android.p2pflowernet.project.entity.UdateUserLoginBean;

/**
 * Created by caishen on 2017/11/18.
 * by--
 */

public interface IUpdateLoginPwdView {

    void onError(String s);

    String getAPwd();
    String getBPwd();
    String getCPwd();

    void hideDialog();

    void sendCodeSuccess(UdateUserLoginBean sendCodeBean);

    void showDialog();

    void onSuccess(String message);
}
