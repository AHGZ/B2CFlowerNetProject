package com.android.p2pflowernet.project.view.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @描述:达到使ListView适应ScrollView的效果
 * @创建人：zhangpeisen
 * @创建时间：2017/10/18 上午10:53
 * @修改人：zhangpeisen
 * @修改时间：2017/10/18 上午10:53
 * @修改备注：
 * @throws
 */
public class MyListView extends ListView {
    private Context mContext;
    private int mMaxXOverscrollDistance;
    private static final int MAX_X_OVERSCROLL_DISTANCE = 100;

    public MyListView(Context context) {
        super(context);
        this.mContext = context;
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public MyListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
    }

    /**
     * 重写该方法，达到使ListView适应ScrollView的效果
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

/*    private void initBounceDistance() {
        final DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        mMaxXOverscrollDistance = (int) (metrics.density * MAX_X_OVERSCROLL_DISTANCE);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        //这块是关键性代码,实现弹性效果
        return_ticket super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, mMaxXOverscrollDistance, maxOverScrollY, isTouchEvent);
    }*/
}