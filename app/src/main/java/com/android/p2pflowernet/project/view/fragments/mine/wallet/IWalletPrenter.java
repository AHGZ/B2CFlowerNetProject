package com.android.p2pflowernet.project.view.fragments.mine.wallet;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.IdEntityBean;
import com.android.p2pflowernet.project.entity.UserAcountBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.partner.IApplyForPartnerModel;
import com.android.p2pflowernet.project.view.fragments.mine.wallet.bankcard.BankcardModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/11/11.
 * by--钱包模块逻辑层
 */

public class IWalletPrenter extends IPresenter<IWalletView> {

    private final IWalletModel iWalletModel;
    private final BankcardModel bankcardModel;
    private final IApplyForPartnerModel iApplyForPartnerModel;


    public IWalletPrenter() {

        iWalletModel = new IWalletModel();
        bankcardModel = new BankcardModel();
        iApplyForPartnerModel = new IApplyForPartnerModel();
    }

    //获取我的钱包数据
    public void getUserAccount() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        getView().showDialog();
        iWalletModel.getUserAccount(new IModelImpl<ApiResponse<UserAcountBean>, UserAcountBean>() {
            @Override
            protected void onSuccess(UserAcountBean data, String message) {
                getView().hideDialog();
                getView().SuccessWallet(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<UserAcountBean>> data, String message) {
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

    @Override
    protected void cancel() {

        iWalletModel.cancel();
        bankcardModel.cancel();
        iApplyForPartnerModel.cancel();
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
                getView().hideDialog();
            }
        });
    }

    //支付宝提现
    public void withdraw() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String money = getView().getMoney();
        if (TextUtils.isEmpty(money)) {
            getView().onError("请输入提现金额");
            return;
        }

        String alipayAccount = getView().getAlipayAccount();
        if (TextUtils.isEmpty(alipayAccount)) {
            getView().onError("请输入支付宝账号");
            return;
        }

        // 加载进度条
        getView().showDialog();
        bankcardModel.withdraw(money, alipayAccount, new IModelImpl<ApiResponse<String>, String>() {
            @Override
            protected void onSuccess(String data, String message) {
                getView().hideDialog();
                getView().onSuccessedWith(message);
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
                getView().hideDialog();
            }
        });
    }

    //检测是否实名认证过
    public void checkIdentity() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        getView().showDialog();
        iApplyForPartnerModel.checkIdentity("", new IModelImpl<ApiResponse<IdEntityBean>, IdEntityBean>() {
            @Override
            protected void onSuccess(IdEntityBean data, String message) {
                getView().hideDialog();
                getView().setGetIdentitySuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<IdEntityBean>> data, String message) {
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
