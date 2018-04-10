package com.android.p2pflowernet.project.o2omain.fragment.mine.group_take_out.fulldetail;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.GroupFullDetailBean;
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
 * Created by heguozhong on 2018/1/26/026.
 * 团购明细数据层
 */

public class IGroupFullDetailModel implements IModel {

    private final Retrofit retrofit;
    private final CompositeDisposable compositeDisposable;

    @Override
    public void cancel() {

        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    public IGroupFullDetailModel() {

        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();

    }

    //获取团购明细
    public void getGroupOrderDetail(String order_num,String latitude, String longitude, final IModelImpl<ApiResponse<GroupFullDetailBean>, GroupFullDetailBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        //订单编号
//        map.put("order_num", String.valueOf(order_num));
        map.put("order_num",order_num );
        //当前纬度
        map.put("latitude", latitude);
        //当前经度
        map.put("longitude",longitude);

        HashMap<String, String> param = new HashMap<>();
//        param.put("order_num", String.valueOf(order_num));
        param.put("order_num", order_num);
        param.put("latitude", String.valueOf(latitude));
        param.put("longitude", String.valueOf(longitude));

        String sign = SignUtil.getInstance().getSign(map);
        O2oService o2oService = retrofit.create(O2oService.class);
        Disposable subscribe = o2oService.getGroupOrderDetail(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<GroupFullDetailBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<GroupFullDetailBean> groupFullDetailBeanApiResponse) throws Exception {
                        listener.onComplete(groupFullDetailBeanApiResponse);
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
