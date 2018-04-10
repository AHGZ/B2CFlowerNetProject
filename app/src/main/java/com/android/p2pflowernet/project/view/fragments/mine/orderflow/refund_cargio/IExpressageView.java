package com.android.p2pflowernet.project.view.fragments.mine.orderflow.refund_cargio;

import com.android.p2pflowernet.project.entity.ExpresListBean;

/**
 * Created by caishen on 2017/11/18.
 * by--申请退货的的视图层
 */

public interface IExpressageView {
    void showDialog();

    void onError(String s);

    void hideDialog();

    void successExpress(ExpresListBean data);
}
