package com.android.p2pflowernet.project.view.fragments.mine.cargo_refund;

import android.text.TextUtils;

import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.mine.orderflow.IOrderFlowModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/11/20.
 * by--申请仲裁的逻辑层
 */

public class IApplyForArbitrationPrenter extends IPresenter<IApplyForArbitrationView> {
    IOrderFlowModel iOrderFlowModel;

    public IApplyForArbitrationPrenter() {
        iOrderFlowModel = new IOrderFlowModel();
    }

    public void applyarbit() {
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String refundId = getView().getRefundId();
        String content = getView().getContent();
        if (refundId.equals("") && TextUtils.isEmpty(refundId)) {
            return;
        }
        if (content.equals("") && TextUtils.isEmpty(content)) {
            return;
        }
        getView().showDialog();
        iOrderFlowModel.applyarbit(refundId, content, new IModelImpl<ApiResponse<String>, String>() {
            @Override
            protected void onSuccess(String data, String message) {
                getView().hideDialog();
                getView().onSuccess(message);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<String>> data, String message) {
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
        iOrderFlowModel.cancel();
    }
}
