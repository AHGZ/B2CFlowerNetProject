package com.android.p2pflowernet.project.adapter;/*
package com.android.p2pflowernet.project.adapter;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.GroupBuyingBean;
import com.android.p2pflowernet.project.view.customview.NoScrollGridView;
import com.android.p2pflowernet.project.view.customview.RatingBarView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by heguozhong on 2018/1/18/018.
 * 团购详情评价列表
 */


import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.GroupBuyingBean;
import com.android.p2pflowernet.project.view.customview.NoScrollGridView;
import com.android.p2pflowernet.project.view.customview.RatingBarView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupEvaluationListiviewAdapter extends BaseAdapter {

    private GroupBuyingBean.InfoBean info;
    private FragmentActivity activity;
    private final List<GroupBuyingBean.InfoBean.EvalListBean> eval_list;

    public GroupEvaluationListiviewAdapter(FragmentActivity activity, GroupBuyingBean.InfoBean info) {
        this.activity=activity;
        this.info=info;
        eval_list = info.getEval_list();
    }

    @Override
    public int getCount() {
        if (eval_list.size()>3){
            return 3;
        }else {
            return eval_list.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return eval_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.group_buying_evaluation_listview_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //是否匿名(0-不匿名 1-匿名)
        if (eval_list.get(position).getIs_anon()==0){
            //评价内容图像，展示位圆形
            final ViewHolder finalViewHolder = viewHolder;
            Glide.with(activity).load(eval_list.get(position).getHeader_img()).asBitmap().centerCrop().into(new BitmapImageViewTarget(viewHolder.evaluationImg) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(activity.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    finalViewHolder.evaluationImg.setImageDrawable(circularBitmapDrawable);
                }
            });
            //评价内容用户的名字
            viewHolder.evaluationUserName.setText(eval_list.get(position).getUsername());
        }else{
            //评价内容用户的名字
            viewHolder.evaluationUserName.setText("匿名用户");
        }

        //评价内容日期
        viewHolder.evaluationDate.setText(eval_list.get(position).getCreated());
        //评价内容的主要内容
        viewHolder.evaluationContent.setText(eval_list.get(position).getContent());
        //评价星数
        viewHolder.ratinStar.setClickable(false);
        viewHolder.ratinStar.setStar(eval_list.get(position).getScore(),true);
        List<String> img_list = eval_list.get(position).getImg_list();
        GroupBuyingShopGridAdapter groupBuyingShopGridAdapter = new GroupBuyingShopGridAdapter(img_list);
        viewHolder.groupBuyingShopdetailsGridview.setAdapter(groupBuyingShopGridAdapter);

        return convertView;
    }

    static class ViewHolder {
        //评价内容用户的头像
        @BindView(R.id.evaluation_img)
        ImageView evaluationImg;
        //评价内容用户的名字
        @BindView(R.id.evaluation_user_name)
        TextView evaluationUserName;
        //评价打星
        @BindView(R.id.ratin_star)
        RatingBarView ratinStar;
        //评价内容的主要内容
        @BindView(R.id.evaluation_content)
        TextView evaluationContent;
        //评价内容中显示图片的gridview布局
        @BindView(R.id.group_buying_shopdetails_gridview)
        NoScrollGridView groupBuyingShopdetailsGridview;
        //评价内容日期
        @BindView(R.id.evaluation_date)
        TextView evaluationDate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}

