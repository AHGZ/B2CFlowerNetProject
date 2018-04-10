package com.android.p2pflowernet.project.view.fragments.mine.setting;

import com.android.p2pflowernet.project.entity.IdEntityBean;
import com.android.p2pflowernet.project.entity.SettingBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.partner.IApplyForPartnerModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/11/4.
 * by--设置页面的逻辑层
 */

public class ISettingPrenter extends IPresenter<ISettingView> {

    private final IsetitngModel isetitngModel;
    private final IApplyForPartnerModel iApplyForPartnerModel;

    @Override
    protected void cancel() {
        iApplyForPartnerModel.cancel();
    }

    public ISettingPrenter() {

        isetitngModel = new IsetitngModel();
        iApplyForPartnerModel = new IApplyForPartnerModel();
    }

    //点击设置返回状态值
    public void getSetting() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        getView().showDialog();
        isetitngModel.getSetting(new IModelImpl<ApiResponse<SettingBean>, SettingBean>() {
            @Override
            protected void onSuccess(SettingBean data, String message) {
                getView().hideDialog();
                getView().setSuccessSet(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<SettingBean>> data, String message) {
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

    //检测是否实名认证过
    public void checkIdentity() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        getView().showDialog();
        iApplyForPartnerModel.checkIdentity("", new IModelImpl<ApiResponse<IdEntityBean>, IdEntityBean>() {
            @Override
            protected void onSuccess(IdEntityBean data, String message) {
                getView().hideDialog();
                getView().setGetIdentitySuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<IdEntityBean>> data, String message) {
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
