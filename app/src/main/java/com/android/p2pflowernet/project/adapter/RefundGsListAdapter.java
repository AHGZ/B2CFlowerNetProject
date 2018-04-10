package com.android.p2pflowernet.project.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.base.HFWBaseAdapter;
import com.android.p2pflowernet.project.callback.BaseHolder;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.RefundDetailBean;
import com.android.p2pflowernet.project.utils.GlideImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.android.p2pflowernet.project.R.id.goodsmoney_tv;

/**
 * author: zhangpeisen
 * created on: 2017/12/18 下午2:19
 * description: 退换货详情(商品列表)
 */
public class RefundGsListAdapter extends HFWBaseAdapter<RefundDetailBean.GoodsListBean> {


    @Override
    public BaseHolder<RefundDetailBean.GoodsListBean> onViewHolderCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mine_refundgoodslist_itemlayout, parent, false);
        return new RefundGsListHolder(view);
    }

    @Override
    public void onViewHolderBind(BaseHolder<RefundDetailBean.GoodsListBean> holder, int position) {
        RefundDetailBean.GoodsListBean goodsListBean = list == null ? null : list.get(position);
        ((RefundGsListHolder) holder).bindDateView(goodsListBean);
    }

    class RefundGsListHolder extends BaseHolder<RefundDetailBean.GoodsListBean> {
        @BindView(R.id.imageview_goods)
        // 商品图片
                ImageView imageviewGoods;
        @BindView(R.id.tv_merchandise_name)
        // 商品名称
                TextView tvMerchandiseName;
        @BindView(R.id.tv_colorattr)
        // 商品规格
                TextView tvColorattr;
        @BindView(goodsmoney_tv)
        // 商品价格
                TextView goodsmoneyTv;
        @BindView(R.id.ly_goodsinfo)
        LinearLayout lyGoodsinfo;
        @BindView(R.id.tv_merchandise_price)
        TextView tvMerchandisePrice;

        public RefundGsListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindDateView(RefundDetailBean.GoodsListBean goodsListBean) {
            if (goodsListBean == null) {
                return;
            }
            // 商品名称
            tvMerchandiseName.setText(TextUtils.isEmpty(goodsListBean.getGoods_name()) ? "" : goodsListBean.getGoods_name());
            // 商品规格
            tvColorattr.setText(TextUtils.isEmpty(goodsListBean.getGoods_spec()) ? "" : goodsListBean.getGoods_spec());
            // 商品单价
            goodsmoneyTv.setText(TextUtils.isEmpty(goodsListBean.getGoods_price()) ? "" : "¥"+goodsListBean.getGoods_price());
            // 商品信息
            tvMerchandisePrice.setText(String.format(
                    getContext().getResources().getString(R.string.str_merchandise_price), TextUtils.isEmpty(goodsListBean.getGoods_num()) ? ""
                            : goodsListBean.getGoods_num()));
            // 商品图片
            GlideImageLoader glideImageLoader = new GlideImageLoader();
            glideImageLoader.displayImage(getContext(), ApiUrlConstant.API_IMG_URL + goodsListBean.getGoods_img(), imageviewGoods);

        }

    }

}
