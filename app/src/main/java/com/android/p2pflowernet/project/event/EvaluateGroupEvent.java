package com.android.p2pflowernet.project.event;

/**
 * Created by Administrator on 2018/1/27.
 */

public class EvaluateGroupEvent {
    private String message;

    public EvaluateGroupEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
