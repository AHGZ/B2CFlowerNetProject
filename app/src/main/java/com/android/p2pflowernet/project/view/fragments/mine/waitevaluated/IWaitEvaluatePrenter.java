package com.android.p2pflowernet.project.view.fragments.mine.waitevaluated;

import com.android.p2pflowernet.project.entity.WaitEvaluatedBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/12/14.
 * by--
 */

public class IWaitEvaluatePrenter extends IPresenter<IWaitEvaluateView> {

    private final WaitEvaluateModel waitEvaluateModel;

    @Override
    protected void cancel() {

        waitEvaluateModel.cancel();
    }

    public IWaitEvaluatePrenter() {

        waitEvaluateModel = new WaitEvaluateModel();
    }

    //获取待评价的列表数据
    public void getWaitComment() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        getView().showDialog();
        int page = getView().getPage();

        waitEvaluateModel.getWaitComment(page, new IModelImpl<ApiResponse<WaitEvaluatedBean>, WaitEvaluatedBean>() {
            @Override
            protected void onSuccess(WaitEvaluatedBean data, String message) {
                getView().hideDialog();
                getView().SuccessWaitEva(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<WaitEvaluatedBean>> data, String message) {
                getView().hideDialog();
            }

            @Override
            protected void onFailure(String code, String message) {
                getView().hideDialog();
                getView().onError(message);
            }

            @Override
            protected void onSuccess() {
                getView().hideDialog();
            }
        });
    }
}
