package com.autodesk.shejijia.consumer.home.homepage.fragment;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

/**
 * @author yaoxuehua .
 * @version 1.0 .
 * @date 16-7-20
 * @file DesignerConstructionFragment.java  .
 * @brief 施工fragment。
 */
public class DesignerConstructionFragment extends BaseFragment {

    private TextView mTvMsg;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_designer_constuction;
    }

    @Override
    protected void initView() {
        mTvMsg = (TextView) rootView.findViewById(R.id.tv_empty_message);

    }

    @Override
    protected void initData() {
        mTvMsg.setText("您还没有施工项目哦！");
    }
}
