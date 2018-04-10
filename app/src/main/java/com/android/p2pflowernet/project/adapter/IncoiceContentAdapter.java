package com.android.p2pflowernet.project.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caishen on 2017/11/8.
 * by--发票内容的适配器
 */

public class IncoiceContentAdapter extends BaseAdapter {
    private final FragmentActivity mContext;

    public IncoiceContentAdapter(FragmentActivity activity) {

        this.mContext = activity;
    }

    @Override
    public int getCount() {

        return 1;
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

            convertView = View.inflate(mContext, R.layout.item_incoice_content, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        //设置数据

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.cb_type)
        CheckBox cbType;
        @BindView(R.id.tv_type)
        TextView tvType;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
