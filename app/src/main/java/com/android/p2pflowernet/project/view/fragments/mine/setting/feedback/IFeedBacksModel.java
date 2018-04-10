package com.android.p2pflowernet.project.view.fragments.mine.setting.feedback;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.FeedBackService;
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
 * Created by caishen on 2017/11/24.
 * by--意见反馈的数据层
 */

public class IFeedBacksModel implements IModel {

    private final Retrofit retrofit;
    private final CompositeDisposable compositeDisposable;

    @Override
    public void cancel() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    public IFeedBacksModel() {

        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    //添加意见反馈的接口
    public void addFeedBack(String type, String content, IModelImpl<ApiResponse<String>, String> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("type", type);
        map.put("content", content);

        HashMap<String, String> param = new HashMap<>();
        param.put("type", type);
        param.put("content", content);

        String sign = SignUtil.getInstance().getSign(map);
        FeedBackService codeService = retrofit.create(FeedBackService.class);
        Disposable subscribe = codeService.addFeedBack(sign, Constants.TOKEN, param)
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

                        if (throwable.getMessage().toString().equals("添加成功")) {
                            listener.onError("0", throwable.getMessage());
                        } else {
                            listener.onError("-1", throwable.getMessage());
                        }
                    }
                });
        compositeDisposable.add(subscribe);
    }
}
