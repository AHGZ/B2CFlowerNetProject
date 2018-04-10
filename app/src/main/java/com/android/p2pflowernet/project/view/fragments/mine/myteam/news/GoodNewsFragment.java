package com.android.p2pflowernet.project.view.fragments.mine.myteam.news;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.GoodNewsAdapter;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;

import butterknife.BindView;

/**
 * Created by caishen on 2017/12/1.
 * by--更多喜讯
 */

public class GoodNewsFragment extends KFragment<IGoodNewsView, IGoodNewsPrenter> implements NormalTopBar.normalTopClickListener {

    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.listView)
    ListView listView;
    private String tag = "";//0-云工 1-代理人

    @Override
    public IGoodNewsPrenter createPresenter() {

        return new IGoodNewsPrenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        tag = arguments.getString("tag");
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_goods_news;
    }

    @Override
    public void initData() {

        //设置适配器
        GoodNewsAdapter mAdapter=new GoodNewsAdapter(getActivity());
        listView.setAdapter(mAdapter);

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        normalTop.setTitleText("更多喜讯");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        Utils.setStatusBar(getActivity(), 0, false);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        normalTop.setTopClickListener(this);

        if (tag.equals("0")) {//云工

        } else {//代理人

        }

        initData();
    }

    public static KFragment newIntence(String tag) {

        GoodNewsFragment goodNewsFragment = new GoodNewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tag", tag);
        goodNewsFragment.setArguments(bundle);
        return goodNewsFragment;
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
}
