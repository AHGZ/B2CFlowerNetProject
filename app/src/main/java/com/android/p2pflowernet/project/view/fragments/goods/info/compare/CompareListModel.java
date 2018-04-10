package com.android.p2pflowernet.project.view.fragments.goods.info.compare;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.CompareListBean;
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
 * Created by caishen on 2018/1/31.
 * by--
 */

class CompareListModel implements IModel {

    private final Retrofit retrofit;
    private final CompositeDisposable compositeDisposable;

    @Override
    public void cancel() {

        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    public CompareListModel() {
        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    public void compares(String specId, IModelImpl<ApiResponse<CompareListBean>, CompareListBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("spec_id", specId);

        HashMap<String, String> param = new HashMap<>();
        param.put("spec_id", specId);

        String sign = SignUtil.getInstance().getSign(map);
        GoodsInfoService goodsService = retrofit.create(GoodsInfoService.class);
        Disposable subscribe = goodsService.compares(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<CompareListBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<CompareListBean> checkMobileBeanApiResponse) throws Exception {
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
