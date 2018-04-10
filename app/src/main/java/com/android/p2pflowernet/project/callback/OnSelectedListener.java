package com.android.p2pflowernet.project.callback;

import com.android.p2pflowernet.project.entity.GoodsAttrBean;

/**
 * 选择成功回调
 */
public interface OnSelectedListener {
//    void onConfirm(String title, String smallValue, String id);

    void onConfirm(String title, GoodsAttrBean.ListsBean.AttrValueBean attrValueBean);
}
