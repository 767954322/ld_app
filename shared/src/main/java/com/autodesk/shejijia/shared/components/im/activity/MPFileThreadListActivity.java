package com.autodesk.shejijia.shared.components.im.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.im.fragment.MPThreadListFragment;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

public class MPFileThreadListActivity extends NavigationBarActivity
{
    public static final String MEMBERID = "Memeber_Id";
    public static final String MEMBERTYPE = "Memeber_Type";

    @Override
    protected int getLayoutResId()
    {
        return R.layout.activity_thread_list_file;
    }

    @Override
    protected void initView()
    {
        super.initView();
        Intent intent = getIntent();

        MPThreadListFragment threadListFragment = new MPThreadListFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(MPThreadListFragment.ISFILEBASE, true);
        bundle.putString(MPThreadListFragment.MEMBERID, intent.getStringExtra(MPFileThreadListActivity.MEMBERID));
        bundle.putString(MPThreadListFragment.MEMBERTYPE, intent.getStringExtra(MPFileThreadListActivity.MEMBERTYPE));
        threadListFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.thread_fragment, threadListFragment);
        fragmentTransaction.show(threadListFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void initData(Bundle savedInstanceState)
    {

    }

    @Override
    protected void initListener()
    {
        super.initListener();
    }



    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    protected String getActivityTitle() {
        return getResources().getString(R.string.mychat);
    }
}
