package com.android.p2pflowernet.project.o2omain.fragment.index.groupbuying.shopdetails.submitorder;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.GroupPutOrderBean;
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
 * 提交订单数据层
 */

public class ISubmitModel implements IModel {
    private Retrofit mRetrofit;
    private CompositeDisposable compositeDisposable;

    public ISubmitModel() {

        mRetrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    //获取团购数量
    public void getGroupNum(int merch_id, int group_id, final IModelImpl<ApiResponse<String>, String> listener) {
        HashMap<String, String> map = new HashMap<>();
        //商家ID
        map.put("merch_id", String.valueOf(merch_id));
        //团购ID
        map.put("group_id", String.valueOf(group_id));

        HashMap<String, String> param = new HashMap<>();
        param.put("merch_id", String.valueOf(merch_id));
        param.put("group_id", String.valueOf(group_id));

        String sign = SignUtil.getInstance().getSign(map);
        GroupBuyingService service = mRetrofit.create(GroupBuyingService.class);
        Disposable subscribe = service.getGroupNum(sign,Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<String>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<String> groupResidueNumBeanApiResponse) throws Exception {
                        listener.onComplete(groupResidueNumBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }

    //团购提交订单
    public void submitOrder(int merch_id, int group_id,int num, final IModelImpl<ApiResponse<GroupPutOrderBean>, GroupPutOrderBean> listener) {
        HashMap<String, String> map = new HashMap<>();
        //商家ID
        map.put("merch_id", String.valueOf(merch_id));
        //团购ID
        map.put("group_id", String.valueOf(group_id));
        //购买数量
        map.put("num", String.valueOf(num));

        HashMap<String, String> param = new HashMap<>();
        param.put("merch_id", String.valueOf(merch_id));
        param.put("group_id", String.valueOf(group_id));
        param.put("num", String.valueOf(num));

        String sign = SignUtil.getInstance().getSign(map);
        GroupBuyingService service = mRetrofit.create(GroupBuyingService.class);
        Disposable subscribe = service.submitOrder(sign,Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<GroupPutOrderBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<GroupPutOrderBean> groupPutOrderBeanApiResponse) throws Exception {
                        listener.onComplete(groupPutOrderBeanApiResponse);
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