package com.android.p2pflowernet.project.view.fragments.register.setpwd;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.DataBean;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.CityDataService;
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
 * description:
 */
public class CityDataModel implements IModel {
    private Retrofit mRetrofit;
    private CompositeDisposable compositeDisposable;

    public CityDataModel() {

        mRetrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    public void getCityDatas(final IModelImpl<ApiResponse<DataBean>, DataBean> listener) {
        final HashMap<String, String> map = new HashMap<>();
        map.put("rts", Constants.RTS);
        HashMap<String, String> param = new HashMap<>();
        param.put("rts", Constants.RTS);

        String sign = SignUtil.getInstance().getSign(map);
        CityDataService service = mRetrofit.create(CityDataService.class);
        Disposable disposable = service.setCity(sign, "", param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<DataBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<DataBean> dataBean) throws Exception {
                        listener.onComplete(dataBean);
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
