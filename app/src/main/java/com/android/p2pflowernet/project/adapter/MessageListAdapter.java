package com.android.p2pflowernet.project.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.MessagesBean;
import com.android.p2pflowernet.project.view.customview.DragIndicatorView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caishen on 2017/11/4.
 * by--消息类型的数据适配器
 */

public class MessageListAdapter extends BaseAdapter {
    private final FragmentActivity mContext;
    private final List<MessagesBean.ListsBean> data;

    public MessageListAdapter(FragmentActivity activity, List<MessagesBean.ListsBean> lists) {
        this.mContext = activity;
        this.data = lists;
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

            convertView = View.inflate(mContext, R.layout.item_message_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        //设置数据
        String type = data.get(position).getType();
        //1-反润 2-物流 3-申请 4-互动 5-系统通知
        if (type.equals("1")) {
            viewHolder.ivType.setImageResource(R.mipmap.icon_frtz);
            viewHolder.tvTitle.setText("返润通知");
        } else if (type.equals("2")) {
            viewHolder.ivType.setImageResource(R.mipmap.icon_wlzt);
            viewHolder.tvTitle.setText("物流通知");
        } else if (type.equals("3")) {
            viewHolder.ivType.setImageResource(R.mipmap.icon_sqtz);
            viewHolder.tvTitle.setText("申请通知");
        } else if (type.equals("4")) {
            viewHolder.ivType.setImageResource(R.mipmap.icon_hd);
            viewHolder.tvTitle.setText("互动通知");
        } else {
            viewHolder.ivType.setImageResource(R.drawable.icon_xttz);
            viewHolder.tvTitle.setText("系统通知");
        }

        viewHolder.tvNotic.setText(data.get(position).getNum().equals("") ? "0" : data.get(position).getNum());
        viewHolder.tvDesc.setText(data.get(position).getInfo() == null ? "" : data.get(position).getInfo());

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_type)
        ImageView ivType;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_notic)
        DragIndicatorView tvNotic;
        @BindView(R.id.fl_notic)
        FrameLayout flNotic;
        @BindView(R.id.tv_desc)
        TextView tvDesc;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
