package com.android.p2pflowernet.project.o2omain.fragment.mine.group_take_out.detail;

import com.android.p2pflowernet.project.entity.CouponCodeBean;

/**
 * Created by caishen on 2018/1/6.
 * by--
 */

interface ITakeOutGroupDetailView {

    void onError(String str);

    void showDialog();

    void hideDialog();

    String getOrderNumber();//获取订单号

    void onSuccess(CouponCodeBean s);
}
