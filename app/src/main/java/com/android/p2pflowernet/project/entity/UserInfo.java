package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * author: zhangpeisen
 * created on: 2017/10/9 下午3:57
 * description:
 */
public class UserInfo implements Serializable {
    /**
     * phone : 18210672821
     * username : hf15106507145448
     * nickname :
     * sex : 0
     * birthday : null
     * region : 110101
     * push_userid : 21
     * push_channelid :
     * push_flag : 1
     * is_partner : 0
     * is_agent : 0
     * is_merchant : 0
     * is_staff : 0
     * invite_code : vvvvvv
     * small_head_id : 0
     * head_path : null
     * user_id : 21
     * token : ffb898681f5738011d6ec87452b16bd3ec542ee0
     * token_expiry : 1513242714
     */
    private String phone;
    private String username;
    private String nickname;
    private int sex;
    private Object birthday;
    private String region;
    private String push_userid;
    private String push_channelid;
    private int push_flag;
    private int is_partner;
    private int is_agent;
    private int is_merchant;
    private int is_staff;
    private String invite_code;
    private int small_head_id;
    private Object head_path;
    private String user_id;
    private String token;
    private int token_expiry;

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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
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

    public String getPush_userid() {
        return push_userid;
    }

    public void setPush_userid(String push_userid) {
        this.push_userid = push_userid;
    }

    public String getPush_channelid() {
        return push_channelid;
    }

    public void setPush_channelid(String push_channelid) {
        this.push_channelid = push_channelid;
    }

    public int getPush_flag() {
        return push_flag;
    }

    public void setPush_flag(int push_flag) {
        this.push_flag = push_flag;
    }

    public int getIs_partner() {
        return is_partner;
    }

    public void setIs_partner(int is_partner) {
        this.is_partner = is_partner;
    }

    public int getIs_agent() {
        return is_agent;
    }

    public void setIs_agent(int is_agent) {
        this.is_agent = is_agent;
    }

    public int getIs_merchant() {
        return is_merchant;
    }

    public void setIs_merchant(int is_merchant) {
        this.is_merchant = is_merchant;
    }

    public int getIs_staff() {
        return is_staff;
    }

    public void setIs_staff(int is_staff) {
        this.is_staff = is_staff;
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public int getSmall_head_id() {
        return small_head_id;
    }

    public void setSmall_head_id(int small_head_id) {
        this.small_head_id = small_head_id;
    }

    public Object getHead_path() {
        return head_path;
    }

    public void setHead_path(Object head_path) {
        this.head_path = head_path;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    @Override
    public String toString() {
        return "UserInfo{" +
                "phone='" + phone + '\'' +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex=" + sex +
                ", birthday=" + birthday +
                ", region=" + region +
                ", push_userid='" + push_userid + '\'' +
                ", push_channelid='" + push_channelid + '\'' +
                ", push_flag=" + push_flag +
                ", is_partner=" + is_partner +
                ", is_agent=" + is_agent +
                ", is_merchant=" + is_merchant +
                ", is_staff=" + is_staff +
                ", invite_code='" + invite_code + '\'' +
                ", small_head_id=" + small_head_id +
                ", head_path=" + head_path +
                ", user_id='" + user_id + '\'' +
                ", token='" + token + '\'' +
                ", token_expiry=" + token_expiry +
                '}';
    }
}
