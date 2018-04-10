package com.android.p2pflowernet.project.event;

import com.android.p2pflowernet.project.entity.O2oIndexBean;

import java.util.List;

/**
 * author: zhangpeisen
 * created on: 2018/1/10 下午1:33
 * description: 店铺详情数据
 */
public class StoreDetailEvent {
    public List<O2oIndexBean.ListsBean> o2oIndexBeanLists;

    public StoreDetailEvent(List<O2oIndexBean.ListsBean> o2oIndexBeanLists) {
        this.o2oIndexBeanLists = o2oIndexBeanLists;
    }
}
