package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.ApplyForHistoryBean;
import com.android.p2pflowernet.project.entity.ApplyForWaitBean;
import com.android.p2pflowernet.project.entity.StakeBean;
import com.rxy.netlib.http.ApiResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by caishen on 2017/11/23.
 * by--申请
 */

public interface ApplyForService {


    @FormUrlEncoded
    @POST(ApiUrlConstant.MORE_APPLY)
    Observable<ApiResponse<ApplyForWaitBean>> appForWait(@Header("sign") String sign,@Header("token") String token, @FieldMap HashMap<String, String> options);

    @FormUrlEncoded
    @POST(ApiUrlConstant.GET_APPLY_HISTORY)
    Observable<ApiResponse<ApplyForHistoryBean>> appForHistory(@Header("sign") String sign,@Header("token") String token, @FieldMap HashMap<String, String> options);

    @FormUrlEncoded
    @POST(ApiUrlConstant.GET_APPLY_DETAIL)
    Observable<ApiResponse<StakeBean>> getStake(@Header("sign") String sign,@Header("token") String token, @FieldMap HashMap<String, String> options);

    /**
     * 退出代理资质
     * @param sign
     * @param options
     * @return
     */
    @FormUrlEncoded
    @POST(ApiUrlConstant.EXIT_AGENT)
    Observable<ApiResponse<String>> exitAgent(@Header("sign") String sign,@Header("token") String token, @FieldMap HashMap<String, String> options);
}
