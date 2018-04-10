package com.android.p2pflowernet.project.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.bigkoo.convenientbanner.holder.Holder;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 图片轮播适配器
 */
public class NetworkImageHolderView implements Holder<String> {
    private View rootview;
    private SimpleDraweeView imageView;

    @Override
    public View createView(Context context) {
        rootview = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.goods_item_head_img, null);
        imageView = (SimpleDraweeView) rootview.findViewById(R.id.sdv_item_head_img);
        return rootview;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        imageView.setImageURI(Uri.parse(ApiUrlConstant.API_IMG_URL+data));
    }
}
