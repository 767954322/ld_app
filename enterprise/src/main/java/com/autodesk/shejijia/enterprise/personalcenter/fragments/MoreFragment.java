package com.autodesk.shejijia.enterprise.personalcenter.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.enterprise.base.common.utils.ToastUtils;
import com.autodesk.shejijia.enterprise.base.fragments.BaseFragment;

/**
 * Created by t_xuz on 8/30/16.
 * 我页--更多页面
 */
public class MoreFragment extends BaseFragment implements View.OnClickListener{

    private TextView mMoreTitle;
    private TextView mClearCache;
    private TextView mAboutApp;
    private TextView mLogoutApp;
    private ImageButton mBackBtn;
    private PopupWindow bottomPopUp;

    public static MoreFragment newInstance(){
        return new MoreFragment();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal_center_more;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mBackBtn = (ImageButton)view.findViewById(R.id.imgBtn_back);
        mClearCache = (TextView)view.findViewById(R.id.tv_clear_cache);
        mAboutApp = (TextView)view.findViewById(R.id.tv_about_app);
        mLogoutApp = (TextView)view.findViewById(R.id.tv_logout_app);
        mMoreTitle = (TextView)view.findViewById(R.id.tv_personal_title);

        mMoreTitle.setText(getString(R.string.personal_center_more));
    }

    @Override
    protected void initData() {}

    @Override
    protected void initEvents() {
        mClearCache.setOnClickListener(this);
        mAboutApp.setOnClickListener(this);
        mLogoutApp.setOnClickListener(this);
        mBackBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBtn_back:
                mContext.getSupportFragmentManager().popBackStack();
                break;
            case R.id.tv_about_app:
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_fragment_horizontal_enter,R.anim.slide_fragment_horizontal_exit,R.anim.slide_fragment_pop_enter,R.anim.slide_fragment_pop_exit)
                        .add(R.id.fly_personal_center_container,AboutAppFragment.newInstance())
                        .addToBackStack(AboutAppFragment.newInstance().getClass().getSimpleName())
                        .commit();
                break;
            case R.id.tv_clear_cache:
                initBottomPopup();
                break;
            case R.id.tv_logout_app:

                break;
        }
    }

    private void initBottomPopup(){
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.popup_clear_cache_dialog,null);
        TextView mCacheSize = (TextView)contentView.findViewById(R.id.tv_cache_size);
        TextView mCacheConfirm = (TextView)contentView.findViewById(R.id.tv_cache_confirm);
        TextView mCacheCancel = (TextView)contentView.findViewById(R.id.tv_cache_cancel);
        mCacheSize.setText("清除本地缓存数据(包括图片和语音缓存)");
        mCacheSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort((Activity) mContext,"预约时间已录入!");
            }
        });
        mCacheConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomPopUp.dismiss();
            }
        });
        mCacheCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomPopUp.dismiss();
            }
        });

        contentView.setFocusable(true);
        contentView.setFocusableInTouchMode(true);
        bottomPopUp = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        bottomPopUp.setFocusable(true);
        bottomPopUp.setOutsideTouchable(false);
        contentView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK){

                    bottomPopUp.dismiss();
                    return true;
                }
                return false;
            }
        });
        bottomPopUp.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackGround(1.0f);
            }
        });
        View view = (((Activity)mContext).findViewById(android.R.id.content)).getRootView();
        bottomPopUp.setAnimationStyle(R.style.pop_bottom_animation);
        setBackGround(0.7f);
        bottomPopUp.showAtLocation(view, Gravity.BOTTOM,0,0);
    }

    private void setBackGround(float alpha){
        WindowManager.LayoutParams layoutParams = ((Activity)mContext).getWindow().getAttributes();
        layoutParams.alpha = alpha;
        ((Activity)mContext).getWindow().setAttributes(layoutParams);
    }
}