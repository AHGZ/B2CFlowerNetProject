package com.android.p2pflowernet.project.view.fragments.mine.setting.pay;

import com.android.p2pflowernet.project.entity.CheckPwdBean;

/**
 * Created by caishen on 2017/12/7.
 * by--
 */

public interface ISettingPayView{
    void onError(String s);

    void showDialog();

    void hideDialog();

    void onSuccessCheck(CheckPwdBean data);

    void onSuccessed(String message);

}
