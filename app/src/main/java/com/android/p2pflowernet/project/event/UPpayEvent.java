package com.android.p2pflowernet.project.event;

import android.content.Intent;

/**
 * Created by caishen on 2017/12/18.
 * by--银联支付回调
 */

public class UPpayEvent {
    private int requestCode;
    private int resultCode;
    private Intent data;

    public UPpayEvent(int requestCode, int resultCode, Intent data) {

        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public int getResultCode() {
        return resultCode;
    }

    public Intent getData() {
        return data;
    }
}
