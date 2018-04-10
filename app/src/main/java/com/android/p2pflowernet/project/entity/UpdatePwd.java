package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * Created by caishen on 2017/11/16.
 * by--修改密码bean
 */

public class UpdatePwd implements Serializable {
    String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UpdatePwd{" +
                "data='" + data + '\'' +
                '}';
    }
}
