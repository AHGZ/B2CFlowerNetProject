package com.android.p2pflowernet.project.o2omain.fragment.mine.group_take_out.to_evaluate;

import com.android.p2pflowernet.project.constant.Constants;
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
 * Created by zhangkun on 2018/1/22.
 */

public class GroupTakeOutToEvaluateModel implements IModel {
    private Retrofit retrofit;
    private CompositeDisposable compositeDisposable;

    public GroupTakeOutToEvaluateModel() {
        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void cancel() {

    }

    //提交评论
    public void toEvaluate(int merch_id, int group_id, int order_num, int score,
                           String content, int is_anon, String imgs, final IModelImpl<ApiResponse<String>,String> listener){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("merch_id",merch_id + "");
        hashMap.put("group_id",group_id + "");
        hashMap.put("order_num",order_num + "");
        hashMap.put("score",score + "");
        hashMap.put("content",content);
        hashMap.put("is_anon",is_anon + "");
        hashMap.put("imgs",imgs);

        HashMap<String,String> parm = new HashMap<>();
        parm.put("merch_id",merch_id + "");
        parm.put("group_id",group_id + "");
        parm.put("order_num",order_num + "");
        parm.put("score",score + "");
        parm.put("content",content);
        parm.put("is_anon",is_anon + "");
        parm.put("imgs",imgs);

        String sign = SignUtil.getInstance().getSign(hashMap);
        ToEvaluateService service = retrofit.create(ToEvaluateService.class);
        Disposable disposable = service.groupEvaluate(sign, Constants.TOKEN,parm)
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
        compositeDisposable.add(disposable);
    }
}
