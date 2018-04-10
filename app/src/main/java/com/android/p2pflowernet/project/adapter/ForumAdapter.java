package com.android.p2pflowernet.project.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by zhangkun on 2018/1/18.
 */

public class ForumAdapter extends FragmentPagerAdapter {

//    private String [] titles = {"花返会员","花返代理","花返合伙人","花返云工"};
    private List<String> title;
    private List<Fragment> list;
    public ForumAdapter(FragmentManager fm, List<Fragment> data,List<String> title) {
        super(fm);
        this.list = data;
        this.title = title;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }
}
