package com.android.p2pflowernet.project.view.fragments.mine.wallet;

import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.IdEntityBean;
import com.android.p2pflowernet.project.entity.UserAcountBean;

/**
 * Created by caishen on 2017/11/11.
 * by--钱包模块的视图
 */

public interface IWalletView {
    void onError(String s);

    void showDialog();

    void hideDialog();

    void SuccessWallet(UserAcountBean data);

    void onSuccessCheck(CheckPwdBean data);

    void onSuccessed(String message);

    String getMoney();

    String getAlipayAccount();

    void onSuccessedWith(String message);

    void setGetIdentitySuccess(IdEntityBean data);
}
