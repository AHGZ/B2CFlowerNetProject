package com.android.p2pflowernet.project.o2omain.fragment.mine.take_out.to_evaluate;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.TakeOutToEvaluateGoodsBean;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.ToEvaluateService;
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
 * Created by zhangkun on 2018/1/20.
 */

public class TakeOutToEvaluateModel implements IModel {

    private Retrofit retrofit;
    private CompositeDisposable compositeDisposable;

    public TakeOutToEvaluateModel(){

        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void cancel() {

    }

    //获取评论页面数据
    public void getEvaluateData(String order_num, IModelImpl<ApiResponse<TakeOutToEvaluateGoodsBean>,
            TakeOutToEvaluateGoodsBean> listener){
        HashMap<String,String> map = new HashMap<>();
        map.put("order_num",order_num);
        HashMap<String,String> param = new HashMap<>();
        param.put("order_num",order_num);
        String sign = SignUtil.getInstance().getSign(map);
        ToEvaluateService service = retrofit.create(ToEvaluateService.class);
        Disposable subscribe = service.getEvaluateData(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<TakeOutToEvaluateGoodsBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<TakeOutToEvaluateGoodsBean> sendCodeBeanApiResponse) throws Exception {
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


    //发布评论
    public void releseEvaluate(String order_num,int distrib_type,int distrib_id,int distrib_score,int merch_score
                                ,String merch_evel_content,String merch_evel_img,String goods_evel,int is_anon
                                ,final IModelImpl<ApiResponse<String>, String> listener){
        HashMap<String,String> map = new HashMap<>();
        map.put("order_num",order_num);
        map.put("distrib_type",distrib_type + "");
        map.put("distrib_id",distrib_id + "");
        map.put("distrib_score",distrib_score + "");
        map.put("merch_score",merch_score + "");
        map.put("merch_evel_content",merch_evel_content);
        map.put("merch_evel_img",merch_evel_img);
        map.put("goods_evel",goods_evel);
        map.put("is_anon",is_anon + "");

        HashMap<String,String> param = new HashMap<>();
        param.put("order_num",order_num);
        param.put("distrib_type",distrib_type + "");
        param.put("distrib_id",distrib_id + "");
        param.put("distrib_score",distrib_score + "");
        param.put("merch_score",merch_score + "");
        param.put("merch_evel_content",merch_evel_content);
        param.put("merch_evel_img",merch_evel_img);
        param.put("goods_evel",goods_evel);
        param.put("is_anon",is_anon + "");

        String sign = SignUtil.getInstance().getSign(map);
        ToEvaluateService service = retrofit.create(ToEvaluateService.class);
        Disposable subscribe = service.takeoutEvaluate(sign, Constants.TOKEN, param)
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
