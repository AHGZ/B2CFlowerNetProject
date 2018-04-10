package com.android.p2pflowernet.project.event;

/**
 * Created by zhangkun on 2018/2/27.
 */

public class RevisePasswordEvent {
    private String s;

    public RevisePasswordEvent(String s) {
        this.s = s;
    }

    public String getS() {
        return s;
    }
}
