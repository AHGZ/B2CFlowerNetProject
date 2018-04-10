package com.android.p2pflowernet.project.view.fragments.mine.applyfor.partner;

import com.android.p2pflowernet.project.entity.AddAuthInfoBean;
import com.android.p2pflowernet.project.entity.AllPlaceDataBean;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2017/11/20.
 * by--
 */

public interface IImproveInfoView {
    String getUserId();

    void hideDialog();

    void onError(String message);

    void setAddAuthInfoSuccess(AddAuthInfoBean data);

    void showDialog();

    void setVestAddressSuccess(AllPlaceDataBean data);

    void onSucceessAllData(ArrayList<ApiResponse<AllPlaceDataBean>> data);

    String getmaritalState();

    String getAddress();

    String getprovinceIid();

    String getcityId();

    String getdistictIid();

    String getlocationAddress();

    void onSucessAddInsuranceInfo(String message);

    void onSuccess(String message);
}
