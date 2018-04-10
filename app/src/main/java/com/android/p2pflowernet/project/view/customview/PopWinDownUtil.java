package com.android.p2pflowernet.project.view.customview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.android.p2pflowernet.project.R;


/**
 * Created by caishen on 2018/1/4.
 * by--
 */

public class PopWinDownUtil {

    private Context context;
    private View contentView;
    private View relayView;
    private PopupWindow popupWindow;
    public PopWinDownUtil(Context context, View contentView, View relayView) {
        this.context = context;
        this.contentView = contentView;
        this.relayView = relayView;
        init();
    }

    public void init() {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
        int screenHeight = (int) (height / density);// 屏幕高度(dp)

        //内容，高度，宽度
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);

        //动画效果
        popupWindow.setAnimationStyle(R.style.AnimationTopFade);

        //菜单背景色
        ColorDrawable dw = new ColorDrawable(Color.TRANSPARENT);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.setOutsideTouchable(true);

        //关闭事件
        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (onDismissLisener != null) {
                    onDismissLisener.onDismiss();
                }
            }
        });
    }

    public void show() {
        //显示位置
        popupWindow.showAsDropDown(relayView);
    }

    public void hide() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    private OnDismissLisener onDismissLisener;

    public void setOnDismissListener(OnDismissLisener onDismissLisener) {
        this.onDismissLisener = onDismissLisener;
    }

    public interface OnDismissLisener {
        void onDismiss();
    }

    public boolean isShowing() {
        return popupWindow.isShowing();
    }
}
