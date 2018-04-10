package com.android.p2pflowernet.project.o2omain.fragment.mine.take_out.all_order;

import android.util.Log;

import com.android.p2pflowernet.project.entity.AppTradeBean;
import com.android.p2pflowernet.project.entity.BanlanceBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.EncoreBean;
import com.android.p2pflowernet.project.entity.TakeOutBean;
import com.android.p2pflowernet.project.event.UserBanclanceBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.o2omain.fragment.mine.take_out.TakeOutModel;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.affirm.AffirmIndentModel;
import com.android.p2pflowernet.project.view.fragments.mine.orderflow.IOrderFlowModel;
import com.android.p2pflowernet.project.view.fragments.mine.wallet.bankcard.BankcardModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2018/1/5.
 * by--
 */

public class ITakeOutOrderPrenter extends IPresenter<ITakeOutOrderView> {
    private final TakeOutModel takeOutModel;
    private final IOrderFlowModel iOrderFlowModel;
    private final AffirmIndentModel affirmIndentModel;
    private final BankcardModel bankcardModel;

    @Override
    protected void cancel() {

        takeOutModel.cancel();
    }

    public ITakeOutOrderPrenter() {

        takeOutModel = new TakeOutModel();
        iOrderFlowModel = new IOrderFlowModel();
        affirmIndentModel = new AffirmIndentModel();
        bankcardModel = new BankcardModel();
    }

    //外卖订单列表数据
    public void getTakeOut() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String sate = getView().getSate();
        int page = getView().getPage();

        //加载进度条
        getView().showDialog();
        takeOutModel.getTakeOut(sate, page, new IModelImpl<ApiResponse<TakeOutBean>, TakeOutBean>() {
            @Override
            protected void onSuccess(TakeOutBean data, String message) {
                getView().hideDialog();
                getView().onSuccessData(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<TakeOutBean>> data, String message) {
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

    //支付宝支付
    public void AlipayPresenter() {
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

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

    //余额支付
    public void yEpay() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String count = getView().getOrderNum();
        String salePrice = getView().getSalePrice();
        String father_order = getView().is_father_order();
        String type = getView().getType();

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

    //取消订单
    public void cancelOrder(){
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String orderNumber = getView().getOrderNum();

        getView().showDialog();
        iOrderFlowModel.cancelOrder(orderNumber, new IModelImpl<ApiResponse<String>, String>() {
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
    public void againOneOrder( ){
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String orderNumber = getView().getOrderNum();

        getView().showDialog();
        takeOutModel.encore(orderNumber, new IModelImpl<ApiResponse<EncoreBean>, EncoreBean>() {
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
    public void cancelRefund(){
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String orderNumber = getView().getOrderNum();

        getView().showDialog();
        takeOutModel.cancelRefund(orderNumber, new IModelImpl<ApiResponse<String>, String>() {
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
    public void confirmGoods(){
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String order_id = getView().getOrderNum();

        getView().showDialog();
        takeOutModel.confirmGoods(order_id, new IModelImpl<ApiResponse<String>, String>() {
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
}
