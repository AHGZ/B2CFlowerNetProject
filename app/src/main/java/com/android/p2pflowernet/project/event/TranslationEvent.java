package com.android.p2pflowernet.project.event;

/**
 * Created by caishen on 2017/12/5.
 * by--平移
 */

public class TranslationEvent {
    private final int str;

    public TranslationEvent(int s) {
        this.str = s;
    }

    public int getStr() {
        return str;
    }
}
