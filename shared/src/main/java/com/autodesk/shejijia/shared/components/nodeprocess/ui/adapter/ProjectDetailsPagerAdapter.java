package com.autodesk.shejijia.shared.components.nodeprocess.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment.ProjectDetailTasksFragment;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_xuz on 11/14/16.
 */

public class ProjectDetailsPagerAdapter extends FragmentStatePagerAdapter {

    private List<List<Task>> mTaskLists;
    private String mAvatarUrl;

    public ProjectDetailsPagerAdapter(FragmentManager fm, String avatarUrl, List<List<Task>> taskLists) {
        super(fm);
        this.mAvatarUrl = avatarUrl;
        this.mTaskLists = taskLists;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public Fragment getItem(int position) {
        return getFragment(position);
    }

    @Override
    public int getCount() {
        return mTaskLists.size();
    }

//    @Override
//    public int getItemPosition(Object object) {
//        return PagerAdapter.POSITION_NONE;
//    }

    @Override
    public int getItemPosition(Object object) {
        ProjectDetailTasksFragment f = (ProjectDetailTasksFragment ) object;
        if (f != null) {
            int postion = f.getArguments().getInt("position", -1);
            if (postion != -1) {
                f.update(mTaskLists.get(postion));
            }
        }
        return super.getItemPosition(object);
    }

    public void updateFragment(List<List<Task>> taskLists) {
        this.mTaskLists = taskLists;
        notifyDataSetChanged();
    }

    private Fragment getFragment(int position) {
        Bundle taskListBundle = new Bundle();
        ArrayList<Task> childTaskArrayList = new ArrayList<>();
        childTaskArrayList.addAll(mTaskLists.get(position));
        taskListBundle.putSerializable(ConstructionConstants.BUNDLE_KEY_TASK_LIST, childTaskArrayList);
        taskListBundle.putString(ConstructionConstants.BUNDLE_KEY_USER_AVATAR, mAvatarUrl);
        taskListBundle.putInt("position", position);
        return ProjectDetailTasksFragment.newInstance(taskListBundle);
    }
}
