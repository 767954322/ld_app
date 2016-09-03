package com.autodesk.shejijia.consumer.personalcenter.consumer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * @author luchongbin .
 * @version 1.0 .
 * @date 16-6-7 上午11:22
 * @file RollFragAdapter.java  .
 * @brief 消费者家装订单适配器 .
 */
public class RollFragAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> mFragments;

    public RollFragAdapter(FragmentManager fm) {
        super(fm);
    }

    public RollFragAdapter(FragmentManager fm, ArrayList<Fragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }
}

