package com.android.p2pflowernet.project.view.fragments.platformdata.awarddata;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.BusinessBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.DateUtils;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.fragments.platformdata.rebatedata.DayAxisValueFormatter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhangkun on 2018/1/31.
 * 业务奖励方法数据
 */

public class AwardDataFragment extends KFragment<IAwardDataView, IAwardDataPresenter> implements
        NormalTopBar.normalTopClickListener, IAwardDataView {

    @BindView(R.id.normal_top)
    NormalTopBar topBar;
    @BindView(R.id.fragment_award_barChart)
    BarChart mBarChart;
    @BindView(R.id.fragment_award_tv_date)
    TextView tvDate;

    private ShapeLoadingDialog shapeLoadingDialog;
    private List<BusinessBean.ListBean> listBeans;
    private List<String> names;
    private XAxis xAxis;

    int[] MATERIAL_COLORS = {
            rgb("#FCD94C")
    };

    public static KFragment newInstance() {
        AwardDataFragment fragment = new AwardDataFragment();

        return fragment;
    }

    @Override
    public IAwardDataPresenter createPresenter() {
        return new IAwardDataPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_awarddata;
    }

    @Override
    public void initData() {

    }

//    private void setData( ) {
//        // 下面只有barChart(柱状图)有用
//        List<BarEntry> barEntries = new ArrayList<>();
//        for (int i = 0; i < 6; i++) {
//
//            barEntries.add(new BarEntry(i, new Random().nextInt(1000)));
////            barEntries.add(new BarEntry(i, Float.parseFloat(listBeans.get(i).getAward_count())));
////            names.add(listBeans.get(i).getProvince());
//        }
//        names.add("北京");
//        names.add("上海");
//        names.add("深圳");
//        names.add("广州");
//        names.add("成都");
//        names.add("南京");
//
//        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mBarChart,"Award",names);
//        xAxis.setValueFormatter(xAxisFormatter);
//        BarDataSet iBarDataSet = new BarDataSet(barEntries, "");
//        iBarDataSet.setColors(MATERIAL_COLORS);
//
//        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
//        dataSets.add(iBarDataSet);
//
//        BarData data = new BarData(dataSets);
//        data.setDrawValues(true);
//        data.setValueTextSize(10f);
//        data.setValueTextColor(Color.WHITE);
//        data.setBarWidth(0.9f);
//
//        mBarChart.setData(data);
//    }

    private void setData(List<BusinessBean.ListBean> listBeans) {
        // 下面只有barChart(柱状图)有用
        List<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i < listBeans.size(); i++) {
            barEntries.add(new BarEntry(i, Float.parseFloat(listBeans.get(i).getAward_count())));
            names.add(listBeans.get(i).getProvince());
        }

        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mBarChart, "Award", names);
        xAxis.setValueFormatter(xAxisFormatter);
        BarDataSet iBarDataSet = new BarDataSet(barEntries, "");
        iBarDataSet.setColors(MATERIAL_COLORS);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(iBarDataSet);

        BarData data = new BarData(dataSets);
        data.setDrawValues(true);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.WHITE);
        data.setBarWidth(0.9f);

        mBarChart.setData(data);
        mBarChart.invalidate();
    }


    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        Eyes.setStatusBarColor(getActivity(), getResources().getColor(R.color.rebateData_tabar_gbColor));
        topBar.setBackgroundColor(getResources().getColor(R.color.rebateData_tabar_gbColor));
        topBar.setTitleTextColor(getResources().getColor(R.color.white));
        topBar.setTitleText("业务奖励发放数量");
        topBar.setLeftImageId(R.mipmap.icon_back_white);
        topBar.setTopClickListener(this);

        listBeans = new ArrayList<>();
        names = new ArrayList<>();

        //初始化加载进度条
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .delay(5000)
                .loadText("加载中...")
                .build();

        initBarchard();
    }

    private void initBarchard() {
        // 柱形阴影，一般有值被绘制，但是值到顶部的位置为空，这个方法设置也画这部分，但是性能下降约40%，默认false
        mBarChart.setDrawBarShadow(false);
        // 所有值都绘制在柱形外顶部，而不是柱形内顶部。默认true
        mBarChart.setDrawValueAboveBar(true);
        mBarChart.getDescription().setEnabled(false);
        // 数据点上显示的标签，
        mBarChart.setMaxVisibleValueCount(100);
        // 双击缩放,默认true
        mBarChart.setDoubleTapToZoomEnabled(false);
        // 绘制区域的背景（默认是一个灰色矩形背景）将绘制，默认false
        mBarChart.setDrawGridBackground(false);
        // 不绘制右侧的轴线
        mBarChart.getAxisRight().setEnabled(false);

        //X轴设置
        xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(null);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setAxisLineWidth(1f);
        xAxis.setLabelCount(13);
        xAxis.setAxisLineColor(Color.WHITE);

        //Y轴设置
        final DecimalFormat format = new DecimalFormat("####");
        IAxisValueFormatter custom = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return format.format(value);
            }
        };
        YAxis leftAxis = mBarChart.getAxisLeft();
        leftAxis.setTypeface(null);
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisLineWidth(1f);
        leftAxis.setTextColor(Color.WHITE);  // 标签字体颜色
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setLabelCount(6, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        //图例设置
        Legend legend = mBarChart.getLegend(); // 获取图例，但是在数据设置给chart之前是不可获取的
        legend.setEnabled(false);    // 是否绘制图例

//        setData();

        //设置数据
        mPresenter.getBusinessData();
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
    public void onSuccess(BusinessBean data) {
        if (null != data) {
            String str = String.valueOf(data.getTime());
            String strDate = DateUtils.timedate(str);
            tvDate.setText(TextUtils.isEmpty(strDate) ? "" : strDate);
            List<BusinessBean.ListBean> lists = data.getList();
            if (null != lists && lists.size() > 0) {

                listBeans.addAll(lists);

                for (int i = 0; i < listBeans.size(); i++) {
                    names.add(listBeans.get(i).getProvince());
                }

                setData(listBeans);
            }
        }
    }
}
