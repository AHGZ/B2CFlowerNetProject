package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.UserInfo;
import com.rxy.netlib.http.ApiResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by rxy on 17/7/20.
 */

public interface LoginService {

    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_LOGIN)
    Observable<ApiResponse<UserInfo>> login(@Header("sign") String sign,@Header("token") String token,@FieldMap HashMap<String, String> options);


    @POST("Login/logout.koala")
    Observable<ApiResponse<Object>> loginOut(@Header("token") String token, @Header("sign") String sign,
                                             @Header("t") String time);
}
