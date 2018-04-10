package com.android.p2pflowernet.project.view.fragments.checkmobile;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.PayService;
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
 * Created by caishen on 2017/11/22.
 * by--检验银行预留手机号
 */

public class ICheckMobileModel implements IModel {

    private final Retrofit retrofit;
    private final CompositeDisposable compositeDisposable;

    @Override
    public void cancel() {

        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    public ICheckMobileModel() {

        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }


    //检验输入的支付密码是否正确
    public void checkPayPwd(String pwd, final IModelImpl<ApiResponse<String>, String> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("pay_pwd", pwd);

        HashMap<String, String> param = new HashMap<>();
        param.put("pay_pwd", pwd);

        String sign = SignUtil.getInstance().getSign(map);
        PayService codeService = retrofit.create(PayService.class);
        Disposable subscribe = codeService.verifyPwd(sign, Constants.TOKEN, param)
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
