package com.autodesk.shejijia.shared.components.common.uielements.commentview.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.CommentConfig;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.comment.CommentFragment;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.comment.CommentPresenter;

/**
 * Created by t_panya on 16/12/2.
 */

public class CommonBaseActivity extends AppCompatActivity {
    CommentFragment fragment;
    private CommentPresenter mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_comment);
        setFragment();
        mPresenter = new CommentPresenter(fragment,null);
    }

    private void setFragment(){
        CommentConfig mConfig = new CommentConfig();
        fragment = CommentFragment.getInstance(mConfig);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.fl_container,fragment,"common");
        Log.d("CommonBaseActivity", "setFragment: containerid == " + R.id.fl_container);
        ft.commitAllowingStateLoss();
    }
}
