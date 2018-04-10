package com.android.p2pflowernet.project.view.fragments.trade;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;
import com.android.p2pflowernet.project.base.BaseApplication;
import com.android.p2pflowernet.project.entity.GoodsInfo;
import com.android.p2pflowernet.project.entity.StoreInfo;
import com.android.p2pflowernet.project.utils.CacheUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;


/**
 * 购物车数据存储类
 */
public class CartProvider {

    public static final String JSON_CART = "json_cart";
    private Context context;

    //优化过的HashMap集合
    private SparseArray<GoodsInfo> datas;

    private static CartProvider cartProvider;

    public CartProvider(Context context) {
        this.context = context;
        datas = new SparseArray<>(100);
        listToSparse();
    }

    public static CartProvider getInstance() {
        if (cartProvider == null) {
            cartProvider = new CartProvider(BaseApplication.getContext());
        }
        return cartProvider;
    }

    private void listToSparse() {

        List<GoodsInfo> carts = getAllData();
        //放到sparseArry中
        if (carts != null && carts.size() > 0) {
            for (int i = 0; i < carts.size(); i++) {
                GoodsInfo goodsBean = carts.get(i);
                datas.put(Integer.parseInt(goodsBean.getId()), goodsBean);
            }
        }
    }


    private List<GoodsInfo> parsesToList() {

        List<GoodsInfo> carts = new ArrayList<>();
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsInfo shoppingCart = datas.valueAt(i);
                carts.add(shoppingCart);
            }
        }
        return carts;
    }

    public List<GoodsInfo> getAllData() {
        return getDataFromLocal();
    }

    //本地获取json数据，并且通过Gson解析成list列表数据
    public List<GoodsInfo> getDataFromLocal() {

        List<GoodsInfo> carts = new ArrayList<>();
        //从本地获取缓存数据
        String savaJson = CacheUtils.getString(context, JSON_CART);
        if (!TextUtils.isEmpty(savaJson)) {
            //把数据转换成列表
            carts = new Gson().fromJson(savaJson, new TypeToken<List<StoreInfo>>() {
            }.getType());
        }

        return carts;
    }

    public void addData(GoodsInfo cart) {

        //添加数据
        GoodsInfo tempCart = datas.get(Integer.parseInt(cart.getId()));

        if (tempCart != null) {

            tempCart.setCount(cart.getCount() + 1);

        } else {

            tempCart = cart;
            tempCart.setCount(1);
        }

        datas.put(Integer.parseInt(tempCart.getId()), tempCart);

        //保存数据
        commit();
    }

    //保存数据
    private void commit() {

        //把parseArray转换成list
        List<GoodsInfo> carts = parsesToList();
        //把转换成String
        String json = new Gson().toJson(carts);
        // 保存
        CacheUtils.putString(context, JSON_CART, json);
    }


    public void deleteData(GoodsInfo cart) {

        //删除数据
        datas.delete(Integer.parseInt(cart.getId()));

        //保存数据
        commit();
    }

    public void updataData(GoodsInfo cart) {

        //修改数据
        datas.put(Integer.parseInt(cart.getId()), cart);
        //保存数据
        commit();
    }
}
