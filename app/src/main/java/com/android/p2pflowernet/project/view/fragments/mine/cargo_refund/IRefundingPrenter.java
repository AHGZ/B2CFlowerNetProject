package com.android.p2pflowernet.project.view.fragments.mine.cargo_refund;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.RefundBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.mine.orderflow.IOrderFlowModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/11/17.
 * by--退换货的逻辑层
 */

public class IRefundingPrenter extends IPresenter<IRefundingView> {
    IOrderFlowModel iOrderFlowModel;

    public IRefundingPrenter() {
        iOrderFlowModel = new IOrderFlowModel();
    }

    public void refundrecordlists() {
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        int page = getView().getPage();
        if (page == 0) {
            return;
        }
        getView().showDialog();
        iOrderFlowModel.refundrecordlists(page, new IModelImpl<ApiResponse<RefundBean>, RefundBean>() {
            @Override
            protected void onSuccess(RefundBean data, String message) {
                getView().hideDialog();
                getView().onSuccess(message);
                getView().onSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<RefundBean>> data, String message) {
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

    /**
     * @throws
     * @描述:
     * @方法名: 取消退款货
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/18 下午5:32
     * @修改人 zhangpeisen
     * @修改时间 2017/12/18 下午5:32
     * @修改备注
     */
    public void cancelrefund() {
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String refundid = getView().getRefundid();
        if (TextUtils.isEmpty(refundid) && refundid.equals("")) {
            return;
        }
        getView().showDialog();
        iOrderFlowModel.cancelrefund(refundid, new IModelImpl<ApiResponse<String>, String>() {
            @Override
            protected void onSuccess(String data, String message) {
                getView().hideDialog();
                getView().onCancelSuccess(message);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<String>> data, String message) {
                getView().hideDialog();
                getView().onCancelSuccess(message);
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

    /**
     * @throws
     * @描述:
     * @方法名: 取消仲裁申请
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/18 下午5:32
     * @修改人 zhangpeisen
     * @修改时间 2017/12/18 下午5:32
     * @修改备注
     */
    public void cancelarbitr() {
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String refundid = getView().getRefundid();
        if (TextUtils.isEmpty(refundid) && refundid.equals("")) {
            return;
        }
        getView().showDialog();
        iOrderFlowModel.cancelarbitr(refundid, new IModelImpl<ApiResponse<String>, String>() {
            @Override
            protected void onSuccess(String data, String message) {
                getView().hideDialog();
                getView().onCancelSuccess(message);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<String>> data, String message) {
                getView().hideDialog();
                getView().onCancelSuccess(message);
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
