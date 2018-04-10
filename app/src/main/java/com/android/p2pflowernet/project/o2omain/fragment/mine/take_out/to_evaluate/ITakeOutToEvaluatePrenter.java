package com.android.p2pflowernet.project.o2omain.fragment.mine.take_out.to_evaluate;

import com.android.p2pflowernet.project.entity.ImgDataBean;
import com.android.p2pflowernet.project.entity.TakeOutToEvaluateGoodsBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.mine.setting.personal.PersonalModel;
import com.rxy.netlib.http.ApiResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangkun on 2018/1/20.
 */

public class ITakeOutToEvaluatePrenter extends IPresenter<ITakeOutToEvaluateView> {
    private TakeOutToEvaluateModel toEvaluateModel;
    private PersonalModel personalModel;

    public ITakeOutToEvaluatePrenter() {
        toEvaluateModel = new TakeOutToEvaluateModel();
        personalModel = new PersonalModel();
    }

    @Override
    protected void cancel() {
        toEvaluateModel.cancel();
        personalModel.cancel();
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

    //发布评论
    public void releseEvaluate() {
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String orderNumber = getView().orderNumber();//订单编号
        String shopContent = getView().shopContent();//店铺评价内容
        String shopImgs = getView().shopImgs();//店铺评价图片
        String goodsContent = getView().goodsContent();//商品评价内容
        int distribType = getView().distribType();//配送类型 1-平台配送 2-商家配送 3-到店自提
        int distribId = getView().distribId();//配送平台ID
        int distribScore = getView().distribScore();//配送评星
        int merchScore = getView().merchScore();//店铺评星
        int isAnonymous = getView().isAnonymous();//是否匿名

        getView().showDialog();
        toEvaluateModel.releseEvaluate(orderNumber, distribType, distribId,
                distribScore, merchScore, shopContent, shopImgs, goodsContent, isAnonymous, new IModelImpl<ApiResponse<String>, String>() {
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

    //获取评论页面数据
    public void getEvaluateData() {
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String orderNumber = getView().orderNumber();//订单编号
        getView().showDialog();
        toEvaluateModel.getEvaluateData(orderNumber, new IModelImpl<ApiResponse<TakeOutToEvaluateGoodsBean>, TakeOutToEvaluateGoodsBean>() {
            @Override
            protected void onSuccess(TakeOutToEvaluateGoodsBean data, String message) {
                getView().hideDialog();
                getView().onSuccessData(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<TakeOutToEvaluateGoodsBean>> data, String message) {
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
