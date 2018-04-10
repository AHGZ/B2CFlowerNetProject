package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * author: zhangpeisen
 * created on: 2017/11/24 上午11:24
 * description: 代理人排队实例
 */
public class ApplyQueueBean implements Serializable {

    private String nickname;
    private String mobile;
    private String amount;
    private String applydate;


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getApplydate() {
        return applydate;
    }

    public void setApplydate(String applydate) {
        this.applydate = applydate;
    }

    @Override
    public String toString() {
        return "ApplyQueueBean{" +
                "nickname='" + nickname + '\'' +
                ", mobile='" + mobile + '\'' +
                ", amount='" + amount + '\'' +
                ", applydate='" + applydate + '\'' +
                '}';
    }
}
