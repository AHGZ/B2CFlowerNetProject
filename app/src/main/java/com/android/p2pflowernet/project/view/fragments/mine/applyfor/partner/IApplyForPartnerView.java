package com.android.p2pflowernet.project.view.fragments.mine.applyfor.partner;

import com.android.p2pflowernet.project.entity.IdEntityBean;

/**
 * Created by caishen on 2017/11/20.
 * by--
 */

public interface IApplyForPartnerView {

    String getIdCard();

    String getName();

    String getuserId();

    void onError(String s);

    void hideDialog();

    void setCheckIdentitySuccess(String data);

    String getIschecked();

    void setGetIdentitySuccess(IdEntityBean data);

    void showDialog();

    void onSuccess(String s);
}
