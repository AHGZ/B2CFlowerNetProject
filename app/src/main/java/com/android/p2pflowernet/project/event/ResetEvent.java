package com.android.p2pflowernet.project.event;

/**
 * Created by zhangkun on 2018/2/27.
 */

public class ResetEvent {
    private String s;
    public ResetEvent(String s) {
        this.s = s;
    }

    public String getString() {
        return s;
    }
}
