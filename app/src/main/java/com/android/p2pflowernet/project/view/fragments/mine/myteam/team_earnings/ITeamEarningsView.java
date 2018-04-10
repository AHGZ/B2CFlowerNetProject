package com.android.p2pflowernet.project.view.fragments.mine.myteam.team_earnings;

import com.android.p2pflowernet.project.entity.MyTeamProfitBean;

/**
 * Created by caishen on 2017/11/28.
 * by--
 */

interface ITeamEarningsView {
    void onError(String s);

    void showDialog();

    void hideDialog();

    void successTeamProfit(MyTeamProfitBean data);
}
