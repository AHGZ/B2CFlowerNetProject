package com.android.p2pflowernet.project.view.fragments.mine.setting.login;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.ChangePhoneBean;
import com.android.p2pflowernet.project.entity.SendCodeBean;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.ChangePhoneService;
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
 * Created by caishen on 2017/11/18.
 * by--更换手机号的数据层
 */

public class ChangePhoneModel implements IModel {

    private final Retrofit retrofit;
    private final CompositeDisposable compositeDisposable;

    @Override
    public void cancel() {

        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    public ChangePhoneModel() {

        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();

    }

    //发送验证码
    public void sendCode(String phone, IModelImpl<ApiResponse<SendCodeBean>, SendCodeBean> iModel) {

        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", phone);
        String sign = SignUtil.getInstance().getSign(map);
        CodeService codeService = retrofit.create(CodeService.class);
        Disposable subscribe = codeService.sendCode(sign, Constants.TOKEN, phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<SendCodeBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<SendCodeBean> sendCodeBeanApiResponse) throws Exception {
                        iModel.onComplete(sendCodeBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        iModel.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }

    //更换手机号
    public void changePhone(String phone, String code, IModelImpl<ApiResponse<ChangePhoneBean>, ChangePhoneBean> iModel) {

        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", phone);
        map.put("code", code);

        HashMap<String, String> param = new HashMap<>();
        param.put("mobile", phone);
        param.put("code", code);

        String sign = SignUtil.getInstance().getSign(map);
        ChangePhoneService codeService = retrofit.create(ChangePhoneService.class);
        Disposable subscribe = codeService.changePhone(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<ChangePhoneBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<ChangePhoneBean> sendCodeBeanApiResponse) throws Exception {
                        iModel.onComplete(sendCodeBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        iModel.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }
}
