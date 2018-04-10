package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.ChangeGsAttrBean;
import com.android.p2pflowernet.project.entity.CompareListBean;
import com.android.p2pflowernet.project.entity.EveluateBean;
import com.android.p2pflowernet.project.entity.GoodsAttrBean;
import com.android.p2pflowernet.project.entity.GoodsInfoBean;
import com.android.p2pflowernet.project.entity.GuaranteeBean;
import com.android.p2pflowernet.project.entity.OrderDetailBean;
import com.android.p2pflowernet.project.entity.ProductParamBean;
import com.android.p2pflowernet.project.entity.ShareGoodsBean;
import com.android.p2pflowernet.project.entity.SpecCompareBean;
import com.rxy.netlib.http.ApiResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by caishen on 2017/12/5.
 * by--商品
 */

public interface GoodsInfoService {

    // 获取商品评价
    @FormUrlEncoded
    @POST(ApiUrlConstant.GET_GOODS_COMMENT)
    Observable<ApiResponse<EveluateBean>> getEveluate(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 获取商品详情
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_GOODS_XQ)
    Observable<ApiResponse<GoodsInfoBean>> getGoodsXq(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 初始化商品产品规格
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_GOODS_SPEC)
    Observable<ApiResponse<GoodsAttrBean>> getGoodsSpec(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 获取商品指定规格信息
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_GOODS_SPEC_INFO)
    Observable<ApiResponse<ChangeGsAttrBean>> getGoodsSpecInfo(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 获取商品产品参数
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_GOODS_PARAM)
    Observable<ApiResponse<ProductParamBean>> getGoodsParam(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 基础保障
    @FormUrlEncoded
    @POST(ApiUrlConstant.GUARANTEE)
    Observable<ApiResponse<GuaranteeBean>> guarantee(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);


    //确认订单
    @FormUrlEncoded
    @POST(ApiUrlConstant.ORDER_SEL)
    Observable<ApiResponse<OrderDetailBean>> orderSel(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);


    //商品分享详情
    @FormUrlEncoded
    @POST(ApiUrlConstant.SHAREGOODS)
    Observable<ApiResponse<ShareGoodsBean>> getShareGoods(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //比价列表
    @FormUrlEncoded
    @POST(ApiUrlConstant.COMPARE)
    Observable<ApiResponse<CompareListBean>> compares(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //根据规格更改比价
    @FormUrlEncoded
    @POST(ApiUrlConstant.COMPSPEC)
    Observable<ApiResponse<SpecCompareBean>> compSpec(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

}
