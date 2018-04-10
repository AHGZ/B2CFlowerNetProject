package com.android.p2pflowernet.project.view.fragments.goods.detail;

import com.android.p2pflowernet.project.entity.GoodsInfoBean;

/**
 * @描述: 商品详情视图类
 * @创建人：zhangpeisen
 * @创建时间：2017/10/18 上午10:19
 * @修改人：zhangpeisen
 * @修改时间：2017/10/18 上午10:19
 * @修改备注：
 * @throws
 */

public interface IGoodsDetailView {


    void showDialog();

    void hideDialog();

    void successGsInfo(GoodsInfoBean data);

    void onSuccess(String message);

    void onError(String message);

    String goodsId();
}
