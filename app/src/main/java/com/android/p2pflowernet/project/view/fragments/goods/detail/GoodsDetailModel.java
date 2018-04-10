package com.android.p2pflowernet.project.view.fragments.goods.detail;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.OrderDetailBean;
import com.android.p2pflowernet.project.entity.ShareGoodsBean;
import com.android.p2pflowernet.project.entity.SpecCompareBean;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.GoodsInfoService;
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
 * Created by caishen on 2017/12/12.
 * by--
 */

public class GoodsDetailModel implements IModel {
    private final Retrofit retrofit;
    private final CompositeDisposable compositeDisposable;

    @Override
    public void cancel() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    public GoodsDetailModel() {

        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }


    public void orderSel(String specId, String count, String source, String select,
                         IModelImpl<ApiResponse<OrderDetailBean>, OrderDetailBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("spec_id", specId);
        map.put("count", count);
        map.put("source", source);
        map.put("select", select);

        HashMap<String, String> param = new HashMap<>();
        param.put("spec_id", specId);
        param.put("count", count);
        param.put("source", source);
        param.put("select", select);

        String sign = SignUtil.getInstance().getSign(map);
        GoodsInfoService codeService = retrofit.create(GoodsInfoService.class);
        Disposable subscribe = codeService.orderSel(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<OrderDetailBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<OrderDetailBean> sendCodeBeanApiResponse) throws Exception {
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


    //商品分享详情
    public void getShareGoods(String goodId, IModelImpl<ApiResponse<ShareGoodsBean>, ShareGoodsBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("goods_id", goodId);

        HashMap<String, String> param = new HashMap<>();
        param.put("goods_id", goodId);

        String sign = SignUtil.getInstance().getSign(map);
        GoodsInfoService codeService = retrofit.create(GoodsInfoService.class);
        Disposable subscribe = codeService.getShareGoods(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<ShareGoodsBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<ShareGoodsBean> sendCodeBeanApiResponse) throws Exception {
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

    //根据规格更改比价
    public void compSpec(String sepcId, IModelImpl<ApiResponse<SpecCompareBean>, SpecCompareBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("spec_id", sepcId);

        HashMap<String, String> param = new HashMap<>();
        param.put("spec_id", sepcId);

        String sign = SignUtil.getInstance().getSign(map);
        GoodsInfoService codeService = retrofit.create(GoodsInfoService.class);
        Disposable subscribe = codeService.compSpec(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<SpecCompareBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<SpecCompareBean> sendCodeBeanApiResponse) throws Exception {
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
