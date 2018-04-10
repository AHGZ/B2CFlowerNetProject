package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.AgentHistoryBean;
import com.android.p2pflowernet.project.entity.AgentInfoBean;
import com.android.p2pflowernet.project.entity.AgentQuereBean;
import com.android.p2pflowernet.project.entity.AuditHistoryBean;
import com.android.p2pflowernet.project.entity.CloudOfficeHistoryBean;
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
 * description:代理人API
 */
public interface AgentService {
    // 添加代理申请信息
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_AGENT_APPLYADD)
    Observable<ApiResponse<String>> agentapplyadd(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 获取代理人信息
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_AGENT_APPLYAALIST)
    Observable<ApiResponse<AgentInfoBean>> agentapplyaalist(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 更新代理申请状态
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_AGENT_APPLYCHANGESTATE)
    Observable<ApiResponse<String>> agentapplychangestate(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 申请排队代理人信息列表
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_GET_AGENT_QUEUE_LIST)
    Observable<ApiResponse<AgentQuereBean>> getagentqueuelist(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 审批历史
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_TRIALHISTORY)
    Observable<ApiResponse<AuditHistoryBean>> trialhistory(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 云工历史
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_CLOUDHISTORY)
    Observable<ApiResponse<CloudOfficeHistoryBean>> cloudhistory(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 代理历史
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_AGENTHISTORY)
    Observable<ApiResponse<AgentHistoryBean>> agenthistory(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

}
