package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.LogisticsDetailBean;
import com.rxy.netlib.http.ApiResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * author: zhangpeisen
 * created on: 2017/12/22 下午3:37
 * description: 物流信息接口
 */
public interface LogisticService {
    // 获取商家收货地址
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_MANUFAC_ADDRESS_INFO)
    Observable<ApiResponse<String>> manufacaddressinfo(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 物流查询
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_QUERY_DELIVERY)
    Observable<ApiResponse<LogisticsDetailBean>> querydelivery(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 完善物流
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_PERFECT_EXPRESS)
    Observable<ApiResponse<String>> perfectexpress(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);
}
