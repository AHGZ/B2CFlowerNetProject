package com.android.p2pflowernet.project.view.fragments.mine.wallet.bill;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.BillAllBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.mine.wallet.IWalletModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;


/**
 * @描述:账单模块逻辑层
 * @创建人：zhangpeisen
 * @创建时间：2017/12/22 下午5:27
 * @修改人：zhangpeisen
 * @修改时间：2017/12/22 下午5:27
 * @修改备注：
 * @throws
 */
public class IBillPrenter extends IPresenter<IBillView> {

    private IWalletModel iWalletModel;


    public IBillPrenter() {

        iWalletModel = new IWalletModel();
    }

    /**
     * @throws
     * @描述:账单
     * @方法名:
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/22 下午5:33
     * @修改人 zhangpeisen
     * @修改时间 2017/12/22 下午5:33
     * @修改备注
     */
    public void walletbill() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String page = getView().getPage();
        String Year = getView().getYear();
        String Month = getView().getMonth();

        if (TextUtils.isEmpty(page) && page.equals("")) {
            return;
        }
        if (TextUtils.isEmpty(Year) && Year.equals("")) {
            return;
        }
        if (TextUtils.isEmpty(Month) && Month.equals("")) {
            return;
        }
        getView().showDialog();
        iWalletModel.walletbill(page, Year, Month, new IModelImpl<ApiResponse<BillAllBean>, BillAllBean>() {
            @Override
            protected void onSuccess(BillAllBean data, String message) {
                getView().hideDialog();
                getView().onSuccessData(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<BillAllBean>> data, String message) {
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

    @Override
    protected void cancel() {
        iWalletModel.cancel();
    }
}
