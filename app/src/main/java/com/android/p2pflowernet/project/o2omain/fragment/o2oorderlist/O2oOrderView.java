package com.android.p2pflowernet.project.o2omain.fragment.o2oorderlist;

import com.android.p2pflowernet.project.entity.GoPayBean;
import com.android.p2pflowernet.project.entity.O2oGoodsInfoBean;

/**
 * author: zhangpeisen
 * created on: 2017/7/25 上午10:21
 * description:O2O点餐列表
 */
public interface O2oOrderView {
    void onError(String s);

    String getMerId();

    String getGoodId();

    int getpage();

    void showDialog();

    void hideDialog();

    void onSuccessData(O2oGoodsInfoBean data);

    String merchId();

    String longitude();

    String latitude();

    void onSuccessGoPay(GoPayBean data);
}
