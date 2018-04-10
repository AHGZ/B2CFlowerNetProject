package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * Created by caishen on 2017/11/23.
 * by--设置
 */

public class SettingBean implements Serializable{


    /**
     * user_id : 18
     * phone : 18513081386
     * username : hf15106403448807
     * nickname :
     * sex : 0
     * birthday : null
     * region : 110000
     * is_partner : 0
     * is_agent : 0
     * is_merchant : 0
     * is_staff : 0
     * invite_code : ssssss
     * small_head_id : 0
     * head_path : null
     * token : 83ff6cbba21f3c2f447551edbbce51e9c1773f21
     * token_expiry : 1514013595
     */

    private String user_id;
    private String phone;
    private String username;
    private String nickname;
    private String sex;
    private Object birthday;
    private String region;
    private String is_partner;
    private String is_agent;
    private String is_merchant;
    private String is_staff;
    private String invite_code;
    private String small_head_id;
    private Object head_path;
    private String token;
    private int token_expiry;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Object getBirthday() {
        return birthday;
    }

    public void setBirthday(Object birthday) {
        this.birthday = birthday;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getIs_partner() {
        return is_partner;
    }

    public void setIs_partner(String is_partner) {
        this.is_partner = is_partner;
    }

    public String getIs_agent() {
        return is_agent;
    }

    public void setIs_agent(String is_agent) {
        this.is_agent = is_agent;
    }

    public String getIs_merchant() {
        return is_merchant;
    }

    public void setIs_merchant(String is_merchant) {
        this.is_merchant = is_merchant;
    }

    public String getIs_staff() {
        return is_staff;
    }

    public void setIs_staff(String is_staff) {
        this.is_staff = is_staff;
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public String getSmall_head_id() {
        return small_head_id;
    }

    public void setSmall_head_id(String small_head_id) {
        this.small_head_id = small_head_id;
    }

    public Object getHead_path() {
        return head_path;
    }

    public void setHead_path(Object head_path) {
        this.head_path = head_path;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getToken_expiry() {
        return token_expiry;
    }

    public void setToken_expiry(int token_expiry) {
        this.token_expiry = token_expiry;
    }
}
