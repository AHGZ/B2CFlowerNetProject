package com.android.p2pflowernet.project.view.fragments.mine.message;

import com.android.p2pflowernet.project.entity.MessagesBean;

/**
 * Created by caishen on 2017/11/4.
 * by--
 */

public interface IMessageView {


    void onError(String s);

    void showDialog();

    void hideDialog();

    void onSuccessMessages(MessagesBean data);
}
