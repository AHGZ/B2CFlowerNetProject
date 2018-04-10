package com.android.p2pflowernet.project.view.fragments.advertising;

import com.android.p2pflowernet.project.entity.SplashBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2018/2/1.
 * by--
 */

class ISplashPrenter extends IPresenter<ISplashView> {

    private final SplashModel splashModel;

    @Override
    protected void cancel() {

        splashModel.cancel();
    }

    public ISplashPrenter() {

        splashModel = new SplashModel();
    }

    //获取是否显示广告的数据
    public void getAdvertising() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            return;
        }

        splashModel.getAdvertising(new IModelImpl<ApiResponse<SplashBean>, SplashBean>() {
            @Override
            protected void onSuccess(SplashBean data, String message) {
                getView().successData(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<SplashBean>> data, String message) {

            }

            @Override
            protected void onFailure(String code, String message) {
            }

            @Override
            protected void onSuccess() {

            }
        });
    }
}
