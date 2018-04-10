package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.UpdatePwd;
import com.android.p2pflowernet.project.entity.UserInfo;
import com.rxy.netlib.http.ApiResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * author: zhangpeisen
 * created on: 2017/11/6 上午11:18
 * description:注册
 */
public interface RegisterService {

    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_REGISTER)
    Observable<ApiResponse<UserInfo>> register(@Header("sign") String sign,@Header("token") String token, @FieldMap HashMap<String, String> options);

    @FormUrlEncoded
    @POST(ApiUrlConstant.RESET_ACCOUNT_PWD)
    Observable<ApiResponse<UpdatePwd>> updatePwd(@Header("sign") String sign,@Header("token") String token, @FieldMap HashMap<String, String> options);

}
