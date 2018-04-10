package com.android.p2pflowernet.project.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.callback.ShopCartImp;
import com.android.p2pflowernet.project.entity.O2oIndexBean;
import com.android.p2pflowernet.project.entity.ShopCart;
import com.android.p2pflowernet.project.event.MessageEvent;
import com.android.p2pflowernet.project.event.RefreshSyncListCart;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @描述:已选菜品列表
 * @创建人：zhangpeisen
 * @创建时间：2018/1/10 下午8:04
 * @修改人：zhangpeisen
 * @修改时间：2018/1/10 下午8:04
 * @修改备注：
 * @throws
 */
public class PopCartAdapter extends RecyclerView.Adapter {

    private static String TAG = "PopCartAdapter";
    private ShopCart shopCart;
    private Context context;
    private int itemCount;
    private ArrayList<O2oIndexBean.ListsBean.GoodsListBean> dishList;
    private ShopCartImp shopCartImp;

    public PopCartAdapter(Context context, ShopCart shopCart) {
        this.shopCart = shopCart;
        this.context = context;
        this.itemCount = shopCart.getDishAccount();
        this.dishList = new ArrayList<>();
        dishList.addAll(shopCart.getShoppingSingleMap().keySet());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);
        DishViewHolder viewHolder = new DishViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        DishViewHolder dishholder = (DishViewHolder) holder;
        final O2oIndexBean.ListsBean.GoodsListBean goodsListBean = getDishByPosition(position);
        if (goodsListBean != null) {

            dishholder.carName.setText(goodsListBean.getName());
            dishholder.carPrice.setText(goodsListBean.getPrice() + "");
            int num = shopCart.getShoppingSingleMap().get(goodsListBean) == null ? 0 : shopCart.getShoppingSingleMap().get(goodsListBean);
            dishholder.tvGoodsSelectNum.setText(num + "");

            dishholder.ivGoodsAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (shopCart.addShoppingSingle(goodsListBean)) {
                        notifyItemChanged(position);
                        if (shopCartImp != null)
                            shopCartImp.add(shopCart, position);

                        changeShopCart();
                        EventBus.getDefault().post(new RefreshSyncListCart());
                    }
                }
            });


            dishholder.ivGoodsMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (shopCart.subShoppingSingle(goodsListBean)) {
                        dishList.clear();
                        dishList.addAll(shopCart.getShoppingSingleMap().keySet());
                        itemCount = shopCart.getDishAccount();
                        notifyItemChanged(position);
                        if (shopCartImp != null)
                            shopCartImp.remove(shopCart, position);
                        subChangeShopCart();
                        EventBus.getDefault().post(new RefreshSyncListCart());
                    }
                }
            });
        }
    }

    /**
     * 修改购物车状态(减)
     */
    private void subChangeShopCart() {

        EventBus.getDefault().post(new MessageEvent("sub", shopCart.getShoppingAccount(),
                shopCart.getShoppingTotalPrice(), shopCart));
    }

    /**
     * 修改购物车状态（加）
     */
    private void changeShopCart() {

        EventBus.getDefault().post(new MessageEvent("add", shopCart.getShoppingAccount(),
                shopCart.getShoppingTotalPrice(), shopCart));
    }

    @Override
    public int getItemCount() {
        return this.itemCount;
    }

    public O2oIndexBean.ListsBean.GoodsListBean getDishByPosition(int position) {
        return dishList.get(position);
    }

    public ShopCartImp getShopCartImp() {
        return shopCartImp;
    }

    public void setShopCartImp(ShopCartImp shopCartImp) {
        this.shopCartImp = shopCartImp;
    }

    public class DishViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.car_name)
        TextView carName;
        @BindView(R.id.car_price)
        TextView carPrice;
        @BindView(R.id.ivGoodsAdd)
        // 加
                ImageView ivGoodsAdd;
        @BindView(R.id.tvGoodsSelectNum)
        // 选择菜单个数
                TextView tvGoodsSelectNum;
        @BindView(R.id.ivGoodsMinus)
        ImageView ivGoodsMinus;

        public DishViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 显示减号的动画
     *
     * @return
     */
    private Animation getShowAnimation() {
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0, 720, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 2f
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }


    /**
     * 隐藏减号的动画
     *
     * @return
     */
    private Animation getHiddenAnimation() {
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0, 720, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 4f
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(1, 0);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }
}
