package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.PayPwdBean;
import com.rxy.netlib.http.ApiResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by caishen on 2017/11/16.
 * by--购物车订单的支付接口
 */

public interface PayService {

    @FormUrlEncoded
    @POST(ApiUrlConstant.ADD_PWD)
    Observable<ApiResponse<PayPwdBean>> setPayPwd(@Header("sign") String sign,@Header("token") String token, @FieldMap HashMap<String, String> param);


    @FormUrlEncoded
    @POST(ApiUrlConstant.VERIFYPWD)
    Observable<ApiResponse<String>> verifyPwd(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> param);

    /**
     * 修改支付密码
     *
     * @param sign
     * @param param
     * @return
     */
    @FormUrlEncoded
    @POST(ApiUrlConstant.UPDATE_PWD)
    Observable<ApiResponse<String>> updataPayPwd(@Header("sign") String sign,@Header("token") String token, @FieldMap HashMap<String, String> param);

}
