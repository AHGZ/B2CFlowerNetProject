package com.android.p2pflowernet.project.entity;


import android.Manifest;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.android.p2pflowernet.project.base.BaseApplication;
import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.utils.LogUtils;
import com.android.p2pflowernet.project.utils.MD5Utils;


/**
 * @author zhangpeisen
 * @ClassName: URLEntity
 * @Description: TODO 构造请求服务器的url
 * @date 2016年9月12日 下午7:35:58
 */
public class URLEntity {

    private static final String TAG = "URLEntity";
    private static String macAddress;
    static TelephonyManager telephonyManager;
    private static String[] PERMISSION = {Manifest.permission.READ_PHONE_STATE};

    private URLEntity() {
    }


    private static URLEntity instance = null;

    public static URLEntity getInstance() {
        if (instance == null) {
            instance = new URLEntity();

            //解决Android7.0 TelephonyManager.getDeviceId()返回null的问题
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                instance.setImei(Settings.Secure.getString(BaseApplication.getContext().
                        getContentResolver(), Settings.Secure.ANDROID_ID));

            } else {

                getDeviceInfo();
            }
        }
        return instance;
    }


    /**
     * 调用api 接口名称，比如“login”是指登录的接口名称
     */
    private String api;
    /**
     * OKe家 合伙人Android 应用版本号，如”1.0.1”。
     */
    private String v;
    /**
     * 手机的IMEI
     */
    private String imei;
    /**
     * 手机的IMSI
     */
    private String imsi;
    /**
     * 手机的 品牌
     */
    private String mb;
    /**
     * 手机的Android 版本号
     */
    private String mv;
    /**
     * 请求的时间戳以秒计算，正常情况下api的t允许有15分钟误差。t以1970-1-1 00:00:00为基准，取秒数
     */
    // private String t;
    /**
     * 签名参数 md5(SecretKey+api+v+imei+imsi+t)
     * 这里的SecretKey是一个在App端的常量，是保密码，只有App开发和后台接口开发知道。gonewiththewind009966
     */
    private String sign;

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getMb() {
        return mb;
    }

    public void setMb(String mb) {
        this.mb = mb;
    }

    public String getMv() {
        return mv;
    }

    public void setMv(String mv) {
        this.mv = mv;
    }


    public String getT() {
        return System.currentTimeMillis() / 1000L + "";
    }

    /*
     * public void setT(String t) { this.t = t; }
     */
    public String getSign(String api, String time) {
        sign = MD5Utils.MD5To32("gonewiththewind009966" + api + Constants.APPVERSION + (imei == null ? "" : imei)
                + (imsi == null ? "" : imsi) + time);
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }



    public static void getDeviceInfo() {
        try {
            telephonyManager = (TelephonyManager) BaseApplication.getContext()
                    .getSystemService(Context.TELEPHONY_SERVICE);
        } catch (Exception e) {
            // TODO: handle exception
        }
        instance.setV(Constants.APPVERSION);
        // test imei=null start
        String imei = telephonyManager.getDeviceId();

        LogUtils.e("URLEntity", "直接获取出来的imei=" + imei);
        // imei = "00000";
        if (imei != null && !imei.equals("") && !imei.equals("null") && !imei.contains("00000")
                && !imei.contains("11111") && !imei.contains("22222") && !imei.contains("33333")
                && !imei.contains("44444") && !imei.contains("55555") && !imei.contains("66666")
                && !imei.contains("77777") && !imei.contains("88888") && !imei.contains("99999")
                && !imei.contains("123456") && !imei.contains("abcd")) {
            LogUtils.e("URLEntity", "IMEI合法！");

            instance.setImei(telephonyManager.getDeviceId());

        } else {

            LogUtils.e("URLEntity", "IMEI不合法！");
            // 若imei==null则使用md5(mc+主板信息+mode型号+cpu指令集类型)作为imei;
            final WifiManager wifi = (WifiManager) BaseApplication.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifi.getConnectionInfo();
            macAddress = wifiInfo.getMacAddress();
            LogUtils.e("URLEntity", "MacAddress=" + macAddress);
            // 开机之后没有打开过wifi得到的mac地址是null
            if (macAddress == null && !wifi.isWifiEnabled()) {
                new Thread() {
                    @Override
                    public void run() {
                        wifi.setWifiEnabled(true);
                        for (int i = 0; i < 10; i++) {
                            WifiInfo _info = wifi.getConnectionInfo();
                            macAddress = _info.getMacAddress();
                            // LogUtils.e("URLEntity",
                            // "for循环中的MacAddress="+macAddress);
                            if (macAddress != null)
                                break;
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        wifi.setWifiEnabled(false);
                    }
                }.start();
            }
            LogUtils.e("URLEntity", "线程下面继续执行，准备md5(..)设置imei时候的macAddress=" + macAddress);
            // md5(mc+主板信息+mode型号+cpu指令集类型)作为imei
            instance.setImei(MD5Utils.MD5To32(macAddress + Build.BOARD + Build.MODEL + Build.CPU_ABI));
        }

        instance.setImsi(telephonyManager.getSubscriberId());
        String mb = Build.MODEL;
        if (mb.toLowerCase().startsWith("ip")) {
            mb = mb.replace('p', 'q').replace('P', 'q');
        }
        instance.setMb(mb);
        // instance.setMb(android.os.Build.MODEL.replace('p',
        // 'q').replace('P', 'q'));
        instance.setMv(Build.VERSION.RELEASE);
    }


}
