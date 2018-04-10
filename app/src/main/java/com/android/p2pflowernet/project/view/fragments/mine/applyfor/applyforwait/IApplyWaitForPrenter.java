package com.android.p2pflowernet.project.view.fragments.mine.applyfor.applyforwait;

import com.android.p2pflowernet.project.entity.ApplyForWaitBean;
import com.android.p2pflowernet.project.entity.BankInfoBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.IdEntityBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.partner.IApplyForPartnerModel;
import com.android.p2pflowernet.project.view.fragments.mine.wallet.bankcard.BankcardModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/11/14.
 * by--待申请的逻辑层
 */

public class IApplyWaitForPrenter extends IPresenter<IApplyWaitForView> {

    private final IApplyWaitForModel iApplyWaitForModel;
    private final BankcardModel bankcardModel;
    private final IApplyForPartnerModel iApplyForPartnerModel;

    @Override
    protected void cancel() {

        iApplyWaitForModel.cancel();
        bankcardModel.cancel();
        iApplyForPartnerModel.cancel();
    }

    public IApplyWaitForPrenter() {

        iApplyWaitForModel = new IApplyWaitForModel();
        bankcardModel = new BankcardModel();
        iApplyForPartnerModel = new IApplyForPartnerModel();
    }

    //待申请的状态值
    public void waitApplyFor() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String userId = getView().getUserId();
        getView().showDialog();

        iApplyWaitForModel.waitApplyFor(userId, new IModelImpl<ApiResponse<ApplyForWaitBean>, ApplyForWaitBean>() {
            @Override
            protected void onSuccess(ApplyForWaitBean data, String message) {
                getView().hideDialog();
                getView().setApplyForWaitSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<ApplyForWaitBean>> data, String message) {
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

    //检测是否设置过支付密码
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
