package com.android.p2pflowernet.project.o2omain.fragment.o2oorderlist.o2oorderdetail;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.O2oGoodsInfoBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.o2omain.fragment.o2oorderlist.IO2oOrderDetailView;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;


public class IO2oOrderDetailPrenter extends IPresenter<IO2oOrderDetailView> {

    private final IO2oOrderDetaiModel io2oOrderDetaiModel;

    @Override
    protected void cancel() {

        io2oOrderDetaiModel.cancel();
    }

    public IO2oOrderDetailPrenter() {

        io2oOrderDetaiModel = new IO2oOrderDetaiModel();
    }

    //商品明细及商品评价
    public void get_goods_info() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String merId = getView().getMerId();
        if (TextUtils.isEmpty(merId)) {
            return;
        }
        String goodId = getView().getGoodId();
        if (TextUtils.isEmpty(goodId)) {
            return;
        }

        int page = getView().getpage();

        //加载进度条
        getView().showDialog();
        io2oOrderDetaiModel.get_goods_info(merId, goodId, page, new IModelImpl<ApiResponse<O2oGoodsInfoBean>, O2oGoodsInfoBean>() {
            @Override
            protected void onSuccess(O2oGoodsInfoBean data, String message) {
                getView().hideDialog();
                getView().onSuccessData(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<O2oGoodsInfoBean>> data, String message) {
                getView().hideDialog();
            }

            @Override
            protected void onFailure(String code, String message) {
                getView().hideDialog();
                getView().onError(message);
            }

            @Override
            protected void onSuccess() {
                getView().hideDialog();
            }
        });
    }

    //去结算
    public void goPay() {




    }
}
