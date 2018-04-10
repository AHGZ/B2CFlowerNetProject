package com.android.p2pflowernet.project.o2omain.fragment.mine.group_take_out.to_evaluate;

import com.android.p2pflowernet.project.entity.ImgDataBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.mine.setting.personal.PersonalModel;
import com.rxy.netlib.http.ApiResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangkun on 2018/1/22.
 */

public class IGroupTakeOutToEvaluatePrenter extends IPresenter<IGroupTakeOutToEvaluateView> {

    private GroupTakeOutToEvaluateModel groupTakeOutToEvaluateModel;
    private PersonalModel personalModel;

    public IGroupTakeOutToEvaluatePrenter() {
        groupTakeOutToEvaluateModel = new GroupTakeOutToEvaluateModel();
        personalModel = new PersonalModel();
    }

    @Override
    protected void cancel() {
        groupTakeOutToEvaluateModel.cancel();
        personalModel.cancel();
    }

    //上传图片
    public void uploadImg(){
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        List<File> fileList = getView().getfileList();

        if (fileList.size() < 0) {

            getView().onError("没有选择图片");
            return;
        }

        String type = getView().getType();
        getView().showDialog();
        personalModel.mofityPhoto(type, fileList, new IModelImpl<ApiResponse<ImgDataBean>, ImgDataBean>() {
            @Override
            protected void onSuccess(ImgDataBean data, String message) {
                getView().hideDialog();
                getView().setUploadImgSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<ImgDataBean>> data, String message) {
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

    //发布评论
    public void releseEvaluate(){
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        int merchId = getView().merchId();//商家ID
        int groupId = getView().groupId();//团购ID
        int orderNumber = getView().orderNumber();//订单号
        int score = getView().score();//评分
        int isAnonymous = getView().isAnonymous();//是否匿名 0-不匿名 1-匿名
        String toEvaluateContext = getView().toEvaluateContext();//评价内容
        String imgs = getView().imgs();//店铺评价配图

        getView().showDialog();
        groupTakeOutToEvaluateModel.toEvaluate(merchId, groupId, orderNumber, score, toEvaluateContext, isAnonymous, imgs, new IModelImpl<ApiResponse<String>, String>() {
            @Override
            protected void onSuccess(String data, String message) {
                getView().hideDialog();
                getView().onSuccess(message);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<String>> data, String message) {
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
