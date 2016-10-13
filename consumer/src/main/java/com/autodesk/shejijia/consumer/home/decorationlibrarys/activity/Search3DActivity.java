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

        mSearchKeywords = "";
        if (userHome3DCaseAdapter == null) {
            userHome3DCaseAdapter = new UserHome3DCaseAdapter(this, case3DBeanList, this, screenWidth, screenHeight);
        }
        mPlvContentView.setAdapter(userHome3DCaseAdapter);
        userHome3DCaseAdapter.setOnItemImageHeadClickListener(this, this, this);
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

        MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        if (mMemberEntity != null) {
            member_id = mMemberEntity.getAcs_member_id();
            CustomProgress.show(this, "", false, null);
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
            LoginUtils.doLogin(this);
        }
////

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
     * 搜索按键监听.
     */
    private void setOnKeyListenerForClearEditText() {
        mCetSearchClick.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(Search3DActivity.this.getCurrentFocus().getWindowToken(),
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
        case3DBeanList = null;
        userHome3DCaseAdapter = null;
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
    private String hsUid, member_id;

    private ArrayList<Case3DLibraryListBean.CasesBean> case3DBeanList = new ArrayList<>();
    private UserHome3DCaseAdapter userHome3DCaseAdapter;
    private FiltrateContentBean mFiltrateContentBean;
    private Case3DLibraryListBean case3DLibraryListBean;
    private Intent mIntent;
}
