package com.android.p2pflowernet.project.adapter;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.CouponCodeBean;
import com.android.p2pflowernet.project.utils.QRCodeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caishen on 2018/1/6.
 * by--
 */

public class DetailGroupAdapter extends BaseAdapter {
    private final FragmentActivity mContext;
    private final List<CouponCodeBean.ListBean> data;

    public DetailGroupAdapter(FragmentActivity activity, List<CouponCodeBean.ListBean> bitmaps) {
        this.mContext = activity;
        this.data = bitmaps;
    }

    @Override
    public int getCount() {

        return data.size() == 0 ? 0 : data.size();
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

        ViewHolder viewHolder;
        if (convertView == null) {

            convertView = View.inflate(mContext, R.layout.item_detail_group, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }
        CouponCodeBean.ListBean listBean = data.get(position);
        Bitmap bitmap = QRCodeUtil.creatBarcode(mContext, String.valueOf(listBean.getGroup_code()), 600, 240, false);
        viewHolder.ivImg.setImageBitmap(bitmap);

        viewHolder.tvName.setText(listBean.getCode_name());
        viewHolder.orderNum.setText(listBean.getGroup_code());

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.iv_img)
        ImageView ivImg;
        @BindView(R.id.tv_oredernum)
        TextView orderNum;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
