package com.android.p2pflowernet.project.o2omain.fragment.pay;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.AppTradeBean;
import com.android.p2pflowernet.project.entity.BanlanceBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.MerchXqBean;
import com.android.p2pflowernet.project.entity.ScanOrderBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.affirm.AffirmIndentModel;
import com.android.p2pflowernet.project.view.fragments.mine.wallet.bankcard.BankcardModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/12/29.
 * by--
 */

public class IAffirmPayPrenter extends IPresenter<IAffirmPayView> {

    private final AffirmPayModel affirmPayModel;
    private final AffirmIndentModel affirmIndentModel;
    private final BankcardModel bankcardModel;

    @Override
    protected void cancel() {

        affirmPayModel.cancel();
    }

    public IAffirmPayPrenter() {

        affirmPayModel = new AffirmPayModel();
        affirmIndentModel = new AffirmIndentModel();
        bankcardModel = new BankcardModel();
    }

    //获取店铺信息
    public void getMerceInfo() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String merch_id = getView().merch_id();
        if (TextUtils.isEmpty(merch_id)) {
            return;
        }

        getView().showDialog();

        affirmPayModel.getMerceInfo(merch_id, new IModelImpl<ApiResponse<MerchXqBean>, MerchXqBean>() {
            @Override
            protected void onSuccess(MerchXqBean data, String message) {
                getView().hideDialog();
                getView().successData(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<MerchXqBean>> data, String message) {
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
                getView().hideDialog();
            }
        });
    }

    //线下扫码下单
    public void payscanOrder() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String merch_id = getView().merch_id();
        if (TextUtils.isEmpty(merch_id)) {
            return;
        }

        String money = getView().getMoney();
        if (TextUtils.isEmpty(money) || money.equals("")) {
            getView().onError("请输入支付金额");
            return;
        }

        getView().showDialog();

        affirmPayModel.payscanOrder(merch_id, money, new IModelImpl<ApiResponse<ScanOrderBean>, ScanOrderBean>() {
            @Override
            protected void onSuccess(ScanOrderBean data, String message) {
                getView().hideDialog();
                getView().successCommit(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<ScanOrderBean>> data, String message) {
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
                getView().hideDialog();
            }
        });
    }

    //支付宝，银联请求支付签名
    public void AlipayPresenter() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String orderNum = getView().getOrderNum();
        if (TextUtils.isEmpty(orderNum)) {
            return;
        }

        String source = getView().getPaySource();
        if (TextUtils.isEmpty(source)) {
            return;
        }

        String type = getView().getType();
        if (TextUtils.isEmpty(type)) {
            return;
        }

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

    //检验是否设置过支付密码
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
        String father_order = getView().is_father_order();
        String type = getView().getType();//支付类型

        getView().showDialog();
        affirmIndentModel.yEPay(count, type, "", father_order, "", new IModelImpl<ApiResponse<BanlanceBean>, BanlanceBean>() {
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
}
