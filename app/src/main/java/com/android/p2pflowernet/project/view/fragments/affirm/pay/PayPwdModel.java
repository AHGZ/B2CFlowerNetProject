package com.android.p2pflowernet.project.view.fragments.affirm.pay;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.PayPwdBean;
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
 * Created by caishen on 2017/11/16.
 * by--设置支付密码的逻辑
 */

public class PayPwdModel implements IModel {


    private final Retrofit retrofit;
    private final CompositeDisposable compositeDisposable;

    @Override
    public void cancel() {

        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    public PayPwdModel() {

        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }


    //设置支付密码
    public void setPayPwd(String pwd, String confirmPwd, final IModelImpl<ApiResponse<PayPwdBean>, PayPwdBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("pay_pwd", pwd);
        map.put("pay_confirm_pwd", confirmPwd);

        HashMap<String, String> param = new HashMap<>();
        param.put("pay_pwd", pwd);
        param.put("pay_confirm_pwd", confirmPwd);

        String sign = SignUtil.getInstance().getSign(map);
        PayService codeService = retrofit.create(PayService.class);
        Disposable subscribe = codeService.setPayPwd(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<PayPwdBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<PayPwdBean> sendCodeBeanApiResponse) throws Exception {
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

    //修改支付密码
    public void updataPayPwd(String firstStr, String lastStr, final IModelImpl<ApiResponse<String>, String> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("pay_pwd", firstStr);
        map.put("pay_confirm_pwd", lastStr);

        HashMap<String, String> param = new HashMap<>();
        param.put("pay_pwd", firstStr);
        param.put("pay_confirm_pwd", lastStr);

        String sign = SignUtil.getInstance().getSign(map);
        PayService codeService = retrofit.create(PayService.class);
        Disposable subscribe = codeService.updataPayPwd(sign, Constants.TOKEN, param)
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
