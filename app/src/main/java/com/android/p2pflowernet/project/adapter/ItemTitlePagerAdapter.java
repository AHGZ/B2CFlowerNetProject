package com.android.p2pflowernet.project.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.android.p2pflowernet.project.mvp.KFragment;

import java.util.List;

/**
 * item页ViewPager的内容适配器
 */
public class ItemTitlePagerAdapter extends FragmentStatePagerAdapter {
    private String[] titleArray;
    private List<KFragment> fragmentList;

    public ItemTitlePagerAdapter(FragmentManager fm) {
        super(fm);
    }


    public void setFramentData(List<KFragment> fragmentList) {
        this.fragmentList = fragmentList;
        notifyDataSetChanged();
    }

    public void setTitleData(String[] titleArray) {
        this.titleArray = titleArray;
        notifyDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleArray[position];
    }

    @Override
    public int getCount() {
        return titleArray.length;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
    }
}
