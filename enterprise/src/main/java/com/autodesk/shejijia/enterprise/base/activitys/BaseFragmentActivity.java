package com.autodesk.shejijia.enterprise.base.activitys;

import android.support.v4.app.Fragment;
import android.view.KeyEvent;

/**
 * Created by t_xuz on 8/18/16.
 * 供 流式fragment继承
 */
public abstract class BaseFragmentActivity extends BaseActivity {

    protected abstract int getFragmentContentId();

    //添加Fragment
    public void addFragment(Fragment fragment) {

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(getFragmentContentId(), fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }

    }

    //移除Fragment
    public void removeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }


    //返回键事件监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
