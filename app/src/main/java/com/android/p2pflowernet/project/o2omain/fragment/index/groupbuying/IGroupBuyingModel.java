package com.android.p2pflowernet.project.o2omain.fragment.index.groupbuying;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.GroupHomeBean;
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
 * Created by Administrator on 2018/1/27.
 */

public class IGroupBuyingModel implements IModel {
    private Retrofit retrofit;
    private CompositeDisposable compositeDisposable;

    public IGroupBuyingModel( ) {
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void cancel() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    //获取团购首页数据
    public void getGroupHomeData(int pages, int zt, int state, String city, String latitude, String longitude,
                                 int yuyue, int holiday_usable, int weekend_usable, int is_new, String fit_type,
                                 int liebie, int district_id, final IModelImpl<ApiResponse<GroupHomeBean>,GroupHomeBean> listener){

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("pages",pages + "");
        hashMap.put("zt",zt + "");
        hashMap.put("state",state + "");
        hashMap.put("city",city);
        hashMap.put("latitude",latitude);
        hashMap.put("longitude",longitude);
        hashMap.put("yuyue",yuyue + "");
        hashMap.put("holiday_usable",holiday_usable + "");
        hashMap.put("weekend_usable",weekend_usable + "");
        hashMap.put("is_new",is_new + "");
        hashMap.put("fit_type",fit_type);
        hashMap.put("liebie",liebie + "");
        hashMap.put("district_id",district_id + "");

        HashMap<String,String> param = new HashMap<>();
        param.put("pages",pages + "");
        param.put("zt",zt + "");
        param.put("state",state + "");
        param.put("city",city);
        param.put("latitude",latitude);
        param.put("longitude",longitude);
        param.put("yuyue",yuyue + "");
        param.put("holiday_usable",holiday_usable + "");
        param.put("weekend_usable",weekend_usable + "");
        param.put("is_new",is_new + "");
        param.put("fit_type",fit_type);
        param.put("liebie",liebie + "");
        param.put("district_id",district_id + "");

        String sign = SignUtil.getInstance().getSign(hashMap);
        O2oService o2oService = retrofit.create(O2oService.class);
        Disposable disposable = o2oService.getGroupHomeData(sign, Constants.TOKEN,param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<GroupHomeBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<GroupHomeBean> string) throws Exception {
                        listener.onComplete(string);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(disposable);

    }
    //搜索团购首页数据
    public void searchGroupHomeData(int pages, int zt, int state, String city, String latitude, String longitude,
                                 int yuyue, int holiday_usable, int weekend_usable, int is_new, String fit_type,
                                 int liebie, int district_id,String name, final IModelImpl<ApiResponse<GroupHomeBean>,GroupHomeBean> listener){

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("pages",pages + "");
        hashMap.put("zt",zt + "");
        hashMap.put("state",state + "");
        hashMap.put("city",city);
        hashMap.put("latitude",latitude);
        hashMap.put("longitude",longitude);
        hashMap.put("yuyue",yuyue + "");
        hashMap.put("holiday_usable",holiday_usable + "");
        hashMap.put("weekend_usable",weekend_usable + "");
        hashMap.put("is_new",is_new + "");
        hashMap.put("fit_type",fit_type);
        hashMap.put("liebie",liebie + "");
        hashMap.put("district_id",district_id + "");
        hashMap.put("name",name);

        HashMap<String,String> param = new HashMap<>();
        param.put("pages",pages + "");
        param.put("zt",zt + "");
        param.put("state",state + "");
        param.put("city",city);
        param.put("latitude",latitude);
        param.put("longitude",longitude);
        param.put("yuyue",yuyue + "");
        param.put("holiday_usable",holiday_usable + "");
        param.put("weekend_usable",weekend_usable + "");
        param.put("is_new",is_new + "");
        param.put("fit_type",fit_type);
        param.put("liebie",liebie + "");
        param.put("district_id",district_id + "");
        param.put("name",name);

        String sign = SignUtil.getInstance().getSign(hashMap);
        O2oService o2oService = retrofit.create(O2oService.class);
        Disposable disposable = o2oService.searchGroupHomeData(sign, Constants.TOKEN,param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<GroupHomeBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<GroupHomeBean> string) throws Exception {
                        listener.onComplete(string);
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
