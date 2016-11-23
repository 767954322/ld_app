package com.autodesk.shejijia.shared.components.nodeprocess.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment.ProjectDetailTasksFragment;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_xuz on 11/14/16.
 */

public class ProjectDetailsPagerAdapter extends FragmentPagerAdapter {

    private List<List<Task>> taskLists;

    public ProjectDetailsPagerAdapter(FragmentManager fm, List<List<Task>> taskLists) {
        super(fm);
        this.taskLists = taskLists;
    }

    @Override
    public Fragment getItem(int position) {
        return getFragment(position);
    }

    @Override
    public int getCount() {
        return taskLists.size();
    }

    private Fragment getFragment(int position) {
        Bundle taskListBundle = new Bundle();
        ArrayList<Task> childTaskArrayList = new ArrayList<>();
        childTaskArrayList.addAll(taskLists.get(position));
        taskListBundle.putSerializable(ConstructionConstants.BUNDLE_KEY_TASK_LIST, childTaskArrayList);
        return ProjectDetailTasksFragment.newInstance(taskListBundle);
    }
}
