package com.android.p2pflowernet.project.view.customview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * author: zhangpeisen
 * created on: 2017/11/15 上午10:07
 * description:  解决viewpager滑动不流畅问题
 */
public class FastSrcollViewpagerView extends ViewPager {

    private int preX = 0;

    public FastSrcollViewpagerView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public FastSrcollViewpagerView(Context context) {
        super(context);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent even) {

        if (even.getAction() == MotionEvent.ACTION_DOWN) {
            preX = (int) even.getX();
        } else {
            if (Math.abs((int) even.getX() - preX) > 10) {
                return true;
            } else {
                preX = (int) even.getX();
            }
        }
        return super.onInterceptTouchEvent(even);
    }
}
