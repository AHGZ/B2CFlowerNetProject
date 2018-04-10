package com.android.p2pflowernet.project.view.fragments.platformdata.rebatedata;

import com.android.p2pflowernet.project.entity.ProfitBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by zhangkun on 2018/1/30.
 */

public class RebateDataPresenter extends IPresenter<IRebateDataView> {

    private RebateDataModel rebateDataModel;

    public RebateDataPresenter() {
        this.rebateDataModel = new RebateDataModel();
    }

    @Override
    protected void cancel() {
        rebateDataModel.cancel();
    }

    //获取收益数据
    public void getProfitData(){
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        getView().showDialog();
        rebateDataModel.getProfitData(new IModelImpl<ApiResponse<ProfitBean>, ProfitBean>() {
            @Override
            protected void onSuccess(ProfitBean data, String message) {
                getView().hideDialog();
                getView().onSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<ProfitBean>> data, String message) {
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
