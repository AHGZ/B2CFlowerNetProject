package com.android.p2pflowernet.project.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.android.p2pflowernet.project.mvp.KFragment;

import java.util.List;

;

/**
 * Created by heguozhong on 2018/1/5/005.
 * 展示评价内容的viewpager的适配器
 */

public class EvaluatViewpagerAdapter extends FragmentStatePagerAdapter {
    private String[] tabName=new String[]{"全部 (999)","晒图 (889)","低分 (110)"};
    private List<KFragment> fragmentList;

    public EvaluatViewpagerAdapter(FragmentManager fm) {
        super(fm);
    }


    public void setFramentData(List<KFragment> fragmentList) {
        this.fragmentList = fragmentList;
        notifyDataSetChanged();
    }

    public void setTitleData(String[] titleArray) {
        this.tabName = titleArray;
        notifyDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabName[position];
    }

    @Override
    public int getCount() {
        return tabName.length;
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
