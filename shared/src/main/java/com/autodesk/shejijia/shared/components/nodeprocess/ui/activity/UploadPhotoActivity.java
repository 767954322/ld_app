package com.autodesk.shejijia.shared.components.nodeprocess.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.CommentConfig;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.comment.CommentFragment;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.comment.CommentPresenter;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.model.entity.ImageInfo;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.UploadPhotoContact;
import com.autodesk.shejijia.shared.components.nodeprocess.presenter.UploadPhotoPresenter;
import com.autodesk.shejijia.shared.components.nodeprocess.utility.DialogHelper;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

import java.util.List;

/**
 * Class description
 *
 * @author wenhulin
 * @since 16/12/12
 */

public class UploadPhotoActivity extends BaseActivity implements UploadPhotoContact.View {

    private CommentPresenter mCommentPresenter;
    private UploadPhotoContact.Presenter mUploadPhotoPresenter;

    private DialogHelper mDialogHelper;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_pick_photo;
    }

    @Override
    protected void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.upload_photo);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.upload_photo, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        List<ImageInfo> selectedPhotos = mCommentPresenter.getImageData();
        MenuItem item = menu.findItem(R.id.menu_submit);
        item.setEnabled(selectedPhotos != null && !selectedPhotos.isEmpty());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_submit) {
            mUploadPhotoPresenter.uploadPhotos(mCommentPresenter.getImageData());
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        supportFinishAfterTransition();
        return true;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        CommentFragment commentFragment;
        if (savedInstanceState == null) {
            CommentConfig config = new CommentConfig();
            config.seteLayoutType(CommentConfig.LayoutType.EDIT_IMAGE_ONLY);
            commentFragment = CommentFragment.getInstance(config);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.content_container, commentFragment);
            transaction.commit();
        } else {
            commentFragment = (CommentFragment) getSupportFragmentManager().findFragmentById(R.id.content_container);
        }

        mCommentPresenter = new CommentPresenter(commentFragment, null);

        String projectId = getIntent().getStringExtra(ConstructionConstants.BUNDLE_KEY_PROJECT_ID);
        String taskId = getIntent().getStringExtra(ConstructionConstants.BUNDLE_KEY_TASK_ID);
        mUploadPhotoPresenter = new UploadPhotoPresenter(this, projectId, taskId);

        mDialogHelper = new DialogHelper(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        invalidateOptionsMenu();
    }

    @Override
    public void showNetError(ResponseError error) {
        mDialogHelper.showNetError(error);
    }

    @Override
    public void showError(String errorMsg) {
        mDialogHelper.showError(errorMsg);
    }

    @Override
    public void showLoading() {
        mDialogHelper.showLoading();
    }

    @Override
    public void hideLoading() {
        mDialogHelper.hideLoading();
    }

    @Override
    public void showUploading() {
        mDialogHelper.showUpLoading();
    }

    @Override
    public void hideUploading() {
        mDialogHelper.hideUpLoading();
    }

    @Override
    public void showError(@NonNull String error, @NonNull DialogInterface.OnClickListener positiveClickListener, @NonNull DialogInterface.OnClickListener negativeClickListener) {
        mDialogHelper.showError(error, positiveClickListener, negativeClickListener);
    }

    @Override
    public void onUploadSuccess() {
        setResult(RESULT_OK);
        supportFinishAfterTransition();
    }
}
