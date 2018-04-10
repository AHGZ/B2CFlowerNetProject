package com.android.p2pflowernet.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.TakeOutBean;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangkun on 2018/1/24.
 */

public class RefundAdapter extends BaseAdapter {
    private List<TakeOutBean.ListBean.GoodsBean> data;
    private Context mContext;

    public RefundAdapter(List<TakeOutBean.ListBean.GoodsBean> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_refund_list,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TakeOutBean.ListBean.GoodsBean bean = data.get(position);
        viewHolder.tv_name.setText(bean.getGoods_name());
        viewHolder.tv_spce.setText(bean.getGoods_spec());
        viewHolder.tv_pPrice.setText("¥" + bean.getGoods_price());
        viewHolder.tv_number.setText("x" + bean.getGoods_num());
        viewHolder.tv_takeBack.setText("花返：" + bean.getRebate());
        Glide.with(mContext)
                .load(ApiUrlConstant.API_IMG_URL + bean.getFile_path())
                .placeholder(R.mipmap.default_header)
                .error(R.mipmap.default_header)
                .into(viewHolder.mImageView);
        return convertView;
    }

    static class ViewHolder{
        @BindView(R.id.item_refund_img)
        ImageView mImageView;
        @BindView(R.id.item_refund_tv_name)
        TextView tv_name;
        @BindView(R.id.item_refund_tv_spec)
        TextView tv_spce;
        @BindView(R.id.item_refund_tv_pPrice)
        TextView tv_pPrice;
        @BindView(R.id.item_refund_tv_oPrice)
        TextView tv_oPrice;
        @BindView(R.id.item_refund_tv_number)
        TextView tv_number;
        @BindView(R.id.item_refund_tv_takeBack)
        TextView tv_takeBack;

        ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
