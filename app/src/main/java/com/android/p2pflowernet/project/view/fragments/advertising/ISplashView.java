package com.android.p2pflowernet.project.view.fragments.advertising;

import com.android.p2pflowernet.project.entity.SplashBean;

/**
 * Created by caishen on 2018/2/1.
 * by--
 */

interface ISplashView {
    void onError(String s);

    void hideDialog();

    void successData(SplashBean data);
}
