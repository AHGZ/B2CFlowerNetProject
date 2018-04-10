package com.android.p2pflowernet.project.view.fragments.mine.myteam.agence_office.strongplan;

import com.android.p2pflowernet.project.entity.AgentGrowBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/11/30.
 * by--代理人成长计划的逻辑层
 */

public class IStrongPlanPrenter extends IPresenter<IStrongPlanView> {

    private final StrongPlanModel strongPlanModel;

    @Override
    protected void cancel() {

        strongPlanModel.cancel();
    }

    public IStrongPlanPrenter() {

        strongPlanModel = new StrongPlanModel();
    }

    //成长计划（代理）
    public void getagentGrowPlan() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        getView().showDialog();
        strongPlanModel.getagentGrowPlan(new IModelImpl<ApiResponse<AgentGrowBean>, AgentGrowBean>() {
            @Override
            protected void onSuccess(AgentGrowBean data, String message) {
                getView().hideDialog();
                getView().SuccessAgent(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<AgentGrowBean>> data, String message) {
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
