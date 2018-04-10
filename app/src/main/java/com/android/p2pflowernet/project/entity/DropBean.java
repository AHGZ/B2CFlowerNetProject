package com.android.p2pflowernet.project.entity;

/**
 * Created by cui on 2016/12/13.
 */

public class DropBean {
    private String name;
    private boolean choiced = false;
    public DropBean(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean isChoiced() {
        return choiced;
    }
    public void setChoiced(boolean choiced) {
        this.choiced = choiced;
    }
}
