package com.autodesk.shejijia.enterprise.nodeprocess.plan;

/**
 * Created by wenhulin on 11/3/16.
 */

public class EditPlanPresenter implements EditPlanContract.Presenter {
    private EditPlanContract.View mView;
    private EditState mEditState;

    @Override
    public void bindView(EditPlanContract.View view) {
        mView = view;
        mView.bindPresenter(this);
    }

    @Override
    public void fetchPlan() {

    }

    @Override
    public void commitPlan() {

    }

    @Override
    public void updateEditState(EditState newState) {
        mEditState = newState;
    }

    @Override
    public EditState getEditState() {
        return mEditState;
    }

    @Override
    public void updatePlan() {

    }
}
