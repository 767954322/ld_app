package com.autodesk.shejijia.consumer.home.decorationdesigners.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.adapter.DesignerSearchAdapter;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.DesignerWorkTimeBean;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.FindDesignerBean;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.SearchHoverCaseBean;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.utils.CharacterParser;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.ClearEditText;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONObject;

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
        TextWatcher,
        View.OnClickListener,
        View.OnKeyListener,
        AdapterView.OnItemClickListener {

    private DesignerWorkTimeBean designerWorkTimeBean;
    private List<DesignerWorkTimeBean.RelateInformationListBean> mWorkTimeList = new ArrayList<>();
    private List<DesignerWorkTimeBean.RelateInformationListBean> mStyleList = new ArrayList<>();
    private List<DesignerWorkTimeBean.RelateInformationListBean> mCostList = new ArrayList<>();

    private ImageView mIvSearchBack;
    private ClearEditText mCetSearchClick;
    private ClearEditText mCetSearchContent;
    private View mSearchLayout;

    /// 点击搜索框时弹出的popupWindow .
    private PopupWindow mSearchPopupWindow;
    private ImageView mIvSearchCBack;
    private TextView mTvSearchCancel;
    private ListView mListView;
    private CharacterParser mCharacterParser;
    private DesignerSearchAdapter mDesignerSearchAdapter;

    //    private String mSearchKeywords;
//    private SearchDesignerBean mSearchDesignerBean; /// 户型，面积，空间 .
    private List<SearchHoverCaseBean> mSearchList = new ArrayList<>();
    private List<SearchHoverCaseBean> mRequestContent = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_designer_search;
    }

    @Override
    protected void initView() {
        super.initView();
        mSearchLayout = LayoutInflater.from(this).inflate(R.layout.activity_search_popup_designer, null);

        mIvSearchBack = (ImageView) findViewById(R.id.iv_search_back);
        mCetSearchClick = (ClearEditText) findViewById(R.id.cet_search);

        mCetSearchContent = (ClearEditText) mSearchLayout.findViewById(R.id.ect_et_search);
        mIvSearchCBack = (ImageView) mSearchLayout.findViewById(R.id.ect_searchc_back);
        mTvSearchCancel = (TextView) mSearchLayout.findViewById(R.id.tv_ect_search_cancel);
        mListView = (ListView) mSearchLayout.findViewById(R.id.tv_ect_search_listview);
    }

    @Override
    protected void initListener() {
        mIvSearchBack.setOnClickListener(this);
        mCetSearchClick.setOnClickListener(this);

        mTvSearchCancel.setOnClickListener(this);
        mIvSearchCBack.setOnClickListener(this);

        mCetSearchContent.addTextChangedListener(this);
        mCetSearchContent.setOnKeyListener(this);
        mListView.setOnItemClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mCharacterParser = CharacterParser.getInstance();
        mCetSearchContent.setHint(UIUtils.getString(R.string.search_designer));

        getDesignerWorkTime();
        getDesignerCost();
        getDesignerStyles();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search_back:
                finish();
                break;

            case R.id.cet_search:   /// 展示搜索页面 .
                openPopupWindow();
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
     */
    private void openPopupWindow() {

        mSearchPopupWindow = new PopupWindow(mSearchLayout,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT);

        String searchContent = mCetSearchClick.getText().toString().trim();

        mRequestContent.clear();

        if (TextUtils.isEmpty(searchContent)) {

            mRequestContent.addAll(filterData(searchContent));
        }
        if (mDesignerSearchAdapter == null) {
            mDesignerSearchAdapter = new DesignerSearchAdapter(DesignerSearchActivity.this,
                    mRequestContent);
        }

        mListView.setAdapter(mDesignerSearchAdapter);
        mDesignerSearchAdapter.notifyDataSetChanged();

        mSearchPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        mSearchPopupWindow.setTouchable(true);
        mSearchPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mSearchPopupWindow.showAtLocation(mIvSearchBack, Gravity.CENTER, 0, 0);
        mSearchPopupWindow.setFocusable(true);
        mSearchPopupWindow.update();
    }


    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private List<SearchHoverCaseBean> filterData(String filterStr) {
        List<SearchHoverCaseBean> filterDateList = new ArrayList<>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = mSearchList;
        } else {
            filterDateList.clear();
            for (SearchHoverCaseBean sortModel : mSearchList) {
                String name = sortModel.getValue();
                if (name.indexOf(filterStr.toString()) != -1
                        || mCharacterParser.getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }
        return filterDateList;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mListView == parent) {
            SearchHoverCaseBean selectedHoverCaseBean = mRequestContent.get(position);

//            String value; /// name .
//            String code;    /// code .

            String code = selectedHoverCaseBean.getCode();
            String name = selectedHoverCaseBean.getValue();

            String nick_name = "";
            String start_experience = "";
            String end_experience = "";
            String style_names = "";
            String design_price_code = "-1";

            switch (selectedHoverCaseBean.getType()) {
                case 0: /// workTime .
                    String[] workTime = code.split("-");
                    if (null != workTime) {
                        if (workTime.length >= 2) {
                            start_experience = workTime[0];
                            end_experience = workTime[1];
                        }
                        if (workTime.length == 1) {
                            start_experience = end_experience = workTime[0];
                        }
                    }
                    break;

                case 1: /// priceCode .
                    design_price_code = code;
                    break;

                case 2: /// style .
                    style_names = name;
                    break;

                default:
                    break;
            }

//            nick_name 昵称
//            style_names  风格
//            start_experience 工作年限开始时间
//            end_experience   工作年限结束时间
//            design_price_code 设计师设计费区间code
            FindDesignerBean findDesignerBean = new FindDesignerBean();
            findDesignerBean.setNick_name(nick_name);
            findDesignerBean.setStyle_names(style_names);
            findDesignerBean.setStart_experience(start_experience);
            findDesignerBean.setEnd_experience(end_experience);
            findDesignerBean.setDesign_price_code(design_price_code);

            finishSelf(findDesignerBean);
            cancelPopupWindowAndClearSearchContent();
        }
    }

    /**
     * 搜索框内容改变监听.
     */
    @Override
    public void afterTextChanged(Editable s) {
        mRequestContent.clear();
        String searchInPutString = s.toString().trim();
        mRequestContent.addAll(filterData(searchInPutString));
        mDesignerSearchAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(DesignerSearchActivity.this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
            String mSearchKeywords = mCetSearchContent.getText().toString().trim();
            mCetSearchClick.setText(mSearchKeywords);
            if (TextUtils.isEmpty(mSearchKeywords)) {
                mSearchKeywords = "";
            }
            FindDesignerBean findDesignerBean = new FindDesignerBean();
            findDesignerBean.setNick_name(mSearchKeywords);

            findDesignerBean.setStyle_names("");
            findDesignerBean.setStart_experience("");
            findDesignerBean.setEnd_experience("");
            findDesignerBean.setDesign_price_code("");
            finishSelf(findDesignerBean);
            cancelPopupWindowAndClearSearchContent();
            return true;
        }
        return false;
    }

    /**
     * setResult
     */
    private void finishSelf(FindDesignerBean findDesignerBean) {
        Intent intent = new Intent();
        intent.putExtra(Constant.CaseLibrarySearch.DESIGNER_FILTRATE, findDesignerBean);
        setResult(RESULT_OK, intent);
        finish();
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

    /**
     * 获取设计师从业年限.
     */
    public void getDesignerWorkTime() {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String jsonToString = GsonUtil.jsonToString(jsonObject);
                designerWorkTimeBean = GsonUtil.jsonToBean(jsonToString, DesignerWorkTimeBean.class);
                mWorkTimeList.addAll(designerWorkTimeBean.getRelate_information_list());
                forEachSearchData(0, mWorkTimeList);

            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, DesignerSearchActivity.this,
                        AlertView.Style.Alert, null).show();
            }
        };
        MPServerHttpManager.getInstance().getDesignerExperiences(okResponseCallback);
    }

    /**
     * 获取设计师设计费区间.
     */
    public void getDesignerCost() {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String jsonToString = GsonUtil.jsonToString(jsonObject);
                designerWorkTimeBean = GsonUtil.jsonToBean(jsonToString, DesignerWorkTimeBean.class);
                mCostList.addAll(designerWorkTimeBean.getRelate_information_list());
                forEachSearchData(1, mCostList);

            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, DesignerSearchActivity.this,
                        AlertView.Style.Alert, null).show();
            }
        };
        MPServerHttpManager.getInstance().getDesignerCost(okResponseCallback);
    }

    /**
     * 获取设计师设计风格.
     */
    public void getDesignerStyles() {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String jsonToString = GsonUtil.jsonToString(jsonObject);
                designerWorkTimeBean = GsonUtil.jsonToBean(jsonToString, DesignerWorkTimeBean.class);
                mStyleList.addAll(designerWorkTimeBean.getRelate_information_list());
                forEachSearchData(2, mStyleList);

            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, DesignerSearchActivity.this,
                        AlertView.Style.Alert, null).show();
            }
        };
        MPServerHttpManager.getInstance().getDesignerStyles(okResponseCallback);
    }

    /**
     * 搜索数据
     */
    private void forEachSearchData(int type, List<DesignerWorkTimeBean.RelateInformationListBean> list) {
        if (null == list) {
            return;
        }
        for (DesignerWorkTimeBean.RelateInformationListBean findDesignerBean : list) {
            SearchHoverCaseBean searchHoverCaseBean = new SearchHoverCaseBean();

            String name = findDesignerBean.getName();
            String description = findDesignerBean.getDescription();
            String code = findDesignerBean.getCode();
            description = TextUtils.isEmpty(description) ? "" : description;

            searchHoverCaseBean.setType(type);
            searchHoverCaseBean.setCode(code);
            searchHoverCaseBean.setValue(name);
            searchHoverCaseBean.setDescription(description);

            mSearchList.add(searchHoverCaseBean);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

}
