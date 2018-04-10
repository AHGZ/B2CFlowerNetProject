package com.android.p2pflowernet.project.adapter;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.base.HFWBaseAdapter;
import com.android.p2pflowernet.project.callback.BaseHolder;
import com.android.p2pflowernet.project.entity.O2oGoodsInfoBean;
import com.android.p2pflowernet.project.view.customview.NoScrollGridView;
import com.android.p2pflowernet.project.view.customview.RatingBarView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caishen on 2018/1/25.
 * by--
 */

public class O2oOrderDetailAdapter extends HFWBaseAdapter<O2oGoodsInfoBean.ListsBean> {
    private final FragmentActivity mContext;

    public O2oOrderDetailAdapter(FragmentActivity activity) {
        this.mContext = activity;
    }

    @Override
    public BaseHolder<O2oGoodsInfoBean.ListsBean> onViewHolderCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_evaluation, parent, false);
        return new O2oGoodsInfoViewHolder(view);
    }

    @Override
    public void onViewHolderBind(BaseHolder<O2oGoodsInfoBean.ListsBean> holder, int position) {
        ((O2oGoodsInfoViewHolder) holder).bindData(list, position, mContext);
    }

    static class O2oGoodsInfoViewHolder extends BaseHolder<O2oGoodsInfoBean.ListsBean> {
        @BindView(R.id.evaluation_img)
        ImageView evaluationImg;
        @BindView(R.id.evaluation_user_name)
        TextView evaluationUserName;
        @BindView(R.id.ratin_star)
        RatingBarView ratinStar;
        @BindView(R.id.evaluation_content)
        TextView evaluationContent;
        @BindView(R.id.group_buying_shopdetails_gridview)
        NoScrollGridView groupBuyingShopdetailsGridview;
        @BindView(R.id.evaluation_date)
        TextView evaluationDate;

        O2oGoodsInfoViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindData(List<O2oGoodsInfoBean.ListsBean> lists, int position, FragmentActivity mContext) {

            //给用来显示图片的gridview设置适配器
            GroupBuyingEvaluationGridAdapter groupBuyingEvaluationGridAdapter = new GroupBuyingEvaluationGridAdapter(mContext,lists);
            groupBuyingShopdetailsGridview.setAdapter(groupBuyingEvaluationGridAdapter);
            //是否匿名(0-不匿名 1-匿名)
            if (lists.get(position).getIs_anon().equals("0")) {
                //评价内容图像，展示位圆形
                Glide.with(mContext).load(lists.get(position).getFile_path())
                        .asBitmap().centerCrop().into(new BitmapImageViewTarget(evaluationImg) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        evaluationImg.setImageDrawable(circularBitmapDrawable);
                    }
                });
                //评价内容用户的名字
                evaluationUserName.setText(lists.get(position).getUsername());
            } else {
                //评价内容用户的名字
                evaluationUserName.setText("匿名用户");
            }
            //评论主要内容
            evaluationContent.setText(lists.get(position).getContent());
            //日期
            evaluationDate.setText(lists.get(position).getCreated());
            //评分
            ratinStar.setStar(Integer.parseInt(lists.get(position).getScore()), true);
            ratinStar.setEnabled(false);
            ratinStar.setFocusable(false);
        }
    }
}
