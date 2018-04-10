package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.AppTradeBean;
import com.android.p2pflowernet.project.entity.ApplyFroPayBean;
import com.android.p2pflowernet.project.entity.BanlanceBean;
import com.android.p2pflowernet.project.entity.CommitAffirmBean;
import com.android.p2pflowernet.project.entity.ExpresListBean;
import com.android.p2pflowernet.project.entity.MerchXqBean;
import com.android.p2pflowernet.project.entity.OrderDetailItemBean;
import com.android.p2pflowernet.project.entity.OrderListBean;
import com.android.p2pflowernet.project.entity.RefundBean;
import com.android.p2pflowernet.project.entity.RefundDetailBean;
import com.android.p2pflowernet.project.entity.ScanOrderBean;
import com.android.p2pflowernet.project.entity.WaitEvaluatedBean;
import com.android.p2pflowernet.project.event.UserBanclanceBean;
import com.rxy.netlib.http.ApiResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * author: zhangpeisen
 * created on: 2017/12/12 上午9:28
 * description: 订单相关的接口
 */
public interface OrderFlowService {
    // 订单列表
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_ORDER_LIST)
    Observable<ApiResponse<OrderListBean>> orderflowlist(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 订单详情
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_ORDER_DETAIL)
    Observable<ApiResponse<OrderDetailItemBean>> orderflowdetail(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //待评价
    @FormUrlEncoded
    @POST(ApiUrlConstant.GET_WAIT_COMMENT)
    Observable<ApiResponse<WaitEvaluatedBean>> getWaitComment(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //退换货列表
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_REFUND_RECORD_LISTS)
    Observable<ApiResponse<RefundBean>> refundrecordlists(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //退货/退款详情
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_REFUND_DETAIL)
    Observable<ApiResponse<RefundDetailBean>> refundrecorddetail(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //申请退货/退款
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_ADD_REFUND_RECORD)
    Observable<ApiResponse<String>> addrefundrecord(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    // 取消退款/退货申请
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_CANCEL_REFUND)
    Observable<ApiResponse<String>> cancelrefund(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //添加评价
    @FormUrlEncoded
    @POST(ApiUrlConstant.ADD_GOODS_EVAL)
    Observable<ApiResponse<String>> addGoodsEval(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //提交订单
    @FormUrlEncoded
    @POST(ApiUrlConstant.ORDER_SUBMIT)
    Observable<ApiResponse<CommitAffirmBean>> commitSel(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //取消订单
    @FormUrlEncoded
    @POST(ApiUrlConstant.CANCEL_ORDER)
    Observable<ApiResponse<String>> cancleOrder(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //确认收货
    @FormUrlEncoded
    @POST(ApiUrlConstant.CONFIRM_ORDER)
    Observable<ApiResponse<String>> affirmOrder(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //余额支付
    @FormUrlEncoded
    @POST(ApiUrlConstant.BALANCEPAY)
    Observable<ApiResponse<BanlanceBean>> balancePay(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //支付宝支付
    @FormUrlEncoded
    @POST(ApiUrlConstant.APPTRADE)
    Observable<ApiResponse<AppTradeBean>> appTrade(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //获取用户余额
    @FormUrlEncoded
    @POST(ApiUrlConstant.USER_BALANCE)
    Observable<ApiResponse<UserBanclanceBean>> getBanlance(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //申请仲裁
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_APPLY_ARBIT)
    Observable<ApiResponse<String>> applyarbit(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //取消仲裁
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_CANCEL_ARBITR)
    Observable<ApiResponse<String>> cancelarbitr(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //获取快递公司列表数据
    @POST(ApiUrlConstant.EXPRESSLIST)
    Observable<ApiResponse<ExpresListBean>> expressList(@Header("sign") String sign, @Header("token") String token);

    //删除订单
    @FormUrlEncoded
    @POST(ApiUrlConstant.DEL_ORDER_SHOW)
    Observable<ApiResponse<String>> delOrder(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //身份支付请求
    @FormUrlEncoded
    @POST(ApiUrlConstant.APPLY_PAY)
    Observable<ApiResponse<ApplyFroPayBean>> applyPay(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //取消订单
    @FormUrlEncoded
    @POST(ApiUrlConstant.O2O_CANCEL_ORDER)
    Observable<ApiResponse<String>> cancelOrder(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //线下商家详情
    @FormUrlEncoded
    @POST(ApiUrlConstant.MERCHXQ)
    Observable<ApiResponse<MerchXqBean>> getMerceInfo(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    //线下扫码下单
    @FormUrlEncoded
    @POST(ApiUrlConstant.SCANORDER)
    Observable<ApiResponse<ScanOrderBean>> payscanOrder(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

}
