package com.android.p2pflowernet.project.view.fragments.trade;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.AddShopBean;
import com.android.p2pflowernet.project.entity.GoodsInventoryCount;
import com.android.p2pflowernet.project.entity.ShopCarBean;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.TradeService;
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
 * Created by caishen on 2017/12/7.
 * by--购物车数据层
 */

public class TradeModel implements IModel {

    private final Retrofit retrofit;
    private final CompositeDisposable compositeDisposable;


    public TradeModel() {

        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    /**
     * @描述:
     * @方法名:  获取购物车数据
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/7 下午5:10
     * @修改人 zhangpeisen
     * @修改时间 2017/12/7 下午5:10
     * @修改备注
     * @since
     * @throws
     */
    public void getShopCars(final IModelImpl<ApiResponse<ShopCarBean>, ShopCarBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        String sign = SignUtil.getInstance().getSign(map);
        TradeService codeService = retrofit.create(TradeService.class);
        Disposable subscribe = codeService.getShopCars(sign, Constants.TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<ShopCarBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<ShopCarBean> sendCodeBeanApiResponse) throws Exception {
                        listener.onComplete(sendCodeBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }


    //更改库存数量
    public void editCarCount(String cartId, String goodId, String specId, int num, String isChoose,
                             final IModelImpl<ApiResponse<GoodsInventoryCount>, GoodsInventoryCount> listener) {


        HashMap<String, String> map = new HashMap<>();
        map.put("cart_id", cartId);
        map.put("goods_id", goodId);
        map.put("spec_id", specId);
        map.put("num", num + "");
        map.put("is_sel", isChoose);

        HashMap<String, String> param = new HashMap<>();
        param.put("cart_id", cartId);
        param.put("goods_id", goodId);
        param.put("spec_id", specId);
        param.put("num", num + "");
        param.put("is_sel", isChoose);


        String sign = SignUtil.getInstance().getSign(map);
        TradeService codeService = retrofit.create(TradeService.class);
        Disposable subscribe = codeService.editCarCount(sign,Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<GoodsInventoryCount>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<GoodsInventoryCount> sendCodeBeanApiResponse) throws Exception {
                        listener.onComplete(sendCodeBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }

    //删除购物车中的商品
    public void delCarGoods(String cartId, final IModelImpl<ApiResponse<String>, String> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("cart_id", cartId);

        HashMap<String, String> param = new HashMap<>();
        param.put("cart_id", cartId);

        String sign = SignUtil.getInstance().getSign(map);
        TradeService codeService = retrofit.create(TradeService.class);
        Disposable subscribe = codeService.delCarGoods(sign,Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<String>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<String> sendCodeBeanApiResponse) throws Exception {
                        listener.onComplete(sendCodeBeanApiResponse);
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
     * @描述: 添加购物车
     * @方法名:addShopCars
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/7 下午5:10
     * @修改人 zhangpeisen
     * @修改时间 2017/12/7 下午5:10
     * @修改备注
     */
    public void addShopCars(String goodsId, String sepcId, String count, final IModelImpl<ApiResponse<AddShopBean>, AddShopBean> listener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("goods_id", goodsId);
        map.put("sepc_id", sepcId);
        map.put("count", count);
        HashMap<String, String> params = new HashMap<>();
        params.put("goods_id", goodsId);
        params.put("goods_id", goodsId);
        params.put("sepc_id", sepcId);
        params.put("count", count);
        String sign = SignUtil.getInstance().getSign(map);
        TradeService tradeService = retrofit.create(TradeService.class);
        Disposable subscribe = tradeService.addShopCar(sign,Constants.TOKEN, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<AddShopBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<AddShopBean> sendCodeBeanApiResponse) throws Exception {
                        listener.onComplete(sendCodeBeanApiResponse);
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

        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    //设置购物车的选中状态
    public void setCarGoods(String cartId, String isChoose, final IModelImpl<ApiResponse<String>, String> listener) {


        HashMap<String, String> map = new HashMap<>();
        map.put("cart_id", cartId);
        map.put("is_sel", isChoose);

        HashMap<String, String> param = new HashMap<>();
        param.put("cart_id", cartId);
        param.put("is_sel", isChoose);

        String sign = SignUtil.getInstance().getSign(map);
        TradeService codeService = retrofit.create(TradeService.class);
        Disposable subscribe = codeService.setCarGoods(sign,Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<String>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<String> sendCodeBeanApiResponse) throws Exception {
                        listener.onComplete(sendCodeBeanApiResponse);
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
