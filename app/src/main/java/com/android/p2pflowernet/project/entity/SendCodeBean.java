package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * author: zhangpeisen
 * created on: 2017/11/14 下午3:55
 * description:
 */
public class SendCodeBean implements Serializable {

    /**
     * code : 865930
     */
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "SendCodeBean{" +
                "code=" + code +
                '}';
    }
}
