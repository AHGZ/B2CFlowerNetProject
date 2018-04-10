package com.android.p2pflowernet.project.o2omain.fragment.index.groupbuying.shopdetails;

import com.android.p2pflowernet.project.entity.GroupBuyingBean;

/**
 * Created by heguozhong on 2018/1/4/004.
 * 团购商品详情视图层
 */

public interface IShopDetailsView{
    void onError(String errorMsg);

    void onSuccess(GroupBuyingBean groupBuyingBean);

    void onSuccess(String message);

    void showDialog();

    void hideDialog();
    // 收货地址的纬度
    String latitude();

    // 收货地址的经度
    String longitude();
}