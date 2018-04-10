package com.android.p2pflowernet.project.view.fragments.trade;

import com.android.p2pflowernet.project.entity.GoodsInventoryCount;
import com.android.p2pflowernet.project.entity.OrderDetailBean;
import com.android.p2pflowernet.project.entity.ShopCarBean;

/**
 * author: zhangpeisen
 * created on: 2017/7/25 上午10:21
 * description:
 */
public interface ITradeView {

    void onError(String s);

    void hideDialog();

    void showDialog();

    void SuccessShopCar(ShopCarBean data);

    String getCartId();

    String getGoodId();

    String getSpecId();

    int getNum();

    void SuccessEditInventory(GoodsInventoryCount data);

    String getIsChoose();

    void onSuccess(String message);

    void onSuccessSet(String message);

    String getSource();

    void SuccessOrder(OrderDetailBean data);

    String getSelect();
}
