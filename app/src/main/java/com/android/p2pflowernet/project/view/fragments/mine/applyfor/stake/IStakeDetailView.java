package com.android.p2pflowernet.project.view.fragments.mine.applyfor.stake;

import com.android.p2pflowernet.project.entity.StakeBean;

/**
 * Created by caishen on 2017/11/23.
 * by--
 */

interface IStakeDetailView {

    void onError(String s);

    void showDialog();

    void hideDialog();

    void setSuccessStake(StakeBean data);

    int getPage();
}
