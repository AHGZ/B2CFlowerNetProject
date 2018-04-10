package com.android.p2pflowernet.project.o2omain.fragment.o2oorderlist;

import com.android.p2pflowernet.project.entity.O2oGoodsInfoBean;

/**
 * Created by caishen on 2018/1/23.
 * by--
 */

public interface IO2oOrderDetailView {
    void onError(String s);

    void showDialog();

    String getMerId();

    String getGoodId();

    int getpage();

    void hideDialog();

    void onSuccessData(O2oGoodsInfoBean data);
}
