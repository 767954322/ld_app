package com.autodesk.shejijia.shared.components.issue.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.issue.ui.activity.AddIssueDescriptionActivity;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

/**
 * Created by Menghao.Gu on 2016/12/6.
 */

public class AddIssueTrackingFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout mIssueDescription;

    public static AddIssueTrackingFragment getInstance() {
        AddIssueTrackingFragment fragment = new AddIssueTrackingFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_add_issue_list;
    }

    @Override
    protected void initView() {
        mIssueDescription = (RelativeLayout) rootView.findViewById(R.id.rl_issuedescrip);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        super.initListener();
        mIssueDescription.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.rl_issuedescrip) {
            Intent intent_description = new Intent(activity, AddIssueDescriptionActivity.class);
            startActivity(intent_description);
        }
    }
}
