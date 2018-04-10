package com.android.p2pflowernet.project.view.fragments.goods.comment;

import com.android.p2pflowernet.project.entity.EveluateBean;

/**
 * @描述: 商品评价视图类
 * @创建人：zhangpeisen
 * @创建时间：2017/10/18 上午10:19
 * @修改人：zhangpeisen
 * @修改时间：2017/10/18 上午10:19
 * @修改备注：
 * @throws
 */

public interface IGoodsCommentlView {


    String goodsId();

    int getPage();

    void hideDialog();

    void successEveluate(EveluateBean data);

    void onSuccess(String message);

    void onError(String message);

    void showDialog();
}
