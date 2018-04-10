package com.android.p2pflowernet.project.view.fragments.forum.textdetails;

import com.android.p2pflowernet.project.entity.ForumDetailsBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by zhangkun on 2018/1/23.
 */

public class ITextDetailsPresenter extends IPresenter<ITextDetailsView> {
    ITextDetailsModel iTextDetailsModel;

    public ITextDetailsPresenter() {
        this.iTextDetailsModel = new ITextDetailsModel();
    }

    @Override
    protected void cancel() {
        iTextDetailsModel.cancel();
    }

    //获取内容详情
    public void getForumContent(){
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String id = getView().getCourseId();
        int page = getView().getPage();

        getView().showDialog();
        iTextDetailsModel.getForumContent(id, page, new IModelImpl<ApiResponse<ForumDetailsBean>, ForumDetailsBean>() {
            @Override
            protected void onSuccess(ForumDetailsBean data, String message) {
                getView().hideDialog();
                getView().onSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<ForumDetailsBean>> data, String message) {
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
