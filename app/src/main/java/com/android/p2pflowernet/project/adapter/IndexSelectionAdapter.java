package com.android.p2pflowernet.project.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.GoodsInfo;
import com.android.p2pflowernet.project.entity.StoreInfo;
import com.android.p2pflowernet.project.view.customview.MyElideTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caishen on 2017/10/21.
 * by--首页精选商品的适配器
 */

public class IndexSelectionAdapter extends BaseAdapter {
    private final FragmentActivity mContext;
    private final HashMap<String, List<GoodsInfo>> data;
    private final ArrayList<StoreInfo> groups;

    public IndexSelectionAdapter(FragmentActivity mContext, HashMap<String, List<GoodsInfo>> data, ArrayList<StoreInfo> groups) {

        this.mContext = mContext;
        this.data = data;
        this.groups = groups;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {

            convertView = View.inflate(mContext, R.layout.item_index_selection, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();

        }

        for (int i = 0; i < groups.size(); i++) {

            StoreInfo group = groups.get(i);

            List<GoodsInfo> child = data.get(group.getId());

            for (int j = 0; j < child.size(); j++) {

                GoodsInfo good = child.get(j);

                //设置数据
                viewHolder.tvTitle.setText(good.getDesc());
                viewHolder.tvDiscount.setText(good.getPrime_price() + "");
                viewHolder.tvMoney.setText(good.getPrice() + "");
                viewHolder.tvSales.setText(good.getCount() + "");
            }
        }


        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_discount)
        MyElideTextView tvDiscount;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_sales)
        TextView tvSales;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
