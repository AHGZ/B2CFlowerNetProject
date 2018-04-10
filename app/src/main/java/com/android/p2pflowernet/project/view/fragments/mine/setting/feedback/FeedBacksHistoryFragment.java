package com.android.p2pflowernet.project.view.fragments.mine.setting.feedback;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.FeedBackHistoryAdapter;
import com.android.p2pflowernet.project.entity.FeedBackBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by caishen on 2017/11/24.
 * by--
 */

public class FeedBacksHistoryFragment extends KFragment<IFeedBacksHistoryView, IFeedBacksHistoryPrenter>
        implements IFeedBacksHistoryView, FeedBackHistoryAdapter.OnFeedBackReadListener {

    @BindView(R.id.listView)
    ListView listView;
    private ShapeLoadingDialog shapeLoadingDialog;
    private FeedBackHistoryAdapter mAdapter;
    private List<FeedBackBean.FlBean> fl = new ArrayList<>();
    private FeedBackPopupWindow mPop;
    private WindowManager.LayoutParams params;
    private String num = "";

    @Override
    public IFeedBacksHistoryPrenter createPresenter() {

        return new IFeedBacksHistoryPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_feed_back_history;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

        //获取反馈列表数据
        mPresenter.getFeedList();

    }

    public static Fragment newIntence() {

        FeedBacksHistoryFragment feedBacksHistoryFragment = new FeedBacksHistoryFragment();
        Bundle bundle = new Bundle();
        feedBacksHistoryFragment.setArguments(bundle);
        return feedBacksHistoryFragment;
    }

    @Override
    public void onError(String message) {

        showShortToast(message);
    }

    @Override
    public void hideDialog() {

        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.dismiss();
        }
    }

    @Override
    public void showDialog() {

        shapeLoadingDialog.show();
    }

    @Override
    public void onSuccessFeedBacks(FeedBackBean data) {

        if (data != null) {

            fl = data.getFl();

            //设置适配器
            mAdapter = new FeedBackHistoryAdapter(getActivity(), fl);
            listView.setAdapter(mAdapter);

            //设置去阅读的点击事件
            if (mAdapter != null) {

                mAdapter.setOnFeedBackReadListener(this);
            }
        }
    }

    @Override
    public int getPage() {

        return 1;
    }

    @Override
    public void OnFeedBackreadListener(View view, int position) {

        if (fl != null && fl.size() > 0) {

            String type = fl.get(position).getType();
            String login_name = fl.get(position).getLogin_name();
            String reply_content = fl.get(position).getReply_content();
            String content = fl.get(position).getContent();


            if (type.equals("1")) {
                num = "功能建议";
            } else if (type.equals("2")) {
                num = "购买遇到问题";
            } else if (type.equals("3")) {
                num = "性能问题";
            } else if (type.equals("99")) {
                num = "其他";
            }

            mPop = new FeedBackPopupWindow(getActivity(), num, reply_content, content, reply_content);
            mPop.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER, 0, 0);
            params = getActivity().getWindow().getAttributes();

            //当弹出Popupwindow时，背景变半透明
            params.alpha = 0.6f;
            getActivity().getWindow().setAttributes(params);
            //设置Popupwindow关闭监听，当Popupwindow关闭，背景恢复1f
            mPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    params = getActivity().getWindow().getAttributes();
                    params.alpha = 1f;
                    getActivity().getWindow().setAttributes(params);
                }
            });
        }
    }
}
