package com.android.p2pflowernet.project.view.fragments.trade;


import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.GoodsInventoryCount;
import com.android.p2pflowernet.project.entity.OrderDetailBean;
import com.android.p2pflowernet.project.entity.ShopCarBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.goods.detail.GoodsDetailModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * @描述:购物车逻辑层
 * @创建人：zhangpeisen
 * @创建时间：2017/12/8 下午1:41
 * @修改人：zhangpeisen
 * @修改时间：2017/12/8 下午1:41
 * @修改备注：
 * @throws
 */
public class ITradePresenter extends IPresenter<ITradeView> {

    private TradeModel tradeModel;
    private final GoodsDetailModel goodsDetailModel;


    public ITradePresenter() {

        tradeModel = new TradeModel();
        goodsDetailModel = new GoodsDetailModel();
    }

    /**
     * @throws
     * @描述:获取购物车数据
     * @方法名:getShopCars
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/7 下午5:12
     * @修改人 zhangpeisen
     * @修改时间 2017/12/7 下午5:12
     * @修改备注
     */
    public void getShopCars() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            if (getView() !=null) {
                getView().onError("网络信号弱,请稍后重试");
            }
            return;
        }

        if (getView() != null) {
            getView().showDialog();
        }

        tradeModel.getShopCars(new IModelImpl<ApiResponse<ShopCarBean>, ShopCarBean>() {
            @Override
            protected void onSuccess(ShopCarBean data, String message) {
                if (getView() != null) {
                    getView().hideDialog();
                    getView().SuccessShopCar(data);
                }
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<ShopCarBean>> data, String message) {
                if (getView() != null) {
                    getView().hideDialog();
                }

            }

            @Override
            protected void onFailure(String code, String message) {
                if (getView() != null) {
                    getView().hideDialog();
                    getView().onError(message);
                }
            }

            @Override
            protected void onSuccess() {

            }
        });
    }

    //更改购物车库存
    public void editCartCount() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            if (getView() != null) {
                getView().onError("网络信号弱,请稍后重试");
            }
            return;
        }

        String cartId = getView().getCartId();
        String goodId = getView().getGoodId();
        String specId = getView().getSpecId();
        int num = getView().getNum();
        String isChoose = getView().getIsChoose();

        if (num < 1) {
            if (getView() != null) {
                getView().onError("该商品不能少于一件");
            }
            return;
        }

        if (getView() != null) {
            getView().showDialog();
        }
        tradeModel.editCarCount(cartId, goodId, specId, num, isChoose, new IModelImpl<ApiResponse<GoodsInventoryCount>, GoodsInventoryCount>() {
            @Override
            protected void onSuccess(GoodsInventoryCount data, String message) {
                if (getView() != null) {
                    getView().hideDialog();
                    getView().SuccessEditInventory(data);
                }
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<GoodsInventoryCount>> data, String message) {
                if (getView() != null) {
                    getView().hideDialog();
                }
            }

            @Override
            protected void onFailure(String code, String message) {
                if (getView() != null) {
                    getView().hideDialog();
                    getView().onError(message);
                }
            }

            @Override
            protected void onSuccess() {

            }
        });
    }

    //删除商品
    public void delGoods() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            if (getView() !=null) {
                getView().onError("网络信号弱,请稍后重试");
            }
            return;
        }

        String cartId = getView().getCartId();

        if (TextUtils.isEmpty(cartId)) {
            if (getView() != null) {
                getView().onError("请选中一个商品");
            }
            return;
        }

        if (getView() != null) {
            getView().showDialog();
        }
        tradeModel.delCarGoods(cartId, new IModelImpl<ApiResponse<String>, String>() {
            @Override
            protected void onSuccess(String data, String message) {
                if (getView() != null) {
                    getView().hideDialog();
                    getView().onSuccess(message);
                }
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<String>> data, String message) {
                if (getView() != null) {
                    getView().hideDialog();
                }

            }

            @Override
            protected void onFailure(String code, String message) {
                if (getView() != null) {
                    getView().hideDialog();
                    getView().onError(message);
                }
            }

            @Override
            protected void onSuccess() {

            }
        });
    }

    //设置购物车选中状态
    public void setCarGoods() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            if (getView() != null) {
                getView().onError("网络信号弱,请稍后重试");
            }
            return;
        }

        String cartId = getView().getCartId();

        if (TextUtils.isEmpty(cartId)) {
            if (getView() != null) {
                getView().onError("请选中一个商品");
            }
            return;
        }

        String isChoose = getView().getIsChoose();

        if (getView() != null) {
            getView().showDialog();
        }
        tradeModel.setCarGoods(cartId, isChoose, new IModelImpl<ApiResponse<String>, String>() {
            @Override
            protected void onSuccess(String data, String message) {
                if (getView() != null) {
                    getView().hideDialog();
                    getView().onSuccessSet(message);
                }
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<String>> data, String message) {
                if (getView() != null) {
                    getView().hideDialog();
                }
            }

            @Override
            protected void onFailure(String code, String message) {
                if (getView() != null) {
                    getView().hideDialog();
                    getView().onError(message);
                }
            }

            @Override
            protected void onSuccess() {

            }
        });
    }


    @Override
    protected void cancel() {

        tradeModel.cancel();
    }

    //确认订单
    public void orderSel() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            if (getView() != null) {
                getView().onError("网络信号弱,请稍后重试");
            }
            return;
        }

        String specId = getView().getSpecId();
        String count = getView().getNum() + "";
        String source = getView().getSource();
        String select = getView().getSelect();

        goodsDetailModel.orderSel(specId, count, source, select, new IModelImpl<ApiResponse<OrderDetailBean>, OrderDetailBean>() {
            @Override
            protected void onSuccess(OrderDetailBean data, String message) {
                if (getView() != null) {
                    getView().hideDialog();
                    getView().SuccessOrder(data);
                }
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<OrderDetailBean>> data, String message) {
                if (getView() != null) {
                    getView().hideDialog();
                }
            }

            @Override
            protected void onFailure(String code, String message) {
                if (getView() != null) {
                    getView().hideDialog();
                    getView().onError(message);
                }
            }

            @Override
            protected void onSuccess() {

            }
        });
    }
}
