package com.android.p2pflowernet.project.view.fragments.mine.applyfor.applyforwait;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.ApplyForWaitBean;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.ApplyForService;
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
 * Created by caishen on 2017/11/23.
 * by--待申请的数据层
 */

public class IApplyWaitForModel implements IModel {

    private final Retrofit retrofit;
    private final CompositeDisposable compositeDisposable;

    @Override
    public void cancel() {

        if (compositeDisposable != null) {

            compositeDisposable.dispose();
        }
    }

    public IApplyWaitForModel() {

        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();

    }

    //待申请
    public void waitApplyFor(String userId, IModelImpl<ApiResponse<ApplyForWaitBean>, ApplyForWaitBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        HashMap<String, String> param = new HashMap<>();

        map.put("user_id", userId);
        param.put("user_id", userId);
        String sign = SignUtil.getInstance().getSign(map);
        ApplyForService codeService = retrofit.create(ApplyForService.class);
        Disposable subscribe = codeService.appForWait(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<ApplyForWaitBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<ApplyForWaitBean> checkMobileBeanApiResponse) throws Exception {
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
