package com.android.p2pflowernet.project.entity;

import java.io.Serializable;

/**
 * Created by caishen on 2017/12/11.
 * by--支付宝成功回调
 */

public class PayAlResultBean implements Serializable{


    /**
     * alipay_trade_app_pay_response : {"code":"10000","msg":"Success","app_id":"2017111509941465","auth_app_id":"2017111509941465","charset":"utf-8","timestamp":"2017-12-11 17:04:14","total_amount":"0.01","trade_no":"2017121121001004430566177811","seller_id":"2088821662372481","out_trade_no":"121117041115849"}
     * sign : fp1ohEtWWTFiWaU429F9p8SwLynbMUYhniF7H+YSHDkPZC9rKVLRYEXVVrppSu+9G9MsyoElrpWsG8EhjT0YifJn+2dOeWLgM5pTVu1ATuj4L19yWfMYMgCC6Ixo3YMgcgDw9phAke1KnvEt0J8IhNQJvOk+6DW977F/s1oOzewWJU/qtMUkbX12V7sT/c9xGQnscFCBQc/ehXylLv4mNcZRO43qCUlynZvx0UOzREurL7yJCk75vy2Yn4vxmifD7usgLoov72jqqG2gUbM9p+5lUq468aAk8eTd88Ck/+6k6DfiMKXogh33RlXK3kera4Volr5zXZmQySdDa4AUfg==
     * sign_type : RSA2
     */

    private AlipayTradeAppPayResponseBean alipay_trade_app_pay_response;
    private String sign;
    private String sign_type;

    public AlipayTradeAppPayResponseBean getAlipay_trade_app_pay_response() {
        return alipay_trade_app_pay_response;
    }

    public void setAlipay_trade_app_pay_response(AlipayTradeAppPayResponseBean alipay_trade_app_pay_response) {
        this.alipay_trade_app_pay_response = alipay_trade_app_pay_response;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public static class AlipayTradeAppPayResponseBean {
        /**
         * code : 10000
         * msg : Success
         * app_id : 2017111509941465
         * auth_app_id : 2017111509941465
         * charset : utf-8
         * timestamp : 2017-12-11 17:04:14
         * total_amount : 0.01
         * trade_no : 2017121121001004430566177811
         * seller_id : 2088821662372481
         * out_trade_no : 121117041115849
         */

        private String code;
        private String msg;
        private String app_id;
        private String auth_app_id;
        private String charset;
        private String timestamp;
        private String total_amount;
        private String trade_no;
        private String seller_id;
        private String out_trade_no;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getApp_id() {
            return app_id;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public String getAuth_app_id() {
            return auth_app_id;
        }

        public void setAuth_app_id(String auth_app_id) {
            this.auth_app_id = auth_app_id;
        }

        public String getCharset() {
            return charset;
        }

        public void setCharset(String charset) {
            this.charset = charset;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public String getTrade_no() {
            return trade_no;
        }

        public void setTrade_no(String trade_no) {
            this.trade_no = trade_no;
        }

        public String getSeller_id() {
            return seller_id;
        }

        public void setSeller_id(String seller_id) {
            this.seller_id = seller_id;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }
    }
}
