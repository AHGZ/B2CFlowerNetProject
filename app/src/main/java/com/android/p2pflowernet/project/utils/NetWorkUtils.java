package com.android.p2pflowernet.project.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.android.p2pflowernet.project.base.BaseApplication;

/**
 * 网络检测工具
 *
 * @描述:
 * @创建人：zhangpeisen
 * @创建时间：2017/10/16 下午5:47
 * @修改人：zhangpeisen
 * @修改时间：2017/10/16 下午5:47
 * @修改备注：
 * @throws
 */
public class NetWorkUtils {

    public static boolean isNetworkAvailable() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) BaseApplication.getContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
        } catch (Exception e) {
            Log.v("ConnectivityManager", e.getMessage());
        }
        return false;
    }
}
