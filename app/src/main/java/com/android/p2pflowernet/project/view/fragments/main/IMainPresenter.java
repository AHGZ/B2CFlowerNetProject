package com.android.p2pflowernet.project.view.fragments.main;


import com.android.p2pflowernet.project.base.BaseApplication;
import com.android.p2pflowernet.project.entity.VersionInfo;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * author: zhangpeisen
 * created on: 2017/7/25 上午10:21
 * description: 网络处理类
 */
public class IMainPresenter extends IPresenter<IMainView> {
    UpdateAppIModel updateAppUtils;

    public IMainPresenter() {
        updateAppUtils = new UpdateAppIModel();
    }

    public int getAPPLocalVersion() {
        return updateAppUtils.getAPPLocalVersion(BaseApplication.getContext());
    }

    /**
     * @throws
     * @描述: App更新方法
     * @方法名:
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/26 上午10:50
     * @修改人 zhangpeisen
     * @修改时间 2017/12/26 上午10:50
     * @修改备注
     */
    public void updateApp() {
        if (!NetWorkUtils.isNetworkAvailable()) {
            if (getView() != null) {
                getView().onError("网络信号弱,请稍后重试");
            }
            return;
        }
        if (getView() != null) {
            getView().showDialog();
        }
        updateAppUtils.getAPPServerVersion(new IModelImpl<ApiResponse<VersionInfo>, VersionInfo>() {
            @Override
            protected void onSuccess(VersionInfo data, String message) {
                if (getView() != null) {
                    getView().hideDialog();
                    getView().onSuccess(data);
                }
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<VersionInfo>> data, String message) {
                if (getView() != null) {
                    getView().hideDialog();
                    getView().onSuccess(message);
                }
            }

            @Override
            protected void onFailure(String code, String message) {
                if (getView() != null) {
                    getView().hideDialog();
                    getView().onError(message);
                }
            }

            @Override
            protected void onSuccess() {
                if (getView() != null) {
                    getView().hideDialog();
                }
            }
        });
    }

    @Override
    protected void cancel() {
        updateAppUtils.cancel();
    }
}
