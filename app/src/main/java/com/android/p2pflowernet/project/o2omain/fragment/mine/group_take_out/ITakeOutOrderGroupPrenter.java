package com.android.p2pflowernet.project.o2omain.fragment.mine.group_take_out;

import android.util.Log;

import com.android.p2pflowernet.project.entity.AppTradeBean;
import com.android.p2pflowernet.project.entity.BanlanceBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.TakeOutOrderGroupBean;
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
 * Created by caishen on 2018/1/6.
 * by--
 */

public class ITakeOutOrderGroupPrenter extends IPresenter<ITakeOutOrderGroupView>{

    private final ITakeOutOrderGroupModel iTakeOutOrderGroupModel;
    private final IOrderFlowModel iOrderFlowModel;
    private final AffirmIndentModel affirmIndentModel;
    private final BankcardModel bankcardModel;

    @Override
    protected void cancel() {

    }

    public ITakeOutOrderGroupPrenter() {

        iTakeOutOrderGroupModel = new ITakeOutOrderGroupModel();
        iOrderFlowModel = new IOrderFlowModel();
        affirmIndentModel = new AffirmIndentModel();
        bankcardModel = new BankcardModel();
    }


    //获取团购列表数据
    public void getTakeOutGroup() {


        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String state = getView().getstate();
        if (state.equals("")) {
            return;
        }

        int pages = getView().getpages();

        getView().showDialog();
        iTakeOutOrderGroupModel.getTakeOutGroup(state, pages, new IModelImpl<ApiResponse<TakeOutOrderGroupBean>, TakeOutOrderGroupBean>() {
            @Override
            protected void onSuccess(TakeOutOrderGroupBean data, String message) {
                getView().hideDialog();
                getView().onSuccessData(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<TakeOutOrderGroupBean>> data, String message) {
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

    //团购取消订单
    public void cancelGroupOrder(){
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String order_num = getView().getOrderNumber();

        getView().showDialog();
        iTakeOutOrderGroupModel.cancelGroupOrder(order_num, new IModelImpl<ApiResponse<String>, String>() {
            @Override
            protected void onSuccess(String data, String message) {
                getView().hideDialog();
                getView().onSuccessCancelGroupOrder(data);
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

    //团购退订单
    public void refundGroupOrder(){
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String order_num = getView().getOrderNumber();

        getView().showDialog();
        iTakeOutOrderGroupModel.refundGroupOrder(order_num, new IModelImpl<ApiResponse<String>, String>() {
            @Override
            protected void onSuccess(String data, String message) {
                getView().hideDialog();
                getView().onSuccessRefundGroupOrder(data);
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
}
