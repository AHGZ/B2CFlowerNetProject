package com.android.p2pflowernet.project.event;

/**
 * Created by caishen on 2017/12/27.
 * by--
 */

public class SortLeftEvent {
    private final int sort;

    public SortLeftEvent(int i) {
        this.sort=i;
    }

    public int getSort() {
        return sort;
    }
}
