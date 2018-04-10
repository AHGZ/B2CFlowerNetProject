package com.android.p2pflowernet.project.view.fragments.authentication.result;

import com.android.p2pflowernet.project.mvp.IModel;
import com.rxy.netlib.http.RetrofitUtils;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;

/**
 * Created by zhangkun on 2018/3/12.
 */

public class AuthenticationResultModel implements IModel {
    private Retrofit retrofit;
    private CompositeDisposable compositeDisposable;

    public AuthenticationResultModel() {
        this.retrofit = RetrofitUtils.getInstance();
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void cancel() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

}
