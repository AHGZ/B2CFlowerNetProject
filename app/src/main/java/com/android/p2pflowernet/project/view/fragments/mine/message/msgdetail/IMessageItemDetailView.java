package com.android.p2pflowernet.project.view.fragments.mine.message.msgdetail;

import com.android.p2pflowernet.project.entity.MessaDetailBean;

/**
 * Created by caishen on 2017/12/28.
 * by--
 */

public interface IMessageItemDetailView {
    String getType();

    String getnoticeId();

    void onError(String s);

    void showDialog();

    void hideDialog();

    void onsuccessMessageDe(MessaDetailBean data);

    String getreceiver();
}
