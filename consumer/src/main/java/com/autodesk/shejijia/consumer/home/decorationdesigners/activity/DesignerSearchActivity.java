package com.autodesk.shejijia.consumer.home.decorationdesigners.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.adapter.SeekDesignerAdapter;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.SeekDesignerBean;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter.FuzzySearchAdapter;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.FiltrateContentBean;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.SearchHoverCaseBean;
import com.autodesk.shejijia.consumer.utils.CharacterParser;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.uielements.ClearEditText;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PinnedHeaderListView;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullListView;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullToRefreshLayout;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-8-11 .
 * @file DesignerSearchActivity.java .
 * @brief 设计师搜索页面 .
 */
public class DesignerSearchActivity extends NavigationBarActivity implements
        View.OnClickListener, TextWatcher, View.OnKeyListener, AdapterView.OnItemClickListener, SeekDesignerAdapter.OnItemChatClickListener {

    private LinearLayout mLlSearch;
    private ImageView mIvSearchBack;
    private ClearEditText mCetSearchClick;
    private ClearEditText mCetSearchContent;
    private PullToRefreshLayout mPtrLayout;
    private PullListView mPlvContentView;
    private View mSearchLayout;
    private View mFooterView;

    /// 点击搜索框时弹出的popupWindow .
    private PopupWindow mSearchPopupWindow;
    private ImageView mIvSearchCBack;
    private TextView mTvSearchCancel;
    private PinnedHeaderListView mPinnedHeaderListView;
    private CharacterParser mCharacterParser;
    private FuzzySearchAdapter mFuzzySearchAdapter;

    private int LIMIT = 10;
    private int OFFSET = 0;
    private int screenWidth;
    private int screenHeight;
    private String mSearchKeywords;
    private FiltrateContentBean mFiltrateContentBean; /// 户型，面积，空间 .
    private boolean isFirstIn = false;

    private ImageView mIvEmptyIcon;
    private RelativeLayout mRlEmpty;
    private TextView mTvEmptyMessage;

    private SeekDesignerAdapter mSeekDesignerAdapter;
    private List<SearchHoverCaseBean> mSearchHoverCaseBeenList = new ArrayList<>();
    private List<SearchHoverCaseBean> mSearchHoverCaseBeanArrayList = new ArrayList<>();
    private List<SeekDesignerBean.DesignerListEntity> mDesignerListEntities = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_designer_search;
    }

    @Override
    protected void initView() {
        super.initView();
        mSearchLayout = LayoutInflater.from(this).inflate(R.layout.activity_search_popup, null);
        mFooterView = View.inflate(this, R.layout.view_empty_layout, null);

        mLlSearch = (LinearLayout) findViewById(R.id.ll_search);
        mIvSearchBack = (ImageView) findViewById(R.id.iv_search_back);
        mCetSearchClick = (ClearEditText) findViewById(R.id.cet_search);
        mPtrLayout = (PullToRefreshLayout) findViewById(R.id.ptr_layout);
        mPlvContentView = (PullListView) findViewById(R.id.plv_content_view);

        mCetSearchContent = (ClearEditText) mSearchLayout.findViewById(R.id.ect_et_search);
        mIvSearchCBack = (ImageView) mSearchLayout.findViewById(R.id.ect_searchc_back);
        mTvSearchCancel = (TextView) mSearchLayout.findViewById(R.id.tv_ect_search_cancel);
        mPinnedHeaderListView = (PinnedHeaderListView) mSearchLayout.findViewById(R.id.tv_ect_search_listview);

        mRlEmpty = (RelativeLayout) mFooterView.findViewById(R.id.rl_empty);
        mTvEmptyMessage = (TextView) mFooterView.findViewById(R.id.tv_empty_message);
        mIvEmptyIcon = ((ImageView) mFooterView.findViewById(R.id.iv_default_empty));
        mPlvContentView.addFooterView(mFooterView);

    }

    @Override
    protected void initListener() {
        mIvSearchBack.setOnClickListener(this);
        mCetSearchClick.setOnClickListener(this);

        mTvSearchCancel.setOnClickListener(this);
        mIvSearchCBack.setOnClickListener(this);

        mCetSearchContent.addTextChangedListener(this);
        mCetSearchContent.setOnKeyListener(this);
        mPinnedHeaderListView.setOnItemClickListener(this);
        mSeekDesignerAdapter.setOnItemChatClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        screenWidth = this.getWindowManager().getDefaultDisplay().getWidth();
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        mCharacterParser = CharacterParser.getInstance();

        mSearchKeywords = "";
        if (null == mSeekDesignerAdapter) {
            mSeekDesignerAdapter = new SeekDesignerAdapter(this, mDesignerListEntities);
        }
        mPlvContentView.setAdapter(mSeekDesignerAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search_back:
                finish();
                break;

            case R.id.cet_search:   /// 展示搜索页面 .
                String searchContent = mCetSearchClick.getText().toString().trim();
                openPopupWindow(searchContent);
                break;

            case R.id.tv_ect_search_cancel:
            case R.id.ect_searchc_back:
                cancelPopupWindowAndClearSearchContent();
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

        setSelection(mCetSearchContent);
        mSearchHoverCaseBeanArrayList.clear();
        if (searchContent != null && searchContent.length() > 0) {

            mSearchHoverCaseBeanArrayList.addAll(filterData(searchContent));
        }
        if (mFuzzySearchAdapter == null) {
            mFuzzySearchAdapter = new FuzzySearchAdapter(DesignerSearchActivity.this, mSearchHoverCaseBeanArrayList);
        }
        mPinnedHeaderListView.setAdapter(mFuzzySearchAdapter);
        mFuzzySearchAdapter.notifyDataSetChanged();

        mSearchPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        mSearchPopupWindow.setTouchable(true);
        mSearchPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mSearchPopupWindow.showAtLocation(mIvSearchBack, Gravity.CENTER, 0, 0);
        mSearchPopupWindow.setFocusable(true);
        mSearchPopupWindow.update();
    }

    private void setSelection(ClearEditText editText) {
        CharSequence charSequence = editText.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private List<SearchHoverCaseBean> filterData(String filterStr) {
        List<SearchHoverCaseBean> filterDateList = new ArrayList<SearchHoverCaseBean>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = mSearchHoverCaseBeenList;
        } else {
            filterDateList.clear();
            for (SearchHoverCaseBean sortModel : mSearchHoverCaseBeenList) {
                String name = sortModel.getValue();
                if (name.indexOf(filterStr.toString()) != -1 || mCharacterParser.getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }
        return filterDateList;
    }

    /**
     * 弹框消失,搜索条件制空.
     */
    private void cancelPopupWindowAndClearSearchContent() {
        if (null != mSearchPopupWindow) {
            mSearchPopupWindow.dismiss();
        }
        mCetSearchContent.setText("");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SearchHoverCaseBean selectedHoverCaseBean = mSearchHoverCaseBeanArrayList.get(position - 1);
        mCetSearchClick.setText(selectedHoverCaseBean.getValue());
        setSelection(mCetSearchClick);
        mFiltrateContentBean = new FiltrateContentBean();
        mSearchKeywords = "";
        switch (selectedHoverCaseBean.getType()) {
            case 0:
                mFiltrateContentBean.setHousingType(selectedHoverCaseBean.getKey());
                break;
            case 1:
                mFiltrateContentBean.setStyle(selectedHoverCaseBean.getKey());
                break;
            case 2:
                mFiltrateContentBean.setArea(selectedHoverCaseBean.getKey());
                break;
            default:
                break;
        }
        isFirstIn = true;
        cancelPopupWindowAndClearSearchContent();
    }

    /**
     * 搜索框内容改变监听.
     */
    @Override
    public void afterTextChanged(Editable s) {
        mSearchHoverCaseBeanArrayList.clear();
        String searchInPutString = s.toString().trim();
        mSearchHoverCaseBeanArrayList.addAll(filterData(searchInPutString));
        mFuzzySearchAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(DesignerSearchActivity.this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
            mSearchKeywords = mCetSearchContent.getText().toString().trim();
            mCetSearchClick.setText(mSearchKeywords);
            if (mSearchKeywords != null && mSearchKeywords.length() > 0) {
                try {
                    mSearchKeywords = URLEncoder.encode(mSearchKeywords, Constant.NetBundleKey.UTF_8);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            setSelection(mCetSearchClick);
            mFiltrateContentBean = null;
            isFirstIn = true;
            cancelPopupWindowAndClearSearchContent();
        }
        return false;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void OnItemChatClick(int position) {

    }
}
