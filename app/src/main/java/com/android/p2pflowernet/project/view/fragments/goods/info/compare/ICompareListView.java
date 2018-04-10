package com.android.p2pflowernet.project.view.fragments.goods.info.compare;

import com.android.p2pflowernet.project.entity.CompareListBean;

/**
 * Created by caishen on 2018/1/29.
 * by--
 */

public interface ICompareListView {
    void onError(String s);

    String getSpecId();

    void showDialog();

    void hideDialog();

    void successGsInfo(CompareListBean data);

    void onSuccess(String message);
}
