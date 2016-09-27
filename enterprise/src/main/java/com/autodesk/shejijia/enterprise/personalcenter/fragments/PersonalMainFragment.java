package com.autodesk.shejijia.enterprise.personalcenter.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.enterprise.base.fragments.BaseFragment;

/**
 * Created by t_xuz on 8/31/16.
 * 个人中心主fragment
 */
public class PersonalMainFragment extends BaseFragment implements View.OnClickListener {

    private ImageButton mHeadPicBtn;
    private TextView mPersonalProject;
    private TextView mPersonalMore;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal_center_main;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mPersonalProject = (TextView) view.findViewById(R.id.tv_personal_project);
        mPersonalMore = (TextView) view.findViewById(R.id.tv_personal_more);
        mHeadPicBtn = (ImageButton) view.findViewById(R.id.imgBtn_personal_headPic);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initEvents() {
        mHeadPicBtn.setOnClickListener(this);
        mPersonalProject.setOnClickListener(this);
        mPersonalMore.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBtn_personal_headPic://头像

                break;
            case R.id.tv_personal_project: //全部项目
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_fragment_horizontal_enter,R.anim.slide_fragment_horizontal_exit)
                        .add(R.id.fly_personal_center_container, MyProjectListFragment.newInstance())
                        .addToBackStack(MyProjectListFragment.newInstance().getClass().getSimpleName())
                        .commit();
                break;
            case R.id.tv_personal_more: //更多
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_fragment_horizontal_enter,R.anim.slide_fragment_horizontal_exit)
                        .add(R.id.fly_personal_center_container, MoreFragment.newInstance())
                        .addToBackStack(MoreFragment.newInstance().getClass().getSimpleName())
                        .commit();
                break;

        }
    }
}
