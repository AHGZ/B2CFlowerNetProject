package com.android.p2pflowernet.project.view.fragments.mine.myteam.agence_office;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.AgentHistoryBean;
import com.android.p2pflowernet.project.entity.AuditHistoryBean;
import com.android.p2pflowernet.project.entity.CloudOfficeHistoryBean;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.AgentService;
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
 * author: zhangpeisen
 * created on: 2017/12/21 上午9:15
 * description: 代理办公数据访问层
 */
public class AgencyOfficeModel implements IModel {
    private Retrofit mRetrofit;
    private CompositeDisposable compositeDisposable;

    public AgencyOfficeModel() {
        mRetrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    /**
     * @throws
     * @描述: 审核历史
     * @创建人：zhangpeisen
     * @创建时间：2017/12/25 上午9:47
     * @修改人：zhangpeisen
     * @修改时间：2017/12/25 上午9:47
     * @修改备注：
     */
    public void trialhistory(String year, String month, final IModelImpl<ApiResponse<AuditHistoryBean>, AuditHistoryBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        // 生成sign
        map.put("y", year);
        map.put("m", month);
        HashMap<String, String> param = new HashMap<>();
        // 传参
        param.put("y", year);
        param.put("m", month);
        String sign = SignUtil.getInstance().getSign(map);
        AgentService agentService = mRetrofit.create(AgentService.class);
        Disposable subscribe = agentService.trialhistory(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<AuditHistoryBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<AuditHistoryBean> checkMobileBeanApiResponse) throws Exception {
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

    /**
     * @throws
     * @描述:
     * @方法名: 代理历史
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/21 上午9:20
     * @修改人 zhangpeisen
     * @修改时间 2017/12/21 上午9:20
     * @修改备注
     */
    public void agenthistory(String year, String month, final IModelImpl<ApiResponse<AgentHistoryBean>, AgentHistoryBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        // 生成sign
        map.put("y", year);
        map.put("m", month);
        HashMap<String, String> param = new HashMap<>();
        // 传参
        param.put("y", year);
        param.put("m", month);
        String sign = SignUtil.getInstance().getSign(map);
        AgentService agentService = mRetrofit.create(AgentService.class);
        Disposable subscribe = agentService.agenthistory(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<AgentHistoryBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<AgentHistoryBean> checkMobileBeanApiResponse) throws Exception {
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

    /**
     * @throws
     * @描述: 云工历史
     * @创建人：zhangpeisen
     * @创建时间：2017/12/25 上午9:42
     * @修改人：zhangpeisen
     * @修改时间：2017/12/25 上午9:42
     * @修改备注：
     */
    public void cloudhistory(String year, String month, final IModelImpl<ApiResponse<CloudOfficeHistoryBean>, CloudOfficeHistoryBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        // 生成sign
        map.put("y", year);
        map.put("m", month);
        HashMap<String, String> param = new HashMap<>();
        // 传参
        param.put("y", year);
        param.put("m", month);
        String sign = SignUtil.getInstance().getSign(map);
        AgentService agentService = mRetrofit.create(AgentService.class);
        Disposable subscribe = agentService.cloudhistory(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<CloudOfficeHistoryBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<CloudOfficeHistoryBean> checkMobileBeanApiResponse) throws Exception {
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

    @Override
    public void cancel() {
        if (compositeDisposable != null)
            compositeDisposable.dispose();
    }
}
