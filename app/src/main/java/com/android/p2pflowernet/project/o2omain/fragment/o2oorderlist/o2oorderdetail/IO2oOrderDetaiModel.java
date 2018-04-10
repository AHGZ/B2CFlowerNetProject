package com.android.p2pflowernet.project.o2omain.fragment.o2oorderlist.o2oorderdetail;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.O2oGoodsInfoBean;
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
 * Created by caishen on 2018/1/25.
 * by--
 */

public class IO2oOrderDetaiModel implements IModel {

    private final Retrofit retrofit;
    private final CompositeDisposable compositeDisposable;

    @Override
    public void cancel() {

        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    public IO2oOrderDetaiModel() {

        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    public void get_goods_info(String merId, String goodId, int page,
                               final IModelImpl<ApiResponse<O2oGoodsInfoBean>, O2oGoodsInfoBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("merch_id", merId);
        map.put("goods_id", goodId);
        map.put("page", String.valueOf(page));

        HashMap<String, String> param = new HashMap<>();
        param.put("merch_id", merId);
        param.put("goods_id", goodId);
        param.put("page", String.valueOf(page));
        String sign = SignUtil.getInstance().getSign(map);
        O2oService o2oService = retrofit.create(O2oService.class);
        Disposable subscribe = o2oService.get_goods_info(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<O2oGoodsInfoBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<O2oGoodsInfoBean> checkMobileBeanApiResponse) throws Exception {
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
