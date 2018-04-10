package com.android.p2pflowernet.project.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caishen on 2018/2/2.
 * by--根据规格改变比价列表数据
 */

public class SpecCompareBean implements Serializable {


    private List<GoodsInfoBean.CompareBean> compare;

    public List<GoodsInfoBean.CompareBean> getCompare() {
        return compare;
    }

    public void setCompare(List<GoodsInfoBean.CompareBean> compare) {
        this.compare = compare;
    }
}
