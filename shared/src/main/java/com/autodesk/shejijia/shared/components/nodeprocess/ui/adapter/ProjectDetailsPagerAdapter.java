package com.autodesk.shejijia.shared.components.nodeprocess.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import java.util.List;

/**
 * Created by t_xuz on 11/14/16.
 */

public class ProjectDetailsPagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> fragmentList;

    public ProjectDetailsPagerAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
