package com.android.p2pflowernet.project.adapter;

import android.graphics.Paint;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.OrderDetailsBean;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by heguozhong on 2018/1/11/011.
 * 外卖订单明细商品适配器
 */

public class TakeFullDetailListviewAdapter extends BaseAdapter {
    private List<OrderDetailsBean.GoodsListsBean> goods_lists;
    private FragmentActivity activity;
    private int itemCount = 2;

    public TakeFullDetailListviewAdapter(FragmentActivity activity, List<OrderDetailsBean.GoodsListsBean> goods_lists) {
        this.goods_lists=goods_lists;
        this.activity=activity;
    }

    public TakeFullDetailListviewAdapter(int num) {

    }

    @Override
    public int getCount() {
        if (goods_lists.size() > 2) {
            return itemCount;
        } else {
            return goods_lists.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return goods_lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.confirm_order_item_listview, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.confirmOrderListviewOutprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        //赋值商品名称
        viewHolder.confirmOrderListviewName.setText(goods_lists.get(position).getGoods_name());
        //赋值商品价格
        viewHolder.confirmOrderListviewPrice.setText("￥"+goods_lists.get(position).getGoods_price());
        //赋值商品数量
        viewHolder.confirmOrderListviewSum.setText("x"+goods_lists.get(position).getGoods_num());
        //赋值商品花返
        viewHolder.confirmOrderListviewHfPrice.setText("￥"+goods_lists.get(position).getRebate());
        //赋值商品logo
        String logo_url = goods_lists.get(position).getFile_path();
        String logoUrl = ApiUrlConstant.API_IMG_URL + logo_url;
        Glide.with(activity).load(logoUrl).placeholder(R.drawable.dp).into(viewHolder.confirmOrderListviewImg);
        return convertView;
    }

    public void addItemNum(int number) {
        itemCount = number;
    }

    static class ViewHolder {
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

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
