package com.android.p2pflowernet.project.view.fragments.mine.myteam.agence_office;

import com.android.p2pflowernet.project.entity.AgentOfficeBean;
import com.android.p2pflowernet.project.entity.AutoWorkBean;
import com.android.p2pflowernet.project.entity.ShareCodeBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.mine.setting.personal.PersonalModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/11/28.
 * by--代理办公
 */

public class IAgenceOfficePrenter extends IPresenter<IAgenceOfficeView> {

    private final AgenceOfficeModel agenceOfficeModel;
    private final PersonalModel personalModel;

    @Override
    protected void cancel() {

        agenceOfficeModel.cancel();
        personalModel.cancel();
    }

    public IAgenceOfficePrenter() {

        agenceOfficeModel = new AgenceOfficeModel();
        personalModel = new PersonalModel();
    }

    //代理办公
    public void getagentWork() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        getView().showDialog();
        agenceOfficeModel.getagentWork(new IModelImpl<ApiResponse<AgentOfficeBean>, AgentOfficeBean>() {
            @Override
            protected void onSuccess(AgentOfficeBean data, String message) {
                getView().hideDialog();
                getView().Succesagent(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<AgentOfficeBean>> data, String message) {
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

    //修改审批状态
    public void autoWork() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String state = getView().getState();

        if (state.equals("")) {
            return;
        }

        getView().showDialog();
        agenceOfficeModel.autoWork(state, new IModelImpl<ApiResponse<AutoWorkBean>, AutoWorkBean>() {
            @Override
            protected void onSuccess(AutoWorkBean data, String message) {
                getView().hideDialog();
                getView().onSuccessAutoWork(data);
                getView().onSuccess(message);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<AutoWorkBean>> data, String message) {
                getView().hideDialog();
                getView().onSuccess(message);
            }

            @Override
            protected void onFailure(String code, String message) {
                getView().hideDialog();
                if (code.equals("-3")) {
                    getView().onErrorAudit(message);
                } else {
                    getView().onError(message);
                }

            }

            @Override
            protected void onSuccess() {
                getView().hideDialog();
            }
        });
    }

    //手动审批
    public void trial() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String reccardId = getView().getReccardId();
        if (reccardId.isEmpty()) {
            getView().onError("目前没有可审批的选项");
            return;
        }

        agenceOfficeModel.trial(reccardId, new IModelImpl<ApiResponse<String>, String>() {
            @Override
            protected void onSuccess(String data, String message) {
                getView().hideDialog();
                getView().onSuccesstrial(message);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<String>> data, String message) {
                getView().hideDialog();
                getView().onSuccesstrial(message);
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

    //获取分享详情
    public void getShareCode() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        // 加载进度条
        getView().showDialog();
        personalModel.getShareCode(new IModelImpl<ApiResponse<ShareCodeBean>, ShareCodeBean>() {
            @Override
            protected void onSuccess(ShareCodeBean data, String message) {
                getView().hideDialog();
                getView().onSuccessShare(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<ShareCodeBean>> data, String message) {
                getView().hideDialog();
                getView().onSuccess(message);
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
