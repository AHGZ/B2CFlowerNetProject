package com.android.p2pflowernet.project.view.fragments.brand;

import com.android.p2pflowernet.project.entity.BrandClassBean;
import com.android.p2pflowernet.project.entity.BrandScendBean;

/**
 * @描述: 品牌分类视图层
 * @创建人：zhangpeisen
 * @创建时间：2017/12/5 上午9:37
 * @修改人：zhangpeisen
 * @修改时间：2017/12/5 上午9:37
 * @修改备注：
 * @throws
 */
public interface IBrandView {
    // 一级商品分类id
    String getCateid();


    void showDialog();

    void hideDialog();

    void onError(String errorMsg);

    void onSuccess(String message);

    // 一级分类
    void onSuccess(BrandClassBean brandClassBean);

    // 二级分类
    void onSuccess(BrandScendBean brandScendBean);
}
