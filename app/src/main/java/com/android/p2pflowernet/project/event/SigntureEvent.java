package com.android.p2pflowernet.project.event;

/**
 * Created by caishen on 2017/11/20.
 * by--签名回调
 */

public class SigntureEvent {

    private final String path;

    public SigntureEvent(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
