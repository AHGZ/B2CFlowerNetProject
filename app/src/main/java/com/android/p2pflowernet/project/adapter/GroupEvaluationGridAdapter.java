package com.android.p2pflowernet.project.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.utils.GlideImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by heguozhong on 2018/1/5/005.
 * 商品详情页面展示评价的商品图片的gridview适配器
 */

public class GroupEvaluationGridAdapter extends BaseAdapter {

    private List<String> img_list;
    public GroupEvaluationGridAdapter(List<String> img_list) {
        this.img_list=img_list;
    }

    @Override
    public int getCount() {
        return img_list == null ? 0 : img_list.size();
    }

    @Override
    public Object getItem(int position) {
        return img_list.get(position);
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
                + img_list.get(position), viewHolder.groupBuyingShopdetailsGridviewIv);
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
