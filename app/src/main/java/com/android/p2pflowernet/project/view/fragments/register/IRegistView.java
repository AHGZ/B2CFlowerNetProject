package com.android.p2pflowernet.project.view.fragments.register;

import com.android.p2pflowernet.project.entity.CheckMobileBean;
import com.android.p2pflowernet.project.entity.SendCodeBean;

/**
 * Created by caishen on 2017/10/16.
 * by--注册页面的视图层
 */

public interface IRegistView {


    String getPhone();

    void sendCheckMobileSuccess(CheckMobileBean checkMobileBean);

    void sendCodeSuccess(SendCodeBean sendCodeBean);

    void onError(String errorMsg);

    void showDialog();

    void hideDialog();
}
