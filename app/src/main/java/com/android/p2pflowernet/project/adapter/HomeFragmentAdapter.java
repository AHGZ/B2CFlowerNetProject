package com.android.p2pflowernet.project.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by caishen on 2017/4/20.
 * ViewPage主页的适配器
 */
public class HomeFragmentAdapter extends FragmentPagerAdapter {

    private final List<Fragment> list_fragment;
    private final List<String> list_Title;


    public HomeFragmentAdapter(FragmentManager supportFragmentManager, List<Fragment> list_fragment,
                               List<String> list_title) {

        super(supportFragmentManager);
        this.list_fragment = list_fragment;
        this.list_Title = list_title;
    }

    @Override
    public Fragment getItem(int position) {

        return list_fragment.get(position);
    }

    @Override
    public int getCount() {

        return list_Title.size();
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {

        return list_Title.get(position % list_Title.size());
    }

}
