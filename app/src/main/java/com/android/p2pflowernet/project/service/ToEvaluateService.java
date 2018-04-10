package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.TakeOutToEvaluateGoodsBean;
import com.rxy.netlib.http.ApiResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by zhangkun on 2018/1/22.
 * 评价相关接口
 */

public interface ToEvaluateService {

    //去评价
    @FormUrlEncoded
    @POST(ApiUrlConstant.GO_ORDER_EVAL)
    Observable<ApiResponse<TakeOutToEvaluateGoodsBean>> getEvaluateData(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 外卖评价
    @FormUrlEncoded
    @POST(ApiUrlConstant.TAKEOUT_EVALUATE)
    Observable<ApiResponse<String>> takeoutEvaluate(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 团购评价
    @FormUrlEncoded
    @POST(ApiUrlConstant.GROUP_EVALUATE)
    Observable<ApiResponse<String>> groupEvaluate(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);
}
