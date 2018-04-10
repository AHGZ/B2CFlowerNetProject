package com.android.p2pflowernet.project.view.fragments.index.bigdata;

import com.android.p2pflowernet.project.entity.BigDataMapBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2018/2/1.
 * by--
 */

public class INationwidePrenter extends IPresenter<INationwideView> {
    private INationwideModel model;

    public INationwidePrenter() {
        this.model = new INationwideModel();
    }

    @Override
    protected void cancel() {
        if (model != null) {
            model.cancel();
        }
    }

    //获取地图数据
    public void getMapData(){
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        getView().showDialog();
        model.getMapData(new IModelImpl<ApiResponse<BigDataMapBean>, BigDataMapBean>() {
            @Override
            protected void onSuccess(BigDataMapBean data, String message) {
                getView().hideDialog();
                getView().onSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<BigDataMapBean>> data, String message) {
                getView().hideDialog();
            }

            @Override
            protected void onFailure(String code, String message) {
                getView().hideDialog();
                getView().onError(message);
            }

            @Override
            protected void onSuccess() {

            }
        });
    }
}
