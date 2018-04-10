package com.android.p2pflowernet.project.adapter;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.ShopEvaluationBean;
import com.android.p2pflowernet.project.view.customview.NoScrollGridView;
import com.android.p2pflowernet.project.view.customview.RatingBarView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.List;

/**
 * Created by heguozhong on 2018/1/5/005.
 * 评价详情适配器
 */

public class EvaluationRecyclerAdapter extends RecyclerView.Adapter {

    private FragmentActivity activity;
    private List<ShopEvaluationBean.ListsBean> lists;

    public EvaluationRecyclerAdapter(FragmentActivity activity, List<ShopEvaluationBean.ListsBean> lists) {
        this.activity = activity;
        this.lists=lists;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_evaluation, parent, false);
        MyViewHolder1 myViewHolder1 = new MyViewHolder1(view);
        return myViewHolder1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final MyViewHolder1 holder1 = (MyViewHolder1) holder;

        List<String> imgs_list = lists.get(position).getImgs_list();
//        给用来显示图片的gridview设置适配器
        GroupEvaluationGridAdapter groupBuyingEvaluationGridAdapter = new GroupEvaluationGridAdapter(imgs_list);
        holder1.gridView.setAdapter(groupBuyingEvaluationGridAdapter);
        //是否匿名(0-不匿名 1-匿名)
        if (lists.get(position).getIs_anon().equals("0")){
            //评价内容图像，展示位圆形
            Glide.with(activity).load(lists.get(position).getFile_path()).asBitmap().centerCrop().placeholder(R.mipmap.default_header).into(new BitmapImageViewTarget(holder1.evaluationImg) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(activity.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    holder1.evaluationImg.setImageDrawable(circularBitmapDrawable);
                }
            });
            //评价内容用户的名字
            holder1.evaluationUserName.setText(lists.get(position).getUsername());
        }else{
            //评价内容用户的名字
            holder1.evaluationUserName.setText("匿名用户");
        }
        //评论主要内容
        holder1.evaluationContent.setText(lists.get(position).getContent());
        //日期
        holder1.evaluationDate.setText(lists.get(position).getCreated());
        //评分
        holder1.ratingBarView.setClickable(false);
        holder1.ratingBarView.setStar(Integer.parseInt(lists.get(position).getScore()),true);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class MyViewHolder1 extends RecyclerView.ViewHolder {

        private final NoScrollGridView gridView;//评价内容中显示图片的gridview布局
        private final ImageView evaluationImg;//评价内容用户的头像
        private final TextView evaluationUserName;//评价内容用户的名字
        private final TextView evaluationDate;//评价内容日期
        private final TextView evaluationContent;//评价内容的主要内容
        private final RatingBarView ratingBarView;//打星

        public MyViewHolder1(View itemView) {
            super(itemView);
            gridView = (NoScrollGridView) itemView.findViewById(R.id.group_buying_shopdetails_gridview);
            evaluationImg = (ImageView) itemView.findViewById(R.id.evaluation_img);
            evaluationUserName = (TextView) itemView.findViewById(R.id.evaluation_user_name);
            evaluationDate = (TextView) itemView.findViewById(R.id.evaluation_date);
            evaluationContent = (TextView) itemView.findViewById(R.id.evaluation_content);
            ratingBarView = (RatingBarView) itemView.findViewById(R.id.ratin_star);
        }


    }
}
