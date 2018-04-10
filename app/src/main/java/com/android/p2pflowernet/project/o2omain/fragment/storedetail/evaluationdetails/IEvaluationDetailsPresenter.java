package com.android.p2pflowernet.project.o2omain.fragment.storedetail.evaluationdetails;

import com.android.p2pflowernet.project.entity.ShopEvaluationBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.o2omain.fragment.O2oModel;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by heguozhong on 2018/1/5/005.
 * 评论详情逻辑层
 */

public class IEvaluationDetailsPresenter extends IPresenter<IEvaluationDetailsView> {
    private final O2oModel o2oModel;

    public IEvaluationDetailsPresenter() {
        o2oModel = new O2oModel();
    }

    //获取店铺商家信息
    public void getShopEvaluation(String merch_id,int page) {
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        getView().showDialog();
        o2oModel.o2oevellist(merch_id,page, new IModelImpl<ApiResponse<ShopEvaluationBean>, ShopEvaluationBean>() {
            @Override
            protected void onSuccess(ShopEvaluationBean data, String message) {
                getView().hideDialog();
                getView().onSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<ShopEvaluationBean>> data, String message) {
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

    @Override
    protected void cancel() {
        o2oModel.cancel();
    }
}
