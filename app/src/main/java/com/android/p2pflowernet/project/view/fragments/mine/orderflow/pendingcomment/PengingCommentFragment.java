package com.android.p2pflowernet.project.view.fragments.mine.orderflow.pendingcomment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.customview.OrderDetailRecyclerView;

import butterknife.BindView;

/**
 * @描述:待评价主页
 * @创建人：zhangpeisen
 * @创建时间：2017/10/30 下午5:40
 * @修改人：zhangpeisen
 * @修改时间：2017/10/30 下午5:40
 * @修改备注：
 * @throws
 */
public class PengingCommentFragment extends KFragment<IPengingcommentView, IPendingcommentPrenter> implements IPengingcommentView {
    @BindView(R.id.ordercomment_recyclerview)
    OrderDetailRecyclerView recyclerView;


    public static PengingCommentFragment newInstance() {
        Bundle args = new Bundle();
        PengingCommentFragment fragment = new PengingCommentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public IPendingcommentPrenter createPresenter() {
        return new IPendingcommentPrenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.mine_orderdetail_pengingcomment_fragment;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

    }

}
