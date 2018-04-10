package com.android.p2pflowernet.project.callback;

/**
 * author: zhangpeisen
 * created on: 2017/12/6 下午4:35
 * description: 选择后回调
 */
public interface SelectGoodsAttr {
    /**
     * 选中属性
     */
    void selectedAttribute(String type, String id, String[] attrname);
}
