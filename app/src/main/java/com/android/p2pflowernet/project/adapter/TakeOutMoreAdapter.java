package com.android.p2pflowernet.project.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.TakeOutBean;
import com.android.p2pflowernet.project.utils.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caishen on 2018/1/5.
 * by--外卖订单多以一件商品的适配器
 */

public class TakeOutMoreAdapter extends BaseExpandableListAdapter {

    private final FragmentActivity mContext;
    private final List<TakeOutBean.ListBean.GoodsBean> data;
    private onShowMoreListClickLitener onShowMoreListClickLitener;
    private onHideMoreListClickLitener onHideMoreListClickLitener;
    private boolean isShow = true;

    public TakeOutMoreAdapter(FragmentActivity mContext, List<TakeOutBean.ListBean.GoodsBean> list) {
        this.mContext = mContext;
        this.data = list;
    }

    public void setOnHideMoreListClickLitener(TakeOutMoreAdapter.onHideMoreListClickLitener onHideMoreListClickLitener) {
        this.onHideMoreListClickLitener = onHideMoreListClickLitener;
    }

    public void setOnShowMoreListClickLitener(TakeOutMoreAdapter.onShowMoreListClickLitener onShowMoreListClickLitener) {
        this.onShowMoreListClickLitener = onShowMoreListClickLitener;
    }

    public interface onShowMoreListClickLitener {

        void showMoreListLitener(View view, int position, boolean isShow);
    }

    public interface onHideMoreListClickLitener {

        void hideMoreListLitener(View view, int position, boolean isShow);
    }

    @Override
    public int getGroupCount() {

        if (data.size() > 3) {

            return 3;

        } else {

            return data.size();
        }
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return data.size() - 3;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        GroupViewHolder groupViewHolder;
        if (convertView == null) {

            convertView = View.inflate(mContext, R.layout.item_take_out_all_more, null);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);

        } else {

            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }

        //设置数据
        groupViewHolder.tvName.setText(data.get(groupPosition).getGoods_name());
        groupViewHolder.tvNum.setText("x" + data.get(groupPosition).getGoods_num());
        groupViewHolder.tvPrice.setText("¥" + data.get(groupPosition).getGoods_price());

        if (groupPosition == 2) {

            groupViewHolder.llMore.setVisibility(View.VISIBLE);

            if (isShow == true) {
                groupViewHolder.llMore.setVisibility(View.VISIBLE);
            } else {
                groupViewHolder.llMore.setVisibility(View.GONE);
            }

        } else {
            groupViewHolder.llMore.setVisibility(View.GONE);
        }

        UIUtils.setTouchDelegate(groupViewHolder.llMore, 50);

        groupViewHolder.llMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                groupViewHolder.llMore.setVisibility(View.GONE);

                if (onShowMoreListClickLitener != null) {
                    onShowMoreListClickLitener.showMoreListLitener(v, groupPosition, isShow);
                }

                isShow = false;
            }
        });


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ChildViewHolder childViewHolder;
        if (convertView == null) {

            convertView = View.inflate(mContext, R.layout.item_take_out_child_more, null);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);

        } else {

            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        //设置数据
        childViewHolder.tvName.setText(data.get(childPosition + 3).getGoods_name());
        childViewHolder.tvNum.setText("x" + data.get(childPosition + 3).getGoods_num());
        childViewHolder.tvPrice.setText("¥" + data.get(childPosition + 3).getGoods_price());

        UIUtils.setTouchDelegate(childViewHolder.llMore, 50);

        if (childPosition == data.size() - 4) {
            childViewHolder.llMore.setVisibility(View.VISIBLE);
        } else {
            childViewHolder.llMore.setVisibility(View.GONE);
        }

        childViewHolder.llMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                childViewHolder.llMore.setVisibility(View.GONE);

                if (onHideMoreListClickLitener != null) {

                    onHideMoreListClickLitener.hideMoreListLitener(v, groupPosition, isShow);
                }

                isShow = true;
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class GroupViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.iv_show)
        ImageView ivShow;
        @BindView(R.id.ll_more)
        LinearLayout llMore;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ChildViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.iv_show)
        ImageView ivShow;
        @BindView(R.id.ll_more)
        LinearLayout llMore;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
