package com.android.p2pflowernet.project.event;

/**
 * author: zhangpeisen
 * created on: 2017/12/27 下午2:15
 * description:
 */
public class PushMsgEvent {
    String title;
    String content;

    public PushMsgEvent(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
