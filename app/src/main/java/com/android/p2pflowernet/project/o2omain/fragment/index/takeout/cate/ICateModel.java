package com.android.p2pflowernet.project.o2omain.fragment.index.takeout.cate;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.TakeCateThreeBean;
import com.android.p2pflowernet.project.entity.TakeCateTwoBean;
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
 * Created by heguozhong on 2018/1/29/029.
 * 美食首页
 */

public class ICateModel implements IModel {
    private Retrofit mRetrofit;
    private CompositeDisposable compositeDisposable;

    public ICateModel() {

        mRetrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    //查看外卖美食三级
    public void getTakeoutCateThree(String state,String pages,String sreen,String latitude,String longitude,int cate_id,int level, final IModelImpl<ApiResponse<TakeCateThreeBean>, TakeCateThreeBean> listener) {
        HashMap<String, String> map = new HashMap<>();
        //状态
        map.put("state", state);
        //页数
        map.put("pages",pages);
        //筛选条件
        map.put("sreen", sreen);
        //当前纬度
        map.put("latitude", latitude);
        //当前经度
        map.put("longitude",longitude);
        //分类id
        map.put("cate_id", String.valueOf(cate_id));
        //级别
        map.put("level", String.valueOf(level));

        HashMap<String, String> param = new HashMap<>();
        param.put("state", state);
        param.put("pages",pages);
        param.put("sreen", sreen);
        param.put("latitude", latitude);
        param.put("longitude",longitude);
        param.put("cate_id", String.valueOf(cate_id));
        param.put("level", String.valueOf(level));

        String sign = SignUtil.getInstance().getSign(map);
        O2oService service = mRetrofit.create(O2oService.class);
        Disposable subscribe = service.get_takeout_cate_three(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<TakeCateThreeBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<TakeCateThreeBean> takeCateThreeBeanApiResponse) throws Exception {
                        listener.onComplete(takeCateThreeBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }

    //查看外卖美食二级
    public void getTakeoutCateTwo(int cate_id, final IModelImpl<ApiResponse<TakeCateTwoBean>, TakeCateTwoBean> listener) {
        HashMap<String, String> map = new HashMap<>();
        //美食id
        map.put("cate_id", String.valueOf(cate_id));

        HashMap<String, String> param = new HashMap<>();
        param.put("cate_id", String.valueOf(cate_id));

        String sign = SignUtil.getInstance().getSign(map);
        O2oService service = mRetrofit.create(O2oService.class);
        Disposable subscribe = service.get_takeout_cate_two(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<TakeCateTwoBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<TakeCateTwoBean> takeCateTwoBeanApiResponse) throws Exception {
                        listener.onComplete(takeCateTwoBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }

    //外卖美食搜索
    public void searchTakeoutCate(String state,String pages,String sreen,String latitude,String longitude,int cate_id,int level,String name, final IModelImpl<ApiResponse<TakeCateThreeBean>, TakeCateThreeBean> listener) {
        HashMap<String, String> map = new HashMap<>();
        //状态
        map.put("state", state);
        //页数
        map.put("pages",pages);
        //筛选条件
        map.put("sreen", sreen);
        //当前纬度
        map.put("latitude", latitude);
        //当前经度
        map.put("longitude",longitude);
        //分类id
        map.put("cate_id", String.valueOf(cate_id));
        //级别
        map.put("level", String.valueOf(level));
        //搜索名称
        map.put("name",name);

        HashMap<String, String> param = new HashMap<>();
        param.put("state", state);
        param.put("pages",pages);
        param.put("sreen", sreen);
        param.put("latitude", latitude);
        param.put("longitude",longitude);
        param.put("cate_id", String.valueOf(cate_id));
        param.put("level", String.valueOf(level));
        param.put("name",name);

        String sign = SignUtil.getInstance().getSign(map);
        O2oService service = mRetrofit.create(O2oService.class);
        Disposable subscribe = service.search_takeout_cate(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<TakeCateThreeBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<TakeCateThreeBean> takeCateThreeBeanApiResponse) throws Exception {
                        listener.onComplete(takeCateThreeBeanApiResponse);
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
