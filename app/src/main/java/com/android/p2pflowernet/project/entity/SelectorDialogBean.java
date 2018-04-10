package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * Created by caishen on 2018/1/30.
 * by--单项选择弹出框
 */

public class SelectorDialogBean implements Serializable{

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
