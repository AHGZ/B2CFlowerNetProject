package com.android.p2pflowernet.project.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.GroupBuyingBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by heguozhong on 2018/1/4/004.
 * 团购团单内容适配器
 */

class GroupBuyingShopListAdapter extends BaseAdapter {

    private List<GroupBuyingBean.InfoBean.GoodsListBean> goodsList;
    public GroupBuyingShopListAdapter(List<GroupBuyingBean.InfoBean.GoodsListBean> goodsList) {
        this.goodsList=goodsList;
    }

    @Override
    public int getCount() {
        return goodsList==null?0:goodsList.size();
    }

    @Override
    public Object getItem(int position) {
        return goodsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = convertView.inflate(parent.getContext(), R.layout.group_buying_shopdetails_item_data, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.groupBuyingShopdetailsTuandanName.setText(goodsList.get(position).getGoods_name());
        viewHolder.groupBuyingShopdetailsTuandanNum.setText(goodsList.get(position).getNum()+"份");
        viewHolder.groupBuyingShopdetailsTuandanPrice.setText("￥"+goodsList.get(position).getPrice());
        return convertView;
    }


    static class ViewHolder {
        //团单名称
        @BindView(R.id.group_buying_shopdetails_tuandan_name)
        TextView groupBuyingShopdetailsTuandanName;
        //团单份额
        @BindView(R.id.group_buying_shopdetails_tuandan_num)
        TextView groupBuyingShopdetailsTuandanNum;
        //团单价格
        @BindView(R.id.group_buying_shopdetails_tuandan_price)
        TextView groupBuyingShopdetailsTuandanPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
