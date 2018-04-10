package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.AllPlaceDataBean;
import com.android.p2pflowernet.project.entity.DataBean;
import com.rxy.netlib.http.ApiResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * author: zhangpeisen
 * created on: 2017/11/6 上午11:18
 * description:城市列表数据
 */
public interface CityDataService {

    @FormUrlEncoded
    @POST(ApiUrlConstant.GET_PROVINCE_CITY)
    Observable<ApiResponse<DataBean>> setCity(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    @FormUrlEncoded
    @POST(ApiUrlConstant.GET_ALL_REGION)
    Observable<ApiResponse<AllPlaceDataBean>> setAllCity(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

}
