package com.android.p2pflowernet.project.o2omain.fragment.mine.take_out;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.EncoreBean;
import com.android.p2pflowernet.project.entity.TakeOutBean;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.EncoreService;
import com.android.p2pflowernet.project.service.TakeOutSerVice;
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
 * Created by caishen on 2018/1/16.
 * by--
 */

public class TakeOutModel implements IModel {

    private final Retrofit retrofit;
    private final CompositeDisposable compositeDisposable;

    @Override
    public void cancel() {

        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    public TakeOutModel() {
        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    public void getTakeOut(String sate, int page, final IModelImpl<ApiResponse<TakeOutBean>, TakeOutBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        HashMap<String, String> param = new HashMap<>();
        // 传入参数
        param.put("state", sate);
        param.put("page", String.valueOf(page));
        // 传入签名
        map.put("state", sate);
        map.put("page", String.valueOf(page));
        String sign = SignUtil.getInstance().getSign(map);
        TakeOutSerVice personal = retrofit.create(TakeOutSerVice.class);
        Disposable subscribe = personal.getTakeOut(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<TakeOutBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<TakeOutBean> sendCodeBeanApiResponse) throws Exception {
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

    //再来一单
    public void encore(String order_num, final IModelImpl<ApiResponse<EncoreBean>, EncoreBean> listener) {
        // 传入签名
        HashMap<String, String> map = new HashMap<>();
        map.put("order_num", order_num);
        // 传入参数
        HashMap<String, String> param = new HashMap<>();
        param.put("order_num", order_num);
        String sign = SignUtil.getInstance().getSign(map);
        EncoreService personal = retrofit.create(EncoreService.class);
        Disposable subscribe = personal.Encore(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<EncoreBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<EncoreBean> sendCodeBeanApiResponse) throws Exception {
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

    //取消退款
    public void cancelRefund(String order_num, final IModelImpl<ApiResponse<String>, String> listener) {
        // 传入签名
        HashMap<String, String> map = new HashMap<>();
        map.put("order_num", order_num);
        // 传入参数
        HashMap<String, String> param = new HashMap<>();
        param.put("order_num", order_num);
        String sign = SignUtil.getInstance().getSign(map);
        TakeOutSerVice takeOutSerVice = retrofit.create(TakeOutSerVice.class);
        Disposable subscribe = takeOutSerVice.cancelRefund(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<String>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<String> string) throws Exception {
                        listener.onComplete(string);
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
    public void confirmGoods(String order_id, final IModelImpl<ApiResponse<String>, String> listener) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("order_id", order_id);

        HashMap<String, String> param = new HashMap<>();
        param.put("order_id", order_id);

        String sign = SignUtil.getInstance().getSign(hashMap);
        TakeOutSerVice takeOutSerVice = retrofit.create(TakeOutSerVice.class);
        Disposable disposable = takeOutSerVice.confirmGoods(sign,Constants.TOKEN,param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<String>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<String> string) throws Exception {
                        listener.onComplete(string);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(disposable);
    }
}
