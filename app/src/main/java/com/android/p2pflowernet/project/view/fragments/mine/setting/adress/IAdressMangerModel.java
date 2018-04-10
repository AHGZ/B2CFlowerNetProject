package com.android.p2pflowernet.project.view.fragments.mine.setting.adress;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.AdressMangerBean;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.AdressMangerService;
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
 * by--地址管理的数据层
 */

public class IAdressMangerModel implements IModel {

    private final Retrofit retrofit;
    private final CompositeDisposable compositeDisposable;

    @Override
    public void cancel() {

        if (compositeDisposable != null) {

            compositeDisposable.dispose();
        }
    }

    public IAdressMangerModel() {

        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    //获取地址列表
    public void getUserAddressList(IModelImpl<ApiResponse<AdressMangerBean>, AdressMangerBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        HashMap<String, String> param = new HashMap<>();
        String sign = SignUtil.getInstance().getSign(map);
        AdressMangerService codeService = retrofit.create(AdressMangerService.class);
        Disposable subscribe = codeService.getAdressList(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<AdressMangerBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<AdressMangerBean> checkMobileBeanApiResponse) throws Exception {
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

    //删除地址
    public void deleteAddress(String adressId, IModelImpl<ApiResponse<String>, String> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("ids", adressId);

        String sign = SignUtil.getInstance().getSign(map);
        HashMap<String, String> param = new HashMap<>();
        param.put("ids", adressId);

        AdressMangerService codeService = retrofit.create(AdressMangerService.class);
        Disposable subscribe = codeService.deleteAdress(sign, Constants.TOKEN, param)
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

                        if (throwable.getMessage().toString().equals("删除成功")) {
                            listener.onError("0", throwable.getMessage());
                        } else {
                            listener.onError("-1", throwable.getMessage());
                        }
                    }
                });
        compositeDisposable.add(subscribe);
    }
}
