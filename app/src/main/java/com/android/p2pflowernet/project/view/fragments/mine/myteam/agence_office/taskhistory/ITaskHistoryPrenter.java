package com.android.p2pflowernet.project.view.fragments.mine.myteam.agence_office.taskhistory;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.AgentHistoryBean;
import com.android.p2pflowernet.project.entity.CloudOfficeHistoryBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.mine.myteam.agence_office.AgencyOfficeModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * @描述:任务历史(代理人,云工)逻辑层
 * @创建人：zhangpeisen
 * @创建时间：2017/11/30 下午3:19
 * @修改人：zhangpeisen
 * @修改时间：2017/11/30 下午3:19
 * @修改备注：
 * @throws
 */
public class ITaskHistoryPrenter extends IPresenter<ITaskHistoryView> {
    AgencyOfficeModel agencyOfficeModel;

    public ITaskHistoryPrenter() {
        agencyOfficeModel = new AgencyOfficeModel();
    }

    /**
     * @throws
     * @描述: 代理历史
     * @创建人：zhangpeisen
     * @创建时间：2017/12/25 上午9:44
     * @修改人：zhangpeisen
     * @修改时间：2017/12/25 上午9:44
     * @修改备注：
     */
    public void agenthistory() {
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
        agencyOfficeModel.agenthistory(year, month, new IModelImpl<ApiResponse<AgentHistoryBean>, AgentHistoryBean>() {
            @Override
            protected void onSuccess(AgentHistoryBean data, String message) {
                getView().hideDialog();
                getView().onSuccess(message);
                getView().onSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<AgentHistoryBean>> data, String message) {
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

    /**
     * @throws
     * @描述: 云工历史
     * @创建人：zhangpeisen
     * @创建时间：2017/12/25 上午9:44
     * @修改人：zhangpeisen
     * @修改时间：2017/12/25 上午9:44
     * @修改备注：
     */
    public void cloudhistory() {
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
        agencyOfficeModel.cloudhistory(year, month, new IModelImpl<ApiResponse<CloudOfficeHistoryBean>, CloudOfficeHistoryBean>() {
            @Override
            protected void onSuccess(CloudOfficeHistoryBean data, String message) {
                getView().hideDialog();
                getView().onSuccess(message);
                getView().onSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<CloudOfficeHistoryBean>> data, String message) {
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
