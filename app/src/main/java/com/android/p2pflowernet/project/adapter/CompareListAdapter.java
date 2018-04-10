package com.android.p2pflowernet.project.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.CompareListBean;
import com.android.p2pflowernet.project.utils.GlideImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caishen on 2018/1/29.
 * by--
 */

public class CompareListAdapter extends BaseExpandableListAdapter {
    private final FragmentActivity mContext;
    private final List<CompareListBean.ListBean> data;

    public CompareListAdapter(FragmentActivity activity, List<CompareListBean.ListBean> data) {
        this.mContext = activity;
        this.data = data;
    }

    @Override
    public int getGroupCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        CompareListBean.ListBean listBean = data.get(groupPosition);
        List<CompareListBean.ListBean.GoodsBean> goods = listBean.getGoods();

        return goods == null ? 0 : goods.size();
    }

    @Override
    public Object getGroup(int groupPosition) {

        CompareListBean.ListBean listBean = data.get(groupPosition);
        return listBean;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        CompareListBean.ListBean listBean = data.get(groupPosition);
        CompareListBean.ListBean.GoodsBean goodsBean = listBean.getGoods().get(childPosition);
        return goodsBean == null ? "" : goodsBean;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        GroupViewHolder groupViewHolder;
        if (convertView == null) {

            convertView = View.inflate(mContext, R.layout.item_compare, null);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);

        } else {

            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }

        CompareListBean.ListBean listBean = data.get(groupPosition);
        groupViewHolder.tvBrand.setText(listBean.getName() == null ? "" : listBean.getName());

        //1-京东 2-天猫 3-国美 4-苏宁
        switch (listBean.getName()) {
            case "京东":
                groupViewHolder.ivBrand.setBackgroundResource(R.drawable.icon_jingdong);
                break;
            case "天猫":
                groupViewHolder.ivBrand.setBackgroundResource(R.drawable.icon_tianmao);
                break;
            case "国美":
                groupViewHolder.ivBrand.setBackgroundResource(R.drawable.icon_guomei);
                break;
            case "苏宁":
                groupViewHolder.ivBrand.setBackgroundResource(R.drawable.icon_suning);
                break;
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ChildViewHolder childViewHolder;
        if (convertView == null) {

            convertView = View.inflate(mContext, R.layout.item_compare_child, null);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);

        } else {

            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        //设置数据
        CompareListBean.ListBean.GoodsBean goodsBean = data.get(groupPosition).getGoods().get(childPosition);
        childViewHolder.tvDescGov.setText(goodsBean.getGoods_name() == null ? "" : goodsBean.getGoods_name());
        childViewHolder.tvPriceGov.setText(goodsBean.getPrice() == null ? "¥" : "¥" + goodsBean.getPrice());
        childViewHolder.tvDescProperty.setText(goodsBean.getSpec() == null ? "" : goodsBean.getSpec());
        String img_url = ApiUrlConstant.API_IMG_URL + goodsBean.getImg_url();
        new GlideImageLoader().displayImage(mContext, img_url, childViewHolder.ivGov);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    static class GroupViewHolder {
        @BindView(R.id.iv_brand)
        ImageView ivBrand;
        @BindView(R.id.tv_brand)
        TextView tvBrand;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ChildViewHolder {
        @BindView(R.id.iv_gov)
        ImageView ivGov;
        @BindView(R.id.iv_stockout)
        ImageView ivStockout;
        @BindView(R.id.tv_desc_gov)
        TextView tvDescGov;
        @BindView(R.id.tv_desc_property)
        TextView tvDescProperty;
        @BindView(R.id.tv_price_gov)
        TextView tvPriceGov;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
