package com.android.p2pflowernet.project.view.fragments.mine.applyfor.partner;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.AddAuthInfoBean;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.ApplyForPartnerService;
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
 * Created by caishen on 2017/11/21.
 * by--完善个人信息的数据层
 */

public class ImproveInfoModel implements IModel {

    private final Retrofit retrofit;
    private final CompositeDisposable compositeDisposable;

    @Override
    public void cancel() {

        if (compositeDisposable != null) {

            compositeDisposable.dispose();
        }
    }

    public ImproveInfoModel() {

        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    //完善个人信息
    public void addAuthInfo(String userId, IModelImpl<ApiResponse<AddAuthInfoBean>, AddAuthInfoBean> listener) {

        HashMap<String, String> map = new HashMap<>();

        map.put("user_id", userId);
        map.put("submit", "0");

        HashMap<String, String> param = new HashMap<>();
        param.put("user_id", userId);
        param.put("submit", "0");

        String sign = SignUtil.getInstance().getSign(map);
        ApplyForPartnerService codeService = retrofit.create(ApplyForPartnerService.class);
        Disposable subscribe = codeService.addAuthInfo(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<AddAuthInfoBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<AddAuthInfoBean> checkMobileBeanApiResponse) throws Exception {
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

    //保存个人信息
    public void addInsuranceInfo(String userId, String maritalState, String address,
                                 String provinceIid, String cityId, String distictIid, String locationAddress,
                                 IModelImpl<ApiResponse<String>, String> listener) {

        HashMap<String, String> map = new HashMap<>();

        map.put("submit", "1");
        map.put("marital_state", maritalState);
        map.put("address", address);
        map.put("province_id", provinceIid);
        map.put("city_id", cityId);
        map.put("distict_id", distictIid);
        map.put("location_address", locationAddress);

        HashMap<String, String> param = new HashMap<>();
        param.put("submit", "1");
        param.put("marital_state", maritalState);
        param.put("address", address);
        param.put("province_id", provinceIid);
        param.put("city_id", cityId);
        param.put("distict_id", distictIid);
        param.put("location_address", locationAddress);

        String sign = SignUtil.getInstance().getSign(map);
        ApplyForPartnerService codeService = retrofit.create(ApplyForPartnerService.class);
        Disposable subscribe = codeService.addInsuranceInfo(sign, Constants.TOKEN, param)
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

                        if (throwable.getMessage().toString().equals("保存成功！")) {

                            listener.onError("0", throwable.getMessage());

                        } else {

                            listener.onError("-1", throwable.getMessage());
                        }
                    }
                });
        compositeDisposable.add(subscribe);
    }
}
