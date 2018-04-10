package com.android.p2pflowernet.project.utils;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.android.p2pflowernet.project.R;

/**
 * author: zhangpeisen
 * created on: 2017/10/24 下午5:05
 * description:
 */
public class StatusUtil {
    private View statusBarView;
    private Activity activity;

    public StatusUtil(Activity activity) {
        this.activity = activity;
    }

    /**
     * @throws
     * @描述:
     * @方法名: * useThemestatusBarColor : 0代表渐变色 ，1代表白色,2代表深绿色3代表o2o红色
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/10/24 下午6:38
     * @修改人 zhangpeisen
     * @修改时间 2017/10/24 下午6:38
     * @修改备注
     */
    public void initStatusBar(int useThemestatusBarColor) {
        if (statusBarView == null) {
            int identifier = activity.getResources().getIdentifier("statusBarBackground", "id", "android");
            statusBarView = activity.getWindow().findViewById(identifier);
        }
        if (statusBarView != null) {
            switch (useThemestatusBarColor) {
                case 0:
                    statusBarView.setBackgroundColor(activity.getResources().getColor(R.color.colorstart));
                    break;
                case 1:
                    statusBarView.setBackgroundColor(Color.WHITE);
                    break;
                case 2:
                    statusBarView.setBackgroundColor(activity.getResources().getColor(R.color.colorAccent));
                    break;
                default:
                    statusBarView.setBackgroundColor(activity.getResources().getColor(R.color.colorstart));
                    break;
                case 3:
                    statusBarView.setBackgroundColor(activity.getResources().getColor(R.color.coloro2o));
                    break;
                case 4://透明色
                    statusBarView.setBackgroundColor(activity.getResources().getColor(R.color.tmcolor));
                    break;
            }
        }
    }
}
