package com.android.p2pflowernet.project.utils;

import android.widget.ImageView;

import com.android.p2pflowernet.project.R;
import com.bumptech.glide.Glide;
import com.caimuhao.rxpicker.utils.RxPickerImageLoader;

/**
 * author: zhangpeisen
 * created on: 2017/11/3 下午4:35
 * description: 本地图库选择器
 */
public class RxImageLoader implements RxPickerImageLoader {

    @Override
    public void display(ImageView imageView, String path, int width, int height) {
        Glide.with(imageView.getContext()).load(path).error(R.drawable.icon_defult)
                .centerCrop().override(width, height).into(imageView);
    }
}
