package com.android.p2pflowernet.project.adapter;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caishen on 2018/1/4.
 * by--
 */

public class ActivityAdapter extends BaseAdapter {
    private final FragmentActivity mContext;
    private int selectorPosition;

    public ActivityAdapter(FragmentActivity activity) {
        this.mContext = activity;
    }

    @Override
    public int getCount() {
        return 2;
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

            convertView = View.inflate(mContext, R.layout.item_actiivty_sel, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        //如果当前的position等于传过来点击的position,就去改变他的状态
        if (selectorPosition == position) {

            viewHolder.tvName.setTextColor(Color.parseColor("#FF1E00"));

        } else {

            //其他的恢复原来的状态
            viewHolder.tvName.setTextColor(Color.parseColor("#444444"));
        }

        if (position == 0) {

            viewHolder.ivImg.setImageResource(R.drawable.full);
            viewHolder.tvName.setText("满返优惠");

        } else {

            viewHolder.ivImg.setImageResource(R.drawable.return_ticket);
            viewHolder.tvName.setText("返润商品");
        }


        return convertView;
    }

    public void changeState(int pos) {
        selectorPosition = pos;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @BindView(R.id.iv_img)
        ImageView ivImg;
        @BindView(R.id.tv_name)
        TextView tvName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
