package com.android.p2pflowernet.project.view.fragments.mine.myteam.agence_office;

import com.android.p2pflowernet.project.entity.AgentOfficeBean;
import com.android.p2pflowernet.project.entity.AutoWorkBean;
import com.android.p2pflowernet.project.entity.ShareCodeBean;

/**
 * Created by caishen on 2017/11/28.
 * by--
 */

interface IAgenceOfficeView {
    void onError(String s);

    void showDialog();

    void hideDialog();

    void Succesagent(AgentOfficeBean data);

    String getState();

    void onSuccess(String message);

    String getReccardId();

    void onSuccesstrial(String message);

    void onSuccessAutoWork(AutoWorkBean data);

    void onErrorAudit(String message);

    void onSuccessShare(ShareCodeBean data);
}
