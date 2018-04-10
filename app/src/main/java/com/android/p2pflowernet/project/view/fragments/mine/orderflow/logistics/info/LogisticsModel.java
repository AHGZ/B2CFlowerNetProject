package com.android.p2pflowernet.project.view.fragments.mine.orderflow.logistics.info;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.LogisticsDetailBean;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.LogisticService;
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
 * @描述:完善物流信息数据层
 * @创建人：zhangpeisen
 * @创建时间：2017/12/22 下午3:32
 * @修改人：zhangpeisen
 * @修改时间：2017/12/22 下午3:32
 * @修改备注：
 * @throws
 */
public class LogisticsModel implements IModel {

    private Retrofit mRetrofit;
    private CompositeDisposable compositeDisposable;

    public LogisticsModel() {
        mRetrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    /**
     * @throws
     * @描述:获取商家收货地址
     * @方法名:
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/16 下午3:02
     * @修改人 zhangpeisen
     * @修改时间 2017/12/16 下午3:02
     * @修改备注
     */
    public void manufacaddressinfo(String manufac_id, final IModelImpl<ApiResponse<String>, String> listener) {
        HashMap<String, String> map = new HashMap<>();
        // 生成sign
        map.put("manufac_id", manufac_id);
        HashMap<String, String> param = new HashMap<>();
        // 传参
        param.put("manufac_id", manufac_id);
        String sign = SignUtil.getInstance().getSign(map);
        LogisticService logisticService = mRetrofit.create(LogisticService.class);
        Disposable subscribe = logisticService.manufacaddressinfo(sign, Constants.TOKEN, param)
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
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }


    /**
     * @throws
     * @描述:
     * @方法名: 完善物流
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/16 下午2:51
     * @修改人 zhangpeisen
     * @修改时间 2017/12/16 下午2:51
     * @修改备注
     */
    public void perfectExpress(String refund_id, String waybill_num, String express_name, final IModelImpl<ApiResponse<String>, String> listener) {
        HashMap<String, String> map = new HashMap<>();
        // 生成sign
        map.put("refund_id", refund_id);
        map.put("waybill_num", waybill_num);
        map.put("express_name", express_name);
        HashMap<String, String> param = new HashMap<>();
        // 传参
        param.put("refund_id", refund_id);
        param.put("waybill_num", waybill_num);
        param.put("express_name", express_name);
        String sign = SignUtil.getInstance().getSign(map);
        LogisticService logisticService = mRetrofit.create(LogisticService.class);
        Disposable subscribe = logisticService.perfectexpress(sign, Constants.TOKEN, param)
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
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }

    /**
     * @throws
     * @描述: 物流查询
     * @方法名:
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/22 下午4:00
     * @修改人 zhangpeisen
     * @修改时间 2017/12/22 下午4:00
     * @修改备注
     */
    public void querydelivery(String order_num, String express_id, String waybill_num, String state, final IModelImpl<ApiResponse<LogisticsDetailBean>, LogisticsDetailBean> listener) {
        HashMap<String, String> map = new HashMap<>();
        // 生成sign
        map.put("order_num", order_num);
        map.put("express_id", express_id);
        map.put("waybill_num", waybill_num);
        map.put("state", state);
        HashMap<String, String> param = new HashMap<>();
        // 传参
        param.put("order_num", order_num);
        param.put("express_id", express_id);
        param.put("waybill_num", waybill_num);
        param.put("state", state);
        String sign = SignUtil.getInstance().getSign(map);
        LogisticService logisticService = mRetrofit.create(LogisticService.class);
        Disposable subscribe = logisticService.querydelivery(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<LogisticsDetailBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<LogisticsDetailBean> checkMobileBeanApiResponse) throws Exception {
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

    @Override
    public void cancel() {
        if (compositeDisposable != null)
            compositeDisposable.dispose();
    }

}
