package com.android.p2pflowernet.project.event;

/**
 * Created by caishen on 2017/12/27.
 * by--分类界面的消息
 */

public class BrandEvent {
    private final String id;

    public BrandEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
