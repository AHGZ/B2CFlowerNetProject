package com.android.p2pflowernet.project.utils;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * author: zhangpeisen
 * created on: 2017/10/10 上午9:30
 * description: 通知开关
 */
public class NotificationCheckUtil {

    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

    public static boolean notificationIsOpen(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//API19+
            return notificationCheckFor19Up(context);
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static boolean notificationCheckFor19Up(Context context) {
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = applicationInfo.uid;
        Class appOpsClass;

        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
            int op = (int) opPostNotificationValue.get(Integer.class);
            return ((int) checkOpNoThrowMethod.invoke(appOpsManager, op, uid, pkg) == AppOpsManager.MODE_ALLOWED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public interface NotificationCheckResultListener {
        void checkResult(boolean hasOpenNotification);
    }
}