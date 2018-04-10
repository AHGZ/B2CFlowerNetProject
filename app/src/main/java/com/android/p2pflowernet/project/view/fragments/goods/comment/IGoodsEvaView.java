package com.android.p2pflowernet.project.view.fragments.goods.comment;

import com.android.p2pflowernet.project.entity.EveluateBean;

/**
 * Created by caishen on 2018/3/12.
 * by--
 */

interface IGoodsEvaView {
    void onError(String s);

    void showDialog();

    String goodsId();

    int getPage();

    void hideDialog();

    void successEveluate(EveluateBean data);

    void onSuccess(String message);

    int type();

}
