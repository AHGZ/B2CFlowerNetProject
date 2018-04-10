package com.android.p2pflowernet.project.view.fragments.brand;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.GoodsRecommendAdapter;
import com.android.p2pflowernet.project.adapter.GoodsSortAdapter;
import com.android.p2pflowernet.project.adapter.GoodsSortLeftAdapter;
import com.android.p2pflowernet.project.entity.BrandClassBean;
import com.android.p2pflowernet.project.entity.BrandScendBean;
import com.android.p2pflowernet.project.event.BrandEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.fragments.goods.goodslist.GoodsListActivity;
import com.android.p2pflowernet.project.view.fragments.mine.message.MessageActivity;
import com.android.p2pflowernet.project.view.fragments.search.SearchActivity;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @描述: 商品分类
 * @创建人：zhangpeisen
 * @创建时间：2017/12/5 上午9:13
 * @修改人：zhangpeisen
 * @修改时间：2017/12/5 上午9:13
 * @修改备注：
 * @throws
 */
public class BrandFragment extends KFragment<IBrandView, IBrandPresenter> implements IBrandView {

    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.tosearch_tv)
    EditText tosearchTv;
    @BindView(R.id.message_linear)
    LinearLayout messageLinear;
    @BindView(R.id.rv_recycler_view_left)
    RecyclerView rv_recycler_view_left;
    @BindView(R.id.rv_recycler_view_right)
    RecyclerView rv_recycler_view_right;
    @BindView(R.id.img_message)
    ImageView img_message;
    @BindView(R.id.rl_search)
    RelativeLayout rlSearch;
    private LinearLayoutManager mLinearLayoutManager;
    private List<String> dataTitle = new ArrayList<>();
    // 进度条
    private ShapeLoadingDialog shapeLoadingDialog;
    private BrandClassBean.FlBean flBean;
    private GoodsSortAdapter goodsSortAdapter;
    private String cateidId = "";
    private List<BrandClassBean.FlBean> fl;
    private GoodsSortLeftAdapter goodsSortLeftAdapter;

    public static BrandFragment newInstance() {

        Bundle args = new Bundle();
        BrandFragment fragment = new BrandFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public IBrandPresenter createPresenter() {

        return new IBrandPresenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.brandhome_sort_fragment;
    }

    @Override
    public void initData() {

        mPresenter.Gflists();
    }


    /**
     * 接收首页的点击事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BrandEvent event) {
        String id = event.getId();
        if (!TextUtils.isEmpty(id)) {
            cateidId = id;
//            mPresenter.Gflists();
            mPresenter.Gslists();
        }
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        Eyes.setStatusBarColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.colorstart));
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(10000)
                .build();

        initData();
        UIUtils.setTouchDelegate(img_message,10);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        rv_recycler_view_left.setLayoutManager(mLinearLayoutManager);
        rv_recycler_view_left.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        final LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_recycler_view_right.setLayoutManager(linearLayoutManager);

        rv_recycler_view_left.setOverScrollMode(View.OVER_SCROLL_NEVER);
        rv_recycler_view_right.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    @OnClick(R.id.img_message)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.img_message:
                startActivity(new Intent(getActivity(),MessageActivity.class));
                break;
        }
    }


    private void scrollToTop(int n) {
        //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
        int firstItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        int lastItem = mLinearLayoutManager.findLastVisibleItemPosition();
        //然后区分情况
        if (n <= firstItem) {
            //当要置顶的项在当前显示的第一个项的前面时
//            rvRecyclerView.scrollToPosition(n);//有bug
            rv_recycler_view_left.smoothScrollBy(0, (rv_recycler_view_left.getChildAt(n - firstItem).getTop() + lastItem) / 2, new LinearInterpolator());
        } else if (n <= lastItem) {
            //当要置顶的项已经在屏幕上显示时
            int top = (rv_recycler_view_left.getChildAt(n - firstItem).getTop() + lastItem) / 2;
            rv_recycler_view_left.smoothScrollBy(0, top, new LinearInterpolator());
//            mRecyclerView.smoothScrollToPosition(n);
        } else {
            //当要置顶的项在当前显示的最后一项的后面时
            rv_recycler_view_left.scrollToPosition(n);

            //这里这个变量是用在RecyclerView滚动监听里面的
//            move = true;
        }
    }


    @Override
    public void showDialog() {
        shapeLoadingDialog.show();
    }

    @Override
    public void hideDialog() {
        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.dismiss();
        }
    }

    @Override
    public void onError(String errorMsg) {

        if (goodsSortAdapter != null) {

            goodsSortAdapter.clearList();
        }

        showShortToast(errorMsg);
    }

    @Override
    public void onSuccess(String message) {

        showShortToast(message);
    }

    @Override
    public void onSuccess(BrandClassBean brandClassBean) {

        if (brandClassBean != null) {
            // 分类菜单列表
            if (brandClassBean.getFl().isEmpty() && brandClassBean.getFl().size() == 0) {
                return;
            }

            fl = brandClassBean.getFl();

            //改变选中状态
            for (int i = 0; i < fl.size(); i++) {

                if (!TextUtils.isEmpty(cateidId)) {

                    if (fl.get(i).getId().equals(cateidId)) {
                        fl.get(i).setChoose(true);
                    } else {
                        fl.get(i).setChoose(false);
                    }
                }
            }

            goodsSortLeftAdapter = new GoodsSortLeftAdapter();
            goodsSortLeftAdapter.attachRecyclerView(rv_recycler_view_left);
            goodsSortLeftAdapter.setOnLeftItemClickListener(new GoodsSortLeftAdapter.OnLeftItemClickListener() {
                @Override
                public void onLeftItemClick(int position) {
                    flBean = fl.get(position);
                    cateidId = flBean.getId();
                    if (flBean == null) {
                        return;
                    }
                    if (position == 0) {//推荐分类

                        if (goodsSortLeftAdapter != null) {
                            //清理推荐分类的数据
                            goodsSortAdapter.clearList();
                        }

                        //获取一级列表数据
                        mPresenter.Gflists();

                    } else {//一级分类列表
                        //获取二级列表数据
                        mPresenter.Gslists();
                    }
                    scrollToTop(position);
                }
            });
            goodsSortLeftAdapter.setList(fl);

            // 分类内容
            if (brandClassBean.getRecommend().isEmpty() && brandClassBean.getRecommend().size() == 0) {
                return;
            }

            GoodsRecommendAdapter goodsrecommendadapter = new GoodsRecommendAdapter();
            goodsrecommendadapter.attachRecyclerView(rv_recycler_view_right);
            goodsrecommendadapter.setList(brandClassBean.getRecommend());

            //设置点击事件
            goodsrecommendadapter.setOnRightItemClickListener(new GoodsRecommendAdapter.OnRightItemClickListener() {
                @Override
                public void onRightItemClick(int position) {
                    Intent intent = new Intent(getActivity(), GoodsListActivity.class);
                    intent.putExtra("id", cateidId);
                    intent.putExtra("tag", "0");//0是直接跳 1搜索跳转页面
                    intent.putExtra("searchName", "");
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onSuccess(final BrandScendBean brandScendBean) {
        if (brandScendBean == null) {
            return;
        }
        goodsSortAdapter = new GoodsSortAdapter();
        goodsSortAdapter.attachRecyclerView(rv_recycler_view_right);
        if (brandScendBean.getFl().isEmpty() && brandScendBean.getFl().size() > 0) {
            return;
        }

        goodsSortAdapter.setList(brandScendBean.getFl());
        goodsSortAdapter.setOnRightItemClickListener(new GoodsSortAdapter.OnRightItemClickListener() {
            @Override
            public void onRightItemClick(int index, int position) {
                Intent intent = new Intent(getActivity(), GoodsListActivity.class);
                intent.putExtra("id", brandScendBean.getFl().get(index).getThree().get(position).getId());
                intent.putExtra("tag", "0");//0是直接跳 1搜索跳转页面
                intent.putExtra("searchName", "");
                startActivity(intent);
            }
        });
    }


    @Override
    public String getCateid() {
        return cateidId;
    }

    /***
     * 友盟统计
     */
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("BrandPage"); //统计页面，"MainScreen"为页面名称，可自定义
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("BrandPage");
    }

    @OnClick(R.id.tosearch_tv)
    public void onClick() {

        //搜索模块
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        intent.putExtra("tag", "4");//0-b2c 1-o2o
        startActivity(intent);
    }
}
