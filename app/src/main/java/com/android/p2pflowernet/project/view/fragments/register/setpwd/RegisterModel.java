package com.android.p2pflowernet.project.view.fragments.register.setpwd;

import com.android.p2pflowernet.project.entity.UserInfo;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.RegisterService;
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
 * author: zhangpeisen
 * created on: 2017/11/13 上午11:48
 * description: 注册数据层
 */
public class RegisterModel implements IModel {
    private Retrofit mRetrofit;
    private CompositeDisposable compositeDisposable;

    public RegisterModel() {

        mRetrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    /**
     * @throws
     * @描述:
     * @方法名: register
     * @返回类型 void
     * @创建人 zhangpeisen
     * @创建时间 2017/11/14 下午4:21
     * @修改人 zhangpeisen
     * @修改时间 2017/11/14 下午4:21
     * @修改备注
     */
    public void register(String mobile, String pwd, String invite_code, int region, final IModelImpl<ApiResponse<UserInfo>, UserInfo> listener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("pwd", pwd);
        map.put("invite_code", invite_code);
        map.put("region", String.valueOf(region));

        HashMap<String, String> param = new HashMap<>();
        param.put("mobile", mobile);
        param.put("pwd", pwd);
        param.put("invite_code", invite_code);
        param.put("region", String.valueOf(region));

        String sign = SignUtil.getInstance().getSign(map);
        RegisterService service = mRetrofit.create(RegisterService.class);
        Disposable disposable = service.register(sign,"",param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<UserInfo>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<UserInfo> userInfoApiResponse) throws Exception {
                        listener.onComplete(userInfoApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void cancel() {
        if (compositeDisposable != null)
            compositeDisposable.dispose();
    }
}
