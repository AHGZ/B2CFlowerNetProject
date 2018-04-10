package com.android.p2pflowernet.project.event;

/**
 * Created by caishen on 2017/12/9.
 * by--首页下部的选中
 */

public class MainEvent {
    private final int str;

    public MainEvent(int s) {
        this.str=s;
    }

    public int getStr() {
        return str;
    }
}
