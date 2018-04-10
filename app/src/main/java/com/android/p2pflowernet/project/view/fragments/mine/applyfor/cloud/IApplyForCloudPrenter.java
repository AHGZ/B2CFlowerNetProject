package com.android.p2pflowernet.project.view.fragments.mine.applyfor.cloud;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.CloudInfoBean;
import com.android.p2pflowernet.project.entity.IdEntityBean;
import com.android.p2pflowernet.project.entity.ImgDataBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.IDCardValidate;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.partner.IApplyForPartnerModel;
import com.android.p2pflowernet.project.view.fragments.mine.setting.personal.PersonalModel;
import com.rxy.netlib.http.ApiResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by caishen on 2017/11/22.
 * by--申请云工的逻辑层
 */

public class IApplyForCloudPrenter extends IPresenter<IApplyForCloudView> {

    private final PersonalModel personalModel;
    private final ApplyForCloudModel applyForCloudModel;
    private final IApplyForPartnerModel iApplyForPartnerModel;

    @Override
    protected void cancel() {

    }

    public IApplyForCloudPrenter() {

        personalModel = new PersonalModel();
        applyForCloudModel = new ApplyForCloudModel();
        iApplyForPartnerModel = new IApplyForPartnerModel();

    }

    //上传图片返回地址
    public void uploadImg() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String userId = getView().getUserId();
        List<File> fileList = getView().getfileList();

        if (fileList.isEmpty()) {

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

    //添加云工
    public void addCloud() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String backPhoto = getView().getBackPhoto();
        String frontPhoto = getView().getFrontPhoto();
        String realName = getView().getRealName();
        String sex = getView().getSex();
        String idNumber = getView().getIdNumber();
        if (TextUtils.isEmpty(realName) || realName.equals("")) {
            getView().onError("请输入真实姓名");
            return;
        }
        if (TextUtils.isEmpty(idNumber) && idNumber.equals("")) {
            getView().onError("请输入身份证号");
            return;
        }
        // 判断是否符合身份证号码的规范
        boolean checkIDCard = IDCardValidate.checkIDCard(idNumber);
        if (!checkIDCard) {//有效返回""，无效返回错误信息
            getView().onError("请填写正确的身份证号");
            return;
        }

        if (TextUtils.isEmpty(sex) || sex.equals("0")) {
            getView().onError("请选择性别");
            return;
        }
        if (TextUtils.isEmpty(frontPhoto) || frontPhoto.equals("")) {
            getView().onError("请上传身份证正面照片");
            return;
        }

        if (TextUtils.isEmpty(backPhoto) || backPhoto.equals("")) {
            getView().onError("请上传身份证反面照片");
            return;
        }
        getView().showDialog();
        applyForCloudModel.addCloud(realName, sex, idNumber, frontPhoto, backPhoto, new IModelImpl<ApiResponse<String>, String>() {
            @Override
            protected void onSuccess(String data, String message) {
                getView().hideDialog();
                getView().onSuccess(message);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<String>> data, String message) {
                getView().hideDialog();
                getView().successed(message);
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

    //获取云工信息
    public void getCloudInfo() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String cloudId = getView().getCloudId();
        String statue = getView().getStatue();

        getView().showDialog();

        applyForCloudModel.getCloudInfo(cloudId, statue, new IModelImpl<ApiResponse<CloudInfoBean>, CloudInfoBean>() {
            @Override
            protected void onSuccess(CloudInfoBean data, String message) {
                getView().hideDialog();
                getView().setGetCloudSuccessInfo(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<CloudInfoBean>> data, String message) {
                getView().hideDialog();
            }

            @Override
            protected void onFailure(String code, String message) {
                getView().onError(message);
            }

            @Override
            protected void onSuccess() {

            }
        });
    }

    //修改云工的信息
    public void updataCloud() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String cloudId = getView().getCloudId();
        String backPhoto = getView().getBackPhoto();
        String frontPhoto = getView().getFrontPhoto();
        String realName = getView().getRealName();
        String sex = getView().getSex();
        String idNumber = getView().getIdNumber();
        if (TextUtils.isEmpty(realName) || realName.equals("")) {
            getView().onError("请输入真实姓名");
            return;
        }
        if (TextUtils.isEmpty(idNumber) || idNumber.equals("")) {
            getView().onError("请输入身份证号");
            return;
        }
        // 判断是否符合身份证号码的规范
        boolean checkIDCard = IDCardValidate.checkIDCard(idNumber);
        if (!checkIDCard) {//有效返回""，无效返回错误信息
            getView().onError("请填写正确的身份证号");
            return;
        }
        if (TextUtils.isEmpty(sex) || sex.equals("0")) {
            getView().onError("请选择性别");
            return;
        }
        if (TextUtils.isEmpty(frontPhoto) || frontPhoto.equals("")) {
            getView().onError("请上传身份证正面照片");
            return;
        }
        if (TextUtils.isEmpty(backPhoto) || backPhoto.equals("")) {
            getView().onError("请上传身份证反面照片");
            return;
        }
        getView().showDialog();
        applyForCloudModel.upodataCloud(cloudId, backPhoto, frontPhoto, realName, sex, idNumber, new IModelImpl<ApiResponse<String>, String>() {
            @Override
            protected void onSuccess(String data, String message) {
                getView().hideDialog();
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<String>> data, String message) {
                getView().onSuccess(message);
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

    //检测是否实名认证过
    public void checkIdentity() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        getView().showDialog();
        iApplyForPartnerModel.checkIdentity("", new IModelImpl<ApiResponse<IdEntityBean>, IdEntityBean>() {
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
