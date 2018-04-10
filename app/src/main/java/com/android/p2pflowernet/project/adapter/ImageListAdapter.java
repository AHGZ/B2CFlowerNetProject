package com.android.p2pflowernet.project.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.view.customview.ICoverFlowAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by caishen on 2017/10/31.
 * by--
 */

public class ImageListAdapter implements ICoverFlowAdapter {

    private Context mContext;
    List<String> mImageBeanList;

    public ImageListAdapter(Context mContext, List<String> imageBeanList) {
        this.mImageBeanList = imageBeanList;
        this.mContext = mContext;
    }

    public ViewHolder viewHolder;


    @Override
    public int getCount() {
        return mImageBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return mImageBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.informatiom_coverflow_item, null);
            viewHolder.galleryImageview = (ImageView) convertView.findViewById(R.id.coverflow_imageview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    @Override
    public void getData(View view, int i) {
        ViewHolder holder = (ViewHolder) view.getTag();

        if (mImageBeanList == null) {
            return;
        }


        Glide.with(mContext)
                .load(mImageBeanList.get(i))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.test)
                .into(holder.galleryImageview);

//        GlideImageLoader glideImageLoader = new GlideImageLoader();
//        glideImageLoader.displayImage(mContext, mImageBeanList, holder.galleryImageview);
    }

    public class ViewHolder {
        ImageView galleryImageview;
    }

}