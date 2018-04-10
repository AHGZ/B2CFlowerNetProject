package com.android.p2pflowernet.project.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.android.p2pflowernet.project.MainActivity;
import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.GuideViewPagerAdapter;
import com.android.p2pflowernet.project.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述:花返网引导页
 * @创建人：zhangpeisen
 * @创建时间：2018/1/16 上午10:47
 * @修改人：zhangpeisen
 * @修改时间：2018/1/16 上午10:47
 * @修改备注：
 * @throws
 */
public class WelcomeGuideActivity extends Activity implements View.OnClickListener {
    private ViewPager vp;
    private GuideViewPagerAdapter adapter;
    private List<View> views;

    // 引导页图片资源
    private static final int[] pics = {R.layout.guide_view1,
            R.layout.guide_view2, R.layout.guide_view3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        views = new ArrayList<View>();

        // 初始化引导页视图列表
        for (int i = 0; i < pics.length; i++) {
            View view = LayoutInflater.from(this).inflate(pics[i], null);

            if (i == pics.length - 1) {
                view.setTag("enter");
                view.setOnClickListener(this);
            }

            views.add(view);

        }

        vp = (ViewPager) findViewById(R.id.vp_guide);
        adapter = new GuideViewPagerAdapter(views);
        vp.setAdapter(adapter);
        vp.addOnPageChangeListener(new PageChangeListener());

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 如果切换到后台，就设置下次不进入功能引导页
        SPUtils.putBoolean(WelcomeGuideActivity.this, SPUtils.FIRST_OPEN, false);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * 设置当前view
     *
     * @param position
     */
    private void setCurView(int position) {
        if (position < 0 || position >= pics.length) {
            return;
        }
        vp.setCurrentItem(position);
    }

    @Override
    public void onClick(View v) {
        if (v.getTag().equals("enter")) {
            enterMainActivity();
            return;
        }

        int position = (Integer) v.getTag();
        setCurView(position);
    }


    private void enterMainActivity() {
        Intent intent = new Intent(WelcomeGuideActivity.this,
                MainActivity.class);
        startActivity(intent);
        SPUtils.putBoolean(WelcomeGuideActivity.this, SPUtils.FIRST_OPEN, false);
        WelcomeGuideActivity.this.finish();
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int position) {

        }

        @Override
        public void onPageScrolled(int position, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int position) {

        }

    }
}
