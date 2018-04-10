package com.android.p2pflowernet.project.event;

import com.android.p2pflowernet.project.entity.AdressMangerBean;

/**
 * Created by caishen on 2017/12/11.
 * by--地址管理操作
 */

public class AdressMangerEvent {

    private final AdressMangerBean data;
    private final int position;

    public AdressMangerEvent(AdressMangerBean data, int position) {
        this.data = data;
        this.position = position;
    }

    public AdressMangerBean getData() {
        return data;
    }

    public int getPosition() {
        return position;
    }
}
