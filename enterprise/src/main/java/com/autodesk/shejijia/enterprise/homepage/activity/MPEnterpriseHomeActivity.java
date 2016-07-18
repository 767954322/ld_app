package com.autodesk.shejijia.enterprise.homepage.activity;

import android.app.Fragment;

import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.shared.framework.activity.BaseHomeActivity;

public class MPEnterpriseHomeActivity extends BaseHomeActivity
{
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_enterprise_main;
    }

    @Override
    protected void initView() {
        super.initView();

        //hide navigation left button
        setVisibilityForNavButton(ButtonType.LEFT, false);
    }


    protected int getIMButtonId()
    {
        return R.id.designer_session_radio_btn;
    }

    protected int getRadioGroupId()
    {
        return R.id.designer_main_radio_group;
    }

    protected int getMainContentId()
    {
        return R.id.main_content;
    }

}
