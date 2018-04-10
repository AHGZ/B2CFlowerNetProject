package com.android.p2pflowernet.project.adapter;

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
 * Created by caishen on 2017/12/1.
 * by--详细信息的适配器
 */

public class GoodNewsAdapter extends BaseAdapter {
    private final FragmentActivity mContext;

    public GoodNewsAdapter(FragmentActivity activity) {
        this.mContext = activity;
    }

    @Override
    public int getCount() {
        return 4;
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

            convertView = View.inflate(mContext, R.layout.item_good_news, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }
        //设置数据
        if (position == 0) {
            viewHolder.tvTitle.setText("活动1");
            viewHolder.tvNum.setText("¥5000");
            viewHolder.tvDesc.setText("推荐的合伙人及间接推荐的合 伙人季度累积数量达50人。");
        } else if (position == 1) {
            viewHolder.tvTitle.setText("活动1");
            viewHolder.tvNum.setText("¥10000");
            viewHolder.tvDesc.setText("推荐的合伙人及间接推荐的合 伙人季度累积数量达100人。");
        } else if (position == 2) {
            viewHolder.tvTitle.setText("活动1");
            viewHolder.tvNum.setText("¥30000");
            viewHolder.tvDesc.setText("推荐的合伙人及间接推荐的合 伙人季度累积数量达300人。");
        } else if (position == 3) {
            viewHolder.tvTitle.setText("活动1");
            viewHolder.tvNum.setText("¥50000");
            viewHolder.tvDesc.setText("推荐的合伙人及间接推荐的合 伙人季度累积数量达500人。");
        }


        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.iv_img)
        ImageView ivImg;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.tv_num)
        TextView tvNum;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
