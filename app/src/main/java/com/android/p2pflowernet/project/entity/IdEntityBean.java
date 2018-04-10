package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * Created by caishen on 2017/11/21.
 * by--
 */

public class IdEntityBean implements Serializable{


    /**
     * user_id : 5
     * realname : 张坤
     * id_number : 513722198905070018
     * is_checked : 1
     * card_num : 6217730707949002
     * bank_name : 中信银行
     */

    private int user_id;
    private String realname;
    private String id_number;
    private int is_checked;
    private String card_num;
    private String bank_name;

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

    public String getCard_num() {
        return card_num;
    }

    public void setCard_num(String card_num) {
        this.card_num = card_num;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }
}
