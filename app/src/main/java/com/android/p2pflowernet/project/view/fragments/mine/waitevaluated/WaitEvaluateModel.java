package com.android.p2pflowernet.project.view.fragments.mine.waitevaluated;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.WaitEvaluatedBean;
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
 * Created by caishen on 2017/12/14.
 * by--待评价的逻辑层
 */

public class WaitEvaluateModel implements IModel {

    private final Retrofit mRetrofit;
    private final CompositeDisposable compositeDisposable;

    @Override
    public void cancel() {

        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    public WaitEvaluateModel() {

        mRetrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    //获取待评价列表
    public void getWaitComment(int page, IModelImpl<ApiResponse<WaitEvaluatedBean>, WaitEvaluatedBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("page", page + "");
        HashMap<String, String> param = new HashMap<>();
        param.put("page", page + "");
        String sign = SignUtil.getInstance().getSign(map);
        OrderFlowService codeService = mRetrofit.create(OrderFlowService.class);
        Disposable subscribe = codeService.getWaitComment(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<WaitEvaluatedBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<WaitEvaluatedBean> checkMobileBeanApiResponse) throws Exception {
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
