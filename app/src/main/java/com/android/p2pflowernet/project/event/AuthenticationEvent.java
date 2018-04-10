package com.android.p2pflowernet.project.event;

/**
 * Created by zhangkun on 2018/3/27.
 */

public class AuthenticationEvent {
    private String msg;
    public AuthenticationEvent(String s) {
        this.msg = s;
    }

    public String getMsg() {
        return msg;
    }
}
