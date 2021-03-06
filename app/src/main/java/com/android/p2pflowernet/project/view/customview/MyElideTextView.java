package com.android.p2pflowernet.project.view.customview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;


/**
 * Created by caishen on 2017/10/19.
 * by--自定义划掉价格字样
 */

public class MyElideTextView extends TextView {

    private Context context;

    public MyElideTextView(Context context) {
        super(context);
        this.context = context;
    }

    public MyElideTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }


    public MyElideTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(dip2px(context, 1));
        canvas.drawLine(0, dip2px(context, 5), getMeasuredWidth(), getMeasuredHeight() - dip2px(context, 5), paint);
    }

    private int dip2px(Context context, float dipValue) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dipValue, r.getDisplayMetrics());
    }
}
