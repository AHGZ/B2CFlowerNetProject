package com.android.p2pflowernet.project.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.ForumListBean;
import com.android.p2pflowernet.project.utils.GlideImageLoader;
import com.android.p2pflowernet.project.view.fragments.forum.textdetails.TextDetailsActivity;
import com.android.p2pflowernet.project.view.fragments.forum.videodetails.VideoDetailsActivity;

import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by zhangkun on 2018/1/18.
 */

public class ForumMemberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPEVIDEOTOP = 2;
    private static final int TYPEVIDEO = 1;
    private static final int TYPETEXT = 0;
    private Context mContext;
    private List<ForumListBean.ListsBean> listsBeans;

    public ForumMemberAdapter(Context context,List<ForumListBean.ListsBean> data) {
        this.mContext = context;
        this.listsBeans = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPEVIDEO) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_videoview, parent, false);
            return new VideoHolder(view);
        } else if (viewType == TYPETEXT){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_textview, parent, false);
            return new TextHolder(view);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_videoview_top,parent,false);
            return new VideoTopHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ForumListBean.ListsBean listsBean = listsBeans.get(position);
        switch (holder.getItemViewType()){

            case TYPETEXT:
                TextHolder textHolder = (TextHolder) holder;
                textHolder.textView.setText(listsBean.getTitle());
                textHolder.tv_describe.setText(listsBean.getShort_title());
                new GlideImageLoader().displayImage(mContext,ApiUrlConstant.API_IMG_URL + listsBean.getUrl(),textHolder.mImageView);
                textHolder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转到内容详情页面
                        Intent intent = new Intent(mContext, TextDetailsActivity.class);
                        intent.putExtra("id",listsBean.getId() + "");
                        mContext.startActivity(intent);
                    }
                });
                break;

            case TYPEVIDEO:
                VideoHolder videoHolder = (VideoHolder) holder;
                videoHolder.tv_title.setText(listsBean.getTitle());
                videoHolder.tv_describe.setText(listsBean.getShort_title());
                videoHolder.jzVideoPlayer.setUp(ApiUrlConstant.API_BASE_URL + listsBean.getUrl(), JZVideoPlayer.SCREEN_WINDOW_LIST, listsBean.getShort_title());
                videoHolder.jzVideoPlayer.positionInList = position;
                new GlideImageLoader().displayImage(mContext, ApiUrlConstant.API_IMG_URL + listsBean.getUrl(),videoHolder.jzVideoPlayer.thumbImageView);
                videoHolder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转视频详情页面
                        Intent intent = new Intent(mContext, VideoDetailsActivity.class);
                        intent.putExtra("id",listsBean.getId() + "");
                        mContext.startActivity(intent);
                    }
                });
                break;

            case TYPEVIDEOTOP://置顶视频

                VideoTopHolder videoTopHolder = (VideoTopHolder) holder;
                videoTopHolder.playerStandard.setUp(ApiUrlConstant.API_BASE_URL + listsBean.getUrl(), JZVideoPlayer.SCREEN_WINDOW_LIST, listsBean.getShort_title());
                new GlideImageLoader().displayImage(mContext, ApiUrlConstant.API_BASE_URL + listsBean.getUrl(),videoTopHolder.playerStandard.thumbImageView);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        ForumListBean.ListsBean bean = listsBeans.get(position);

        if (bean.getType() == 1) {
            return TYPETEXT;//非视频
        }else{
            if (position == 0) {
                if (bean.getIs_top() == 1) {
                    return TYPEVIDEOTOP;//置顶视屏
                }else{
                    return TYPEVIDEO;//视频
                }
            }else{
                return TYPEVIDEO;//视频
            }
        }
    }

    @Override
    public int getItemCount() {
        return listsBeans.size();
    }

    public static class VideoHolder extends RecyclerView.ViewHolder {
        JZVideoPlayerStandard jzVideoPlayer;
        RelativeLayout mRelativeLayout;
        LinearLayout mLinearLayout;
        TextView tv_title;
        TextView tv_describe;

        public VideoHolder(View itemView) {
            super(itemView);
            jzVideoPlayer = (JZVideoPlayerStandard) itemView.findViewById(R.id.videoplayer);
            mRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.item_video_rl);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.item_videoView_linearLayout);
            tv_title = (TextView) itemView.findViewById(R.id.item_video_title);
            tv_describe = (TextView) itemView.findViewById(R.id.item_video_describe);
        }
    }

   public static class TextHolder extends RecyclerView.ViewHolder {
        TextView textView;
        RelativeLayout mRelativeLayout;
        LinearLayout mLinearLayout;
        TextView tv_describe;
        ImageView mImageView;

        public TextHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_video_textView_title);
            mRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.item_video_textView_rl);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.item_textView_linearLayout);
            tv_describe = (TextView) itemView.findViewById(R.id.item_video_textView_describe);
            mImageView = (ImageView) itemView.findViewById(R.id.item_video_textView_img);
        }
   }

   public static class VideoTopHolder extends RecyclerView.ViewHolder {
        JZVideoPlayerStandard playerStandard;

        public VideoTopHolder(View itemView) {
            super(itemView);
            playerStandard = (JZVideoPlayerStandard) itemView.findViewById(R.id.item_videoview_top_JZVideoPlayerStandard);
        }
   }
}
