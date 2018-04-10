package com.android.p2pflowernet.project.adapter;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.MessaTypeBean;
import com.android.p2pflowernet.project.view.fragments.mine.message.msgdetail.MessageItemDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caishen on 2017/12/1.
 * by--消息详情的适配器
 */

public class MessageDeAdapter extends BaseAdapter {
    private final FragmentActivity mContext;
    private final List<MessaTypeBean.ListsBean> data;
    private final String type;

    public MessageDeAdapter(FragmentActivity activity, List<MessaTypeBean.ListsBean> data, String type) {
        this.mContext = activity;
        this.data = data;
        this.type = type;
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

            convertView = View.inflate(mContext, R.layout.item_message_de, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        //设置数据
        viewHolder.tvTitle.setText(data.get(position).getTitle());
        viewHolder.tvDesc.setText(data.get(position).getContent());
        viewHolder.tvDate.setText(data.get(position).getCreated());

        //查看详情
        viewHolder.tvLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, MessageItemDetailActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("notice_id", data.get(position).getId());
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.tv_look)
        TextView tvLook;
        @BindView(R.id.tv_date)
        TextView tvDate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
