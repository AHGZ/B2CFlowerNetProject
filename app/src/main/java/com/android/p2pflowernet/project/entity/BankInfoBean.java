package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caishen on 2017/11/30.
 * by--
 */

public class BankInfoBean implements Serializable{


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 4
         * card_num : 6225760012380262
         * bank_name : 工商银行
         * bankimg : https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=false&word=%E7%BE%8E%E5%A5%B3&step_word=&hs=0&pn=9&spn=0&di=111697470940&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=2019270811%2C1269730008&os=596508702%2C2667300130&simid=4137292997%2C664325661&adpicid=0&lpn=0&ln=3906&fr=&fmq=1512033992426_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=girl&oriquery=&objurl=http%3A%2F%2Fimage.tianjimedia.com%2FuploadImages%2F2015%2F215%2F45%2F04L5VRR21C5W.jpg&gsm=3c&rpstart=0&rpnum=0
         */

        private String id;
        private String card_num;
        private String bank_name;
        private String bankimg;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getBankimg() {
            return bankimg;
        }

        public void setBankimg(String bankimg) {
            this.bankimg = bankimg;
        }
    }
}
