package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.AddAuthInfoBean;
import com.android.p2pflowernet.project.entity.IdEntityBean;
import com.android.p2pflowernet.project.entity.MerchTypeBean;
import com.android.p2pflowernet.project.entity.ReplyBean;
import com.rxy.netlib.http.ApiResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by caishen on 2017/11/21.
 * by--申请合伙人
 */

public interface ApplyForPartnerService {

    @FormUrlEncoded
    @POST(ApiUrlConstant.GET_IDENTITY_INFO)
    Observable<ApiResponse<IdEntityBean>> getIdInfo(@Header("sign") String sign,@Header("token") String token, @FieldMap HashMap<String, String> options);


    @FormUrlEncoded
    @POST(ApiUrlConstant.CHECK_IDENTITY)
    Observable<ApiResponse<String>> checkIdentity(@Header("sign") String sign,@Header("token") String token, @FieldMap HashMap<String, String> options);

    @FormUrlEncoded
    @POST(ApiUrlConstant.ADD_AUTH_INFO)
    Observable<ApiResponse<AddAuthInfoBean>> addAuthInfo(@Header("sign") String sign,@Header("token") String token, @FieldMap HashMap<String, String> options);

    @FormUrlEncoded
    @POST(ApiUrlConstant.ADD_AUTH_INFO)
    Observable<ApiResponse<String>> addInsuranceInfo(@Header("sign") String sign,@Header("token") String token, @FieldMap HashMap<String, String> options);

    @FormUrlEncoded
    @POST(ApiUrlConstant.ADD_INSURANCE_INFO)
    Observable<ApiResponse<ReplyBean>> addInsurancesInfo(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    @FormUrlEncoded
    @POST(ApiUrlConstant.GET_MERCH_CATE)
    Observable<ApiResponse<MerchTypeBean>> getShopType(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);


    @FormUrlEncoded
    @POST(ApiUrlConstant.ADD_MANUFAC_APPLY)
    Observable<ApiResponse<String>> add_manufac_apply(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

}
