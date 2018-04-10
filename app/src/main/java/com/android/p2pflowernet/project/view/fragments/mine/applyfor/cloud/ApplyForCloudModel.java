package com.android.p2pflowernet.project.view.fragments.mine.applyfor.cloud;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.CloudInfoBean;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.ApplyForCloudService;
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
 * Created by caishen on 2017/11/22.
 * by--申请云工的数据层
 */

public class ApplyForCloudModel implements IModel {

    private final Retrofit retrofit;
    private final CompositeDisposable compositeDisposable;

    @Override
    public void cancel() {

        if (compositeDisposable != null) {

            compositeDisposable.dispose();
        }
    }

    public ApplyForCloudModel() {

        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    //添加云工申请的接口
    public void addCloud(String realName, String sex, String idNumber,
                         String frontPhoto, String backPhoto, IModelImpl<ApiResponse<String>, String> listener) {

        HashMap<String, String> map = new HashMap<>();

        map.put("realname", realName);
        map.put("sex", sex);
        map.put("id_number", idNumber);
        map.put("cfi_path", frontPhoto);
        map.put("cbi_path", backPhoto);

        HashMap<String, String> param = new HashMap<>();
        param.put("realname", realName);
        param.put("sex", sex);
        param.put("id_number", idNumber);
        param.put("cfi_path", frontPhoto);
        param.put("cbi_path", backPhoto);

        String sign = SignUtil.getInstance().getSign(map);
        ApplyForCloudService codeService = retrofit.create(ApplyForCloudService.class);
        Disposable subscribe = codeService.addCloud(sign, Constants.TOKEN, param)
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

    //获取云工信息
    public void getCloudInfo(String cloudId, String statue, final IModelImpl<ApiResponse<CloudInfoBean>,
            CloudInfoBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("id", cloudId);

        HashMap<String, String> param = new HashMap<>();
        param.put("id", cloudId);

        String sign = SignUtil.getInstance().getSign(map);
        ApplyForCloudService codeService = retrofit.create(ApplyForCloudService.class);
        Disposable subscribe = codeService.getCloud(sign,Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<CloudInfoBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<CloudInfoBean> checkMobileBeanApiResponse) throws Exception {
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

    //修改云工信息
    public void upodataCloud(String cloudId, String backPhoto, String frontPhoto, String realName, String sex,
                             String idNumber, IModelImpl<ApiResponse<String>, String> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("id", cloudId);
        map.put("realname", realName);
        map.put("sex", sex);
        map.put("id_number", idNumber);
        map.put("cfi_path", frontPhoto);
        map.put("cbi_path", backPhoto);

        HashMap<String, String> param = new HashMap<>();
        param.put("id", cloudId);
        param.put("realname", realName);
        param.put("sex", sex);
        param.put("id_number", idNumber);
        param.put("cfi_path", frontPhoto);
        param.put("cbi_path", backPhoto);

        String sign = SignUtil.getInstance().getSign(map);
        ApplyForCloudService codeService = retrofit.create(ApplyForCloudService.class);
        Disposable subscribe = codeService.updataCloud(sign,Constants.TOKEN, param)
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

                        if (throwable.getMessage().toString().equals("更新成功")) {

                            listener.onError("0", throwable.getMessage());

                        } else {

                            listener.onError("-1", throwable.getMessage());
                        }
                    }
                });
        compositeDisposable.add(subscribe);
    }
}
