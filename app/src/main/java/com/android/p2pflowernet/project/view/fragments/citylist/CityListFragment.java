package com.android.p2pflowernet.project.view.fragments.citylist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.CommonAdapter;
import com.android.p2pflowernet.project.adapter.HeaderRecyclerAndFooterWrapperAdapter;
import com.android.p2pflowernet.project.adapter.MeituanAdapter;
import com.android.p2pflowernet.project.entity.BaseIndexPinyinBean;
import com.android.p2pflowernet.project.entity.MeiTuanBean;
import com.android.p2pflowernet.project.entity.MeituanHeaderBean;
import com.android.p2pflowernet.project.entity.MeituanTopHeaderBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.DividerItemDecoration;
import com.android.p2pflowernet.project.utils.OnItemClickListener;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.utils.ViewHolder;
import com.android.p2pflowernet.project.view.customview.IndexBar;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.SuspensionDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by caishen on 2017/10/19.
 * by--城市定位列表数据
 */

public class CityListFragment extends KFragment<ICityListView, ICityListPrenter>
        implements NormalTopBar.normalTopClickListener {

    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.indexBar)
    IndexBar indexBar;
    @BindView(R.id.tvSideBarHint)
    TextView tvSideBarHint;
    private static final String TAG = "zxt";
    private MeituanAdapter mAdapter;
    private HeaderRecyclerAndFooterWrapperAdapter mHeaderAdapter;
    private LinearLayoutManager mManager;

    //设置给InexBar、ItemDecoration的完整数据集
    private List<BaseIndexPinyinBean> mSourceDatas;
    //头部数据源
    private List<MeituanHeaderBean> mHeaderDatas;
    //主体部分数据源（城市数据）
    private List<MeiTuanBean> mBodyDatas;
    //悬停吸顶
    private SuspensionDecoration mDecoration;

    /**
     * 右侧边栏导航区域
     */
    private IndexBar mIndexBar;

    /**
     * 显示指示器DialogText
     */
    private TextView mTvSideBarHint;

    @Override
    public ICityListPrenter createPresenter() {
        return new ICityListPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.activity_citylist;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        //初始化沉浸栏
        Utils.setStatusBar(getActivity(), 1, false);
        normalTop.setTitleText("城市列表");
        normalTop.setTopClickListener(this);
        // 扩大事件的点击范围
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        //放大视图的点击事件
        UIUtils.setTouchDelegate(normalTop, 50);
        mRv.setLayoutManager(mManager = new LinearLayoutManager(getActivity()));
        mSourceDatas = new ArrayList<>();
        mHeaderDatas = new ArrayList<>();
        List<String> locationCity = new ArrayList<>();
        locationCity.add("定位中");
        mHeaderDatas.add(new MeituanHeaderBean(locationCity, "定位城市", ""));
        List<String> hotCitys = new ArrayList<>();
        mHeaderDatas.add(new MeituanHeaderBean(hotCitys, "热门城市", ""));
        mSourceDatas.addAll(mHeaderDatas);

        mAdapter = new MeituanAdapter(getActivity(), R.layout.meituan_item_select_city, mBodyDatas);

        mHeaderAdapter = new HeaderRecyclerAndFooterWrapperAdapter(mAdapter) {
            @Override
            protected void onBindHeaderHolder(ViewHolder holder, int headerPos, int layoutId, Object o) {
                switch (layoutId) {

                    case R.layout.meituan_item_header:

                        final MeituanHeaderBean meituanHeaderBean = (MeituanHeaderBean) o;
                        //网格
                        RecyclerView recyclerView = holder.getView(R.id.rvCity);
                        recyclerView.setAdapter(

                                new CommonAdapter<String>(getActivity(), R.layout.meituan_item_header_item, meituanHeaderBean.getCityList()) {
                                    @Override
                                    public void convert(ViewHolder holder, final String cityName) {
                                        holder.setText(R.id.tvName, cityName);
                                        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

//                                                EventBus.getDefault().post(new LocationEvent(cityName));
                                                Toast.makeText(mContext, "cityName:" + cityName, Toast.LENGTH_SHORT).show();
                                                removeFragment();
                                            }
                                        });
                                    }
                                });

                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

                        break;
                    case R.layout.meituan_item_header_top:

                        MeituanTopHeaderBean meituanTopHeaderBean = (MeituanTopHeaderBean) o;
                        holder.setText(R.id.tvCurrent, meituanTopHeaderBean.getTxt());

                        break;
                    default:
                        break;
                }
            }
        };

        mHeaderAdapter.setHeaderView(0, R.layout.meituan_item_header_top, new MeituanTopHeaderBean("当前：上海徐汇"));
        mHeaderAdapter.setHeaderView(1, R.layout.meituan_item_header, mHeaderDatas.get(0));
//        mHeaderAdapter.setHeaderView(2, R.layout.meituan_item_header, mHeaderDatas.get(1));
        mHeaderAdapter.setHeaderView(2, R.layout.meituan_item_header, mHeaderDatas.get(1));
        mRv.setAdapter(mHeaderAdapter);

        //吸顶效果实现
        mRv.addItemDecoration(mDecoration = new SuspensionDecoration(getActivity(), mSourceDatas)
                .setmTitleHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, getResources().getDisplayMetrics()))
                .setColorTitleBg(0xffefefef)
                .setTitleFontSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()))
                .setColorTitleFont(getActivity().getResources().getColor(android.R.color.black))
                .setHeaderViewCount(mHeaderAdapter.getHeaderViewCount() - mHeaderDatas.size()));
        mRv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        //使用indexBar
        mTvSideBarHint = (TextView) view.findViewById(R.id.tvSideBarHint);//HintTextView
        mIndexBar = (IndexBar) view.findViewById(R.id.indexBar);//IndexBar
        mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(mManager)//设置RecyclerView的LayoutManager
                .setHeaderViewCount(mHeaderAdapter.getHeaderViewCount() - mHeaderDatas.size());
        initDatas(getResources().getStringArray(R.array.provinces));

        //设置点击事件
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                MeiTuanBean meiTuanBean = mBodyDatas.get(position);
                if (meiTuanBean == null) {
                    return;
                }

                //发送消息
//                EventBus.getDefault().post(new LocationEvent(meiTuanBean.getCity()));
                removeFragment();
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
    }

    /**
     * 组织数据源
     *
     * @param data
     * @return
     */
    private void initDatas(final String[] data) {

        //延迟两秒 模拟加载数据中....（城市列表的数据来源）
        getActivity().getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {

                mBodyDatas = new ArrayList<>();
                for (int i = 0; i < data.length; i++) {
                    MeiTuanBean cityBean = new MeiTuanBean();
                    cityBean.setCity(data[i]);//设置城市名称
                    mBodyDatas.add(cityBean);
                }


                //先排序
                mIndexBar.getDataHelper().sortSourceDatas(mBodyDatas);
                mAdapter.setDatas(mBodyDatas);
                mHeaderAdapter.notifyDataSetChanged();
                mSourceDatas.addAll(mBodyDatas);
                mIndexBar.setmSourceDatas(mSourceDatas)//设置数据
                        .invalidate();
                mDecoration.setmDatas(mSourceDatas);
            }
        }, 0);

        //延迟两秒加载头部（热门城市数据来源）
        getActivity().getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {

                MeituanHeaderBean header1 = mHeaderDatas.get(0);
                header1.getCityList().clear();
                header1.getCityList().add("上海");

                MeituanHeaderBean header2 = mHeaderDatas.get(1);
                List<String> recentCitys = new ArrayList<>();
                recentCitys.add("南京");
                recentCitys.add("北京");
                recentCitys.add("天津");
                recentCitys.add("普京");
                recentCitys.add("吴京");
                header2.setCityList(recentCitys);

//                MeituanHeaderBean header3 = mHeaderDatas.get(2);
//                List<String> hotCitys = new ArrayList<>();
//                hotCitys.add("上海");
//                hotCitys.add("北京");
//                hotCitys.add("杭州");
//                hotCitys.add("广州");
//                header3.setCityList(hotCitys);

                mHeaderAdapter.notifyItemRangeChanged(1, 2);
            }
        }, 0);
    }


    public static KFragment newInstance() {
        return new CityListFragment();
    }


    @Override
    public void onLeftClick(View view) {

        removeFragment();
    }

    @Override
    public void onRightClick(View view) {

    }

    @Override
    public void onTitleClick(View view) {

    }
}
