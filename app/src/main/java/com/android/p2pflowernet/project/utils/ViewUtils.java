package com.android.p2pflowernet.project.utils;

import android.os.Build;
import android.view.View;

import java.util.UUID;

/**
 * @描述:视图工具类
 * @创建人：zhangpeisen
 * @创建时间：2017/12/28 下午1:51
 * @修改人：zhangpeisen
 * @修改时间：2017/12/28 下午1:51
 * @修改备注：
 * @throws
 */
public class ViewUtils {

    public static int generateViewId() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return View.generateViewId();
        } else {
            return UUID.randomUUID().hashCode();
        }
    }
}
