package com.android.p2pflowernet.project.o2omain.fragment.index.takeout.cate;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.ActivityAdapter;
import com.android.p2pflowernet.project.adapter.CateAdapter;
import com.android.p2pflowernet.project.adapter.CateSelListAdapter;
import com.android.p2pflowernet.project.adapter.SelCateListAdapter;
import com.android.p2pflowernet.project.entity.DistriButionBean;
import com.android.p2pflowernet.project.entity.DropBean;
import com.android.p2pflowernet.project.entity.O2oHomeBean;
import com.android.p2pflowernet.project.entity.TakeCateThreeBean;
import com.android.p2pflowernet.project.entity.TakeCateTwoBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.o2omain.activity.StoreDetailActivity;
import com.android.p2pflowernet.project.o2omain.fragment.index.O2oIndexModel;
import com.android.p2pflowernet.project.utils.SPUtils;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.customview.DropdownButton;
import com.android.p2pflowernet.project.view.customview.PopWinDownUtil;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.fragments.search.SearchActivity;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by heguozhong on 2018/1/2/002.
 * 美食主界面
 */

public class CateFragment extends KFragment<ICateView, ICatePresenter> implements PopWinDownUtil.OnDismissLisener, ICateView {
    @BindView(R.id.cate_classify_tablayout)
    TabLayout cateClassifyTablayout;

    //下拉按钮
    @BindView(R.id.cate_iv_xia)
    ImageView cateIvXia;
    @BindView(R.id.cate_down_view)
    View downView;
    //下拉按钮linearlayout布局
    @BindView(R.id.ll_cate)
    LinearLayout llCate;

    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    //综合排序按钮
    @BindView(R.id.db_goods_list_sort)
    DropdownButton dbGoodsListSort;
    //销量最高文字提示
    @BindView(R.id.tv_goods_list_sales)
    TextView tvGoodsListSales;
    //销量最高图标
    @BindView(R.id.iv_goods_list_sales)
    ImageView ivGoodsListSales;
    //销量最高linearlayout布局
    @BindView(R.id.ll_goods_list_sales)
    LinearLayout llGoodsListSales;
    //距离最近文字提示
    @BindView(R.id.tv_goods_list_price)
    TextView tvGoodsListPrice;
    //距离最近图标
    @BindView(R.id.iv_goods_list_arrow)
    ImageView ivGoodsListArrow;
    //距离最近linearlayout布局
    @BindView(R.id.ll_goods_list_nearby)
    LinearLayout llGoodsListNearby;
    //筛选文字提示
    @BindView(R.id.tv_goods_list_select)
    TextView tvGoodsListSelect;
    //筛选文字图标
    @BindView(R.id.iv_goods_list_sel)
    ImageView ivGoodsListSel;
    //筛选linearlayout布局
    @BindView(R.id.ll_goods_list_sel)
    LinearLayout llGoodsListSel;
    //展示主要内容的recyclerview布局
    @BindView(R.id.cate_recyclerview)
    LRecyclerView cateRecyclerview;
    //标题栏左侧按钮
    @BindView(R.id.cate_title_left_iv)
    ImageView cateTitleLeftIv;
    //标题栏文本信息
    @BindView(R.id.cate_title_text)
    TextView cateTitleText;
    //标题栏右侧按钮
    @BindView(R.id.cate_title_right_iv)
    ImageView cateTitleRightIv;

    private List<O2oHomeBean.ZongheBean> zongheBeans;
    private int count = 10;
    private boolean isLoadingMore = false;
    private List<DistriButionBean.DistriButionsBean> distribution;//筛选数据
    private CateSelListAdapter mAdapter;
    private ShapeLoadingDialog shapeLoadingDialog;
    private PopWinDownUtil popWinDownUtil;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private List<DropBean> sorts;
    private int cate_id;
    private int zcate_id;
    private String sreens = "";//筛选条件
    private int pages = 1;
    private int state = 1;
    private int level = 1;
    private CateAdapter cateAdapter;
    private List<TakeCateThreeBean.ZongheBean> zonghe;
    private List<TakeCateTwoBean.ListBean> list;
    private String searchName;

    public static CateFragment newInstance(String cate_id,int zcate_id,String searchName) {

        Bundle args = new Bundle();
        CateFragment fragment = new CateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ICatePresenter createPresenter() {
        return new ICatePresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_cate;
    }

    @Override
    public void initData() {
        Bundle arguments = getArguments();
        cate_id = Integer.parseInt(arguments.getString("cate_id"));
        searchName = arguments.getString("searchName");
        zcate_id = arguments.getInt("zcate_id");

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        //初始化沉浸式
        Eyes.setStatusBarColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.coloro2o));
        // 扩大事件的点击范围
        UIUtils.setTouchDelegate(cateTitleLeftIv, 50);
        UIUtils.setTouchDelegate(cateTitleRightIv, 50);
        //初始化加载进度条
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .delay(5000)
                .loadText("加载中...")
                .build();
        //初始化综合排序按钮数据
        initData();

        //调用查看外卖美食二级
        mPresenter.getTakeoutCateTwo();

        //调用查看外卖美食三级
        if (TextUtils.isEmpty(searchName) || searchName.equals("")) {
            mPresenter.getTakeoutCateThree();
        } else {//搜索数据
            mPresenter.searchTakeoutCate();
        }

        zongheBeans = new ArrayList<>();
        //设置综合排序的点击事件
        dbGoodsListSort.setOnDropItemSelectListener(new DropdownButton.OnDropItemSelectListener() {
            @Override
            public void onDropItemSelect(int Postion) {

                state = zonghe.get(Postion).getState();
                if (TextUtils.isEmpty(searchName) || searchName.equals("")) {
                    mPresenter.getTakeoutCateThree();
                } else {//搜索数据
                    mPresenter.searchTakeoutCate();
                }
            }
        });

    }

    @OnClick({R.id.cate_title_left_iv, R.id.cate_title_right_iv, R.id.ll_cate, R.id.ll_goods_list_sales, R.id.ll_goods_list_nearby, R.id.ll_goods_list_sel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_cate://顶部分类下拉按钮点击事件
                initSel(view);
                break;

            //销量最高点击事件
            case R.id.ll_goods_list_sales:
                state = 2;
                //改变其他按钮颜色颜色
                tvGoodsListSales.setTextColor(Color.RED);
                tvGoodsListPrice.setTextColor(Color.parseColor("#444444"));
                tvGoodsListSelect.setTextColor(Color.parseColor("#444444"));
                if (TextUtils.isEmpty(searchName) || searchName.equals("")) {
                    mPresenter.getTakeoutCateThree();
                } else {//搜索数据
                    mPresenter.searchTakeoutCate();
                }
                break;
            //距离最近点击事件
            case R.id.ll_goods_list_nearby:
                state = 3;
                tvGoodsListSales.setTextColor(Color.parseColor("#444444"));
                tvGoodsListPrice.setTextColor(Color.RED);
                tvGoodsListSelect.setTextColor(Color.parseColor("#444444"));
                if (TextUtils.isEmpty(searchName) || searchName.equals("")) {
                    mPresenter.getTakeoutCateThree();
                } else {//搜索数据
                    mPresenter.searchTakeoutCate();
                }
                break;
            //筛选排序点击事件
            case R.id.ll_goods_list_sel:
                tvGoodsListSales.setTextColor(Color.parseColor("#444444"));
                tvGoodsListPrice.setTextColor(Color.parseColor("#444444"));
                tvGoodsListSelect.setTextColor(Color.RED);
                initSell(view);
                break;
            //标题左侧返回按钮监听
            case R.id.cate_title_left_iv:
                removeFragment();
                break;
            //标题右侧搜索按钮监听
            case R.id.cate_title_right_iv:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("cate_id", cate_id);//0-b2c 1-o2o 2-o2o外卖
                intent.putExtra("tag", "2");//0-b2c 1-o2o 2-o2o外卖
                intent.putExtra("zcate_id", zcate_id);//0-b2c 1-o2o 2-o2o外卖
                startActivity(intent);
                removeFragment();
                break;
        }
    }

    //筛选按钮弹框
    private void initSell(View v) {
            level = 1;
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dropdown_goods_sel, null);
        ExpandableListView expandableListView = (ExpandableListView) view.findViewById(R.id.ex_listView);
        Button fram_reset_but = (Button) view.findViewById(R.id.fram_reset_but);//重置
        Button fram_ok_but = (Button) view.findViewById(R.id.fram_ok_but);//完成

        //完成
        fram_ok_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(searchName) || searchName.equals("")) {
                    mPresenter.getTakeoutCateThree();
                } else {//搜索数据
                    mPresenter.searchTakeoutCate();
                }

                popWinDownUtil.hide();
            }
        });

        //重置
        fram_reset_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetData();
            }
        });
        if (distribution != null && distribution.size() > 0) {

            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }

        } else {

            distribution = O2oIndexModel.ShopQueue();
        }

        //设置筛选的适配器
        mAdapter = new CateSelListAdapter(getActivity(), distribution);
        expandableListView.setAdapter(mAdapter);
        expandableListView.setGroupIndicator(null); //设置属性 GroupIndicator 去掉向下箭头

        for (int i = 0; i < mAdapter.getGroupCount(); i++) {

            expandableListView.expandGroup(i); //关键步骤4:初始化，将ExpandableListView以展开的方式显示
        }

        //设置选中数据回调
        mAdapter.setOnCheckedClickLitener(new CateSelListAdapter.OnCheckedClickLitener() {
            @Override
            public void onCheckedCLicklitener(View view, int position,List<DistriButionBean.DistriButionsBean> data) {
                String id = "";
                for (int i = 0; i < data.size(); i++) {

                    List<DistriButionBean.DistriButionsBean.DistriBean> distri = data.get(i).getDistri();

                    for (int i1 = 0; i1 < distri.size(); i1++) {

                        if (distri.get(i1).ischoose()) {

                            id += distri.get(i).getId() + ",";
                        }
                    }
                }

                if (!id.equals("") || !id.equals(",")) {

                    sreens = id.toString().substring(0, id.toString().lastIndexOf(","));
                }


                mAdapter.notifyDataSetChanged();
            }
        });


        //添加尾部布局
        View footView = LayoutInflater.from(getActivity()).inflate(R.layout.dropdown_goods_sel_item, null);
        GridView gridview = (GridView) footView.findViewById(R.id.gridview);
        TextView tv_activity = (TextView) footView.findViewById(R.id.tv_activity);
        //设置尾部活动的适配器
        final ActivityAdapter activityAdapter = new ActivityAdapter(getActivity());
        gridview.setAdapter(activityAdapter);

        //点击事件
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                activityAdapter.changeState(position);
            }
        });

//        //添加尾部布局
//        expandableListView.addFooterView(footView);

        popWinDownUtil = new PopWinDownUtil(getActivity(), view, llGoodsListSel);
        popWinDownUtil.setOnDismissListener(this);
        popWinDownUtil.show();

    }

    //重置数据
    private void resetData() {

        sreens = "";
        if (TextUtils.isEmpty(searchName) || searchName.equals("")) {
            mPresenter.getTakeoutCateThree();
        } else {//搜索数据
            mPresenter.searchTakeoutCate();
        }
        for (int i = 0; i < distribution.size(); i++) {

            List<DistriButionBean.DistriButionsBean.DistriBean> distri = distribution.get(i).getDistri();

            for (int i1 = 0; i1 < distri.size(); i1++) {

                distri.get(i1).setIschoose(false);
            }
        }

        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
        popWinDownUtil.hide();
    }

        //下拉按钮弹框
    private void initSel(View v) {
        level=2;
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dropdown_goods_sell, null);
        ExpandableListView expandableListView = (ExpandableListView) view.findViewById(R.id.ex_listView);

        //设置筛选的适配器
        SelCateListAdapter mAdapter = new SelCateListAdapter(getActivity(), list);
        expandableListView.setAdapter(mAdapter);
        expandableListView.setGroupIndicator(null); //设置属性 GroupIndicator 去掉向下箭头
        for (int i = 0; i < mAdapter.getGroupCount(); i++) {
            expandableListView.expandGroup(i); //关键步骤4:初始化，将ExpandableListView以展开的方式显示
        }
        mAdapter.setOnCheckedClickLitener(new SelCateListAdapter.OnCheckedClickLitener() {
            @Override
            public void onCheckedCLicklitener(View view,int groupPosition, int position, List<TakeCateTwoBean.ListBean> data) {
                cate_id = Integer.parseInt(list.get(groupPosition).getSon().get(position).getId());
                if (TextUtils.isEmpty(searchName) || searchName.equals("")) {
                    mPresenter.getTakeoutCateThree();
                } else {//搜索数据
                    mPresenter.searchTakeoutCate();
                }
                popWinDownUtil.hide();
            }
        });
        popWinDownUtil = new PopWinDownUtil(getActivity(), view, downView);
        popWinDownUtil.setOnDismissListener(this);
        popWinDownUtil.show();
    }

//    private int convertDpToPixel(int dp) {
//
//        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
//        return (int) (dp * displayMetrics.density);
//    }

    @Override
    public void onDismiss() {
        if (popWinDownUtil != null && popWinDownUtil.isShowing()) {
            popWinDownUtil.hide();
        }
    }

    @Override
    public void onError(String errorMsg) {
        showShortToast(errorMsg);
    }

    @Override
    public void onSuccessTwo(TakeCateTwoBean takeCateTwoBean) {

        list = takeCateTwoBean.getList();
        for (int i = 0; i < list.size(); i++) {
            cateClassifyTablayout.addTab(cateClassifyTablayout.newTab().setText(list.get(i).getName()));
        }

        //设置默认第一个选中
        for (int i=0;i<takeCateTwoBean.getList().size();i++){
            if (cate_id==takeCateTwoBean.getList().get(i).getId()){
                cateClassifyTablayout.getTabAt(i).select();
            }
        }



        //设置tablayout中被选中条目的点击事件
        cateClassifyTablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                cate_id = list.get(tab.getPosition()).getId();
                if (TextUtils.isEmpty(searchName) || searchName.equals("")) {
                    mPresenter.getTakeoutCateThree();
                } else {//搜索数据
                    mPresenter.searchTakeoutCate();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onSuccessThree(TakeCateThreeBean takeCateThreeBean) {
        zonghe = takeCateThreeBean.getZonghe();
        final List<TakeCateThreeBean.ListBean> list = takeCateThreeBean.getList();
        //全部数据分类
        if(zongheBeans.size()==0){
            for (int i = 0; i < zonghe.size(); i++) {
                String name = zonghe.get(i).getName();
                O2oHomeBean.ZongheBean zongheBean = new O2oHomeBean.ZongheBean();
                zongheBean.setName(name);
                zongheBean.setChoiced(false);
                zongheBeans.add(zongheBean);
            }
            dbGoodsListSort.setData(zongheBeans);
        }


        //获取布局管理者
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        //设置为垂直排列格式
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //recyclerview应用垂直排列格式
        cateRecyclerview.setLayoutManager(linearLayoutManager);
        //设置适配器
        cateAdapter = new CateAdapter(getActivity(), list);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(cateAdapter);
        cateRecyclerview.setAdapter(lRecyclerViewAdapter);
        //禁止下拉刷新
        cateRecyclerview.setPullRefreshEnabled(false);
        cateRecyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                if (cateAdapter != null) {

                    if (count >= cateAdapter.getItemCount()) {

                        isLoadingMore = true;
                        pages += 1;
                        if (TextUtils.isEmpty(searchName) || searchName.equals("")) {
                            mPresenter.getTakeoutCateThree();
                        } else {//搜索数据
                            mPresenter.searchTakeoutCate();
                        }

                    } else {

                        cateRecyclerview.setNoMore(true);
                        showShortToast("没有更多数据了！");
                    }
                }

            }
        });
        cateAdapter.setOnEveryItemClickListener(new CateAdapter.OnEveryItemClickListener() {
            @Override
            public void onItemClick(View view,int position) {
                Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
                intent.putExtra("merch_id",list.get(position).getMerch_id());
                startActivity(intent);
            }
        });
//        //添加分割线
//        cateRecyclerview.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                outRect.bottom = 0;
//                // top bottom left right 对应的值应该是dpi 而不是dp  dpi根据不同手机而不同
//
//                int i = parent.getChildLayoutPosition(view) % 1;//每行n个
//                switch (i) {
//                    case 0://第一个
//                        outRect.left = convertDpToPixel(0);
//                        outRect.right = convertDpToPixel(0);
//                        outRect.bottom = convertDpToPixel(1);
//                        outRect.top = convertDpToPixel(1);
//                        break;
//                }
//            }
//        });
    }


    @Override
    public void onSuccess(String message) {
        showShortToast(message);
        cateAdapter.notifyDataSetChanged();
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
    public String longitude() {
        return String.valueOf(SPUtils.get(getActivity(), "longitude", ""));
    }

    @Override
    public String getpages() {
        return String.valueOf(pages);
    }

    @Override
    public String getsreen() {
        return sreens;
    }

    @Override
    public String getstate() {
        return String.valueOf(state);
    }

    @Override
    public int getcateid() {
        return cate_id;
    }

    @Override
    public int getzcateid() {
        return zcate_id;
    }

    @Override
    public int getlevel() {
        return level;
    }

    @Override
    public String getName() {
        return searchName;
    }

    @Override
    public String latitude() {
        return String.valueOf(SPUtils.get(getActivity(), "latitude", ""));
    }

}
