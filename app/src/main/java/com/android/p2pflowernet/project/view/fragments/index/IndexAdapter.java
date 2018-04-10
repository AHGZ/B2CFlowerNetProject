package com.android.p2pflowernet.project.view.fragments.index;


import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.IndexHotAdapter;
import com.android.p2pflowernet.project.adapter.O2oMenueAdapter;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.IndexHomeBean;
import com.android.p2pflowernet.project.event.BrandEvent;
import com.android.p2pflowernet.project.event.MainEvent;
import com.android.p2pflowernet.project.utils.GlideImageLoader;
import com.android.p2pflowernet.project.view.activity.GoodsDetailActivity;
import com.android.p2pflowernet.project.view.customview.CoverFlowView;
import com.android.p2pflowernet.project.view.customview.NoScrollGridView;
import com.android.p2pflowernet.project.view.customview.NoticTextView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.android.p2pflowernet.project.utils.ConvertUtils.getResources;


/**
 * Created by caishen on 2017/10/21.
 * by--首页分类适配器
 */

public class IndexAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ONE = 0;//轮播图
    private static final int TYPE_TWO = 1;//通知公告
    private static final int TYPE_THREE = 2;//分类
    private static final int TYPE_FOUR = 3;//热门推荐
    private static final int TYPE_FIVE = 4;//精选商品
    private static final int TYPE_SIX = 5;//精选商品
    private static final int TYPE_SEVEN = 6;//玩转3C
    private static final int TYPE_EIGHT = 7;//时尚服饰
    private static final int TYPE_NIGHT = 8;//大牌家电
    private static final int TYPE_TEN = 9;//爱吃
    private static final int TYPE_ELEVEN = 10;//广告banner

    private final FragmentActivity mContext;
    private final LayoutInflater mLayoutInflater;
    private final IndexHomeBean data;
    private BannerOnclickListener bannerListener;
    private NoticeOnclickListener noticeOnclickListener;
    private O2oOnclickListener o2oOnclickListener;
    private HotOnclickListener hotOnclickListener;
    private SelectionsOnclickListener selectionsOnclickListener;
    private ElectronicOnclickListener electronicOnclickListener;
    private ClothingOnclickListener clothingOnclickListener;
    private AppliancesOnclickListener appliancesOnclickListener;
    private LoveEatOnclickListener loveEatOnclickListener;
    private SelectionOnclickListener selectionOnclickListener;
    private AppliancesOnclickListener advertisingOnclickListener;
    private List<String> mImages = new ArrayList<>();


    public IndexAdapter(FragmentActivity activity, IndexHomeBean data) {
        this.mContext = activity;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.data = data;
    }


    /**
     * 轮播图的点击事件
     */
    public interface BannerOnclickListener {

        void bannerOnclickListener(View view, int position, String url);
    }


    /**
     * 通知公告的点击事件
     */
    public interface NoticeOnclickListener {

        void noticeOnclickListener(View view, int position);
    }


    /**
     * O2o的点击事件
     */
    public interface O2oOnclickListener {

        void o2oOnclickListener(View view, int position);
    }

    /**
     * 热门推荐的点击事件
     */
    public interface HotOnclickListener {

        void hotOnclickListener(View view, int position);
    }


    /**
     * 精选商品的点击事件
     */
    public interface SelectionsOnclickListener {

        void selectionsOnclickListener(View view, int position);
    }

    /**
     * 玩转3C的点击事件
     */
    public interface ElectronicOnclickListener {

        void electronicOnclickListener(View view, int position);
    }


    /**
     * 时尚服装的点击事件
     */
    public interface ClothingOnclickListener {

        void clothingOnclickListener(View view, int position);
    }

    /**
     * 大牌家电的点击事件
     */
    public interface AppliancesOnclickListener {

        void appliancesOnclickListener(View view, int position);
    }

    /**
     * 爱吃的点击事件
     */
    public interface LoveEatOnclickListener {

        void loveEatOnclickListener(View view, int position);
    }


    /**
     * 商品推荐的点击事件
     */
    public interface SelectionOnclickListener {

        void selectionOnclickListener(View view, int position);
    }

    /**
     * 广告banner的点击事件
     */
    public interface AdvertisingOnclickListener {

        void advertisingOnclickListener(View view, int position);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ONE) {//轮播图
            return new BannerViewHolder(mLayoutInflater.inflate(R.layout.index_banner, parent, false));
        } else if (viewType == TYPE_TWO) {//通知公告
            return new NoticeViewHolder(mLayoutInflater.inflate(R.layout.index_notic, parent, false));
        } else if (viewType == TYPE_THREE) {//分类
            return new O2oViewHolder(mLayoutInflater.inflate(R.layout.index_o2o, parent, false));
        } else if (viewType == TYPE_FOUR) {//热门推荐
            return new HotViewHolder(mLayoutInflater.inflate(R.layout.index_hot, parent, false));
        } else if (viewType == TYPE_SIX) {//精选商品
            return new SelectionsViewHolder(mLayoutInflater.inflate(R.layout.index_selections, parent, false));
        } else if (viewType == TYPE_SEVEN) {//玩转3C
            return new ElectronicViewHolder(mLayoutInflater.inflate(R.layout.index_electronic, parent, false));
        } else if (viewType == TYPE_EIGHT) {//时尚服装
            return new ClothingViewHolder(mLayoutInflater.inflate(R.layout.index_clothing, parent, false));
        } else if (viewType == TYPE_NIGHT) {//大牌家电
            return new AppliancesViewHolder(mLayoutInflater.inflate(R.layout.index_appliances, parent, false));
        } else if (viewType == TYPE_TEN) {//爱吃
            return new Love_EatViewHolder(mLayoutInflater.inflate(R.layout.love_eat_hot, parent, false));
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (getItemViewType(position) == TYPE_ONE) {
            BannerViewHolder bannerHolder = (BannerViewHolder) holder;
            bannerHolder.setData(mContext, position);
        } else if (getItemViewType(position) == TYPE_TWO) {
            NoticeViewHolder bannerHolder = (NoticeViewHolder) holder;
            bannerHolder.setData(mContext, position);
        } else if (getItemViewType(position) == TYPE_THREE) {
            O2oViewHolder bannerHolder = (O2oViewHolder) holder;
            bannerHolder.setData(mContext, position);
        } else if (getItemViewType(position) == TYPE_FOUR) {
            HotViewHolder bannerHolder = (HotViewHolder) holder;
            bannerHolder.setData(mContext, position);
        } else if (getItemViewType(position) == TYPE_SIX) {
            SelectionsViewHolder bannerHolder = (SelectionsViewHolder) holder;
            bannerHolder.setData(mContext, position);
        } else if (getItemViewType(position) == TYPE_SEVEN) {
            ElectronicViewHolder bannerHolder = (ElectronicViewHolder) holder;
            bannerHolder.setData(mContext, position);
        } else if (getItemViewType(position) == TYPE_EIGHT) {
            ClothingViewHolder bannerHolder = (ClothingViewHolder) holder;
            bannerHolder.setData(mContext, position);
        } else if (getItemViewType(position) == TYPE_NIGHT) {
            AppliancesViewHolder bannerHolder = (AppliancesViewHolder) holder;
            bannerHolder.setData(mContext, position);
        } else if (getItemViewType(position) == TYPE_TEN) {
            Love_EatViewHolder bannerHolder = (Love_EatViewHolder) holder;
            bannerHolder.setData(mContext, position);
        } else if (getItemViewType(position) == TYPE_ELEVEN) {
            AdvertisingViewHolder bannerHolder = (AdvertisingViewHolder) holder;
            bannerHolder.setData(mContext, position);
        } else {
            SelectionViewHolder bannerHolder = (SelectionViewHolder) holder;
            bannerHolder.setData(mContext, position);
        }
    }

    @Override
    public int getItemCount() {

        return data.getCountlc() + 2;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {

            return TYPE_ONE;//轮播图

        } else if (position == 1) {

            return TYPE_THREE;//gridview分类

        } else if (data.getList().get(position - 2).getCount() == 3) {//精选商品（3张图）

            return TYPE_SIX;

        } else if (data.getList().get(position - 2).getCount() == 2) {//（2张图）

            return TYPE_FOUR;//热门推荐

        } else if (data.getList().get(position - 2).getCount() == 5) {//玩转3C(5张图)

            return TYPE_SEVEN;

        } else if (data.getList().get(position - 2).getCount() == 4) {//时尚服饰(4张图)

            return TYPE_EIGHT;

        } else {

            return TYPE_FOUR;//2张图
        }
    }

    //轮播图
    private class BannerViewHolder extends RecyclerView.ViewHolder {

        private final Banner banner;

        public BannerViewHolder(View inflate) {
            super(inflate);
            banner = (Banner) inflate.findViewById(R.id.banner);
        }

        public void setData(FragmentActivity mContext, int position) {

            final List<IndexHomeBean.CarouselBean> carousel = data.getCarousel();

            if (mImages != null && mImages.size() > 0) {
                mImages.clear();
            }

            for (int i = 0; i < carousel.size(); i++) {
                String file_path = ApiUrlConstant.API_IMG_URL + carousel.get(i).getFile_path();
                mImages.add(file_path);
            }

            banner.setImageLoader(new GlideImageLoader())
                    .setImages(mImages)
                    .setDelayTime(5000)
                    .start();

            //设置点击事件
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {

                    if (bannerListener != null) {

                        bannerListener.bannerOnclickListener(banner, position, carousel.get(position).getUrl());
                    }
                }
            });
        }
    }

    //通知公告
    private class NoticeViewHolder extends RecyclerView.ViewHolder {

        private final NoticTextView index_info;

        public NoticeViewHolder(View inflate) {

            super(inflate);
            index_info = (NoticTextView) inflate.findViewById(R.id.index_info);
        }

        public void setData(FragmentActivity mContext, final int position) {

            index_info.setText("女人衣柜不能没有的6种时髦衣服....");

            //设置点击事件
            index_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (noticeOnclickListener != null) {

                        noticeOnclickListener.noticeOnclickListener(index_info, position);
                    }
                }
            });
        }
    }

    //生活服务类数据
    private class O2oViewHolder extends RecyclerView.ViewHolder {

        private final NoScrollGridView gridview;

        public O2oViewHolder(View inflate) {
            super(inflate);
            gridview = (NoScrollGridView) inflate.findViewById(R.id.gridview);
        }

        public void setData(FragmentActivity mContext, int position) {

            //设置适配器
            O2oMenueAdapter o2oMenueAdapter = new O2oMenueAdapter(mContext, data.getCategory());
            gridview.setAdapter(o2oMenueAdapter);

            //设置点击事件
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    //发消息去分类的界面
                    EventBus.getDefault().post(new MainEvent(1));
                    EventBus.getDefault().post(new BrandEvent(data.getCategory().get(position).getId()));
                }
            });
        }
    }

    //精选商品
    private class SelectionViewHolder extends RecyclerView.ViewHolder {

        private final NoScrollGridView gridview;

        public SelectionViewHolder(View inflate) {

            super(inflate);
            gridview = (NoScrollGridView) inflate.findViewById(R.id.gridview);

        }

        public void setData(FragmentActivity mContext, int position) {

            //设置适配器
//            IndexSelectionAdapter mAdapter = new IndexSelectionAdapter(mContext, data, groups);
//            gridview.setAdapter(mAdapter);


            //设置商品的点击事件
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if (selectionOnclickListener != null) {

                        selectionOnclickListener.selectionOnclickListener(view, position);
                    }
                }
            });
        }
    }


    //精选商品
    public class SelectionsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.selections_more)
        TextView selectionsMore;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.iv_one)
        ImageView ivOne;
        @BindView(R.id.tv_two_up)
        TextView tvTwoUp;
        @BindView(R.id.tv_two_up_desc)
        TextView tvTwoUpDesc;
        @BindView(R.id.iv_two)
        ImageView ivTwo;
        @BindView(R.id.tv_two_down)
        TextView tvTwoDown;
        @BindView(R.id.tv_two_desc)
        TextView tvTwoDesc;
        @BindView(R.id.iv_two_down)
        ImageView ivTwoDown;
        @BindView(R.id.tv_nick)
        TextView tvNick;
        @BindView(R.id.iv_icon)
        ImageView ivIcon;

        SelectionsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setData(final FragmentActivity mContext, int position) {

            IndexHomeBean.ListBean listBean = data.getList().get(position - 2);

            tvNick.setText(listBean.getName());
            String file_path = ApiUrlConstant.API_IMG_URL + listBean.getFile_path();
            GlideImageLoader glideImageLoader = new GlideImageLoader();
            glideImageLoader.displayImage(mContext, file_path, ivIcon);

            final List<IndexHomeBean.ListBean.GoodsBean> goods = listBean.getGoods();

            for (int i = 0; i < goods.size(); i++) {

                int finalI = i;
                if (goods.get(i).getPosition().equals("1")) {
                    glideImageLoader.displayImage(mContext, ApiUrlConstant.API_IMG_URL + goods.get(i).getFile_path(), ivOne);
                } else if (goods.get(i).getPosition().equals("2")) {
                    glideImageLoader.displayImage(mContext, ApiUrlConstant.API_IMG_URL + goods.get(i).getFile_path(), ivTwo);
                } else {
                    glideImageLoader.displayImage(mContext, ApiUrlConstant.API_IMG_URL + goods.get(i).getFile_path(), ivTwoDown);
                }
            }

            //设置点击事件
            ivOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                    intent.putExtra("goodsId", goods.get(0).getGoods_id());
                    mContext.startActivity(intent);
                }
            });

            ivTwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                    intent.putExtra("goodsId", goods.get(1).getGoods_id());
                    mContext.startActivity(intent);
                }
            });


            ivTwoDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                    intent.putExtra("goodsId", goods.get(2).getGoods_id());
                    mContext.startActivity(intent);
                }
            });
        }
    }


    //玩转3c
    public class ElectronicViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.selections_more)
        TextView selectionsMore;
        @BindView(R.id.tv_left_title)
        TextView tvLeftTitle;
        @BindView(R.id.tv_left_desc)
        TextView tvLeftDesc;
        @BindView(R.id.iv_left)
        ImageView ivLeft;
        @BindView(R.id.tv_right_title)
        TextView tvRightTitle;
        @BindView(R.id.tv_right_desc)
        TextView tvRightDesc;
        @BindView(R.id.iv_right)
        ImageView ivRight;
        @BindView(R.id.tv_down_title01)
        TextView tvDownTitle01;
        @BindView(R.id.iv_down_01)
        ImageView ivDown01;
        @BindView(R.id.tv_down_title02)
        TextView tvDownTitle02;
        @BindView(R.id.iv_down_02)
        ImageView ivDown02;
        @BindView(R.id.tv_down_title03)
        TextView tvDownTitle03;
        @BindView(R.id.iv_down_03)
        ImageView ivDown03;
        @BindView(R.id.view_gray)
        View viewGray;
        @BindView(R.id.tv_nick)
        TextView tvNick;
        @BindView(R.id.iv_icon)
        ImageView ivIcon;

        ElectronicViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setData(final FragmentActivity mContext, int position) {

            IndexHomeBean.ListBean listBean = data.getList().get(position - 2);
            if (listBean == null) {
                return;
            }
            tvNick.setText(TextUtils.isEmpty(listBean.getName()) ? "" : listBean.getName());
            String file_path = ApiUrlConstant.API_IMG_URL + listBean.getFile_path();
            GlideImageLoader glideImageLoader = new GlideImageLoader();
            glideImageLoader.displayImage(mContext, file_path, ivIcon);
            final List<IndexHomeBean.ListBean.GoodsBean> goods = listBean.getGoods();

            //设置图片数据
            for (int i = 0; i < goods.size(); i++) {

                String file_path1 = ApiUrlConstant.API_IMG_URL + goods.get(i).getFile_path();

                if (goods.get(i).getPosition().equals("1")) {
                    glideImageLoader.displayImage(mContext, file_path1, ivLeft);
                } else if (goods.get(i).getPosition().equals("2")) {
                    glideImageLoader.displayImage(mContext, file_path1, ivRight);
                } else if (goods.get(i).getPosition().equals("3")) {
                    glideImageLoader.displayImage(mContext, file_path1, ivDown01);
                } else if (goods.get(i).getPosition().equals("4")) {
                    glideImageLoader.displayImage(mContext, file_path1, ivDown02);
                } else {
                    glideImageLoader.displayImage(mContext, file_path1, ivDown03);
                }
            }

            //设置点击事件
            ivLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                    intent.putExtra("goodsId", goods.get(0).getGoods_id());
                    mContext.startActivity(intent);
                }
            });

            ivRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                    intent.putExtra("goodsId", goods.get(1).getGoods_id());
                    mContext.startActivity(intent);
                }
            });


            ivDown01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                    intent.putExtra("goodsId", goods.get(2).getGoods_id());
                    mContext.startActivity(intent);
                }
            });

            ivDown02.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                    intent.putExtra("goodsId", goods.get(3).getGoods_id());
                    mContext.startActivity(intent);
                }
            });


            ivDown03.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                    intent.putExtra("goodsId", goods.get(4).getGoods_id());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    //时尚服饰
    public class ClothingViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.selections_more)
        TextView selectionsMore;
        @BindView(R.id.tv_up_title)
        TextView tvUpTitle;
        @BindView(R.id.tv_up_desc)
        TextView tvUpDesc;
        @BindView(R.id.iv_up)
        ImageView ivUp;
        @BindView(R.id.tv_down_title01)
        TextView tvDownTitle01;
        @BindView(R.id.tv_desc_01)
        TextView tvDesc01;
        @BindView(R.id.iv_down_01)
        ImageView ivDown01;
        @BindView(R.id.tv_down_title02)
        TextView tvDownTitle02;
        @BindView(R.id.tv_desc_02)
        TextView tvDesc02;
        @BindView(R.id.iv_down_02)
        ImageView ivDown02;
        @BindView(R.id.tv_down_title03)
        TextView tvDownTitle03;
        @BindView(R.id.tv_desc_03)
        TextView tvDesc03;
        @BindView(R.id.iv_down_03)
        ImageView ivDown03;
        @BindView(R.id.tv_nick)
        TextView tvNick;
        @BindView(R.id.iv_icon)
        ImageView ivIcon;

        ClothingViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setData(final FragmentActivity mContext, int position) {

            IndexHomeBean.ListBean listBean = data.getList().get(position - 2);
            tvNick.setText(listBean.getName());
            String file_path = ApiUrlConstant.API_IMG_URL + listBean.getFile_path();
            GlideImageLoader glideImageLoader = new GlideImageLoader();
            glideImageLoader.displayImage(mContext, file_path, ivIcon);
            final List<IndexHomeBean.ListBean.GoodsBean> goods = listBean.getGoods();

            //设置图片数据
            for (int i = 0; i < goods.size(); i++) {

                String file_path1 = ApiUrlConstant.API_IMG_URL + goods.get(i).getFile_path();

                if (goods.get(i).getPosition().equals("1")) {
                    glideImageLoader.displayImage(mContext, file_path1, ivUp);
                } else if (goods.get(i).getPosition().equals("2")) {
                    glideImageLoader.displayImage(mContext, file_path1, ivDown01);
                } else if (goods.get(i).getPosition().equals("3")) {
                    glideImageLoader.displayImage(mContext, file_path1, ivDown02);
                } else {
                    glideImageLoader.displayImage(mContext, file_path1, ivDown03);
                }
            }

            //设置点击事件
            ivUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                    intent.putExtra("goodsId", goods.get(0).getGoods_id());
                    mContext.startActivity(intent);
                }
            });

            ivDown01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                    intent.putExtra("goodsId", goods.get(1).getGoods_id());
                    mContext.startActivity(intent);
                }
            });

            ivDown02.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                    intent.putExtra("goodsId", goods.get(2).getGoods_id());
                    mContext.startActivity(intent);
                }
            });


            ivDown03.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                    intent.putExtra("goodsId", goods.get(3).getGoods_id());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    //大牌家电
    public class AppliancesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.selections_more)
        TextView selectionsMore;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.iv_one)
        ImageView ivOne;
        @BindView(R.id.tv_two_up)
        TextView tvTwoUp;
        @BindView(R.id.tv_two_up_desc)
        TextView tvTwoUpDesc;
        @BindView(R.id.iv_two)
        ImageView ivTwo;
        @BindView(R.id.tv_two_down)
        TextView tvTwoDown;
        @BindView(R.id.tv_two_desc)
        TextView tvTwoDesc;
        @BindView(R.id.iv_two_down)
        ImageView ivTwoDown;
        @BindView(R.id.view_gray)
        View viewGray;

        AppliancesViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setData(FragmentActivity mContext, int position) {

        }
    }

    //爱吃
    public class Love_EatViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.more)
        TextView more;
        @BindView(R.id.coverflow_view)
        CoverFlowView coverFlowView;
        @BindView(R.id.view_gray)
        View viewGray;
        @BindView(R.id.grid_view)
        NoScrollGridView gridView;
        private List<String> mImages = new ArrayList<>();
        private int colnum;

        Love_EatViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setData(FragmentActivity mContext, int position) {


            int size = 8;
            int length = 180;
            DisplayMetrics dm = new DisplayMetrics();
            mContext.getWindowManager().getDefaultDisplay().getMetrics(dm);
            float density = dm.density;
            int gridviewWidth = (int) (size * (length + 2) * density);
            int itemWidth = (int) (length * density);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridviewWidth,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            gridView.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
            gridView.setColumnWidth(itemWidth); // 设置列表项宽
//            gridView.setHorizontalSpacing(5); // 设置列表项水平间距
            gridView.setStretchMode(GridView.NO_STRETCH);
            colnum = ((getResources().getDisplayMetrics().widthPixels)) / 200;
            gridView.setNumColumns(size); // 设置列数量=列表集合数

//            IndexHotAdapter indexHotAdapter = new IndexHotAdapter(mContext, listBean);
//            gridView.setAdapter(indexHotAdapter);

            //设置点击事件
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if (loveEatOnclickListener != null) {

                        loveEatOnclickListener.loveEatOnclickListener(view, position);
                    }
                }
            });
        }
    }

    //广告banner
    public class AdvertisingViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.banner)
        Banner banner;

        AdvertisingViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setData(FragmentActivity mContext, int position) {

            //不显示标题和指示器
            banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
//
//            banner.setImages(mImages)
//                    .setImageLoader(new GlideImageLoader())
//                    .setDelayTime(3000)
//                    .start();

            //设置点击事件
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {

                    if (advertisingOnclickListener != null) {

                        advertisingOnclickListener.appliancesOnclickListener(banner, position);
                    }
                }
            });
        }
    }

    //热门推荐
    public class HotViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.more)
        TextView more;
        @BindView(R.id.grid_view)
        NoScrollGridView gridView;
        @BindView(R.id.view_gray)
        View viewGray;
        @BindView(R.id.tv_nick)
        TextView tvNick;
        @BindView(R.id.iv_icon)
        ImageView ivIcon;

        HotViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setData(final FragmentActivity mContext, int position) {

            IndexHomeBean.ListBean listBean = data.getList().get(position - 2);
            tvNick.setText(listBean.getName());
            String file_path = ApiUrlConstant.API_IMG_URL + listBean.getFile_path();
            GlideImageLoader glideImageLoader = new GlideImageLoader();
            glideImageLoader.displayImage(mContext, file_path, ivIcon);
            final List<IndexHomeBean.ListBean.GoodsBean> goods = listBean.getGoods();
            if (goods != null) {
                int size = goods.size();
                if (size == 0) {
                    return;
                }
                int length = 180;
                DisplayMetrics dm = new DisplayMetrics();
                mContext.getWindowManager().getDefaultDisplay().getMetrics(dm);
                float density = dm.density;
                int gridviewWidth = (int) (size * (length + 2) * density);
                int itemWidth = (int) (length * density);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridviewWidth,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                gridView.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
                gridView.setColumnWidth(itemWidth); // 设置列表项宽
//            gridView.setHorizontalSpacing(5); // 设置列表项水平间距
                gridView.setStretchMode(GridView.NO_STRETCH);
                int colnum = ((getResources().getDisplayMetrics().widthPixels)) / 200;
                gridView.setNumColumns(size); // 设置列数量=列表集合数

                IndexHotAdapter indexHotAdapter = new IndexHotAdapter(mContext, goods);
                gridView.setAdapter(indexHotAdapter);

                //设置点击事件
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (goods == null) {
                            return;
                        }
                        Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                        intent.putExtra("goodsId", goods.get(position).getGoods_id());
                        mContext.startActivity(intent);
                    }
                });
            }

        }
    }


    public void setBannerListener(BannerOnclickListener bannerListener) {
        this.bannerListener = bannerListener;
    }

    public void setNoticeOnclickListener(NoticeOnclickListener noticeOnclickListener) {
        this.noticeOnclickListener = noticeOnclickListener;
    }

    public void setO2oOnclickListener(O2oOnclickListener o2oOnclickListener) {
        this.o2oOnclickListener = o2oOnclickListener;
    }

    public void setHotOnclickListener(HotOnclickListener hotOnclickListener) {
        this.hotOnclickListener = hotOnclickListener;
    }

    public void setSelectionsOnclickListener(SelectionsOnclickListener selectionsOnclickListener) {
        this.selectionsOnclickListener = selectionsOnclickListener;
    }

    public void setElectronicOnclickListener(ElectronicOnclickListener electronicOnclickListener) {
        this.electronicOnclickListener = electronicOnclickListener;
    }

    public void setClothingOnclickListener(ClothingOnclickListener clothingOnclickListener) {
        this.clothingOnclickListener = clothingOnclickListener;
    }

    public void setAppliancesOnclickListener(AppliancesOnclickListener appliancesOnclickListener) {
        this.appliancesOnclickListener = appliancesOnclickListener;
    }

    public void setLoveEatOnclickListener(LoveEatOnclickListener loveEatOnclickListener) {
        this.loveEatOnclickListener = loveEatOnclickListener;
    }

    public void setSelectionOnclickListener(SelectionOnclickListener selectionOnclickListener) {
        this.selectionOnclickListener = selectionOnclickListener;
    }

    public void setAdvertisingOnclickListener(AppliancesOnclickListener advertisingOnclickListener) {
        this.advertisingOnclickListener = advertisingOnclickListener;
    }
}