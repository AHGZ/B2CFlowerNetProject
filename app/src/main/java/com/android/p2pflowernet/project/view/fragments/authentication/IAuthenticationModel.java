package com.android.p2pflowernet.project.view.fragments.authentication;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.AuthenticationService;
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
 * Created by zhangkun on 2018/3/12.
 */

public class IAuthenticationModel implements IModel {

    private Retrofit retrofit;
    private CompositeDisposable compositeDisposable;

    public IAuthenticationModel() {
        this.retrofit = RetrofitUtils.getInstance();
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void cancel() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    public void gotoAuthentication(String realname,String id_number,String bank_num,String bank_name, final IModelImpl<ApiResponse<String>, String> listener) {
        HashMap<String, String> map = new HashMap<>();
        // 生成签名
        map.put("realname", realname);
        map.put("id_number", id_number);
        map.put("bank_num", bank_num);
        map.put("bank_name", bank_name);

        HashMap<String, String> param = new HashMap<>();
        // 传参
        param.put("realname", realname);
        param.put("id_number", id_number);
        param.put("bank_num", bank_num);
        param.put("bank_name", bank_name);
        String sign = SignUtil.getInstance().getSign(map);
        AuthenticationService service = retrofit.create(AuthenticationService.class);
        Disposable subscribe = service.authentication(sign, Constants.TOKEN, param)
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
