package com.android.p2pflowernet.project.view.fragments.updatepwd;

import com.android.p2pflowernet.project.entity.CheckMobileBean;
import com.android.p2pflowernet.project.entity.SendCodeBean;
import com.android.p2pflowernet.project.entity.UpdatePwd;
import com.android.p2pflowernet.project.mvp.IView;

/**
 * Created by caishen on 2017/10/18.
 * by--
 */

interface IUpdatePwdView extends IView<IUpdatePwdPresenter> {

    /**
     * 获取新密码
     */
    String getPwd();

    /**
     * 获取确认密码
     * @return
     */
    String getaffirmPwd();

    void onError(String errorMsg);

    void onLoginSuccess(UpdatePwd info);

    void onSendCodeSuccess(String message);

    void showDialog();

    void hideDialog();

    String getPhone();

    void sendCheckMobileSuccess(CheckMobileBean checkMobileBean);

    void sendCodeSuccess(SendCodeBean sendCodeBean);

    String getSendCode();
}
