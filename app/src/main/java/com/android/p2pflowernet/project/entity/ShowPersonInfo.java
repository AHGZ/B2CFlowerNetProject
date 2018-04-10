package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * Created by caishen on 2017/11/15.
 * by--获取到的个人信息
 */

public class ShowPersonInfo implements Serializable {


    /**
     * user_id : 9
     * city : 北京
     * region : 昌平区
     * invite_code : jjjjjj
     * file_path : null
     * nickname :
     * sex : 0
     * birthday : 2017-11-28
     * phone : 15801249045
     */

    private String user_id;
    private String city;
    private String region;
    private String invite_code;
    private String file_path;
    private String nickname;
    private int sex;
    private String birthday;
    private String phone;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
