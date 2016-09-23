package com.autodesk.shejijia.consumer.home.decorationdesigners.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.FindDesignerBean;
import com.autodesk.shejijia.consumer.home.homepage.fragment.DesignerListFragment;
import com.autodesk.shejijia.shared.components.common.uielements.ClearEditText;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-8-11 .
 * @file DesignerSearchActivity.java .
 * @brief 设计师搜索页面 .
 */
public class DesignerSearchActivity extends NavigationBarActivity implements
        View.OnClickListener,
        View.OnKeyListener {

    private ClearEditText mCetSearchContent;
    private ImageView mIvSearchCBack;
    private TextView mTvSearchCancel;
    private DesignerListFragment mDesignerListFragment;
    private FrameLayout mFrameLayoutContainer;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_search_popup_designer;
    }

    @Override
    protected void initView() {
        super.initView();
        mCetSearchContent = (ClearEditText) findViewById(R.id.ect_et_search_pop);
        mIvSearchCBack = (ImageView) findViewById(R.id.ect_searchc_back_pop);
        mTvSearchCancel = (TextView) findViewById(R.id.tv_ect_search_cancel_pop);
        mFrameLayoutContainer = (FrameLayout) findViewById(R.id.fragment_container);

        mDesignerListFragment = (DesignerListFragment) (getSupportFragmentManager().findFragmentById(R.id.fragment_designer_list));

    }

    @Override
    protected void initListener() {
        mTvSearchCancel.setOnClickListener(this);
        mIvSearchCBack.setOnClickListener(this);
        mCetSearchContent.setOnKeyListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mCetSearchContent.setText("");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ect_searchc_back_pop:
            case R.id.tv_ect_search_cancel_pop:

                finish();
                break;

//            case R.id.tv_ect_search_cancel_pop:
//                cancelPopupWindowAndClearSearchContent();
//                break;

            default:
                break;
        }
    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(DesignerSearchActivity.this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
            String mSearchKeywords = mCetSearchContent.getText().toString().trim();
            if (TextUtils.isEmpty(mSearchKeywords)) {
                mSearchKeywords = "";
            }
            FindDesignerBean findDesignerBean = new FindDesignerBean();
            findDesignerBean.setNick_name(mSearchKeywords);

            findDesignerBean.setStyle_names("");
            findDesignerBean.setStyle("");
            findDesignerBean.setStart_experience("");
            findDesignerBean.setEnd_experience("");
            findDesignerBean.setDesign_price_code("");

            mFrameLayoutContainer.setVisibility(View.VISIBLE);
            mDesignerListFragment.updateNotify(findDesignerBean);
            return true;
        }
        return false;
    }

    /**
     * 弹框消失,搜索条件制空.
     */
    private void cancelPopupWindowAndClearSearchContent() {

        mCetSearchContent.setText("");
    }
}
