package com.android.p2pflowernet.project.view.fragments.updatepwd;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.CheckMobileBean;
import com.android.p2pflowernet.project.entity.SendCodeBean;
import com.android.p2pflowernet.project.entity.UpdatePwd;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.CodeService;
import com.android.p2pflowernet.project.service.RegisterService;
import com.android.p2pflowernet.project.utils.MD5Utils;
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
 * by--忘记密码传输层
 */

public class UpdatePwdModel implements IModel {


    private final Retrofit retrofit;
    private final CompositeDisposable compositeDisposable;

    @Override
    public void cancel() {

        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    public UpdatePwdModel() {

        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    /**
     * 忘记密码
     *
     * @param pwd
     * @param phone
     */
    public void updateModel(String pwd, String phone, String code, IModelImpl<ApiResponse<UpdatePwd>, UpdatePwd> listener) {

        HashMap<String, String> map = new HashMap<>();
        HashMap<String, String> param = new HashMap<>();
        param.put("mobile", phone);
        param.put("pwd", MD5Utils.MD5To32(pwd));
        param.put("code", code);

        map.put("mobile", phone);
        map.put("pwd", MD5Utils.MD5To32(pwd));
        map.put("code", code);
        String sign = SignUtil.getInstance().getSign(map);
        RegisterService registerService = retrofit.create(RegisterService.class);
        Disposable subscribe = registerService.updatePwd(sign,Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<UpdatePwd>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<UpdatePwd> sendCodeBeanApiResponse) throws Exception {
                        listener.onComplete(sendCodeBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                        if (throwable.getMessage().toString().equals("重置成功")) {

                            listener.onError("0", throwable.getMessage());
                        } else {
                            listener.onError("-1", throwable.getMessage());
                        }
                    }
                });
        compositeDisposable.add(subscribe);
    }

    /**
     * 发送验证码
     *
     * @param phone
     * @param listener
     */
    public void CheckMobile(String phone, IModelImpl<ApiResponse<CheckMobileBean>, CheckMobileBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", phone);
        String sign = SignUtil.getInstance().getSign(map);
        CodeService codeService = retrofit.create(CodeService.class);
        Disposable subscribe = codeService.checkMobile(sign,Constants.TOKEN, phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<CheckMobileBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<CheckMobileBean> sendCodeBeanApiResponse) throws Exception {
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

    //接收到发送的验证码
    public void sendCode(String phone, IModelImpl<ApiResponse<SendCodeBean>, SendCodeBean> listener) {

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
