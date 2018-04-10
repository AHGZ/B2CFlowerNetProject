package com.android.p2pflowernet.project.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caishen on 2017/11/2.
 * by--
 */

public class AddBankListAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<String> data;

    public AddBankListAdapter(Context mContext, List<String> data) {
        this.mContext = mContext;
        this.data = data;

    }

    @Override
    public int getCount() {
        return data.size() + 1;
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

            convertView = View.inflate(mContext, R.layout.item_addbanklist, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }


        //设置数据
        if (data.size() == 0 || position == data.size()) {

            viewHolder.ivAdd.setImageResource(R.mipmap.icon_tj);
            viewHolder.tvName.setText("添加银行卡");

        } else {

            viewHolder.ivAdd.setImageResource(R.mipmap.icon_wx);
            viewHolder.tvName.setText(data.get(position));
        }


        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_add)
        ImageView ivAdd;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.rl_add_bank)
        RelativeLayout rlAddBank;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
