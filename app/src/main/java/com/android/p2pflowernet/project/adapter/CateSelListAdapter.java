package com.android.p2pflowernet.project.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.DistriButionBean;
import com.android.p2pflowernet.project.view.customview.MyGridView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by caishen on 2018/1/4.
 * by--
 */

public class CateSelListAdapter extends BaseExpandableListAdapter {
    private final FragmentActivity mContext;
    private final List<DistriButionBean.DistriButionsBean> data;
    private OnCheckedClickLitener onCheckedClickLitener;

    public CateSelListAdapter(FragmentActivity activity, List<DistriButionBean.DistriButionsBean> distribution) {
        this.mContext = activity;
        this.data = distribution;
    }

    public void setOnCheckedClickLitener(OnCheckedClickLitener onCheckedClickLitener) {
        this.onCheckedClickLitener = onCheckedClickLitener;
    }

    public interface OnCheckedClickLitener {

        void onCheckedCLicklitener(View view, int position,List<DistriButionBean.DistriButionsBean> data);
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
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = View.inflate(mContext, R.layout.item_sel_sort_child, null);
            MyGridView gridView = (MyGridView) convertView.findViewById(R.id.gridview);

            //设置数据
            DistriButionBean.DistriButionsBean distriButionsBean = data.get(groupPosition);
            final List<DistriButionBean.DistriButionsBean.DistriBean> distri = distriButionsBean.getDistri();
            SelMultipleAdapter mAdapter = new SelMultipleAdapter(mContext, distri);
            gridView.setAdapter(mAdapter);

            mAdapter.setOnTextClickLitener(new SelMultipleAdapter.OnTextClickLitener() {
                @Override
                public void textClickLitener(View view, int position) {

                    boolean flag = (boolean) view.getTag();
                    if (flag) {//已经选中
                    } else {
                    }
                    notifyDataSetChanged();

                    if (onCheckedClickLitener != null) {
                        onCheckedClickLitener.onCheckedCLicklitener(view, position,data);
                    }
                }
            });
        }

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
