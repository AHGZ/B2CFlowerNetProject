package com.android.p2pflowernet.project.event;

/**
 * Created by Administrator on 2018/1/25.
 */

public class TakeOutOrderEvent {
    private final String s;

    public TakeOutOrderEvent(String s) {
        this.s=s;
    }

    public String getS() {
        return s;
    }
}
