package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caishen on 2017/12/23.
 * by--修改审批状态
 */

public class AutoWorkBean implements Serializable{


    /**
     * user : 7
     * Partner : 1
     * qual_fund_amount : 100000.00
     * appro_mode : 2
     * Illegalnum : 0
     * notaudited : []
     */

    private int user;
    private int Partner;
    private String qual_fund_amount;
    private String appro_mode;
    private int Illegalnum;
    private List<AgentOfficeBean.NotauditedBean> notaudited;

    public List<AgentOfficeBean.NotauditedBean> getNotaudited() {
        return notaudited;
    }

    public void setNotaudited(List<AgentOfficeBean.NotauditedBean> notaudited) {
        this.notaudited = notaudited;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getPartner() {
        return Partner;
    }

    public void setPartner(int Partner) {
        this.Partner = Partner;
    }

    public String getQual_fund_amount() {
        return qual_fund_amount;
    }

    public void setQual_fund_amount(String qual_fund_amount) {
        this.qual_fund_amount = qual_fund_amount;
    }

    public String getAppro_mode() {
        return appro_mode;
    }

    public void setAppro_mode(String appro_mode) {
        this.appro_mode = appro_mode;
    }

    public int getIllegalnum() {
        return Illegalnum;
    }

    public void setIllegalnum(int Illegalnum) {
        this.Illegalnum = Illegalnum;
    }

}
