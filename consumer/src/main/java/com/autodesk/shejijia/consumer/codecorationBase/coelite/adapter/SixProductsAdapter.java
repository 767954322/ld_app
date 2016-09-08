package com.autodesk.shejijia.consumer.codecorationBase.coelite.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.autodesk.shejijia.consumer.codecorationBase.average.fragment.AverageFragment;
import com.autodesk.shejijia.consumer.codecorationBase.codiy.fragments.DIYFragment;
import com.autodesk.shejijia.consumer.codecorationBase.coelite.fragment.CoEliteFragment;
import com.autodesk.shejijia.consumer.codecorationBase.grandmaster.fragment.GrandMasterFragment;
import com.autodesk.shejijia.consumer.codecorationBase.packages.fragment.PackagesFragment;
import com.autodesk.shejijia.consumer.codecorationBase.studio.fragment.StudioFragment;

import java.util.ArrayList;

/**
 * Created by luchongbin on 16-8-16.
 */
public class SixProductsAdapter extends FragmentStatePagerAdapter {
    private static final int NUM_ITEMS = 6;
    private static final int FRAGMENT_GRANDMASTER_POSITION = 0;
    private static final int FRAGMENT_STUDIO_POSITION = 1;
    private static final int FRAGMENT_COELITE_POSITION = 2;
    private static final int FRAGMENT_AVERAGE_POSITION = 3;
    private static final int FRAGMENT_PACKAGES_POSITION = 4;
    private static final int FRAGMENT_DIY_POSITION = 5;

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
        switch(position) {
            case FRAGMENT_GRANDMASTER_POSITION:
                return new GrandMasterFragment();
            case FRAGMENT_STUDIO_POSITION:
                return new StudioFragment();
            case FRAGMENT_COELITE_POSITION:
                return new CoEliteFragment();
            case FRAGMENT_AVERAGE_POSITION:
                return new AverageFragment();
            case FRAGMENT_PACKAGES_POSITION:
                return new PackagesFragment();
            case FRAGMENT_DIY_POSITION:
                return new DIYFragment();
            default:
                return null; //TODO
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabItems[position];
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
