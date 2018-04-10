package com.android.p2pflowernet.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.utils.GlideImageLoader;

import java.io.File;
import java.util.List;


/**
 * @描述:添加上传图片适配器
 * @创建人：zhangpeisen
 * @创建时间：2017/5/11 下午10:12
 * @修改人：zhangpeisen
 * @修改时间：2017/5/11 下午10:12
 * @修改备注：
 * @throws
 */
public class GridViewAddImgesAdpter extends BaseAdapter {
    private List<File> datas;
    private Context mContext;
    private LayoutInflater inflater;
    /**
     * 可以动态设置最多上传几张，之后就不显示+号了，用户也无法上传了
     * 默认9张
     */
    private int maxImages = Integer.MAX_VALUE;
    private GlideImageLoader glideImageLoader;

    public GridViewAddImgesAdpter(List<File> datas, Context context) {
        this.datas = datas;
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    /**
     * 获取最大上传张数
     *
     * @return
     */
    public int getMaxImages() {
        return maxImages;
    }

    /**
     * 设置最大上传张数
     *
     * @param maxImages
     */
    public void setMaxImages(int maxImages) {
        this.maxImages = maxImages;
    }

    /**
     * 让GridView中的数据数目加1最后一个显示+号
     *
     * @return 返回GridView中的数量
     */
    @Override
    public int getCount() {

        int count = datas == null ? 1 : datas.size() + 1;
        return count;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void notifyDataSetChanged(List<File> datas) {

        this.datas = datas;
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.index_node_uploadgridview_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        glideImageLoader = new GlideImageLoader();
        if (datas != null && datas.size() > 0) {

            if (position == datas.size() && position >= maxImages) {
                viewHolder.ivimage.setVisibility(View.GONE);
            } else if (position < maxImages && position == datas.size()) {
                viewHolder.ivimage.setVisibility(View.VISIBLE);
                glideImageLoader.displayImage(mContext, R.drawable.icon_nine_photo, viewHolder.ivimage);
                viewHolder.ivimage.setScaleType(ImageView.ScaleType.FIT_XY);
            } else {
                glideImageLoader.displayImage(mContext, datas.get(position), viewHolder.ivimage);
            }

        } else {

            viewHolder.ivimage.setVisibility(View.VISIBLE);
            glideImageLoader.displayImage(mContext, R.drawable.icon_nine_photo, viewHolder.ivimage);
            viewHolder.ivimage.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        return convertView;
    }

    public class ViewHolder {
        public final ImageView ivimage;
        public final View root;

        public ViewHolder(View root) {
            ivimage = (ImageView) root.findViewById(R.id.item_grida_image);
            this.root = root;
        }
    }
}
