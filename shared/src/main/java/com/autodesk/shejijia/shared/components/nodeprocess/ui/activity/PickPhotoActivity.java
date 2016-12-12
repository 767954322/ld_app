package com.autodesk.shejijia.shared.components.nodeprocess.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.CommentConfig;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.comment.CommentFragment;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.comment.CommentPresenter;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment.PickPhotoFragment;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

/**
 * Class description
 *
 * @author wenhulin
 * @since 16/12/12
 */

public class PickPhotoActivity extends BaseActivity {
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_pick_photo;
    }

    @Override
    protected void initView() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.upload_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.menu_submit) {
            // TODO submit
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            CommentConfig config = new CommentConfig();
            CommentFragment fragment = CommentFragment.getInstance(config);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.content_container, fragment);
            transaction.commit();

            CommentPresenter presenter = new CommentPresenter(fragment,null);
        }
    }
}
