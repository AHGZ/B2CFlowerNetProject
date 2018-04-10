package com.android.p2pflowernet.project.view.fragments.goods.detail;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.GoodsInfoBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.goods.info.GoodsInfoModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * @描述: 商品详情逻辑处理类
 * @创建人：zhangpeisen
 * @创建时间：2017/10/18 上午10:18
 * @修改人：zhangpeisen
 * @修改时间：2017/10/18 上午10:18
 * @修改备注：
 * @throws
 */
public class IGoodsDetailPresenter extends IPresenter<IGoodsDetailView> {
    private final GoodsInfoModel goodsInfoModel;

    @Override
    protected void cancel() {

        goodsInfoModel.cancel();
    }

    public IGoodsDetailPresenter() {

        goodsInfoModel = new GoodsInfoModel();

    }

    public void goodsXq() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String goodsId = getView().goodsId();
        if (TextUtils.isEmpty(goodsId)) {
            return;
        }
        getView().showDialog();
        goodsInfoModel.getGoodsXq(goodsId, new IModelImpl<ApiResponse<GoodsInfoBean>, GoodsInfoBean>() {
            @Override
            protected void onSuccess(GoodsInfoBean data, String message) {
                getView().hideDialog();
                getView().successGsInfo(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<GoodsInfoBean>> data, String message) {
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
