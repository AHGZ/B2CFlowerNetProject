package com.android.p2pflowernet.project.o2omain.fragment.mine;

import com.android.p2pflowernet.project.entity.MineMyBean;
import com.android.p2pflowernet.project.entity.ShareCodeBean;

/**
 * Created by caishen on 2018/1/2.
 * by--
 */

public interface IO2oMineView {

    void onError(String s);

    void showDialog();

    void hideDialog();

    void setMineMyInfo(MineMyBean data);

    void onSuccessShare(ShareCodeBean data);

    void onSuccess(String message);
}
