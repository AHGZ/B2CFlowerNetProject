package com.android.p2pflowernet.project.adapter;


import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.IndexHomeBean;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caishen on 2017/4/25.
 * 菜单的适配器
 */
public class O2oMenueAdapter extends BaseAdapter {
    private final FragmentActivity mContext;
    private final List<IndexHomeBean.CategoryBean> data;

    public O2oMenueAdapter(FragmentActivity mContext, List<IndexHomeBean.CategoryBean> category) {
        this.mContext = mContext;
        this.data = category;
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

            convertView = View.inflate(mContext, R.layout.item_menue, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        //设置数据
        viewHolder.tvNick.setText(data.get(position).getName());
        String file_path = ApiUrlConstant.API_IMG_URL + data.get(position).getFile_path();
        Glide.with(mContext)
                .load(file_path)
                .into(viewHolder.ivMenue);

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_menue)
        ImageView ivMenue;
        @BindView(R.id.tv_nick)
        TextView tvNick;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
