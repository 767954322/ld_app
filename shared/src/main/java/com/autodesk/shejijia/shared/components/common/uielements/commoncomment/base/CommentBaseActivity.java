//package com.autodesk.shejijia.shared.components.common.uielements.commoncomment.base;
//
//import android.os.Bundle;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//
//import com.autodesk.shejijia.shared.R;
//import com.autodesk.shejijia.shared.components.common.uielements.commoncomment.comment.CommentFragment;
//import com.autodesk.shejijia.shared.components.common.uielements.commoncomment.comment.CommentPresenter;
//import com.autodesk.shejijia.shared.components.common.uielements.commoncomment.CommentConfig;
//import com.autodesk.shejijia.shared.framework.activity.BaseActivity;
//
///**
// * Created by t_panya on 16/11/29.
// */
//
//public class CommentBaseActivity extends BaseActivity {
//
//    private CommentFragment fragment;
//    private CommentPresenter mPresenter;
//    @Override
//    protected int getLayoutResId() {
//        return R.layout.activity_common_comment;
//    }
//
//    @Override
//    protected void initView() {
//        setFragment();
//    }
//
//    @Override
//    protected void initData(Bundle savedInstanceState) {
//        mPresenter = new CommentPresenter(fragment,null);
//    }
//
//    private void setFragment(){
//        CommentConfig mConfig = new CommentConfig();
//        fragment = CommentFragment.getInstance(mConfig);
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction ft = manager.beginTransaction();
//        ft.add(R.id.fl_container,fragment,"common");
//        ft.commitAllowingStateLoss();
//    }
//}
