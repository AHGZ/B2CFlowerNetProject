package com.android.p2pflowernet.project.constant;

import com.android.p2pflowernet.project.base.BaseApplication;
import com.android.p2pflowernet.project.utils.DeviceUtil;
import com.android.p2pflowernet.project.utils.SPUtils;
import com.android.p2pflowernet.project.view.fragments.trade.pay.PayKeys;

/**
 * author: zhangpeisen
 * created on: 2017/10/9 下午12:58
 * description: 数据常量类
 */
public class Constants {

    public static final String PLAFORM = "";
    // 当前时间
    public static String Times = System.currentTimeMillis() / 1000L + "";
    // 默认时间
    public static String TIME = Times;
    // 客户端操作类型 1-Android平台 2-IOS平台 3-微信平台 4-移动网站平台 5-PC平台
    public static String RP = "1";
    //花返网 app 版本号
    public static String APPVERSION = DeviceUtil.getVersionName(BaseApplication.getContext());
    // 用户登录时返回的TOKEN
    public static String TOKEN = String.valueOf(SPUtils.get(BaseApplication.getContext(), "token", ""));
    // 1：b2c   2: o2o
    public static String CLIENT = "1";
    // 默认定位的省市区
    public static String LOCATION = "";
    // 后台固定标识
    public static String SALT = "$&*%))`(";
    // 设备token {用于友盟push}
    public static String DEVICETOKEN = String.valueOf(SPUtils.get(BaseApplication.getContext(), "devicetoken", "1"));

    //appid 微信分配的公众账号ID
    public static final String APP_ID = "wxd930ea5d5a258f4f";

    public static final String MCH_ID = "1415675302";

    public static final String API_KEY = "ganzhoualpha112114115WXZHOUALPHA";
    /*****************************************************************
     * mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
     *****************************************************************/

    /**
     * 银联支付
     */
    public static final String UNIONPAYMODE = "00";//mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
    // 商户PID
    public static final String PARTNER = PayKeys.DEFAULT_PARTNER;   //这几个用了PayKey中的方法；
    // 商户账户
    public static final String SELLER = PayKeys.DEFAULT_SELLER;

    // 商户appid
    public static final String ALPAY_APP_ID = PayKeys.APP_ID;
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = PayKeys.PRIVATE;
    // 支付宝公钥
    public static final String RSA_PUBLIC = PayKeys.PUBLIC;

    private static final int SDK_PAY_FLAG = 1;
    // sign
    public static String SIGN = "";
    // 归属地（省-市）
    public static String RTS = String.valueOf(SPUtils.get(BaseApplication.getContext(), "rts", "0"));
    // 是否已登录
    public static String ISLOGIN = "isLogin";
    // 登录时 region 地区id
    public static String REGION = String.valueOf(SPUtils.get(BaseApplication.getContext(), "region", ""));
    // 商品属性选择
    public static int SELECTPOSTION = -1;
    // 商品属性选择状态
    public static String SELECTSTATUS = "";
}

