package com.android.p2pflowernet.project.o2omain.fragment.refund;

import com.android.p2pflowernet.project.constant.Constants;
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
 * Created by zhangkun on 2018/1/24.
 */

public class RefundModel implements IModel {
    private Retrofit retrofit;
    private CompositeDisposable compositeDisposable;

    public RefundModel() {
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void cancel() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    /**
     *  提交申请退款
     * @param order_num
     * @param reason
     * @param explain
     * @param img_path
     * @param amount
     * @param listener
     */
    public void submitRefund(String order_num, String reason, String explain, String img_path, String amount,
                             IModelImpl<ApiResponse<String>,String> listener){
        HashMap<String, String> map = new HashMap<>();
        map.put("order_num",order_num);
        map.put("reason",reason);
        map.put("explain",explain);
        map.put("img_path",img_path);
        map.put("amount",amount);

        HashMap<String, String> param = new HashMap<>();
        param.put("order_num",order_num);
        param.put("reason",reason);
        param.put("explain",explain);
        param.put("img_path",img_path);
        param.put("amount",amount);

        String sign = SignUtil.getInstance().getSign(map);
        O2oService o2oService = retrofit.create(O2oService.class);
        Disposable disposable = o2oService.o2oapplyorder(sign, Constants.TOKEN,param)
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
