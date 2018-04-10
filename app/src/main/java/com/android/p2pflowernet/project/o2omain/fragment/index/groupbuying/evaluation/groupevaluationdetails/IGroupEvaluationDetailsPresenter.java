package com.android.p2pflowernet.project.o2omain.fragment.index.groupbuying.evaluation.groupevaluationdetails;

import com.android.p2pflowernet.project.entity.GroupEvaluationBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by heguozhong on 2018/1/5/005.
 * 评论详情逻辑层
 */

public class IGroupEvaluationDetailsPresenter extends IPresenter<IGroupEvaluationDetailsView> {
    private final IGroupEvaluationDetailsModel iGroupEvaluationDetailsModel;

    public IGroupEvaluationDetailsPresenter() {
        iGroupEvaluationDetailsModel = new IGroupEvaluationDetailsModel();
    }

    //获取店铺商家信息
    public void getShopEvaluation(int merch_id, int group_id, int type, int page) {
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        getView().showDialog();
        iGroupEvaluationDetailsModel.getGroupEvaluation(merch_id,group_id,type,page, new IModelImpl<ApiResponse<GroupEvaluationBean>, GroupEvaluationBean>() {
            @Override
            protected void onSuccess(GroupEvaluationBean data, String message) {
                getView().hideDialog();
                getView().onSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<GroupEvaluationBean>> data, String message) {
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
        iGroupEvaluationDetailsModel.cancel();
    }
}
