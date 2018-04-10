package com.android.p2pflowernet.project.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.MerchantBean;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by heguozhong on 2018/1/13/013.
 * 满返规则适配器
 */

public class ShopManFanGridview extends BaseAdapter {
    private Context context;
    private List<MerchantBean.InfoBean.ActivityBean> activity;

    public ShopManFanGridview(Context context, List<MerchantBean.InfoBean.ActivityBean> activity) {
        this.context = context;
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return activity==null?0:activity.size();
    }

    @Override
    public Object getItem(int position) {
        return activity.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = convertView.inflate(context, R.layout.shop_manfan_gridview_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position%2==0){
            viewHolder.shopManfanItemText1.setText("满");
            viewHolder.shopManfanItemText1.setBackgroundColor(Color.parseColor("#FE4D4D"));
            DecimalFormat df1 = new DecimalFormat("0");
            String Fullprice = df1.format(activity.get(position).getOrder_amount());
            String Returnprice = df1.format(activity.get(position).getRebate());
            viewHolder.shopManfanItemText2.setText("满"+Fullprice+"返"+Returnprice+"元");
        }else{
            DecimalFormat df1 = new DecimalFormat("0");
            String Fullprice = df1.format(activity.get(position).getOrder_amount());
            String Returnprice = df1.format(activity.get(position).getRebate());
            viewHolder.shopManfanItemText2.setText("满"+Fullprice+"返"+Returnprice+"元");
        }
        return convertView;
    }

    static class ViewHolder {
        //满返文字提示
        @BindView(R.id.shop_manfan_item_text1)
        TextView shopManfanItemText1;
        //满返规则
        @BindView(R.id.shop_manfan_item_text2)
        TextView shopManfanItemText2;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
