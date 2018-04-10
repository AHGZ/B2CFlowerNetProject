package com.android.p2pflowernet.project.entity;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @描述: O2o购物车实体类
 * @创建人：zhangpeisen
 * @创建时间：2018/1/10 下午8:00
 * @修改人：zhangpeisen
 * @修改时间：2018/1/10 下午8:00
 * @修改备注：
 * @throws
 */
public class ShopCart implements Serializable {

    private int shoppingAccount;//商品总数
    private double shoppingTotalPrice;//商品总价钱
    private Map<O2oIndexBean.ListsBean.GoodsListBean, Integer> shoppingSingle;//单个物品的总价价钱
    // 商品ID
    private String id;
    // 商品规格
    private String spec;
    // 商品价格
    private String specprice;
    private int index = -1;

    private int num;
    private O2oIndexBean.ListsBean.GoodsListBean next;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public ShopCart() {
        this.shoppingAccount = 0;
        this.shoppingTotalPrice = 0;
        this.shoppingSingle = new HashMap<>();
    }

    public int getShoppingAccount() {
        return shoppingAccount;
    }

    public double getShoppingTotalPrice() {
        return shoppingTotalPrice;
    }

    public Map<O2oIndexBean.ListsBean.GoodsListBean, Integer> getShoppingSingleMap() {
        return shoppingSingle;
    }

    public String getId() {
        return id;
    }


    public String getSpec() {
        return TextUtils.isEmpty(spec) ? "0" : spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getSpecprice() {
        return specprice;
    }

    public void setSpecprice(String specprice) {
        this.specprice = specprice;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean addShoppingSingle(O2oIndexBean.ListsBean.GoodsListBean goodsListBean) {
        int remain = Integer.parseInt(goodsListBean.getStock());
        if (remain <= 0)
            return false;
        goodsListBean.setStock(String.valueOf(--remain));
        int num = 0;
        if (shoppingSingle.containsKey(goodsListBean)) {
            num = shoppingSingle.get(goodsListBean);
        }
        num += 1;
        // 添加商品id
        id = goodsListBean.getId();
        shoppingSingle.put(goodsListBean, num);
        shoppingTotalPrice += Double.parseDouble(goodsListBean.getPrice());
        shoppingAccount++;
        return true;
    }

    public boolean subShoppingSingle(O2oIndexBean.ListsBean.GoodsListBean goodsListBean) {

        int num = 0;
//        if (goodsListBean.getId().equals(id)) {
//            Iterator<O2oIndexBean.ListsBean.GoodsListBean> data = shoppingSingle.keySet().iterator();
//            while (data.hasNext()) {
//
//                next = data.next();
//                if (next.getId().equals(goodsListBean.getId())) {
//
//                    num = shoppingSingle.get(next);
//                }
//            }
//        }

        if (shoppingSingle.containsKey(goodsListBean)) {
            num = shoppingSingle.get(goodsListBean);
        }
        if (num <= 0) return false;
        num--;
        int remain = Integer.parseInt(goodsListBean.getStock());
        goodsListBean.setStock(String.valueOf(++remain));
        shoppingSingle.remove(goodsListBean);
        shoppingSingle.put(goodsListBean,num);
        if (num == 0) shoppingSingle.remove(goodsListBean);
        id = goodsListBean.getId();
        shoppingTotalPrice -= Double.parseDouble(goodsListBean.getPrice());
        shoppingAccount--;
        return true;
    }

    public int getDishAccount() {

        return shoppingSingle.size();
    }

    public void clear() {

        this.shoppingAccount = 0;
        this.shoppingTotalPrice = 0;
        this.shoppingSingle.clear();
    }
}
