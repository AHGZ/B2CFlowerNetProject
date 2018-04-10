package com.android.p2pflowernet.project.o2omain.fragment.storedetail.shop;

import com.android.p2pflowernet.project.entity.MerchantBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.o2omain.fragment.O2oModel;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by heguozhong on 2018/1/12/012.
 * 店铺商家逻辑层
 */

public class IShopPresenter extends IPresenter<IShopView> {

    private final O2oModel o2oModel;

    @Override
    protected void cancel() {
        o2oModel.cancel();
    }

    public IShopPresenter() {
        o2oModel = new O2oModel();
    }

    public void getShopMerchantInfo(String merch_id) {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        getView().showDialog();
        o2oModel.o2omerchinfo(merch_id, new IModelImpl<ApiResponse<MerchantBean>, MerchantBean>() {
            @Override
            protected void onSuccess(MerchantBean data, String message) {
                getView().hideDialog();
                getView().onSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<MerchantBean>> data, String message) {
                getView().hideDialog();
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
