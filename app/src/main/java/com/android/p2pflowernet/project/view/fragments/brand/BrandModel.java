package com.android.p2pflowernet.project.view.fragments.brand;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.AllSortBean;
import com.android.p2pflowernet.project.entity.BrandClassBean;
import com.android.p2pflowernet.project.entity.BrandScendBean;
import com.android.p2pflowernet.project.entity.BrandSortBean;
import com.android.p2pflowernet.project.entity.ClassifBean;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.BrandService;
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
 * author: zhangpeisen
 * created on: 2017/12/5 上午9:22
 * description:商品分类数据层
 */
public class BrandModel implements IModel {

    private Retrofit mRetrofit;
    private CompositeDisposable compositeDisposable;

    public BrandModel() {

        mRetrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    /**
     * @throws
     * @描述:商品分类一级
     * @方法名:
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/5 上午9:28
     * @修改人 zhangpeisen
     * @修改时间 2017/12/5 上午9:28
     * @修改备注
     */
    public void firstGoodsList(final IModelImpl<ApiResponse<BrandClassBean>, BrandClassBean> listener) {
        HashMap<String, String> map = new HashMap<>();
        String sign = SignUtil.getInstance().getSign(map);
        BrandService service = mRetrofit.create(BrandService.class);
        Disposable subscribe = service.goodscatetj(sign, Constants.TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<BrandClassBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<BrandClassBean> checkMobileBeanApiResponse) throws Exception {
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
     * @描述:商品分类二级
     * @方法名:
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/5 上午9:28
     * @修改人 zhangpeisen
     * @修改时间 2017/12/5 上午9:28
     * @修改备注
     */
    public void scendGoodsList(String cate_id, final IModelImpl<ApiResponse<BrandScendBean>, BrandScendBean> listener) {
        HashMap<String, String> map = new HashMap<>();
        // 生成签名
        map.put("cate_id", cate_id);
        HashMap<String, String> param = new HashMap<>();
        // 传参
        param.put("cate_id", cate_id);
        String sign = SignUtil.getInstance().getSign(map);
        BrandService service = mRetrofit.create(BrandService.class);
        Disposable subscribe = service.goodcatelist(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<BrandScendBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<BrandScendBean> checkMobileBeanApiResponse) throws Exception {
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
     * @param cate_id    商品分类id
     * @param is_new     1为新品0为全部
     * @param brand      品牌id以 ，隔开(1,2,3)
     * @param zt         状态（综合,销售,价格）
     * @param sale_price 价格区间
     * @param pages      分页
     * @param order      价格规格
     * @throws
     * @描述:商品筛选
     * @方法名:
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/5 上午9:28
     * @修改人 zhangpeisen
     * @修改时间 2017/12/5 上午9:28
     * @修改备注
     */
    public void screenlist(String cate_id, String is_new, String brand, String zt, String sale_price, int pages, String order, final IModelImpl<ApiResponse<ClassifBean>, ClassifBean> listener) {
        HashMap<String, String> map = new HashMap<>();
        // 生成签名
        map.put("cate_id", cate_id);
        map.put("is_new", is_new);
        map.put("brand", brand);
        map.put("zt", zt);
        map.put("sale_price", sale_price);
        map.put("pages", String.valueOf(pages));
        map.put("order", order);
        HashMap<String, String> param = new HashMap<>();
        // 传参
        param.put("cate_id", cate_id);
        param.put("is_new", is_new);
        param.put("brand", brand);
        param.put("zt", zt);
        param.put("sale_price", sale_price);
        param.put("pages", String.valueOf(pages));
        param.put("order", order);
        String sign = SignUtil.getInstance().getSign(map);
        BrandService service = mRetrofit.create(BrandService.class);
        Disposable subscribe = service.screenlist(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<ClassifBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<ClassifBean> checkMobileBeanApiResponse) throws Exception {
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

    public void clickfilter(String cate_id, final IModelImpl<ApiResponse<BrandSortBean>, BrandSortBean> listener) {
        HashMap<String, String> map = new HashMap<>();
        // 生成签名
        map.put("cate_id", cate_id);
        HashMap<String, String> param = new HashMap<>();
        // 传参
        param.put("cate_id", cate_id);
        String sign = SignUtil.getInstance().getSign(map);
        BrandService service = mRetrofit.create(BrandService.class);
        Disposable subscribe = service.clickfilter(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<BrandSortBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<BrandSortBean> checkMobileBeanApiResponse) throws Exception {
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

    public void morebrand(String cate_id, final IModelImpl<ApiResponse<AllSortBean>, AllSortBean> listener) {
        HashMap<String, String> map = new HashMap<>();
        // 生成签名
        map.put("cate_id", cate_id);
        HashMap<String, String> param = new HashMap<>();
        // 传参
        param.put("cate_id", cate_id);
        String sign = SignUtil.getInstance().getSign(map);
        BrandService service = mRetrofit.create(BrandService.class);
        Disposable subscribe = service.morebrand(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<AllSortBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<AllSortBean> checkMobileBeanApiResponse) throws Exception {
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

    //关键词搜索数据列表
    public void getSerChList(String searchName, String scendCateid, String isNew, String brand, String zt, String salePrice,
                             int pages, String order, final IModelImpl<ApiResponse<ClassifBean>, ClassifBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        // 生成签名
        map.put("cate_id", scendCateid);
        map.put("is_new", isNew);
        map.put("brand", brand);
        map.put("zt", zt);
        map.put("sale_price", salePrice);
        map.put("pages", String.valueOf(pages));
        map.put("order", order);
        map.put("name", searchName);
        HashMap<String, String> param = new HashMap<>();

        // 传参
        param.put("cate_id", scendCateid);
        param.put("is_new", isNew);
        param.put("brand", brand);
        param.put("zt", zt);
        param.put("sale_price", salePrice);
        param.put("pages", String.valueOf(pages));
        param.put("order", order);
        param.put("name", searchName);
        String sign = SignUtil.getInstance().getSign(map);
        BrandService service = mRetrofit.create(BrandService.class);
        Disposable subscribe = service.getSerChList(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<ClassifBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<ClassifBean> checkMobileBeanApiResponse) throws Exception {
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
}
