package com.android.p2pflowernet.project.view.fragments.mine.setting.personal;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.ImgDataBean;
import com.android.p2pflowernet.project.entity.PersonInfo;
import com.android.p2pflowernet.project.entity.ShowPersonInfo;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by caishen on 2017/11/15.
 * by--个人信息逻辑层
 */

public class IpersonalPerenter extends IPresenter<IpersonalView> {

    private final PersonalModel personalModel;

    @Override
    protected void cancel() {

        personalModel.cancel();
    }

    public IpersonalPerenter() {

        personalModel = new PersonalModel();

    }

    //修改个人信息的接口
    public void updatePersonal() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        int sex = getView().getSex();
        String userName = getView().getUserName();

        if (TextUtils.isEmpty(userName)) {
            getView().onError("昵称不能为空！");
            return;
        }

        int userId = getView().getUserId();
        String brithDay = getView().getBrithDay();
        String filePath = getView().getFilePath();

        //加载进度条
        getView().showDialog();
        personalModel.updatePersonal(userName, userId, sex, brithDay, filePath,
                new IModelImpl<ApiResponse<PersonInfo>, PersonInfo>() {
                    @Override
                    protected void onSuccess(PersonInfo data, String message) {

                        getView().hideDialog();
                        getView().setPersonInfo(data);
                    }

                    @Override
                    protected void onSuccess(ArrayList<ApiResponse<PersonInfo>> data, String message) {

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

    public void mofityPhoto() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        int userId = getView().getUserId();
        List<File> userImgList = getView().getUserImgList();
        //加载进度条
        getView().showDialog();
        String type = getView().getType();

        personalModel.mofityPhoto(type, userImgList, new IModelImpl<ApiResponse<ImgDataBean>, ImgDataBean>() {
            @Override
            protected void onSuccess(ImgDataBean data, String message) {
                getView().hideDialog();
                getView().mofityPhoto(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<ImgDataBean>> data, String message) {

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


    //1.获取个人信息
    public void showPersonalInfo() {
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        //加载进度条
        getView().showDialog();
        personalModel.showPersonalInfo(new IModelImpl<ApiResponse<ShowPersonInfo>, ShowPersonInfo>() {
            @Override
            protected void onSuccess(ShowPersonInfo data, String message) {
                getView().hideDialog();
                getView().setShowPersonInfo(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<ShowPersonInfo>> data, String message) {

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
