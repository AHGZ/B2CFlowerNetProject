package com.android.p2pflowernet.project.view.fragments.goods.detail.huafan;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.GoodsInfoBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.goods.info.GoodsInfoModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2018/1/18.
 * by--
 */

public class IGoodsHuaFanPrenter extends IPresenter<IGoodsHuaFanView> {

    private final GoodsInfoModel goodsInfoModel;

    @Override
    protected void cancel() {

        goodsInfoModel.cancel();
    }

    public IGoodsHuaFanPrenter() {

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
