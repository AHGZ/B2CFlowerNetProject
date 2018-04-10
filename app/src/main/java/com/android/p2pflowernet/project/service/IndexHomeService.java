package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.IndexHomeBean;
import com.rxy.netlib.http.ApiResponse;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by caishen on 2017/12/26.
 * by--首页
 */

public interface IndexHomeService {

    // 获取首页
    @POST(ApiUrlConstant.SELHOME)
    Observable<ApiResponse<IndexHomeBean>> getShopCars(@Header("sign") String sign, @Header("token") String token);

}
