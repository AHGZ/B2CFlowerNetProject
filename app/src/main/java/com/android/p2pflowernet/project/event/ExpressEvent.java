package com.android.p2pflowernet.project.event;

import com.android.p2pflowernet.project.entity.ExpresListBean;

/**
 * Created by caishen on 2017/11/18.
 * by--快递公司
 */

public class ExpressEvent {

    ExpresListBean.ListsBean express;

    public ExpressEvent(ExpresListBean.ListsBean express) {

        this.express = express;
    }

    public ExpresListBean.ListsBean getExpress() {
        return express;
    }

    public void setExpress(ExpresListBean.ListsBean express) {

        this.express = express;
    }
}
