package com.android.p2pflowernet.project.view.fragments.index.bigdata;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.BigDataMapBean;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.DataService;
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
 * Created by zhangkun on 2018/2/8.
 */

public class INationwideModel implements IModel {
    private Retrofit retrofit;
    private CompositeDisposable compositeDisposable;

    public INationwideModel() {
        this.retrofit = RetrofitUtils.getInstance();
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void cancel() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    //获取地图坐标数据
    public void getMapData(final IModelImpl<ApiResponse<BigDataMapBean>, BigDataMapBean> listener){
        HashMap<String,String> hashMap = new HashMap<>();

        String sign = SignUtil.getInstance().getSign(hashMap);
        DataService dataService = retrofit.create(DataService.class);
        Disposable disposable = dataService.getMapData(sign, Constants.TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<BigDataMapBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<BigDataMapBean> sendCodeBeanApiResponse) throws Exception {
                        listener.onComplete(sendCodeBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(disposable);
    }
}
