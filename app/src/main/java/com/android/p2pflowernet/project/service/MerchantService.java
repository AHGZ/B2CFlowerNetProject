package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.MerInfoBean;
import com.rxy.netlib.http.ApiResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * author: zhangpeisen
 * created on: 2017/11/27 上午9:52
 * description:商家api
 */
public interface MerchantService {
    // 添加商家信息
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_MERCHANT_APPLYADD)
    Observable<ApiResponse<String>> addmerchantapply(@Header("sign") String sign,@Header("token") String token, @FieldMap HashMap<String, String> options);

    // 获取商家申请信息
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_MERCHANT_APPLYAPLIST)
    Observable<ApiResponse<MerInfoBean>> merchantapplyaplist(@Header("sign") String sign, @Header("token") String token,@FieldMap HashMap<String, String> options);


    // 修改商家信息
    @FormUrlEncoded
    @POST(ApiUrlConstant.MERCHANT_UPDATE)
    Observable<ApiResponse<String>> updateMerchantapply(@Header("sign") String sign,@Header("token") String token, @FieldMap HashMap<String, String> options);

}
