package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.ForumChannelBean;
import com.android.p2pflowernet.project.entity.ForumDetailsBean;
import com.android.p2pflowernet.project.entity.ForumListBean;
import com.rxy.netlib.http.ApiResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by zhangkun  on 2018/1/29.
 */

public interface ForumService {

    //花返导航
    @FormUrlEncoded
    @POST(ApiUrlConstant.GETFORUMCHANNEL)
    Observable<ApiResponse<ForumChannelBean>> getForumChannel(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //花返对应栏目列表
    @FormUrlEncoded
    @POST(ApiUrlConstant.GETFORUMLIST)
    Observable<ApiResponse<ForumListBean>> getForumList(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //花返内容
    @FormUrlEncoded
    @POST(ApiUrlConstant.GETFORUMCONTENT)
    Observable<ApiResponse<ForumDetailsBean>> getForumContent(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

}
