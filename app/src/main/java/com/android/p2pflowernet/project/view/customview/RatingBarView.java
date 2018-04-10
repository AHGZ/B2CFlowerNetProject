package com.android.p2pflowernet.project.view.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.p2pflowernet.project.R;

/**
 * Created by caishen on 2017/12/5.
 * by--自定义星星
 */

public class RatingBarView extends LinearLayout {




    public interface OnRatingListener {
        void onRating(Object bindObject, int RatingScore);
    }

    private boolean mClickable = true;
    private OnRatingListener onRatingListener;
    private Object bindObject;
    private float starImageSize;
    private int starCount;
    private Drawable starEmptyDrawable;
    private Drawable starFillDrawable;
    private int mStarCount;
    private int starPaddingLeft;
    private int starPaddingTop;
    private int starPaddingRight;
    private int starPaddingBottom;

    public void setClickable(boolean clickable) {
        this.mClickable = clickable;
    }

    public RatingBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.HORIZONTAL);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RatingBarView);
        starImageSize = ta.getDimension(R.styleable.RatingBarView_starImageSize, 20);
        starCount = ta.getInteger(R.styleable.RatingBarView_starCount, 5);
        starEmptyDrawable = ta.getDrawable(R.styleable.RatingBarView_starEmpty);
        starFillDrawable = ta.getDrawable(R.styleable.RatingBarView_starFill);
        starPaddingLeft = ta.getInteger(R.styleable.RatingBarView_paddingleft, 5);
        starPaddingTop = ta.getInteger(R.styleable.RatingBarView_paddingtop, 0);
        starPaddingRight = ta.getInteger(R.styleable.RatingBarView_paddingright, 5);
        starPaddingBottom = ta.getInteger(R.styleable.RatingBarView_paddingbottom, 0);
        ta.recycle();

        for (int i = 0; i < starCount; ++i) {
            ImageView imageView = getStarImageView(context, attrs);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickable) {
                        mStarCount = indexOfChild(v) + 1;
                        setStar(mStarCount, true);
                        if (onRatingListener != null) {
                            onRatingListener.onRating(bindObject, mStarCount);
                        }
                    }
                }
            });
            addView(imageView);
        }
    }

    private ImageView getStarImageView(Context context, AttributeSet attrs) {
        ImageView imageView = new ImageView(context);
        ViewGroup.LayoutParams para = new ViewGroup.LayoutParams(Math.round(starImageSize), Math.round(starImageSize));
        imageView.setLayoutParams(para);
        // TODO:you can change gap between two stars use the padding
        imageView.setPadding(starPaddingLeft, starPaddingTop, starPaddingRight, starPaddingBottom);
//        imageView.setPadding(0, 0, 0, 0);
        imageView.setImageDrawable(starEmptyDrawable);
        imageView.setMaxWidth(10);
        imageView.setMaxHeight(10);
        return imageView;
    }

    public void setStar(int starCount, boolean animation) {
        starCount = starCount > this.starCount ? this.starCount : starCount;
        starCount = starCount < 0 ? 0 : starCount;
        for (int i = 0; i < starCount; ++i) {
            ((ImageView) getChildAt(i)).setImageDrawable(starFillDrawable);
            if (animation) {
                ScaleAnimation sa = new ScaleAnimation(0, 0, 1, 1);
                getChildAt(i).startAnimation(sa);
            }
        }
        for (int i = this.starCount - 1; i >= starCount; --i) {
            ((ImageView) getChildAt(i)).setImageDrawable(starEmptyDrawable);
        }
    }


    public int getStarPaddingLeft() {
        return starPaddingLeft;
    }

    public void setStarPaddingLeft(int starPaddingLeft) {
        this.starPaddingLeft = starPaddingLeft;
    }

    public int getStarPaddingTop() {
        return starPaddingTop;
    }

    public void setStarPaddingTop(int starPaddingTop) {
        this.starPaddingTop = starPaddingTop;
    }

    public int getStarPaddingRight() {
        return starPaddingRight;
    }

    public void setStarPaddingRight(int starPaddingRight) {
        this.starPaddingRight = starPaddingRight;
    }

    public int getStarPaddingBottom() {
        return starPaddingBottom;
    }

    public void setStarPaddingBottom(int starPaddingBottom) {
        this.starPaddingBottom = starPaddingBottom;
    }

    public int getStarCount() {
        return mStarCount;
    }

    public void setStarFillDrawable(Drawable starFillDrawable) {
        this.starFillDrawable = starFillDrawable;
    }

    public void setStarEmptyDrawable(Drawable starEmptyDrawable) {
        this.starEmptyDrawable = starEmptyDrawable;
    }

    public void setStarCount(int startCount) {
        this.starCount = startCount;
    }

    public void setStarImageSize(float starImageSize) {
        this.starImageSize = starImageSize;
    }

    public void setBindObject(Object bindObject) {
        this.bindObject = bindObject;
    }

    /**
     * 这个回调，可以获取到用户评价给出的星星等级
     */
    public void setOnRatingListener(OnRatingListener onRatingListener) {
        this.onRatingListener = onRatingListener;
    }
}
