package com.android.p2pflowernet.project.utils;

import android.content.Context;
import android.graphics.Rect;
import android.view.TouchDelegate;
import android.view.View;

/**
 * @描述: UI工具
 * @创建人：zhangpeisen
 * @创建时间：2017/10/10 上午10:13
 * @修改人：zhangpeisen
 * @修改时间：2017/10/10 上午10:13
 * @修改备注：
 * @throws
 */
public class UIUtils {
    /**
     * dip-->px
     */
    public static int dip2Px(Context context, int dip) {
        // px/dip = density;
        // density = dpi/160
        // 320*480 density = 1 1px = 1dp
        // 1280*720 density = 2 2px = 1dp

        float density = context.getResources().getDisplayMetrics().density;
        int px = (int) (dip * density + 0.5f);
        return px;
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static void setTouchDelegate(final View view, final int expandTouchWidth) {
        final View parentView = (View) view.getParent();
        parentView.post(new Runnable() {
            @Override
            public void run() {
                final Rect rect = new Rect();
                view.getHitRect(rect); // view构建完成后才能获取，所以放在post中执行
                // 4个方向增加矩形区域
                rect.top -= expandTouchWidth;
                rect.bottom += expandTouchWidth;
                rect.left -= expandTouchWidth;
                rect.right += expandTouchWidth;

                parentView.setTouchDelegate(new TouchDelegate(rect, view));
            }
        });
    }


}
