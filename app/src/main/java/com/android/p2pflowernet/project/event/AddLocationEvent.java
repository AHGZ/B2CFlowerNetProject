package com.android.p2pflowernet.project.event;

import com.baidu.mapapi.search.core.PoiInfo;

/**
 * Created by zhangkun on 2018/2/3.
 */

public class AddLocationEvent {
    private PoiInfo poiInfo;

    public AddLocationEvent(PoiInfo poiInfo) {
        this.poiInfo = poiInfo;
    }

    public PoiInfo getMesage() {
        return poiInfo;
    }
}
