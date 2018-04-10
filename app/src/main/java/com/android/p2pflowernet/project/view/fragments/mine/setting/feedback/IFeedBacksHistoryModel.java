package com.android.p2pflowernet.project.view.fragments.mine.setting.feedback;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.FeedBackBean;
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
 * by--
 */

public class IFeedBacksHistoryModel implements IModel {

    private final Retrofit retrofit;
    private final CompositeDisposable compositeDisposable;

    @Override
    public void cancel() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    public IFeedBacksHistoryModel() {

        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    //获取意见反馈列表数据
    public void getFeedBackList(int page, IModelImpl<ApiResponse<FeedBackBean>, FeedBackBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("page", "1");

        HashMap<String, String> param = new HashMap<>();
        param.put("page", "1");

        String sign = SignUtil.getInstance().getSign(map);
        FeedBackService codeService = retrofit.create(FeedBackService.class);
        Disposable subscribe = codeService.getFeedBacks(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<FeedBackBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<FeedBackBean> checkMobileBeanApiResponse) throws Exception {
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
