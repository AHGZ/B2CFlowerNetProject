package com.android.p2pflowernet.project.view.fragments.mine.setting;

import com.android.p2pflowernet.project.entity.IdEntityBean;
import com.android.p2pflowernet.project.entity.SettingBean;

/**
 * Created by caishen on 2017/11/4.
 * by--设置页面的视图层
 */

public interface ISettingView {


    void onError(String s);

    void showDialog();

    void hideDialog();

    void setSuccessSet(SettingBean data);

    void setGetIdentitySuccess(IdEntityBean data);
}
