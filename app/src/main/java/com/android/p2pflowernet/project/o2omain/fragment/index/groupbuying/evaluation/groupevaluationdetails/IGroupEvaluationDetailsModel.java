package com.android.p2pflowernet.project.o2omain.fragment.index.groupbuying.evaluation.groupevaluationdetails;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.GroupEvaluationBean;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.GroupBuyingService;
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
 * Created by heguozhong on 2018/1/17/004.
 * 团购评论详情数据层
 */

public class IGroupEvaluationDetailsModel implements IModel {
    private Retrofit mRetrofit;
    private CompositeDisposable compositeDisposable;

    public IGroupEvaluationDetailsModel() {

        mRetrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    //查看团购详情
    public void getGroupEvaluation(int merch_id, int group_id, int type, int page, final IModelImpl<ApiResponse<GroupEvaluationBean>, GroupEvaluationBean> listener) {
        HashMap<String, String> map = new HashMap<>();
        //商家ID
        map.put("merch_id", String.valueOf(merch_id));
        //团购ID
        map.put("group_id", String.valueOf(group_id));
        //请求类型
        map.put("type", type+"");
        //当前页数
        map.put("page",page+"");

        HashMap<String, String> param = new HashMap<>();
        param.put("merch_id", String.valueOf(merch_id));
        param.put("group_id", String.valueOf(group_id));
        param.put("type", String.valueOf(type));
        param.put("page", String.valueOf(page));

        String sign = SignUtil.getInstance().getSign(map);
        GroupBuyingService service = mRetrofit.create(GroupBuyingService.class);
        Disposable subscribe = service.getGroupEvaluation(sign,Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<GroupEvaluationBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<GroupEvaluationBean> groupEvaluationBeanApiResponse) throws Exception {
                        listener.onComplete(groupEvaluationBeanApiResponse);
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