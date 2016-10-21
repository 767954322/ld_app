package com.autodesk.shejijia.consumer.personalcenter.recommend.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @User: 蜡笔小新
 * @date: 16-10-21
 * @GitHub: https://github.com/meikoz
 */

public class FragmentTabAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private List<String> mTabTitles;

    public FragmentTabAdapter(FragmentManager fm, List<Fragment> list_fragment, List<String> list_Title) {
        super(fm);
        this.mFragments = list_fragment;
        this.mTabTitles = list_Title;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mTabTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles.get(position % mTabTitles.size());
    }
}
