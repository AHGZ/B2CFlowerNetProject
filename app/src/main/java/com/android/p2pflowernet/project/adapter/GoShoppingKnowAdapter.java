package com.android.p2pflowernet.project.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.GroupBuyingBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by heguozhong on 2018/1/18/018.
 * 团购详情购买须知适配器
 */

public class GoShoppingKnowAdapter extends BaseAdapter {

    private List<GroupBuyingBean.InfoBean.NoticeBean> notice;

    public GoShoppingKnowAdapter(List<GroupBuyingBean.InfoBean.NoticeBean> notice) {
        this.notice = notice;
    }

    @Override
    public int getCount() {
        return notice == null ? 0 : notice.size();
    }

    @Override
    public Object getItem(int position) {
        return notice.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = convertView.inflate(parent.getContext(), R.layout.group_goshopping_know_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.groupShoppingknowTitle.setText(notice.get(position).getName());
        viewHolder.groupShoppingknowContent.setText(notice.get(position).getValue());
        return convertView;
    }

    static class ViewHolder {
        //购买须知标题
        @BindView(R.id.group_shoppingknow_title)
        TextView groupShoppingknowTitle;
        //购买须知标题下内容
        @BindView(R.id.group_shoppingknow_content)
        TextView groupShoppingknowContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
