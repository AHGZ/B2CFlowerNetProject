package com.android.p2pflowernet.project.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.FeedBackBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by caishen on 2017/11/24.
 * by--意见反馈历史的适配器
 */

public class FeedBackHistoryAdapter extends BaseAdapter {
    private final FragmentActivity mContext;
    private final List<FeedBackBean.FlBean> data;
    private OnFeedBackReadListener onFeedBackReadListener;


    public FeedBackHistoryAdapter(FragmentActivity activity, List<FeedBackBean.FlBean> data) {
        this.mContext = activity;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    /**
     * 设置去阅读的点击事件
     *
     * @param onFeedBackReadListener
     */
    public void setOnFeedBackReadListener(OnFeedBackReadListener onFeedBackReadListener) {
        this.onFeedBackReadListener = onFeedBackReadListener;
    }

    public interface OnFeedBackReadListener {

        void OnFeedBackreadListener(View view, int position);
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

            convertView = View.inflate(mContext, R.layout.item_feedback_history, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        //设置数据
        viewHolder.tvTitle.setText(data.get(position).getContent());
        viewHolder.tvDesc.setText(data.get(position).getReply_content());
        viewHolder.tvTime.setText(data.get(position).getReply_time());

        String is_read = data.get(position).getIs_read();//0:否，1：是
        String type = data.get(position).getType();


        if (type.equals("1")) {
            viewHolder.tvTitle.setText("功能建议");
        } else if (type.equals("2")) {
            viewHolder.tvTitle.setText("购买遇到问题");
        } else if (type.equals("3")) {
            viewHolder.tvTitle.setText("性能问题");
        } else if (type.equals("99")) {
            viewHolder.tvTitle.setText("其他");
        }

        viewHolder.tvDesc.setText(data.get(position).getContent());

        if (is_read.equals("1")) {
            viewHolder.ivRead.setBackgroundResource(R.drawable.icon_read);
        } else {
            viewHolder.ivRead.setBackgroundResource(R.drawable.icon_unread);
        }

        //设置阅读的点击事件
        viewHolder.ivTonext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onFeedBackReadListener.OnFeedBackreadListener(v, position);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_read)
        ImageView ivRead;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.iv_tonext)
        ImageView ivTonext;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
