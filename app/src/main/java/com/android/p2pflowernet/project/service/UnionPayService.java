package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.entity.UnionPayEntity;
import com.rxy.netlib.http.ApiResponse;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * author: zhangpeisen
 * created on: 2017/11/6 上午11:18
 * description:银联支付
 */
public interface UnionPayService {
    @POST("http://101.231.204.84:8091/sim/getacptn")
    Observable<ApiResponse<UnionPayEntity>> unionpay();
}
