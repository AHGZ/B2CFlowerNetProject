package com.android.p2pflowernet.project.view.fragments.affirm.invoice;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.IncoiceContentAdapter;
import com.android.p2pflowernet.project.event.InvoiceEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.ConvertUtils;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.MyListView;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ProtocoMsglDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by caishen on 2017/11/7.
 * by--开发票的页面
 */

public class InvoiceFragment extends KFragment<IInvoiceView, IInvoicePrenter> implements NormalTopBar.normalTopClickListener {


    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.tv_peper_invoice)
    TextView tvPeperInvoice;
    @BindView(R.id.rb_persson)
    RadioButton rbPersson;
    @BindView(R.id.rb_group)
    RadioButton rbGroup;
    @BindView(R.id.tv_invoice_name)
    TextView tvInvoiceName;
    @BindView(R.id.edit_name)
    CustomEditText editName;
    @BindView(R.id.view_gone)
    View viewGone;
    @BindView(R.id.edit_identify)
    CustomEditText editIdentify;
    @BindView(R.id.ll_identify)
    LinearLayout llIdentify;
    @BindView(R.id.my_listView)
    MyListView myListView;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    private String invoice = "";//表头
    private String tax_num = "";//纳税人识别号
    private String userType = "";//类型

    @Override
    public IInvoicePrenter createPresenter() {
        return new IInvoicePrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.invoice_fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        invoice = arguments.getString("invoice");
        tax_num = arguments.getString("tax_num");
        userType = arguments.getString("userType");


    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        Utils.setStatusBar(getActivity(), 0, false);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);

        normalTop.setTitleText("发票信息");
        normalTop.setRightText("发票须知");
        normalTop.setRightTextColor(Color.WHITE);
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setBackground(getResources().getDrawable(R.drawable.app_statusbar_bg));
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setTopClickListener(this);

        //设置发票内容的适配器
        IncoiceContentAdapter mAdapter = new IncoiceContentAdapter(getActivity());
        myListView.setAdapter(mAdapter);

        //设置回调显示数据
        editName.setText(invoice == null ? "" : invoice);
        if (userType != null && userType.equals("1")) {//个人

            rbPersson.setChecked(true);
            rbGroup.setChecked(false);
            llIdentify.setVisibility(View.GONE);

        } else {//团体

            rbGroup.setChecked(true);
            rbPersson.setChecked(false);
            llIdentify.setVisibility(View.VISIBLE);
            editIdentify.setText(tax_num == null ? "" : tax_num);
        }

        //设置发票类型的点击事件
        rbPersson.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    llIdentify.setVisibility(View.GONE);
                    tvInvoiceName.setText("*个人名称:");
                    rbGroup.setChecked(false);
                    viewGone.setVisibility(View.GONE);

                } else {

                    llIdentify.setVisibility(View.VISIBLE);
                    viewGone.setVisibility(View.VISIBLE);
                    tvInvoiceName.setText("*单位名称:");
                }
            }
        });

        //团体类型的点击事件
        rbGroup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    llIdentify.setVisibility(View.VISIBLE);
                    tvInvoiceName.setText("*单位名称:");
                    viewGone.setVisibility(View.VISIBLE);
                    rbPersson.setChecked(false);

                } else {

                    llIdentify.setVisibility(View.GONE);
                    viewGone.setVisibility(View.GONE);
                    tvInvoiceName.setText("*个人名称:");
                }
            }
        });
    }

    public static KFragment newIntence(String invoice, String tax_num, String userType) {

        InvoiceFragment invoiceFragment = new InvoiceFragment();
        Bundle bundle = new Bundle();
        bundle.putString("invoice", invoice);
        bundle.putString("tax_num", tax_num);
        bundle.putString("userType", userType);
        invoiceFragment.setArguments(bundle);
        return invoiceFragment;
    }

    @Override
    public void initData() {

    }

    @Override
    public void onLeftClick(View view) {

        removeFragment();

    }

    @Override
    public void onRightClick(View view) {

        //发票须知的弹窗
        ProtocoMsglDialog protocoMsglDialog = new ProtocoMsglDialog(getActivity()).builder().setTitle("发票须知")
                .setMsg(ConvertUtils.getString(R.string.protocotext)).setNegativeButton("我知道了", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

        protocoMsglDialog.show();
    }

    @Override
    public void onTitleClick(View view) {

    }

    @OnClick(R.id.btn_commit)
    public void onClick() {//确定

        String trim = editName.getText().toString().trim();
        String trim1 = editIdentify.getText().toString().trim();
        if (rbPersson.isChecked()) {//个人

            if (editName.getText().toString().trim().equals("")) {
                showShortToast("个人名称不能为空");
            } else {
                EventBus.getDefault().post(new InvoiceEvent("1", "1", "", trim));
                removeFragment();
            }
        }

        if (rbGroup.isChecked()) {//团体

            if (editName.getText().toString().trim().equals("")) {
                showShortToast("团体名称不能为空");
            } else if (editIdentify.getText().toString().trim().equals("")) {
                showShortToast("纳税人识别号不能为空");
            } else {
                EventBus.getDefault().post(new InvoiceEvent("2", "1", trim1, trim));
                removeFragment();
            }
        }
    }
}
