package com.android.p2pflowernet.project.view.fragments.goods.config;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.GoodsConfigAdapter;
import com.android.p2pflowernet.project.entity.GoodsConfigBean;
import com.android.p2pflowernet.project.mvp.KFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 图文详情里的规格参数的Fragment
 */
public class GoodsConfigFragment extends KFragment<IGoodsConfiglView, IGoodsConfigPresenter> {
    @BindView(R.id.lv_config)
    public ListView lv_config;


    public static GoodsConfigFragment newInstance() {
        Bundle args = new Bundle();
        GoodsConfigFragment fragment = new GoodsConfigFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public IGoodsConfigPresenter createPresenter() {
        return new IGoodsConfigPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_item_config;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        List<GoodsConfigBean> data = new ArrayList<>();
        data.add(new GoodsConfigBean("品牌", "Letv/乐视"));
        data.add(new GoodsConfigBean("型号", "LETV体感-超级枪王"));
        lv_config.setAdapter(new GoodsConfigAdapter(getActivity(), data));
    }
}
