package com.autodesk.shejijia.consumer.home.homepage.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.activity.SeekDesignerActivity;
import com.autodesk.shejijia.consumer.home.decorationdesigners.activity.SeekDesignerDetailActivity;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.activity.CaseLibraryActivity;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.activity.CaseLibraryNewActivity;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.CaseLibraryBean;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.FiltrateContentBean;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.consumer.activity.IssueDemandActivity;
import com.autodesk.shejijia.consumer.personalcenter.consumer.adapter.UserHomeCaseAdapter;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.ConsumerEssentialInfoEntity;
import com.autodesk.shejijia.shared.components.common.appglobal.ApiManager;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.consumer.uielements.FloatingActionButton;
import com.autodesk.shejijia.consumer.uielements.FloatingActionMenu;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.utility.CommonUtils;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.LoginUtils;
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
public class UserHome2DFragment extends BaseFragment implements UserHomeCaseAdapter.OnItemImageHeadClickListener,/* AbsListView.OnScrollListener,*/ View.OnClickListener {

    private LinearLayout mLlDefaultView;

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
        mFloatingActionsMenu = (FloatingActionMenu) rootView.findViewById(R.id.add_menu_buttons);
        mLlDefaultView = (LinearLayout) rootView.findViewById(R.id.ll_default_view);
        ll_default_view = (LinearLayout) rootView.findViewById(R.id.ll_default_view);
        createCustomAnimation();
        initFloatingAction();

        mSignInNotificationReceiver = new SignInNotificationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadCastInfo.LOGIN_ACTIVITY_FINISHED);
        filter.addAction(BroadCastInfo.MPFITER_CASES);
        activity.registerReceiver(mSignInNotificationReceiver, filter);
    }

    @Override
    protected void initData() {
        mAdapter = new UserHomeCaseAdapter(getActivity(), casesEntities, getActivity(), screenWidth, screenHeight);
        mListView.setAdapter(mAdapter);

        setSwipeRefreshInfo();

        MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        if (mMemberEntity == null) {
            return;
        }
        member_id = mMemberEntity.getAcs_member_id();
        getConsumerInfoData(member_id);

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
        mIntent = new Intent(getActivity(), CaseLibraryNewActivity.class);
        mIntent.putExtra(Constant.CaseLibraryDetail.CASE_ID, case_id);
        activity.startActivity(mIntent);
    }

    /// Chat OnClickListener 聊天监听.
    @Override
    public void OnItemHomeChatClick(final int position) {
        CustomProgress.show(activity, "", false, null);
        MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        if (mMemberEntity != null) {
            final String designer_id = casesEntities.get(position).getDesigner_id();
            final String hs_uid = casesEntities.get(position).getHs_designer_uid();
            final String receiver_name = casesEntities.get(position).getDesigner_info().getNick_name();
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
                        MPChatHttpManager.getInstance().getThreadIdIfNotChatBefore(designer_id, member_id, new OkStringRequest.OKResponseCallback() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                MPNetworkUtils.logError(TAG, volleyError);
                                CustomProgress.cancelDialog();
                            }

                            @Override
                            public void onResponse(String s) {
                                CustomProgress.cancelDialog();
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
            CustomProgress.cancelDialog();
            LoginUtils.doLogin(getActivity());
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
                    LoginUtils.doLogin(getActivity());
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
                mNickNameConsumer = mConsumerEssentialInfoEntity.getNick_name();
                mNickNameConsumer = TextUtils.isEmpty(mNickNameConsumer) ? UIUtils.getString(R.string.anonymity) : mNickNameConsumer;
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
            }
        });
    }

//    /**
//     * Network request 获取案例信息.
//     *
//     * @param offset 页面
//     * @param limit  　每页条数
//     */
//    public void getCaseLibraryData(final int offset, final int limit) {
//        OkJsonRequest.OKResponseCallback callback = new OkJsonRequest.OKResponseCallback() {
//
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//                String jsonString = GsonUtil.jsonToString(jsonObject);
//                mCaseLibraryBean = GsonUtil.jsonToBean(jsonString, CaseLibraryBean.class);
//                updateViewFromCaseLibraryData(offset);
//            }
//
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                MPNetworkUtils.logError(TAG, volleyError);
//                if (getActivity() != null) {
//                    mPtrLayout.onRefreshComplete();
//                    mListView.onLoadMoreComplete();
//                }
//            }
//        };
//        MPServerHttpManager.getInstance().getCaseListData("", "", "", "", "", "01", "", "", offset, limit, callback);
//    }

    private long starttime = 0;

    public void getCaseLibraryData(final String custom_string_style, final String custom_string_type, final String custom_string_keywords,
                                   final String custom_string_area, final String custom_string_bedroom, final String taxonomy_id,
                                   final int offset, final int limit, final String custom_string_restroom, final String custom_string_form, final int state) {
        starttime = System.currentTimeMillis();
        OkJsonRequest.OKResponseCallback callback = new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                // 刷新和筛选的时候滚动到第一位
                if (offset == 0) {
                    mListView.setSelection(0);
                }
                String jsonString = GsonUtil.jsonToString(jsonObject);
                mCaseLibraryBean = GsonUtil.jsonToBean(jsonString, CaseLibraryBean.class);
                updateViewFromCaseLibraryData(offset);
                Log.i("UserHome2D", "2d案例响应时间：" + (System.currentTimeMillis() - starttime));
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
        MPServerHttpManager.getInstance().getCaseListData(custom_string_style, custom_string_type, custom_string_keywords,
                custom_string_area, custom_string_bedroom, taxonomy_id, custom_string_restroom, custom_string_form, offset, limit, callback);
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
                getCaseLibraryData(filtrateContentBean == null ? BLANK : filtrateContentBean.getStyle(), BLANK, BLANK,
                        filtrateContentBean == null ? BLANK : filtrateContentBean.getArea(), BLANK, "01", 0, LIMIT, BLANK,
                        filtrateContentBean == null ? BLANK : filtrateContentBean.getHousingType(), 0);
            }
        });
        mPtrLayout.setLastUpdateTimeRelateObject(this);
        mListView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
//                getCaseLibraryData(mOffset, LIMIT);
                getCaseLibraryData(filtrateContentBean == null ? BLANK : filtrateContentBean.getStyle(), BLANK, BLANK,
                        filtrateContentBean == null ? BLANK : filtrateContentBean.getArea(), BLANK, "01", mOffset, LIMIT, BLANK,
                        filtrateContentBean == null ? BLANK : filtrateContentBean.getHousingType(), 1);

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

        mShadeView.setTag(MENU_COLLAPSE_TAG);

        mFloatingActionsMenu.setClosedOnTouchOutside(true);
//        createCustomAnimation();
        /**
         * requirement btn发布设计需求
         */
        initRequirementBtn();

        MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        if (mMemberEntity != null && Constant.UerInfoKey.DESIGNER_TYPE.equals(mMemberEntity.getMember_type())) {
            /// hide the requirement btn .
            mFloatingActionsMenu.removeMenuButton(requirementButton);

        } else {

        }
        /**
         * find designer btn设计师
         */
        initFindDesignerBtn();

        /**
         * case library btn案例库
         */
        initCaseLibraryBtn();


        mFloatingActionsMenu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                if (opened) {
                    mShadeView.setVisibility(View.VISIBLE);
                } else {
                    mShadeView.setVisibility(View.GONE);
                }
            }
        });
        mShadeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFloatingActionsMenu.close(true);
            }
        });
    }

    private void initRequirementBtn() {
        if (null == requirementButton)
            requirementButton = new FloatingActionButton(activity);
        requirementButton.setLabelText(UIUtils.getString(R.string.requirements));
        requirementButton.setButtonSize(FloatingActionButton.SIZE_MINI);
        requirementButton.setImageResource(R.drawable.icon_release_requirements_normal);
        requirementButton.setColorNormal(Color.TRANSPARENT);
        requirementButton.setColorPressed(Color.TRANSPARENT);
        requirementButton.setTag(REQUIREMENT_BUTTON_TAG);
        mFloatingActionsMenu.addMenuButton(requirementButton);
        requirementButton.setOnClickListener(this);
    }

    private void initFindDesignerBtn() {
        if (null == findDesignerButton)
            findDesignerButton = new FloatingActionButton(activity);
        findDesignerButton.setLabelText(UIUtils.getString(R.string.find_designer));
        findDesignerButton.setButtonSize(FloatingActionButton.SIZE_MINI);
        findDesignerButton.setImageResource(R.drawable.icon_find_designer_normal);
        findDesignerButton.setColorNormal(Color.TRANSPARENT);
        findDesignerButton.setColorPressed(Color.TRANSPARENT);
        findDesignerButton.setTag(FIND_DESIGNER_BUTTON_TAG);
        mFloatingActionsMenu.addMenuButton(findDesignerButton);
        findDesignerButton.setOnClickListener(this);
    }

    private void initCaseLibraryBtn() {
        if (null == caseLibraryButton)
            caseLibraryButton = new FloatingActionButton(activity);
        caseLibraryButton.setLabelText(UIUtils.getString(R.string.case_library));
        caseLibraryButton.setButtonSize(FloatingActionButton.SIZE_MINI);
        caseLibraryButton.setImageResource(R.drawable.icon_case_library_normal);
        caseLibraryButton.setColorNormal(Color.TRANSPARENT);
        caseLibraryButton.setColorPressed(Color.TRANSPARENT);
        mFloatingActionsMenu.addMenuButton(caseLibraryButton);
        caseLibraryButton.setTag(CASE_LIBRARY_BUTTON_TAG);

        caseLibraryButton.setOnClickListener(this);
    }

    private void createCustomAnimation() {
        AnimatorSet set = new AnimatorSet();

        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(mFloatingActionsMenu.getMenuIconView(), "scaleX", 1.0f, 0.2f);
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(mFloatingActionsMenu.getMenuIconView(), "scaleY", 1.0f, 0.2f);

        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(mFloatingActionsMenu.getMenuIconView(), "scaleX", 0.2f, 1.0f);
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(mFloatingActionsMenu.getMenuIconView(), "scaleY", 0.2f, 1.0f);

        scaleOutX.setDuration(50);
        scaleOutY.setDuration(50);

        scaleInX.setDuration(150);
        scaleInY.setDuration(150);

        scaleInX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mFloatingActionsMenu.getMenuIconView().setImageResource(mFloatingActionsMenu.isOpened()
                        ? R.drawable.openfloat : R.drawable.closefloat);
            }
        });

        set.play(scaleOutX).with(scaleOutY);
        set.play(scaleInX).with(scaleInY).after(scaleOutX);
        set.setInterpolator(new OvershootInterpolator(2));

        mFloatingActionsMenu.setIconToggleAnimatorSet(set);
    }

    /**
     * 网络获取数据，更新页面
     *
     * @param offset
     */
    private void updateViewFromCaseLibraryData(int offset) {
        if (offset == 0) {
            casesEntities.clear();
            if (mCaseLibraryBean.getCount() == 0) {
                mLlDefaultView.setVisibility(View.VISIBLE);
            } else {
                mLlDefaultView.setVisibility(View.GONE);
            }
        }
        mOffset = offset + 10;
        //设置数据小于等于2的是不显示没有更多数据了
        if (mCaseLibraryBean.getCases().size() <= 2) {
            mListView.setNoLoadMoreHideView(true);
        }
        if (mCaseLibraryBean.getCases().size() > 0) {
            ll_default_view.setVisibility(View.GONE);
        } else {
            ll_default_view.setVisibility(View.VISIBLE);
        }
        casesEntities.addAll(mCaseLibraryBean.getCases());
        if (mCaseLibraryBean.getCases().size() < LIMIT) {
            mListView.setHasLoadMore(false);
        } else {
            mListView.setHasLoadMore(true);
        }
        Message msg = Message.obtain();
        msg.obj = offset;
        handler.sendMessage(msg);
//
//        if (offset == 0) {
//            casesEntities.clear();
//        }
//        mOffset = offset + 10;
//        if (mCaseLibraryBean.getCases().size()>0){
//            ll_default_view.setVisibility(View.GONE);
//        }else {
//            ll_default_view.setVisibility(View.VISIBLE);
//        }
//        casesEntities.addAll(mCaseLibraryBean.getCases());
//        if (mCaseLibraryBean.getCases().size() < LIMIT) {
//            mListView.setHasLoadMore(false);
//        } else {
//            mListView.setHasLoadMore(false);
//        }
//        Message msg = Message.obtain();
//        msg.obj = offset;
//        handler.sendMessage(msg);
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
                    mFloatingActionsMenu.removeMenuButton(requirementButton);

                } else {
                }

                setSwipeRefreshInfo();
                if (null == mMemberEntity) {
                    return;
                }
                member_id = mMemberEntity.getAcs_member_id();
                getConsumerInfoData(member_id);
            } else if (action.equalsIgnoreCase(BroadCastInfo.MPFITER_CASES)) {

                FiltrateContentBean filtrateContentBean = (FiltrateContentBean) intent.getSerializableExtra("FiltrateContentBean");

                UserHome2DFragment.this.filtrateContentBean = filtrateContentBean;

                getCaseLibraryData(filtrateContentBean == null ? BLANK : filtrateContentBean.getStyle(), BLANK, BLANK,
                        filtrateContentBean == null ? BLANK : filtrateContentBean.getArea(), BLANK, "01", 0, LIMIT, BLANK,
                        filtrateContentBean == null ? BLANK : filtrateContentBean.getHousingType(), 0);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mFloatingActionsMenu.removeAllMenuButtons();
        MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        if (mMemberEntity != null && Constant.UerInfoKey.CONSUMER_TYPE.equals(mMemberEntity.getMember_type())) {
            initRequirementBtn();
            initFindDesignerBtn();
            initCaseLibraryBtn();

        } else if (mMemberEntity != null && Constant.UerInfoKey.DESIGNER_TYPE.equals(mMemberEntity.getMember_type())) {
            initFindDesignerBtn();
            initCaseLibraryBtn();

        } else {
            initRequirementBtn();
            initFindDesignerBtn();
            initCaseLibraryBtn();

        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        setSwipeRefreshInfo();
        mAdapter.notifyDataSetChanged();
        if (CustomProgress.dialog != null && CustomProgress.dialog.isShowing()) {
            CustomProgress.cancelDialog();
        }
        if (null != mFloatingActionsMenu) {
            mFloatingActionsMenu.close(true);
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
    public static final String BLANK = "";
    private FiltrateContentBean filtrateContentBean;
    private RelativeLayout mListViewRootView;
    private FloatingActionMenu mFloatingActionsMenu;
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
    private LinearLayout ll_default_view;
    private String mNickNameConsumer;
    private String hsUid, member_id;
    private Intent mIntent;
    private CaseLibraryBean mCaseLibraryBean;
    private ConsumerEssentialInfoEntity mConsumerEssentialInfoEntity;
    private ArrayList<CaseLibraryBean.CasesEntity> casesEntities = new ArrayList<>();
}