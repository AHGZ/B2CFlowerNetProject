package com.android.p2pflowernet.project.view.fragments.platformdata.regiondata;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.RoleBean;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.DataService;
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
 * Created by zhangkun on 2018/1/31.
 */

public class IRegionRoleDataModel implements IModel {
    private Retrofit retrofit;
    private CompositeDisposable compositeDisposable;

    public IRegionRoleDataModel() {
        this.retrofit = RetrofitUtils.getInstance();
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void cancel() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    //获取各地区角色组成数据
    public void getRoleData(IModelImpl<ApiResponse<RoleBean>, RoleBean> listener){
        HashMap<String,String> hashMap = new HashMap<>();

        HashMap<String,String> param = new HashMap<>();

        String sign = SignUtil.getInstance().getSign(hashMap);
        DataService service = retrofit.create(DataService.class);
        Disposable disposable = service.getRoleData(sign, Constants.TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<RoleBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<RoleBean> checkMobileBeanApiResponse) throws Exception {
                        listener.onComplete(checkMobileBeanApiResponse);
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
