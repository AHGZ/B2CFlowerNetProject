package com.android.p2pflowernet.project.o2omain.fragment.refund;

import android.text.TextUtils;

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
 * Created by zhangkun on 2018/1/24.
 */

public class IRefundPrenter extends IPresenter<IRefundView> {

    private RefundModel refundModel;
    private PersonalModel personalModel;


    public IRefundPrenter() {
        refundModel = new RefundModel();
        personalModel = new PersonalModel();
    }

    @Override
    protected void cancel() {
        refundModel.cancel();
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

    //提交退款
    public void submitRefund(){
        String orderNumber = getView().getOrderNumber();
        String reason = getView().getReason();
        String explain = getView().getExplain();
        String img_path = getView().getImgPath();
        String amount = getView().getAmount();
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        if (TextUtils.isEmpty(reason)) {
            getView().onError("请选择退款理由");
            return;
        }

        if (TextUtils.isEmpty(explain)) {
            getView().onError("请填写退款说明");
            return;
        }

        if (TextUtils.isEmpty(reason)) {
            getView().onError("请上传图片");
            return;
        }

        getView().showDialog();
        refundModel.submitRefund(orderNumber, reason, explain, img_path, amount, new IModelImpl<ApiResponse<String>, String>() {
            @Override
            protected void onSuccess(String data, String message) {
                getView().hideDialog();
                getView().onSuccess(data);
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
