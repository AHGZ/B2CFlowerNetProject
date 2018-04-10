package com.android.p2pflowernet.project.view.fragments.index.bigdata;

import com.android.p2pflowernet.project.entity.BigDataMapBean;

/**
 * Created by caishen on 2018/2/1.
 * by--
 */

public interface INationwideView {

    void onError(String s);

    void showDialog();

    void hideDialog();

    void onSuccess(BigDataMapBean data);
}
