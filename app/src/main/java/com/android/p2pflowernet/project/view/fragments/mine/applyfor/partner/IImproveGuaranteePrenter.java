package com.android.p2pflowernet.project.view.fragments.mine.applyfor.partner;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.AllPlaceDataBean;
import com.android.p2pflowernet.project.entity.ImgDataBean;
import com.android.p2pflowernet.project.entity.ReplyBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.IDCardValidate;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.utils.ParamMatchUtils;
import com.android.p2pflowernet.project.view.fragments.mine.setting.personal.PersonalModel;
import com.rxy.netlib.http.ApiResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by caishen on 2017/11/20.
 * by--完善保单信息的逻辑层
 */

public class IImproveGuaranteePrenter extends IPresenter<IImproveGuaranteeView> {

    private final ImproveGuaranteeModel improveGuaranteeModel;
    private final PersonalModel personalModel;

    @Override
    protected void cancel() {

        improveGuaranteeModel.cancel();
    }

    public IImproveGuaranteePrenter() {

        improveGuaranteeModel = new ImproveGuaranteeModel();
        personalModel = new PersonalModel();
    }

    //获取三级区域列表
    public void getcitydata() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        getView().showDialog();
        improveGuaranteeModel.getCityDatas(new IModelImpl<ApiResponse<AllPlaceDataBean>, AllPlaceDataBean>() {
            @Override
            protected void onSuccess(AllPlaceDataBean data, String message) {
                getView().hideDialog();
                getView().setVestAddressSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<AllPlaceDataBean>> data, String message) {

                getView().hideDialog();
                getView().onSucceess(data);
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

    //填写保单信息
    public void addInsuranceInfo() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String getphone = getView().getphone();
        if (!check(getphone)) {
            getView().onError("请填写正确的手机号");
            return;
        }

        String idName = getView().getidNumber();
        if (TextUtils.isEmpty(idName) || idName.equals("")) {
            getView().onError("请填写身份证号");
            return;
        }

        // 判断是否符合身份证号码的规范
        boolean checkIDCard = IDCardValidate.checkIDCard(idName);
        if (!checkIDCard) {//有效返回""，无效返回错误信息
            getView().onError("请填写正确的身份证号");
            return;
        }

        String realname = getView().getinsurantRealname();
        if (TextUtils.isEmpty(realname) || realname.equals("")) {
            getView().onError("请填写姓名");
            return;
        }

        String address = getView().getAddress();
        if (TextUtils.isEmpty(address) || address.equals("")) {
            getView().onError("请填写户籍地址");
            return;
        }

        String cardName = getView().getcardNum();
        if (TextUtils.isEmpty(cardName) || cardName.equals("")) {
            getView().onError("请填写银行卡号");
            return;
        }

        String backPhoto = getView().getBackPhoto();
        if (TextUtils.isEmpty(backPhoto) || backPhoto.equals("")) {
            getView().onError("请上传身份证背面照片");
            return;
        }

        String frontPhoto = getView().getFrontPhoto();
        if (TextUtils.isEmpty(frontPhoto) || frontPhoto.equals("")) {
            getView().onError("请上传身份证正面照片");
            return;
        }

        String maritalState = getView().getmaritalState();
        String cityId = getView().getcityId();
        String provinceIid = getView().getprovinceIid();
        String countyId = getView().getdistictIid();

        if (TextUtils.isEmpty(cityId) || cityId.equals("") || TextUtils.isEmpty(provinceIid) || TextUtils.isEmpty(countyId)) {
            getView().onError("请选择现居地址");
            return;
        }

        String signUrl = getView().getSignUrl();
        if (TextUtils.isEmpty(frontPhoto) || frontPhoto.equals("")) {
            getView().onError("请您手动签名");
            return;
        }

        String locationAddress = getView().getlocationAddress();
        String userId = getView().getUserId();
        String recordId = getView().getRecordId();//对应资质购买ID

        getView().showDialog();

        improveGuaranteeModel.addInsuranceInfo(realname, address, cardName, idName, getphone, backPhoto, frontPhoto, maritalState
                , cityId, provinceIid, countyId, signUrl, locationAddress, userId, recordId,
                new IModelImpl<ApiResponse<ReplyBean>, ReplyBean>() {
                    @Override
                    protected void onSuccess(ReplyBean data, String message) {
                        getView().hideDialog();
                        getView().onSuccess(message);
                    }

                    @Override
                    protected void onSuccess(ArrayList<ApiResponse<ReplyBean>> data, String message) {

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

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }


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

    private boolean check(String phone) {
        if (TextUtils.isEmpty(phone)) {
            getView().onError("请填写手机号");
            return false;
        }

        if (!ParamMatchUtils.isPhoneAvailable(phone)) {
            getView().onError("请填写正确的手机号");
            return false;
        }
        return true;
    }


}
