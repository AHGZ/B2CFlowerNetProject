package com.android.p2pflowernet.project.view.fragments.goods.comment;

import com.android.p2pflowernet.project.entity.EveluateBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2018/3/12.
 * by--
 */

public class IGoodsEvaPrenter extends IPresenter<IGoodsEvaView> {

    private final GoodsCommentModel goodsCommentModel;

    @Override
    protected void cancel() {
        goodsCommentModel.cancel();
    }

    public IGoodsEvaPrenter() {

        goodsCommentModel = new GoodsCommentModel();
    }

    //获取商品评价数据
    public void getEveluate() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        getView().showDialog();
        String goodsId = getView().goodsId();
        int type = getView().type();
        int page = getView().getPage();
        goodsCommentModel.getEveluate(goodsId, page,type, new IModelImpl<ApiResponse<EveluateBean>, EveluateBean>() {
            @Override
            protected void onSuccess(EveluateBean data, String message) {
                getView().hideDialog();
                getView().successEveluate(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<EveluateBean>> data, String message) {
                getView().hideDialog();
                getView().onSuccess(message);
            }

            @Override
            protected void onFailure(String code, String message) {
                getView().hideDialog();
                getView().onError(message);
            }

            @Override
            protected void onSuccess() {

            }
        });
    }
}
