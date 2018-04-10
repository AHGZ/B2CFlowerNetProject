package com.android.p2pflowernet.project.callback;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * author: zhangpeisen
 * created on: 2017/10/26 下午5:43
 * description: 组件基类
 */
public class BaseHolder<T> extends RecyclerView.ViewHolder {
    public boolean enableCLick = true;

    public BaseHolder(View itemView) {
        super(itemView);
    }

    public BaseHolder(View itemView, boolean enableCLick) {
        super(itemView);
        this.enableCLick = enableCLick;
    }

    public void onBind(T t) {
    }

    public Context getContext() {
        return itemView.getContext();
    }

}