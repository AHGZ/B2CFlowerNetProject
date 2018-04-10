package com.android.p2pflowernet.project.view.fragments.forum;

import com.android.p2pflowernet.project.entity.ForumChannelBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by zhangkun on 2018/1/18.
 */

public class IForumPresenter extends IPresenter<IForumView> {
    private IForumModel iForumModel;

    public IForumPresenter() {
        this.iForumModel = new IForumModel();
    }

    @Override
    protected void cancel() {
        iForumModel.cancel();
    }

    //获取频道首页
    public void getForumChannel(){
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        getView().showDialog();
        iForumModel.getForumChannel(new IModelImpl<ApiResponse<ForumChannelBean>, ForumChannelBean>() {
            @Override
            protected void onSuccess(ForumChannelBean data, String message) {
                getView().hideDialog();
                getView().onSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<ForumChannelBean>> data, String message) {
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
