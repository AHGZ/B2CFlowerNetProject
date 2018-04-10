package com.android.p2pflowernet.project.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.ListsBean;
import com.android.p2pflowernet.project.utils.GlideImageLoader;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.view.customview.RatingBarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;

import static com.android.p2pflowernet.project.constant.ApiUrlConstant.API_IMG_URL;

/**
 * Created by caishen on 2017/12/5.
 * by--
 */

public class GoodsEveluateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final FragmentActivity mContext;
    private List<ListsBean> data;

    public GoodsEveluateAdapter(FragmentActivity activity, List<ListsBean> lists) {
        this.mContext = activity;
        this.data = lists;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_eveluate_layout, parent, false);
        return new GoodsEveluateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ListsBean listsBean = data.get(position);
        ((GoodsEveluateViewHolder) holder).bindDateView(listsBean, position, mContext);
    }

    @Override
    public int getItemCount() {

        if (data.size() >= 2) {
            return 2;
        } else {
            return data == null ? 0 : data.size();
        }
    }

    static class GoodsEveluateViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_user)
        ImageView ivUser;
        @BindView(R.id.tv_user)
        TextView tvUser;
        @BindView(R.id.ms_ratingbar)
        RatingBarView msRatingbar;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.tv_cs)
        TextView tvCs;
        @BindView(R.id.tv_reply)
        TextView tvReply;
        @BindView(R.id.ll_photo)
        LinearLayout llPhoto;
        @BindView(R.id.hs_view)
        HorizontalScrollView hsView;
        private ImageView imageView;
        private int finalI;

        GoodsEveluateViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        //绑定数据
        public void bindDateView(ListsBean eveluateBean, int position, final FragmentActivity mContext) {

            GlideImageLoader glideImageLoader = new GlideImageLoader();
            if (TextUtils.isEmpty(eveluateBean.getHeader_img())) {
                glideImageLoader.displayImage(mContext, R.id.iv_user, ivUser);
            } else {
                glideImageLoader.displayImage(mContext, API_IMG_URL + eveluateBean.getHeader_img(), ivUser);
            }

            tvUser.setText(eveluateBean.getNickname());
            tvDesc.setText(eveluateBean.getContent());
            tvCs.setText(eveluateBean.getGoods_spec());
            msRatingbar.setStar(Integer.valueOf(eveluateBean.getGoods_desc_score()), false);
            msRatingbar.setEnabled(false);
            msRatingbar.setClickable(false);
            //判断图片
            ArrayList<String> img_lists = eveluateBean.getImg_lists();
            if (img_lists != null && img_lists.size() > 0) {//有图片

                llPhoto.setVisibility(View.VISIBLE);
                //动态添加图片地址
                final ArrayList<String> strings = new ArrayList<>();
                if (strings != null && strings.size() > 0) {
                    strings.clear();
                }

                for (int i = 0; i < img_lists.size(); i++) {

                    String imgPath = API_IMG_URL + img_lists.get(i).toString();
                    strings.add(imgPath);
                    imageView = new ImageView(mContext);
                    imageView.setPadding(0, 8, 8, 0);
                    imageView.setLayoutParams(new ViewGroup.LayoutParams(UIUtils.dip2Px(mContext, 84), UIUtils.dip2Px(mContext, 84)));
                    glideImageLoader.displayImage(mContext, imgPath, imageView);

                    //设置点击事件
                    finalI = i;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            mContext.startActivity((BGAPhotoPreviewActivity.newIntent(mContext, null, strings, finalI)));
                        }
                    });

                    llPhoto.addView(imageView);
                }

            } else {//没有图片

                llPhoto.setVisibility(View.GONE);
            }
        }
    }
}
