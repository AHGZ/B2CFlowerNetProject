package com.android.p2pflowernet.project.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.O2oAddressBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caishen on 2018/1/3.
 * by--生活地址管理
 */

public class LocationListAdapter extends BaseAdapter {
    private final FragmentActivity mContext;
    private List<O2oAddressBean.ListsBean> listsBean;
    private OnDeleteAdressListener onDeleteAdressListener;
    private OnEditAdressListener onEditAdressListener;

    public LocationListAdapter(FragmentActivity activity, List<O2oAddressBean.ListsBean> data) {
        this.mContext = activity;
        this.listsBean = data;
    }


    /**
     * 设置编辑的点击事件
     *
     * @param onEditAdressListener
     */
    public void setOnEditAdressListener(OnEditAdressListener onEditAdressListener) {
        this.onEditAdressListener = onEditAdressListener;
    }

    public interface OnEditAdressListener {

        void onEditAdressListener(View view, int position);
    }

    public interface OnDeleteAdressListener {

        void onDeleteAdressListener(View view, int position);
    }

    /**
     * 设置删除的点击事件
     *
     * @param onDeleteAdressListener
     */
    public void setOnDeleteAdressListener(OnDeleteAdressListener onDeleteAdressListener) {
        this.onDeleteAdressListener = onDeleteAdressListener;
    }

    @Override
    public int getCount() {
        return listsBean.size();
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

            convertView = View.inflate(mContext, R.layout.item_location, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        O2oAddressBean.ListsBean o2oAddressBean = listsBean.get(position);

        //设置数据
        viewHolder.tvName.setText(o2oAddressBean.getName());
        viewHolder.tvPhone.setText(o2oAddressBean.getTelephone());
        viewHolder.tvDetailPlace.setText(o2oAddressBean.getAddress());

        //设置删除的点击事件
        viewHolder.tvDelet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onDeleteAdressListener.onDeleteAdressListener(v, position);
            }
        });

        viewHolder.img_delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteAdressListener.onDeleteAdressListener(v, position);
            }
        });


        //设置编辑的点击事件
        viewHolder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onEditAdressListener.onEditAdressListener(v, position);
            }
        });

        viewHolder.img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditAdressListener.onEditAdressListener(v, position);
            }
        });


        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.tv_detail_place)
        TextView tvDetailPlace;
        @BindView(R.id.tv_edit)
        TextView tvEdit;
        @BindView(R.id.tv_delet)
        TextView tvDelet;
        @BindView(R.id.img_edit)
        ImageView img_edit;
        @BindView(R.id.img_delet)
        ImageView img_delet;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
