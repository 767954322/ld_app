package com.autodesk.shejijia.shared.components.nodeprocess.plan;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

/**
 * Created by wenhulin on 11/2/16.
 */

public class CreateOrEditPlanActivity extends BaseActivity {
    private final static String FRAGMENT_TAG_EDIT_MILESTONE = "edit_milestone";
    private final static String FRAGMENT_TAG_EDIT_TASKNODE = "edit_task_node";

    //TODO move this state to presenter
    private EditPlanContract.Presenter mPresenter;

    private ProgressBar mProgressBar;
    private Button mActionBtn;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_create_or_edit_plan;
    }

    @Override
    protected void initView() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        mPresenter = new EditPlanPresenter();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mPresenter.getEditState().equals(EditPlanPresenter.EditState.EDIT_TASK_NODE)) {
            updateEditState(EditPlanPresenter.EditState.EDIT_MILESTONE);
        } else {
            // TODO popup hint
            finish();
        }
    }

    private void updateEditState(EditPlanPresenter.EditState newState) {
        mPresenter.updateEditState(newState);
        if (newState.equals(EditPlanPresenter.EditState.EDIT_MILESTONE)) {
            mProgressBar.setProgress(50);
            mActionBtn.setText("下一步"); // TODO
            switchToFragment(FRAGMENT_TAG_EDIT_MILESTONE);
        } else {
            mProgressBar.setProgress(100);
            mActionBtn.setText("完成"); // TODO
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
                mPresenter.bindView((EditMilestoneNodeFragment)fragment);
            } else {
                fragment = new EditTaskNodeFragment();
                mPresenter.bindView((EditTaskNodeFragment)fragment);
                fragmentTransaction.addToBackStack(null);
            }
        }
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}
