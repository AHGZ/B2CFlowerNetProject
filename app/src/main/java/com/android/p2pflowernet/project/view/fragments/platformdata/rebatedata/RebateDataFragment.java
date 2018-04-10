package com.android.p2pflowernet.project.view.fragments.platformdata.rebatedata;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.ProfitBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.DateUtils;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
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
 * Created by zhangkun on 2018/1/30.
 * 收益数据
 */

public class RebateDataFragment extends KFragment<IRebateDataView,RebateDataPresenter> implements
        NormalTopBar.normalTopClickListener,IRebateDataView{

    @BindView(R.id.normal_top)
    NormalTopBar topBar;
    @BindView(R.id.fragment_rebateData_bineChart)
    BarChart mBarChart;
    @BindView(R.id.fragment_rebate_tv_date)
    TextView tvDate;
    private ShapeLoadingDialog shapeLoadingDialog;
    private ProfitBean.ListBean listBean;
    private List<String> name;

    @Override
    public RebateDataPresenter createPresenter() {
        return new RebateDataPresenter();
    }

    public static KFragment newInstance(){
        RebateDataFragment fragment = new RebateDataFragment();

        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_rebatedata;
    }

    @Override
    public void initData() {

        //设置数据
//        setData( );
    }

    private void setData( ProfitBean.ListBean bean) {
        int[] MATERIAL_COLORS = {
                rgb("#68D2AB"), rgb("#F75C57"), rgb("#9C96FF"), rgb("#48B8FA")
        };
        // 下面只有barChart(柱状图)有用
        List<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0, Float.parseFloat(bean.getUser_income())));
        barEntries.add(new BarEntry(1, Float.parseFloat(bean.getAgent_income())));
        barEntries.add(new BarEntry(2, Float.parseFloat(bean.getPartner_income())));
        barEntries.add(new BarEntry(3, Float.parseFloat(bean.getStaff_income())));
        BarDataSet iBarDataSet = new BarDataSet(barEntries, "");
        iBarDataSet.setColors(MATERIAL_COLORS);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(iBarDataSet);

        BarData data = new BarData(dataSets);
        data.setDrawValues(true);
        data.setValueTextSize(10f);
        data.setBarWidth(0.9f);

        mBarChart.setData(data);
        mBarChart.invalidate();
    }

    public static int rgb(String hex) {
        int color = (int) Long.parseLong(hex.replace("#", ""), 16);
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = (color >> 0) & 0xFF;
        return Color.rgb(r, g, b);
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        Eyes.setStatusBarColor(getActivity(),getResources().getColor(R.color.rebateData_tabar_gbColor));
        topBar.setBackgroundColor(getResources().getColor(R.color.rebateData_tabar_gbColor));
        topBar.setTitleTextColor(getResources().getColor(R.color.white));
        topBar.setTitleText("收益数据");
        topBar.setLeftImageId(R.mipmap.icon_back_white);
        topBar.setTopClickListener(this);

        name = new ArrayList<>();
        name.add("会员");
        name.add("代理人");
        name.add("合伙人");
        name.add("云工");

        //初始化加载进度条
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .delay(5000)
                .loadText("加载中...")
                .build();

        initBarChard();
    }

    private void initBarChard() {
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
        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mBarChart,"Rebate",name);
        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(null);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(100);
        xAxis.setAxisLineWidth(1f);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setValueFormatter(xAxisFormatter);

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

        mPresenter.getProfitData();
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
    public void onSuccess(ProfitBean data) {
        if (null != data) {
            listBean = data.getList();
            if (null != listBean) {
                String str = String.valueOf(listBean.getTime());
                String strDate = DateUtils.timedate(str);
                tvDate.setText(TextUtils.isEmpty(strDate) ? "" : strDate);
                setData(listBean);
            }
        }
    }
}
