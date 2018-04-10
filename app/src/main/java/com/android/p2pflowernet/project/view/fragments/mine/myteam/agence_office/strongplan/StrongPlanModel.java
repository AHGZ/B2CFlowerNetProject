package com.android.p2pflowernet.project.view.fragments.mine.myteam.agence_office.strongplan;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.AgentGrowBean;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.MyTeamService;
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
 * Created by caishen on 2017/12/22.
 * by--
 */

public class StrongPlanModel implements IModel {

    private final Retrofit retrofit;
    private final CompositeDisposable compositeDisposable;

    @Override
    public void cancel() {

        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    public StrongPlanModel() {
        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }


    //成长计划（代理）
    public void getagentGrowPlan(final IModelImpl<ApiResponse<AgentGrowBean>, AgentGrowBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        String sign = SignUtil.getInstance().getSign(map);
        MyTeamService codeService = retrofit.create(MyTeamService.class);
        Disposable subscribe = codeService.getagentGrowPlan(sign, Constants.TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<AgentGrowBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<AgentGrowBean> checkMobileBeanApiResponse) throws Exception {
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
