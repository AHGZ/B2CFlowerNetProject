package com.android.p2pflowernet.project.view.fragments.mine.applyfor.applyforhistory;

import com.android.p2pflowernet.project.entity.ApplyForHistoryBean;
import com.android.p2pflowernet.project.entity.BankInfoBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;

/**
 * Created by caishen on 2017/11/14.
 * by--申请历史的视图层
 */

public interface IApplyForHistoryView {
    void onError(String s);

    String getUserId();

    void showDialog();

    void hideDialog();

    void setApplyForHistorySuccess(ApplyForHistoryBean data);

    String getAgenceId();

    void onSuccess(String message);

    void onCardSuccess(BankInfoBean data, String message);

    void onSuccessCheck(CheckPwdBean data);

    void onSuccessed(String message);
}
