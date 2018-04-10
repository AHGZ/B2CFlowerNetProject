package com.android.p2pflowernet.project.event;

/**
 * Created by caishen on 2017/11/27.
 * by--发送刷新页面的消息
 */

public class RefreshEvent {

    private final String refresh;

    public RefreshEvent(String refresh) {
        this.refresh = refresh;
    }

    public String getRefresh() {
        return refresh;
    }


}
