package com.android.p2pflowernet.project.view.fragments.goods.info;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.ChangeGsAttrBean;
import com.android.p2pflowernet.project.entity.GoodsAttrBean;
import com.android.p2pflowernet.project.entity.GoodsInfoBean;
import com.android.p2pflowernet.project.entity.ProductParamBean;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.GoodsInfoService;
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
 * @描述: 商品信息数据层
 * @创建人：zhangpeisen
 * @创建时间：2017/12/6 上午9:54
 * @修改人：zhangpeisen
 * @修改时间：2017/12/6 上午9:54
 * @修改备注：
 * @throws
 */
public class GoodsInfoModel implements IModel {

    private Retrofit retrofit;
    private CompositeDisposable compositeDisposable;


    public GoodsInfoModel() {

        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    /**
     * @throws
     * @描述: 获取商品详情
     * @方法名:
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/6 上午9:56
     * @修改人 zhangpeisen
     * @修改时间 2017/12/6 上午9:56
     * @修改备注
     */
    public void getGoodsXq(String goodsId, final IModelImpl<ApiResponse<GoodsInfoBean>, GoodsInfoBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("goods_id", goodsId);

        HashMap<String, String> param = new HashMap<>();
        param.put("goods_id", goodsId);

        String sign = SignUtil.getInstance().getSign(map);
        GoodsInfoService goodsService = retrofit.create(GoodsInfoService.class);
        Disposable subscribe = goodsService.getGoodsXq(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<GoodsInfoBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<GoodsInfoBean> checkMobileBeanApiResponse) throws Exception {
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
     * @方法名: 获取商品产品参数
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/6 上午9:57
     * @修改人 zhangpeisen
     * @修改时间 2017/12/6 上午9:57
     * @修改备注
     */
    public void getGoodsParam(String goodsId, final IModelImpl<ApiResponse<ProductParamBean>, ProductParamBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("goods_id", goodsId);
        HashMap<String, String> param = new HashMap<>();
        param.put("goods_id", goodsId);
        String sign = SignUtil.getInstance().getSign(map);
        GoodsInfoService goodsService = retrofit.create(GoodsInfoService.class);
        Disposable subscribe = goodsService.getGoodsParam(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<ProductParamBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<ProductParamBean> checkMobileBeanApiResponse) throws Exception {
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
     * @描述:初始化商品产品规格
     * @方法名:
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/6 下午2:02
     * @修改人 zhangpeisen
     * @修改时间 2017/12/6 下午2:02
     * @修改备注
     */
    public void getGoodsSpec(String goodsId, String sepcId, final IModelImpl<ApiResponse<GoodsAttrBean>, GoodsAttrBean> listener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("goods_id", goodsId);
        map.put("spec_id", sepcId);
        HashMap<String, String> param = new HashMap<>();
        param.put("goods_id", goodsId);
        param.put("spec_id", sepcId);
        String sign = SignUtil.getInstance().getSign(map);
        GoodsInfoService goodsService = retrofit.create(GoodsInfoService.class);
        Disposable subscribe = goodsService.getGoodsSpec(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<GoodsAttrBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<GoodsAttrBean> checkMobileBeanApiResponse) throws Exception {
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
     * @描述:获取商品指定规格信息
     * @方法名:
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/6 下午2:15
     * @修改人 zhangpeisen
     * @修改时间 2017/12/6 下午2:15
     * @修改备注
     */
    public void getGoodsSpecInfo(String goodsId, String opt_id1, String opt_id2, String opt_id3, final IModelImpl<ApiResponse<ChangeGsAttrBean>, ChangeGsAttrBean> listener) {
        HashMap<String, String> map = new HashMap<>();
        // 生成sign
        map.put("goods_id", goodsId);
        map.put("opt_id1", opt_id1);
        map.put("opt_id2", opt_id2);
        map.put("opt_id3", opt_id3);
        HashMap<String, String> param = new HashMap<>();
        // 传参
        param.put("goods_id", goodsId);
        param.put("opt_id1", opt_id1);
        param.put("opt_id2", opt_id2);
        param.put("opt_id3", opt_id3);
        String sign = SignUtil.getInstance().getSign(map);
        GoodsInfoService goodsService = retrofit.create(GoodsInfoService.class);
        Disposable subscribe = goodsService.getGoodsSpecInfo(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<ChangeGsAttrBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<ChangeGsAttrBean> checkMobileBeanApiResponse) throws Exception {
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

        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }
}
