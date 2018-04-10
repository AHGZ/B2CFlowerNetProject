package com.android.p2pflowernet.project.view.fragments.mine.applyfor.manufac;

import android.text.TextUtils;

import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2018/1/29.
 * by--
 */

public class IManufacPrenter extends IPresenter<IManufacView> {

    private final ManufacModel manufacModel;

    @Override
    protected void cancel() {

        manufacModel.cancel();
    }

    public IManufacPrenter() {

        manufacModel = new ManufacModel();

    }

    // 供应商申请
    public void add_manufac_apply() {

        if (!NetWorkUtils.isNetworkAvailable()) {

            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String apply_name = getView().getapply_name();
        if (TextUtils.isEmpty(apply_name)) {
            getView().onError("请填写姓名");
            return;
        }

        String apply_phone = getView().getapply_phone();
        if (TextUtils.isEmpty(apply_phone)) {
            getView().onError("请填写联系方式");
            return;
        }

        String apply_email = getView().apply_email();
        if (TextUtils.isEmpty(apply_email)) {
            getView().onError("请填写邮箱");
            return;
        }

        getView().showDialog();

        manufacModel.add_manufac_apply(apply_name, apply_phone, apply_email, new IModelImpl<ApiResponse<String>, String>() {
            @Override
            protected void onSuccess(String data, String message) {
                getView().hideDiaglog();
                getView().successData(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<String>> data, String message) {
                getView().hideDiaglog();
            }

            @Override
            protected void onFailure(String code, String message) {
                getView().hideDiaglog();
                getView().onError(message);
            }

            @Override
            protected void onSuccess() {
                getView().hideDiaglog();
            }
        });
    }
}
