package com.android.p2pflowernet.project.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @描述:引导页
 * @创建人：zhangpeisen
 * @创建时间：2018/1/16 上午10:41
 * @修改人：zhangpeisen
 * @修改时间：2018/1/16 上午10:41
 * @修改备注：
 * @throws
 */
public class GuideViewPagerAdapter extends PagerAdapter {
    private List<View> views;

    public GuideViewPagerAdapter(List<View> views) {
        super();
        this.views = views;
    }

    @Override
    public int getCount() {
        if (views != null) {
            return views.size();
        }
        return 0;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(views.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager) container).addView(views.get(position), 0);
        return views.get(position);
    }
}