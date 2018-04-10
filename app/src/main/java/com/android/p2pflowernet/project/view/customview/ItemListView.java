package com.android.p2pflowernet.project.view.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * @描述:商品详情页底部的ListView
 * @创建人：zhangpeisen
 * @创建时间：2017/10/18 上午10:46
 * @修改人：zhangpeisen
 * @修改时间：2017/10/18 上午10:46
 * @修改备注：
 * @throws
 */
public class ItemListView extends ListView implements AbsListView.OnScrollListener {
    private float oldX, oldY;
    private int currentPosition;

    public ItemListView(Context context) {
        super(context);
        setOnScrollListener(this);
    }

    public ItemListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnScrollListener(this);
    }

    public ItemListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnScrollListener(this);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float Y = ev.getY();
                float Ys = Y - oldY;
                float X = ev.getX();
                int [] location = new int [2];
                getLocationInWindow(location);

                //滑动到顶部让父控件重新获得触摸事件
                if (Ys > 0 && currentPosition == 0) {
                    getParent().getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;

            case MotionEvent.ACTION_DOWN:
                getParent().getParent().requestDisallowInterceptTouchEvent(true);
                oldY = ev.getY();
                oldX = ev.getX();
                break;

            case MotionEvent.ACTION_UP:
                getParent().getParent().requestDisallowInterceptTouchEvent(true);
                break;

            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        currentPosition = getFirstVisiblePosition();
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
