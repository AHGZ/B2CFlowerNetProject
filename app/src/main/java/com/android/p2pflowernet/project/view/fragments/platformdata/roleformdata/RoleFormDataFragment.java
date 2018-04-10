package com.android.p2pflowernet.project.view.fragments.platformdata.roleformdata;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.RoleFormBean;
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

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by zhangkun on 2018/1/31.
 * 角色组成数据
 */

public class RoleFormDataFragment extends KFragment<IRoleFormDataView, IRoleFormDataPresenter> implements NormalTopBar.normalTopClickListener, IRoleFormDataView {

    @BindView(R.id.normal_top)
    NormalTopBar topBar;

    @BindView(R.id.fragment_roleForm_HorizontalBarChart)
    HorizontalBarChart mBarChart;

    @BindView(R.id.fragment_roleForm_tv_date)
    TextView tvDate;

    private int[] MATERIAL_COLORS = {//分类颜色
            rgb("#68D2AB"), rgb("#F75C57"), rgb("#9C96FF"), rgb("#48B8FA"), rgb("#F4CC4F"), rgb("#FF934A")
    };

    private String[] name = {"会员", "代理人", "合伙人", "云工", "商家", "供货商"};

    private ShapeLoadingDialog shapeLoadingDialog;
    private RoleFormBean.ListBean listBeans;

    public static KFragment newInstance() {
        RoleFormDataFragment fragment = new RoleFormDataFragment();
        return fragment;
    }

    @Override
    public IRoleFormDataPresenter createPresenter() {
        return new IRoleFormDataPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_roleformdata;
    }

    @Override
    public void initData() {

    }

    private void setData(RoleFormBean.ListBean bean) {
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        yVals1.add(new BarEntry(0, Float.parseFloat(bean.getUser_count())));
        yVals1.add(new BarEntry(10, Float.parseFloat(bean.getAgent_count())));
        yVals1.add(new BarEntry(20, Float.parseFloat(bean.getPartner_count())));
        yVals1.add(new BarEntry(30, Float.parseFloat(bean.getStaff_count())));
        yVals1.add(new BarEntry(40, Float.parseFloat(bean.getMerch_count())));
        yVals1.add(new BarEntry(50, Float.parseFloat(bean.getManufac_count())));

        BarDataSet iBarDataSet = new BarDataSet(yVals1, "");
        iBarDataSet.setColors(MATERIAL_COLORS);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(iBarDataSet);

        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        data.setBarWidth(7f);

        mBarChart.setData(data);
        mBarChart.invalidate();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        Eyes.setStatusBarColor(getActivity(), getResources().getColor(R.color.rebateData_tabar_gbColor));
        topBar.setBackgroundColor(getResources().getColor(R.color.rebateData_tabar_gbColor));
        topBar.setTitleTextColor(getResources().getColor(R.color.white));
        topBar.setTitleText("角色组成数据");
        topBar.setLeftImageId(R.mipmap.icon_back_white);
        topBar.setTopClickListener(this);

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
        mBarChart.setMaxVisibleValueCount(60);
        // 双击缩放,默认true
        mBarChart.setDoubleTapToZoomEnabled(false);
        // 绘制区域的背景（默认是一个灰色矩形背景）将绘制，默认false
        mBarChart.setDrawGridBackground(false);
        // 在bar开头结尾两边添加一般bar宽的留白
        mBarChart.setFitBars(true);
        // 数据从下到上动画依次显示*/
        mBarChart.animateY(2500);

        // 获取图例，但是在数据设置给chart之前是不可获取的
        Legend legend = mBarChart.getLegend();
        // 是否绘制图例
        legend.setEnabled(false);

        //X轴
        XAxis xl = mBarChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTypeface(null);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGranularity(10f);
        xl.setTextColor(Color.WHITE);
        xl.setAxisLineColor(Color.WHITE);

        //Y轴设置
        DecimalFormat format = new DecimalFormat("####");
        IAxisValueFormatter custom = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                Log.e("RoleFormData==", value + "");
                String str = "";
                int index = (int) value;
                if (index == 0) {
                    str = name[0];
                } else if (index == 10) {
                    str = name[1];
                } else if (index == 20) {
                    str = name[2];
                } else if (index == 30) {
                    str = name[3];
                } else if (index == 40) {
                    str = name[4];
                } else if (index == 50) {
                    str = name[5];
                }
                return str;
            }
        };
        YAxis yl = mBarChart.getAxisLeft();
        yl.setTypeface(null);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setAxisMinimum(0f);
        xl.setGranularity(10f);
        yl.setLabelCount(6, false);
        yl.setSpaceTop(15f);
        xl.setTextColor(Color.WHITE);
        xl.setAxisLineColor(Color.WHITE);
        xl.setValueFormatter(custom);

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
    public void onSuccess(RoleFormBean data) {
        if (null != data) {
            listBeans = data.getList();
            if (null != listBeans) {
                String str = String.valueOf(listBeans.getTime());
                String strDate = DateUtils.timedate(str);
                tvDate.setText(TextUtils.isEmpty(strDate) ? "" : strDate);
                setData(listBeans);
            }
        }
    }
}
