package com.android.p2pflowernet.project.event;

import com.android.p2pflowernet.project.entity.O2oAddressBean;

/**
 * author: zhangpeisen
 * created on: 2018/1/17 下午6:06
 * description:
 */
public class UpdateAddressEvent {
    public O2oAddressBean.ListsBean listsBean;

    public UpdateAddressEvent(O2oAddressBean.ListsBean listsBean) {
        this.listsBean = listsBean;
    }

}
