package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * author: zhangpeisen
 * created on: 2017/11/14 下午3:36
 * description:
 */
public class CheckMobileBean implements Serializable{

    /**
     * is_used : 0
     */
    private int is_used;

    public int getIs_used() {
        return is_used;
    }

    public void setIs_used(int is_used) {
        this.is_used = is_used;
    }

    @Override
    public String toString() {
        return "CheckMobileBean{" +
                "is_used=" + is_used +
                '}';
    }
}
