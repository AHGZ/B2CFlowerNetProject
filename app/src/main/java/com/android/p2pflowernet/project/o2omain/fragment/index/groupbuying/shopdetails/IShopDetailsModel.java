package com.android.p2pflowernet.project.o2omain.fragment.index.groupbuying.shopdetails;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.GroupBuyingBean;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.GroupBuyingService;
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
 * Created by heguozhong on 2018/1/17/004.
 * 团购商品详情数据层
 */

public class IShopDetailsModel implements IModel {
    private Retrofit mRetrofit;
    private CompositeDisposable compositeDisposable;

    public IShopDetailsModel() {

        mRetrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    //查看团购详情
    public void getGroupBuyingDetail(int merch_id, int group_id, String latitude, String longitude, final IModelImpl<ApiResponse<GroupBuyingBean>, GroupBuyingBean> listener) {
        HashMap<String, String> map = new HashMap<>();
        //商家ID
        map.put("merch_id", String.valueOf(merch_id));
        //团购ID
        map.put("group_id", String.valueOf(group_id));
        //当前纬度
        map.put("latitude", latitude);
        //当前经度
        map.put("longitude",longitude);

        HashMap<String, String> param = new HashMap<>();
        param.put("merch_id", String.valueOf(merch_id));
        param.put("group_id", String.valueOf(group_id));
        param.put("latitude", String.valueOf(latitude));
        param.put("longitude", String.valueOf(longitude));

        String sign = SignUtil.getInstance().getSign(map);
        GroupBuyingService service = mRetrofit.create(GroupBuyingService.class);
        Disposable subscribe = service.getGroupBuyingDetail(sign,Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<GroupBuyingBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<GroupBuyingBean> groupBuyingBeanApiResponse) throws Exception {
                        listener.onComplete(groupBuyingBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }

    @Override
    public void cancel() {
        if (compositeDisposable != null)
            compositeDisposable.dispose();
    }
}