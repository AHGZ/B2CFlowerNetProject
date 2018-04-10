package com.android.p2pflowernet.project.view.fragments.authentication;

import android.text.TextUtils;

import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by zhangkun on 2018/3/12.
 */

public class IAuthenticationPrenter extends IPresenter<IAuthenticationView>{
    private IAuthenticationModel model;

    public IAuthenticationPrenter() {
        this.model = new IAuthenticationModel();
    }

    @Override
    protected void cancel() {
        model.cancel();
    }

    public void userAuthentication (){
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String realname = getView().getUserName();
        String id_number = getView().getIDNumber();
        String bank_num = getView().getBankNumber();
        String bank_name = getView().getBankName();
        if (TextUtils.isEmpty(realname)) {
            getView().onError("真实姓名不能为空");
            return;
        }
        if (TextUtils.isEmpty(id_number)) {
            getView().onError("身份证号不能为空");
            return;
        }
        if (TextUtils.isEmpty(bank_num)) {
            getView().onError("银行卡号不能为空");
            return;
        }
        if (TextUtils.isEmpty(bank_name)) {
            getView().onError("银行名称不能为空");
            return;
        }

        getView().showDialog();
        model.gotoAuthentication(realname, id_number, bank_num, bank_name, new IModelImpl<ApiResponse<String>, String>() {
            @Override
            protected void onSuccess(String data, String message) {
                getView().hideDialog();
                getView().onSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<String>> data, String message) {

            }

            @Override
            protected void onFailure(String code, String message) {
                getView().hideDialog();
                getView().onError(code);
            }

            @Override
            protected void onSuccess() {
                getView().hideDialog();
            }
        });
    }
}
