package com.android.p2pflowernet.project.o2omain.fragment.mine.group_take_out.fulldetail;

import android.text.TextUtils;
import android.util.Log;

import com.android.p2pflowernet.project.entity.AppTradeBean;
import com.android.p2pflowernet.project.entity.BanlanceBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.GroupFullDetailBean;
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
 * Created by heguozhong on 2018/1/15/015.
 * 团购明细逻辑层
 */

public class IGroupFullDetailPresenter extends IPresenter<IGroupFullDetailView>{
    private final IGroupFullDetailModel iGroupFullDetailModel;
    private final IOrderFlowModel iOrderFlowModel;
    private final AffirmIndentModel affirmIndentModel;
    private final BankcardModel bankcardModel;
    @Override
    protected void cancel() {
        iGroupFullDetailModel.cancel();
    }

    public IGroupFullDetailPresenter() {

        iGroupFullDetailModel = new IGroupFullDetailModel();
        iOrderFlowModel = new IOrderFlowModel();
        affirmIndentModel = new AffirmIndentModel();
        bankcardModel = new BankcardModel();
    }


    //获取团购明细
    public void getGroupOrderDetail() {


        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String order_num = getView().getOrderNumber();

        // 收货地址的纬度
        String latitude = getView().latitude();
        // 收货地址的经度
        String longitude = getView().longitude();
        if (TextUtils.isEmpty(latitude) && latitude.equals("")) {
            return;
        }
        if (TextUtils.isEmpty(longitude) && longitude.equals("")) {
            return;
        }
        if (TextUtils.isEmpty(latitude) && latitude.equals("")) {
            return;
        }
        if (TextUtils.isEmpty(order_num) && order_num.equals("")) {
            return;
        }

        getView().showDialog();
        iGroupFullDetailModel.getGroupOrderDetail(order_num,latitude,longitude, new IModelImpl<ApiResponse<GroupFullDetailBean>, GroupFullDetailBean>() {
            @Override
            protected void onSuccess(GroupFullDetailBean data, String message) {
                getView().hideDialog();
                getView().onSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<GroupFullDetailBean>> data, String message) {
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
