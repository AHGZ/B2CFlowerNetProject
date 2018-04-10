package com.android.p2pflowernet.project.view.fragments.mine.message;

import com.android.p2pflowernet.project.entity.MessagesBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/11/4.
 * by--消息信息列表
 */

public class IMessagePrenter extends IPresenter<IMessageView> {

    private final MessageModel messageModel;

    @Override
    protected void cancel() {

    }


    public IMessagePrenter() {

        messageModel = new MessageModel();

    }

    //获取消息信息列表数据
    public void getMessages() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        getView().showDialog();
        messageModel.getMessages(new IModelImpl<ApiResponse<MessagesBean>, MessagesBean>() {
            @Override
            protected void onSuccess(MessagesBean data, String message) {
                getView().hideDialog();
                getView().onSuccessMessages(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<MessagesBean>> data, String message) {
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
