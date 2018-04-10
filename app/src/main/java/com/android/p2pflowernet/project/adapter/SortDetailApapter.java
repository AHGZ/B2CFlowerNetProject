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
import com.android.p2pflowernet.project.entity.BrandScendBean;
import com.android.p2pflowernet.project.utils.RxImageLoader;
import com.caimuhao.rxpicker.utils.DensityUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @描述:
 * @方法名: 商品三级分类
 * @返回类型
 * @创建人 zhangpeisen
 * @创建时间 2017/12/5 下午2:52
 * @修改人 zhangpeisen
 * @修改时间 2017/12/5 下午2:52
 * @修改备注
 * @throws
 */
public class SortDetailApapter extends BaseAdapter {

    private Context mContext;
    private List<BrandScendBean.FlBean.ThreeBean> mThreeBeanList;

    public SortDetailApapter(Context mContext, List<BrandScendBean.FlBean.ThreeBean> threebeanlist) {
        this.mContext = mContext;
        this.mThreeBeanList = threebeanlist;
    }

    @Override
    public int getCount() {
        return mThreeBeanList.size();
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
        BrandScendBean.FlBean.ThreeBean threebean = mThreeBeanList.get(position);
        if (threebean == null) {
            return convertView;
        }
        //设置数据
        String imgpath = ApiUrlConstant.API_IMG_URL + threebean.getFile_path();
        new RxImageLoader().display(viewHolder.ivImage, imgpath, DensityUtil.getDeviceWidth(viewHolder.ivImage.getContext()) / 3,
                DensityUtil.getDeviceWidth(viewHolder.ivImage.getContext()) / 3);
        viewHolder.tvText.setText(TextUtils.isEmpty(threebean.getName()) ? "" : threebean.getName());
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
