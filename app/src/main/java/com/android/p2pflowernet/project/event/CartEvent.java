package com.android.p2pflowernet.project.event;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * author: zhangpeisen
 * created on: 2017/11/2 下午1:48
 * description: 购物车
 */
public class CartEvent {

    ImageView mShoppingCartImageView;
    LinearLayout parentLayout;
    TextView tvCount;
    private int tag;

    public int getTag() {
        return tag;
    }

    public TextView getTvCount() {
        return tvCount;
    }

    public void setTvCount(TextView tvCount) {
        this.tvCount = tvCount;
    }

    public LinearLayout getParentLayout() {
        return parentLayout;
    }

    public void setParentLayout(LinearLayout parentLayout) {
        this.parentLayout = parentLayout;
    }

    public ImageView getmShoppingCartImageView() {
        return mShoppingCartImageView;
    }

    public void setmShoppingCartImageView(ImageView mShoppingCartImageView) {
        this.mShoppingCartImageView = mShoppingCartImageView;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
