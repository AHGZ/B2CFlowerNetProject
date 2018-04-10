package com.android.p2pflowernet.project.view.fragments.mine.myteam.cloud_office.strongplan;

import com.android.p2pflowernet.project.entity.CloudGrowBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/11/30.
 * by--云工成长计划
 */

public class IStrongPlanCloudPrenter extends IPresenter<IStrongPlanCloudView>{

    private final StrongPlanCloudModel strongPlanCloudModel;

    @Override
    protected void cancel() {

    }

    public IStrongPlanCloudPrenter() {

        strongPlanCloudModel = new StrongPlanCloudModel();

    }

    //成长计划（云工）
    public void getCloudGrow() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        getView().showDialog();
        strongPlanCloudModel.getCloudGrow(new IModelImpl<ApiResponse<CloudGrowBean>, CloudGrowBean>() {
            @Override
            protected void onSuccess(CloudGrowBean data, String message) {
                getView().hideDialog();
                getView().SuccessCloud(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<CloudGrowBean>> data, String message) {
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
