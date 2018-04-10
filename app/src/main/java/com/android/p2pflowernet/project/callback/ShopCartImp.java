package com.android.p2pflowernet.project.callback;

import com.android.p2pflowernet.project.entity.ShopCart;

/**
 * @描述:购物车增删操作
 * @创建人：zhangpeisen
 * @创建时间：2018/1/10 下午6:00
 * @修改人：zhangpeisen
 * @修改时间：2018/1/10 下午6:00
 * @修改备注：
 * @throws
 */
public interface ShopCartImp {
    void add(ShopCart shopCart, int postion);

    void remove(ShopCart shopCart, int postion);
}
