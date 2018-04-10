package com.android.p2pflowernet.project.view.fragments.forum.member;

import com.android.p2pflowernet.project.entity.ForumListBean;

/**
 * Created by zhangkun on 2018/1/18.
 */

public interface IForumMemberView  {

    void onError(String string);

    void onSuccess(ForumListBean data);

    void showDialog();

    void hideDialog();

    String getCourseId();

    int getPage();
}
