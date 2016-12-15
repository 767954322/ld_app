package com.autodesk.shejijia.enterprise.personalcenter.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.enterprise.personalcenter.activity.AboutActivity;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.utility.CommonUtils;
import com.autodesk.shejijia.shared.components.common.utility.DataCleanManager;
import com.autodesk.shejijia.shared.components.common.utility.MPFileUtility;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.fragment.BaseConstructionFragment;
import com.autodesk.shejijia.enterprise.common.utils.LoginUtils;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.components.common.utility.BackGroundUtils;

import java.io.File;

/**
 * Created by t_xuz on 8/30/16.
 * 我页--更多页面
 */
public class MoreFragment extends BaseConstructionFragment implements View.OnClickListener {


    private TextView mClearCache;
    private TextView mAboutApp;
    private TextView mLogoutApp;
    private PopupWindow mBottomPopUp;

    public static MoreFragment newInstance() {
        return new MoreFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_personal_center_more;
    }

    @Override
    protected void initView() {
        mClearCache = (TextView) rootView.findViewById(R.id.tv_clear_cache);
        mAboutApp = (TextView) rootView.findViewById(R.id.tv_about_app);
        mLogoutApp = (TextView) rootView.findViewById(R.id.tv_logout_app);

        setHasOptionsMenu(true);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initListener() {
        mClearCache.setOnClickListener(this);
        mAboutApp.setOnClickListener(this);
        mLogoutApp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_about_app:
                startActivity(new Intent(mContext, AboutActivity.class));
                break;
            case R.id.tv_clear_cache:
                initBottomPopup();
                break;
            case R.id.tv_logout_app:
                LoginUtils.doLogout(mContext);
                LoginUtils.doLogin(mContext);
                break;
        }
    }

    private void initBottomPopup() {
        String cacheSize = getCacheSize();
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.popup_clear_cache_dialog, null);
        TextView mCacheSize = (TextView) contentView.findViewById(R.id.tv_cache_size);
        TextView mCacheConfirm = (TextView) contentView.findViewById(R.id.tv_cache_confirm);
        TextView mCacheCancel = (TextView) contentView.findViewById(R.id.tv_cache_cancel);
        mCacheSize.setText(UIUtils.getString(R.string.clear_cache)+cacheSize+")");
        mCacheSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mCacheConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearCache();
            }
        });
        mCacheCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomPopUp.dismiss();
            }
        });

        contentView.setFocusable(true);
        contentView.setFocusableInTouchMode(true);
        mBottomPopUp = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mBottomPopUp.setFocusable(true);
        mBottomPopUp.setOutsideTouchable(false);
        contentView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    mBottomPopUp.dismiss();
                    return true;
                }
                return false;
            }
        });
        mBottomPopUp.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                BackGroundUtils.dimWindowBackground(mContext, 0.7f, 1.0f);
            }
        });
        View view = ((mContext).findViewById(android.R.id.content)).getRootView();
        mBottomPopUp.setAnimationStyle(R.style.pop_bottom_animation);
        BackGroundUtils.dimWindowBackground(mContext, 1.0f, 0.7f);
        mBottomPopUp.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }
    private void clearCache(){
        CustomProgress.show(getActivity(), "", false, null);
        CommonUtils.clearAppCache(getActivity());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CustomProgress.cancelDialog();
                ToastUtils.showLong(getActivity(),UIUtils.getString(R.string.successful));
            }
        }, 1000);
        mBottomPopUp.dismiss();
    }
    private String getCacheSize() {
        String cacheSize ="";
        File cacheDir = getActivity().getCacheDir();
        try {
            double rootDirectoryize = DataCleanManager.getFolderSize(MPFileUtility.getCacheRootDirectoryHandle(getActivity()));
            double dirCachesize = DataCleanManager.getFolderSize(cacheDir);
            cacheSize = DataCleanManager.getFormatSize(rootDirectoryize + dirCachesize);
        }catch (Exception e){
            e.printStackTrace();

        }
        return cacheSize;
    }

}
