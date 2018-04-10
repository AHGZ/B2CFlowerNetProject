package com.android.p2pflowernet.project.o2omain.fragment.storedetail.shop;

import com.android.p2pflowernet.project.entity.MerchantBean;

/**
 * Created by heguozhong on 2018/1/12/012.
 * 店铺商家视图层
 */

public interface IShopView {

    void onError(String s);

    void showDialog();

    void hideDialog();

    void onSuccess(MerchantBean data);
}
