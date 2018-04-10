package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.MessaDetailBean;
import com.android.p2pflowernet.project.entity.MessaTypeBean;
import com.android.p2pflowernet.project.entity.MessagesBean;
import com.rxy.netlib.http.ApiResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by caishen on 2017/12/28.
 * by--消息
 */

public interface MessageService {


    // 获取消息中心
    @FormUrlEncoded
    @POST(ApiUrlConstant.GET_NOTICE_ALL)
    Observable<ApiResponse<MessagesBean>> getMessages(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 取指定分类消息列表
    @FormUrlEncoded
    @POST(ApiUrlConstant.GET_NOTICE_LIST)
    Observable<ApiResponse<MessaTypeBean>> getNoticeList(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 获取消息详情并更改状态
    @FormUrlEncoded
    @POST(ApiUrlConstant.GET_NOTICE_INFO)
    Observable<ApiResponse<MessaDetailBean>> getNoticeInfo(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

}
