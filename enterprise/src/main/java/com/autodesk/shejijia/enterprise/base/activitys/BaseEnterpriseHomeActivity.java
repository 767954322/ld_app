package com.autodesk.shejijia.enterprise.base.activitys;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.autodesk.shejijia.shared.components.common.utility.Constants;
import com.autodesk.shejijia.enterprise.nodeprocess.ui.fragment.GroupChatFragment;
import com.autodesk.shejijia.enterprise.nodeprocess.ui.fragment.IssueListFragment;
import com.autodesk.shejijia.enterprise.nodeprocess.ui.fragment.TaskListFragment;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

import java.util.List;

/**
 * Created by t_xuz on 8/26/16.
 * 主页同级fragment的管理
 */
public abstract class BaseEnterpriseHomeActivity extends BaseActivity {

    private TaskListFragment mTaskListFragment;
    private IssueListFragment mIssueListFragment;
    private GroupChatFragment mGroupChatFragment;

    protected abstract int getFragmentContentId();
    /*
    * 管理 返回栈中的fragment,以及解决内存重启现象
    * */
    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState != null) { //内存重启时调用
            List<Fragment> fragmentLists = getSupportFragmentManager().getFragments();
            for (Fragment fragment : fragmentLists) {
                if (fragment instanceof TaskListFragment) {
                    mTaskListFragment = (TaskListFragment) fragment;
                } else if (fragment instanceof IssueListFragment) {
                    mIssueListFragment = (IssueListFragment) fragment;
                } else if (fragment instanceof GroupChatFragment) {
                    mGroupChatFragment = (GroupChatFragment) fragment;
                }
            }

            //解决内存重启现象
            getSupportFragmentManager().beginTransaction()
                    .show(mTaskListFragment)
                    .hide(mIssueListFragment)
                    .hide(mGroupChatFragment)
                    .commitAllowingStateLoss();
        } else {//正常启动时,添加并管理fragment
            mTaskListFragment = new TaskListFragment();
            mIssueListFragment = new IssueListFragment();
            mGroupChatFragment = new GroupChatFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(getFragmentContentId(), mTaskListFragment,Constants.TASK_LIST_FRAGMENT)
                    .add(getFragmentContentId(), mIssueListFragment,Constants.ISSUE_LIST_FRAGMENT)
                    .add(getFragmentContentId(), mGroupChatFragment,Constants.GROUP_CHAT_FRAGMENT)
                    .show(mTaskListFragment)
                    .hide(mIssueListFragment)
                    .hide(mGroupChatFragment)
                    .commitAllowingStateLoss();
        }
    }

    /*
    * 切换 不同tab下的fragment
    * tag fragment对应的tag
    * position tab对应的位置
    * */
    protected void changeFragment(String tag,int position){
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        if (fragmentList == null) return;
        LogUtils.e("fragmentLis－－－size",fragmentList.size()+"");
        for (Fragment fragment : fragmentList){
            if (fragment.getTag().equalsIgnoreCase(tag) && position==0){
                getSupportFragmentManager().beginTransaction()
                        .show(mTaskListFragment)
                        .hide(mIssueListFragment)
                        .hide(mGroupChatFragment)
                        .commitAllowingStateLoss();
                LogUtils.e("fragmentTag---1",fragment.getTag()+"");
            }else if (fragment.getTag().equalsIgnoreCase(tag) && position==1){
                getSupportFragmentManager().beginTransaction()
                        .show(mIssueListFragment)
                        .hide(mTaskListFragment)
                        .hide(mGroupChatFragment)
                        .commitAllowingStateLoss();
                LogUtils.e("fragmentTag---2",fragment.getTag()+"");
            }else if (fragment.getTag().equalsIgnoreCase(tag) && position==2){
                getSupportFragmentManager().beginTransaction()
                        .show(mGroupChatFragment)
                        .hide(mTaskListFragment)
                        .hide(mIssueListFragment)
                        .commitAllowingStateLoss();
                LogUtils.e("fragmentTag---3",fragment.getTag()+"");
            }
        }
    }

}
