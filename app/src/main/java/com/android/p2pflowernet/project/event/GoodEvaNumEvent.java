package com.android.p2pflowernet.project.event;

import com.android.p2pflowernet.project.entity.EveluateBean;

/**
 * Created by caishen on 2018/3/12.
 * by--
 */

public class GoodEvaNumEvent {
    private final EveluateBean data;

    public GoodEvaNumEvent(EveluateBean data) {
        this.data = data;
    }

    public EveluateBean getData() {
        return data;
    }
}
