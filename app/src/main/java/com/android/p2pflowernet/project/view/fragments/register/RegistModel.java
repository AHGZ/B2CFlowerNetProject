package com.android.p2pflowernet.project.view.fragments.register;

import com.android.p2pflowernet.project.entity.CheckMobileBean;
import com.android.p2pflowernet.project.entity.SendCodeBean;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.CodeService;
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
 * Created by caishen on 2017/11/10.
 * by--注册---数据层
 */

public class RegistModel implements IModel {


    private Retrofit retrofit;
    private CompositeDisposable compositeDisposable;


    public RegistModel() {

        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();

    }

    /**
     * 探测手机号接口
     *
     * @param phone
     * @param listener
     */
    public void CheckMobile(String phone, final IModelImpl<ApiResponse<CheckMobileBean>, CheckMobileBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", phone);
        String sign = SignUtil.getInstance().getSign(map);
        CodeService codeService = retrofit.create(CodeService.class);
        Disposable subscribe = codeService.checkMobile(sign,"", phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<CheckMobileBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<CheckMobileBean> checkMobileBeanApiResponse) throws Exception {
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

    /**
     * 手机验证码生成接口(ty)
     *
     * @param phone
     * @param listener
     */
    public void sendCode(String phone, final IModelImpl<ApiResponse<SendCodeBean>, SendCodeBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", phone);
        String sign = SignUtil.getInstance().getSign(map);
        CodeService codeService = retrofit.create(CodeService.class);
        Disposable subscribe = codeService.sendCode(sign,"", phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<SendCodeBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<SendCodeBean> sendCodeBeanApiResponse) throws Exception {
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


    @Override
    public void cancel() {

        if (compositeDisposable != null) {

            compositeDisposable.dispose();
        }
    }
}
