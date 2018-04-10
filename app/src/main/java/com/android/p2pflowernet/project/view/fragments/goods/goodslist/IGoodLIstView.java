package com.android.p2pflowernet.project.view.fragments.goods.goodslist;

import com.android.p2pflowernet.project.entity.AllSortBean;
import com.android.p2pflowernet.project.entity.BrandSortBean;
import com.android.p2pflowernet.project.entity.ClassifBean;

/**
 * @描述:商品筛选列表的视图层
 * @创建人：zhangpeisen
 * @创建时间：2017/12/5 下午7:29
 * @修改人：zhangpeisen
 * @修改时间：2017/12/5 下午7:29
 * @修改备注：
 * @throws
 */
public interface IGoodLIstView {


    // 二级商品分类id
    String getScendCateid();

    // 是否新品 int 是1为新品  0为全部
    String getIsNew();

    // 品牌id以 ，隔开(1,2,3)
    String getBrand();

    // 筛选状态
    String getZt();

    // 价格区间  (1,2)
    String getSalePrice();

    // 价格规格 0为无数据1为升序2为降序
    String getOrder();

    // 分页页数
    int getPages();


    String getCateid();

    void onError(String errorMsg);

    void onSuccess(String message);

    void showDialog();

    void hideDialog();

    void onSuccessClassif(ClassifBean data);

    void onBrandSortSuccess(BrandSortBean brandSortBean);

    void onAllBrandSortSuccess(AllSortBean allSortBean);

    String getSearchName();

    String getLowPrice();

    String getHeightPrice();
}
