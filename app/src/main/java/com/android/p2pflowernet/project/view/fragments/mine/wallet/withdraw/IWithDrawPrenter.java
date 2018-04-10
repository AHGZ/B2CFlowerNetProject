package com.android.p2pflowernet.project.view.fragments.mine.wallet.withdraw;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.BankInfoBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.WithDrawInfoBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.checkmobile.ICheckMobileModel;
import com.android.p2pflowernet.project.view.fragments.mine.wallet.IWalletModel;
import com.android.p2pflowernet.project.view.fragments.mine.wallet.bankcard.BankcardModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * @描述:提现模块逻辑层
 * @创建人：zhangpeisen
 * @创建时间：2017/11/20 下午7:06
 * @修改人：zhangpeisen
 * @修改时间：2017/11/20 下午7:06
 * @修改备注：
 * @throws
 */
public class IWithDrawPrenter extends IPresenter<IWithDrawView> {
    private IWalletModel iWalletModel;
    private ICheckMobileModel iCheckMobileModel;
    private BankcardModel bankcardModel;
    public IWithDrawPrenter() {

        iWalletModel = new IWalletModel();
        iCheckMobileModel = new ICheckMobileModel();
        bankcardModel = new BankcardModel();

    }

    /**
     * @描述: 检测是否设置支付密码
     * @创建人：zhangpeisen
     * @创建时间：2017/12/23 下午2:08
     * @修改人：zhangpeisen
     * @修改时间：2017/12/23 下午2:08
     * @修改备注：
     * @throws
     */
    public void HasPayPwd() {

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

    /**
     * @throws
     * @描述: 提现
     * @创建人：zhangpeisen
     * @创建时间：2017/12/23 上午9:55
     * @修改人：zhangpeisen
     * @修改时间：2017/12/23 上午9:55
     * @修改备注：
     */
    public void withdrawals() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String balance = getView().getBalance();

        if (TextUtils.isEmpty(balance) && balance.equals("")) {
            return;
        }

        getView().showDialog();
        iWalletModel.withdrawals(balance, new IModelImpl<ApiResponse<String>, String>() {
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
                getView().hideDialog();

            }
        });
    }

    /**
     * @throws
     * @描述: 查看提现
     * @创建人：zhangpeisen
     * @创建时间：2017/12/23 上午9:56
     * @修改人：zhangpeisen
     * @修改时间：2017/12/23 上午9:56
     * @修改备注：
     */
    public void selwithdrawals() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        getView().showDialog();
        iWalletModel.selwithdrawals(new IModelImpl<ApiResponse<WithDrawInfoBean>, WithDrawInfoBean>() {
            @Override
            protected void onSuccess(WithDrawInfoBean data, String message) {
                getView().hideDialog();
                getView().onSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<WithDrawInfoBean>> data, String message) {
                getView().hideDialog();
            }

            @Override
            protected void onFailure(String code, String message) {
                getView().hideDialog();
            }

            @Override
            protected void onSuccess() {
                getView().hideDialog();

            }
        });
    }

    /**
     * @throws
     * @描述:检验输入的支付密码是否正确
     * @创建人：zhangpeisen
     * @创建时间：2017/12/23 上午10:39
     * @修改人：zhangpeisen
     * @修改时间：2017/12/23 上午10:39
     * @修改备注：
     */
    public void checkPayPwd() {
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        getView().showDialog();

        String pwd = getView().getPwd();
        iCheckMobileModel.checkPayPwd(pwd, new IModelImpl<ApiResponse<String>, String>() {
            @Override
            protected void onSuccess(String data, String message) {
                getView().hideDialog();
                getView().onCheckPayPwdSuccess(message);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<String>> data, String message) {
                getView().hideDialog();
                getView().onCheckPayPwdSuccess(message);
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
                getView().onSuccessBankList(data, message);
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

    @Override
    protected void cancel() {
        iWalletModel.cancel();
        iCheckMobileModel.cancel();
        bankcardModel.cancel();
    }
}
