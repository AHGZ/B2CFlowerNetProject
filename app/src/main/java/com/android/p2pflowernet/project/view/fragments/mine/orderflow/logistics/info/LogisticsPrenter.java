package com.android.p2pflowernet.project.view.fragments.mine.orderflow.logistics.info;

import android.text.TextUtils;

import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * @描述: 完善物流信息逻辑层
 * @创建人：zhangpeisen
 * @创建时间：2017/12/22 下午3:35
 * @修改人：zhangpeisen
 * @修改时间：2017/12/22 下午3:35
 * @修改备注：
 * @throws
 */
public class LogisticsPrenter extends IPresenter<LogisticsView> {

    private LogisticsModel logisticsModel;


    public LogisticsPrenter() {

        logisticsModel = new LogisticsModel();
    }

    /**
     * @throws
     * @描述: 获取商家收货地址
     * @方法名:
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/22 下午4:09
     * @修改人 zhangpeisen
     * @修改时间 2017/12/22 下午4:09
     * @修改备注
     */
    public void manufacaddressinfo() {
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String manufacId = getView().getManufacId();
        if (manufacId.equals("") && TextUtils.isEmpty(manufacId)) {
            return;
        }
        getView().showDialog();
        logisticsModel.manufacaddressinfo(manufacId, new IModelImpl<ApiResponse<String>, String>() {
            @Override
            protected void onSuccess(String data, String message) {
                getView().hideDialog();
                getView().onSuccess(message);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<String>> data, String message) {
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
     * @描述: 完善物流
     * @方法名:
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/22 下午4:08
     * @修改人 zhangpeisen
     * @修改时间 2017/12/22 下午4:08
     * @修改备注
     */
    public void perfectExpress() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String refundid = getView().getRefundid();
        String waybillnum = getView().getWaybillnum();
        String expressname = getView().getExpressname();
        if (refundid.equals("") && TextUtils.isEmpty(refundid)) {
            return;
        }
        if (waybillnum.equals("") && TextUtils.isEmpty(waybillnum)) {
            return;
        }
        if (expressname.equals("") && TextUtils.isEmpty(expressname)) {
            return;
        }
        getView().showDialog();
        logisticsModel.perfectExpress(refundid, waybillnum, expressname, new IModelImpl<ApiResponse<String>, String>() {
            @Override
            protected void onSuccess(String data, String message) {
                getView().hideDialog();
                getView().onSuccess(message);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<String>> data, String message) {
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
