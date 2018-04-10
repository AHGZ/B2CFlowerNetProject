package com.android.p2pflowernet.project.view.fragments.forum;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.ForumChannelBean;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.ForumService;
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
 * 数据访问层
 * Created by zhangkun on 2018/1/18.
 */

public class IForumModel implements IModel {
    private Retrofit retrofit;
    private CompositeDisposable compositeDisposable;

    public IForumModel() {
        this.retrofit = RetrofitUtils.getInstance();
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void cancel() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    //获取花返讲堂导航信息
    public void getForumChannel(IModelImpl<ApiResponse<ForumChannelBean> ,ForumChannelBean> listener){
        HashMap<String,String> hashMap = new HashMap<>();
        HashMap<String,String> param = new HashMap<>();

        String sign = SignUtil.getInstance().getSign(hashMap);
        ForumService forumService = retrofit.create(ForumService.class);
        Disposable disposable = forumService.getForumChannel(sign, Constants.TOKEN,param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<ForumChannelBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<ForumChannelBean> sendCodeBeanApiResponse) throws Exception {
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
