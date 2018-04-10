package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.AdressMangerBean;
import com.rxy.netlib.http.ApiResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by caishen on 2017/11/23.
 * by--地址管理
 */

public interface AdressMangerService {


    @FormUrlEncoded
    @POST(ApiUrlConstant.GET_USER_ADDRESS_LIST)
    Observable<ApiResponse<AdressMangerBean>> getAdressList(@Header("sign") String sign,@Header("token") String token, @FieldMap HashMap<String, String> options);

    @FormUrlEncoded
    @POST(ApiUrlConstant.ADD_UPDATE)
    Observable<ApiResponse<String>> addUpdateAdress(@Header("sign") String sign,@Header("token") String token, @FieldMap HashMap<String, String> options);

    @FormUrlEncoded
    @POST(ApiUrlConstant.DEL_USER_ADDRESS)
    Observable<ApiResponse<String>> deleteAdress(@Header("sign") String sign,@Header("token") String token, @FieldMap HashMap<String, String> param);
}
