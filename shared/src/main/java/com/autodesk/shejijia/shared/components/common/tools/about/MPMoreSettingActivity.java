package com.autodesk.shejijia.shared.components.common.tools.about;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.MyToast;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.utility.CommonUtils;
import com.autodesk.shejijia.shared.components.common.utility.DataCleanManager;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPFileUtility;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import java.io.File;

/**
 * @author Malidong .
 * @version 1.0 .
 * @date 16-6-7 下午1:12
 * @file MPMoreSettingActivity.java  .
 * @brief 设置更多.
 */
public class MPMoreSettingActivity extends NavigationBarActivity implements OnClickListener, OnItemClickListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_consumer_setting;
    }

    @Override
    protected void initView() {
        super.initView();
        rl_personal_clear_cache = (RelativeLayout) findViewById(R.id.rl_personal_clear_cache);
        rl_personal_b_about = (RelativeLayout) findViewById(R.id.rl_personal_b_about_designer);
        bt_consumer_exit = (TextView) findViewById(R.id.bt_consumer_exit);
        tv_cache_size = (TextView) findViewById(R.id.tv_cache_size);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleForNavbar(UIUtils.getString(R.string.more));

        ///　调系统方法　.
        intentLogout = getIntent();
        cacheDir = getCacheDir();
        filesDir = getFilesDir();
        externalCacheDir = getExternalCacheDir();

        showDialog();
    }

    /***
     * 提示框
     */
    private void showDialog() {
        mAlertView =
                new AlertView(UIUtils.getString(R.string.clearcache_detail),
                        null, UIUtils.getString(R.string.cancel), new String[]{UIUtils.getString(R.string.clearcache)},
                        null, this, AlertView.Style.ActionSheet, this);
    }


    @Override
    protected void initListener() {
        super.initListener();
        rl_personal_clear_cache.setOnClickListener(this);
        rl_personal_b_about.setOnClickListener(this);
        bt_consumer_exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.rl_personal_clear_cache)
        {
            mAlertView.show();

        }
        else if (i == R.id.bt_consumer_exit)
        {
            CommonUtils.clearAppCache(this);

            intentLogout.putExtra(Constant.LOGOUT, Constant.LOGOUT);
            setResult(RESULT_OK, intentLogout);

            AdskApplication.getInstance().doLogout(this);
            finish();

        }
        else if (i == R.id.rl_personal_b_about_designer)
        {
            CommonUtils.launchActivity(this, AboutActivity.class);

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        try {

            double mpCachesize = DataCleanManager.getFolderSize(MPFileUtility.getCacheRootDirectoryHandle(this));
            double othercachesize = DataCleanManager.getFolderSize(cacheDir);

            cacheSize = DataCleanManager.getFormatSize(mpCachesize + othercachesize);

            filesSize = DataCleanManager.getCacheSize(filesDir);
            externalSize = DataCleanManager.getCacheSize(externalCacheDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.i(TAG, "cacheDir:" + cacheDir + "==>cacheSize:" + cacheSize);
        LogUtils.i(TAG, "filesDir:" + filesDir + "==>filesSize:" + filesSize);
        LogUtils.i(TAG, "externalCacheDir:" + externalCacheDir + "==>externalSize:" + externalSize);

        tv_cache_size.setText(cacheSize);
    }

    /**
     * file-普通的文件存储
     * database-数据库文件（.db文件）
     * sharedPreference-配置数据(.xml文件）
     * cache-图片缓存文件
     */
    private String getCacheSize() {
        try {
            //cacheSize = DataCleanManager.getCacheSize(cacheDir);
            double mpCachesize = DataCleanManager.getFolderSize(MPFileUtility.getCacheRootDirectoryHandle(this));
            double othercachesize = DataCleanManager.getFolderSize(cacheDir);

            cacheSize = DataCleanManager.getFormatSize(mpCachesize + othercachesize);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cacheSize;
    }


    @Override
    public void onItemClick(Object obj, int position) {
        if (obj == mAlertView && position != AlertView.CANCELPOSITION) {
            if (getCacheSize().equals("0.0B")) {
                MyToast.show(this, UIUtils.getString(R.string.no_cache));
            } else {
                CustomProgress.show(MPMoreSettingActivity.this, "清除缓存中...", false, null);
                CommonUtils.clearAppCache(this);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv_cache_size.setText("0.0MB");
                        CustomProgress.cancelDialog();
                        MyToast.show(MPMoreSettingActivity.this, "缓存已清完");
                    }
                }, 1000);
            }
        } else {
            return;
        }
        return;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (mAlertView != null && mAlertView.isShowing()) {
                mAlertView.dismiss();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /// 控件.
    private RelativeLayout rl_personal_clear_cache, rl_personal_b_about;
    private TextView bt_consumer_exit;
    private TextView tv_cache_size;
    private AlertView mAlertView;

    /// 变量.
    private Intent intentLogout;
    private String cacheSize, filesSize, externalSize;
    private File cacheDir, filesDir, externalCacheDir;
}
