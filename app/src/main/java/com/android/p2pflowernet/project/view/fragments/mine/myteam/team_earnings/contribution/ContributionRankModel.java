package com.android.p2pflowernet.project.view.fragments.mine.myteam.team_earnings.contribution;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.ContriRankBean;
import com.android.p2pflowernet.project.entity.MyTeamProfitBean;
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
 * Created by caishen on 2017/12/19.
 * by--
 */

public class ContributionRankModel implements IModel {

    private final Retrofit retrofit;
    private final CompositeDisposable compositeDisposable;

    @Override
    public void cancel() {

        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    public ContributionRankModel() {

        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();

    }


    //获取贡献排行榜
    public void getContriRank(String state, final IModelImpl<ApiResponse<ContriRankBean>, ContriRankBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("state", state);

        HashMap<String, String> param = new HashMap<>();
        param.put("state", state);
        String sign = SignUtil.getInstance().getSign(map);
        MyTeamService codeService = retrofit.create(MyTeamService.class);
        Disposable subscribe = codeService.getContriRank(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<ContriRankBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<ContriRankBean> checkMobileBeanApiResponse) throws Exception {
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

    //获取团队收益数据
    public void getTeamProfit(final IModelImpl<ApiResponse<MyTeamProfitBean>, MyTeamProfitBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        HashMap<String, String> param = new HashMap<>();
        String sign = SignUtil.getInstance().getSign(map);
        MyTeamService codeService = retrofit.create(MyTeamService.class);
        Disposable subscribe = codeService.getTeamProfit(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<MyTeamProfitBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<MyTeamProfitBean> checkMobileBeanApiResponse) throws Exception {
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
