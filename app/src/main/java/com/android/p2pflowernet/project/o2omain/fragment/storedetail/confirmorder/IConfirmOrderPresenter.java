package com.android.p2pflowernet.project.o2omain.fragment.storedetail.confirmorder;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.AppTradeBean;
import com.android.p2pflowernet.project.entity.BanlanceBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.GoPayBean;
import com.android.p2pflowernet.project.entity.O2oorderCommitBean;
import com.android.p2pflowernet.project.event.UserBanclanceBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.o2omain.fragment.O2oModel;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.affirm.AffirmIndentModel;
import com.android.p2pflowernet.project.view.fragments.mine.orderflow.IOrderFlowModel;
import com.android.p2pflowernet.project.view.fragments.mine.wallet.bankcard.BankcardModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by heguozhong on 2018/1/10/010.
 * 确认订单逻辑层
 */

public class IConfirmOrderPresenter extends IPresenter<IConfirmOrderView> {
    private final IOrderFlowModel iOrderFlowModel;
    private final AffirmIndentModel affirmIndentModel;
    private final BankcardModel bankcardModel;
    O2oModel o2oModel;

    public IConfirmOrderPresenter() {
        o2oModel = new O2oModel();
        iOrderFlowModel = new IOrderFlowModel();
        affirmIndentModel = new AffirmIndentModel();
        bankcardModel = new BankcardModel();
    }

    /**
     * @throws
     * @描述: 确认订单
     * @方法名:o2ocreorder
     * @返回类型void
     * @创建人 zhangpeisen
     * @创建时间 2018/1/13 下午2:35
     * @修改人 zhangpeisen
     * @修改时间 2018/1/13 下午2:35
     * @修改备注
     */
    public void o2ocreorder() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        // 商家ID
        String merchId = getView().merchId();
        // 商品ID
        String goodslist = getView().goodslist();
        // 收货地址ID
        String addressid = getView().addressid();
        // 用餐人数
        String dinnersetcount = getView().dinnersetcount();
        // 预约时间
        String reachtime = getView().reachtime();
        // 收货地址的纬度
        String latitude = getView().latitude();
        // 收货地址的经度
        String longitude = getView().longitude();
        //备注
        String note = getView().getNote();

        if (TextUtils.isEmpty(merchId) && merchId.equals("")) {
            return;
        }
        if (TextUtils.isEmpty(goodslist) && goodslist.equals("")) {
            return;
        }
        if (TextUtils.isEmpty(addressid) || addressid.equals("0")) {
            getView().onError("请添加收货地址");
            return;
        }
        if (TextUtils.isEmpty(dinnersetcount) && dinnersetcount.equals("")) {
            return;
        }
        if (TextUtils.isEmpty(latitude) && latitude.equals("")) {
            return;
        }
        if (TextUtils.isEmpty(longitude) && longitude.equals("")) {
            return;
        }

        String is_self = getView().getis_self();
        if (TextUtils.isEmpty(is_self) && is_self.equals("")) {
            return;
        }

        String phone = getView().getphone();
        if (is_self.equals("2") && TextUtils.isEmpty(phone)) {
            getView().onError("请填写自取电话");
            return;
        }

        getView().showDialog();
        o2oModel.o2ocreorder(merchId, goodslist, addressid,
                dinnersetcount, reachtime, latitude, longitude, is_self, phone,note, new IModelImpl<ApiResponse<O2oorderCommitBean>, O2oorderCommitBean>() {
                    @Override
                    protected void onSuccess(O2oorderCommitBean data, String message) {
                        getView().hideDialog();
                        getView().onSuccessAffirm(data);
                    }

                    @Override
                    protected void onSuccess(ArrayList<ApiResponse<O2oorderCommitBean>> data, String message) {
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
     * @描述: 去结算
     * @方法名:o2ogopay
     * @返回类型void
     * @创建人 zhangpeisen
     * @创建时间 2018/1/17 下午3:29
     * @修改人 zhangpeisen
     * @修改时间 2018/1/17 下午3:29
     * @修改备注
     */
    public void o2ogopay() {
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String merchId = getView().merchId();
        String longitude = getView().longitude();
        String latitude = getView().latitude();
        if (TextUtils.isEmpty(merchId) && merchId.equals("")) {
            return;
        }
        if (TextUtils.isEmpty(longitude) && longitude.equals("")) {
            getView().onError("抱歉,未能找到结果");
            return;
        }
        if (TextUtils.isEmpty(latitude) && latitude.equals("")) {
            getView().onError("抱歉,未能找到结果");
            return;
        }
        getView().showDialog();
        o2oModel.o2ogopay(merchId, longitude, latitude, new IModelImpl<ApiResponse<GoPayBean>, GoPayBean>() {
            @Override
            protected void onSuccess(GoPayBean data, String message) {
                getView().hideDialog();
                getView().onSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<GoPayBean>> data, String message) {
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
        o2oModel.cancel();
    }

    //获取余额支付
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

    // 支付宝支付处理逻辑
    public void AlipayPresenter() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

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

    //余额支付
    public void yEpay() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String count = getView().getOrderNum();
        String salePrice = getView().getSalePrice();
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
