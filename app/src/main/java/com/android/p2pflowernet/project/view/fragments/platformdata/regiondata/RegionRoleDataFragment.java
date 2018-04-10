package com.android.p2pflowernet.project.view.fragments.platformdata.regiondata;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.RoleBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.DateUtils;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

/**
 * Created by zhangkun on 2018/1/31.
 * 花返地区角色组成数据
 */

public class RegionRoleDataFragment extends KFragment<IRegionRoleDataView, IRegionRoleDataPresenter> implements
        NormalTopBar.normalTopClickListener, IRegionRoleDataView {

    @BindView(R.id.normal_top)
    NormalTopBar topBar;
    @BindView(R.id.fragment_region_barChart)
    HorizontalBarChart mBarChart;
    @BindView(R.id.fragment_region_tv_date)
    TextView tvDate;

    private ShapeLoadingDialog shapeLoadingDialog;
    private List<RoleBean.ListBean> listBeans;
    private XAxis xAxis;

    @Override
    public IRegionRoleDataPresenter createPresenter() {
        return new IRegionRoleDataPresenter();
    }

    public static KFragment newInstance() {
        RegionRoleDataFragment fragment = new RegionRoleDataFragment();

        return fragment;
    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_region;
    }

    @Override
    public void initData() {

    }

    protected BarData generateBarData(List<RoleBean.ListBean> dataSets) {
        ArrayList<IBarDataSet> sets = new ArrayList<IBarDataSet>();
        //会员
        ArrayList<BarEntry> valueSet1 = new ArrayList<BarEntry>();
        for (int i = 0; i < dataSets.size(); i++) {
            RoleBean.ListBean bean = dataSets.get(i);
            valueSet1.add(new BarEntry(i,Float.parseFloat(bean.getUser_count())));
        }

        //代理商
        ArrayList<BarEntry> valueSet2 = new ArrayList<BarEntry>();
        for (int i = 0; i < dataSets.size(); i++) {
            RoleBean.ListBean bean = dataSets.get(i);
                valueSet2.add(new BarEntry(i,Float.parseFloat(bean.getAgent_count())));
        }

        //合伙人
        ArrayList<BarEntry> valueSet3 = new ArrayList<BarEntry>();
        for (int i = 0; i < dataSets.size(); i++) {
            RoleBean.ListBean bean = dataSets.get(i);
            valueSet3.add(new BarEntry(i,Float.parseFloat(bean.getPartner_count())));
        }

        //云工
        ArrayList<BarEntry> valueSet4 = new ArrayList<BarEntry>();
        for (int i = 0; i < dataSets.size(); i++) {
            RoleBean.ListBean bean = dataSets.get(i);
            valueSet4.add(new BarEntry(i,Float.parseFloat(bean.getStaff_count())));
        }

        //商家
        ArrayList<BarEntry> valueSet5 = new ArrayList<BarEntry>();
        for (int i = 0; i < dataSets.size(); i++) {
            RoleBean.ListBean bean = dataSets.get(i);
            valueSet5.add(new BarEntry(i,Float.parseFloat(bean.getMerch_count())));
        }

        //供货商
        ArrayList<BarEntry> valueSet6 = new ArrayList<BarEntry>();
        for (int i = 0; i < dataSets.size(); i++) {
            RoleBean.ListBean bean = dataSets.get(i);
            valueSet6.add(new BarEntry(i,Float.parseFloat(bean.getManufac_count())));
        }

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "会员");
        barDataSet1.setColor(Color.parseColor("#68D2AB"));

        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "代理商");
        barDataSet2.setColor(Color.parseColor("#F75C57"));

        BarDataSet barDataSet3 = new BarDataSet(valueSet3, "合伙人");
        barDataSet3.setColor(Color.parseColor("#9C96FF"));

        BarDataSet barDataSet4 = new BarDataSet(valueSet4, "云工");
        barDataSet4.setColor(Color.parseColor("#48B8FA"));

        BarDataSet barDataSet5 = new BarDataSet(valueSet5, "商家");
        barDataSet5.setColor(Color.parseColor("#F4CC4F"));

        BarDataSet barDataSet6 = new BarDataSet(valueSet6, "供货商");
        barDataSet6.setColor(Color.parseColor("#FF934A"));

        sets.add(barDataSet1);
        sets.add(barDataSet2);
        sets.add(barDataSet3);
        sets.add(barDataSet4);
        sets.add(barDataSet5);
        sets.add(barDataSet6);

        BarData d = new BarData(sets);
        d.setValueTextSize(10f);

        return d;
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        Eyes.setStatusBarColor(getActivity(), getResources().getColor(R.color.rebateData_tabar_gbColor));
        topBar.setBackgroundColor(getResources().getColor(R.color.rebateData_tabar_gbColor));
        topBar.setTitleTextColor(getResources().getColor(R.color.white));
        topBar.setTitleText("各地区角色组成数据");
        topBar.setLeftImageId(R.mipmap.icon_back_white);
        topBar.setTopClickListener(this);

        listBeans = new ArrayList<>();

        //初始化加载进度条
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .delay(5000)
                .loadText("加载中...")
                .build();

//        initBean();

        initBarChard();
    }

    private void initBean() {
        for (int i = 0; i <10 ; i++) {
            RoleBean.ListBean bean = new RoleBean.ListBean();
            bean.setAgent_count(String.valueOf(new Random().nextInt(1000)));
            bean.setManufac_count(String.valueOf(new Random().nextInt(1000)));
            bean.setMerch_count(String.valueOf(new Random().nextInt(1000)));
            bean.setPartner_count(String.valueOf(new Random().nextInt(1000)));
            bean.setStaff_count(String.valueOf(new Random().nextInt(1000)));
            bean.setUser_count(String.valueOf(new Random().nextInt(1000)));
            bean.setProvince("北京" + i);
            listBeans.add(bean);
        }
    }

    private void initBarChard() {
        // 柱形阴影，一般有值被绘制，但是值到顶部的位置为空，这个方法设置也画这部分，但是性能下降约40%，默认false
        mBarChart.setDrawBarShadow(false);
        // 所有值都绘制在柱形外顶部，而不是柱形内顶部。默认true
        mBarChart.setDrawValueAboveBar(true);
        mBarChart.getDescription().setEnabled(false);
        // 数据点上显示的标签，
        mBarChart.setMaxVisibleValueCount(60);
        // 双击缩放,默认true
        mBarChart.setDoubleTapToZoomEnabled(false);
        // 绘制区域的背景（默认是一个灰色矩形背景）将绘制，默认false
        mBarChart.setDrawGridBackground(false);
        // 在bar开头结尾两边添加一般bar宽的留白
        mBarChart.setFitBars(false);
        // 数据从下到上动画依次显示*/
        mBarChart.animateY(2500);


        // 获取图例，但是在数据设置给chart之前是不可获取的
        Legend legend = mBarChart.getLegend();
        // 是否绘制图例
        legend.setEnabled(false);

        //x坐标轴设置
        xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float v, AxisBase axisBase) {
                int index = (int) v;
                String str = "";
                if (null != listBeans && listBeans.size() > 0) {
                    if (v >= 0 && v <listBeans.size()) {
                        str =  listBeans.get(index).getProvince();
                    }
                }
                return str;
            }
        });

        //Y轴设置
        YAxis yl = mBarChart.getAxisLeft();
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(false);
        yl.setGranularity(1f);
        yl.setStartAtZero(true);
        yl.setDrawLabels(true);

        mPresenter.getRoleData();
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

    public static int rgb(String hex) {
        int color = (int) Long.parseLong(hex.replace("#", ""), 16);
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = (color >> 0) & 0xFF;
        return Color.rgb(r, g, b);
    }

    public void setDatas(ArrayList<BarEntry> yVals, int n) {
        for (int i = n; i < n + 6; n++) {
            yVals.add(new BarEntry(i, new Random().nextInt(300) + 1));
        }
    }

    @Override
    public void onError(String s) {
//        showShortToast(s);
    }

    @Override
    public void showDialog() {
        shapeLoadingDialog.show();
    }

    @Override
    public void hideDialog() {
        if (shapeLoadingDialog.isShowing()) {
            shapeLoadingDialog.dismiss();
        }
    }

    @Override
    public void onSuccess(RoleBean data) {
        if (null != data) {
            listBeans.addAll(data.getList());
            String str = String.valueOf(data.getTime());
            String strDate = DateUtils.timedate(str);
            tvDate.setText(TextUtils.isEmpty(strDate) ? "" : strDate);
            if (null != listBeans && listBeans.size() > 0) {
                mBarChart.setData(generateBarData(listBeans));

                float groupSpace = 0.04f;
                float barSpace = 0.02f;
                float barWidth = 0.14f;
                xAxis.setLabelCount(listBeans.size());
                mBarChart.getBarData().setBarWidth(barWidth);
                mBarChart.getAxisLeft().setAxisMinimum(0);
                mBarChart.groupBars(- 0.5f,groupSpace, barSpace);
            }
        }
    }
}
