package com.android.p2pflowernet.project.entity;

/**
 * author: zhangpeisen
 * created on: 2018/1/17 上午11:11
 * description:
 */
public class StoreInfoEvent {
    public MerchantBean merchantBean;

    public StoreInfoEvent(MerchantBean merchantBean) {
        this.merchantBean = merchantBean;
    }

    public MerchantBean getMerchantBean() {
        return merchantBean;
    }

    public void setMerchantBean(MerchantBean merchantBean) {
        this.merchantBean = merchantBean;
    }
}
