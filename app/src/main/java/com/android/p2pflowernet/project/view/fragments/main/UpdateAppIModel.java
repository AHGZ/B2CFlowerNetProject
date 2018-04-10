package com.android.p2pflowernet.project.view.fragments.main;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.VersionInfo;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.SettingService;
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
 * @描述: 更新版本工具类
 * @创建人：zhangpeisen
 * @创建时间：2017/12/26 上午9:33
 * @修改人：zhangpeisen
 * @修改时间：2017/12/26 上午9:33
 * @修改备注：
 * @throws
 */
public class UpdateAppIModel implements IModel {
    private Retrofit retrofit;
    private CompositeDisposable compositeDisposable;

    public UpdateAppIModel() {
        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    /**
     * 获取当前apk的版本号 currentVersionCode
     *
     * @param ctx
     * @return
     */
    public int getAPPLocalVersion(Context ctx) {
        int currentVersionCode = 0;
        PackageManager manager = ctx.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            String appVersionName = info.versionName; // 版本名
            currentVersionCode = info.versionCode; // 版本号
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return currentVersionCode;
    }

    /**
     * 获取当前apk的版本号 currentVersionCode
     *
     * @param ctx
     * @return
     */
    public String getAPPLocalVersionName(Context ctx) {
        int currentVersionCode = 0;
        String appVersionName = "";
        PackageManager manager = ctx.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            appVersionName = info.versionName; // 版本名
            currentVersionCode = info.versionCode; // 版本号
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appVersionName;
    }

    /**
     * 获取服务器上版本信息
     *
     * @param listener
     */
    public void getAPPServerVersion(final IModelImpl<ApiResponse<VersionInfo>, VersionInfo> listener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("app_type", Constants.RP);
        HashMap<String, String> param = new HashMap<>();
        param.put("app_type", Constants.RP);
        String sign = SignUtil.getInstance().getSign(map);
        SettingService codeService = retrofit.create(SettingService.class);
        Disposable subscribe = codeService.updateapp(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<VersionInfo>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<VersionInfo> checkMobileBeanApiResponse) throws Exception {
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
