package com.android.p2pflowernet.project.view.fragments.mine.applyfor.cloud;

import com.android.p2pflowernet.project.entity.CloudInfoBean;
import com.android.p2pflowernet.project.entity.IdEntityBean;
import com.android.p2pflowernet.project.entity.ImgDataBean;

import java.io.File;
import java.util.List;

/**
 * Created by caishen on 2017/11/22.
 * by--申请云工的视图层
 */

public interface IApplyForCloudView {
    void showDialog();

    String getUserId();

    List<File> getfileList();

    void hideDialog();

    void setUploadImgSuccess(ImgDataBean data);

    void onError(String message);

    String getBackPhoto();

    String getFrontPhoto();

    String getRealName();

    String getSex();

    String getIdNumber();

    void successed(String message);

    String getCloudId();

    String getStatue();

    void setGetCloudSuccessInfo(CloudInfoBean data);

    void onSuccess(String message);

    String getType();

    void setGetIdentitySuccess(IdEntityBean data);
}
