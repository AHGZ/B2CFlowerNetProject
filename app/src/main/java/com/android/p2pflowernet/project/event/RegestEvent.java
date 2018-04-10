package com.android.p2pflowernet.project.event;

/**
 * Created by caishen on 2018/2/1.
 * by--
 */

public class RegestEvent {

    private final String result;

    public RegestEvent(String resultString) {
        this.result = resultString;
    }

    public String getResult() {
        return result;
    }
}
