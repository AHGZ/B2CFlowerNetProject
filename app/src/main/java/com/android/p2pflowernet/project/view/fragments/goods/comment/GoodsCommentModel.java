package com.android.p2pflowernet.project.view.fragments.goods.comment;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.EveluateBean;
import com.android.p2pflowernet.project.entity.GuaranteeBean;
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
 * Created by caishen on 2017/12/5.
 * by--商品详情评价数据层
 */

public class GoodsCommentModel implements IModel {

    private final Retrofit retrofit;
    private final CompositeDisposable compositeDisposable;

    @Override
    public void cancel() {

        if (compositeDisposable != null) {

            compositeDisposable.dispose();
        }
    }

    public GoodsCommentModel() {

        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    //获取商品详情评价
    public void getEveluate(String goodsId, int page, int type, final IModelImpl<ApiResponse<EveluateBean>, EveluateBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("goods_id", goodsId);
        map.put("page", page + "");
        map.put("type", type + "");

        HashMap<String, String> param = new HashMap<>();
        param.put("goods_id", goodsId);
        param.put("page", page + "");
        param.put("type", type + "");

        String sign = SignUtil.getInstance().getSign(map);
        GoodsInfoService goodsService = retrofit.create(GoodsInfoService.class);
        Disposable subscribe = goodsService.getEveluate(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<EveluateBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<EveluateBean> checkMobileBeanApiResponse) throws Exception {
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

    //基本保障
    public void guarantee(String goodsId, IModelImpl<ApiResponse<GuaranteeBean>, GuaranteeBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("goods_id", goodsId);

        HashMap<String, String> param = new HashMap<>();
        param.put("goods_id", goodsId);

        String sign = SignUtil.getInstance().getSign(map);
        GoodsInfoService goodsService = retrofit.create(GoodsInfoService.class);
        Disposable subscribe = goodsService.guarantee(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<GuaranteeBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<GuaranteeBean> checkMobileBeanApiResponse) throws Exception {
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
