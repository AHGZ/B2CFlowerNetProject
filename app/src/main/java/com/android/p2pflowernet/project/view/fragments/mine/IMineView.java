package com.android.p2pflowernet.project.view.fragments.mine;

import com.android.p2pflowernet.project.entity.BankInfoBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.MineMyBean;
import com.android.p2pflowernet.project.entity.ShareCodeBean;

/**
 * Created by caishen on 2017/10/16.
 * by--我的视图层
 */

public interface IMineView {

    void setMineMyInfo(MineMyBean mineMyBean);

    void showDialog();

    void hideDialog();

    void onError(String s);

    void onCardSuccess(BankInfoBean data, String message);

    void onSuccess(String message);

    void onSuccessCheck(CheckPwdBean data);

    void onSuccessShare(ShareCodeBean data);
}
