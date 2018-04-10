package com.android.p2pflowernet.project.callback;

import com.android.p2pflowernet.project.entity.GoodsAttrBean;

/**
 * @描述:sku回调
 * @创建人：zhangpeisen
 * @创建时间：2017/12/28 下午2:08
 * @修改人：zhangpeisen
 * @修改时间：2017/12/28 下午2:08
 * @修改备注：
 * @throws
 */
public interface OnSkuListener {
    /**
     * 属性取消选中
     *
     * @param unselectedAttribute
     */
    void onUnselected(GoodsAttrBean.ListsBean.AttrValueBean unselectedAttribute);

    /**
     * 属性选中
     *
     * @param selectAttribute
     */
    void onSelect(GoodsAttrBean.ListsBean.AttrValueBean selectAttribute);

    /**
     * sku选中
     *
     * @param sku
     */
    void onSkuSelected(GoodsAttrBean.ListsBean sku);
}