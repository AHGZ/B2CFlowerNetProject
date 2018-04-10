package com.android.p2pflowernet.project.view.fragments.platformdata.roleformdata;

import com.android.p2pflowernet.project.entity.RoleFormBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by zhangkun on 2018/1/31.
 */

public class IRoleFormDataPresenter extends IPresenter<IRoleFormDataView> {

    private IRoleFormDataModel iRoleFormDataModel;

    public IRoleFormDataPresenter() {
        this.iRoleFormDataModel = new IRoleFormDataModel();
    }

    @Override
    protected void cancel() {
        iRoleFormDataModel.cancel();
    }

    //获取角色组成数据
    public void getRoleData(){
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        getView().showDialog();
        iRoleFormDataModel.getRoleData(new IModelImpl<ApiResponse<RoleFormBean>, RoleFormBean>() {
            @Override
            protected void onSuccess(RoleFormBean data, String message) {
                getView().hideDialog();
                getView().onSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<RoleFormBean>> data, String message) {
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
