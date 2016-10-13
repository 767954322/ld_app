package com.autodesk.shejijia.enterprise.personalcenter.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.enterprise.base.fragments.BaseFragment;

/**
 * Created by t_xuz on 9/2/16.
 * 我页--关于app页面
 */
public class AboutFragment extends BaseFragment{
    private ImageButton mBackBtn;
    private TextView mTopBarTitle;

    public static AboutFragment newInstance(){
        return new AboutFragment();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal_center_about;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mBackBtn = (ImageButton)view.findViewById(R.id.imgBtn_back);
        mTopBarTitle = (TextView)view.findViewById(R.id.tv_personal_title);
        mTopBarTitle.setText(mContext.getString(R.string.personal_center_more_about));
    }

    @Override
    protected void initData() {}

    @Override
    protected void initEvents() {
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.getSupportFragmentManager().popBackStack();
            }
        });
    }
}
