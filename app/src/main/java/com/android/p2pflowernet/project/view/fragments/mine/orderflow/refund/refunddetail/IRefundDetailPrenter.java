package com.android.p2pflowernet.project.view.fragments.mine.orderflow.refund.refunddetail;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.RefundDetailBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.mine.orderflow.IOrderFlowModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * @描述:各种订单详情逻辑层
 * @创建人：zhangpeisen
 * @创建时间：2017/12/16 上午9:32
 * @修改人：zhangpeisen
 * @修改时间：2017/12/16 上午9:32
 * @修改备注：
 * @throws
 */
public class IRefundDetailPrenter extends IPresenter<IRefundDetailView> {
    IOrderFlowModel iOrderFlowModel;

    public IRefundDetailPrenter() {
        iOrderFlowModel = new IOrderFlowModel();
    }

    public void refundrecorddetail() {
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String refundid = getView().getRefundid();
        if (TextUtils.isEmpty(refundid) && refundid.equals("")) {
            return;
        }
        getView().showDialog();
        iOrderFlowModel.refundrecorddetail(refundid, new IModelImpl<ApiResponse<RefundDetailBean>, RefundDetailBean>() {
            @Override
            protected void onSuccess(RefundDetailBean data, String message) {
                getView().hideDialog();
                getView().onSuccess(message);
                getView().onSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<RefundDetailBean>> data, String message) {
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
