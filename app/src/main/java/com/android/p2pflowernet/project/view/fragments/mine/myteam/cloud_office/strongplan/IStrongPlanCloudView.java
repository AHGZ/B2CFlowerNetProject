package com.android.p2pflowernet.project.view.fragments.mine.myteam.cloud_office.strongplan;

import com.android.p2pflowernet.project.entity.AgentGrowBean;
import com.android.p2pflowernet.project.entity.CloudGrowBean;

/**
 * Created by caishen on 2017/11/30.
 * by--
 */

public interface IStrongPlanCloudView {
    void onError(String s);

    void showDialog();

    void hideDialog();

    void SuccessCloud(CloudGrowBean data);
}
