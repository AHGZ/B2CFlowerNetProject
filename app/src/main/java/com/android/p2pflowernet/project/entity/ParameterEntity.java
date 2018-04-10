package com.android.p2pflowernet.project.entity;

/**
 * @描述:临时赋值商品属性实体
 * @创建人：zhangpeisen
 * @创建时间：2017/12/19 下午4:06
 * @修改人：zhangpeisen
 * @修改时间：2017/12/19 下午4:06
 * @修改备注：
 * @throws
 */
public class ParameterEntity {
    public GoodsAttrBean.ListsBean.AttrValueBean attrValueBean;
    public boolean selected = false;

    public ParameterEntity(GoodsAttrBean.ListsBean.AttrValueBean attrValueBean) {
        this.attrValueBean = attrValueBean;
    }

    @Override
    public String toString() {
        return "ParameterEntity{" +
                "attrValueBean=" + attrValueBean +
                ", selected=" + selected +
                '}';
    }
}
