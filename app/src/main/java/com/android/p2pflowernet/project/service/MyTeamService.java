package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.AgentGrowBean;
import com.android.p2pflowernet.project.entity.AgentOfficeBean;
import com.android.p2pflowernet.project.entity.AutoWorkBean;
import com.android.p2pflowernet.project.entity.CloudGrowBean;
import com.android.p2pflowernet.project.entity.CloudOfficeBean;
import com.android.p2pflowernet.project.entity.ContriRankBean;
import com.android.p2pflowernet.project.entity.MyTeamProfitBean;
import com.rxy.netlib.http.ApiResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by caishen on 2017/12/19.
 * by--我的团队
 */

public interface MyTeamService {

    //贡献排行榜
    @FormUrlEncoded
    @POST(ApiUrlConstant.TEAMRANKING)
    Observable<ApiResponse<ContriRankBean>> getContriRank(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //团队收益
    @FormUrlEncoded
    @POST(ApiUrlConstant.TEAMPROFIT)
    Observable<ApiResponse<MyTeamProfitBean>> getTeamProfit(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //云工办公
    @POST(ApiUrlConstant.CLOUDWORK)
    Observable<ApiResponse<CloudOfficeBean>> getCloudOffice(@Header("sign") String sign, @Header("token") String token);

    //代理办公
    @POST(ApiUrlConstant.AGENTWORK)
    Observable<ApiResponse<AgentOfficeBean>> getagentWork(@Header("sign") String sign, @Header("token") String token);

    //修改审批状态
    @FormUrlEncoded
    @POST(ApiUrlConstant.AUTOWORK)
    Observable<ApiResponse<AutoWorkBean>> autoWork(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //成长计划（代理）
    @POST(ApiUrlConstant.AGENTGROWPLAN)
    Observable<ApiResponse<AgentGrowBean>> getagentGrowPlan(@Header("sign") String sign, @Header("token") String token);


    //成长计划（云工）
    @POST(ApiUrlConstant.CLOUDGROWPLAN)
    Observable<ApiResponse<CloudGrowBean>> getcloudGrowPlan(@Header("sign") String sign, @Header("token") String token);

    //手动审批
    @FormUrlEncoded
    @POST(ApiUrlConstant.TRIAL)
    Observable<ApiResponse<String>> trial(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);
}
