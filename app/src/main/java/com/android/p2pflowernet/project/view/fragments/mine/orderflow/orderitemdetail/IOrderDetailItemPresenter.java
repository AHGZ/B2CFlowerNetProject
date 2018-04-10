package com.android.p2pflowernet.project.view.fragments.mine.orderflow.orderitemdetail;

import android.app.Activity;
import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.AppTradeBean;
import com.android.p2pflowernet.project.entity.BanlanceBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.OrderDetailItemBean;
import com.android.p2pflowernet.project.entity.UnionPayEntity;
import com.android.p2pflowernet.project.event.UserBanclanceBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.affirm.AffirmIndentModel;
import com.android.p2pflowernet.project.view.fragments.mine.orderflow.IOrderFlowModel;
import com.android.p2pflowernet.project.view.fragments.mine.wallet.bankcard.BankcardModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/11/10.
 * by--所有订单详情页的逻辑层
 */

public class IOrderDetailItemPresenter extends IPresenter<IOrderDetailItemView> {


    private final IOrderFlowModel iOrderFlowModel;
    private final AffirmIndentModel affirmIndentModel;
    private final BankcardModel bankcardModel;


    public IOrderDetailItemPresenter() {

        iOrderFlowModel = new IOrderFlowModel();
        affirmIndentModel = new AffirmIndentModel();
        bankcardModel = new BankcardModel();
    }

    //获取订单详情
    public void getOrderDetail() {

        String orderId = getView().getOrderId();
        if (TextUtils.isEmpty(orderId)) {
            return;
        }
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        getView().showDialog();

        iOrderFlowModel.orderflowdetail(orderId, new IModelImpl<ApiResponse<OrderDetailItemBean>, OrderDetailItemBean>() {
            @Override
            protected void onSuccess(OrderDetailItemBean data, String message) {

                getView().hideDialog();
                getView().SuccessDetail(data);

            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<OrderDetailItemBean>> data, String message) {
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

    ///取消订单
    public void cancleOrder() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String orderId = getView().getOrderId();
        int i = Integer.parseInt(orderId);

        getView().showDialog();
        //取消订单
        iOrderFlowModel.cancleOrder(i, new IModelImpl<ApiResponse<String>, String>() {
            @Override
            protected void onSuccess(String data, String message) {
                getView().hideDialog();
                getView().onSuccessCancleOrder(message);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<String>> data, String message) {
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

    //确认收货
    public void AffirmOrder() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String orderId = getView().getOrderId();
        int i = Integer.parseInt(orderId);

        getView().showDialog();
        //取消订单
        iOrderFlowModel.AffirmOrder(i, new IModelImpl<ApiResponse<String>, String>() {
            @Override
            protected void onSuccess(String data, String message) {
                getView().hideDialog();
                getView().onSuccessAffirm(message);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<String>> data, String message) {
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

    //获取余额
    public void getBanlce() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        getView().showDialog();
        //取消订单
        iOrderFlowModel.getBalance(new IModelImpl<ApiResponse<UserBanclanceBean>, UserBanclanceBean>() {
            @Override
            protected void onSuccess(UserBanclanceBean data, String message) {
                getView().hideDialog();
                getView().SuccessgetBanlance(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<UserBanclanceBean>> data, String message) {
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

    // 银联支付处理逻辑
    public void UnionPayPresenter() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }


        affirmIndentModel.UnionPay(new IModelImpl<ApiResponse<UnionPayEntity>, UnionPayEntity>() {
            @Override
            protected void onSuccess(UnionPayEntity unionPayResult, String message) {
                getView().hideDialog();
                getView().UnionPaySuccess(unionPayResult);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<UnionPayEntity>> data, String message) {

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

    // 微信支付处理逻辑
    public void WeChatPayPresenter(Activity activity) {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        affirmIndentModel.WeChatPay(activity);
    }

    // 支付宝支付处理逻辑
    public void AlipayPresenter() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String body = getView().getBody();
        String orderNum = getView().getOrderNum();
        String source = getView().getPaySource();
        String type = getView().getType();
        String father_order = getView().is_father_order();

        getView().showDialog();
        affirmIndentModel.aliPay(orderNum, type, father_order, source, "", new IModelImpl<ApiResponse<AppTradeBean>, AppTradeBean>() {
            @Override
            protected void onSuccess(AppTradeBean data, String message) {

                getView().hideDialog();
                getView().onSuccessAli(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<AppTradeBean>> data, String message) {

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

    //检测是否设置支付密码
    public void checkPayPwd() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        // 加载进度条
        getView().showDialog();
        bankcardModel.checkPwd(new IModelImpl<ApiResponse<CheckPwdBean>, CheckPwdBean>() {
            @Override
            protected void onSuccess(CheckPwdBean data, String message) {
                getView().hideDialog();
                getView().onSuccessCheck(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<CheckPwdBean>> data, String message) {
                getView().hideDialog();
                getView().onSuccessed(message);
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

    //余额支付接口
    public void yEpay() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String count = getView().getOrderNum();
        String salePrice = getView().getSalePrice();
        String body = getView().getBody();
        String father_order = getView().is_father_order();
        String type = getView().getType();//支付类型

        getView().showDialog();
        affirmIndentModel.yEPay(count, type, body, father_order, "", new IModelImpl<ApiResponse<BanlanceBean>, BanlanceBean>() {
            @Override
            protected void onSuccess(BanlanceBean data, String message) {
                getView().hideDialog();
                getView().onSuccessYe(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<BanlanceBean>> data, String message) {
                getView().hideDialog();

            }

            @Override
            protected void onFailure(String code, String message) {
                getView().hideDialog();
                getView().onPayYeError(message);
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
