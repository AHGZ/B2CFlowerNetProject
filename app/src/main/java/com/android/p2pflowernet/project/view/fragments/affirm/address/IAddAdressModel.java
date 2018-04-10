package com.android.p2pflowernet.project.view.fragments.affirm.address;

import com.android.p2pflowernet.project.constant.Constants;
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
 * by--添加收货地址的数据层
 */

public class IAddAdressModel implements IModel {

    private final Retrofit retrofit;
    private final CompositeDisposable compositeDisposable;

    @Override
    public void cancel() {

        if (compositeDisposable != null) {

            compositeDisposable.dispose();
        }
    }

    public IAddAdressModel() {

        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    //添加/修改收货地址
    public void addUpdateAdress(String id, String name, String gettelephone, String provinceId,
                                String cityId, String location, String countyId,
                                String getaddress, String isDefault,
                                IModelImpl<ApiResponse<String>, String> listener) {

        HashMap<String, String> map = new HashMap<>();

        map.put("id", id);
        map.put("name", name);
        map.put("telephone", gettelephone);
        map.put("province_id", provinceId);
        map.put("city_id", cityId);
        map.put("district_id", countyId);
        map.put("location", location);
        map.put("address", getaddress);
        map.put("is_default", isDefault);

        HashMap<String, String> param = new HashMap<>();
        param.put("name", name);
        param.put("telephone", gettelephone);
        param.put("province_id", provinceId);
        param.put("city_id", cityId);
        param.put("district_id", countyId);
        param.put("location", location);
        param.put("address", getaddress);
        param.put("is_default", isDefault);
        param.put("id", id);

        String sign = SignUtil.getInstance().getSign(map);
        AdressMangerService codeService = retrofit.create(AdressMangerService.class);
        Disposable subscribe = codeService.addUpdateAdress(sign, Constants.TOKEN, param)
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

                        if (throwable.getMessage().toString().equals("操作成功")) {

                            listener.onError("0", throwable.getMessage());
                        } else {

                            listener.onError("-1", throwable.getMessage());
                        }
                    }
                });
        compositeDisposable.add(subscribe);
    }
}
