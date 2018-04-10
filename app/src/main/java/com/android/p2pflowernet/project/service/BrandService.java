package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.AllSortBean;
import com.android.p2pflowernet.project.entity.BrandClassBean;
import com.android.p2pflowernet.project.entity.BrandScendBean;
import com.android.p2pflowernet.project.entity.BrandSortBean;
import com.android.p2pflowernet.project.entity.ClassifBean;
import com.rxy.netlib.http.ApiResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * author: zhangpeisen
 * created on: 2017/12/5 上午9:19
 * description: 商品分类接口
 */
public interface BrandService {

    // 商品一级分类
    @POST(ApiUrlConstant.HFW_GOODS_CATETJ)
    Observable<ApiResponse<BrandClassBean>> goodscatetj(@Header("sign") String sign, @Header("token") String token);

    // 商品二级分类
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_GOOD_CATELIST)
    Observable<ApiResponse<BrandScendBean>> goodcatelist(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 把筛选接口和商品三级分类下商品接口合并为筛选接口
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_SCREENLIST)
    Observable<ApiResponse<ClassifBean>> screenlist(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 获取某分类下全部品牌筛选
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_MORE_BRAND)
    Observable<ApiResponse<AllSortBean>> morebrand(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 点击筛选按钮
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_CLICK_FILTER)
    Observable<ApiResponse<BrandSortBean>> clickfilter(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 搜索关键词数据列表
    @FormUrlEncoded
    @POST(ApiUrlConstant.SEARCHGOODS)
    Observable<ApiResponse<ClassifBean>> getSerChList(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

}
