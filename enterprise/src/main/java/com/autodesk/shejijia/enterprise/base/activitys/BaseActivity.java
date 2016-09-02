package com.autodesk.shejijia.enterprise.base.activitys;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.autodesk.shejijia.enterprise.R;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.feedback.PgyFeedbackShakeManager;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.views.PgyerDialog;
import com.socks.library.KLog;

/**
 * Created by t_xuz on 8/15/16.
 *
 */
public abstract class BaseActivity extends AppCompatActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getContentViewId());
        //注册蒲公英
        registerPgy();

        initData(savedInstanceState);

        initViews();

        initEvents();
    }

    protected abstract int getContentViewId();

    protected abstract void initData(Bundle savedInstanceState);

    protected abstract void initViews();

    protected abstract void initEvents();

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    private void registerPgy(){
        //crash注册
        PgyCrashManager.register(this);
        //version update
//        PgyUpdateManager.register(this); //放在主页里(projectListActivity)提示version update
        //设置反馈页面dialog的风格,符合app
        PgyerDialog.setDialogTitleBackgroundColor("#2B77C1");
        PgyerDialog.setDialogTitleTextColor("#ffffff");
    }

    //返回键事件监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
       /* if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
                return true;
            }
        }*/
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 蒲公英feedback功能注册
        // custom sensitivity, defaults to 950, the smaller the number higher sensitivity.
        PgyFeedbackShakeManager.setShakingThreshold(1000);

        // Open as a dialog
        PgyFeedbackShakeManager.register(BaseActivity.this);

        // 以页面的形式展示反馈页面有点丑,屏蔽
        //  Open as an Activity, in the case you must configure FeedbackActivity in the file of AndroidManifest.xml
//        PgyFeedbackShakeManager.register(BaseActivity.this, false);

    }

    @Override
    protected void onPause() {
        super.onPause();
        // 蒲公英feedback取消注册功能
        PgyFeedbackShakeManager.unregister();
    }
}
