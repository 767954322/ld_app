package com.autodesk.shejijia.consumer.home.decorationlibrarys.activity;

import android.content.Intent;
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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.activity.SeekDesignerDetailActivity;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter.FuzzySearchAdapter;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.Case3DLibraryListBean;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.FiltrateContentBean;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.SearchHoverCaseBean;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.consumer.adapter.UserHome3DCaseAdapter;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.consumer.utils.CharacterParser;
import com.autodesk.shejijia.shared.components.common.appglobal.ApiManager;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.shared.components.common.uielements.ClearEditText;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PinnedHeaderListView;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullToRefreshLayout;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.im.activity.ChatRoomActivity;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThread;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThreads;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatUtility;
import com.autodesk.shejijia.shared.components.im.manager.MPChatHttpManager;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Malidong .
 * @version 1.0 .
 * @date 2015/12/25 0025 15:57 .
 * @file SearchActivity  .
 * @brief 搜索案例库页面 .
 */
public class Search3DActivity extends NavigationBarActivity implements
        PullToRefreshLayout.OnRefreshListener,
        View.OnClickListener,
        UserHome3DCaseAdapter.OnItemImageHeadClickListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        mCetSearchClick = (ClearEditText) findViewById(R.id.et_search);
        mPtrLayout = ((PullToRefreshLayout) findViewById(R.id.refresh_view));
        mPlvContentView = (ListView) findViewById(R.id.content_view);
        mSearchBack = (ImageView) findViewById(R.id.searchc_back);

        mFooterView = View.inflate(this, R.layout.view_empty_layout, null);
        mRlEmpty = (RelativeLayout) mFooterView.findViewById(R.id.rl_empty);
        mTvEmptyMessage = (TextView) mFooterView.findViewById(R.id.tv_empty_message);
        mIvEmptyIcon = ((ImageView) mFooterView.findViewById(R.id.iv_default_empty));
        mPlvContentView.addFooterView(mFooterView);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        screenWidth = this.getWindowManager().getDefaultDisplay().getWidth();
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        mCharacterParser = CharacterParser.getInstance();

        mSearchKeywords = "";
//        initSearchData(0, AppJsonFileReader.getRoomHall(this));
//        initSearchData(1, AppJsonFileReader.getStyle(this));
//        initSearchData(2, AppJsonFileReader.getArea(this));

        if (userHome3DCaseAdapter == null) {
            userHome3DCaseAdapter = new UserHome3DCaseAdapter(this, case3DBeanList, this, screenWidth, screenHeight);
        }
        mPlvContentView.setAdapter(userHome3DCaseAdapter);
        userHome3DCaseAdapter.setOnItemImageHeadClickListener(this, this, this);

        MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        if (mMemberEntity == null) {
            return;
        }
        member_id = mMemberEntity.getAcs_member_id();
    }

    @Override
    protected void initListener() {
        mPtrLayout.setOnRefreshListener(this);
        mCetSearchClick.setOnClickListener(this); /// cancel search.
        mSearchBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_search:/// 搜索.
                openPopupWindow(mCetSearchClick.getText().toString());
                break;
            case R.id.searchc_back:/// 返回.
                if (mCetSearchContent != null) {
                    mCetSearchContent.setText("");
                }

                finish();
                break;
            default:
                break;
        }
    }

    /// 查看设计师详情页面.
    @Override
    public void OnItemImageHeadClick(int position) {
        Case3DLibraryListBean.CasesBean casesBean = case3DBeanList.get(position);
        String acs_member_id = casesBean.getDesigner_info().getDesigner().getAcs_member_id();
        hsUid = casesBean.getHs_designer_uid();
        Intent intent = new Intent(this, SeekDesignerDetailActivity.class);
        intent.putExtra(Constant.ConsumerDecorationFragment.designer_id, acs_member_id);
        intent.putExtra(Constant.ConsumerDecorationFragment.hs_uid, hsUid);
        startActivity(intent);
    }

    /// 进入案例详情页面.
    @Override
    public void OnItemCaseClick(int position) {
        String design_asset_id = case3DBeanList.get(position).getDesign_asset_id();
        mIntent = new Intent(this, CaseLibraryDetail3DActivity.class);
        mIntent.putExtra(Constant.CaseLibraryDetail.CASE_ID, design_asset_id);
        this.startActivity(mIntent);
    }

    /// 聊天.
    @Override
    public void OnItemHomeChatClick(final int position) {
        CustomProgress.show(this, "", false, null);
        MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        if (mMemberEntity != null) {
            Case3DLibraryListBean.CasesBean casesBean = case3DBeanList.get(position);
            final int designer_id = casesBean.getDesigner_id();
            final String hs_uid = casesBean.getHs_designer_uid();
            final String receiver_name = casesBean.getDesigner_info().getNick_name();

//            final String designer_id = case3DLibraryListBean.getCases().get(position).getDesigner_id()+"";
//            final String hs_uid =  case3DLibraryListBean.getCases().get(position).getHs_designer_uid();
//            final String receiver_name = case3DLibraryListBean.getCases().get(position).getDesigner_info().getNick_name();
            final String mMemberType = mMemberEntity.getMember_type();
            final String recipient_ids = member_id + "," + designer_id + "," + ApiManager.getAdmin_User_Id();

            MPChatHttpManager.getInstance().retrieveMultipleMemberThreads(recipient_ids, 0, 10, new OkStringRequest.OKResponseCallback() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    MPNetworkUtils.logError(TAG, volleyError);
                    CustomProgress.cancelDialog();
                }

                @Override
                public void onResponse(String s) {
                    CustomProgress.cancelDialog();
                    MPChatThreads mpChatThreads = MPChatThreads.fromJSONString(s);

                    final Intent intent = new Intent(Search3DActivity.this, ChatRoomActivity.class);
                    intent.putExtra(ChatRoomActivity.RECIEVER_USER_ID, designer_id);
                    intent.putExtra(ChatRoomActivity.RECIEVER_USER_NAME, receiver_name);
                    intent.putExtra(ChatRoomActivity.MEMBER_TYPE, mMemberType);
                    intent.putExtra(ChatRoomActivity.ACS_MEMBER_ID, member_id);

                    if (mpChatThreads != null && mpChatThreads.threads.size() > 0) {

                        MPChatThread mpChatThread = mpChatThreads.threads.get(0);
                        int assetId = MPChatUtility.getAssetIdFromThread(mpChatThread);
                        intent.putExtra(ChatRoomActivity.THREAD_ID, mpChatThread.thread_id);
                        intent.putExtra(ChatRoomActivity.ASSET_ID, assetId + "");
                        intent.putExtra(ChatRoomActivity.RECIEVER_HS_UID, hs_uid);
                        Search3DActivity.this.startActivity(intent);

                    } else {
                        MPChatHttpManager.getInstance().getThreadIdIfNotChatBefore(designer_id + "", member_id, new OkStringRequest.OKResponseCallback() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                MPNetworkUtils.logError(TAG, volleyError);
                                CustomProgress.cancelDialog();
                            }

                            @Override
                            public void onResponse(String s) {
                                try {
                                    CustomProgress.cancelDialog();
                                    JSONObject jsonObject = new JSONObject(s);
                                    String thread_id = jsonObject.getString("thread_id");
                                    intent.putExtra(ChatRoomActivity.ASSET_ID, "");
                                    intent.putExtra(ChatRoomActivity.RECIEVER_HS_UID, hs_uid);
                                    intent.putExtra(ChatRoomActivity.THREAD_ID, thread_id);
                                    Search3DActivity.this.startActivity(intent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            });
        } else {
            AdskApplication.getInstance().doLogin(this);
        }
////

    }

    /**
     * 点击搜索表格行的Click事件
     *
     * @param pinnedHeaderListView
     */
    private void setOnItemClickListenerForTvEctSearchListView(PinnedHeaderListView pinnedHeaderListView) {
        pinnedHeaderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        });
    }

    /**
     * 请求访问网络的公用方法
     *
     * @param offset
     * @param state
     */
    private void requestSearchData(int offset, int state) {
        String area = (mFiltrateContentBean != null && mFiltrateContentBean.getArea() != null) ? mFiltrateContentBean.getArea() : BLANK;
        String string_form = (mFiltrateContentBean != null && mFiltrateContentBean.getHousingType() != null) ? mFiltrateContentBean.getHousingType() : BLANK;
        String style = (mFiltrateContentBean != null && mFiltrateContentBean.getStyle() != null) ? mFiltrateContentBean.getStyle() : BLANK;
        String space = (mFiltrateContentBean != null && mFiltrateContentBean.getSpace() != null) ? mFiltrateContentBean.getSpace() : BLANK;

        getCaseLibraryData(mSearchKeywords, "01", area, string_form, style, space, BLANK, BLANK, offset, LIMIT, state);
    }

    /// 获取数据.
    public void getCaseLibraryData(String custom_string_keywords,
                                   String taxonomy_id,
                                   String custom_string_area,
                                   String custom_string_form,
                                   String custom_string_style,
                                   String custom_string_type,
                                   String custom_string_restroom,
                                   String custom_string_bedroom,
                                   final int offset,
                                   final int limit,
                                   final int state) {
        OkJsonRequest.OKResponseCallback callback = new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                notifyConsumeHomeAdapter(jsonObject, state);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                mPtrLayout.loadmoreFinish(PullToRefreshLayout.FAIL);

//                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.chatroom_audio_recording_erroralert_ok)}, null, SearchActivity.this,
//                        AlertView.Style.Alert, null).show();
                ApiStatusUtil.getInstance().apiStatuError(volleyError, Search3DActivity.this);
                hideFooterView(case3DBeanList);
                userHome3DCaseAdapter.notifyDataSetChanged();
            }
        };

        MPServerHttpManager.getInstance().get3DCaseListData(  //标示 1说明是 3d搜索
                custom_string_style,
                custom_string_type,
                custom_string_keywords,
                custom_string_area,
                custom_string_bedroom,
                taxonomy_id,
                custom_string_restroom,
                custom_string_form,
                offset, limit, callback);


    }

    /**
     * 搜索数据
     *
     * @param type 类型
     * @param map  集合
     */
    private void initSearchData(int type, Map<String, String> map) {
        for (Map.Entry mapString : map.entrySet()) {
            String key = (String) mapString.getKey();
            String value = (String) mapString.getValue();

            SearchHoverCaseBean searchHoverCaseBean = new SearchHoverCaseBean();
            searchHoverCaseBean.setType(type);
            searchHoverCaseBean.setKey(key);
            searchHoverCaseBean.setValue(value);

            mSearchHoverCaseBeenList.add(searchHoverCaseBean);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // 第一次进入自动刷新
        if (isFirstIn) {
            mPtrLayout.autoRefresh();
            isFirstIn = false;
        }
    }

    /**
     * 打开PopupWindow窗口
     *
     * @param searchTextData
     */
    private void openPopupWindow(String searchTextData) {

        mSearchLayout = LayoutInflater.from(this).inflate(R.layout.activity_search_popup, null);
        final ImageView ivSearchCBack = (ImageView) mSearchLayout.findViewById(R.id.ect_searchc_back);
        final TextView tvSearchCancel = (TextView) mSearchLayout.findViewById(R.id.tv_ect_search_cancel);
        PinnedHeaderListView pinnedHeaderListView = (PinnedHeaderListView) mSearchLayout.findViewById(R.id.tv_ect_search_listview);
        mCetSearchContent = (ClearEditText) mSearchLayout.findViewById(R.id.ect_et_search);

        mCetSearchContent.setText(searchTextData);
        setSelection(mCetSearchContent);
        mSearchHoverCaseBeanArrayList.clear();
        if (searchTextData != null && searchTextData.length() > 0) {

            mSearchHoverCaseBeanArrayList.addAll(filterData(searchTextData));
        }
        if (mFuzzySearchAdapter == null) {
            mFuzzySearchAdapter = new FuzzySearchAdapter(Search3DActivity.this, mSearchHoverCaseBeanArrayList);
        }
        pinnedHeaderListView.setAdapter(mFuzzySearchAdapter);
        mFuzzySearchAdapter.notifyDataSetChanged();

        setOnClickListener(tvSearchCancel, ivSearchCBack);
        setOnKeyListenerForClearEditText();
        addTextChangedListenerForClearEditText();
        setOnItemClickListenerForTvEctSearchListView(pinnedHeaderListView);

        mSearchPopupWindow = new PopupWindow(mSearchLayout, android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
        mSearchPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        mSearchPopupWindow.setTouchable(true);
        mSearchPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mSearchPopupWindow.showAtLocation(mSearchBack, Gravity.CENTER, 0, 0);
        mSearchPopupWindow.setFocusable(true);
        mSearchPopupWindow.update();
    }

    /**
     * 设置取消和返回的监听.
     *
     * @param textView
     * @param imageView
     */
    private void setOnClickListener(TextView textView, ImageView imageView) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelPopupWindowAndClearSearchContent();
                mCetSearchContent.setText("");
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelPopupWindowAndClearSearchContent();
                mCetSearchContent.setText("");
                Search3DActivity.this.finish();
            }
        });
    }

    /**
     * 搜索框内容改变监听.
     */
    private void addTextChangedListenerForClearEditText() {
        mCetSearchContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mSearchHoverCaseBeanArrayList.clear();
                String searchInPutString = s.toString().trim();
                mSearchHoverCaseBeanArrayList.addAll(filterData(searchInPutString));
                mFuzzySearchAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 搜索按键监听.
     */
    private void setOnKeyListenerForClearEditText() {
        mCetSearchContent.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(Search3DActivity.this.getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    mSearchKeywords = mCetSearchContent.getText().toString().trim();
                    mCetSearchClick.setText(mSearchKeywords);
                    if (mSearchKeywords != null && mSearchKeywords.length() > 0) {
                        try {
                            mSearchKeywords = URLEncoder.encode(mSearchKeywords, Constant.NetBundleKey.UTF_8);
                            setSelection(mCetSearchClick);
                            mFiltrateContentBean = null;
                            isFirstIn = true;
                            cancelPopupWindowAndClearSearchContent();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }


                }
                return false;
            }
        });
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
                name = name.toLowerCase();
                filterStr = filterStr.toLowerCase();
                if (name.indexOf(filterStr.toString()) != -1 || mCharacterParser.getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }
        return filterDateList;
    }

    /// 加载更多.
    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        requestSearchData(OFFSET, 1);
    }

    /// 刷新.
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        requestSearchData(0, 0);
    }

    /**
     * 更新适配数据
     *
     * @param jsonObject json数据
     * @param state      状态
     */
    private void notifyConsumeHomeAdapter(JSONObject jsonObject, int state) {
        try {
            String jsonString = GsonUtil.jsonToString(jsonObject);
            case3DLibraryListBean = GsonUtil.jsonToBean(jsonString, Case3DLibraryListBean.class);

            LogUtils.i(TAG, jsonString);
            switch (state) {
                case 0:
                    OFFSET = 10;
                    case3DBeanList.clear();
                    break;
                case 1:
                    OFFSET += 10;
                    break;
                default:
                    break;
            }
            List<Case3DLibraryListBean.CasesBean> cases = case3DLibraryListBean.getCases();
            if (cases != null && cases.size() > 0) {
                case3DBeanList.addAll(cases);
            }
        } finally {
            hideFooterView(case3DBeanList);
            userHome3DCaseAdapter.notifyDataSetChanged();
            mPtrLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
        }
    }

    /**
     * 是否隐藏底部布局.
     *
     * @param list 数据集合
     */
    private void hideFooterView(ArrayList<Case3DLibraryListBean.CasesBean> list) {
        if (list != null && list.size() > 0) {
            mRlEmpty.setVisibility(View.GONE);
        } else {
            mRlEmpty.setVisibility(View.VISIBLE);
        }
//        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.photopicker_thumbnail_placeholder);
//        mIvEmptyIcon.setImageBitmap(bmp);
        WindowManager wm = (WindowManager) Search3DActivity.this.getSystemService(Search3DActivity.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        android.view.ViewGroup.LayoutParams mRlEmptyLayoutParams = mRlEmpty.getLayoutParams();
        mRlEmptyLayoutParams.height = height - 10;
        mRlEmpty.getLayoutParams();
        mRlEmpty.setLayoutParams(mRlEmptyLayoutParams);
        mTvEmptyMessage.setText(UIUtils.getString(R.string.not_found));
    }

    private void setSelection(ClearEditText editText) {
        CharSequence charSequence = editText.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchHoverCaseBeenList = null;
        mSearchHoverCaseBeanArrayList = null;
        case3DBeanList = null;
        mFuzzySearchAdapter = null;
        userHome3DCaseAdapter = null;
        mCharacterParser = null;
        mFiltrateContentBean = null;
        mSearchKeywords = null;
        if (mSearchPopupWindow != null) {
            mSearchPopupWindow.dismiss();
        }
        if (mSearchLayout != null) {
            mSearchLayout = null;
        }
    }

    public static final String BLANK = "";

    /// 控件.
    private ClearEditText mCetSearchClick;
    private ClearEditText mCetSearchContent;
    private ListView mPlvContentView;
    private PullToRefreshLayout mPtrLayout;
    private ImageView mSearchBack;
    private RelativeLayout mRlEmpty;
    private TextView mTvEmptyMessage;
    private ImageView mIvEmptyIcon;
    /// 点击搜索框时弹出的popupWindow .
    private PopupWindow mSearchPopupWindow;
    private View mSearchLayout;
    private View mFooterView;
    private int LIMIT = 10;
    private int OFFSET = 0;
    private int screenWidth;
    private int screenHeight;
    private String mSearchKeywords;
    private boolean isFirstIn = false;
    private String hsUid, member_id;
    private List<SearchHoverCaseBean> mSearchHoverCaseBeenList = new ArrayList<>();
    private List<SearchHoverCaseBean> mSearchHoverCaseBeanArrayList = new ArrayList<>();
    //    private ArrayList<CaseLibraryBean.CasesEntity> mCasesEntities = new ArrayList<>();
//    private UserHomeCaseAdapter mUserHomeCaseAdapter;
    private ArrayList<Case3DLibraryListBean.CasesBean> case3DBeanList = new ArrayList<>();
    private UserHome3DCaseAdapter userHome3DCaseAdapter;
    private FiltrateContentBean mFiltrateContentBean;
    //    private CaseLibraryBean mCaseLibraryBean;
    private Case3DLibraryListBean case3DLibraryListBean;
    private FuzzySearchAdapter mFuzzySearchAdapter;
    private CharacterParser mCharacterParser;
    private Intent mIntent;
}
