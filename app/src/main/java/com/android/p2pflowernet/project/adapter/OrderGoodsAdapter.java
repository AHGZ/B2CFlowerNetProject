package com.android.p2pflowernet.project.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.callback.ShopCartImp;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.O2oIndexBean;
import com.android.p2pflowernet.project.entity.ShopCart;
import com.android.p2pflowernet.project.utils.RxImageLoader;
import com.caimuhao.rxpicker.utils.DensityUtil;

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
public class OrderGoodsAdapter extends RecyclerView.Adapter {

    private static String TAG = "PopCartAdapter";
    private ShopCart shopCart;
    private Context context;
    private int itemCount;
    private ArrayList<O2oIndexBean.ListsBean.GoodsListBean> dishList;
    private ShopCartImp shopCartImp;

    public OrderGoodsAdapter(Context context, ShopCart shopCart) {
        this.shopCart = shopCart;
        this.context = context;
        this.itemCount = shopCart.getDishAccount();
        this.dishList = new ArrayList<>();
        dishList.addAll(shopCart.getShoppingSingleMap().keySet());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.confirm_order_item_listview, parent, false);
        DishViewHolder viewHolder = new DishViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        DishViewHolder dishholder = (DishViewHolder) holder;
        final O2oIndexBean.ListsBean.GoodsListBean goodsListBean = getDishByPosition(position);
        if (goodsListBean != null) {
            // 商品图片
            String logo_url = ApiUrlConstant.API_IMG_URL + goodsListBean.getGoods_img();
            new RxImageLoader().display(dishholder.confirmOrderListviewImg, logo_url,
                    DensityUtil.getDeviceWidth(dishholder.confirmOrderListviewImg.getContext()) / 3,
                    DensityUtil.getDeviceWidth(dishholder.confirmOrderListviewImg.getContext()) / 3);
            // 商品名称
            dishholder.confirmOrderListviewName.setText(TextUtils.isEmpty(goodsListBean.getName()) ? "" : goodsListBean.getName());
            // 商品价格
            dishholder.confirmOrderListviewPrice.setText(TextUtils.isEmpty(goodsListBean.getPrice()) ? "¥" : "¥" + goodsListBean.getPrice());
            // 商品过去价格
//            dishholder.confirmOrderListviewOutprice.setText(TextUtils.isEmpty(goodsListBean.getPrice()) ? "" : "¥" + goodsListBean.getPrice());
            dishholder.confirmOrderListviewHfPrice.setText(TextUtils.isEmpty(goodsListBean.getHuafan()) ? "花返" : "花返 ：¥" + goodsListBean.getHuafan());
            // 商品数量
            int num = shopCart.getShoppingSingleMap().get(goodsListBean);
            dishholder.confirmOrderListviewSum.setText("X" + num);
        }
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
        //商品图片
        @BindView(R.id.confirm_order_listview_img)
        ImageView confirmOrderListviewImg;
        //商品名称
        @BindView(R.id.confirm_order_listview_name)
        TextView confirmOrderListviewName;
        //商品价格
        @BindView(R.id.confirm_order_listview_price)
        TextView confirmOrderListviewPrice;
        //商品过去价格
        @BindView(R.id.confirm_order_listview_outprice)
        TextView confirmOrderListviewOutprice;
        //商品数量
        @BindView(R.id.confirm_order_listview_sum)
        TextView confirmOrderListviewSum;
        //商品花返价格
        @BindView(R.id.confirm_order_listview_hf_price)
        TextView confirmOrderListviewHfPrice;

        public DishViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
