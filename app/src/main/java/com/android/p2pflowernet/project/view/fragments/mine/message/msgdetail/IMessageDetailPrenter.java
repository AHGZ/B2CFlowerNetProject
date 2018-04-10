package com.android.p2pflowernet.project.view.fragments.mine.message.msgdetail;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.MessaTypeBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.mine.message.MessageModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/11/8.
 * by--信息类型详情页面
 */

public class IMessageDetailPrenter extends IPresenter<IMessageDetailView> {

    private final MessageModel messageModel;

    @Override
    protected void cancel() {

        messageModel.cancel();
    }

    public IMessageDetailPrenter() {
        messageModel = new MessageModel();
    }

    //获取指定分类数据
    public void getNoticeList() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String type = getView().getType();
        if (TextUtils.isEmpty(type)) {
            return;
        }

        String receiver = getView().getreceiver();

        int page = getView().getPage();
        getView().showDialog();

        messageModel.getNoticeList(type, page,receiver, new IModelImpl<ApiResponse<MessaTypeBean>, MessaTypeBean>() {
            @Override
            protected void onSuccess(MessaTypeBean data, String message) {
                getView().hideDialog();
                getView().onsuccessType(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<MessaTypeBean>> data, String message) {
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
