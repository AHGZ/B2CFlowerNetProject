package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.ChangePhoneBean;
import com.rxy.netlib.http.ApiResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by caishen on 2017/11/18.
 * by--更换手机号
 */

public interface ChangePhoneService {

    @FormUrlEncoded
    @POST(ApiUrlConstant.CHANGE_PHONE)
    Observable<ApiResponse<ChangePhoneBean>> changePhone(@Header("sign") String sign,@Header("token") String token, @FieldMap HashMap<String, String> options);

}
