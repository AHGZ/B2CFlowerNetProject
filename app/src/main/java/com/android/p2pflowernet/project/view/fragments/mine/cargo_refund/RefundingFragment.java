package com.android.p2pflowernet.project.view.fragments.mine.cargo_refund;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.RefundBean;
import com.android.p2pflowernet.project.event.RefundEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.activity.RefundDetailActivity;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.OrderDetailRecyclerView;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.fragments.mine.orderflow.logistics.info.LogisticsinfoActivity;
import com.android.p2pflowernet.project.view.fragments.mine.orderflow.refund.ApplyforRefundActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

/**
 * @描述: 退换货列表
 * @创建人：zhangpeisen
 * @创建时间：2017/12/15 上午10:43
 * @修改人：zhangpeisen
 * @修改时间：2017/12/15 上午10:43
 * @修改备注：
 * @throws
 */
public class RefundingFragment extends KFragment<IRefundingView, IRefundingPrenter>
        implements NormalTopBar.normalTopClickListener, IRefundingView {

    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.refund_recyclerview)
    OrderDetailRecyclerView refundRecyclerview;
    @BindView(R.id.img_empty)
    ImageView imgEmpty;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    private RefundingAdapter refundingAdapter;
    // 加载进度条
    private ShapeLoadingDialog shapeLoadingDialog;
    private String listsBeanId;

    public static RefundingFragment newIntence() {
        Bundle bundle = new Bundle();
        RefundingFragment refundingFragment = new RefundingFragment();
        refundingFragment.setArguments(bundle);
        return refundingFragment;
    }

    @Override
    public IRefundingPrenter createPresenter() {

        return new IRefundingPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_refunding;
    }

    @Override
    public void initData() {

        mPresenter.refundrecordlists();


    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        normalTop.setTitleText("退换货");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.getLeftImage().setImageResource(R.mipmap.icon_back_white);
        normalTop.setRightImageId(R.mipmap.mine_nomessage_icon);
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        normalTop.setTopClickListener(this);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        Utils.setStatusBar(getActivity(), 0, false);
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();
        //初始化数据
        initData();
    }

    @Override
    public void onLeftClick(View view) {

        removeFragment();
    }

    @Override
    public void onRightClick(View view) {
        //消息

    }

    @Override
    public void onTitleClick(View view) {

    }


    @Override
    public int getPage() {
        return 1;
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
    public void onSuccess(RefundBean refundBean) {
        if (refundBean == null) {

            return;
        }
        final List<RefundBean.ListsBean> refundBeanLists = refundBean.getLists();
        if (refundBeanLists.isEmpty() && refundBeanLists == null) {

            return;
        }
        if (refundBeanLists.size() == 0) {

            return;
        }
        tvEmpty.setVisibility(View.GONE);
        imgEmpty.setVisibility(View.GONE);
        refundingAdapter = new RefundingAdapter(refundBeanLists);
        refundRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        refundRecyclerview.setAdapter(refundingAdapter);
        //设置单个点击事件
        refundingAdapter.setOnItemViewListener(new RefundingAdapter.OnItemClickViewListener() {
            @Override
            public void onItemClick(View view, int position) {
                RefundBean.ListsBean listsBean = refundBeanLists.get(position);
                if (listsBean == null) {
                    return;
                }
                listsBeanId = listsBean.getId();
                Bundle bundle = new Bundle();
                Intent intent = new Intent(getActivity(), RefundDetailActivity.class);
                bundle.putSerializable("listsBean", listsBean);
                bundle.putSerializable("refundid", listsBeanId);
                // 退换货id
                intent.putExtras(bundle);
                startActivity(intent);
//                if (listsBean.getRefund_state().equals("9")) {
//
//                } else if (listsBean.getRefund_state().equals("10")) {
//
//                } else if (listsBean.getRefund_state().equals("9")) {
//
//                } else {
//                }
            }
        });

        refundingAdapter.setOnCancleRefundClick(new RefundingAdapter.OnCancleRefundClickListener() {
            @Override
            public void onCancleRefundClick(View view, int position) {
                RefundBean.ListsBean listsBean = refundBeanLists.get(position);
                if (listsBean == null) {
                    return;
                }
                listsBeanId = listsBean.getId();
                mPresenter.cancelrefund();
            }
        });

        refundingAdapter.setOnAppforArbitrationClickListener(new RefundingAdapter.OnAppforArbitrationClickListener() {
            @Override
            public void onAppforArbitrationClick(View view, int position) {
                RefundBean.ListsBean listsBean = refundBeanLists.get(position);
                if (listsBean == null) {
                    return;
                }
                Intent intent = new Intent(getActivity(), ApplyForArbitrationActivity.class);
                listsBeanId = listsBean.getId();
                intent.putExtra("refundid", listsBeanId);
                startActivity(intent);
            }
        });

        refundingAdapter.setOnApplyForRefundonClickListener(new RefundingAdapter.OnApplyForRefundonClickListener() {
            @Override
            public void onApplyForRefundonClick(View view, int position) {
                RefundBean.ListsBean listsBean = refundBeanLists.get(position);
                if (listsBean == null) {
                    return;
                }
                // 待退款
                Intent intent = new Intent(getActivity(), ApplyforRefundActivity.class);
                // 订单商品表id
                intent.putExtra("ogid", listsBean.getOg_id());
                intent.putExtra("ordernum", listsBean.getOrder_num());
                startActivity(intent);
            }
        });

        refundingAdapter.setOnDetailonClickListener(new RefundingAdapter.OnDetailonClickListener() {
            @Override
            public void onDetailonClick(View view, int position) {
                showShortToast("查看详情");
            }
        });

        refundingAdapter.setOnWlDetailonClick(new RefundingAdapter.OnWlDetailonClickListener() {
            @Override
            public void onWlDetailonClick(View view, int position) {
                RefundBean.ListsBean listsBean = refundBeanLists.get(position);
                if (listsBean == null) {
                    return;
                }
                // 完善物流信息
                Bundle bundle = new Bundle();
                Intent intent = new Intent(getActivity(), LogisticsinfoActivity.class);
                bundle.putSerializable("refundlists", listsBean);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public String getRefundid() {
        return listsBeanId;
    }

    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onCancelSuccess(String message) {
        mPresenter.refundrecordlists();
        showShortToast(message);
        if (refundingAdapter != null) {
            refundingAdapter.notifyDataSetChanged();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RefundEvent event) {
        mPresenter.refundrecordlists();
        if (refundingAdapter != null) {
            refundingAdapter.notifyDataSetChanged();
        }
    }


}
