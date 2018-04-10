package com.android.p2pflowernet.project.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.base.HFWBaseAdapter;
import com.android.p2pflowernet.project.callback.BaseHolder;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.O2oHomeBean;
import com.android.p2pflowernet.project.utils.GlideImageLoader;
import com.android.p2pflowernet.project.view.customview.NoScrollGridView;
import com.android.p2pflowernet.project.view.customview.RatingBarView;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caishen on 2017/12/28.
 * by--生活首页的适配器
 */

public class IndexO2OAdapter extends HFWBaseAdapter<O2oHomeBean.ListBean> {

    private static final int TYPE_ONE = 0;//轮播图
    private static final int TYPE_TWO = 1;//gridView分类
    private static final int TYPE_THREE = 2;//附近商家
    private final FragmentActivity mContext;
    private List<String> mImages = new ArrayList<>();
    private O2oOnclickListener o2oOnclickListener;
    private OnListSortClickLitener onListSortClickLitener;
    private OnListSaleSortClickLitener onListSaleSortClickLitener;
    private OnListDistenceSortClickLitener onListDistenceSortClickLitener;
    private OnListSelSortClickLitener onListSelSortClickLitener;

    public IndexO2OAdapter(FragmentActivity activity) {
        this.mContext = activity;
    }

    public void setO2oOnclickListener(O2oOnclickListener o2oOnclickListener) {
        this.o2oOnclickListener = o2oOnclickListener;
    }

    public void setOnListSortClickLitener(OnListSortClickLitener onListSortClickLitener) {
        this.onListSortClickLitener = onListSortClickLitener;
    }

    public void setOnListSaleSortClickLitener(OnListSaleSortClickLitener onListSaleSortClickLitener) {
        this.onListSaleSortClickLitener = onListSaleSortClickLitener;
    }

    public void setOnListDistenceSortClickLitener(OnListDistenceSortClickLitener onListDistenceSortClickLitener) {
        this.onListDistenceSortClickLitener = onListDistenceSortClickLitener;
    }

    public void setOnListSelSortClickLitener(OnListSelSortClickLitener onListSelSortClickLitener) {
        this.onListSelSortClickLitener = onListSelSortClickLitener;
    }

    /**
     * O2o的点击事件
     */
    public interface O2oOnclickListener {

        void o2oOnclickListener(View view, int position);
    }

    //综合排序
    public interface OnListSortClickLitener {

        void listSortClickLitener(View view, int position);

    }

    //销量最高
    public interface OnListSaleSortClickLitener {

        void listSaleSortClickLitener(View view, int position);

    }

    //距离最近
    public interface OnListDistenceSortClickLitener {

        void listDistenSortClickLitener(View view, int position);

    }

    //筛选
    public interface OnListSelSortClickLitener {

        void listSelSortClickLitener(View view, int position);

    }

    @Override
    public BaseHolder<O2oHomeBean.ListBean> onViewHolderCreate(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ONE) {
            return new BannerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.index_banner, parent, false));
        } else if (viewType == TYPE_TWO) {
            return new O2oViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.index_o2o, parent, false));
        } else {
            return new NearbyShopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nearby_shop, parent, false));
        }
    }

    @Override
    public void onViewHolderBind(BaseHolder<O2oHomeBean.ListBean> holder, int position) {
        if (getItemViewType(position) == TYPE_ONE) {
            O2oHomeBean.ListBean indexO2oBean = list.get(position);
            ((BannerViewHolder) holder).setData(indexO2oBean, position);
        } else if (getItemViewType(position) == TYPE_TWO) {
            O2oHomeBean.ListBean indexO2oBean = list.get(position);
            ((O2oViewHolder) holder).setData(mContext, position);
        } else if (getItemViewType(position) == TYPE_THREE) {
            O2oHomeBean.ListBean indexO2oBean = list.get(position);
            ((NearbyShopViewHolder) holder).setData(mContext, indexO2oBean, position);
        }
    }

    @Override
    public int getItemViewTypes(int position) {

        return TYPE_THREE;
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    //轮播图
    class BannerViewHolder extends BaseHolder<O2oHomeBean.ListBean> {
        @BindView(R.id.banner)
        Banner banner;

        BannerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        //设置数据
        public void setData(O2oHomeBean.ListBean indexO2oBean, int position) {

            banner.setImageLoader(new GlideImageLoader())
                    .setImages(mImages)
                    .setDelayTime(5000)
                    .start();
        }
    }

    //分类
    class O2oViewHolder extends BaseHolder<O2oHomeBean.ListBean> {
        @BindView(R.id.gridview)
        NoScrollGridView gridview;
        @BindView(R.id.view_gray)
        View viewGray;

        O2oViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        //设置数据
        public void setData(FragmentActivity mContext, int position) {
            viewGray.setVisibility(View.GONE);

            //设置适配器
            IndexO2oMenueAdapter o2oMenueAdapter = new IndexO2oMenueAdapter(mContext);
            gridview.setAdapter(o2oMenueAdapter);

            //设置点击事件
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
        }
    }

    //附近商家来列表数据
    class NearbyShopViewHolder extends BaseHolder<O2oHomeBean.ListBean> {

        @BindView(R.id.iv_img)
        ImageView ivImg;
        @BindView(R.id.iv_by_ticket)
        ImageView ivByTicket;
        @BindView(R.id.tv_by_self)
        TextView tvBySelf;
        @BindView(R.id.ratin_star)
        RatingBarView ratinStar;
        @BindView(R.id.yu_sale)
        TextView yuSale;
        @BindView(R.id.tv_minue)
        TextView tvMinue;
        @BindView(R.id.tv_distance)
        TextView tvDistance;
        @BindView(R.id.qis)
        TextView qis;
        @BindView(R.id.peis)
        TextView peis;
        @BindView(R.id.rj)
        TextView rj;
        @BindView(R.id.iv_manf)
        ImageView ivManf;
        @BindView(R.id.tv_manf)
        TextView tvManf;
        @BindView(R.id.iv_height)
        ImageView ivHeight;
        @BindView(R.id.tv_height)
        TextView tvHeight;
        @BindView(R.id.tv_nick)
        TextView tvNick;
        @BindView(R.id.ll_item)
        LinearLayout ll_item;

        NearbyShopViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        //设置数据
        public void setData(FragmentActivity mContext, O2oHomeBean.ListBean data, final int position) {

            tvNick.setText(data.getMerch_name() == null ? "" : data.getMerch_name());
            ratinStar.setStar(Integer.parseInt(String.valueOf(Math.round(Double.parseDouble(data.getEval_score())))), false);
            ratinStar.setEnabled(false);
            qis.setText(data.getDistrib_quota() == null ? "" : "起送" + "¥" + data.getDistrib_quota());
            peis.setText(data.getDistrib_money() == null ? "配送" + "¥ 0" : "配送" + "¥" + data.getDistrib_money());
            yuSale.setText(data.getMonth_sale() == null ? "" : data.getMonth_sale());//月售
            tvMinue.setText(data.getService_time() == null ? "" + "分钟" : data.getService_time() + "分钟");
            tvDistance.setText(data.getDistance() + "m");//距离
            String file_path = ApiUrlConstant.API_IMG_URL + data.getFile_path();
            new GlideImageLoader().displayImage(mContext, file_path, ivImg);

            //是否支持自取
            //0-不允许 1-允许
            String self_pick_setting = data.getSelf_pick_setting();
            if (self_pick_setting.equals("0")) {
                tvBySelf.setVisibility(View.GONE);
            } else {
                tvBySelf.setVisibility(View.VISIBLE);
            }

            //是否支持开发票
            //0-不 1-是
            String invoice_setting = data.getInvoice_setting();
            if (invoice_setting.equals("0")) {
                ivByTicket.setVisibility(View.GONE);
            } else {
                ivByTicket.setVisibility(View.VISIBLE);
            }

            ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (o2oOnclickListener != null) {
                        o2oOnclickListener.o2oOnclickListener(v, position);
                    }
                }
            });

//            rj.setText("人均" + data.getRj());
//            tvManf.setText(data.getAtivity());
//            tvHeight.setText(data.getHeight());
        }
    }

}
