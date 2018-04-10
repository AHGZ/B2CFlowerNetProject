package com.android.p2pflowernet.project.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.EvaluateGoodsBean;
import com.android.p2pflowernet.project.entity.TakeOutToEvaluateGoodsBean;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.RatingBarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/20.
 */

public class TakeOutToEvaluateListAdapter extends RecyclerView.Adapter {

    private List<TakeOutToEvaluateGoodsBean.GoodsListBean> beans;
    private List<EvaluateGoodsBean> data = new ArrayList<>();
    private Context mContext;
    private OnGetGoodsBeanListener onGetGoodsBeanListener;

    public void setOnGetGoodsBeanListener(OnGetGoodsBeanListener onGetGoodsBeanListener) {
        this.onGetGoodsBeanListener = onGetGoodsBeanListener;
    }

    public interface OnGetGoodsBeanListener{
        void getGoodsBean(List<EvaluateGoodsBean> goodsBeans);
    }

    public TakeOutToEvaluateListAdapter(Context context,List<TakeOutToEvaluateGoodsBean.GoodsListBean> beans ,List<EvaluateGoodsBean> goodsBean) {
        this.beans = beans;
        this.mContext = context;
        data.addAll(goodsBean);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_takeout_toevaluate_list,parent,false);
        return new  ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.editText.setTag(position);
        viewHolder.editText.setNestedScrollingEnabled(false);
        viewHolder.mTextView.setText(beans.get(position).getGoods_name());
        viewHolder.barView.setStar(5,false);
        viewHolder.barView.setOnRatingListener(new RatingBarView.OnRatingListener() {
            @Override
            public void onRating(Object bindObject, int RatingScore) {
                data.get(position).setScore(RatingScore);
                if (onGetGoodsBeanListener != null){
                    onGetGoodsBeanListener.getGoodsBean(data);
                }
            }
        });
        viewHolder.editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = viewHolder.editText.getText().toString().trim();
                if (!TextUtils.isEmpty(str) && str.length() > 0) {
                    data.get(position).setContent(str);
                    viewHolder.tv_place.setText(str.length() + "/30字");
                    if (onGetGoodsBeanListener != null){
                        onGetGoodsBeanListener.getGoodsBean(data);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_takeout_toEvaluate_list_tv)
        TextView mTextView;
        @BindView(R.id.item_takeout_toEvaluate_list_rb)
        RatingBarView barView;
        @BindView(R.id.item_takeout_toEvaluate_editText_goods)
        CustomEditText editText;
        @BindView(R.id.item_takeout_toEvaluate_list_tv_goods)
        TextView tv_place;//输入评论字数限制显示

         public ViewHolder(View view) {
             super(view);
             ButterKnife.bind(this, view);
        }
    }
}
