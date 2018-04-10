package com.android.p2pflowernet.project.view.fragments.forum.videodetails;

import com.android.p2pflowernet.project.entity.ForumDetailsBean;

/**
 * Created by zhangkun on 2018/1/22.
 */

public interface IVideoDetailsView {

    void onError(String string);

    void onSuccess(ForumDetailsBean data);

    void showDialog();

    void hideDialog();

    String getCourseId();

    int getPage();
}
