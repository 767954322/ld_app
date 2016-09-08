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
    private enum FragmentEnum {
        FRAGMENT_GRANDMASTER,
        FRAGMENT_STUDIO,
        FRAGMENT_COELITE,
        FRAGMENT_AVERAGE,
        FRAGMENT_PACKAGES,
        FRAGMENT_DIY
    };

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
        switch(FragmentEnum.values()[position]) {
            case FRAGMENT_GRANDMASTER:
                fragment = new GrandMasterFragment();
                break;
            case FRAGMENT_STUDIO:
                fragment = new StudioFragment();
                break;
            case FRAGMENT_COELITE:
                fragment = new CoEliteFragment();
                break;
            case FRAGMENT_AVERAGE:
                fragment = new AverageFragment();
                break;
            case FRAGMENT_PACKAGES:
                fragment = new PackagesFragment();
                break;
            case FRAGMENT_DIY:
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
        return FragmentEnum.values().length;
    }
}
