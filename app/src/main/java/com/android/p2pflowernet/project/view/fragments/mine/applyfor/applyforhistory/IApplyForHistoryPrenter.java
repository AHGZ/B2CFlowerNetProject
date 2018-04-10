package com.android.p2pflowernet.project.view.fragments.mine.applyfor.applyforhistory;

import com.android.p2pflowernet.project.entity.ApplyForHistoryBean;
import com.android.p2pflowernet.project.entity.BankInfoBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.mine.wallet.bankcard.BankcardModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/11/14.
 * by--申请历史的逻辑层
 */

public class IApplyForHistoryPrenter extends IPresenter<IApplyForHistoryView> {

    private final IApplyForHistoryModel iApplyForHistoryModel;
    private final BankcardModel bankcardModel;

    @Override
    protected void cancel() {

        iApplyForHistoryModel.cancel();
    }

    public IApplyForHistoryPrenter() {

        iApplyForHistoryModel = new IApplyForHistoryModel();
        bankcardModel = new BankcardModel();
    }

    //申请历史
    public void applyHistory() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String userId = getView().getUserId();
        getView().showDialog();

        iApplyForHistoryModel.applyForHistory(userId, new IModelImpl<ApiResponse<ApplyForHistoryBean>, ApplyForHistoryBean>() {
            @Override
            protected void onSuccess(ApplyForHistoryBean data, String message) {
                getView().hideDialog();
                getView().setApplyForHistorySuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<ApplyForHistoryBean>> data, String message) {
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

    //退出代理资质
    public void exitAgent() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String agenceId = getView().getAgenceId();
        getView().showDialog();

        iApplyForHistoryModel.exitAgent(agenceId, new IModelImpl<ApiResponse<String>, String>() {

            @Override
            protected void onSuccess(String data, String message) {
                getView().hideDialog();
                getView().onSuccess(message);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<String>> data, String message) {
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

    //判断是否绑定过银行卡
    public void isbindCard() {


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
                getView().onCardSuccess(data, message);
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

    //检测过是否设置过支付密码
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
}
