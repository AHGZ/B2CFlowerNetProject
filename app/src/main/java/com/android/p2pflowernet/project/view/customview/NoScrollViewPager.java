package com.android.p2pflowernet.project.view.customview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * author: zhangpeisen
 * created on: 2017/10/10 下午3:03
 * description:
 */
public class NoScrollViewPager extends ViewPager {
    private boolean isScroll;
    private int preX = 0;

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollViewPager(Context context) {
        super(context);
    }

    /**
     * 1.dispatchTouchEvent一般情况不做处理
     * ,如果修改了默认的返回值,子孩子都无法收到事件
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);   // return_ticket true;不行
    }

    /**
     * 是否拦截
     * 拦截:会走到自己的onTouchEvent方法里面来
     * 不拦截:事件传递给子孩子
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // return_ticket false;//可行,不拦截事件,
        // return_ticket true;//不行,孩子无法处理事件
        //return_ticket super.onInterceptTouchEvent(ev);//不行,会有细微移动
        if (isScroll) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }

    /**
     * 是否消费事件
     * 消费:事件就结束
     * 不消费:往父控件传
     */
    @Override
    public boolean onTouchEvent(MotionEvent even) {
        //return_ticket false;// 可行,不消费,传给父控件
        //return_ticket true;// 可行,消费,拦截事件
        //super.onTouchEvent(ev); //不行,
        //虽然onInterceptTouchEvent中拦截了,
        //但是如果viewpage里面子控件不是viewgroup,还是会调用这个方法.
        if (even.getAction() == MotionEvent.ACTION_DOWN) {
            preX = (int) even.getX();
        } else {
            if (Math.abs((int) even.getX() - preX) > 10) {
                return true;
            } else {
                preX = (int) even.getX();
            }
        }
        if (isScroll) {
            return super.onTouchEvent(even);
        } else {
            return true;// 可行,消费,拦截事件
        }
    }

    public void setScroll(boolean scroll) {
        isScroll = scroll;
    }
}
