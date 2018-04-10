package com.android.p2pflowernet.project.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.TakeOutOrderGroupBean;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by heguozhong on 2018/1/15/015.
 */

public class GroupGullDetailsListviewAdapter extends BaseAdapter {
    private Context context;
    private TakeOutOrderGroupBean.ListBean group_listBean;

    public GroupGullDetailsListviewAdapter(Context context, TakeOutOrderGroupBean.ListBean group_listBean) {
        this.context = context;
        this.group_listBean=group_listBean;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.group_full_detail_listview_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String img = ApiUrlConstant.API_IMG_URL + group_listBean.getFile_path();
        Glide.with(context).load(img).asBitmap().placeholder(R.mipmap.default_header).into(viewHolder.groupFullDetailImg);
        viewHolder.groupFullDetailShopName.setText(group_listBean.getManager_name());//店铺名称
        viewHolder.groupFullDetailFoodName.setText(group_listBean.getTitle());//食物名称
        viewHolder.groupFullDetailShopPrice.setText("花返: ¥"+group_listBean.getRebate_amount());//店铺花返价格
        viewHolder.groupFullDetailFoodPrice.setText("¥"+group_listBean.getOrder_amount());//食物价格
        return convertView;
    }

    static class ViewHolder {
        //店铺logo
        @BindView(R.id.group_full_detail_img)
        ImageView groupFullDetailImg;
        //食物名称
        @BindView(R.id.group_full_detail_food_name)
        TextView groupFullDetailFoodName;
        //食物价格
        @BindView(R.id.group_full_detail_food_price)
        TextView groupFullDetailFoodPrice;
        //店铺名称
        @BindView(R.id.group_full_detail_shop_name)
        TextView groupFullDetailShopName;
        //店铺花返价格
        @BindView(R.id.group_full_detail_shop_price)
        TextView groupFullDetailShopPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

