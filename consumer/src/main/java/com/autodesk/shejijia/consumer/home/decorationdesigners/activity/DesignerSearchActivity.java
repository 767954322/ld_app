package com.autodesk.shejijia.consumer.home.decorationdesigners.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.adapter.SeekDesignerAdapter;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.SeekDesignerBean;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.activity.SearchActivity;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter.FuzzySearchAdapter;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.FiltrateContentBean;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.SearchHoverCaseBean;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.consumer.utils.CharacterParser;
import com.autodesk.shejijia.shared.components.common.appglobal.ApiManager;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.appglobal.UrlMessagesContants;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.shared.components.common.uielements.ClearEditText;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PinnedHeaderListView;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullListView;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullToRefreshLayout;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.im.activity.ChatRoomActivity;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThread;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThreads;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatUtility;
import com.autodesk.shejijia.shared.components.im.manager.MPChatHttpManager;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        AdapterView.OnItemClickListener,
        SeekDesignerAdapter.OnItemChatClickListener,
        PullToRefreshLayout.OnRefreshListener {

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
    //    private int screenWidth;
//    private int screenHeight;
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
    private SeekDesignerBean mSeekDesignerBean;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_designer_search;
    }

    @Override
    protected void initView() {
        super.initView();
        mSearchLayout = LayoutInflater.from(this).inflate(R.layout.activity_search_popup, null);
        mFooterView = View.inflate(this, R.layout.view_empty_layout, null);

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

        mPtrLayout.setOnRefreshListener(this);
        mPlvContentView.setOnItemClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
//        screenWidth = this.getWindowManager().getDefaultDisplay().getWidth();
//        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        mCharacterParser = CharacterParser.getInstance();
        mCetSearchContent.setHint(UIUtils.getString(R.string.search_designer));

        initSearchData(0, AppJsonFileReader.getWorkingTime(this));
        initSearchData(1, AppJsonFileReader.getStyle(this));
        initSearchData(2, AppJsonFileReader.getPrice(this));

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
            mFuzzySearchAdapter = new FuzzySearchAdapter(DesignerSearchActivity.this,
                    mSearchHoverCaseBeanArrayList);
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
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        requestSearchData(0, 0);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        requestSearchData(OFFSET, 1);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mPinnedHeaderListView == parent) {
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
        } else if (mPlvContentView == parent) {
            String designer_id = mDesignerListEntities.get(position).getDesigner().getAcs_member_id();
            String hs_uid = mDesignerListEntities.get(position).getHs_uid();
            Intent intent = new Intent(DesignerSearchActivity.this, SeekDesignerDetailActivity.class);
            intent.putExtra(Constant.ConsumerDecorationFragment.designer_id, designer_id);
            intent.putExtra(Constant.ConsumerDecorationFragment.hs_uid, hs_uid);
            startActivity(intent);
        }
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

    /**
     * 请求访问网络的公用方法
     *
     * @param offset
     * @param state
     */
    private void requestSearchData(int offset, int state) {
        String area = null;
        String house_type = null;
        String style = null;
        String space = null;
        if (null != mFiltrateContentBean) {
            area = mFiltrateContentBean.getArea();
            house_type = mFiltrateContentBean.getHousingType();
            style = mFiltrateContentBean.getStyle();
            space = mFiltrateContentBean.getSpace();
        }

        area = TextUtils.isEmpty(area) ? "" : area;
        house_type = TextUtils.isEmpty(house_type) ? "" : house_type;
        style = TextUtils.isEmpty(style) ? "" : style;
        space = TextUtils.isEmpty(space) ? "" : space;

        getSearchDesignerList(mSearchKeywords, "01", area, house_type, style, space, "", "", offset, LIMIT, state);
    }

    /**
     * 请求网络，进行搜索
     */
    private void getSearchDesignerList(String searchKeywords,
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
                updateDesignerList(jsonObject, state);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                mPtrLayout.loadmoreFinish(PullToRefreshLayout.FAIL);

//                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.chatroom_audio_recording_erroralert_ok)}, null, DesignerSearchActivity.this,
//                        AlertView.Style.Alert, null).show();
                ApiStatusUtil.getInstance().apiStatuError(volleyError,DesignerSearchActivity.this);
                hideFooterView(mDesignerListEntities);
                mSeekDesignerAdapter.notifyDataSetChanged();
            }
        };

        MPServerHttpManager.getInstance().getCaseListData(
                custom_string_style,
                custom_string_type,
                searchKeywords,
                custom_string_area,
                custom_string_bedroom,
                taxonomy_id,
                custom_string_restroom,
                custom_string_form,
                offset, limit, callback);
    }

    /**
     * 获取搜索设计师列表网络数据,更新布局
     *
     * @param jsonObject
     * @param state
     */
    private void updateDesignerList(JSONObject jsonObject, int state) {
        try {
            String jsonString = GsonUtil.jsonToString(jsonObject);
            mSeekDesignerBean = GsonUtil.jsonToBean(jsonString, SeekDesignerBean.class);
            KLog.json(TAG, jsonString);
            switch (state) {
                case 0:
                    OFFSET = 10;
                    mDesignerListEntities.clear();
                    break;
                case 1:
                    OFFSET += 10;
                    break;
                default:
                    break;
            }
            List<SeekDesignerBean.DesignerListEntity> designerListEntities = mSeekDesignerBean.getDesigner_list();

            if (designerListEntities != null && designerListEntities.size() > 0) {
                mDesignerListEntities.addAll(designerListEntities);
            }
        } finally {
            hideFooterView(mDesignerListEntities);
            mSeekDesignerAdapter.notifyDataSetChanged();
            mPtrLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
        }
    }

    @Override
    public void OnItemChatClick(int position) {
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (memberEntity != null) {
            final String member_id = memberEntity.getAcs_member_id();
            final String designer_id = mDesignerListEntities.get(position).getDesigner().getAcs_member_id();
            final String hs_uid = mDesignerListEntities.get(position).getHs_uid();
            final String mMemberType = memberEntity.getMember_type();
            final String receiver_name = mDesignerListEntities.get(position).getNick_name();
            final String recipient_ids = member_id + "," + designer_id + "," + ApiManager.getAdmin_User_Id(ApiManager.RUNNING_DEVELOPMENT);

            MPChatHttpManager.getInstance().retrieveMultipleMemberThreads(recipient_ids, 0, 10, new OkStringRequest.OKResponseCallback() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    MPNetworkUtils.logError(TAG, volleyError);
                }

                @Override
                public void onResponse(String s) {

                    MPChatThreads mpChatThreads = MPChatThreads.fromJSONString(s);

                    Intent intent = new Intent(DesignerSearchActivity.this, ChatRoomActivity.class);
                    intent.putExtra(ChatRoomActivity.RECIEVER_USER_ID, designer_id);
                    intent.putExtra(ChatRoomActivity.RECIEVER_USER_NAME, receiver_name);
                    intent.putExtra(ChatRoomActivity.MEMBER_TYPE, mMemberType);
                    intent.putExtra(ChatRoomActivity.ACS_MEMBER_ID, member_id);

                    if (mpChatThreads != null && mpChatThreads.threads.size() > 0) {

                        MPChatThread mpChatThread = mpChatThreads.threads.get(0);
                        int assetId = MPChatUtility.getAssetIdFromThread(mpChatThread);
                        intent.putExtra(ChatRoomActivity.THREAD_ID, mpChatThread.thread_id);
                        intent.putExtra(ChatRoomActivity.ASSET_ID, assetId + "");
                        intent.putExtra(ChatRoomActivity.MEDIA_TYPE, UrlMessagesContants.mediaIdProject);

                    } else {
                        intent.putExtra(ChatRoomActivity.RECIEVER_HS_UID, hs_uid);
                        intent.putExtra(ChatRoomActivity.ASSET_ID, "");
                    }

                    startActivity(intent);
                }
            });
        } else {
            AdskApplication.getInstance().doLogin(this);
        }
    }

    /**
     * 是否隐藏底部布局.
     *
     * @param mDesignerListEntities 数据集合
     */
    private void hideFooterView(List<SeekDesignerBean.DesignerListEntity> mDesignerListEntities) {
        if (mDesignerListEntities != null && mDesignerListEntities.size() > 0) {
            mRlEmpty.setVisibility(View.GONE);
        } else {
            mRlEmpty.setVisibility(View.VISIBLE);
        }
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.photopicker_thumbnail_placeholder);
        mIvEmptyIcon.setImageBitmap(bmp);
        WindowManager wm = (WindowManager) DesignerSearchActivity.this.getSystemService(SearchActivity.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        android.view.ViewGroup.LayoutParams mRlEmptyLayoutParams = mRlEmpty.getLayoutParams();
        mRlEmptyLayoutParams.height = height - 10;
        mRlEmpty.getLayoutParams();
        mRlEmpty.setLayoutParams(mRlEmptyLayoutParams);
        mTvEmptyMessage.setText(UIUtils.getString(R.string.no_designer_case));
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
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // 第一次进入自动刷新
        if (isFirstIn) {
            mPtrLayout.autoRefresh();
            isFirstIn = false;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

}
