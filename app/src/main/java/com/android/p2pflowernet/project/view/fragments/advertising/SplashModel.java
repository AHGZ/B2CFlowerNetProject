package com.android.p2pflowernet.project.view.fragments.advertising;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.SplashBean;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.SettingService;
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
 * Created by caishen on 2018/2/1.
 * by--
 */

class SplashModel implements IModel {

    private final Retrofit retrofit;
    private final CompositeDisposable compositeDisposable;

    @Override
    public void cancel() {

        if (compositeDisposable != null) {

            compositeDisposable.dispose();
        }
    }

    public SplashModel() {

        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    //获取广告播放权限
    public void getAdvertising(final IModelImpl<ApiResponse<SplashBean>, SplashBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        HashMap<String, String> param = new HashMap<>();
        String sign = SignUtil.getInstance().getSign(map);
        SettingService codeService = retrofit.create(SettingService.class);
        Disposable subscribe = codeService.getAdvertising(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<SplashBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<SplashBean> checkMobileBeanApiResponse) throws Exception {
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
