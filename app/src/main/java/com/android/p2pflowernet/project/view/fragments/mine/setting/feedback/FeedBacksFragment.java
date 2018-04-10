package com.android.p2pflowernet.project.view.fragments.mine.setting.feedback;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.FeedBacksAdapter;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.MyGridView;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by caishen on 2017/11/24.
 * by--意见反馈
 */

public class FeedBacksFragment extends KFragment<IFeedBacksView, IFeedBacksPrenter>
        implements TextWatcher, IFeedBacksView {

    @BindView(R.id.gridview)
    MyGridView gridview;
    @BindView(R.id.et_feedback)
    CustomEditText etFeedback;
    @BindView(R.id.tv_num_text)
    TextView tvNumText;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_time)
    TextView tvTime;
    private boolean islMaxCount;
    private String[] mStr = new String[]{"功能建议", "购买遇到问题", "性能问题", "其他"};
    private ShapeLoadingDialog shapeLoadingDialog;
    private String type = "";

    @Override
    public IFeedBacksPrenter createPresenter() {

        return new IFeedBacksPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_feed_backs;
    }

    @Override
    public void initData() {

        //设置适配器
        FeedBacksAdapter mAdapter = new FeedBacksAdapter(getActivity(), mStr);
        gridview.setAdapter(mAdapter);
        gridview.setAdapter(mAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.setSeclection(position);//传值更新
                mAdapter.notifyDataSetChanged();
                type = mStr[position];
            }
        });
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

        etFeedback.addTextChangedListener(this);
        initData();
    }

    public static Fragment newIntence() {

        FeedBacksFragment feedBacksFragment = new FeedBacksFragment();
        Bundle bundle = new Bundle();
        feedBacksFragment.setArguments(bundle);
        return feedBacksFragment;
    }

    @OnClick({R.id.btn_commit, R.id.tv_phone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_commit://提交

                mPresenter.addFeedBack();

                break;
            case R.id.tv_phone://客服电话

                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if (s.length() > 0) {
            setButtonBackground();
        }
    }

    /**
     * 设置editetxt的字数输入限制
     *
     * @param editable
     */
    @OnTextChanged(value = R.id.et_feedback, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void editTextDetailChange(Editable editable) {
        int detailLength = editable.length();
        tvNumText.setText(detailLength + "/100");
        if (detailLength == 99) {
            islMaxCount = true;
        }
    }

    /**
     * @param
     * @return void    返回类型
     * @throws
     * @Title: setButtonBackground
     * @Description: TODO 设置登录按钮的背景颜色
     */
    private void setButtonBackground() {

        String username = etFeedback.getText().toString().trim();
        if (username.length() > 0) {

            btnCommit.setClickable(true);
            btnCommit.setBackgroundResource(R.drawable.pay_bg);

        } else {

            btnCommit.setClickable(false);
            btnCommit.setBackgroundResource(R.drawable.btn_norml);
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
    public void onError(String message) {

        showShortToast(message);
    }

    @Override
    public String getType() {

        String num = "";
        if (type.equals("功能建议")) {
            num = "1";
        } else if (type.equals("购买遇到问题")) {
            num = "2";
        } else if (type.equals("性能问题")) {
            num = "3";
        } else if (type.equals("其他")) {
            num = "99";
        }

        return num;
    }

    @Override
    public String getContent() {

        return etFeedback.getText().toString().trim();
    }

    @Override
    public void onSuccess(String message) {

        showShortToast(message);
        removeFragment();
    }
}
