package com.android.p2pflowernet.project.view.fragments.mine.orderflow.evaluate;

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
 * Created by caishen on 2017/12/5.
 * by--
 */

public class IEvaluatePrenter extends IPresenter<IEvaluateView> {

    private final PersonalModel personalModel;
    private final IEvaluateModel iEvaluateModel;

    @Override
    protected void cancel() {

    }

    public IEvaluatePrenter() {

        personalModel = new PersonalModel();
        iEvaluateModel = new IEvaluateModel();
    }

    //上传图片
    public void uploadImg() {

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

    //添加商品评价
    public void addGoodsEval() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String imgPath = getView().getImgPath();
        String content = getView().getContent();

        int serviceAttitudeScore = getView().getServiceAttitudeScore();
        int logisticsServiceScore = getView().getLogisticsServiceScore();
        int goodsDescScore = getView().getGoodsDescScore();
        String isAnonymous = getView().getIsAnonymous();//匿名是否
        String orderId = getView().getOrderId();

        getView().showDialog();
        iEvaluateModel.addGoodsEval(orderId, isAnonymous, goodsDescScore,
                logisticsServiceScore, serviceAttitudeScore, content, imgPath, new IModelImpl<ApiResponse<String>, String>() {
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
