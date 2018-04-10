package com.android.p2pflowernet.project.view.fragments.mine.message.msgdetail;

import com.android.p2pflowernet.project.entity.MessaTypeBean;

/**
 * Created by caishen on 2017/11/8.
 * by--
 */

public interface IMessageDetailView {


    String getType();

    int getPage();

    void onError(String s);

    void showDialog();

    void hideDialog();

    void onsuccessType(MessaTypeBean data);

    String getreceiver();
}
