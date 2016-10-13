package com.autodesk.shejijia.enterprise.personalcenter.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.enterprise.base.activitys.BaseActivity;
import com.autodesk.shejijia.enterprise.personalcenter.fragment.PersonalMainFragment;

/**
 * Created by t_xuz on 8/30/16.
 * 我页--业务管理页面
 */
public class PersonalCenterActivity extends BaseActivity {

    //主fragment
    private PersonalMainFragment mPersonalMainFragment;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_personal_center_main;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {}
    @Override
    protected void initEvents() {}

    @Override
    protected void initViews() {

        //个人中心每次打开都会显示personalMainFragment
        FragmentManager fm = getSupportFragmentManager();
        mPersonalMainFragment = (PersonalMainFragment) fm.findFragmentById(R.id.fly_personal_center_container);
        if (mPersonalMainFragment == null){
            mPersonalMainFragment = new PersonalMainFragment();
            fm.beginTransaction()
                    .add(R.id.fly_personal_center_container,mPersonalMainFragment)
                    .commit();
        }
    }

}
