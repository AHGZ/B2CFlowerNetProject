package com.android.p2pflowernet.project.o2omain.fragment.pay;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.MerchXqBean;
import com.android.p2pflowernet.project.entity.ScanOrderBean;
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
 * Created by caishen on 2018/2/2.
 * by--店铺扫码支付
 */

public class AffirmPayModel implements IModel {

    private final Retrofit retrofit;
    private final CompositeDisposable compositeDisposable;

    @Override
    public void cancel() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    public AffirmPayModel() {

        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    //线下商家详情
    public void getMerceInfo(String merch_id, final IModelImpl<ApiResponse<MerchXqBean>, MerchXqBean> listener) {

        HashMap<String, String> map = new HashMap<>();

        map.put("merch_id", merch_id);

        HashMap<String, String> param = new HashMap<>();
        param.put("merch_id", merch_id);

        String sign = SignUtil.getInstance().getSign(map);
        OrderFlowService codeService = retrofit.create(OrderFlowService.class);
        Disposable subscribe = codeService.getMerceInfo(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<MerchXqBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<MerchXqBean> sendCodeBeanApiResponse) throws Exception {
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

    //下线扫码下单
    public void payscanOrder(String merch_id, String money,
                             IModelImpl<ApiResponse<ScanOrderBean>, ScanOrderBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("merch_id", merch_id);
        map.put("money", money);

        HashMap<String, String> param = new HashMap<>();
        param.put("merch_id", merch_id);
        param.put("money", money);

        String sign = SignUtil.getInstance().getSign(map);
        OrderFlowService codeService = retrofit.create(OrderFlowService.class);
        Disposable subscribe = codeService.payscanOrder(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<ScanOrderBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<ScanOrderBean> sendCodeBeanApiResponse) throws Exception {
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
