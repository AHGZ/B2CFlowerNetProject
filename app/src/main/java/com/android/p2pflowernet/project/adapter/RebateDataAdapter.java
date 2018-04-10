package com.android.p2pflowernet.project.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.p2pflowernet.project.entity.RebateDataBean;

import java.util.List;

/**
 * Created by zhangkun on 2018/1/30.
 */

public class RebateDataAdapter extends BaseAdapter {

    private List<RebateDataBean> dataBeans;
    private Context mContext;

    public RebateDataAdapter(List<RebateDataBean> dataBeans, Context mContext) {
        this.dataBeans = dataBeans;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return dataBeans.size() > 0 ? dataBeans.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return dataBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    class ViewHolder{

    }
}
