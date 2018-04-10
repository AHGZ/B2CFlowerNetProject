package com.android.p2pflowernet.project.view.customview;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by caishen on 2017/10/21.
 * by--自定义 跑马灯效果
 */

public class NoticTextView extends AppCompatTextView {

    public NoticTextView(Context context) {
        super(context);
    }

    public NoticTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoticTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //关键在这
    @Override
    public boolean isFocused() {
        return true;
    }
}
