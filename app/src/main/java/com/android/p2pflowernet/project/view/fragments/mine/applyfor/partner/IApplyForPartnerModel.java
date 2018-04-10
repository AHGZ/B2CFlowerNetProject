package com.android.p2pflowernet.project.view.fragments.mine.applyfor.partner;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.IdEntityBean;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.ApplyForPartnerService;
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
 * Created by caishen on 2017/11/21.
 * by--申请合伙人的数据层
 */

public class IApplyForPartnerModel implements IModel {

    private final Retrofit retrofit;
    private final CompositeDisposable compositeDisposable;

    @Override
    public void cancel() {

        if (compositeDisposable != null) {

            compositeDisposable.dispose();
        }
    }

    public IApplyForPartnerModel() {

        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();

    }

    //没有验证过的去实名认证
    public void commit(String idCard, String name, String Is_checked, String user_id, final
    IModelImpl<ApiResponse<String>, String> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("realname", name);
        map.put("id_number", idCard);
        map.put("is_checked", Is_checked);

        HashMap<String, String> parma = new HashMap<>();
        parma.put("realname", name);
        parma.put("id_number", idCard);
        parma.put("is_checked", Is_checked);
        String sign = SignUtil.getInstance().getSign(map);
        ApplyForPartnerService codeService = retrofit.create(ApplyForPartnerService.class);
        Disposable subscribe = codeService.checkIdentity(sign, Constants.TOKEN, parma)
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

                        if (throwable.getMessage().toString().equals("验证成功！")) {

                            listener.onError("0", throwable.getMessage());

                        } else if (throwable.getMessage().toString().equals("身份证已被使用！")) {

                            listener.onError("-4", throwable.getMessage());

                        } else {

                            listener.onError("-1", throwable.getMessage());
                        }
                    }
                });

        compositeDisposable.add(subscribe);
    }

    //检验是否实名认证过
    public void checkIdentity(String userId, final IModelImpl<ApiResponse<IdEntityBean>, IdEntityBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        HashMap<String, String> param = new HashMap<>();
        String sign = SignUtil.getInstance().getSign(map);
        ApplyForPartnerService codeService = retrofit.create(ApplyForPartnerService.class);
        Disposable subscribe = codeService.getIdInfo(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<IdEntityBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<IdEntityBean> checkMobileBeanApiResponse) throws Exception {
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
