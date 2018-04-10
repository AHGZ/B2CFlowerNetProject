package com.android.p2pflowernet.project.view.fragments.mine.myteam.agence_office.audit;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.AuditHitoryAdapter;
import com.android.p2pflowernet.project.entity.AuditHistoryBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.actionsheet.DatePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by caishen on 2017/12/1.
 * by--审核历史
 */

public class AuditHistoryFragment extends KFragment<IAuditHistoryView, IAuditPrenter> implements IAuditHistoryView {

    @BindView(R.id.checkhistory_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.im_back)
    ImageView imBack;
    @BindView(R.id.right_date)
    ImageView rightDate;
    @BindView(R.id.taskhistory_date)
    TextView taskhistorydate;
    private AuditHitoryAdapter auditHitoryAdapter;
    String mYear;
    String mMonth;
    // 加载进度条
    private ShapeLoadingDialog shapeLoadingDialog;

    public static KFragment newIntence() {

        AuditHistoryFragment auditHistoryFragment = new AuditHistoryFragment();
        Bundle bundle = new Bundle();
        auditHistoryFragment.setArguments(bundle);
        return auditHistoryFragment;
    }

    @Override
    public IAuditPrenter createPresenter() {
        return new IAuditPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.mine_checkhistory_fragment;
    }

    @Override
    public void initData() {


    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        //初始化沉浸式
        Utils.setStatusBar(getActivity(), 0, false);
        //增加控件点击区域
        UIUtils.setTouchDelegate(imBack, 50);
        UIUtils.setTouchDelegate(rightDate, 50);
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();
        Calendar nowCalendar = Calendar.getInstance();
        mYear = String.valueOf(nowCalendar.get(GregorianCalendar.YEAR));
        mMonth = String.valueOf(nowCalendar.get(GregorianCalendar.MONTH) + 1);
        mPresenter.trialHistory();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        auditHitoryAdapter = new AuditHitoryAdapter();
        auditHitoryAdapter.attachRecyclerView(recyclerView);

    }

    @OnClick({R.id.im_back, R.id.right_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back:
                // 返回
                removeFragment();
                break;
            case R.id.right_date:
                // 切换日期
                SelectProjectDate();
                break;
        }
    }

    @Override
    public String getRegion() {
        return null;
    }

    /**
     * 日期弹窗控件
     */
    public void SelectProjectDate() {
        DatePicker picker = new DatePicker(getActivity(), DatePicker.Mode.YEAR_MONTH);
        picker.setSubmitTextColor(Color.parseColor("#FF2E00"));//确定
        picker.setCancelTextColor(Color.parseColor("#7B838D"));//取消
        picker.setTitleTextColor(Color.parseColor("#656565"));
        picker.setTextColor(Color.parseColor("#333333"));
        Calendar nowCalendar = Calendar.getInstance();
        int nowyear = nowCalendar.get(GregorianCalendar.YEAR);
        int nowmonth = nowCalendar.get(GregorianCalendar.MONTH);
        picker.setSelectedItem(nowyear, nowmonth + 1);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthPickListener() {
            @Override
            public void onDatePicked(String year, String month) {
                taskhistorydate.setText(year + "年" + month + "月");
                mYear = year;
                mMonth = month;
                mPresenter.trialHistory();
            }
        });
        picker.show();
    }

    @Override
    public String getYear() {
        return mYear;
    }

    @Override
    public String getMonth() {
        return mMonth;
    }

    @Override
    public void showDialog() {
        shapeLoadingDialog.show();
    }

    @Override
    public void hideDialog() {
        if (shapeLoadingDialog.isShowing())
            shapeLoadingDialog.dismiss();
    }

    @Override
    public void onError(String errorMsg) {
        showShortToast(errorMsg);
    }

    @Override
    public void onSuccess(String message) {
        showShortToast(message);
    }

    @Override
    public void onSuccess(AuditHistoryBean auditHistoryBean) {
        if (auditHistoryBean == null) {
            return;
        }
        List<AuditHistoryBean.ListBean> auditHistoryBeanList = auditHistoryBean.getList();
        if (auditHistoryBeanList == null && auditHistoryBeanList.isEmpty()) {
            return;
        }
        auditHitoryAdapter.setList(auditHistoryBeanList);
    }
}
