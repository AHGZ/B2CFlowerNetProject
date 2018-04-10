package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.CouponCodeBean;
import com.android.p2pflowernet.project.entity.GoPayBean;
import com.android.p2pflowernet.project.entity.GroupFullDetailBean;
import com.android.p2pflowernet.project.entity.GroupHomeBean;
import com.android.p2pflowernet.project.entity.MerchantBean;
import com.android.p2pflowernet.project.entity.O2oAddressBean;
import com.android.p2pflowernet.project.entity.O2oGoodsInfoBean;
import com.android.p2pflowernet.project.entity.O2oHomeBean;
import com.android.p2pflowernet.project.entity.O2oIndexBean;
import com.android.p2pflowernet.project.entity.O2oorderCommitBean;
import com.android.p2pflowernet.project.entity.OrderDetailsBean;
import com.android.p2pflowernet.project.entity.SearchStoreBean;
import com.android.p2pflowernet.project.entity.ShopEvaluationBean;
import com.android.p2pflowernet.project.entity.TakeCateThreeBean;
import com.android.p2pflowernet.project.entity.TakeCateTwoBean;
import com.android.p2pflowernet.project.entity.TakeOutOrderGroupBean;
import com.rxy.netlib.http.ApiResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * author: zhangpeisen
 * created on: 2018/1/10 上午9:23
 * description: O2o外卖接口
 */
public interface O2oService {
    // 店铺商品列表
    @FormUrlEncoded
    @POST(ApiUrlConstant.O2O_GOODS_LIST)
    Observable<ApiResponse<O2oIndexBean>> o2ogoodslist(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 获取商品规格
    @FormUrlEncoded
    @POST(ApiUrlConstant.O2O_GOODS_SPEC)
    Observable<ApiResponse<String>> o2ogoodsspec(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 获取商家店铺评价
    @FormUrlEncoded
    @POST(ApiUrlConstant.O2O_EVEL_LIST)
    Observable<ApiResponse<ShopEvaluationBean>> o2oevellist(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 获取商家信息
    @FormUrlEncoded
    @POST(ApiUrlConstant.O2O_MERCH_INFO)
    Observable<ApiResponse<MerchantBean>> o2omerchinfo(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 商品明细及商品评价
    @FormUrlEncoded
    @POST(ApiUrlConstant.O2O_MERCHGOODS_LIST)
    Observable<ApiResponse<String>> o2omerchgoodslist(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 确认订单
    @FormUrlEncoded
    @POST(ApiUrlConstant.O2O_CRE_ORDER)
    Observable<ApiResponse<O2oorderCommitBean>> o2ocreorder(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 订单详情
    @FormUrlEncoded
    @POST(ApiUrlConstant.O2O_ORDER_DETAIL)
    Observable<ApiResponse<OrderDetailsBean>> o2oorderdetail(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 退款订单详情
    @FormUrlEncoded
    @POST(ApiUrlConstant.TAKE_REFUNDORDER)
    Observable<ApiResponse<OrderDetailsBean>> getrefundorder(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 去结算
    @FormUrlEncoded
    @POST(ApiUrlConstant.O2O_GO_PAY)
    Observable<ApiResponse<GoPayBean>> o2ogopay(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 确认订单
    @FormUrlEncoded
    @POST(ApiUrlConstant.ADD_USER_ADDRE)
    Observable<ApiResponse<String>> adduseraddre(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 收货地址列表
    @FormUrlEncoded
    @POST(ApiUrlConstant.GET_ADD_LIST)
    Observable<ApiResponse<O2oAddressBean>> getaddlist(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 编辑收货地址
    @FormUrlEncoded
    @POST(ApiUrlConstant.UP_USER_ADD)
    Observable<ApiResponse<String>> upuseradd(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 删除收货地址
    @FormUrlEncoded
    @POST(ApiUrlConstant.DEL_USER_ADD)
    Observable<ApiResponse<String>> deluseradd(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 查看用户订单
    @FormUrlEncoded
    @POST(ApiUrlConstant.O2O_SELORDER)
    Observable<ApiResponse<String>> o2oselorder(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 取消接单
    @FormUrlEncoded
    @POST(ApiUrlConstant.O2O_CANCELORDER)
    Observable<ApiResponse<String>> o2ocancelorder(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 申请退款
    @FormUrlEncoded
    @POST(ApiUrlConstant.O2O_APPLYORDER)
    Observable<ApiResponse<String>> o2oapplyorder(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //  外卖首页
    @FormUrlEncoded
    @POST(ApiUrlConstant.O2OSELHOME)
    Observable<ApiResponse<O2oHomeBean>> getO2oHome(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);


    //  外卖首页
    @FormUrlEncoded
    @POST(ApiUrlConstant.GET_GOODS_INFO)
    Observable<ApiResponse<O2oGoodsInfoBean>> get_goods_info(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //外卖美食二级
    @FormUrlEncoded
    @POST(ApiUrlConstant.TAKEOUT_CATE_TWO)
    Observable<ApiResponse<TakeCateTwoBean>> get_takeout_cate_two(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //外卖美食三级
    @FormUrlEncoded
    @POST(ApiUrlConstant.TAKEOUT_CATE_THREE)
    Observable<ApiResponse<TakeCateThreeBean>> get_takeout_cate_three(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //搜索外卖美食
    @FormUrlEncoded
    @POST(ApiUrlConstant.TAKE_CATE_SEARCH)
    Observable<ApiResponse<TakeCateThreeBean>> search_takeout_cate(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //  团购订单列表
    @FormUrlEncoded
    @POST(ApiUrlConstant.SELGROUPORDER)
    Observable<ApiResponse<TakeOutOrderGroupBean>> getTakeOutGroup(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 团购订单明细
    @FormUrlEncoded
    @POST(ApiUrlConstant.GET_GROUP_ORDER_DETAIL)
    Observable<ApiResponse<GroupFullDetailBean>> getGroupOrderDetail(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //  取消团购订单
    @FormUrlEncoded
    @POST(ApiUrlConstant.CANCELGROUPORDER)
    Observable<ApiResponse<String>> cancelGroupOrder(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //  团购退订单
    @FormUrlEncoded
    @POST(ApiUrlConstant.REFUNDGROUPORDER)
    Observable<ApiResponse<String>> refundGroupOrder(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //  查看用户团购码
    @FormUrlEncoded
    @POST(ApiUrlConstant.SELCODE)
    Observable<ApiResponse<CouponCodeBean>> selCode(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //  查看团购首页数据
    @FormUrlEncoded
    @POST(ApiUrlConstant.O2O_GROUP_SELHOME)
    Observable<ApiResponse<GroupHomeBean>> getGroupHomeData(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //  搜索团购首页数据
    @FormUrlEncoded
    @POST(ApiUrlConstant.O2O_GROUP_SEARCHHOME)
    Observable<ApiResponse<GroupHomeBean>> searchGroupHomeData(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //删除外卖收货地址
    @FormUrlEncoded
    @POST(ApiUrlConstant.DEL_USER_ADD)
    Observable<ApiResponse<String>> deleteAdress(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    @FormUrlEncoded
    @POST(ApiUrlConstant.O2OINDEX_SEARCHGOODS)
    Observable<ApiResponse<O2oHomeBean>> getSearchO2oHome(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    @FormUrlEncoded
    @POST(ApiUrlConstant.O2OINDEX_SEARCHSTORE_GOODS)
    Observable<ApiResponse<SearchStoreBean>> searchStore(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

}
