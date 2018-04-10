package com.android.p2pflowernet.project.view.fragments.mine.setting.login;

import com.android.p2pflowernet.project.entity.ChangePhoneBean;
import com.android.p2pflowernet.project.entity.SendCodeBean;

/**
 * Created by caishen on 2017/11/18.
 * by--
 */

public interface IChangePhoneView {
    void onError(String s);

    String getPhone();

    void showDialog();

    void hideDialog();

    void sendCodeSuccess(SendCodeBean sendCodeBean);

    void changePhoneSuccess(ChangePhoneBean changePhoneBean);

    String getCode();

    void onSuccess(String message);
}
