package com.android.p2pflowernet.project.utils;


import android.content.Context;
import android.widget.ImageView;

import com.android.p2pflowernet.project.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by geyifeng on 2017/6/4.
 */

public class GlideImageLoader extends ImageLoader {


    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {

        Glide.with(context.getApplicationContext())
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.icon_defult)
                .into(imageView);
    }
}
