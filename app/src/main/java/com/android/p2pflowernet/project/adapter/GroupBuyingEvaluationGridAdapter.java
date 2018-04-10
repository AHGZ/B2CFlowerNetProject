package com.android.p2pflowernet.project.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.O2oGoodsInfoBean;
import com.android.p2pflowernet.project.utils.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by heguozhong on 2018/1/5/005.
 * 商品详情页面展示评价的商品图片的gridview适配器
 */

public class GroupBuyingEvaluationGridAdapter extends BaseAdapter {
    private final ArrayList<String> image;
    private final FragmentActivity mContext;
    private final List<O2oGoodsInfoBean.ListsBean> data;

    public GroupBuyingEvaluationGridAdapter(FragmentActivity mContext, List<O2oGoodsInfoBean.ListsBean> lists) {
        image = new ArrayList<>();
        this.mContext = mContext;
        this.data = lists;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.group_buying_shopdetails_grid_item_data, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //给gridview赋值
        GlideImageLoader glideImageLoader = new GlideImageLoader();
        glideImageLoader.displayImage(parent.getContext(), ApiUrlConstant.API_IMG_URL
                + data.get(position).getFile_path(), viewHolder.groupBuyingShopdetailsGridviewIv);
        return convertView;
    }

    static class ViewHolder {
        //用来展示评价的商品图片的gridview布局
        @BindView(R.id.group_buying_shopdetails_gridview_iv)
        ImageView groupBuyingShopdetailsGridviewIv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
