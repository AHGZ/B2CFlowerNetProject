package com.android.p2pflowernet.project.view.fragments.mine.myteam.cloud_office;

import com.android.p2pflowernet.project.entity.CloudOfficeBean;
import com.android.p2pflowernet.project.entity.ShareCodeBean;

/**
 * Created by caishen on 2017/11/28.
 * by--
 */

interface ICloudOfficeView {
    void onError(String s);

    void showDialog();

    void hideDialog();

    void Successcloud(CloudOfficeBean data);

    void onSuccessShare(ShareCodeBean data);

    void onSuccess(String message);
}
