package com.android.p2pflowernet.project.entity;

/**
 * Created by caishen on 2017/11/20.
 * by--追踪物流信息
 */

public class TraceBean {


    private boolean isHead;
    private String info;
    private String phone;
    private String time;

    public TraceBean(boolean isHead, String info, String phone, String time) {
        super();
        this.isHead = isHead;
        this.info = info;
        this.phone = phone;
        this.time = time;
    }

    public boolean isHead() {
        return isHead;
    }

    public void setHead(boolean isHead) {
        this.isHead = isHead;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
