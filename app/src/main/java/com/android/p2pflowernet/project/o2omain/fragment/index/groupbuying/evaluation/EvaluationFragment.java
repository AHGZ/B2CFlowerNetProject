package com.android.p2pflowernet.project.o2omain.fragment.index.groupbuying.evaluation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.EvaluatViewpagerAdapter;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.o2omain.fragment.index.groupbuying.evaluation.groupevaluationdetails.GroupEvaluationDetailsFragment;
import com.android.p2pflowernet.project.o2omain.fragment.index.groupbuying.evaluation.groupevaluationdetails.GroupEvaluationDetailsFragment2;
import com.android.p2pflowernet.project.o2omain.fragment.index.groupbuying.evaluation.groupevaluationdetails.GroupEvaluationDetailsFragment3;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by heguozhong on 2018/1/5/005.
 * 评论详情首页界面
 */

public class EvaluationFragment extends KFragment<IEvaluationView, IEvaluationPresenter> {

    //选项卡（全部、晒图、低分）
    @BindView(R.id.group_buying_evaluat_tab)
    TabLayout groupBuyingEvaluatTab;
    //显示评价内容的viewpager
    @BindView(R.id.group_buying_evaluat_viewpager)
    ViewPager groupBuyingEvaluatViewpager;
    //标题栏返回按钮
    @BindView(R.id.cate_title_left_iv)
    ImageView cateTitleLeftIv;
    //标题栏文本内容
    @BindView(R.id.cate_title_text)
    TextView cateTitleText;
    //标题栏右侧按钮
    @BindView(R.id.cate_title_right_iv)
    ImageView cateTitleRightIv;
    private List<KFragment> fragmentList = new ArrayList<>();

    public static EvaluationFragment newInstance() {
        Bundle args = new Bundle();
        EvaluationFragment fragment = new EvaluationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public IEvaluationPresenter createPresenter() {
        return new IEvaluationPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.groupbuying_evaluation_details;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        //初始化沉浸式
        Eyes.setStatusBarColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.coloro2o));
        fragmentList.add(GroupEvaluationDetailsFragment.newInstance());
        fragmentList.add(GroupEvaluationDetailsFragment2.newInstance());//有图
        fragmentList.add(GroupEvaluationDetailsFragment3.newInstance());//低分
        //给显示评价内容的viewpager设置适配器
        EvaluatViewpagerAdapter evaluatViewpager = new EvaluatViewpagerAdapter(getActivity().getSupportFragmentManager());
        evaluatViewpager.setFramentData(fragmentList);
        groupBuyingEvaluatViewpager.setAdapter(evaluatViewpager);
        //将tablayout与viewpager关联
        groupBuyingEvaluatTab.setupWithViewPager(groupBuyingEvaluatViewpager);

        //设置标题栏基本信息
        cateTitleText.setText("评价详情");
        cateTitleRightIv.setVisibility(View.INVISIBLE);
        //设置标题栏左侧返回按钮点击事件
        cateTitleLeftIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFragment();
            }
        });
    }
}
