package com.android.p2pflowernet.project.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caishen on 2017/12/28.
 * by--
 */

public class IndexO2oMenueAdapter extends BaseAdapter {
    private final FragmentActivity mContext;

    public IndexO2oMenueAdapter(FragmentActivity mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 8;
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
        if (position == 0) {
            viewHolder.tvNick.setText("外卖");
            Glide.with(mContext)
                    .load(R.drawable.o2o_icon_wm)
                    .into(viewHolder.ivMenue);

        } else if (position == 1) {
            viewHolder.tvNick.setText("团购优惠");
            Glide.with(mContext)
                    .load(R.drawable.o_icon_tgyh)
                    .into(viewHolder.ivMenue);

        } else if (position == 2) {
            viewHolder.tvNick.setText("生鲜果蔬");
            Glide.with(mContext)
                    .load(R.drawable.o2o_icon_sxgs)
                    .into(viewHolder.ivMenue);

        } else if (position == 3) {
            viewHolder.tvNick.setText("酒店住宿");
            Glide.with(mContext)
                    .load(R.drawable.o2o_icon_jdzs)
                    .into(viewHolder.ivMenue);

        } else if (position == 4) {
            viewHolder.tvNick.setText("跑腿代购");
            Glide.with(mContext)
                    .load(R.drawable.o2o_icon_ptdg)
                    .into(viewHolder.ivMenue);

        } else if (position == 5) {
            viewHolder.tvNick.setText("KTV");
            Glide.with(mContext)
                    .load(R.drawable.o2o_icon_ktv)
                    .into(viewHolder.ivMenue);

        } else if (position == 6) {
            viewHolder.tvNick.setText("甜点饮品");
            Glide.with(mContext)
                    .load(R.drawable.o2o_icon_tdyp)
                    .into(viewHolder.ivMenue);

        } else {
            viewHolder.tvNick.setText("快食简餐");
            Glide.with(mContext)
                    .load(R.drawable.o2o_icon_ksjc)
                    .into(viewHolder.ivMenue);
        }

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
