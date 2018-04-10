package com.android.p2pflowernet.project.view.fragments.affirm.partnerapttitude;

import android.app.Activity;

import com.android.p2pflowernet.project.entity.AppTradeBean;
import com.android.p2pflowernet.project.entity.ApplyFroPayBean;
import com.android.p2pflowernet.project.entity.BanlanceBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
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
 * Created by caishen on 2017/11/9.
 * by--购买合伙人资质的逻辑层
 */

public class IBuyPartnerViewPrenter extends IPresenter<IBuyPartnerView> {

    private final IOrderFlowModel iOrderFlowModel;
    private AffirmIndentModel affirmIndentModel;
    private final BankcardModel bankcardModel;

    public IBuyPartnerViewPrenter() {

        affirmIndentModel = new AffirmIndentModel();
        bankcardModel = new BankcardModel();
        iOrderFlowModel = new IOrderFlowModel();
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
        String getnum = getView().getnum();

        getView().showDialog();
        affirmIndentModel.aliPay(orderNum, type, source, father_order, getnum, new IModelImpl<ApiResponse<AppTradeBean>, AppTradeBean>() {
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

    @Override
    protected void cancel() {

        affirmIndentModel.cancel();
        iOrderFlowModel.cancel();
        bankcardModel.cancel();
    }

    //检验是否设置过支付密码
    public void checkIsSetPayPwd() {

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
        String body = getView().getBody();
        String father_order = getView().is_father_order();
        String type = getView().getType();//支付类型
        String getnum = getView().getnum();//购买份数
        getView().showDialog();

        affirmIndentModel.yEPay(count, type, "", father_order, getnum,
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

    //获取用户余额
    public void getBalance() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        getView().showDialog();
        //获取用户余额
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

    //身份调起支付请求
    public void applyPay() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String type = getView().getType();
        String getnum = getView().getnum();

        getView().showDialog();
        //获取用户余额
        iOrderFlowModel.applyPay(type, getnum, new IModelImpl<ApiResponse<ApplyFroPayBean>, ApplyFroPayBean>() {
            @Override
            protected void onSuccess(ApplyFroPayBean data, String message) {
                getView().hideDialog();
                getView().onsuccessApplyFor(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<ApplyFroPayBean>> data, String message) {
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
}
