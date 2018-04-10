package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * Created by heguozhong on 2018/1/22/022.
 * 团购剩余数量
 */

public class GroupResidueNumBean implements Serializable{

    /**
     * r : 0
     * msg : 查询成功
     * data : 0
     */

    private int r;
    private String msg;
    private int data;

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
