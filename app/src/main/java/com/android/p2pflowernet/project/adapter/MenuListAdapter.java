package com.android.p2pflowernet.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import com.android.p2pflowernet.project.R;

import java.util.List;

/**
 * @描述: 筛选菜单的数据适配器
 * @创建人：zhangpeisen
 * @创建时间：2017/4/27 下午4:22
 * @修改人：zhangpeisen
 * @修改时间：2017/4/27 下午4:22
 * @修改备注：
 * @throws
 */
public class MenuListAdapter extends BaseAdapter {
    private Context context;

    private ViewHolder viewHolder;

    private List<String> list;

    public MenuListAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    public List<String> getrefershList() {
        return list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.act_item_popu_list, null);
        }

        viewHolder.text1 = (CheckedTextView) convertView.findViewById(R.id.text);
        viewHolder.text1.setText(list.get(position));

        return convertView;
    }

    private class ViewHolder {
        private CheckedTextView text1;
    }
}
