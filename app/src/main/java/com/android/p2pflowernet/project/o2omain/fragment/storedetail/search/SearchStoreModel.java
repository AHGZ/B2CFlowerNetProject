package com.android.p2pflowernet.project.o2omain.fragment.storedetail.search;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.SearchStoreBean;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.O2oService;
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
 * Created by caishen on 2018/2/3.
 * by--
 */

public class SearchStoreModel implements IModel {

    private final Retrofit retrofit;
    private final CompositeDisposable compositeDisposable;

    @Override
    public void cancel() {

        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    public SearchStoreModel() {

        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    public void searchStore(String searchKey, String merchId, String longitude,
                            String latitude, IModelImpl<ApiResponse<SearchStoreBean>, SearchStoreBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("merch_id", merchId);
        map.put("longitude", longitude);
        map.put("latitude", latitude);
        map.put("name", searchKey);

        HashMap<String, String> param = new HashMap<>();
        param.put("merch_id", merchId);
        param.put("longitude", longitude);
        param.put("latitude", latitude);
        param.put("name", searchKey);

        String sign = SignUtil.getInstance().getSign(map);
        O2oService o2oService = retrofit.create(O2oService.class);
        Disposable subscribe = o2oService.searchStore(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<SearchStoreBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<SearchStoreBean> checkMobileBeanApiResponse) throws Exception {
                        listener.onComplete(checkMobileBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                        if (throwable.getMessage().toString().trim().equals("商家休息中！")) {
                            listener.onError("-2", throwable.getMessage());
                        } else {
                            listener.onError("-1", throwable.getMessage());
                        }
                    }
                });
        compositeDisposable.add(subscribe);
    }
}
