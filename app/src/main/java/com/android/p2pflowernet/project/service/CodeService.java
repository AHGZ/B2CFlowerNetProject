package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.CheckMobileBean;
import com.android.p2pflowernet.project.entity.SendCodeBean;
import com.rxy.netlib.http.ApiResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by rxy on 17/7/24.
 */

public interface CodeService {

    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_CHECK_MOBILE)
    Observable<ApiResponse<CheckMobileBean>> checkMobile(@Header("sign") String sign, @Header("token") String token,@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_SENDMOBILECODE)
    Observable<ApiResponse<SendCodeBean>> sendCode(@Header("sign") String sign, @Header("token") String token,@Field("mobile") String mobile);
}
