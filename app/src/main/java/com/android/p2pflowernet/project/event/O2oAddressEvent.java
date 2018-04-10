package com.android.p2pflowernet.project.event;

/**
 * Created by Administrator on 2018/1/17.
 */

public class O2oAddressEvent {
    private final String o2oAddress;

    public O2oAddressEvent(String o2oAddress) {
        this.o2oAddress = o2oAddress;
    }

    public String getO2oAddress(){
        return o2oAddress;
    }
}
