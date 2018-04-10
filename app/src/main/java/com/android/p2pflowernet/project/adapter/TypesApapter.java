package com.android.p2pflowernet.project.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.BrandClassBean;
import com.android.p2pflowernet.project.utils.RxImageLoader;
import com.caimuhao.rxpicker.utils.DensityUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caishen on 2017/12/2.
 * by--详细分类的适配器
 */

public class TypesApapter extends BaseAdapter {

    private Context mContext;
    private List<BrandClassBean.RecommendBean> Recommend;

    public TypesApapter(Context mContext, List<BrandClassBean.RecommendBean> recommend) {
        this.mContext = mContext;
        this.Recommend = recommend;
    }

    @Override
    public int getCount() {
        return Recommend == null ? 0 : Recommend.size();
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

            convertView = View.inflate(mContext, R.layout.item_types, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }
        BrandClassBean.RecommendBean recommendBean = Recommend.get(position);
        //设置数据
        String imgpath = ApiUrlConstant.API_IMG_URL + recommendBean.getFile_path();
        new RxImageLoader().display(viewHolder.ivImage, imgpath, DensityUtil.getDeviceWidth(viewHolder.ivImage.getContext()) / 3,
                DensityUtil.getDeviceWidth(viewHolder.ivImage.getContext()) / 3);
        viewHolder.tvText.setText(TextUtils.isEmpty(recommendBean.getName()) ? "" : recommendBean.getName());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.tv_text)
        TextView tvText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
