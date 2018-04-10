package com.android.p2pflowernet.project.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by heguozhong on 2018/1/12/012.
 * 添加备注gridview适配器
 */

public class AddRemarksAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> shopName;

    public AddRemarksAdapter(Context context) {
        this.context = context;
        shopName = new ArrayList<>();
        shopName.add("中辣");
        shopName.add("不吃辣");
        shopName.add("不吃辣");
        shopName.add("不吃辣");
        shopName.add("中辣");
    }

    @Override
    public int getCount() {
        return shopName.size();
    }

    @Override
    public Object getItem(int position) {
        return shopName.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = convertView.inflate(context, R.layout.add_remarks_gridview_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.addRemarksGridviewItemText.setText(shopName.get(position));
        return convertView;
    }

    static class ViewHolder {
        //口味
        @BindView(R.id.add_remarks_gridview_item_text)
        TextView addRemarksGridviewItemText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
