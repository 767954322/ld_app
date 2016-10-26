package com.autodesk.shejijia.consumer.codecorationBase.coelite.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.autodesk.shejijia.consumer.codecorationBase.average.fragment.AverageFragment;
import com.autodesk.shejijia.consumer.codecorationBase.codiy.fragments.DIYFragment;
import com.autodesk.shejijia.consumer.codecorationBase.coelite.fragment.CoEliteFragment;
import com.autodesk.shejijia.consumer.codecorationBase.grandmaster.fragment.GrandMasterFragment;
import com.autodesk.shejijia.consumer.codecorationBase.packages.fragment.PackagesFragment;
import com.autodesk.shejijia.consumer.codecorationBase.studio.fragment.StudioFragment;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;

/**
 * Created by luchongbin on 16-8-16.
 */
public class SixProductsAdapter extends FragmentStatePagerAdapter {
    private String[]tabItems;
    public SixProductsAdapter(FragmentManager fm) {
        super(fm);
    }

    public SixProductsAdapter(FragmentManager fm ,String[] tabItems) {
        super(fm);
        this.tabItems = tabItems;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch(position) {
            case Constant.FragmentEnum.ZERO:
                fragment = new GrandMasterFragment();
                break;
            case Constant.FragmentEnum.ONE:
                fragment = new StudioFragment();
                break;
            case Constant.FragmentEnum.TWO:
                fragment = new CoEliteFragment();
                break;
            case Constant.FragmentEnum.THREE:
                fragment = new AverageFragment();
                break;
            case Constant.FragmentEnum.FOUR:
                fragment = new PackagesFragment();
                break;
            case Constant.FragmentEnum.FIVE:
                fragment = new DIYFragment();
                break;
            default:
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabItems[position];
    }

    @Override
    public int getCount() {
        return tabItems.length;
    }
}
