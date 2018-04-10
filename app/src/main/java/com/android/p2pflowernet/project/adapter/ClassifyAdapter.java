package com.android.p2pflowernet.project.adapter;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
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
import com.android.p2pflowernet.project.entity.ClassifBean;
import com.android.p2pflowernet.project.utils.GlideImageLoader;
import com.android.p2pflowernet.project.view.activity.GoodsDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caishen on 2017/12/2.
 * by--分类筛选主页商品数据
 */

public class ClassifyAdapter extends HFWBaseAdapter<ClassifBean.GoodsBean> {

    private final FragmentActivity mContext;

    public ClassifyAdapter(FragmentActivity activity) {

        this.mContext = activity;
    }

    @Override
    public BaseHolder<ClassifBean.GoodsBean> onViewHolderCreate(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_classif_layout, parent, false);
        return new ClassifViewHolder(view);
    }

    @Override
    public void onViewHolderBind(BaseHolder<ClassifBean.GoodsBean> holder, int position) {

        ClassifBean.GoodsBean auditHistoryBean = list.get(position);
        ((ClassifViewHolder) holder).bindDateView(auditHistoryBean, position, mContext);
    }

    static class ClassifViewHolder extends BaseHolder<ClassifBean.GoodsBean> {

        @BindView(R.id.iv_img)
        ImageView ivImg;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_cs)
        TextView tvCs;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_back)
        TextView tvBack;
        @BindView(R.id.linear_goods)
        LinearLayout linearLayout;

        ClassifViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        //设置数据
        public void bindDateView(final ClassifBean.GoodsBean auditHistoryBean, int position, final FragmentActivity mContext) {

            tvTitle.setText(TextUtils.isEmpty(auditHistoryBean.getGoods_name()) ? "" : auditHistoryBean.getGoods_name());
            tvMoney.setText(TextUtils.isEmpty("¥" + auditHistoryBean.getSale_price()) ? "" : "¥" + auditHistoryBean.getSale_price());
            tvBack.setText(TextUtils.isEmpty("花返 ¥ +" + auditHistoryBean.getHuafan()) ? "" : "花返 ¥" + auditHistoryBean.getHuafan());
            tvCs.setText(TextUtils.isEmpty(auditHistoryBean.getGuige()) ? "" : auditHistoryBean.getGuige());
            GlideImageLoader glideImageLoader = new GlideImageLoader();
            String imgPath = ApiUrlConstant.API_IMG_URL + auditHistoryBean.getImg();
            glideImageLoader.displayImage(mContext, imgPath, ivImg);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                    intent.putExtra("goodsId", auditHistoryBean.getId());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
