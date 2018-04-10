package com.android.p2pflowernet.project.view.customview;


import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @描述: 自定义的EditText右边带图片，可以设置点击事件
 * @创建人：zhangpeisen
 * @创建时间：2017/10/10 下午2:31
 * @修改人：zhangpeisen
 * @修改时间：2017/10/10 下午2:31
 * @修改备注：
 * @throws
 */
public class CustomEditText extends AppCompatEditText implements TextWatcher {
    //控件左边的图片
    private Drawable leftDrawable = null;
    //控件右边的图片
    private Drawable rightDrawable = null;
    // 控件是否有焦点
    private boolean hasFoucs;
    private IMyRightDrawableClick mightDrawableClick;

    public CustomEditText(Context context) {
        this(context, null);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        //这里构造方法也很重要，不加这个很多属性不能再XML里面定义
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //初始化基本图片
    private void init() {
        //获取RadioButton的图片集合
        Drawable[] drawables = getCompoundDrawables();
        leftDrawable = drawables[0];
        rightDrawable = drawables[2];
        setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, null, null);
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
    }

    //设置显示图片的大小
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        //这里只要改后面两个参数就好了，一个宽一个是高，如果想知道为什么可以查找源码
        if (left != null) {
            left.setBounds(0, 0, left.getIntrinsicWidth(), left.getIntrinsicHeight());
        }
        if (right != null) {
            right.setBounds(0, 0, right.getIntrinsicWidth(), right.getIntrinsicHeight());
        }
        if (top != null) {
            top.setBounds(0, 0, top.getIntrinsicWidth(), top.getIntrinsicHeight());
        }
        if (bottom != null) {
            bottom.setBounds(0, 0, bottom.getIntrinsicWidth(), bottom.getIntrinsicHeight());
        }
        setCompoundDrawables(left, top, right, bottom);
    }

    //光标选中时判断
    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        this.hasFoucs = focused;
        if (focused) {
            setImageVisible(getText().length() > 0);
        } else {
            setImageVisible(false);
        }
    }

    //设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
    protected void setImageVisible(boolean flag) {
        //如果当前右侧有图片则覆盖右侧的图片，如果没有还是显示原来的图片
        if (getCompoundDrawables()[2] != null) {
            rightDrawable = getCompoundDrawables()[2];
        }
        if (flag) {
            setCompoundDrawables(getCompoundDrawables()[0], null, rightDrawable, null);
        } else {
            setCompoundDrawables(getCompoundDrawables()[0], null, null, null);
        }
    }

    //文本框监听事件
    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if (hasFoucs) {
            if (text.length() > 0) {
                setImageVisible(true);
            } else {
                setImageVisible(false);
            }
        }
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void afterTextChanged(Editable s) {
    }

    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在 EditText的宽度 - 图标到控件右边的间距 - 图标的宽度 和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     * （参考 http://blog.csdn.net/xiaanming/article/details/11066685/）
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) {
                    //调用点击事件（外部实现）
                    mightDrawableClick.rightDrawableClick(this);
                }
            }
        }
        return super.onTouchEvent(event);
    }

    //设置右侧按钮的点击事件，外部调用的时候实现该方法
    public void setDrawableClick(IMyRightDrawableClick myMightDrawableClick) {
        this.mightDrawableClick = myMightDrawableClick;
    }


    //自定义接口（实现右边图片点击事件）
    public interface IMyRightDrawableClick {
        void rightDrawableClick(View view);
    }

    //允许外部修改右侧显示的图片
    public void setRightDrawable(Drawable drawable) {
        rightDrawable = drawable;
        setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, rightDrawable, null);
    }
}
