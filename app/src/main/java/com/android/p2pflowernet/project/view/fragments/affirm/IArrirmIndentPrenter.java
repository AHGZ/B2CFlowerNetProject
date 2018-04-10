package com.android.p2pflowernet.project.view.fragments.affirm;

import android.app.Activity;
import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.AppTradeBean;
import com.android.p2pflowernet.project.entity.BanlanceBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.CommitAffirmBean;
import com.android.p2pflowernet.project.entity.OrderDetailBean;
import com.android.p2pflowernet.project.entity.UnionPayEntity;
import com.android.p2pflowernet.project.event.UserBanclanceBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.goods.detail.GoodsDetailModel;
import com.android.p2pflowernet.project.view.fragments.mine.orderflow.IOrderFlowModel;
import com.android.p2pflowernet.project.view.fragments.mine.wallet.bankcard.BankcardModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/10/28.
 * by--确认订单的逻辑层
 */

public class IArrirmIndentPrenter extends IPresenter<IArrirmIndentView> {

    private final GoodsDetailModel goodsDetailModel;
    AffirmIndentModel affirmIndentModel;

    public static final int PLUGIN_NOT_INSTALLED = -1;
    public static final int PLUGIN_NEED_UPGRADE = 2;
    private final BankcardModel bankcardModel;
    private final IOrderFlowModel iOrderFlowModel;

    public IArrirmIndentPrenter() {

        affirmIndentModel = new AffirmIndentModel();
        bankcardModel = new BankcardModel();
        iOrderFlowModel = new IOrderFlowModel();
        goodsDetailModel = new GoodsDetailModel();
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

    @Override
    protected void cancel() {

        affirmIndentModel.cancel();
        bankcardModel.cancel();
        iOrderFlowModel.cancel();
        goodsDetailModel.cancel();
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

            }
        });
    }

    //余额支付接口
    public void yEpay() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String count = getView().getOrderNum();
        String salePrice = getView().getSalePrice();
        String body = getView().getBody();
        String father_order = getView().is_father_order();
        String type = getView().getType();//支付类型

        getView().showDialog();
        affirmIndentModel.yEPay(count, type, body, father_order, "", new IModelImpl<ApiResponse<BanlanceBean>, BanlanceBean>() {
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

    //提交订单
    public void commitSel() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String specId = getView().getSpecId();
        String count = getView().getCount();
        String source = getView().getSource();
        String select = getView().getSelect();
        String invoice = getView().getInvoice();
        String title = getView().getTitle();
        String taxNum = getView().getTaxNum();
        String texture = getView().getTexture();
        String userType = getView().getUserType();
        String addressId = getView().getAddressId();

        if (TextUtils.isEmpty(addressId)) {
            getView().onError("请添加收货地址");
            return;
        }

        if (userType.equals("2")) {//单位
            if (TextUtils.isEmpty(taxNum)) {
                getView().onError("请填写税号");
                return;
            }
        }

        // 加载进度条
        getView().showDialog();
        affirmIndentModel.commitSel(specId, count, source, select, invoice, title, taxNum, texture, userType, addressId,
                new IModelImpl<ApiResponse<CommitAffirmBean>, CommitAffirmBean>() {
                    @Override
                    protected void onSuccess(CommitAffirmBean data, String message) {

                        getView().hideDialog();
                        getView().SuccessComit(data);
                    }

                    @Override
                    protected void onSuccess(ArrayList<ApiResponse<CommitAffirmBean>> data, String message) {
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

    //获取余额
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

    //确认订单
    public void affirmOrder() {


        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String specId = getView().getSpecId();
        String count = getView().getCount();
        String source = getView().getSource();//来源
        String select = getView().getSelect();//是否全选

        goodsDetailModel.orderSel(specId, count, source, select,
                new IModelImpl<ApiResponse<OrderDetailBean>, OrderDetailBean>() {
                    @Override
                    protected void onSuccess(OrderDetailBean data, String message) {
                        getView().hideDialog();
                        getView().SuccessOrder(data);
                    }

                    @Override
                    protected void onSuccess(ArrayList<ApiResponse<OrderDetailBean>> data, String message) {
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
