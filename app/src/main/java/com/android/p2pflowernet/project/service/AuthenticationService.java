package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.rxy.netlib.http.ApiResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by zhangkun on 2018/3/26.
 * 实名认证
 */

public interface AuthenticationService {

    // 获取代理人信息
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_USER_AUTHENTICATION)
    Observable<ApiResponse<String>> authentication(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

}
