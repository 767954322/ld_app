package com.autodesk.shejijia.enterprise.base.activitys;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.feedback.PgyFeedbackShakeManager;
import com.pgyersdk.views.PgyerDialog;

/**
 * Created by t_xuz on 8/15/16.
 *
 */
public abstract class BaseEnterpriseActivity extends BaseActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册蒲公英
        registerPgy();
    }

    private void registerPgy(){
        //crash注册
        PgyCrashManager.register(this);
        //设置反馈页面dialog的风格,符合app
        PgyerDialog.setDialogTitleBackgroundColor("#2B77C1");
        PgyerDialog.setDialogTitleTextColor("#ffffff");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 蒲公英feedback功能注册
        // custom sensitivity, defaults to 950, the smaller the number higher sensitivity.
        PgyFeedbackShakeManager.setShakingThreshold(1000);

        // Open as a dialog
        PgyFeedbackShakeManager.register(BaseEnterpriseActivity.this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        // 蒲公英feedback取消注册功能
        PgyFeedbackShakeManager.unregister();
    }
}
