package com.android.p2pflowernet.project.view.fragments.mine.myteam.team_earnings.contribution;

import com.android.p2pflowernet.project.entity.ContriRankBean;

/**
 * Created by caishen on 2017/12/19.
 * by--
 */

public interface IContributionRankView {
    String getState();

    void onError(String s);

    void showDialog();

    void hideDialog();

    void successRanks(ContriRankBean data);
}
