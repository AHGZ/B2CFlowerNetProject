package com.android.p2pflowernet.project.view.fragments.mine.myteam.agence_office.taskhistory;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.AgentHistoryBean;
import com.android.p2pflowernet.project.entity.CloudOfficeHistoryBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.actionsheet.DatePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @描述: 任务历史(代理人, 云工)主页面
 * @创建人：zhangpeisen
 * @创建时间：2017/11/30 下午3:20
 * @修改人：zhangpeisen
 * @修改时间：2017/11/30 下午3:20
 * @修改备注：
 * @throws
 */
public class TaskHistoryFragment extends KFragment<ITaskHistoryView, ITaskHistoryPrenter> implements ITaskHistoryView {

    @BindView(R.id.im_back)
    ImageView imBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.right_date)
    ImageView rightDate;
    @BindView(R.id.taskhistory_date)
    TextView taskhistoryDate;
    @BindView(R.id.allmoney_meun_title)
    TextView allmoneyMeunTitle;
    @BindView(R.id.ordinary_member)
    TextView ordinaryMember;
    @BindView(R.id.partner_aptitudenum)
    TextView partnerAptitudenum;
    @BindView(R.id.nohistory_ly)
    LinearLayout nohistoryLy;
    @BindView(R.id.ticket_one_leftvalue)
    TextView ticketOneLeftvalue;
    @BindView(R.id.ticket_one_rightvalue)
    TextView ticketOneRightvalue;
    @BindView(R.id.ticket_one_ly)
    LinearLayout ticketOneLy;
    @BindView(R.id.ticket_two_leftvalue)
    TextView ticketTwoLeftvalue;
    @BindView(R.id.ticket_two_rightvalue)
    TextView ticketTwoRightvalue;
    @BindView(R.id.ticket_two_ly)
    LinearLayout ticketTwoLy;
    @BindView(R.id.redpackage_value1)
    TextView redpackageValue1;
    @BindView(R.id.redpackage_value2)
    TextView redpackageValue2;
    @BindView(R.id.redpackage_value3)
    TextView redpackageValue3;
    // 0 代表：代理人 1 代表：云工
    int tag = -1;
    String mYear;
    String mMonth;
    // 加载进度条
    private ShapeLoadingDialog shapeLoadingDialog;

    public static TaskHistoryFragment newInstance(int tag) {
        Bundle args = new Bundle();
        TaskHistoryFragment fragment = new TaskHistoryFragment();
        args.putInt("tag", tag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag = getArguments().getInt("tag");
    }

    @Override
    public ITaskHistoryPrenter createPresenter() {
        return new ITaskHistoryPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.mine_taskhistory_fragment;
    }

    @Override
    public void initData() {
        if (tag == -1) {
            return;
        }
        Calendar nowCalendar = Calendar.getInstance();
        mYear = String.valueOf(nowCalendar.get(GregorianCalendar.YEAR));
        mMonth = String.valueOf(nowCalendar.get(GregorianCalendar.MONTH));
        if (tag == 0) {
            // 代理人
            ticketTwoLeftvalue.setText("奖品");
            mPresenter.agenthistory();

        } else {
            // 云工
            ticketTwoRightvalue.setText("底薪");
            mPresenter.cloudhistory();
        }
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        UIUtils.setTouchDelegate(imBack, 50);
        UIUtils.setTouchDelegate(rightDate, 50);
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();
        initData();

    }


    @OnClick({R.id.im_back, R.id.right_date, R.id.nohistory_ly, R.id.ticket_one_ly, R.id.ticket_two_ly})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back:
                // 返回键
                removeFragment();
                break;
            case R.id.right_date:
                // 选择日期
                SelectProjectDate();
                break;
            case R.id.nohistory_ly:
                // 无奖励布局
                break;
            case R.id.ticket_one_ly:

                break;
            case R.id.ticket_two_ly:
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
        picker.setTitleText("选择日期");
        picker.setTextColor(Color.parseColor("#333333"));
        Calendar nowCalendar = Calendar.getInstance();
        int nowyear = nowCalendar.get(GregorianCalendar.YEAR);
        int nowmonth = nowCalendar.get(GregorianCalendar.MONTH);
        picker.setSelectedItem(nowyear, nowmonth + 1);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthPickListener() {
            @Override
            public void onDatePicked(String year, String month) {
                taskhistoryDate.setText(year + "年" + month + "月");
                allmoneyMeunTitle.setText("亲爱的聪明宝贝会员,以下是您" + month + "月份的任务奖励表");
                mYear = year;
                mMonth = month;
                if (tag == 0) {
                    // 代理人
                    mPresenter.agenthistory();
                } else {
                    // 云工
                    mPresenter.cloudhistory();
                }
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
    public void onSuccess(CloudOfficeHistoryBean cloudOfficeHistoryBean) {
        if (cloudOfficeHistoryBean == null) {
            return;
        }
        // 会员数
        ordinaryMember.setText(TextUtils.isEmpty(cloudOfficeHistoryBean.getUsercount()) ? "" : "普通会员(" + cloudOfficeHistoryBean.getUsercount() + ")人");
        // 合伙数
        partnerAptitudenum.setText(TextUtils.isEmpty(cloudOfficeHistoryBean.getPartnercount()) ? "" : "合伙人资质(" + cloudOfficeHistoryBean.getPartnercount() + ")人");
        // 云工奖励
        ticketOneLeftvalue.setText(TextUtils.isEmpty(cloudOfficeHistoryBean.getStaff_reward()) ? "" : cloudOfficeHistoryBean.getStaff_reward());
        // 云工底薪
        ticketTwoLeftvalue.setText(TextUtils.isEmpty(cloudOfficeHistoryBean.getStaff_salary()) ? "" : cloudOfficeHistoryBean.getStaff_salary());
    }

    @Override
    public void onSuccess(AgentHistoryBean agentHistoryBean) {
        if (agentHistoryBean == null) {
            return;
        }
        // 会员数
        ordinaryMember.setText(TextUtils.isEmpty(agentHistoryBean.getUsercount()) ? "" : "普通会员(" + agentHistoryBean.getUsercount() + ")人");
        // 合伙数
        partnerAptitudenum.setText(TextUtils.isEmpty(agentHistoryBean.getPartnercount()) ? "" : "合伙人资质(" + agentHistoryBean.getPartnercount() + ")人");
        // 代理奖励
        ticketOneLeftvalue.setText(TextUtils.isEmpty(agentHistoryBean.getAgent_reward()) ? "" : agentHistoryBean.getAgent_reward());
        // 代理奖品
        ticketTwoLeftvalue.setText(TextUtils.isEmpty(agentHistoryBean.getAgent_prize()) ? "" : agentHistoryBean.getAgent_prize());
    }
}
