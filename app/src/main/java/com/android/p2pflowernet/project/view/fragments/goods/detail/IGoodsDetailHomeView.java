package com.android.p2pflowernet.project.view.fragments.goods.detail;

import com.android.p2pflowernet.project.entity.OrderDetailBean;

/**
 * @描述: 商品详情主页面视图类
 * @创建人：zhangpeisen
 * @创建时间：2017/10/18 上午10:19
 * @修改人：zhangpeisen
 * @修改时间：2017/10/18 上午10:19
 * @修改备注：
 * @throws
 */

public interface IGoodsDetailHomeView {


    void onError(String s);

    String getSpecId();

    String getCount();

    String getSource();

    void hideDialog();

    void SuccessOrder(OrderDetailBean data);
}
