package com.android.p2pflowernet.project.callback;

/**
 * author: zhangpeisen
 * created on: 2017/10/26 下午5:28
 * description: 实现Recycleview多选接口类
 */
public interface ISelect {
    int SINGLE_MODE = 1;
    int MULTIPLE_MODE = 2;

    boolean isSelected();

    void setSelected(boolean selected);

}