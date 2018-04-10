package com.android.p2pflowernet.project.event;

/**
 * Created by caishen on 2017/12/4.
 * by--
 */

public class CheckPwdEvent {
    private final String pwd;

    public CheckPwdEvent(String str) {
        this.pwd = str;
    }

    public String getPwd() {
        return pwd;
    }
}
