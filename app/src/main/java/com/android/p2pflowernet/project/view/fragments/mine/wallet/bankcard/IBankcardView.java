package com.android.p2pflowernet.project.view.fragments.mine.wallet.bankcard;

import com.android.p2pflowernet.project.entity.BankInfoBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;

/**
 * Created by caishen on 2017/11/11.
 * by--银行卡视图
 */

public interface IBankcardView {
    void showDialog();

    void hideDialog();

    void onSuccess(BankInfoBean data, String message);

    void onError(String message);

    String getBankId();

    void onSuccessDel(String message);

    void onSuccessCheck(CheckPwdBean data);

    void onSuccessed(String message);
}
