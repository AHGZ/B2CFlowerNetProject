package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.SettingBean;
import com.android.p2pflowernet.project.entity.SplashBean;
import com.android.p2pflowernet.project.entity.VersionInfo;
import com.rxy.netlib.http.ApiResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by caishen on 2017/11/23.
 * by--
 */

public interface SettingService {

    @FormUrlEncoded
    @POST(ApiUrlConstant.GET_USER_SET)
    Observable<ApiResponse<SettingBean>> getSetting(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //更新app
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_UPDATE_APP)
    Observable<ApiResponse<VersionInfo>> updateapp(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //更新app
    @FormUrlEncoded
    @POST(ApiUrlConstant.GETADVERTISING)
    Observable<ApiResponse<SplashBean>> getAdvertising(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

}
