package com.android.p2pflowernet.project.view.fragments.mine.applyfor.manufac;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.ApplyForPartnerService;
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
 * Created by caishen on 2018/1/29.
 * by--
 */

public class ManufacModel implements IModel {

    private final Retrofit retrofit;
    private final CompositeDisposable compositeDisposable;

    @Override
    public void cancel() {

        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    public ManufacModel() {

        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    public void add_manufac_apply(String apply_name, String apply_phone, String apply_email,
                                  IModelImpl<ApiResponse<String>, String> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("apply_name", apply_name);
        map.put("apply_phone", apply_phone);
        map.put("apply_email", apply_email);

        HashMap<String, String> parma = new HashMap<>();
        parma.put("apply_name", apply_name);
        parma.put("apply_phone", apply_phone);
        parma.put("apply_email", apply_email);

        String sign = SignUtil.getInstance().getSign(map);
        ApplyForPartnerService codeService = retrofit.create(ApplyForPartnerService.class);
        Disposable subscribe = codeService.add_manufac_apply(sign, Constants.TOKEN, parma)
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

        compositeDisposable.add(subscribe);
    }
}
