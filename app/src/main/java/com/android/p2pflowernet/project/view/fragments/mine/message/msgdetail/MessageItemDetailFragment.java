package com.android.p2pflowernet.project.view.fragments.mine.message.msgdetail;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.MessaDetailBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;

import butterknife.BindView;

/**
 * Created by caishen on 2017/12/28.
 * by--查看系统通知，申请通知，返润通知的详情
 */

public class MessageItemDetailFragment extends KFragment<IMessageItemDetailView, IMessageItemDetailPrenter>
        implements NormalTopBar.normalTopClickListener, IMessageItemDetailView {

    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    private String notice_id;
    private String type;
    private ShapeLoadingDialog shapeLoadingDialog;
    private String recelver = "1,2,3,4,5";//店铺app取值12

    public static KFragment newItence(String type, String notice_id) {

        MessageItemDetailFragment messageItemDetailFragment = new MessageItemDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("notice_id", notice_id);
        messageItemDetailFragment.setArguments(bundle);
        return messageItemDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        notice_id = arguments.getString("notice_id");
        type = arguments.getString("type");
    }

    @Override
    public IMessageItemDetailPrenter createPresenter() {
        return new IMessageItemDetailPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_message_item_detail;
    }

    @Override
    public void initData() {

        mPresenter.getNoticeInfo();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        //1-反润 2-物流 3-申请 4-互动 5-系统通知
        if (type.equals("1")) {
            normalTop.setTitleText("反润通知");
        } else if (type.equals("3")) {
            normalTop.setTitleText("申请通知");
        } else {
            normalTop.setTitleText("系统通知");
        }

        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        normalTop.setTopClickListener(this);

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

        initData();
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
    public String getType() {
        return type;
    }

    @Override
    public String getnoticeId() {
        return notice_id;
    }

    @Override
    public void onError(String s) {

        showShortToast(s);
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
    public void onsuccessMessageDe(MessaDetailBean data) {

        if (data != null) {

            tvTitle.setText(data.getInfo().getTitle());
            tvDesc.setText(data.getInfo().getContent());

        }
    }

    @Override
    public String getreceiver() {
        return recelver;
    }
}
