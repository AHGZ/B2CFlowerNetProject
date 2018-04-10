package com.android.p2pflowernet.project.view.fragments.mine.myteam.agence_office.audit;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.AuditHistoryBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.mine.myteam.agence_office.AgencyOfficeModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/12/1.
 * by--审核历史逻辑
 */

public class IAuditPrenter extends IPresenter<IAuditHistoryView>{
    AgencyOfficeModel agencyOfficeModel;

    public IAuditPrenter() {
        agencyOfficeModel = new AgencyOfficeModel();
    }

    public void trialHistory() {
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String year = getView().getYear();
        String month = getView().getMonth();
        if (TextUtils.isEmpty(year) && year.equals("") || TextUtils.isEmpty(month) && month.equals("")) {
            getView().onError("请选择年月");
            return;
        }
        getView().showDialog();
        agencyOfficeModel.trialhistory(year, month, new IModelImpl<ApiResponse<AuditHistoryBean>, AuditHistoryBean>() {
            @Override
            protected void onSuccess(AuditHistoryBean data, String message) {
                getView().hideDialog();
                getView().onSuccess(message);
                getView().onSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<AuditHistoryBean>> data, String message) {
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

    @Override
    protected void cancel() {
        agencyOfficeModel.cancel();
    }
}
