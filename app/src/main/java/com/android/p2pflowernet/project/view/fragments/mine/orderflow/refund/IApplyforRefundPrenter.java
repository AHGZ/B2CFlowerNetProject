package com.android.p2pflowernet.project.view.fragments.mine.orderflow.refund;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.ImgDataBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.mine.orderflow.IOrderFlowModel;
import com.android.p2pflowernet.project.view.fragments.mine.setting.personal.PersonalModel;
import com.rxy.netlib.http.ApiResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by caishen on 2017/11/17.
 * by--申请退款的逻辑层
 */

public class IApplyforRefundPrenter extends IPresenter<IApplyforRefundView> {
    IOrderFlowModel iOrderFlowModel;
    private PersonalModel personalModel;

    public IApplyforRefundPrenter() {
        iOrderFlowModel = new IOrderFlowModel();
        personalModel = new PersonalModel();

    }

    public void addrefundrecord() {
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String ogid = getView().getOgid();
        String orderNum = getView().getOrderNum();
        String isreturn = getView().getIsreturn();
        String refundmoney = getView().getRefundmoney();
        String reason = getView().getReason();
        String explain = getView().getExplain();
        String imgpath = getView().getImgpath();

        if (TextUtils.isEmpty(ogid) && ogid.equals("")) {
            return;
        }
        if (TextUtils.isEmpty(orderNum) && orderNum.equals("")) {
            return;
        }
        if (TextUtils.isEmpty(isreturn) && isreturn.equals("")) {
            getView().onError("请选择货物状态");
            return;
        }
        if (TextUtils.isEmpty(refundmoney) && refundmoney.equals("")) {
            return;
        }
        if (TextUtils.isEmpty(reason) && reason.equals("")) {
            getView().onError("请选择退款原因");
            return;
        }
        if (TextUtils.isEmpty(explain) && explain.equals("")) {
            getView().onError("请填写退款说明");
            return;
        }

        getView().showDialog();

        iOrderFlowModel.addrefundrecord(ogid, orderNum, isreturn, refundmoney,
                reason, explain, imgpath, new IModelImpl<ApiResponse<String>, String>() {
                    @Override
                    protected void onSuccess(String data, String message) {
                        getView().hideDialog();
                        getView().onSuccess(message);
                    }

                    @Override
                    protected void onSuccess(ArrayList<ApiResponse<String>> data, String message) {
                        getView().hideDialog();
                        getView().onSuccess(message);
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

    //上传图片
    public void uploadImg() {

        getView().showDialog();
        List<File> fileList = getView().getfileList();
        if (fileList.isEmpty()) {
            getView().onError("没有图片上传");
            return;
        }

        String type = getView().getType();

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

    @Override
    protected void cancel() {
        iOrderFlowModel.cancel();
    }
}
