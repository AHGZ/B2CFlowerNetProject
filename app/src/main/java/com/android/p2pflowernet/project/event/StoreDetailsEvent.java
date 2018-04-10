package com.android.p2pflowernet.project.event;

import com.android.p2pflowernet.project.entity.O2oIndexBean;

/**
 * Created by caishen on 2018/1/25.
 * by--
 */

public class StoreDetailsEvent {

    private final O2oIndexBean StoreDetail;

    public StoreDetailsEvent(O2oIndexBean o2oIndexBean1) {
        this.StoreDetail=o2oIndexBean1;
    }

    public O2oIndexBean getStoreDetail() {
        return StoreDetail;
    }
}
