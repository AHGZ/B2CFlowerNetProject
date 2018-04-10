package com.android.p2pflowernet.project.view.fragments.goods.detail.huafan;

import com.android.p2pflowernet.project.entity.GoodsInfoBean;

/**
 * Created by caishen on 2018/1/18.
 * by--
 */

public interface IGoodsHuaFanView {
    void onError(String s);

    String goodsId();

    void showDialog();

    void hideDialog();

    void onSuccess(String message);

    void successGsInfo(GoodsInfoBean data);
}
