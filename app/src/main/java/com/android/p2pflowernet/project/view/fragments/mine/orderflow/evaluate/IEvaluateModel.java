package com.android.p2pflowernet.project.view.fragments.mine.orderflow.evaluate;

import com.android.p2pflowernet.project.constant.Constants;
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
 * by--
 */

public class IEvaluateModel implements IModel {

    private final Retrofit retrofit;
    private final CompositeDisposable compositeDisposable;

    @Override
    public void cancel() {

        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    public IEvaluateModel() {
        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    //添加评价
    public void addGoodsEval(String orderId, String isAnonymous, int goodsDescScore,
                             int logisticsServiceScore, int serviceAttitudeScore,
                             String content, String imgPath,
                             final IModelImpl<ApiResponse<String>, String> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("order_id", orderId);
        map.put("is_anonymous", isAnonymous);
        map.put("goods_desc_score", goodsDescScore + "");
        map.put("logistics_service_score", logisticsServiceScore + "");
        map.put("service_attitude_score", serviceAttitudeScore + "");
        map.put("content", content);
        map.put("img_path", imgPath);

        HashMap<String, String> param = new HashMap<>();
        param.put("order_id", orderId);
        param.put("is_anonymous", isAnonymous);
        param.put("goods_desc_score", goodsDescScore + "");
        param.put("logistics_service_score", logisticsServiceScore + "");
        param.put("service_attitude_score", serviceAttitudeScore + "");
        param.put("content", content);
        param.put("img_path", imgPath);


        String sign = SignUtil.getInstance().getSign(map);
        OrderFlowService codeService = retrofit.create(OrderFlowService.class);
        Disposable subscribe = codeService.addGoodsEval(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<String>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<String> sendCodeBeanApiResponse) throws Exception {
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
