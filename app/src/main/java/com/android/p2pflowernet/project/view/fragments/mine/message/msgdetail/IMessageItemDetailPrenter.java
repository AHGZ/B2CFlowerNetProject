package com.android.p2pflowernet.project.view.fragments.mine.message.msgdetail;

import com.android.p2pflowernet.project.entity.MessaDetailBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.mine.message.MessageModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/12/28.
 * by--消息详情
 */

public class IMessageItemDetailPrenter extends IPresenter<IMessageItemDetailView> {

    private final MessageModel messageModel;

    @Override
    protected void cancel() {

        messageModel.cancel();
    }

    public IMessageItemDetailPrenter() {
        messageModel = new MessageModel();
    }

    //获取消息详情
    public void getNoticeInfo() {

        String type = getView().getType();
        String noticeId = getView().getnoticeId();

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String receiver = getView().getreceiver();

        getView().showDialog();
        messageModel.getNoticeInfo(type, noticeId,receiver, new IModelImpl<ApiResponse<MessaDetailBean>, MessaDetailBean>() {
            @Override
            protected void onSuccess(MessaDetailBean data, String message) {
                getView().hideDialog();
                getView().onsuccessMessageDe(data);

            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<MessaDetailBean>> data, String message) {
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
