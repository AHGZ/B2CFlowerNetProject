package com.android.p2pflowernet.project.view.fragments.mine.wallet.bill;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.BillRecyclerAdapter;
import com.android.p2pflowernet.project.entity.BillAllBean;
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
 * @描述:账单页面
 * @创建人：zhangpeisen
 * @创建时间：2017/11/15 下午4:03
 * @修改人：zhangpeisen
 * @修改时间：2017/11/15 下午4:03
 * @修改备注：
 * @throws
 */
public class BillFragment extends KFragment<IBillView, IBillPrenter> implements IBillView {
    @BindView(R.id.im_back)
    // 返回按钮
            ImageView im_back;

    @BindView(R.id.billdate_select)
    // 选择年月日的值
            TextView billdateSelect;
    @BindView(R.id.bill_allmount)
    // 账单金额
            TextView billAllmount;
    @BindView(R.id.select_billdate)
    // 选择日历控件
            TextView selectBilldate;
    @BindView(R.id.bill_RecyclerView)
    // 账单列表
            RecyclerView billRecyclerView;
    @BindView(R.id.fragment_mine_bill_tv_noData)
    TextView tvEmpty;

    BillRecyclerAdapter billRecyclerAdapter;

    // 加载进度条
    private ShapeLoadingDialog shapeLoadingDialog;
    String mYear;
    String mMonth;

    public static BillFragment newIntence() {

        Bundle bundle = new Bundle();
        BillFragment walletFragment = new BillFragment();
        walletFragment.setArguments(bundle);
        return walletFragment;
    }

    @Override
    public IBillPrenter createPresenter() {
        return new IBillPrenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.mine_bill_fragment;
    }

    @Override
    public void initData() {
        //初始化沉浸式
        Utils.setStatusBar(getActivity(), 2, false);
        //增加控件点击区域
        UIUtils.setTouchDelegate(im_back, 50);
        UIUtils.setTouchDelegate(selectBilldate, 50);
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        Calendar nowCalendar = Calendar.getInstance();
        mYear = String.valueOf(nowCalendar.get(GregorianCalendar.YEAR));
        mMonth = String.valueOf(nowCalendar.get(GregorianCalendar.MONTH));
        int monthes = Integer.parseInt(mMonth) + 1;
        mMonth = monthes + "";
        billdateSelect.setText(mYear + "年" + monthes + "月");
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();
        mPresenter.walletbill();

    }

    @OnClick({R.id.im_back, R.id.select_billdate})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.im_back:
                // 返回按钮
                removeFragment();
                break;
            case R.id.select_billdate:
                // 选择日历
                SelectProjectDate();
                break;
        }
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
        picker.setSelectedItem(nowyear, nowmonth);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthPickListener() {
            @Override
            public void onDatePicked(String year, String month) {
                billdateSelect.setText(year + "年" + month + "月");
//                billRecyclerAdapter.setList(OrderDetailModel.BillList(year, month));
                mYear = year;
                mMonth = month;
                mPresenter.walletbill();
            }
        });
        picker.show();
    }

    @Override
    public String getPage() {
        return "1";
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
    public void onSuccessData(BillAllBean data) {

        if (data != null) {
            //设置提现金额
            billAllmount.setText("提现￥"+data.getWithdrawals());
            List<BillAllBean.ListBean> list = data.getList();

            billRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            billRecyclerAdapter = new BillRecyclerAdapter();
            billRecyclerAdapter.attachRecyclerView(billRecyclerView);
            billRecyclerAdapter.setList(list);
        }
    }
}