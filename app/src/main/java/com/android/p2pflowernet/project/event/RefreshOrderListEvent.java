package com.android.p2pflowernet.project.event;

/**
 * Created by Administrator on 2018/1/25.
 */

public class RefreshOrderListEvent {
    private String messgae;

    public RefreshOrderListEvent(String messgae) {
        this.messgae = messgae;
    }

    public String getMessgae() {
        return messgae;
    }
}
