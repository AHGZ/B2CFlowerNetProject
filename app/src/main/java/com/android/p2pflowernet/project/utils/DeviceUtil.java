package com.android.p2pflowernet.project.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 名称：DeviceUtil.java
 * 描述： 获取和设备相关的数据
 * 创建人：zhangpeisen
 * 创建时间：2016/11/29 19:41
 */
public class DeviceUtil {
    private static final String TAG = "DeviceUtil";

    private static int SCREEN_WIDTH, SCREEN_HEIGHT;
    private static String DEVICE_IMEI, MOBILE_NUM;

    /**
     * @param context
     * @return
     */
    public static int getScrrenWidth(Context context) {
        if (SCREEN_WIDTH == 0) {
            initWidthHeight(context);
        }
        return SCREEN_WIDTH;
    }

    public static int getScrrenHeight(Context context) {
        if (SCREEN_HEIGHT == 0) {
            initWidthHeight(context);
        }
        return SCREEN_HEIGHT;
    }

    private static void initWidthHeight(Context context) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
    }

    public static String getDeviceIMEI(Context context) {
        if (TextUtils.isEmpty(DEVICE_IMEI)) {
            initDeviceInfo(context);
        }
        return DEVICE_IMEI;
    }

    public static String getMobileNum(Context context) {
        if (TextUtils.isEmpty(MOBILE_NUM)) {
            initDeviceInfo(context);
        }

        if (TextUtils.isEmpty(MOBILE_NUM)) {
            return MOBILE_NUM;
        }

        if (MOBILE_NUM.length() > 11) {
            int len = MOBILE_NUM.length();
            int start = len - 11;
            MOBILE_NUM = MOBILE_NUM.substring(start);
        }
        return MOBILE_NUM;
    }

    private static void initDeviceInfo(Context context) {
        try {
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            DEVICE_IMEI = manager.getDeviceId();
            MOBILE_NUM = manager.getLine1Number();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取DisplayMetrics，包括屏幕高宽，密度等
     *
     * @param context
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    // 获取手机状态栏高度
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * @param @param  context
     * @param @return_ticket
     * @return String 返回类型
     * @throws
     * @Title: getNetworkType
     * @Description: 获取网络类型
     */
    public static String getNetworkType(Context context) {
        String networkType = "unknown";

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            networkType = networkInfo.getTypeName();
        }
        return networkType;
    }

    /**
     * 检测网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetWorkConnected(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi, gprs等连接的管理）
        ConnectivityManager con = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取网络连接管理的对象
        NetworkInfo networkinfo = con.getActiveNetworkInfo();
        return !(networkinfo == null || !networkinfo.isAvailable());
    }


    /**
     * @param @return_ticket
     * @return String 返回类型
     * @throws
     * @Title: getDeviceModel
     * @Description: 获取机器型号
     */
    public static String getDeviceModel() {
        return Build.MODEL;
    }

    /**
     * @param @return_ticket
     * @return String 返回类型
     * @throws
     * @Title: getDeviceOSVersionCode
     * @Description: 获取系统版本号
     */
    public static int getDeviceOSVersionCode() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * @param @return_ticket
     * @return String 返回类型
     * @throws
     * @Title: getDeviceOSVersionName
     * @Description: 获取系统版本名
     */
    public static String getDeviceOSVersionName() {
        return Build.VERSION.RELEASE;
    }

    /*
         * 获取应用名
         */
    public static String getVersionName(Context context) {
        String versionName = null;
        try {
            //获取包管理者
            PackageManager pm = context.getPackageManager();
            //获取packageInfo
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            //获取versionName
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return versionName;
    }

    /*
     * 获取应用版本
     */
    public static int getVersionCode(Context context) {

        int versionCode = 0;
        try {
            //获取包管理者
            PackageManager pm = context.getPackageManager();
            //获取packageInfo
            PackageInfo info = pm.getPackageInfo("com.android.p2pflowernet.project", PackageManager.GET_ACTIVITIES);
            //获取versionCode
            versionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return versionCode;
    }

    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }
}
