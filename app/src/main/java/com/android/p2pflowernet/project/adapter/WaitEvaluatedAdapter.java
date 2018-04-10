package com.android.p2pflowernet.project.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.WaitEvaluatedBean;
import com.android.p2pflowernet.project.utils.GlideImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caishen on 2017/12/14.
 * by--待评价
 */

public class WaitEvaluatedAdapter extends BaseAdapter {
    private final FragmentActivity mContext;
    private final List<WaitEvaluatedBean.ListsBean> data;
    private OnEvualuatedClickLintener onEvualuatedClickLintener;

    public WaitEvaluatedAdapter(FragmentActivity activity, List<WaitEvaluatedBean.ListsBean> datas) {
        this.mContext = activity;
        this.data = datas;
    }

    public void setOnEvualuatedClickLintener(OnEvualuatedClickLintener onEvualuatedClickLintener) {
        this.onEvualuatedClickLintener = onEvualuatedClickLintener;
    }

    public interface OnEvualuatedClickLintener {

        void OnEvualuatedClickLintener(View view, int position);
    }

    @Override
    public int getCount() {
        return data.size() == 0 ? 0 : data.size();
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

            convertView = View.inflate(mContext, R.layout.item_wait_evaluate, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        //设置数据
        viewHolder.tvTitle.setText(data.get(position).getGoods_name() == null ? "" : data.get(position).getGoods_name());
        viewHolder.tvDescProperty.setText(data.get(position).getGoods_spec() == null ? "" : data.get(position).getGoods_spec());

        GlideImageLoader glideImageLoader = new GlideImageLoader();
        String imgPath = ApiUrlConstant.API_IMG_URL + data.get(position).getImg_path()
                == null ? "" : ApiUrlConstant.API_IMG_URL + data.get(position).getImg_path();
        glideImageLoader.displayImage(mContext, imgPath, viewHolder.ivGov);

        //设置去评价的点击事件
        viewHolder.tvRightState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onEvualuatedClickLintener != null) {
                    onEvualuatedClickLintener.OnEvualuatedClickLintener(v, position);
                }
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_gov)
        ImageView ivGov;
        @BindView(R.id.iv_stockout)
        ImageView ivStockout;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_desc_property)
        TextView tvDescProperty;
        @BindView(R.id.tv_right_state)
        TextView tvRightState;
        @BindView(R.id.ll_state)
        LinearLayout llState;
        @BindView(R.id.ll_item)
        LinearLayout llItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
