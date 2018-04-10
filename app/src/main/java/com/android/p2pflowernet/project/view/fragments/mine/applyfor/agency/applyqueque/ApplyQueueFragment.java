package com.android.p2pflowernet.project.view.fragments.mine.applyfor.agency.applyqueque;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.AgencyApplyQueueAdapter;
import com.android.p2pflowernet.project.entity.AgentQuereBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.SPUtils;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.activity.AgencyActivity;
import com.android.p2pflowernet.project.view.customview.NoticTextView;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @描述:申请排队功能页面
 * @创建人：zhangpeisen
 * @创建时间：2017/11/15 下午4:03
 * @修改人：zhangpeisen
 * @修改时间：2017/11/15 下午4:03
 * @修改备注：
 * @throws
 */
public class ApplyQueueFragment extends KFragment<IApplyQueueView, IApplyQueuePrenter> implements IApplyQueueView {

    @BindView(R.id.im_back)
    // 返回按钮
            ImageView imBack;
    @BindView(R.id.index_info)
    // 通知文本
            NoticTextView indexInfo;
    @BindView(R.id.applyqueue_recyclerview)
    // 排行榜列表
            RecyclerView applyqueueRecyclerview;
    @BindView(R.id.toapply_btn)
    //去申请按钮
            Button toapplyBtn;

    private AgencyApplyQueueAdapter agencyApplyQueueAdapter;
    private String state = "";//3--追加金额  5--去申请
    private String mId;
    private ShapeLoadingDialog shapeLoadingDialog;

    public static ApplyQueueFragment newIntence(String id, String state) {

        Bundle bundle = new Bundle();
        bundle.putString("state", state);
        bundle.putString("id", id);
        ApplyQueueFragment applyQueueFragment = new ApplyQueueFragment();
        applyQueueFragment.setArguments(bundle);
        return applyQueueFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        state = arguments.getString("state");
        mId = arguments.getString("id");
    }

    @Override
    public IApplyQueuePrenter createPresenter() {
        return new IApplyQueuePrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.mine_agencyapplyqueuing_fragment;
    }

    @Override
    public void initData() {


    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        //初始化沉浸式
        Utils.setStatusBar(getActivity(), 2, false);
        //增加控件点击区域
        UIUtils.setTouchDelegate(imBack, 50);

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

        //state( 0 :没有该身份,1:是该身份, 3:排队中, 5:该地区有代理)
        if (state.equals("3")) {
            toapplyBtn.setVisibility(View.GONE);
        } else {
            toapplyBtn.setVisibility(View.VISIBLE);
        }

        mPresenter.AgentQueue();
    }


    @OnClick({R.id.im_back, R.id.toapply_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back:
                // 返回
                removeFragment();

                break;

            case R.id.toapply_btn:

                // 编辑代理人
                Intent intent = new Intent(getActivity(), AgencyActivity.class);
                intent.putExtra("state", state);
                intent.putExtra("id", mId);
                startActivity(intent);

                break;
        }
    }

    @Override
    public String getRegion() {

        String region = SPUtils.get(getActivity(), "region", "").toString();
        return region;
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
    public void onSuccessData(AgentQuereBean data) {

        if (data != null) {

            int count = data.getCount();
            indexInfo.setText("您好,代理人申请排队人数已有" + count + "位");

            List<AgentQuereBean.AqBean> aq = data.getAq();
            applyqueueRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
            agencyApplyQueueAdapter = new AgencyApplyQueueAdapter();
            agencyApplyQueueAdapter.attachRecyclerView(applyqueueRecyclerview);
            agencyApplyQueueAdapter.setList(aq);
        }
    }

    @Override
    public void onError(String message) {

        showShortToast(message);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.AgentQueue();
    }
}
