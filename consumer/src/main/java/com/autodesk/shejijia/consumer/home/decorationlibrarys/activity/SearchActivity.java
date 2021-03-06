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
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.CaseLibraryBean;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.FiltrateContentBean;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.SearchHoverCaseBean;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.consumer.adapter.UserHomeCaseAdapter;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.consumer.utils.CharacterParser;
import com.autodesk.shejijia.shared.components.common.appglobal.ApiManager;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.shared.components.common.uielements.ClearEditText;
import com.autodesk.shejijia.consumer.uielements.pulltorefresh.PinnedHeaderListView;
import com.autodesk.shejijia.consumer.uielements.pulltorefresh.PullToRefreshLayout;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.LoginUtils;
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
public class SearchActivity extends NavigationBarActivity implements
        PullToRefreshLayout.OnRefreshListener,
        View.OnClickListener,
        UserHomeCaseAdapter.OnItemImageHeadClickListener {

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
        tvSearchCancel = (TextView)findViewById(R.id.tv_search_cancel);
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
        if (mUserHomeCaseAdapter == null) {
            mUserHomeCaseAdapter = new UserHomeCaseAdapter(this,
                    mCasesEntities, this,
                    screenWidth, screenHeight);
        }
        mPlvContentView.setAdapter(mUserHomeCaseAdapter);
        mUserHomeCaseAdapter.setOnItemImageHeadClickListener(this, this, this);
    }

    @Override
    protected void initListener() {
        mPtrLayout.setOnRefreshListener(this);
        mCetSearchClick.setOnClickListener(this); /// cancel search.
        mSearchBack.setOnClickListener(this);
        tvSearchCancel.setOnClickListener(this);
        setOnKeyListenerForClearEditText();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search_cancel:
                finish();
                break;
            case R.id.et_search:/// 搜索.
                break;
            case R.id.searchc_back:/// 返回.

                finish();
                break;
            default:
                break;
        }
    }

    /// 查看设计师详情页面.
    @Override
    public void OnItemImageHeadClick(int position) {
        String designer_id;
        String hsUid;
        Intent intent = new Intent(this, SeekDesignerDetailActivity.class);
        CaseLibraryBean.CasesEntity.DesignerInfoEntity designerInfoEntity = mCasesEntities.get(position).getDesigner_info();
        if (designerInfoEntity != null) {
            CaseLibraryBean.CasesEntity.DesignerInfoEntity.DesignerEntity designerEntity = designerInfoEntity.getDesigner();
            if (designerEntity != null) {
                designer_id = designerEntity.getAcs_member_id();
                intent.putExtra(Constant.ConsumerDecorationFragment.designer_id, designer_id);
            }
        }
        hsUid = mCasesEntities.get(position).getHs_designer_uid();
        intent.putExtra(Constant.ConsumerDecorationFragment.hs_uid, hsUid);
        startActivity(intent);
    }

    /// 进入案例详情页面.
    @Override
    public void OnItemCaseClick(int position) {
        String case_id = mCasesEntities.get(position).getId();
        mIntent = new Intent(this, CaseLibraryNewActivity.class);
        mIntent.putExtra(Constant.CaseLibraryDetail.CASE_ID, case_id);
        startActivity(mIntent);
    }

    /// 聊天.
    @Override
    public void OnItemHomeChatClick(final int position) {
        MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        if (mMemberEntity == null) {
            LoginUtils.doLogin(this);
            return;
        }
        final String member_id = mMemberEntity.getAcs_member_id();
        if (mMemberEntity != null) {
            final String designer_id = mCasesEntities.get(position).getDesigner_id();
            final String hs_uid = mCasesEntities.get(position).getHs_designer_uid();
            final String receiver_name = mCasesEntities.get(position).getDesigner_info().getNick_name();
            final String mMemberType = mMemberEntity.getMember_type();
            final String recipient_ids = member_id + "," + designer_id + "," + ApiManager.getAdmin_User_Id();

            MPChatHttpManager.getInstance().retrieveMultipleMemberThreads(recipient_ids, 0, 10, new OkStringRequest.OKResponseCallback() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    MPNetworkUtils.logError(TAG, volleyError);
                }

                @Override
                public void onResponse(String s) {

                    MPChatThreads mpChatThreads = MPChatThreads.fromJSONString(s);

                    final Intent intent = new Intent(SearchActivity.this, ChatRoomActivity.class);
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
                        SearchActivity.this.startActivity(intent);

                    } else {
                        MPChatHttpManager.getInstance().getThreadIdIfNotChatBefore(designer_id, member_id, new OkStringRequest.OKResponseCallback() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                MPNetworkUtils.logError(TAG, volleyError);
                            }

                            @Override
                            public void onResponse(String s) {
                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    String thread_id = jsonObject.getString("thread_id");
                                    intent.putExtra(ChatRoomActivity.ASSET_ID, "");
                                    intent.putExtra(ChatRoomActivity.RECIEVER_HS_UID, hs_uid);
                                    intent.putExtra(ChatRoomActivity.THREAD_ID, thread_id);
                                    SearchActivity.this.startActivity(intent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            });
        } else {
            LoginUtils.doLogin(SearchActivity.this);
        }
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
                ApiStatusUtil.getInstance().apiStatuError(volleyError, SearchActivity.this);
                hideFooterView(mCasesEntities);
                mUserHomeCaseAdapter.notifyDataSetChanged();
            }
        };
            MPServerHttpManager.getInstance().getCaseListData(
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
//    /**
//     * 搜索按键监听.
//     */
    private void setOnKeyListenerForClearEditText() {
        mCetSearchClick.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    mSearchKeywords = mCetSearchClick.getText().toString().trim();
//                    mCetSearchClick.setText(mSearchKeywords);
                    if (mSearchKeywords != null && mSearchKeywords.length() > 0) {
                        try {
                            mSearchKeywords = URLEncoder.encode(mSearchKeywords, Constant.NetBundleKey.UTF_8);
                            setSelection(mCetSearchClick);
                            mFiltrateContentBean = null;
                            mPtrLayout.autoRefresh();
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
            mCaseLibraryBean = GsonUtil.jsonToBean(jsonString, CaseLibraryBean.class);
            LogUtils.i(TAG, jsonString);
            switch (state) {
                case 0:
                    OFFSET = 10;
                    mCasesEntities.clear();
                    break;
                case 1:
                    OFFSET += 10;
                    break;
                default:
                    break;
            }
            List<CaseLibraryBean.CasesEntity> casesEntity =
                    mCaseLibraryBean.getCases();

            if (casesEntity != null && casesEntity.size() > 0) {
                mCasesEntities.addAll(casesEntity);
            }
        } finally {
            hideFooterView(mCasesEntities);
            mUserHomeCaseAdapter.notifyDataSetChanged();
            mPtrLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
        }
    }

    /**
     * 是否隐藏底部布局.
     *
     * @param list 数据集合
     */
    private void hideFooterView(List<CaseLibraryBean.CasesEntity> list) {
        if (list != null && list.size() > 0) {
            mRlEmpty.setVisibility(View.GONE);
        } else {
            mRlEmpty.setVisibility(View.VISIBLE);
        }
//        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.photopicker_thumbnail_placeholder);
//        mIvEmptyIcon.setImageBitmap(bmp);
        WindowManager wm = (WindowManager) SearchActivity.this.getSystemService(SearchActivity.WINDOW_SERVICE);
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
        mCasesEntities = null;
        mUserHomeCaseAdapter = null;
        mCharacterParser = null;
        mFiltrateContentBean = null;
        mSearchKeywords = null;

        if (mSearchLayout != null) {
            mSearchLayout = null;
        }
    }

    public static final String BLANK = "";

    /// 控件.
    private ClearEditText mCetSearchClick;
    private ListView mPlvContentView;
    private PullToRefreshLayout mPtrLayout;
    private ImageView mSearchBack;
    private RelativeLayout mRlEmpty;
    private TextView mTvEmptyMessage,tvSearchCancel;
    private ImageView mIvEmptyIcon;
    private View mSearchLayout;
    private View mFooterView;
    private int LIMIT = 10;
    private int OFFSET = 0;
    private int screenWidth;
    private int screenHeight;
    private String mSearchKeywords;

    private List<SearchHoverCaseBean> mSearchHoverCaseBeenList = new ArrayList<>();
    private ArrayList<CaseLibraryBean.CasesEntity> mCasesEntities = new ArrayList<>();
    private UserHomeCaseAdapter mUserHomeCaseAdapter;
    private FiltrateContentBean mFiltrateContentBean;
    private CaseLibraryBean mCaseLibraryBean;
    private CharacterParser mCharacterParser;
    private Intent mIntent;
}
