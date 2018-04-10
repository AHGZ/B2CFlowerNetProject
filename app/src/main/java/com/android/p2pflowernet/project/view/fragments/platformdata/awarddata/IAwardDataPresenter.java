package com.android.p2pflowernet.project.view.fragments.platformdata.awarddata;

import com.android.p2pflowernet.project.entity.BusinessBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by zhangkun on 2018/1/31.
 */

public class IAwardDataPresenter extends IPresenter<IAwardDataView>{

    private IAwardDataModel iAwardDataModel;

    public IAwardDataPresenter() {
        this.iAwardDataModel = new IAwardDataModel();
    }

    @Override
    protected void cancel() {
        iAwardDataModel.cancel();
    }

    //获取业务奖励数据
    public void getBusinessData(){
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        getView().showDialog();
        iAwardDataModel.getBusinessData(new IModelImpl<ApiResponse<BusinessBean>, BusinessBean>() {
            @Override
            protected void onSuccess(BusinessBean data, String message) {
                getView().hideDialog();
                getView().onSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<BusinessBean>> data, String message) {
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
