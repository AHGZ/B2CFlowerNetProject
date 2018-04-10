package com.android.p2pflowernet.project.view.fragments.mine.setting.feedback;

import com.android.p2pflowernet.project.entity.FeedBackBean;

/**
 * Created by caishen on 2017/11/24.
 * by--
 */

public interface IFeedBacksHistoryView {
    void onError(String message);

    void hideDialog();

    void showDialog();

    void onSuccessFeedBacks(FeedBackBean data);

    int getPage();
}
