package com.android.p2pflowernet.project.view.fragments.goods.info;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.GuaranteeBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caishen on 2017/11/9.
 * by--正品保障的适配器
 */

public class SafeGuardAdapter extends BaseAdapter {
    private final FragmentActivity mContext;
    private final List<GuaranteeBean.ListsBean> data;

    public SafeGuardAdapter(FragmentActivity activity, List<GuaranteeBean.ListsBean> lists) {
        this.mContext = activity;
        this.data = lists;
    }

    @Override
    public int getCount() {

        return data == null ? 0 : data.size();
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

            convertView = View.inflate(mContext, R.layout.item_safeguard, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        //设置数据
        viewHolder.tvTitle.setText(data.get(position).getName());
        viewHolder.tvDesc.setText(data.get(position).getInfo());


        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_log)
        ImageView ivLog;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_desc)
        TextView tvDesc;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
