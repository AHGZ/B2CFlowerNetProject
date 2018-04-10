package com.android.p2pflowernet.project.event;

/**
 * Created by caishen on 2017/11/7.
 * by--发送发票的抬头
 */

public class InvoiceEvent {

    private String tax_num;
    private String title;
    private String userType;
    private String invoice;

    public InvoiceEvent(String user_type, String invoice, String number, String trim) {
        this.invoice = invoice;//类型
        this.tax_num = number;//税号
        this.title = trim;//抬头
        this.userType = user_type;//用户类别
    }

    public String getTax_num() {
        return tax_num;
    }

    public void setTax_num(String tax_num) {
        this.tax_num = tax_num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }
}
