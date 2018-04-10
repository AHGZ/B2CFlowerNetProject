package com.android.p2pflowernet.project.view.fragments.affirm;

import android.app.Activity;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.AppTradeBean;
import com.android.p2pflowernet.project.entity.BanlanceBean;
import com.android.p2pflowernet.project.entity.CommitAffirmBean;
import com.android.p2pflowernet.project.entity.UnionPayEntity;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.OrderFlowService;
import com.android.p2pflowernet.project.service.UnionPayService;
import com.android.p2pflowernet.project.utils.SignUtil;
import com.rxy.netlib.http.ApiResponse;
import com.rxy.netlib.http.RetrofitUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * author: zhangpeisen
 * created on: 2017/11/6 下午1:18
 * description: 确认支付
 */
public class AffirmIndentModel implements IModel {


    private Retrofit mRetrofit;
    private CompositeDisposable compositeDisposable;

    public AffirmIndentModel() {

        mRetrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    public void UnionPay(final IModelImpl<ApiResponse<UnionPayEntity>, UnionPayEntity> listener) {

        UnionPayService service = mRetrofit.create(UnionPayService.class);
        Disposable disposable = service.unionpay()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<UnionPayEntity>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<UnionPayEntity> unionPayResult) throws Exception {
                        listener.onComplete(unionPayResult);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(disposable);
    }

    // 微信支付
    public void WeChatPay(Activity activity) {

        String wx_appid = "wxd930ea5d5a258f4f";     //替换为自己的appid
//        WXPay.init(activity, wx_appid);      //要在支付前调用
        PayReq request = new PayReq();
        request.appId = "wxd930ea5d5a258f4f";
        request.partnerId = "1900000109";
        request.prepayId = "1101000000140415649af9fc314aa427";
        request.packageValue = "Sign=WXPay";
        request.nonceStr = "1101000000140429eb40476f8896f4c9";
        request.timeStamp = "1398746574";
        request.sign = "7FFECB600D7157C5AA49810D2D8F28BC2811827B";

//        WXPay.getInstance().doPay(request.toString(), new WXPay.WXPayResultCallBack() {
//            @Override
//            public void onSuccess() {
//
//                Toast.makeText(activity, "支付成功", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onError(int error_code) {
//                switch (error_code) {
//                    case WXPay.NO_OR_LOW_WX:
//
//                        Toast.makeText(activity, "未安装微信或微信版本过低", Toast.LENGTH_SHORT).show();
//                        break;
//
//                    case WXPay.ERROR_PAY_PARAM:
//
//                        Toast.makeText(activity, "参数错误", Toast.LENGTH_SHORT).show();
//                        break;
//
//                    case WXPay.ERROR_PAY:
//
//                        Toast.makeText(activity, "支付失败", Toast.LENGTH_SHORT).show();
//
//                        break;
//                }
//            }
//
//            @Override
//            public void onCancel() {
//
//                Toast.makeText(activity, "支付取消", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public void cancel() {
        if (compositeDisposable != null)
            compositeDisposable.dispose();
    }

    public boolean UnionPayCheck() {

        return true;
    }

    //余额支付接口
    public void yEPay(String count, String type, String body, String isFaher, String num,
                      IModelImpl<ApiResponse<BanlanceBean>, BanlanceBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("order_num", count);
        map.put("is_father_order", isFaher);
        map.put("num", num);
        map.put("type", type);

        HashMap<String, String> param = new HashMap<>();
        param.put("order_num", count);
        param.put("is_father_order", isFaher);
        param.put("num", num);
        param.put("type", type);

        String sign = SignUtil.getInstance().getSign(map);
        OrderFlowService codeService = mRetrofit.create(OrderFlowService.class);
        Disposable subscribe = codeService.balancePay(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<BanlanceBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<BanlanceBean> sendCodeBeanApiResponse) throws Exception {
                        listener.onComplete(sendCodeBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }

    //提交订单
    public void commitSel(String specId, String count, String source, String select,
                          String invoice, String title, String taxNum,
                          String texture, String userType, String addressId, IModelImpl<ApiResponse<CommitAffirmBean>, CommitAffirmBean> listener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("spec_id", specId);
        map.put("count", count);
        map.put("source", source);
        map.put("select", select);
        map.put("invoice", invoice);
        map.put("title", title);
        map.put("tax_num", taxNum);
        map.put("texture", texture);
        map.put("user_type", userType);
        map.put("address_id", addressId);

        HashMap<String, String> param = new HashMap<>();
        param.put("spec_id", specId);
        param.put("count", count);
        param.put("source", source);
        param.put("select", select);
        param.put("invoice", invoice);
        param.put("title", title);
        param.put("tax_num", taxNum);
        param.put("texture", texture);
        param.put("user_type", userType);
        param.put("address_id", addressId);

        String sign = SignUtil.getInstance().getSign(map);
        OrderFlowService codeService = mRetrofit.create(OrderFlowService.class);
        Disposable subscribe = codeService.commitSel(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<CommitAffirmBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<CommitAffirmBean> sendCodeBeanApiResponse) throws Exception {
                        listener.onComplete(sendCodeBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }

    //支付宝请求签名接口
    public void aliPay(String orderNum, String type, String isFather, String source, String num,
                       IModelImpl<ApiResponse<AppTradeBean>, AppTradeBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("order_num", orderNum);
        map.put("type", type);
        map.put("source", source);
        map.put("is_father_order", isFather);
        map.put("num", num);

        HashMap<String, String> param = new HashMap<>();
        param.put("order_num", orderNum);
        param.put("type", type);
        param.put("source", source);
        param.put("is_father_order", isFather);
        param.put("num", num);

        String sign = SignUtil.getInstance().getSign(map);
        OrderFlowService codeService = mRetrofit.create(OrderFlowService.class);
        Disposable subscribe = codeService.appTrade(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<AppTradeBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<AppTradeBean> sendCodeBeanApiResponse) throws Exception {
                        listener.onComplete(sendCodeBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }

    //删除订单
    public void delOrder(int orderId, IModelImpl<ApiResponse<String>, String> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("order_id", orderId + "");

        HashMap<String, String> param = new HashMap<>();
        param.put("order_id", orderId + "");

        String sign = SignUtil.getInstance().getSign(map);
        OrderFlowService codeService = mRetrofit.create(OrderFlowService.class);
        Disposable subscribe = codeService.delOrder(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<String>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<String> sendCodeBeanApiResponse) throws Exception {
                        listener.onComplete(sendCodeBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }
}
