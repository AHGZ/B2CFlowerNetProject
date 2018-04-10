package com.android.p2pflowernet.project.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.MessaTypeBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caishen on 2017/12/1.
 * by--物流通知适配器
 */

public class WlDeAdapter extends BaseAdapter {
    private final FragmentActivity mContext;
    private final List<MessaTypeBean.ListsBean> data;

    public WlDeAdapter(FragmentActivity activity, List<MessaTypeBean.ListsBean> data) {
        this.mContext = activity;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size() == 0 ? 0 : data.size();
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

            convertView = View.inflate(mContext, R.layout.item_wl, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }





        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_nick)
        TextView tvNick;
        @BindView(R.id.iv_img)
        ImageView ivImg;
        @BindView(R.id.tv_desc)
        TextView tvDesc;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
