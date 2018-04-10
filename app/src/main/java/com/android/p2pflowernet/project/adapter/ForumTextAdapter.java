package com.android.p2pflowernet.project.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.ForumDetailsBean;

import java.util.List;

import cn.droidlover.xrichtext.XRichText;

/**
 * Created by Administrator on 2018/1/30.
 */

public class ForumTextAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ForumDetailsBean> detailsBeans;
    private Context mContext;

    public ForumTextAdapter(List<ForumDetailsBean> detailsBeans, Context mContext) {
        this.detailsBeans = detailsBeans;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_forum_text,parent,false);
        return new TextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextViewHolder textViewHolder = (TextViewHolder) holder;
        textViewHolder.xRichText.text(detailsBeans.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return detailsBeans.size() > 0 ? detailsBeans.size() : 0;
    }

    public static class TextViewHolder extends RecyclerView.ViewHolder{
        private XRichText xRichText;

        public TextViewHolder(View view) {
            super(view);
            xRichText = (XRichText) view.findViewById(R.id.richText);
        }
    }
}
