package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caishen on 2018/1/9.
 * by--特用于修改数据返回空集合的bean类
 */

public class ReplyBean implements Serializable{


    private List<?> list;

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }
}
