package com.android.p2pflowernet.project.view.fragments.goods.comment;

import com.android.p2pflowernet.project.mvp.IPresenter;

/**
 * @描述: 商品评价逻辑处理类
 * @创建人：zhangpeisen
 * @创建时间：2017/10/18 上午10:18
 * @修改人：zhangpeisen
 * @修改时间：2017/10/18 上午10:18
 * @修改备注：
 * @throws
 */
public class IGoodsCommentPresenter extends IPresenter<IGoodsCommentlView> {

    private final GoodsCommentModel goodsCommentModel;

    @Override
    protected void cancel() {

        goodsCommentModel.cancel();
    }

    public IGoodsCommentPresenter() {

        goodsCommentModel = new GoodsCommentModel();
    }

    //获取商品评价数据
    public void getEveluate() {

    }
}
