package com.android.p2pflowernet.project.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.base.HFWBaseAdapter;
import com.android.p2pflowernet.project.callback.BaseHolder;
import com.android.p2pflowernet.project.entity.ListsBean;
import com.android.p2pflowernet.project.utils.GlideImageLoader;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.view.customview.RatingBarView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;

import static com.android.p2pflowernet.project.constant.ApiUrlConstant.API_IMG_URL;

/**
 * Created by caishen on 2017/12/5.
 * by--评价适配器
 */

public class EveluateAdapter extends HFWBaseAdapter<ListsBean> {
    private final FragmentActivity mContext;

    public EveluateAdapter(FragmentActivity activity) {
        this.mContext = activity;
    }

    @Override
    public BaseHolder<ListsBean> onViewHolderCreate(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_eveluate_layout, parent, false);
        return new EveluateViewHolder(view);
    }

    @Override
    public void onViewHolderBind(BaseHolder<ListsBean> holder, int position) {

        ListsBean eveluateBean = list.get(position);
        ((EveluateViewHolder) holder).bindDateView(eveluateBean, position, mContext);
    }

    static class EveluateViewHolder extends BaseHolder<ListsBean> {

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
        @BindView(R.id.hs_view)
        HorizontalScrollView hsView;
        @BindView(R.id.ll_photo)
        LinearLayout llPhoto;

        EveluateViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        //设置数据
        public void bindDateView(ListsBean eveluateBean, int position, final FragmentActivity mContext) {

            GlideImageLoader glideImageLoader = new GlideImageLoader();
            glideImageLoader.displayImage(mContext, API_IMG_URL + eveluateBean.getHeader_img(), ivUser);
            tvUser.setText(eveluateBean.getNickname());
            tvDesc.setText(eveluateBean.getContent());
            tvCs.setText(eveluateBean.getGoods_spec());
            msRatingbar.setStar(Integer.valueOf(eveluateBean.getGoods_desc_score()), false);
            msRatingbar.setEnabled(false);
            msRatingbar.setClickable(false);
            if (eveluateBean.getIs_reply().equals("1")){
                tvReply.setVisibility(View.VISIBLE);
            }else{
                tvReply.setText("商家回复:"+eveluateBean.getReply_content());
            }
            //判断图片
            ArrayList<String> img_lists = eveluateBean.getImg_lists();
            if (img_lists != null && img_lists.size() > 0) {//有图片

                //动态添加图片地址
                final ArrayList<String> strings = new ArrayList<>();
                if (strings != null && strings.size() > 0) {
                    strings.clear();
                }

                llPhoto.setVisibility(View.VISIBLE);
                for (int i = 0; i < img_lists.size(); i++) {
                    String imgPath = API_IMG_URL + img_lists.get(i);
                    strings.add(imgPath);

                    ImageView imageView = new ImageView(mContext);
                    imageView.setPadding(0, 8, 8, 0);
                    imageView.setLayoutParams(new ViewGroup.LayoutParams(UIUtils.dip2Px(mContext, 84), UIUtils.dip2Px(mContext, 84)));
                    glideImageLoader.displayImage(mContext, imgPath, imageView);

                    //设置点击事件
                    final int finalI = i;
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
