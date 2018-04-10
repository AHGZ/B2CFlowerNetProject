package com.android.p2pflowernet.project.view.fragments.mine.orderflow.refund_cargio;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.ExpresListBean;
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
 * Created by caishen on 2017/12/16.
 * by--
 */

public class ExpressageModel implements IModel {

    private final Retrofit retrofit;
    private final CompositeDisposable compositeDisposable;

    @Override
    public void cancel() {

        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    public ExpressageModel() {

        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    //获取快递公司的列表数据
    public void expressList(IModelImpl<ApiResponse<ExpresListBean>, ExpresListBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        String sign = SignUtil.getInstance().getSign(map);
        OrderFlowService codeService = retrofit.create(OrderFlowService.class);
        Disposable subscribe = codeService.expressList(sign, Constants.TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<ExpresListBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<ExpresListBean> sendCodeBeanApiResponse) throws Exception {
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
