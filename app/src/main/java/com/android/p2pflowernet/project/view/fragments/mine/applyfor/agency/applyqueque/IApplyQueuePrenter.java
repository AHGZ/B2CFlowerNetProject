package com.android.p2pflowernet.project.view.fragments.mine.applyfor.agency.applyqueque;

import com.android.p2pflowernet.project.entity.AgentQuereBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.agency.IAgentModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * @描述:申请排队功能逻辑层
 * @创建人：zhangpeisen
 * @创建时间：2017/11/22 下午5:13
 * @修改人：zhangpeisen
 * @修改时间：2017/11/22 下午5:13
 * @修改备注：
 * @throws
 */
public class IApplyQueuePrenter extends IPresenter<IApplyQueueView> {
    IAgentModel iAgentModel;

    public IApplyQueuePrenter() {

        iAgentModel = new IAgentModel();
    }

    //代理人申请排队数据
    public void AgentQueue() {

        String region = getView().getRegion();
        if (region.equals("")) {
            return;
        }

        getView().showDialog();

        iAgentModel.agentqueue(region, new IModelImpl<ApiResponse<AgentQuereBean>, AgentQuereBean>() {
            @Override
            protected void onSuccess(AgentQuereBean data, String message) {

                getView().hideDialog();
                getView().onSuccessData(data);

            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<AgentQuereBean>> data, String message) {
                getView().hideDialog();
            }

            @Override
            protected void onFailure(String code, String message) {
                getView().hideDialog();
                getView().onError(message);
            }

            @Override
            protected void onSuccess() {
                getView().hideDialog();
            }
        });
    }

    @Override
    protected void cancel() {
        iAgentModel.cancel();
    }
}
