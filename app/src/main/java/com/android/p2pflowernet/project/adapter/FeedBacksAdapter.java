package com.android.p2pflowernet.project.adapter;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caishen on 2017/11/24.
 * by--意见反馈的适配器
 */

public class FeedBacksAdapter extends BaseAdapter {

    private final FragmentActivity mContext;
    private String[] data = null;
    private int lastPosition = -1;//定义一个标记为最后选择的位置

    public FeedBacksAdapter(FragmentActivity activity, String[] mStr) {

        this.mContext = activity;
        this.data = mStr;
    }


    public void setStr(String[] str) {
        this.data = str;
    }

    public void setSeclection(int position) {
        lastPosition = position;
    }

    @Override
    public int getCount() {
        return data.length;
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
            convertView = View.inflate(mContext, R.layout.item_feedbacks, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvTitle.setText(data[position] + "");

        if (lastPosition == position) {//最后选择的位置

            viewHolder.tvTitle.setBackgroundResource(R.drawable.icon_feedbacks);
            viewHolder.tvTitle.setTextColor(Color.parseColor("#FF2E00"));

        } else {

            viewHolder.tvTitle.setBackgroundResource(R.drawable.icon_feedback_n);
            viewHolder.tvTitle.setTextColor(Color.parseColor("#8D8D8D"));
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
