package com.android.p2pflowernet.project.o2omain.fragment.mine.take_out.takefulldetail;

import android.util.Log;

import com.android.p2pflowernet.project.entity.AppTradeBean;
import com.android.p2pflowernet.project.entity.BanlanceBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.EncoreBean;
import com.android.p2pflowernet.project.entity.OrderDetailsBean;
import com.android.p2pflowernet.project.event.UserBanclanceBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.o2omain.fragment.O2oModel;
import com.android.p2pflowernet.project.o2omain.fragment.mine.take_out.TakeOutModel;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.affirm.AffirmIndentModel;
import com.android.p2pflowernet.project.view.fragments.mine.orderflow.IOrderFlowModel;
import com.android.p2pflowernet.project.view.fragments.mine.wallet.bankcard.BankcardModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by heguozhong on 2018/1/15/015.
 * 外卖明细逻辑层
 */

public class ITakeFullDetailPresenter extends IPresenter<ITakeFullDetailView>{
    private final O2oModel o2oModel;
    private final TakeOutModel takeOutModel;
    private final IOrderFlowModel iOrderFlowModel;
    private final AffirmIndentModel affirmIndentModel;
    private final BankcardModel bankcardModel;

    public ITakeFullDetailPresenter() {
        o2oModel = new O2oModel();
        takeOutModel = new TakeOutModel();
        iOrderFlowModel = new IOrderFlowModel();
        affirmIndentModel = new AffirmIndentModel();
        bankcardModel = new BankcardModel();
    }

    //查看外卖详情方法
    public void o2oorderdetail(String order_num) {
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        getView().showDialog();
        o2oModel.o2oorderdetail(order_num,new IModelImpl<ApiResponse<OrderDetailsBean>, OrderDetailsBean>() {
            @Override
            protected void onSuccess(OrderDetailsBean data, String message) {
                getView().hideDialog();
                getView().onSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<OrderDetailsBean>> data, String message) {
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

    //查看外卖退款详情方法
    public void getRefundOrder(String order_num,String refund_id) {
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        getView().showDialog();
        o2oModel.getRefundOrder(order_num,refund_id,new IModelImpl<ApiResponse<OrderDetailsBean>, OrderDetailsBean>() {
            @Override
            protected void onSuccess(OrderDetailsBean data, String message) {
                getView().hideDialog();
                getView().onSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<OrderDetailsBean>> data, String message) {
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

    //获取余额
    public void getBanlce() {


        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        getView().showDialog();

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

    //余额支付
    public void yEpay() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String count = getView().getOrderNumber();
        String salePrice = getView().getSalePrice();
        String father_order = getView().is_father_order();
        String type = getView().getType();
        Log.e("=========", count);
        Log.e("=========", salePrice);
        Log.e("=========", father_order);
        Log.e("=========", type);
        getView().showDialog();
        affirmIndentModel.yEPay(count, type, "", father_order, "",
                new IModelImpl<ApiResponse<BanlanceBean>, BanlanceBean>() {
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

    //支付宝支付
    public void AlipayPresenter() {
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String orderNum = getView().getOrderNumber();
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

    //检测支付密码
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
    //取消订单
    public void cancelOrder(String order_num){
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        getView().showDialog();
        iOrderFlowModel.cancelOrder(order_num, new IModelImpl<ApiResponse<String>, String>() {
            @Override
            protected void onSuccess(String data, String message) {
                getView().hideDialog();
                getView().onSuccessCancelOrder(data);
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

    //再来一单
    public void againOneOrder(String order_num ){
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }


        getView().showDialog();
        takeOutModel.encore(order_num, new IModelImpl<ApiResponse<EncoreBean>, EncoreBean>() {
            @Override
            protected void onSuccess(EncoreBean data, String message) {
                getView().hideDialog();
                getView().onSuccessEncore(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<EncoreBean>> data, String message) {
                getView().hideDialog();
            }

            @Override
            protected void onFailure(String code, String message) {
                getView().hideDialog();
                getView().onError(message);
                Log.e("takeOutModel", message);
            }

            @Override
            protected void onSuccess() {

            }
        });
    }

    //取消退款
    public void cancelRefund(String order_num ){
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        getView().showDialog();
        takeOutModel.cancelRefund(order_num, new IModelImpl<ApiResponse<String>, String>() {
            @Override
            protected void onSuccess(String data, String message) {
                getView().hideDialog();
                getView().onSuccessCancelRefund(message);
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
    public void confirmGoods(String order_num ){
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        getView().showDialog();
        takeOutModel.confirmGoods(order_num, new IModelImpl<ApiResponse<String>, String>() {
            @Override
            protected void onSuccess(String data, String message) {
                getView().hideDialog();
                getView().onSuccessConfirmGoods(data);
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

    @Override
    protected void cancel() {
        o2oModel.cancel();
    }
}
