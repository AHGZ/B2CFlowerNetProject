package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.GroupBuyingBean;
import com.android.p2pflowernet.project.entity.GroupEvaluationBean;
import com.android.p2pflowernet.project.entity.GroupPutOrderBean;
import com.rxy.netlib.http.ApiResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by heguozhong on 2018/1/17/017.
 * 团购
 */

public interface GroupBuyingService {
    //团购查看详情
    @FormUrlEncoded
    @POST(ApiUrlConstant.GROUP_BUYING_DETAILS)
    Observable<ApiResponse<GroupBuyingBean>> getGroupBuyingDetail(@Header("sign") String sign, @Header("token") String token, @FieldMap Map<String, String> options);

    //团购获取剩余数量
    @FormUrlEncoded
    @POST(ApiUrlConstant.GROUP_GET_NUM)
    Observable<ApiResponse<String>> getGroupNum(@Header("sign") String sign, @Header("token") String token, @FieldMap Map<String, String> options);

    //团购提交订单
    @FormUrlEncoded
    @POST(ApiUrlConstant.GROUP_SUBMIT_ORDER)
    Observable<ApiResponse<GroupPutOrderBean>> submitOrder(@Header("sign") String sign, @Header("token") String token, @FieldMap Map<String, String> options);

    //团购全部评论
    @FormUrlEncoded
    @POST(ApiUrlConstant.GROUP_ALL_EVALUATION)
    Observable<ApiResponse<GroupEvaluationBean>> getGroupEvaluation(@Header("sign") String sign, @Header("token") String token, @FieldMap Map<String, String> options);
}
