package com.autodesk.shejijia.shared.components.nodeprocess.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.EditPlanContract;
import com.autodesk.shejijia.shared.components.nodeprocess.data.ProjectRepository;
import com.autodesk.shejijia.shared.components.nodeprocess.presenter.EditMilestonePresenter;
import com.autodesk.shejijia.shared.components.nodeprocess.presenter.EditTaskNodePresenter;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment.EditMilestoneNodeFragment;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment.EditTaskNodeFragment;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

/**
 * Created by wenhulin on 11/2/16.
 */

public class CreateOrEditPlanActivity extends BaseActivity {
    private final static String FRAGMENT_TAG_EDIT_MILESTONE = "edit_milestone";
    private final static String FRAGMENT_TAG_EDIT_TASKNODE = "edit_task_node";

    private EditState mEditState = EditState.EDIT_MILESTONE;
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
                switch (mEditState) {
                    case EDIT_MILESTONE:
                        updateEditState(EditState.EDIT_TASK_NODE);
                        break;
                    case EDIT_TASK_NODE:
                        EditTaskNodeFragment fragment = (EditTaskNodeFragment) getFragment(FRAGMENT_TAG_EDIT_TASKNODE);
                        fragment.getPresenter().commitPlan();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            updateEditState(EditState.EDIT_MILESTONE);
        } else {
            mEditState = EditState.values()[savedInstanceState.getInt("state")];
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("state", mEditState.ordinal());
        // TODO presenter save data
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mEditState == EditState.EDIT_MILESTONE) {
            Intent data = new Intent();
            data.putExtra(ConstructionConstants.BUNDLE_KEY_IS_PLAN_EDITING, ProjectRepository.getInstance().isActivePlanEditing());
            setResult(Activity.RESULT_CANCELED, data);
            supportFinishAfterTransition();
        } else {
            updateEditState(EditState.EDIT_MILESTONE);
        }
    }

    private void updateEditState(EditState newState) {
        if (newState.equals(EditState.EDIT_MILESTONE)) {
            mProgressBar.setProgress(50);
            mActionBtn.setText(R.string.edit_plan_next_step);
        } else {
            mProgressBar.setProgress(100);
            mActionBtn.setText(R.string.edit_plan_complete);
        }
        switchToFragment(getFragmentTag(newState));
        mEditState = newState;
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
            }
        }
        fragmentTransaction.replace(R.id.container, fragment, tag);
        fragmentTransaction.commit();
    }

    private String getFragmentTag(EditState editState) {
        if (editState == EditState.EDIT_MILESTONE) {
            return FRAGMENT_TAG_EDIT_MILESTONE;
        } else {
            return FRAGMENT_TAG_EDIT_TASKNODE;
        }
    }

    private Fragment getFragment(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        return fragmentManager.findFragmentByTag(tag);
    }

    public static enum EditState {
        EDIT_MILESTONE,
        EDIT_TASK_NODE,
    }
}
