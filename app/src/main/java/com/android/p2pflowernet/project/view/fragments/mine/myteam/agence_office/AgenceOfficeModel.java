package com.android.p2pflowernet.project.view.fragments.mine.myteam.agence_office;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.AgentOfficeBean;
import com.android.p2pflowernet.project.entity.AutoWorkBean;
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
 * Created by caishen on 2017/12/20.
 * by--代理办公
 */

public class AgenceOfficeModel implements IModel {

    private final Retrofit retrofit;
    private final CompositeDisposable compositeDisposable;

    @Override
    public void cancel() {

        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    public AgenceOfficeModel() {

        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    //代理办公
    public void getagentWork(IModelImpl<ApiResponse<AgentOfficeBean>, AgentOfficeBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        String sign = SignUtil.getInstance().getSign(map);
        MyTeamService codeService = retrofit.create(MyTeamService.class);
        Disposable subscribe = codeService.getagentWork(sign, Constants.TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<AgentOfficeBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<AgentOfficeBean> sendCodeBeanApiResponse) throws Exception {
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

    //修改审批状态
    public void autoWork(String state, IModelImpl<ApiResponse<AutoWorkBean>, AutoWorkBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("state", state);

        HashMap<String, String> param = new HashMap<>();
        param.put("state", state);

        String sign = SignUtil.getInstance().getSign(map);
        MyTeamService codeService = retrofit.create(MyTeamService.class);
        Disposable subscribe = codeService.autoWork(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<AutoWorkBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<AutoWorkBean> sendCodeBeanApiResponse) throws Exception {
                        listener.onComplete(sendCodeBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                        if (throwable.getMessage().toString().equals("开启自动模式需要审批金大于等于10万元," +
                                "自动模式开启后系统将自动为您审批合伙人,从此将不存在违规风险！")) {
                            listener.onError("-3", throwable.getMessage());
                        } else {
                            listener.onError("-1", throwable.getMessage());
                        }
                    }
                });
        compositeDisposable.add(subscribe);
    }

    //手动审批
    public void trial(String reccardId, IModelImpl<ApiResponse<String>, String> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("record_id", reccardId);

        HashMap<String, String> param = new HashMap<>();
        param.put("record_id", reccardId);

        String sign = SignUtil.getInstance().getSign(map);
        MyTeamService codeService = retrofit.create(MyTeamService.class);
        Disposable subscribe = codeService.trial(sign, Constants.TOKEN, param)
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
