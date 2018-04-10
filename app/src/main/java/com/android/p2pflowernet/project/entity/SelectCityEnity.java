package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author: zhangpeisen
 * created on: 2017/6/25 下午3:42
 * description:
 */
public class SelectCityEnity implements Serializable {
    private int id;
    private String name;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    private int num;
    private String showName;
    private List<SelectCityEnity> children;

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

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public List<SelectCityEnity> getChildren() {
        return children;
    }

    public void setChildren(List<SelectCityEnity> children) {
        this.children = children;
    }

}
