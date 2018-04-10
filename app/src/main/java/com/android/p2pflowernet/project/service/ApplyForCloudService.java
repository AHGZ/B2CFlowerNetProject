package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.CloudInfoBean;
import com.rxy.netlib.http.ApiResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by caishen on 2017/11/22.
 * by--申请云工
 */

public interface ApplyForCloudService {

    @FormUrlEncoded
    @POST(ApiUrlConstant.ADDCLOUD)
    Observable<ApiResponse<String>> addCloud(@Header("sign") String sign,@Header("token") String token, @FieldMap HashMap<String, String> options);

    @FormUrlEncoded
    @POST(ApiUrlConstant.GETCLOUD)
    Observable<ApiResponse<CloudInfoBean>> getCloud(@Header("sign") String sign,@Header("token") String token, @FieldMap HashMap<String, String> options);

    @FormUrlEncoded
    @POST(ApiUrlConstant.UPDATELOUD)
    Observable<ApiResponse<String>> updataCloud(@Header("sign") String sign,@Header("token") String token, @FieldMap HashMap<String, String> options);
}
