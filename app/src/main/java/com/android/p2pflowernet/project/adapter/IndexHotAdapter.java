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
import com.android.p2pflowernet.project.utils.GlideImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caishen on 2017/10/21.
 * by--首页热门推荐的适配器
 */

public class IndexHotAdapter extends BaseAdapter {
    private final FragmentActivity mContext;
    private final List<IndexHomeBean.ListBean.GoodsBean> data;

    public IndexHotAdapter(FragmentActivity mContext, List<IndexHomeBean.ListBean.GoodsBean> listBean) {
        this.mContext = mContext;
        this.data = listBean;
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

            convertView = View.inflate(mContext, R.layout.item_index_hot, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        //设置数据
        String file_path = ApiUrlConstant.API_IMG_URL + data.get(position).getFile_path();
        GlideImageLoader glideImageLoader = new GlideImageLoader();
        glideImageLoader.displayImage(mContext, file_path, viewHolder.ivImgIndexHot);

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_index_hot_title)
        TextView tvIndexHotTitle;
        @BindView(R.id.tv_index_hot)
        TextView tvIndexHot;
        @BindView(R.id.iv_img_index_hot)
        ImageView ivImgIndexHot;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
