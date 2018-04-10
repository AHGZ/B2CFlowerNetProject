package com.android.p2pflowernet.project.view.fragments.mine.wallet.bankcard;

import com.android.p2pflowernet.project.entity.BankInfoBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/11/11.
 * by--银行卡逻辑层
 */

public class IBankcardPrenter extends IPresenter<IBankcardView> {

    private final BankcardModel bankcardModel;

    @Override
    protected void cancel() {

        bankcardModel.cancel();
    }

    public IBankcardPrenter() {

        bankcardModel = new BankcardModel();
    }

    //获取银行卡信息
    public void getBankInfo() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        // 加载进度条
        getView().showDialog();

        bankcardModel.bancardlist(new IModelImpl<ApiResponse<BankInfoBean>, BankInfoBean>() {
            @Override
            protected void onSuccess(BankInfoBean data, String message) {

                getView().hideDialog();
                getView().onSuccess(data, message);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<BankInfoBean>> data, String message) {
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

    //解绑银行卡
    public void delBnak() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        // 加载进度条
        getView().showDialog();
        String bankId = getView().getBankId();

        bankcardModel.delBank(bankId, new IModelImpl<ApiResponse<String>, String>() {
            @Override
            protected void onSuccess(String data, String message) {

                getView().hideDialog();
                getView().onSuccessDel(message);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<String>> data, String message) {

                getView().hideDialog();
                getView().onSuccessDel(message);
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

    //检测用户是否设置过支付密码
    public void checkPwd() {

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
