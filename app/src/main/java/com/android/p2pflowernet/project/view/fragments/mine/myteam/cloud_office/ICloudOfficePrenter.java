package com.android.p2pflowernet.project.view.fragments.mine.myteam.cloud_office;

import com.android.p2pflowernet.project.entity.CloudOfficeBean;
import com.android.p2pflowernet.project.entity.ShareCodeBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.mine.setting.personal.PersonalModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/11/28.
 * by--
 */

public class ICloudOfficePrenter extends IPresenter<ICloudOfficeView> {

    private final CloudOfficeModel cloudOfficeModel;
    private final PersonalModel personalModel;

    @Override
    protected void cancel() {

        cloudOfficeModel.cancel();
        personalModel.cancel();
    }

    public ICloudOfficePrenter() {

        cloudOfficeModel = new CloudOfficeModel();
        personalModel = new PersonalModel();
    }

    //云工办公
    public void getCloudOffice() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        getView().showDialog();

        cloudOfficeModel.getCloudOffice(new IModelImpl<ApiResponse<CloudOfficeBean>, CloudOfficeBean>() {
            @Override
            protected void onSuccess(CloudOfficeBean data, String message) {
                getView().hideDialog();
                getView().Successcloud(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<CloudOfficeBean>> data, String message) {
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
