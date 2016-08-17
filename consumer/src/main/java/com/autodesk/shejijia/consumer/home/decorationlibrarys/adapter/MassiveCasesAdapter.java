package com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by luchongbin on 16-8-16.
 */
public class MassiveCasesAdapter extends FragmentStatePagerAdapter {
    private String[]titles = {"海量案例","3D案例"};
    private  FragmentManager fm;
    private ArrayList<Fragment> mFragments;
    public MassiveCasesAdapter(FragmentManager fm) {
        super(fm);
    }

    public MassiveCasesAdapter(FragmentManager fm, ArrayList<Fragment> mFragments) {
        super(fm);
        this.fm = fm;
        this.mFragments = mFragments;

    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
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
