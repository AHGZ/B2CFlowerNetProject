package com.android.p2pflowernet.project.event;

/**
 * Created by caishen on 2018/3/28.
 * by--
 */

public class AffirmEvent {

    private boolean closeAct;

    public AffirmEvent(Boolean closeAct) {
        this.closeAct = closeAct;
    }

    private boolean getCloseAct() {
        return closeAct;
    }
}
