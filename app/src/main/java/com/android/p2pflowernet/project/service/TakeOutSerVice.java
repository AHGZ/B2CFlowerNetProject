package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.TakeOutBean;
import com.rxy.netlib.http.ApiResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by caishen on 2018/1/16.
 * by--
 */

public interface TakeOutSerVice {

    //添加商品
    @FormUrlEncoded
    @POST(ApiUrlConstant.SELORDER)
    Observable<ApiResponse<TakeOutBean>> getTakeOut(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //取消退款
    @FormUrlEncoded
    @POST(ApiUrlConstant.CANCELREFUND)
    Observable<ApiResponse<String>> cancelRefund(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //确认收货
    @FormUrlEncoded
    @POST(ApiUrlConstant.O2O_CONFIRM_ORDER)
    Observable<ApiResponse<String>> confirmGoods(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

}
