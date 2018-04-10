package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * Created by caishen on 2017/12/4.
 * by--检测用户是否设置支付密码
 */

public class CheckPwdBean implements Serializable{


    /**
     * is_set : 1
     */

    private int is_set;

    public int getIs_set() {
        return is_set;
    }

    public void setIs_set(int is_set) {
        this.is_set = is_set;
    }
}
