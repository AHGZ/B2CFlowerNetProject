package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.AddShopBean;
import com.android.p2pflowernet.project.entity.GoodsInventoryCount;
import com.android.p2pflowernet.project.entity.ShopCarBean;
import com.rxy.netlib.http.ApiResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * @描述:
 * @创建人：zhangpeisen
 * @创建时间：2017/12/7 下午5:02
 * @修改人：zhangpeisen
 * @修改时间：2017/12/7 下午5:02
 * @修改备注：购物车
 * @throws
 */
public interface TradeService {

    // 查看购物车
    @POST(ApiUrlConstant.CARTLIST)
    Observable<ApiResponse<ShopCarBean>> getShopCars(@Header("sign") String sign,@Header("token") String token);

    // 添加购物车
    @FormUrlEncoded
    @POST(ApiUrlConstant.ADDCART)
    Observable<ApiResponse<AddShopBean>> addShopCar(@Header("sign") String sign,@Header("token") String token, @FieldMap HashMap<String, String> options);

    /**
     * 修改库存
     *
     * @param sign
     * @param options
     * @return
     */
    @FormUrlEncoded
    @POST(ApiUrlConstant.EDIT_CART_GOODS)
    Observable<ApiResponse<GoodsInventoryCount>> editCarCount(@Header("sign") String sign,@Header("token") String token, @FieldMap HashMap<String, String> options);


    /**
     * 删除购物车中的商品
     *
     * @param sign
     * @param options
     * @return
     */
    @FormUrlEncoded
    @POST(ApiUrlConstant.DEL_CART_GOODS)
    Observable<ApiResponse<String>> delCarGoods(@Header("sign") String sign,@Header("token") String token, @FieldMap HashMap<String, String> options);

    /**
     * @param sign
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST(ApiUrlConstant.CART_GOODS_SEL)
    Observable<ApiResponse<String>> setCarGoods(@Header("sign") String sign,@Header("token") String token, @FieldMap HashMap<String, String> options);
}
