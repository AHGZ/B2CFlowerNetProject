package com.android.p2pflowernet.project.view.fragments.forum.textdetails;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.ForumDetailsBean;
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
 * Created by zhangkun on 2018/1/23.
 */

public class ITextDetailsModel implements IModel {
    private Retrofit retrofit;
    private CompositeDisposable compositeDisposable;

    public ITextDetailsModel() {
        this.retrofit = RetrofitUtils.getInstance();
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void cancel() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    //获取文章内容
    public void getForumContent(String id, int page, IModelImpl<ApiResponse<ForumDetailsBean>,ForumDetailsBean>listener){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("course_id",id);
        hashMap.put("page",page + "");

        HashMap<String,String> param = new HashMap<>();
        param.put("course_id",id);
        param.put("page",page + "");

        String sign = SignUtil.getInstance().getSign(hashMap);
        ForumService forumService = retrofit.create(ForumService.class);

        Disposable disposable = forumService.getForumContent(sign, Constants.TOKEN,param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<ForumDetailsBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<ForumDetailsBean> sendCodeBeanApiResponse) throws Exception {
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
