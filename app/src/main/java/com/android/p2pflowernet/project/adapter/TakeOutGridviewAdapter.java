package com.android.p2pflowernet.project.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.O2oHomeBean;
import com.android.p2pflowernet.project.utils.GlideImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by heguozhong on 2017/12/28/028.
 * 外卖Gridview适配器
 */

public class TakeOutGridviewAdapter extends BaseAdapter {

    private final FragmentActivity mContext;
    private final List<O2oHomeBean.CategoryBean> data;

    public TakeOutGridviewAdapter(FragmentActivity activity, List<O2oHomeBean.CategoryBean> category) {
        this.mContext = activity;
        this.data = category;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_menue, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvNick.setText(data.get(position).getName());
        String file_path = ApiUrlConstant.API_IMG_URL + data.get(position).getFile_path();
        new GlideImageLoader().displayImage(mContext, file_path, viewHolder.ivMenue);
        return convertView;
    }

    static class ViewHolder {
        //功能图标
        @BindView(R.id.iv_menue)
        ImageView ivMenue;
        //功能文字描述
        @BindView(R.id.tv_nick)
        TextView tvNick;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
