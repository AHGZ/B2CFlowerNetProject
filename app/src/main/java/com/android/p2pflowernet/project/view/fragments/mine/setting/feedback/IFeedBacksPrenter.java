package com.android.p2pflowernet.project.view.fragments.mine.setting.feedback;

import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/11/24.
 * by--
 */

public class IFeedBacksPrenter extends IPresenter<IFeedBacksView>{

    private final IFeedBacksModel iFeedBacksModel;

    @Override
    protected void cancel() {

        iFeedBacksModel.cancel();
    }

    public IFeedBacksPrenter() {

        iFeedBacksModel = new IFeedBacksModel();
    }

    //添加意见反馈的列表
    public void addFeedBack() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String type = getView().getType();
        String content = getView().getContent();

        getView().showDialog();

        //添加意见反馈
        iFeedBacksModel.addFeedBack(type, content, new IModelImpl<ApiResponse<String>, String>() {
            @Override
            protected void onSuccess(String data, String message) {
                getView().hideDialog();
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<String>> data, String message) {

                getView().hideDialog();
            }

            @Override
            protected void onFailure(String code, String message) {
                getView().hideDialog();

                if(code.equals("0")) {
                    getView().onSuccess(message);
                }else {
                    getView().onError(message);
                }
            }

            @Override
            protected void onSuccess() {

            }
        });
    }
}
