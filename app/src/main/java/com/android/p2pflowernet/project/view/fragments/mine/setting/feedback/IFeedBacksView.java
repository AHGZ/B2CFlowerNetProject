package com.android.p2pflowernet.project.view.fragments.mine.setting.feedback;

/**
 * Created by caishen on 2017/11/24.
 * by--
 */

interface IFeedBacksView {
    void showDialog();

    void hideDialog();

    void onError(String message);

    String getType();

    String getContent();

    void onSuccess(String message);
}
