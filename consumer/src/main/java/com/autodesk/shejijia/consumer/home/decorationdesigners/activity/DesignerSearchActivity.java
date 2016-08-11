package com.autodesk.shejijia.consumer.home.decorationdesigners.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.shared.components.common.uielements.ClearEditText;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PinnedHeaderListView;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullListView;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullToRefreshLayout;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-8-11 .
 * @file DesignerSearchActivity.java .
 * @brief 设计师搜索页面 .
 */
public class DesignerSearchActivity extends NavigationBarActivity implements View.OnClickListener {

    private LinearLayout mLlSearch;
    private ImageView mIvSearchBack;
    private ClearEditText mCetSearch;
    private TextView mTextCancelSearch;
    private PullToRefreshLayout mPtrLayout;
    private PullListView mPlvContentView;
    private View mSearchLayout;
    private PopupWindow mSearchPopupWindow;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_designer_search;
    }

    @Override
    protected void initView() {
        super.initView();
        mSearchLayout = LayoutInflater.from(this).inflate(R.layout.activity_search_popup, null);
        mLlSearch = (LinearLayout) findViewById(R.id.ll_search);
        mIvSearchBack = (ImageView) findViewById(R.id.iv_search_back);
        mCetSearch = (ClearEditText) findViewById(R.id.cet_search);
        mPtrLayout = (PullToRefreshLayout) findViewById(R.id.ptr_layout);
        mPlvContentView = (PullListView) findViewById(R.id.plv_content_view);
    }

    @Override
    protected void initListener() {
        mIvSearchBack.setOnClickListener(this);
        mCetSearch.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search_back:
                finish();
                break;

            case R.id.cet_search:   /// 展示搜索页面 .
                String searchContent = mCetSearch.getText().toString().trim();
                openPopupWindow(searchContent);
                break;

            default:
                break;
        }
    }

    /**
     * 搜索页面弹窗
     *
     * @param searchContent 搜索的内容
     */
    private void openPopupWindow(String searchContent) {


        mSearchPopupWindow = new PopupWindow(mSearchLayout,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT);

        mSearchPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        mSearchPopupWindow.setTouchable(true);
        mSearchPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mSearchPopupWindow.showAtLocation(mIvSearchBack, Gravity.CENTER, 0, 0);
        mSearchPopupWindow.setFocusable(true);
        mSearchPopupWindow.update();


        final ImageView ivSearchCBack = (ImageView) mSearchLayout.findViewById(R.id.ect_searchc_back);
        final TextView tvSearchCancel = (TextView) mSearchLayout.findViewById(R.id.tv_ect_search_cancel);
        PinnedHeaderListView pinnedHeaderListView = (PinnedHeaderListView) mSearchLayout.findViewById(R.id.tv_ect_search_listview);
//        mClearEditText2 = (ClearEditText) mSearchLayout.findViewById(R.id.ect_et_search);

//        mClearEditText2.setText(searchTextData);
//        setSelection(mClearEditText2);
//        mSearchHoverCaseBeanArrayList.clear();
//        if (searchTextData != null && searchTextData.length() > 0) {
//
//            mSearchHoverCaseBeanArrayList.addAll(filterData(searchTextData));
//        }
//        if (mFuzzySearchAdapter == null) {
//            mFuzzySearchAdapter = new FuzzySearchAdapter(SearchActivity.this, mSearchHoverCaseBeanArrayList);
//        }
//        pinnedHeaderListView.setAdapter(mFuzzySearchAdapter);
//        mFuzzySearchAdapter.notifyDataSetChanged();
//
//        setOnClickListener(tvSearchCancel, ivSearchCBack);
//        setOnKeyListenerForClearEditText();
//        addTextChangedListenerForClearEditText();
//        setOnItemClickListenerForTvEctSearchListView(pinnedHeaderListView);

    }
}
