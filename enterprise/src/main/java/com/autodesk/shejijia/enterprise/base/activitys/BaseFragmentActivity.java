package com.autodesk.shejijia.enterprise.base.activitys;

import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import java.util.List;

/**
 * Created by t_xuz on 8/18/16.
 * 供 流式fragment继承
 */
public abstract class BaseFragmentActivity extends BaseActivity {

    protected abstract int getFragmentContentId();
    private List<Fragment> mFragmentLists;

    //添加Fragment
    public void addFragment(Fragment fragment) {
        mFragmentLists = getSupportFragmentManager().getFragments();
        if (fragment != null) {
            if (mFragmentLists == null) { //第一次进入的时候
                getSupportFragmentManager().beginTransaction()
                        .add(getFragmentContentId(), fragment, fragment.getClass().getSimpleName())
                        .addToBackStack(fragment.getClass().getSimpleName())
                        .commitAllowingStateLoss();
            }else {
                if (mFragmentLists.contains(fragment)){
                    getSupportFragmentManager().beginTransaction()
                            .show(fragment)
                            .commitAllowingStateLoss();
                }else {
                    getSupportFragmentManager().beginTransaction()
                            .add(getFragmentContentId(), fragment, fragment.getClass().getSimpleName())
                            .addToBackStack(fragment.getClass().getSimpleName())
                            .commitAllowingStateLoss();
                }
            }
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
