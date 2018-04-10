package com.android.p2pflowernet.project.service;

import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.BankInfoBean;
import com.android.p2pflowernet.project.entity.BillAllBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.UserAcountBean;
import com.android.p2pflowernet.project.entity.WithDrawInfoBean;
import com.rxy.netlib.http.ApiResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * @描述: 有关银行卡信息接口类
 * @创建人：zhangpeisen
 * @创建时间：2017/11/30 下午4:14
 * @修改人：zhangpeisen
 * @修改时间：2017/11/30 下午4:14
 * @修改备注：
 * @throws
 */
public interface BankcardService {
//    /**
//     * 个人银行卡信息验证
//     */
//    @POST(ApiUrlConstant.HFW_VERIFY_USER_BANKINFO)
//    Observable<ApiResponse<String>> bankcardverify(@Header("sign") String sign, @QueryMap HashMap<String, String> options);

    /**
     * 添加认证过的银行卡
     */
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_CARDINFOADD)
    Observable<ApiResponse<String>> bankcardadd(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    /**
     * 银行卡列表
     */
    @POST(ApiUrlConstant.HFW_CARD_LIST)
    Observable<ApiResponse<BankInfoBean>> bankcardlist(@Header("sign") String sign, @Header("token") String token);

    /**
     * 解绑银行卡
     */
    @FormUrlEncoded
    @POST(ApiUrlConstant.DELBANK)
    Observable<ApiResponse<String>> delbank(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);


    /**
     * 检测用户支付密码
     */
    @POST(ApiUrlConstant.CHECK_PWD)
    Observable<ApiResponse<CheckPwdBean>> checkPwd(@Header("sign") String sign, @Header("token") String token);

    //获取用户钱包数据
    @POST(ApiUrlConstant.USER_ACCOUNT)
    Observable<ApiResponse<UserAcountBean>> getUserAccount(@Header("sign") String sign, @Header("token") String token);

    /**
     * 账单
     */
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_WALLET_USERBILL)
    Observable<ApiResponse<BillAllBean>> walletbill(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> options);

    /**
     * 查看提现
     *
     * @param sign
     * @return
     */
    @POST(ApiUrlConstant.HFW_SELWITHDRAWALS)
    Observable<ApiResponse<WithDrawInfoBean>> selwithdrawals(@Header("sign") String sign, @Header("token") String token);

    /**
     * 提现
     *
     * @param sign
     * @param param
     * @return
     */
    @FormUrlEncoded
    @POST(ApiUrlConstant.HFW_WITHDRAWALS)
    Observable<ApiResponse<String>> withdrawals(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> param);


    //支付宝提现
    @FormUrlEncoded
    @POST(ApiUrlConstant.WITHDRAWALS)
    Observable<ApiResponse<String>> withdraw(@Header("sign") String sign, @Header("token") String token, @FieldMap HashMap<String, String> param);
}
