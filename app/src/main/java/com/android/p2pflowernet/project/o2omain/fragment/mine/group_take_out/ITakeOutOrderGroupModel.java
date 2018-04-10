package com.android.p2pflowernet.project.o2omain.fragment.mine.group_take_out;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.TakeOutOrderGroupBean;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.O2oService;
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
 * Created by caishen on 2018/1/26.
 * by--
 */

class ITakeOutOrderGroupModel implements IModel{

    private final Retrofit retrofit;
    private final CompositeDisposable compositeDisposable;

    @Override
    public void cancel() {

        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    public ITakeOutOrderGroupModel() {

        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();

    }

    //获取团购订单列表数据
    public void getTakeOutGroup(String state, int pages, final IModelImpl<ApiResponse<TakeOutOrderGroupBean>, TakeOutOrderGroupBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("state", state);
        map.put("pages", String.valueOf(pages));


        HashMap<String, String> param = new HashMap<>();
        param.put("state", state);
        param.put("pages", String.valueOf(pages));
        String sign = SignUtil.getInstance().getSign(map);
        O2oService o2oService = retrofit.create(O2oService.class);
        Disposable subscribe = o2oService.getTakeOutGroup(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<TakeOutOrderGroupBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<TakeOutOrderGroupBean> checkMobileBeanApiResponse) throws Exception {
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

    //取消团购订单
    public void cancelGroupOrder(String order_num, final IModelImpl<ApiResponse<String> ,String> listener){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("order_num",order_num);

        HashMap<String,String> param = new HashMap<>();
        param.put("order_num",order_num);

        String sign = SignUtil.getInstance().getSign(hashMap);
        O2oService o2oService = retrofit.create(O2oService.class);
        Disposable disposable = o2oService.cancelGroupOrder(sign,Constants.TOKEN,param)
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
        compositeDisposable.add(disposable);
    }

    //团购退订单
    public void refundGroupOrder(String order_num, final IModelImpl<ApiResponse<String>,String> listener){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("order_num",order_num);

        HashMap<String,String> param = new HashMap<>();
        param.put("order_num",order_num);

        String sign = SignUtil.getInstance().getSign(hashMap);

        O2oService o2oService = retrofit.create(O2oService.class);
        Disposable disposable = o2oService.refundGroupOrder(sign,Constants.TOKEN,param)
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
        compositeDisposable.add(disposable);
    }

}
