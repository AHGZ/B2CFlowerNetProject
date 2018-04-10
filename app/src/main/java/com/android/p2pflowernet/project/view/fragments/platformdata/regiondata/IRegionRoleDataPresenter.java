package com.android.p2pflowernet.project.view.fragments.platformdata.regiondata;

import com.android.p2pflowernet.project.entity.RoleBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by zhangkun on 2018/1/31.
 */

public class IRegionRoleDataPresenter extends IPresenter<IRegionRoleDataView> {
    private IRegionRoleDataModel iRegionRoleDataModel;

    public IRegionRoleDataPresenter() {
        this.iRegionRoleDataModel = new IRegionRoleDataModel();
    }

    @Override
    protected void cancel() {
        iRegionRoleDataModel.cancel();
    }

    //获取各地区角色组成数据
    public void getRoleData(){
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        getView().showDialog();
        iRegionRoleDataModel.getRoleData(new IModelImpl<ApiResponse<RoleBean>, RoleBean>() {
            @Override
            protected void onSuccess(RoleBean data, String message) {
                getView().hideDialog();
                getView().onSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<RoleBean>> data, String message) {
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
