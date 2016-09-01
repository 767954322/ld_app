package com.autodesk.shejijia.enterprise.personalcenter.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.enterprise.base.fragments.BaseFragment;

/**
 * Created by t_xuz on 8/30/16.
 * 我页--全部项目列表页面
 */
public class MyProjectListFragment extends BaseFragment implements View.OnClickListener{
    private TextView mTopBarTitle;
    private ImageButton mScreenBtn;
    private ImageButton mBackBtn;

    public static MyProjectListFragment newInstance(){
        return new MyProjectListFragment();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal_center_project;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mTopBarTitle = (TextView)view.findViewById(R.id.tv_personal_title);
        mScreenBtn = (ImageButton)view.findViewById(R.id.imgBtn_screen);
        mBackBtn = (ImageButton)view.findViewById(R.id.imgBtn_back);
        mTopBarTitle.setText(getString(R.string.personal_center_my_project));
    }

    @Override
    protected void initData() {}

    @Override
    protected void initEvents() {
        mScreenBtn.setOnClickListener(this);
        mBackBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBtn_back:
                mContext.getSupportFragmentManager().popBackStack();
                break;
            case R.id.imgBtn_screen:

                break;
        }
    }
}
