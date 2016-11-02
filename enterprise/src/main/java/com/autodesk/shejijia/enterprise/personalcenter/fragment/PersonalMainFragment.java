package com.autodesk.shejijia.enterprise.personalcenter.fragment;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.shared.framework.fragment.BaseConstructionFragment;

/**
 * Created by t_xuz on 8/31/16.
 * 个人中心主fragment
 */
public class PersonalMainFragment extends BaseConstructionFragment implements View.OnClickListener {

    private ImageButton mHeadPicBtn;
    private TextView mPersonalProject;
    private TextView mPersonalMore;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_personal_center_main;
    }

    @Override
    protected void initView() {
        mPersonalProject = (TextView) rootView.findViewById(R.id.tv_personal_project);
        mPersonalMore = (TextView) rootView.findViewById(R.id.tv_personal_more);
        mHeadPicBtn = (ImageButton) rootView.findViewById(R.id.imgBtn_personal_headPic);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initListener() {
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
                        .setCustomAnimations(R.anim.slide_fragment_horizontal_enter,R.anim.slide_fragment_horizontal_exit,R.anim.slide_fragment_pop_enter,R.anim.slide_fragment_pop_exit)
                        .add(R.id.fly_personal_center_container, ProjectListFragment.newInstance())
                        .addToBackStack(ProjectListFragment.newInstance().getClass().getSimpleName())
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
