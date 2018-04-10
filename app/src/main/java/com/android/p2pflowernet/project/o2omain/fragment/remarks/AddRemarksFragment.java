package com.android.p2pflowernet.project.o2omain.fragment.remarks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.AddRemarksAdapter;
import com.android.p2pflowernet.project.event.RemarksEditTextEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.MyGridView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by heguozhong on 2018/1/12/012.
 * 添加备注主页面
 */

public class AddRemarksFragment extends KFragment<IAddRemarksView, IAddRemarksPresenter> {

    //用来展示口味的gridview布局
    @BindView(R.id.add_remarks_gridview)
    MyGridView addRemarksGridview;

    //标题栏左侧返回按钮
    @BindView(R.id.add_remarks_title_left_iv)
    ImageView addRemarksTitleLeftIv;
    //标题栏文字信息
    @BindView(R.id.add_remarks_title_text)
    TextView addRemarksTitleText;
    //标题栏右侧完成按钮
    @BindView(R.id.add_remarks_title_tv_right)
    TextView addRemarksTitleTvRight;
    //输入口味的输入框
    @BindView(R.id.add_remarks_item_edittext)
    CustomEditText addRemarksItemEdittext;
    //  0/50字
    @BindView(R.id.add_remarks_item_tv_hint)
    TextView addRemarksItemTvHint;
    //不需要发票
    @BindView(R.id.add_remarks_bxyfp)
    LinearLayout addRemarksBxyfp;
    //添加发票抬头
    @BindView(R.id.add_remarks_tjfptt)
    LinearLayout addRemarksTjfptt;
    //不需要发票的imageview
    @BindView(R.id.add_remarks_bxyfp_img)
    ImageView addRemarksBxyfpImg;
    @BindView(R.id.remarks_view1)
    View remarksView1;
    @BindView(R.id.remarks_view2)
    View remarksView2;
    @BindView(R.id.zzkfp_tv)
    TextView zzkfpTv;

    private int isInvoice;

    public static AddRemarksFragment newInstance(int isInvoice) {
        AddRemarksFragment fragment = new AddRemarksFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("isInvoice", isInvoice);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        isInvoice = arguments.getInt("isInvoice");

    }

    @Override
    public IAddRemarksPresenter createPresenter() {
        return new IAddRemarksPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.addremarks;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        //初始化沉浸式
        Eyes.setStatusBarColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.coloro2o));
        //扩大返回按钮点击范围
        UIUtils.setTouchDelegate(addRemarksTitleLeftIv, 50);

        //给装在口味的gridview设置适配器
        AddRemarksAdapter addRemarksAdapter = new AddRemarksAdapter(getActivity());
        addRemarksGridview.setAdapter(addRemarksAdapter);
        //设置gridview的每个item的点击事件
        addRemarksGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView text = (TextView) view.findViewById(R.id.add_remarks_gridview_item_text);
                String itemText = text.getText().toString().trim();
                String editText = addRemarksItemEdittext.getText().toString().trim();
                addRemarksItemEdittext.setText(editText + itemText);
            }
        });

        //监听输入框
        addRemarksItemEdittext.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addRemarksItemTvHint.setText(start + count + "" + "/50");
                if (start + count == 50) {
                    showShortToast("只能添加50个字的备注");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //is_invoice: 0-不支持 1-支持
        if (isInvoice==0){
            zzkfpTv.setText("不支持开发票");
            addRemarksBxyfp.setVisibility(View.GONE);
            addRemarksTjfptt.setVisibility(View.GONE);
            remarksView1.setVisibility(View.GONE);
            remarksView2.setVisibility(View.GONE);
        }

    }

    @OnClick({R.id.add_remarks_title_left_iv, R.id.add_remarks_title_tv_right, R.id.add_remarks_bxyfp, R.id.add_remarks_tjfptt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //标题栏左侧返回按钮
            case R.id.add_remarks_title_left_iv:

                removeFragment();
                break;
            //标题栏右侧完成按钮
            case R.id.add_remarks_title_tv_right:
                String remarksText = addRemarksItemEdittext.getText().toString().trim();
                EventBus.getDefault().post(new RemarksEditTextEvent(remarksText));
                removeFragment();

                break;
            //不需要发票
            case R.id.add_remarks_bxyfp:
                showShortToast("您点击了不需要发票");
                if (addRemarksBxyfpImg.getVisibility() == View.GONE) {
                    addRemarksBxyfpImg.setVisibility(View.VISIBLE);
                } else {
                    addRemarksBxyfpImg.setVisibility(View.GONE);
                }
                break;
            //添加发票抬头
            case R.id.add_remarks_tjfptt:
                showShortToast("您点击了添加发票抬头");
                break;
        }
    }

}
