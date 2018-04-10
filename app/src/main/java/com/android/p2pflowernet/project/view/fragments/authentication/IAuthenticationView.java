package com.android.p2pflowernet.project.view.fragments.authentication;

/**
 * Created by zhangkun on 2018/3/12.
 */

public interface IAuthenticationView {

    void onError(String errorMsg);

    void onSuccess(String str);

    void showDialog();

    void hideDialog();

    //实名认证姓名
    String getUserName();

    //身份证号码
    String getIDNumber();

    //银行开户行
    String getBankName();

    //银行卡号
    String getBankNumber();
}
