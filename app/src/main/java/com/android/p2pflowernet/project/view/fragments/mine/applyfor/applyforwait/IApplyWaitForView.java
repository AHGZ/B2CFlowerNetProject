package com.android.p2pflowernet.project.view.fragments.mine.applyfor.applyforwait;

import com.android.p2pflowernet.project.entity.ApplyForWaitBean;
import com.android.p2pflowernet.project.entity.BankInfoBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.IdEntityBean;

/**
 * Created by caishen on 2017/11/14.
 * by--待申请的视图逻辑
 */

public interface IApplyWaitForView {
    void onError(String s);

    void showDialog();

    String getUserId();

    void hideDialog();

    void setApplyForWaitSuccess(ApplyForWaitBean data);

    void onCardSuccess(BankInfoBean data, String message);

    void onSuccessCheck(CheckPwdBean data);

    void onSuccess(String message);

    void setGetIdentitySuccess(IdEntityBean data);
}
