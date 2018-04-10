package com.android.p2pflowernet.project.view.fragments.mine.applyfor.partner;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.IdEntityBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.IDCardValidate;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/11/20.
 * by--申请合伙人的逻辑层
 */

public class IApplyForPartnerPrenter extends IPresenter<IApplyForPartnerView> {

    private final IApplyForPartnerModel iApplyForPartnerModel;

    @Override
    protected void cancel() {

        iApplyForPartnerModel.cancel();
    }

    public IApplyForPartnerPrenter() {

        iApplyForPartnerModel = new IApplyForPartnerModel();
    }

    //获取姓名及身份证
    public void commit() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String idCard = getView().getIdCard();


        String name = getView().getName();
        String ischecked = getView().getIschecked();
        String s = getView().getuserId();
        if (TextUtils.isEmpty(name) && name.equals("")) {
            getView().onError("请填写姓名");
            return;
        }
        if (TextUtils.isEmpty(idCard) && idCard.equals("")) {
            getView().onError("请填写身份证号");
            return;
        }
        // 判断是否符合身份证号码的规范
        boolean checkIDCard = IDCardValidate.checkIDCard(idCard);
        if (!checkIDCard) {//有效返回""，无效返回错误信息
            getView().onError("请填写正确的身份证号");
            return;
        }
        getView().showDialog();
        //获取身份证和姓名
        iApplyForPartnerModel.commit(idCard, name, ischecked, s, new IModelImpl<ApiResponse<String>,
                String>() {
            @Override
            protected void onSuccess(String data, String message) {
                getView().onSuccess(message);
                getView().hideDialog();
                getView().setCheckIdentitySuccess(data);
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

            }
        });
    }

    //检验是否实名认证
    public void checkIdentity() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String userId = getView().getuserId();

        getView().showDialog();
        iApplyForPartnerModel.checkIdentity(userId, new IModelImpl<ApiResponse<IdEntityBean>, IdEntityBean>() {
            @Override
            protected void onSuccess(IdEntityBean data, String message) {
                getView().hideDialog();
                getView().setGetIdentitySuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<IdEntityBean>> data, String message) {
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
