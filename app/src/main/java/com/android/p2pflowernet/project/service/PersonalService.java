package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.AcmIncomBean;
import com.android.p2pflowernet.project.entity.MineMyBean;
import com.android.p2pflowernet.project.entity.PersonInfo;
import com.android.p2pflowernet.project.entity.ShareCodeBean;
import com.android.p2pflowernet.project.entity.ShowPersonInfo;
import com.rxy.netlib.http.ApiResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by caishen on 2017/11/15.
 * by--个人信息
 */

public interface PersonalService {

    /**
     * 文本信息上传
     */
    @FormUrlEncoded
    @POST(ApiUrlConstant.UPDATE_USER_DETAIL_INFO)
    Observable<ApiResponse<PersonInfo>> personinfo(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 获取个人信息
    @FormUrlEncoded
    @POST(ApiUrlConstant.GET_USER_DETAIL)
    Observable<ApiResponse<ShowPersonInfo>> showPersonInfo(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> param);

    // 我的
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_USERMY)
    Observable<ApiResponse<MineMyBean>> usermy(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> param);

    // 累计收益
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_USERINCOME)
    Observable<ApiResponse<AcmIncomBean>> userincome(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> param);

    // 好友分享邀请
    @FormUrlEncoded
    @POST(ApiUrlConstant.SHARECODE)
    Observable<ApiResponse<ShareCodeBean>> getShareCode(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> param);

}
