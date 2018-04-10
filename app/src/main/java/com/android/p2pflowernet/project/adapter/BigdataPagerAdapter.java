package com.android.p2pflowernet.project.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by caishen on 2018/1/31.
 * by--viewPager适配器
 */

public class BigdataPagerAdapter extends FragmentPagerAdapter {
    private final FragmentManager mContext;
    private final ArrayList<Fragment> data;

    public BigdataPagerAdapter(FragmentManager supportFragmentManager, ArrayList<Fragment> list_fragment) {
        super(supportFragmentManager);
        this.mContext = supportFragmentManager;
        this.data = list_fragment;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Fragment getItem(int position) {
        return data.get(position);
    }
}
