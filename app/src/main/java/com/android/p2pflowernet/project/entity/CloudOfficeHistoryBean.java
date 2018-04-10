package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * author: zhangpeisen
 * created on: 2017/12/25 上午10:15
 * description: 云工任务历史
 */
public class CloudOfficeHistoryBean implements Serializable {

    /**
     * usercount : 100
     * partnercount : 20
     * staff_reward : 1500.00
     * staff_salary : 3500.00
     */

    private String usercount;
    private String partnercount;
    private String staff_reward;
    private String staff_salary;

    public String getUsercount() {
        return usercount;
    }

    public void setUsercount(String usercount) {
        this.usercount = usercount;
    }

    public String getPartnercount() {
        return partnercount;
    }

    public void setPartnercount(String partnercount) {
        this.partnercount = partnercount;
    }

    public String getStaff_reward() {
        return staff_reward;
    }

    public void setStaff_reward(String staff_reward) {
        this.staff_reward = staff_reward;
    }

    public String getStaff_salary() {
        return staff_salary;
    }

    public void setStaff_salary(String staff_salary) {
        this.staff_salary = staff_salary;
    }

    @Override
    public String toString() {
        return "CloudOfficeHistoryBean{" +
                "usercount='" + usercount + '\'' +
                ", partnercount='" + partnercount + '\'' +
                ", staff_reward='" + staff_reward + '\'' +
                ", staff_salary='" + staff_salary + '\'' +
                '}';
    }
}
