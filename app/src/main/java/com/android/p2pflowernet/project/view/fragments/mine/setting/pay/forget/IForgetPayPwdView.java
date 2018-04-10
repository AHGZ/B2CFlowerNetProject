package com.android.p2pflowernet.project.view.fragments.mine.setting.pay.forget;

import com.android.p2pflowernet.project.entity.SendCodeBean;
import com.android.p2pflowernet.project.entity.ShowPersonInfo;

/**
 * Created by caishen on 2017/12/7.
 * by--
 */

interface IForgetPayPwdView {

    void onError(String s);

    String getPhone();

    void showDialog();

    void hideDialog();

    void sendCodeSuccess(SendCodeBean sendCodeBean);

    void setShowPersonInfo(ShowPersonInfo data);
}
