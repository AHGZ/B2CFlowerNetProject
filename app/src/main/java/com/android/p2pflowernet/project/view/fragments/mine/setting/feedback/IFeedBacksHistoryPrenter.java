package com.android.p2pflowernet.project.view.fragments.mine.setting.feedback;

import com.android.p2pflowernet.project.entity.FeedBackBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/11/24.
 * by--
 */

public class IFeedBacksHistoryPrenter extends IPresenter<IFeedBacksHistoryView> {

    private final IFeedBacksHistoryModel iFeedBacksHistory;

    @Override
    protected void cancel() {

        iFeedBacksHistory.cancel();
    }

    public IFeedBacksHistoryPrenter() {

        iFeedBacksHistory = new IFeedBacksHistoryModel();

    }

    //获取意见反馈列表数据
    public void getFeedList() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        getView().showDialog();
        int page = getView().getPage();

        //添加意见反馈
        iFeedBacksHistory.getFeedBackList(page,new IModelImpl<ApiResponse<FeedBackBean>, FeedBackBean>() {
            @Override
            protected void onSuccess(FeedBackBean data, String message) {
                getView().hideDialog();
                getView().onSuccessFeedBacks(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<FeedBackBean>> data, String message) {

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
