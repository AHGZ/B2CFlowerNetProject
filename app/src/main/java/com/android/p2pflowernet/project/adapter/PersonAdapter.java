package com.android.p2pflowernet.project.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.callback.AdapterLoader;
import com.android.p2pflowernet.project.callback.ISelect;
import com.android.p2pflowernet.project.callback.ShopCartImp;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.O2oIndexBean;
import com.android.p2pflowernet.project.entity.ShopCart;
import com.android.p2pflowernet.project.event.GoodsaddAminEvent;
import com.android.p2pflowernet.project.event.MessageEvent;
import com.android.p2pflowernet.project.utils.SPUtils;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * @描述:店铺点餐适配器
 * @创建人：zhangpeisen
 * @创建时间：2018/1/3 下午7:44
 * @修改人：zhangpeisen
 * @修改时间：2018/1/3 下午7:44
 * @修改备注：
 * @throws
 */
public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

    private List<O2oIndexBean.ListsBean.GoodsListBean> dataList;
    private Context mContext;
    private Activity mActivity;
    private ImageView buyImg;
    private ShopCart shopCart;
    private ShopCartImp shopCartImp;
    private OnItemClickLitener onItemClickLitener;
    private int selectPosition;


    public PersonAdapter(Context context, List<O2oIndexBean.ListsBean.GoodsListBean> items
            , ShopCart shopCart) {
        this.mContext = context;
        this.dataList = items;
        this.shopCart = shopCart;
        setHasStableIds(true);
    }


    public void setmActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }

    /**
     * 开始动画
     *
     * @param view
     */
    private void startAnim(View view) {
        buyImg = new ImageView(mActivity);
        buyImg.setBackgroundResource(R.mipmap.icon_goods_add_item);// 设置buyImg的图片
        int[] loc = new int[2];
        view.getLocationInWindow(loc);
        int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
        view.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
        EventBus.getDefault().post(new GoodsaddAminEvent(buyImg, startLocation));
    }


    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        this.onItemClickLitener = onItemClickLitener;
    }

    public interface OnItemClickLitener {

        void OnitemCLickLitener(View view, int position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_goods_list, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public long getItemId(int position) {

        return dataList.get(position).hashCode();
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //设置名
        holder.goodsCategoryName.setText(dataList.get(position).getName());
        //设置说明
//        holder.tvGoodsDescription.setText(dataList.get(position).getIntroduce());
        //设置价格
        holder.tvGoodsPrice.setText("¥" + dataList.get(position).getPrice());

        Glide.with(mContext)
                .load(ApiUrlConstant.API_IMG_URL + dataList.get(position).getGoods_img())
                .centerCrop()
                .placeholder(R.mipmap.logo)
                .crossFade()
                .into(holder.ivGoodsImage);

        if (dataList.get(position).getIs_spec() == 0) {
            // 0 无规格
            holder.ivselectxgg.setVisibility(View.GONE);
            holder.ivGoodsAdd.setVisibility(View.VISIBLE);
            int count = 0;
            if (shopCart != null) {
                if (shopCart.getShoppingSingleMap().containsKey(dataList.get(position))) {
                    count = shopCart.getShoppingSingleMap().get(dataList.get(position));
                }
            }
            if (count <= 0) {
                holder.ivGoodsMinus.setAnimation(getHiddenAnimation());
                holder.tvGoodsSelectNum.setAnimation(getHiddenAnimation());
                holder.ivGoodsMinus.setVisibility(View.GONE);
                holder.tvGoodsSelectNum.setVisibility(View.GONE);
            } else {
                holder.ivGoodsMinus.setAnimation(getShowAnimation());
                holder.tvGoodsSelectNum.setAnimation(getShowAnimation());
                holder.ivGoodsMinus.setVisibility(View.VISIBLE);
                holder.tvGoodsSelectNum.setVisibility(View.VISIBLE);
                holder.tvGoodsSelectNum.setText(count + "");
            }

        } else {

            // 1 有规格
            holder.ivselectxgg.setVisibility(View.VISIBLE);
            holder.ivGoodsAdd.setVisibility(View.GONE);
        }

        //加号按钮点击
        holder.ivGoodsAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shopCart.getShoppingAccount() <= 1) {

                    holder.ivGoodsMinus.setAnimation(getShowAnimation());
                    holder.tvGoodsSelectNum.setAnimation(getShowAnimation());
                    holder.ivGoodsMinus.setVisibility(View.VISIBLE);
                    holder.tvGoodsSelectNum.setVisibility(View.VISIBLE);
                }
                // 添加购物车
                if (shopCart.addShoppingSingle(dataList.get(position))) {

                    notifyItemChanged(position);

                    if (shopCartImp != null)
                        shopCartImp.add(shopCart, position);
                }

                startAnim(holder.ivGoodsAdd);
                changeShopCart();
            }
        });

        //减号点击按钮点击
        holder.ivGoodsMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (shopCart.getShoppingAccount() <= 0) {

                    holder.ivGoodsMinus.setAnimation(getHiddenAnimation());
                    holder.tvGoodsSelectNum.setAnimation(getHiddenAnimation());
                    holder.ivGoodsMinus.setVisibility(View.GONE);
                    holder.tvGoodsSelectNum.setVisibility(View.GONE);
                }

                // 移除购物车的数据
                if (shopCart.subShoppingSingle(dataList.get(position))) {
                    notifyItemChanged(position);
                    if (shopCartImp != null)
                        shopCartImp.remove(shopCart, position);
                }
                subShopCart();
            }
        });

        //设置item的点击事件
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onItemClickLitener != null) {

                    onItemClickLitener.OnitemCLickLitener(v, position);
                }
            }
        });


        // 弹出规格
        holder.ivselectxgg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopGsSpec(position, shopCart);
            }
        });
        shopCart.setIndex(position);
    }

    public void PopGsSpec(final int position, final ShopCart shopCart) {

        final AppCompatDialog appCompatDialog = new AppCompatDialog(mContext, R.style.AlertDialogStyle);
        appCompatDialog.setContentView(R.layout.select_o2ogoodsspec_layout);
        appCompatDialog.setCanceledOnTouchOutside(false);
        RecyclerView mSpecRecyclerView = (RecyclerView) appCompatDialog.findViewById(R.id.gsSpec_RecyclerView);
        final TextView singlePriceTv = (TextView) appCompatDialog.findViewById(R.id.singleprice_tv);
        final TextView selectSpecTv = (TextView) appCompatDialog.findViewById(R.id.selectspec_tv);
        ImageView jrGwcadd = (ImageView) appCompatDialog.findViewById(R.id.jrgwc_add);
        ImageView o2oGspeClose = (ImageView) appCompatDialog.findViewById(R.id.o2o_gspecclose_iv);
        O2oIndexBean.ListsBean.GoodsListBean goodsListBean = dataList.get(position);
        if (goodsListBean == null) {
            return;
        }
        if (goodsListBean.getIs_spec() == 0) {
            // 0 无规格
            shopCart.setSpec("0");
        } else {
            // 1 有规格
            shopCart.setSpec(SPUtils.get(mContext, "spec", "") == null ? "" : String.valueOf(SPUtils.get(mContext, "spec", "")));   // 0 无规格
        }
        final List<O2oIndexBean.ListsBean.GoodsListBean.SpecListBean>
                goodsListBeanSpec_list = goodsListBean.getSpec_list();

        if (goodsListBeanSpec_list == null && goodsListBeanSpec_list.isEmpty()) {
            Toast.makeText(mContext, "点餐规格为空", Toast.LENGTH_SHORT).show();
            return;
        }
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        mSpecRecyclerView.setLayoutManager(manager);
        final SelectSpecAdapter selectSpecAdapter = new SelectSpecAdapter();
        selectSpecAdapter.attachRecyclerView(mSpecRecyclerView);
        selectSpecAdapter.setList(goodsListBeanSpec_list);
        selectSpecAdapter.setSelectedMode(ISelect.SINGLE_MODE);

        selectSpecAdapter.setOnItemClickListener(new AdapterLoader.OnItemClickListener<O2oIndexBean.ListsBean.GoodsListBean.SpecListBean>() {
            @Override
            public void onItemClick(View itemView, int position, O2oIndexBean.ListsBean.GoodsListBean.SpecListBean item) {
                selectPosition=position;
                for (O2oIndexBean.ListsBean.GoodsListBean.SpecListBean specListBean : goodsListBeanSpec_list) {
                    specListBean.setSelected(false);
                }
                singlePriceTv.setText(TextUtils.isEmpty(item.getO_spec_price()) ? ""
                        : "¥" + item.getO_spec_price());
                selectSpecTv.setText(TextUtils.isEmpty(item.getGoods_spec()) ? ""
                        : item.getGoods_spec());
                SPUtils.put(mContext, "specprice", TextUtils.isEmpty(item.getO_spec_price()) ? ""
                        : "¥" + item.getO_spec_price());
                SPUtils.put(mContext, "spec", TextUtils.isEmpty(item.getGoods_spec()) ? ""
                        : item.getGoods_spec());
                item.setSelected(true);
                selectSpecAdapter.notifyDataSetChanged();
            }
        });

        // 关闭规格视图
        o2oGspeClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appCompatDialog != null && appCompatDialog.isShowing()) {
                    selectSpecAdapter.getItem(selectPosition).setSelected(false);
                    appCompatDialog.dismiss();
                }
            }
        });

        // 加入购物车
        jrGwcadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (singlePriceTv.getText().toString().trim().equals("")
                        && selectSpecTv.getText().toString().trim().equals("")) {
                    Toast.makeText(mContext, "请选择规格", Toast.LENGTH_SHORT).show();
                } else {
                    if (appCompatDialog != null && appCompatDialog.isShowing()) {
                        // 添加购物车
                        if (shopCart.addShoppingSingle(dataList.get(position))) {
                            notifyItemChanged(position);
                            if (shopCartImp != null)
                                shopCartImp.add(shopCart, position);
                        }
//                        dataList.get(position).setPrice(dataList.get(position).getSpec_list().get(selectPosition).getO_spec_price());
                        changeShopCart();
                        selectSpecAdapter.getItem(selectPosition).setSelected(false);
                        appCompatDialog.dismiss();
                    }
                }
            }
        });
        appCompatDialog.show();
    }

    // 同步购物车和列表数据状态
    public void UpdateOrderDatas() {

        if (shopCart == null) {
            return;
        }
        if (shopCart.getIndex() == -1) {
            return;
        }
        changeShopCart();
        notifyDataSetChanged();
    }

    /**
     * 修改购物车状态(增加)
     */
    private void changeShopCart() {

        EventBus.getDefault().post(new MessageEvent("add", shopCart.getShoppingAccount(),
                shopCart.getShoppingTotalPrice(), shopCart));
    }

    /**
     * 修改购物车状态（减）
     */
    private void subShopCart() {

        EventBus.getDefault().post(new MessageEvent("sub", shopCart.getShoppingAccount(),
                shopCart.getShoppingTotalPrice(), shopCart));
    }

    @Override
    public int getItemCount() {

        return dataList == null ? 0 : dataList.size();
    }

    /**
     * 显示减号的动画
     *
     * @return
     */
    private Animation getShowAnimation() {
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0, 720, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 2f
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }


    /**
     * 隐藏减号的动画
     *
     * @return
     */
    private Animation getHiddenAnimation() {
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0, 720, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 4f
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(1, 0);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }

    public ShopCartImp getShopCartImp() {
        return shopCartImp;
    }

    public void setShopCartImp(ShopCartImp shopCartImp) {
        this.shopCartImp = shopCartImp;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivGoodsImage;
        public TextView goodsCategoryName;
        public TextView tvGoodsDescription;
        public LinearLayout goodsInfo;
        public TextView tvGoodsPrice;
        public TextView tvGoodsIntegral;
        public ImageView ivGoodsMinus;
        public TextView tvGoodsSelectNum;
        public ImageView ivGoodsAdd;
        public ImageView ivselectxgg;
        public final View root;
        private final RelativeLayout rl_item;

        public ViewHolder(View root) {
            super(root);
            ivGoodsImage = (ImageView) root.findViewById(R.id.ivGoodsImage);
            goodsCategoryName = (TextView) root.findViewById(R.id.goodsCategoryName);
            tvGoodsDescription = (TextView) root.findViewById(R.id.tvGoodsDescription);
            goodsInfo = (LinearLayout) root.findViewById(R.id.goodsInfo);
            tvGoodsPrice = (TextView) root.findViewById(R.id.tvGoodsPrice);
            tvGoodsIntegral = (TextView) root.findViewById(R.id.tvGoodsIntegral);
            ivGoodsMinus = (ImageView) root.findViewById(R.id.ivGoodsMinus);
            tvGoodsSelectNum = (TextView) root.findViewById(R.id.tvGoodsSelectNum);
            ivGoodsAdd = (ImageView) root.findViewById(R.id.ivGoodsAdd);
            ivselectxgg = (ImageView) root.findViewById(R.id.ivselectxgg);
            rl_item = (RelativeLayout) root.findViewById(R.id.rl_item);
            this.root = root;
        }
    }
}
