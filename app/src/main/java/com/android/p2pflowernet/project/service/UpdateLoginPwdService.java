package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.UdateUserLoginBean;
import com.rxy.netlib.http.ApiResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by caishen on 2017/11/18.
 * by--修改登录密码
 */

public interface UpdateLoginPwdService {

    @FormUrlEncoded
    @POST(ApiUrlConstant.UPDATE_USER_PWD)
    Observable<ApiResponse<UdateUserLoginBean>> updateLoginPwd(@Header("sign") String sign,@Header("token") String token, @FieldMap HashMap<String, String> options);

}
