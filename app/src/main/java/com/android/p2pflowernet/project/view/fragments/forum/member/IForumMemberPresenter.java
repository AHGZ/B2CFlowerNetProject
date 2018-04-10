package com.android.p2pflowernet.project.view.fragments.forum.member;

import com.android.p2pflowernet.project.entity.ForumListBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by zhangkun on 2018/1/18.
 */

public class IForumMemberPresenter extends IPresenter<IForumMemberView> {
    private IForumMemberModel iForumMemberModel;

    public IForumMemberPresenter() {
        this.iForumMemberModel = new IForumMemberModel();
    }

    @Override
    protected void cancel() {
        iForumMemberModel.cancel();
    }

    //花返对应栏目列表
    public void getForumList(){
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String id = getView().getCourseId();
        int page = getView().getPage();

        getView().showDialog();
        iForumMemberModel.getForumList(id,page, new IModelImpl<ApiResponse<ForumListBean>, ForumListBean>() {
            @Override
            protected void onSuccess(ForumListBean data, String message) {
                getView().hideDialog();
                getView().onSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<ForumListBean>> data, String message) {
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
