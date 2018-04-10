package com.android.p2pflowernet.project.event;

import com.android.p2pflowernet.project.entity.O2oAddressBean;

/**
 * Created by zhangkun on 2018/2/3.
 */

public class O2oHomeAddressEvent {

    private O2oAddressBean.ListsBean listsBean;

    public O2oHomeAddressEvent(O2oAddressBean.ListsBean listsBean) {
        this.listsBean = listsBean;
    }

    public O2oAddressBean.ListsBean getListsBean() {
        return listsBean;
    }
}
