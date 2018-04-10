package com.android.p2pflowernet.project.view.fragments.mine.orderflow;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.ApplyFroPayBean;
import com.android.p2pflowernet.project.entity.OrderDetailItemBean;
import com.android.p2pflowernet.project.entity.OrderListBean;
import com.android.p2pflowernet.project.entity.RefundBean;
import com.android.p2pflowernet.project.entity.RefundDetailBean;
import com.android.p2pflowernet.project.event.UserBanclanceBean;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.OrderFlowService;
import com.android.p2pflowernet.project.utils.SignUtil;
import com.rxy.netlib.http.ApiResponse;
import com.rxy.netlib.http.RetrofitUtils;

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
 * created on: 2017/12/12 上午9:24
 * description: 订单相关的数据层
 */
public class IOrderFlowModel implements IModel {

    private Retrofit mRetrofit;
    private CompositeDisposable compositeDisposable;

    public IOrderFlowModel() {
        mRetrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    public void orderflowlist(int page, int sel_type, final IModelImpl<ApiResponse<OrderListBean>, OrderListBean> listener) {
        HashMap<String, String> map = new HashMap<>();
        // 生成sign
        map.put("page", String.valueOf(page));
        map.put("sel_type", String.valueOf(sel_type));
        HashMap<String, String> param = new HashMap<>();
        // 传参
        param.put("page", String.valueOf(page));
        param.put("sel_type", String.valueOf(sel_type));
        String sign = SignUtil.getInstance().getSign(map);
        OrderFlowService orderFlowService = mRetrofit.create(OrderFlowService.class);

        Disposable subscribe = orderFlowService.orderflowlist(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<OrderListBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<OrderListBean> checkMobileBeanApiResponse) throws Exception {
                        listener.onComplete(checkMobileBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }

    //获取订单详情数据
    public void orderflowdetail(String order_id, final IModelImpl<ApiResponse<OrderDetailItemBean>, OrderDetailItemBean> listener) {
        HashMap<String, String> map = new HashMap<>();
        // 生成sign
        map.put("order_id", String.valueOf(order_id));
        HashMap<String, String> param = new HashMap<>();
        // 传参
        param.put("order_id", String.valueOf(order_id));
        String sign = SignUtil.getInstance().getSign(map);
        OrderFlowService orderFlowService = mRetrofit.create(OrderFlowService.class);
        Disposable subscribe = orderFlowService.orderflowdetail(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<OrderDetailItemBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<OrderDetailItemBean> checkMobileBeanApiResponse) throws Exception {
                        listener.onComplete(checkMobileBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }


    //取消订单
    public void cancleOrder(int orderId, final IModelImpl<ApiResponse<String>, String> listener) {

        HashMap<String, String> map = new HashMap<>();
        // 生成sign
        map.put("order_id", String.valueOf(orderId));
        HashMap<String, String> param = new HashMap<>();
        // 传参
        param.put("order_id", String.valueOf(orderId));
        String sign = SignUtil.getInstance().getSign(map);
        OrderFlowService orderFlowService = mRetrofit.create(OrderFlowService.class);
        Disposable subscribe = orderFlowService.cancleOrder(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<String>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<String> checkMobileBeanApiResponse) throws Exception {
                        listener.onComplete(checkMobileBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }

    /**
     * @throws
     * @描述: 退换货列表
     * @创建人：zhangpeisen
     * @创建时间：2017/12/15 上午10:13
     * @修改人：zhangpeisen
     * @修改时间：2017/12/15 上午10:13
     * @修改备注：
     */
    public void refundrecordlists(int page, final IModelImpl<ApiResponse<RefundBean>, RefundBean> listener) {
        HashMap<String, String> map = new HashMap<>();
        // 生成sign
        map.put("page", String.valueOf(page));
        HashMap<String, String> param = new HashMap<>();
        // 传参
        param.put("page", String.valueOf(page));
        String sign = SignUtil.getInstance().getSign(map);
        OrderFlowService orderFlowService = mRetrofit.create(OrderFlowService.class);
        Disposable subscribe = orderFlowService.refundrecordlists(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<RefundBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<RefundBean> checkMobileBeanApiResponse) throws Exception {
                        listener.onComplete(checkMobileBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }

    /**
     * @throws
     * @描述:退货/退款详情
     * @创建人：zhangpeisen
     * @创建时间：2017/12/15 上午11:28
     * @修改人：zhangpeisen
     * @修改时间：2017/12/15 上午11:28
     * @修改备注：
     */
    public void refundrecorddetail(String refund_id, final IModelImpl<ApiResponse<RefundDetailBean>, RefundDetailBean> listener) {
        HashMap<String, String> map = new HashMap<>();
        // 生成sign
        map.put("refund_id", refund_id);
        HashMap<String, String> param = new HashMap<>();
        // 传参
        param.put("refund_id", refund_id);
        String sign = SignUtil.getInstance().getSign(map);
        OrderFlowService orderFlowService = mRetrofit.create(OrderFlowService.class);
        Disposable subscribe = orderFlowService.refundrecorddetail(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<RefundDetailBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<RefundDetailBean> checkMobileBeanApiResponse) throws Exception {
                        listener.onComplete(checkMobileBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }

    /**
     * @throws
     * @描述:
     * @方法名: 申请退货/退款
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/16 下午2:42
     * @修改人 zhangpeisen
     * @修改时间 2017/12/16 下午2:42
     * @修改备注
     */
    public void addrefundrecord(String og_id, String order_num, String is_return, String refund_money, String reason, String explain, String img_path, final IModelImpl<ApiResponse<String>, String> listener) {
        HashMap<String, String> map = new HashMap<>();
        // 生成sign
        map.put("og_id", og_id);
        map.put("order_num", order_num);
        map.put("is_return", is_return);
        map.put("refund_money", refund_money);
        map.put("reason", reason);
        map.put("explain", explain);
        map.put("img_path", img_path);
        HashMap<String, String> param = new HashMap<>();
        // 传参
        param.put("og_id", og_id);
        param.put("order_num", order_num);
        param.put("is_return", is_return);
        param.put("refund_money", refund_money);
        param.put("reason", reason);
        param.put("explain", explain);
        param.put("img_path", img_path);
        String sign = SignUtil.getInstance().getSign(map);
        OrderFlowService orderFlowService = mRetrofit.create(OrderFlowService.class);
        Disposable subscribe = orderFlowService.addrefundrecord(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<String>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<String> checkMobileBeanApiResponse) throws Exception {
                        listener.onComplete(checkMobileBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }


    /**
     * @throws
     * @描述:取消退款/退货申请
     * @方法名:
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/18 下午5:26
     * @修改人 zhangpeisen
     * @修改时间 2017/12/18 下午5:26
     * @修改备注
     */
    public void cancelrefund(String refund_id, final IModelImpl<ApiResponse<String>, String> listener) {
        HashMap<String, String> map = new HashMap<>();
        // 生成sign
        map.put("refund_id", refund_id);
        HashMap<String, String> param = new HashMap<>();
        // 传参
        param.put("refund_id", refund_id);
        String sign = SignUtil.getInstance().getSign(map);
        OrderFlowService orderFlowService = mRetrofit.create(OrderFlowService.class);
        Disposable subscribe = orderFlowService.cancelrefund(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<String>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<String> checkMobileBeanApiResponse) throws Exception {
                        listener.onComplete(checkMobileBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }

    /**
     * @throws
     * @描述:申请仲裁
     * @方法名:
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/16 下午3:02
     * @修改人 zhangpeisen
     * @修改时间 2017/12/16 下午3:02
     * @修改备注
     */
    public void applyarbit(String refund_id, String content, final IModelImpl<ApiResponse<String>, String> listener) {
        HashMap<String, String> map = new HashMap<>();
        // 生成sign
        map.put("refund_id", refund_id);
        map.put("content", content);
        HashMap<String, String> param = new HashMap<>();
        // 传参
        param.put("refund_id", refund_id);
        param.put("content", content);
        String sign = SignUtil.getInstance().getSign(map);
        OrderFlowService orderFlowService = mRetrofit.create(OrderFlowService.class);
        Disposable subscribe = orderFlowService.applyarbit(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<String>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<String> checkMobileBeanApiResponse) throws Exception {
                        listener.onComplete(checkMobileBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }

    /**
     * @throws
     * @描述:取消仲裁申请
     * @方法名:
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/18 下午5:26
     * @修改人 zhangpeisen
     * @修改时间 2017/12/18 下午5:26
     * @修改备注
     */
    public void cancelarbitr(String refund_id, final IModelImpl<ApiResponse<String>, String> listener) {
        HashMap<String, String> map = new HashMap<>();
        // 生成sign
        map.put("refund_id", refund_id);
        HashMap<String, String> param = new HashMap<>();
        // 传参
        param.put("refund_id", refund_id);
        String sign = SignUtil.getInstance().getSign(map);
        OrderFlowService orderFlowService = mRetrofit.create(OrderFlowService.class);
        Disposable subscribe = orderFlowService.cancelarbitr(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<String>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<String> checkMobileBeanApiResponse) throws Exception {
                        listener.onComplete(checkMobileBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }


    //确认收货
    public void AffirmOrder(int orderId, final IModelImpl<ApiResponse<String>, String> listener) {

        HashMap<String, String> map = new HashMap<>();
        // 生成sign
        map.put("order_id", String.valueOf(orderId));
        HashMap<String, String> param = new HashMap<>();
        // 传参
        param.put("order_id", String.valueOf(orderId));
        String sign = SignUtil.getInstance().getSign(map);
        OrderFlowService orderFlowService = mRetrofit.create(OrderFlowService.class);
        Disposable subscribe = orderFlowService.affirmOrder(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<String>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<String> checkMobileBeanApiResponse) throws Exception {
                        listener.onComplete(checkMobileBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }

    //获取支付余额
    public void getBalance(final IModelImpl<ApiResponse<UserBanclanceBean>, UserBanclanceBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        HashMap<String, String> param = new HashMap<>();

        String sign = SignUtil.getInstance().getSign(map);
        OrderFlowService orderFlowService = mRetrofit.create(OrderFlowService.class);
        Disposable subscribe = orderFlowService.getBanlance(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<UserBanclanceBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<UserBanclanceBean> checkMobileBeanApiResponse) throws Exception {
                        listener.onComplete(checkMobileBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }

    @Override
    public void cancel() {
        if (compositeDisposable != null)
            compositeDisposable.dispose();
    }

    //身份吊起支付请求
    public void applyPay(String type, String getnum, final IModelImpl<ApiResponse<ApplyFroPayBean>, ApplyFroPayBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("num", getnum);
        map.put("type", type);

        HashMap<String, String> param = new HashMap<>();
        param.put("num", getnum);
        param.put("type", type);

        String sign = SignUtil.getInstance().getSign(map);
        OrderFlowService orderFlowService = mRetrofit.create(OrderFlowService.class);
        Disposable subscribe = orderFlowService.applyPay(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<ApplyFroPayBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<ApplyFroPayBean> checkMobileBeanApiResponse) throws Exception {
                        listener.onComplete(checkMobileBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);

    }

    //取消订单
    public void cancelOrder(String order_num, final IModelImpl<ApiResponse<String>,String> listener){
        HashMap<String, String> map = new HashMap<>();
        map.put("order_num", order_num);
        HashMap<String, String> param = new HashMap<>();
        param.put("order_num", order_num);
        String sign = SignUtil.getInstance().getSign(map);
        OrderFlowService orderFlowService = mRetrofit.create(OrderFlowService.class);
        Disposable subscribe = orderFlowService.cancelOrder(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<String>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<String> checkMobileBeanApiResponse) throws Exception {
                        listener.onComplete(checkMobileBeanApiResponse);
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
