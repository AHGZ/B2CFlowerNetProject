package com.android.p2pflowernet.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.baidu.mapapi.search.core.PoiInfo;

import java.util.List;

/**
 * Created by zhangkun on 2018/2/2.
 */

public class AllAddressAdapter extends BaseAdapter{

    private List<PoiInfo> poiInfos;
    private Context mContext;

    public AllAddressAdapter(List<PoiInfo> poiInfos, Context mContext) {
        this.poiInfos = poiInfos;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return poiInfos.size() > 0 ? poiInfos.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return poiInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView ) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_alladdress,parent,false);
            holder = new ViewHolder();
            holder.img_icon = (ImageView) convertView.findViewById(R.id.item_allAddress_img);
            holder.tv_name = (TextView) convertView.findViewById(R.id.item_allAddress_tv_name);
            holder.tv_address = (TextView) convertView.findViewById(R.id.item_allAddress_tv_address);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == 0){
            holder.img_icon.setImageResource(R.mipmap.icon_orientation);
        }else{
            holder.img_icon.setImageResource(R.mipmap.icon_quan);
        }
        holder.tv_name.setText(poiInfos.get(position).name);
        holder.tv_address.setText(poiInfos.get(position).address);
        return convertView;
    }

    class ViewHolder{
        ImageView img_icon;
        TextView tv_name;
        TextView tv_address;
    }
}
