package com.android.p2pflowernet.project.view.fragments.mine.applyfor.partner;

import com.android.p2pflowernet.project.entity.AllPlaceDataBean;
import com.android.p2pflowernet.project.entity.ImgDataBean;
import com.rxy.netlib.http.ApiResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by caishen on 2017/11/20.
 * by--
 */

interface IImproveGuaranteeView {

    void showDialog();

    void hideDialog();

    void setVestAddressSuccess(AllPlaceDataBean data);

    void onError(String message);

    void onSucceess(ArrayList<ApiResponse<AllPlaceDataBean>> data);

    String getmaritalState();

    String getAddress();

    String getprovinceIid();

    String getcityId();

    String getdistictIid();

    String getlocationAddress();

    String getBackPhoto();

    String getFrontPhoto();

    String getSignUrl();

    String getinsurantRealname();

    String getidNumber();

    String getphone();

    String getcardNum();

    String getUserId();

    List<File> getfileList();

    void setUploadImgSuccess(ImgDataBean data);

    void onSuccess(String message);

    String getType();

    String getRecordId();
}
