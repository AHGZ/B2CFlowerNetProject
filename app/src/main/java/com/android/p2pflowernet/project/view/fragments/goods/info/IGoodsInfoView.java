package com.android.p2pflowernet.project.view.fragments.goods.info;

import com.android.p2pflowernet.project.entity.ChangeGsAttrBean;
import com.android.p2pflowernet.project.entity.EveluateBean;
import com.android.p2pflowernet.project.entity.GoodsAttrBean;
import com.android.p2pflowernet.project.entity.GoodsInfoBean;
import com.android.p2pflowernet.project.entity.GuaranteeBean;
import com.android.p2pflowernet.project.entity.OrderDetailBean;
import com.android.p2pflowernet.project.entity.ProductParamBean;
import com.android.p2pflowernet.project.entity.ShareGoodsBean;
import com.android.p2pflowernet.project.entity.SpecCompareBean;

/**
 * @描述: 商品详情视图类
 * @创建人：zhangpeisen
 * @创建时间：2017/10/18 上午10:19
 * @修改人：zhangpeisen
 * @修改时间：2017/10/18 上午10:19
 * @修改备注：
 * @throws
 */

public interface IGoodsInfoView {


    String goodsId();

    // 规格id
    String sepcId();

    // 购买数量
    String ShopCarcount();

    String optId1();

    String optId2();

    String optId3();

    int getPage();

    void showDialog();

    void hideDialog();

    void onSuccess(String message);

    // 获取商品评价
    void successEveluate(EveluateBean data);

    // 获取商品详情
    void successGsInfo(GoodsInfoBean data);

    // 获取商品属性列表
    void successGoodsSpec(GoodsAttrBean data);

    // 修改商品属性
    void changeGoodsSpec(ChangeGsAttrBean data);

    // 产品参数
    void productParam(ProductParamBean productParamBean);

    void onError(String message);

    void onSuccessGuarantee(GuaranteeBean data);

    String getSource();

    void SuccessOrder(OrderDetailBean data);

    void onAddShopCarSuccess(String message);

    String getSelect();

    String getGoodId();

    void SuccessShare(ShareGoodsBean data);

    void Successcompare(SpecCompareBean data);
}
