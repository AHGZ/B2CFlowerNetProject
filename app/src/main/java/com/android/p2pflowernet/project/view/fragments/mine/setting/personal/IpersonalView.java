package com.android.p2pflowernet.project.view.fragments.mine.setting.personal;

import com.android.p2pflowernet.project.entity.ImgDataBean;
import com.android.p2pflowernet.project.entity.PersonInfo;
import com.android.p2pflowernet.project.entity.ShowPersonInfo;

import java.io.File;
import java.util.List;

/**
 * Created by caishen on 2017/11/15.
 * by--个人信息视图层
 */

public interface IpersonalView {

    void onError(String s);

    int getSex();

    String getUserName();

    int getUserId();

    String getBrithDay();

    void showDialog();

    void hideDialog();

    //修改个人信息
    void setPersonInfo(PersonInfo personInfo);

    // 上传头像
    void mofityPhoto(ImgDataBean imgDataBean);

    //获取个人信息
    void setShowPersonInfo(ShowPersonInfo data);

    File getUserImg();

    List<File> getUserImgList();

    String getType();

    String getFilePath();
}
