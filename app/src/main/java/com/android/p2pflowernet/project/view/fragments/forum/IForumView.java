package com.android.p2pflowernet.project.view.fragments.forum;

import com.android.p2pflowernet.project.entity.ForumChannelBean;

/**
 * Created by zhangkun on 2018/1/18.
 */

public interface IForumView {

    void onError(String string);

    void onSuccess(ForumChannelBean data);

    void showDialog();

    void hideDialog();
}
