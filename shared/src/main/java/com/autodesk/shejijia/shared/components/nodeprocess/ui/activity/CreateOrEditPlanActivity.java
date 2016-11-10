package com.autodesk.shejijia.shared.components.nodeprocess.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.EditPlanContract;
import com.autodesk.shejijia.shared.components.nodeprocess.presenter.EditPlanPresenter;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment.EditMilestoneNodeFragment;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment.EditTaskNodeFragment;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

/**
 * Created by wenhulin on 11/2/16.
 */

public class CreateOrEditPlanActivity extends BaseActivity {
    private final static String FRAGMENT_TAG_EDIT_MILESTONE = "edit_milestone";
    private final static String FRAGMENT_TAG_EDIT_TASKNODE = "edit_task_node";

    private EditPlanContract.Presenter mPresenter;

    private ProgressBar mProgressBar;
    private Button mActionBtn;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_create_or_edit_plan;
    }

    @Override
    protected void initView() {
        mActionBtn = (Button) findViewById(R.id.actionButton);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mPresenter.getEditState()) {
                    case EDIT_MILESTONE:
                        updateEditState(EditPlanPresenter.EditState.EDIT_TASK_NODE);
                        break;
                    case EDIT_TASK_NODE:
                        mPresenter.commitPlan();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mPresenter = new EditPlanPresenter(getIntent().getStringExtra("pid"));
        if (savedInstanceState == null) {
            updateEditState(EditPlanPresenter.EditState.EDIT_MILESTONE);
        } else {
            // TODO save and restore data when activity is destroyed in background
            mPresenter.updateEditState(EditPlanPresenter.EditState.values()[savedInstanceState.getInt("state")]);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("state", mPresenter.getEditState().ordinal());
        // TODO presenter save data
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (getSupportFragmentManager().popBackStackImmediate()) {
            updateEditState(EditPlanPresenter.EditState.EDIT_MILESTONE);
        } else {
            supportFinishAfterTransition();
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().popBackStackImmediate()) {
            updateEditState(EditPlanPresenter.EditState.EDIT_MILESTONE);
        } else {
            supportFinishAfterTransition();
        }
    }

    private void updateEditState(EditPlanPresenter.EditState newState) {
        mPresenter.updateEditState(newState);
        if (newState.equals(EditPlanPresenter.EditState.EDIT_MILESTONE)) {
            mProgressBar.setProgress(50);
            mActionBtn.setText(R.string.edit_plan_next_step);
            switchToFragment(FRAGMENT_TAG_EDIT_MILESTONE);
        } else {
            mProgressBar.setProgress(100);
            mActionBtn.setText(R.string.edit_plan_complete);
            switchToFragment(FRAGMENT_TAG_EDIT_TASKNODE);
        }
    }

    private void switchToFragment(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            if (FRAGMENT_TAG_EDIT_MILESTONE.equals(tag)) {
                fragment = new EditMilestoneNodeFragment();
            } else {
                fragment = new EditTaskNodeFragment();
                fragmentTransaction.addToBackStack(null);
            }
            ((EditPlanContract.View) fragment).bindPresenter(mPresenter);
        }
        mPresenter.bindView((EditPlanContract.View) fragment);
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}
