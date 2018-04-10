package com.android.p2pflowernet.project.view.fragments.mine.setting.personal;

import com.android.p2pflowernet.project.entity.ShowPersonInfo;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/11/15.
 * by--二维码邀请的逻辑层
 */

public class IQrInvitePrenter extends IPresenter<IQrInViteView> {

    private final PersonalModel personalModel;

    @Override
    protected void cancel() {

    }

    public IQrInvitePrenter() {

        personalModel = new PersonalModel();
    }

    //获取个人信息
    public void getPersonal() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        //加载进度条
        getView().showDialog();

        personalModel.showPersonalInfo(new IModelImpl<ApiResponse<ShowPersonInfo>, ShowPersonInfo>() {
            @Override
            protected void onSuccess(ShowPersonInfo data, String message) {
                getView().hideDialog();
                getView().setShowPersonInfo(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<ShowPersonInfo>> data, String message) {
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
