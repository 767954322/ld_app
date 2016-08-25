package com.autodesk.shejijia.consumer.home.homepage.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.activity.SeekDesignerActivity;
import com.autodesk.shejijia.consumer.home.decorationdesigners.activity.SeekDesignerDetailActivity;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.activity.CaseLibraryActivity;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.activity.CaseLibraryDetailActivity;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.CaseLibraryBean;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.consumer.activity.IssueDemandActivity;
import com.autodesk.shejijia.consumer.personalcenter.consumer.adapter.UserHomeCaseAdapter;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.ConsumerEssentialInfoEntity;
import com.autodesk.shejijia.shared.components.common.appglobal.ApiManager;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.shared.components.common.uielements.FloatingActionButton;
import com.autodesk.shejijia.shared.components.common.uielements.FloatingActionsMenu;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.utility.CommonUtils;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.im.activity.ChatRoomActivity;
import com.autodesk.shejijia.shared.components.im.constants.BroadCastInfo;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThread;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThreads;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatUtility;
import com.autodesk.shejijia.shared.components.im.manager.MPChatHttpManager;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.finalteam.loadingviewfinal.ListViewFinal;
import cn.finalteam.loadingviewfinal.OnDefaultRefreshListener;
import cn.finalteam.loadingviewfinal.OnLoadMoreListener;
import cn.finalteam.loadingviewfinal.PtrClassicFrameLayout;
import cn.finalteam.loadingviewfinal.PtrFrameLayout;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 2015/12/13 0025 9:47 .
 * @file UserHomeFragment  .
 * @brief 首页案例fragment, 展示案例 .
 */
public class UserHomeFragment extends BaseFragment implements UserHomeCaseAdapter.OnItemImageHeadClickListener,/* AbsListView.OnScrollListener,*/ View.OnClickListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        screenWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        screenHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        mListView = (ListViewFinal) rootView.findViewById(R.id.lv_home);
        mPtrLayout = (PtrClassicFrameLayout) rootView.findViewById(R.id.ptr_layout);
        mListViewRootView = (RelativeLayout) rootView.findViewById(R.id.listview_content);
        initFloatingAction();

        mSignInNotificationReceiver = new SignInNotificationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadCastInfo.LOGIN_ACTIVITY_FINISHED);
        activity.registerReceiver(mSignInNotificationReceiver, filter);
    }

    @Override
    protected void initData() {
        mAdapter = new UserHomeCaseAdapter(getActivity(), casesEntities, getActivity(), screenWidth, screenHeight);
        mListView.setAdapter(mAdapter);

        setSwipeRefreshInfo();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mAdapter.setOnItemImageHeadClickListener(this, this, this);
    }

    /// HeadIcon OnClickListener查看设计师详情页面 .
    @Override
    public void OnItemImageHeadClick(int position) {
        Intent intent = new Intent(activity, SeekDesignerDetailActivity.class);
        CaseLibraryBean.CasesEntity.DesignerInfoEntity designerInfoEntity = casesEntities.get(position).getDesigner_info();
        String designer_id = null;
        if (designerInfoEntity != null) {
            CaseLibraryBean.CasesEntity.DesignerInfoEntity.DesignerEntity designerEntity = designerInfoEntity.getDesigner();
            if (designerEntity != null) {
                designer_id = designerEntity.getAcs_member_id();
            }
        }
        hsUid = casesEntities.get(position).getHs_designer_uid();
        intent.putExtra(Constant.ConsumerDecorationFragment.designer_id, designer_id);
        intent.putExtra(Constant.ConsumerDecorationFragment.hs_uid, hsUid);
        startActivity(intent);
    }

    /// Case OnClickListener查看案例库详情页面 .
    @Override
    public void OnItemCaseClick(int position) {
        String case_id = casesEntities.get(position).getId();
        mIntent = new Intent(getActivity(), CaseLibraryDetailActivity.class);
        mIntent.putExtra(Constant.CaseLibraryDetail.CASE_ID, case_id);
        activity.startActivity(mIntent);
    }

    /// Chat OnClickListener 聊天监听.
    @Override
    public void OnItemHomeChatClick(final int position) {
        MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        if (mMemberEntity != null) {
            final String designer_id = casesEntities.get(position).getDesigner_id();
            final String hs_uid = casesEntities.get(position).getHs_designer_uid();
            final String receiver_name = casesEntities.get(position).getDesigner_info().getNick_name();
            final String mMemberType = mMemberEntity.getMember_type();
            final String recipient_ids = member_id + "," + designer_id + "," + ApiManager.getAdmin_User_Id(ApiManager.RUNNING_DEVELOPMENT);

            MPChatHttpManager.getInstance().retrieveMultipleMemberThreads(recipient_ids, 0, 10, new OkStringRequest.OKResponseCallback() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    MPNetworkUtils.logError(TAG, volleyError);
                }

                @Override
                public void onResponse(String s) {

                    MPChatThreads mpChatThreads = MPChatThreads.fromJSONString(s);

                    final Intent intent = new Intent(getActivity(), ChatRoomActivity.class);
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
                        getActivity().startActivity(intent);

                    } else {
                        MPChatHttpManager.getInstance().getThreadIdIfNotChatBefore(member_id, designer_id, new OkStringRequest.OKResponseCallback() {
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
                                    getActivity().startActivity(intent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            });
        } else {
            AdskApplication.getInstance().doLogin(getActivity());
        }

    }

    ///事件监听 .
    @Override
    public void onClick(View view) {
        switch ((String) view.getTag()) {
            case REQUIREMENT_BUTTON_TAG:     /// 跳入 IssueDemandActivity .
                MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
                if (mMemberEntity != null) {
                    mNickNameConsumer = TextUtils.isEmpty(mNickNameConsumer) ? UIUtils.getString(R.string.anonymous) : mNickNameConsumer;
                    Intent intent = new Intent(getActivity(), IssueDemandActivity.class);
                    intent.putExtra(Constant.ConsumerPersonCenterFragmentKey.NICK_NAME, mNickNameConsumer);
                    startActivity(intent);
                } else {
                    AdskApplication.getInstance().doLogin(getActivity());
                }
                break;

            case FIND_DESIGNER_BUTTON_TAG:  /// Jump SeekDesignerActivity查看设计师信息 .
                CommonUtils.launchActivity(activity, SeekDesignerActivity.class);
                break;

            case CASE_LIBRARY_BUTTON_TAG:    /// 查看案例库详情页面.
                CommonUtils.launchActivity(activity, CaseLibraryActivity.class);
                break;
        }
    }

    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int offset = (int) msg.obj;
            if (offset == 0) {
                mPtrLayout.onRefreshComplete();
            } else {
                mListView.onLoadMoreComplete();
            }
            mAdapter.notifyDataSetChanged();
        }
    };

    /**
     * 获取个人基本信息
     *
     * @param member_id
     * @brief For details on consumers .
     */
    public void getConsumerInfoData(String member_id) {
        MPServerHttpManager.getInstance().getConsumerInfoData(member_id, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String jsonString = GsonUtil.jsonToString(jsonObject);
                mConsumerEssentialInfoEntity = GsonUtil.jsonToBean(jsonString, ConsumerEssentialInfoEntity.class);
                String nickName = mConsumerEssentialInfoEntity.getNick_name();
                mNickNameConsumer = TextUtils.isEmpty(nickName) ? UIUtils.getString(R.string.anonymity) : nickName;
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
            }
        });
    }

    /**
     * Network request 获取案例信息.
     *
     * @param offset 页面
     * @param limit  　每页条数
     */
    public void getCaseLibraryData(final int offset, final int limit) {
        OkJsonRequest.OKResponseCallback callback = new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                String jsonString = GsonUtil.jsonToString(jsonObject);
                mCaseLibraryBean = GsonUtil.jsonToBean(jsonString, CaseLibraryBean.class);
                updateViewFromCaseLibraryData(offset);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                if (getActivity() != null) {
                    mPtrLayout.onRefreshComplete();
                    mListView.onLoadMoreComplete();
                }
            }
        };
        MPServerHttpManager.getInstance().getCaseListData("", "", "", "", "", "01", "", "", offset, limit, callback);
    }

    /**
     * 刷新,加载,自动刷新.
     */
    private void setSwipeRefreshInfo() {

        mRetryAlertView = new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, getActivity(),
                AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object obj, int position) {
                if (obj == mRetryAlertView) {
                    setSwipeRefreshInfo();
                }
            }
        });
        mPtrLayout.setOnRefreshListener(new OnDefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getCaseLibraryData(0, LIMIT);
            }
        });
        mPtrLayout.setLastUpdateTimeRelateObject(this);
        mListView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                getCaseLibraryData(mOffset, LIMIT);
            }
        });
        mPtrLayout.autoRefresh();
    }

    /// 悬浮窗设置.
    private void initFloatingAction() {
        mShadeView = new View(activity);
        mShadeView.setBackgroundColor(activity.getResources().getColor(R.color.shade_back_color));
        mShadeViewLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mListViewRootView.addView(mShadeView, mShadeViewLayoutParams);
        mShadeView.setVisibility(View.GONE);
        mFloatingActionsMenu = (FloatingActionsMenu) rootView.findViewById(R.id.add_menu_buttons);
        mShadeView.setTag(MENU_COLLAPSE_TAG);

        /**
         * requirement btn发布设计需求
         */
        requirementButton = new FloatingActionButton(activity);
        requirementButton.setTitle(UIUtils.getString(R.string.requirements));
        requirementButton.setSize(FloatingActionButton.SIZE_MORE_MINI);
        requirementButton.setBackgroundResource(R.drawable.icon_release_requirements_normal);
        requirementButton.setTag(REQUIREMENT_BUTTON_TAG);
        mFloatingActionsMenu.addButton(requirementButton);
        requirementButton.setOnClickListener(this);

        MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        if (mMemberEntity != null && Constant.UerInfoKey.DESIGNER_TYPE.equals(mMemberEntity.getMember_type())) {
            /// hide the requirement btn .
            mFloatingActionsMenu.removeButton(requirementButton);

        } else {

        }
        /**
         * find designer btn设计师
         */
        findDesignerButton = new FloatingActionButton(activity);
        findDesignerButton.setTitle(UIUtils.getString(R.string.find_designer));
        findDesignerButton.setSize(FloatingActionButton.SIZE_MORE_MINI);
        findDesignerButton.setBackgroundResource(R.drawable.icon_find_designer_normal);
        findDesignerButton.setTag(FIND_DESIGNER_BUTTON_TAG);
        findDesignerButton.setOnClickListener(this);

        /**
         * case library btn案例库
         */
        caseLibraryButton = new FloatingActionButton(activity);
        caseLibraryButton.setTitle(UIUtils.getString(R.string.case_library));
        caseLibraryButton.setSize(FloatingActionButton.SIZE_MORE_MINI);
        caseLibraryButton.setBackgroundResource(R.drawable.icon_case_library_normal);
        caseLibraryButton.setTag(CASE_LIBRARY_BUTTON_TAG);
        caseLibraryButton.setOnClickListener(this);

        mFloatingActionsMenu.addButton(caseLibraryButton);
        mFloatingActionsMenu.addButton(findDesignerButton);
        mFloatingActionsMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                /// open floating action Menu
                mShadeView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onMenuCollapsed() {
                /// hide floating action
                mShadeView.setVisibility(View.GONE);
            }
        });
        mShadeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFloatingActionsMenu.collapse();
            }
        });
    }

    /**
     * 网络获取数据，更新页面
     *
     * @param offset
     */
    private void updateViewFromCaseLibraryData(int offset) {
        if (offset == 0) {
            casesEntities.clear();
        }
        mOffset = offset + 10;
        casesEntities.addAll(mCaseLibraryBean.getCases());
        if (mCaseLibraryBean.getCases().size() < LIMIT) {
            mListView.setHasLoadMore(false);
        } else {
            mListView.setHasLoadMore(true);
        }
        Message msg = Message.obtain();
        msg.obj = offset;
        handler.sendMessage(msg);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mFloatingActionsMenu != null) {
            mFloatingActionsMenu.collapse();
        }
        MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        if (mMemberEntity == null) {
            return;
        }
        member_id = mMemberEntity.getAcs_member_id();
        getConsumerInfoData(member_id);
    }

    /**
     * 全局的广播接收者,用于处理登录后数据的操作
     */
    private class SignInNotificationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            if (action.equalsIgnoreCase(BroadCastInfo.LOGIN_ACTIVITY_FINISHED)) {

                MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
                if (mMemberEntity != null && Constant.UerInfoKey.DESIGNER_TYPE.equals(mMemberEntity.getMember_type())) {
                    /// hide the requirement btn .
                    mFloatingActionsMenu.removeButton(requirementButton);

                } else {
                }

                setSwipeRefreshInfo();

            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mSignInNotificationReceiver != null) {

            getActivity().unregisterReceiver(mSignInNotificationReceiver);
        }
    }

    private static final String REQUIREMENT_BUTTON_TAG = "requirement_button";
    private static final String FIND_DESIGNER_BUTTON_TAG = "find_designer_button";
    private static final String CASE_LIBRARY_BUTTON_TAG = "case_library_button";
    private final static String MENU_COLLAPSE_TAG = "menu_collapse";

    private RelativeLayout mListViewRootView;
    private FloatingActionsMenu mFloatingActionsMenu;
    private PtrClassicFrameLayout mPtrLayout;
    private ListViewFinal mListView;
    private AlertView mRetryAlertView;
    protected View mShadeView;
    private FloatingActionButton findDesignerButton, caseLibraryButton, requirementButton;
    private SignInNotificationReceiver mSignInNotificationReceiver;

    private LinearLayout.LayoutParams mShadeViewLayoutParams;
    private UserHomeCaseAdapter mAdapter;
    private int mOffset = 0;
    private int LIMIT = 10;
    private int screenWidth, screenHeight;

    private String mNickNameConsumer;
    private String hsUid, member_id;
    private Intent mIntent;
    private CaseLibraryBean mCaseLibraryBean;
    private ConsumerEssentialInfoEntity mConsumerEssentialInfoEntity;
    private ArrayList<CaseLibraryBean.CasesEntity> casesEntities = new ArrayList<>();
}