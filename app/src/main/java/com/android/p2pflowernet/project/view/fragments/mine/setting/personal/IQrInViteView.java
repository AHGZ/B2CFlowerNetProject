package com.android.p2pflowernet.project.view.fragments.mine.setting.personal;

import com.android.p2pflowernet.project.entity.ShowPersonInfo;

/**
 * Created by caishen on 2017/11/15.
 * by--二维码邀请视图层
 */

public interface IQrInViteView {


    void onError(String s);

    void showDialog();

    void hideDialog();

    void setShowPersonInfo(ShowPersonInfo data);
}
