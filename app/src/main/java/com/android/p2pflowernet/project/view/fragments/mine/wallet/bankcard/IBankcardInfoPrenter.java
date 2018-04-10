package com.android.p2pflowernet.project.view.fragments.mine.wallet.bankcard;

import android.text.TextUtils;

import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.IDCardValidate;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/11/11.
 * by--银行卡信息逻辑层
 */

public class IBankcardInfoPrenter extends IPresenter<IBankcardInfoView> {
    BankcardModel bankcardModel;

    public IBankcardInfoPrenter() {
        bankcardModel = new BankcardModel();
    }

    public void bancardadd() {
        // 银行卡
        String bankcardNo = getView().getBankcardNo();
        // 持卡人姓名
        String realName = getView().getRealName();
        // 身份证
        String idCard = getView().getIdCard();
        boolean profile = getView().getProfile();
        if (!profile) {
            getView().onError("请勾选协议");
            return;
        }
        if (TextUtils.isEmpty(bankcardNo) && bankcardNo.equals("")) {
            getView().onError("请填写银行卡号");
            return;
        }
        if (TextUtils.isEmpty(realName) && bankcardNo.equals("")) {
            getView().onError("请填写持卡人姓名");
            return;
        }
        if (TextUtils.isEmpty(idCard) && bankcardNo.equals("")) {
            getView().onError("请填写身份证号");
            return;
        }
       // 判断是否符合身份证号码的规范
        boolean checkIDCard = IDCardValidate.checkIDCard(idCard);
        if (!checkIDCard) {//有效返回""，无效返回错误信息
            getView().onError("请填写正确的身份证号");
            return;
        }
        // 加载进度条
        getView().showDialog();
        bankcardModel.bancardadd(bankcardNo, realName, idCard, new IModelImpl<ApiResponse<String>, String>() {
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

    @Override
    protected void cancel() {
        bankcardModel.cancel();
    }
}
