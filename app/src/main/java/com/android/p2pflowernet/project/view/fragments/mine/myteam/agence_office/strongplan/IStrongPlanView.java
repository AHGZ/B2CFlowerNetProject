package com.android.p2pflowernet.project.view.fragments.mine.myteam.agence_office.strongplan;

import com.android.p2pflowernet.project.entity.AgentGrowBean;

/**
 * Created by caishen on 2017/11/30.
 * by--
 */

public interface IStrongPlanView {
    void onError(String s);

    void showDialog();

    void hideDialog();

    void SuccessAgent(AgentGrowBean data);
}
