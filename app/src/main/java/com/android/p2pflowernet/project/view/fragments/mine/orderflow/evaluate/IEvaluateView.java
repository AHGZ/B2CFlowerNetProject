package com.android.p2pflowernet.project.view.fragments.mine.orderflow.evaluate;

import com.android.p2pflowernet.project.entity.ImgDataBean;

import java.io.File;
import java.util.List;

/**
 * Created by caishen on 2017/12/5.
 * by--
 */

interface IEvaluateView {
    void onError(String s);

    void showDialog();

    List<File> getfileList();

    String getType();

    void hideDialog();

    void setUploadImgSuccess(ImgDataBean data);

    String getImgPath();

    String getContent();

    int getServiceAttitudeScore();

    int getLogisticsServiceScore();

    int getGoodsDescScore();

    String getIsAnonymous();

    String getOrderId();

    void onSuccess(String message);
}
