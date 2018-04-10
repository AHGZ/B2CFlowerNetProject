package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * Created by caishen on 2017/11/21.
 * by--完善个人信息
 */

public class AddAuthInfoBean implements Serializable{


    /**
     * user_id : 1
     * realname : 张怡
     * sex : 1
     * birthday : 1988-05-16
     * id_number : 130503198805160071
     * is_checked : 1
     */

    private int user_id;
    private String realname;
    private int sex;
    private String birthday;
    private String id_number;
    private int is_checked;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public int getIs_checked() {
        return is_checked;
    }

    public void setIs_checked(int is_checked) {
        this.is_checked = is_checked;
    }
}
