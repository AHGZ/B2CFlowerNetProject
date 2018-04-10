package com.android.p2pflowernet.project.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.TakeCateTwoBean;
import com.android.p2pflowernet.project.view.customview.MyGridView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by caishen on 2018/1/4.
 * by--
 */

public class SelCateListAdapter extends BaseExpandableListAdapter {
    private final FragmentActivity mContext;
    private final List<TakeCateTwoBean.ListBean> data;
    private OnCheckedClickLitener onCheckedClickLitener;

    public SelCateListAdapter(FragmentActivity activity, List<TakeCateTwoBean.ListBean> list) {
        this.mContext = activity;
        this.data = list;

    }

    public void setOnCheckedClickLitener(OnCheckedClickLitener onCheckedClickLitener) {
        this.onCheckedClickLitener = onCheckedClickLitener;
    }

    public interface OnCheckedClickLitener {

        void onCheckedCLicklitener(View view,int groupPosition, int position, List<TakeCateTwoBean.ListBean> data);
    }

    @Override
    public int getGroupCount() {

        return data == null ? 0 : data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
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
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        GroupViewHolder groupViewHolder;
        if (convertView == null) {

            convertView = View.inflate(mContext, R.layout.item_sel_sort, null);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);

        } else {

            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }

        //设置数据
        groupViewHolder.tvName.setText(data.get(groupPosition).getName() == null ? "" : data.get(groupPosition).getName());

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {

            convertView = View.inflate(mContext, R.layout.item_sel_sort_child, null);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);

        } else {

            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

            //设置数据
            TakeCateTwoBean.ListBean listBean = data.get(groupPosition);
            List<TakeCateTwoBean.ListBean.SonBean> son = listBean.getSon();
            SelCateMultipleAdapter mAdapter = new SelCateMultipleAdapter(mContext, son);
            childViewHolder.gridview.setAdapter(mAdapter);

            mAdapter.setOnTextClickLitener(new SelCateMultipleAdapter.OnTextClickLitener() {
                @Override
                public void textClickLitener(View view, int position) {

                    boolean flag = (boolean) view.getTag();
                    if (flag) {//已经选中
                    } else {
                    }
                    notifyDataSetChanged();

                    if (onCheckedClickLitener != null) {
                        onCheckedClickLitener.onCheckedCLicklitener(view,groupPosition, position, data);
                    }
                }
            });


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    static class GroupViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ChildViewHolder {
        @BindView(R.id.gridview)
        MyGridView gridview;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
