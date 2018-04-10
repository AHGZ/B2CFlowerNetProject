package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.FeedBackBean;
import com.rxy.netlib.http.ApiResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by caishen on 2017/11/24.
 * by--意见反馈
 */

public interface FeedBackService {

    @FormUrlEncoded
    @POST(ApiUrlConstant.ADD_FEEDBACK)
    Observable<ApiResponse<String>> addFeedBack(@Header("sign") String sign,@Header("token") String token, @FieldMap HashMap<String, String> options);

    @FormUrlEncoded
    @POST(ApiUrlConstant.GET_FEED_BACK_LIST)
    Observable<ApiResponse<FeedBackBean>> getFeedBacks(@Header("sign") String sign,@Header("token") String token, @FieldMap HashMap<String, String> options);

}
