package com.android.p2pflowernet.project.view.fragments.mine.orderflow.logistics;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.LogisticsDetailBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.mine.orderflow.logistics.info.LogisticsModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/11/20.
 * by--查看物流信息逻辑层
 */

public class ILogisticsDetailPrenter extends IPresenter<ILogisticsDetailView> {
    private LogisticsModel logisticsModel;

    public ILogisticsDetailPrenter() {

        logisticsModel = new LogisticsModel();
    }

    /**
     * @throws
     * @描述:物流查询
     * @方法名:
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/22 下午4:46
     * @修改人 zhangpeisen
     * @修改时间 2017/12/22 下午4:46
     * @修改备注
     */
    public void querydelivery() {
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String ordernum = getView().getOrderNum();
        String expressid = getView().getExpressId();
        String waybillnum = getView().getWaybillnum();
        String status = getView().getStatus();
        if (ordernum.equals("") && TextUtils.isEmpty(ordernum)) {
            return;
        }
        if (expressid.equals("") && TextUtils.isEmpty(expressid)) {
            return;
        }
        if (waybillnum.equals("") && TextUtils.isEmpty(waybillnum)) {
            return;
        }

        if (status.equals("") && TextUtils.isEmpty(status)) {
            return;
        }
        getView().showDialog();
        logisticsModel.querydelivery(ordernum, expressid, waybillnum, status, new IModelImpl<ApiResponse<LogisticsDetailBean>, LogisticsDetailBean>() {
            @Override
            protected void onSuccess(LogisticsDetailBean data, String message) {
                getView().hideDialog();
                getView().onSuccess(message);
                getView().onSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<LogisticsDetailBean>> data, String message) {
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

        logisticsModel.cancel();
    }
}
