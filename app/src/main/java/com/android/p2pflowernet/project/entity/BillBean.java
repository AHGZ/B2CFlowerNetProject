package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * author: zhangpeisen
 * created on: 2017/11/20 下午4:47
 * description: 账单封装类
 */
public class BillBean implements Serializable {
    private String billtype;
    private String billdate;
    private String billamount;

    public String getBilldate() {
        return billdate;
    }

    public void setBilldate(String billdate) {
        this.billdate = billdate;
    }

    public String getBillamount() {
        return billamount;
    }

    public void setBillamount(String billamount) {
        this.billamount = billamount;
    }

    public String getBilltype() {
        return billtype;
    }

    public void setBilltype(String billtype) {
        this.billtype = billtype;
    }

    @Override
    public String toString() {
        return "BillBean{" +
                "billtype='" + billtype + '\'' +
                ", billdate='" + billdate + '\'' +
                ", billamount='" + billamount + '\'' +
                '}';
    }
}
