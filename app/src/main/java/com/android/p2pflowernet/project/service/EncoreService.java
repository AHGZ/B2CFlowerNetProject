package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.EncoreBean;
import com.rxy.netlib.http.ApiResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by zhangkun on 2018/1/25.
 */

public interface EncoreService {

    //再来一单
    @FormUrlEncoded
    @POST(ApiUrlConstant.ENCORE)
    Observable<ApiResponse<EncoreBean>> Encore(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

}
