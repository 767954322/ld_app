package com.autodesk.shejijia.shared.components.im.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.im.activity.ChatRoomActivity;
import com.autodesk.shejijia.shared.components.im.activity.ImageChatRoomActivity;
import com.autodesk.shejijia.shared.components.im.activity.MPFileThreadListActivity;
import com.autodesk.shejijia.shared.components.im.adapter.ThreadListAdapter;
import com.autodesk.shejijia.shared.components.im.constants.BroadCastInfo;
import com.autodesk.shejijia.shared.components.im.constants.MPChatConstants;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatEntityInfo;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThread;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThreads;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatUser;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatUtility;
import com.autodesk.shejijia.shared.components.im.manager.MPChatHttpManager;
import com.autodesk.shejijia.shared.framework.AdskApplication;

import java.util.ArrayList;

import cn.finalteam.loadingviewfinal.DefaultLoadMoreView;
import cn.finalteam.loadingviewfinal.ListViewFinal;
import cn.finalteam.loadingviewfinal.OnDefaultRefreshListener;
import cn.finalteam.loadingviewfinal.OnLoadMoreListener;
import cn.finalteam.loadingviewfinal.PtrClassicFrameLayout;
import cn.finalteam.loadingviewfinal.PtrFrameLayout;


public class MPThreadListFragment extends Fragment implements View.OnClickListener,
        ThreadListAdapter.ThreadListAdapterInterface,
        AdapterView.OnItemClickListener {
    public static final String ISFILEBASE = "IsFileBase";
    public static final String MEMBERID = "Memeber_Id";
    public static final String MEMBERTYPE = "Memeber_Type";
    public static final String LIST_TYPE = "list_type";//聊天列表类型

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutResId(), container, false);
        initData(savedInstanceState);
        initView(rootView);
        registerBroadCast();
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;   //this is required although seems not so obvious

        mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        if (mMemberEntity == null) {
            return;
        }
//        xToken = mMemberEntity.getHs_accesstoken();
//        memType = mMemberEntity.getMember_type();
//        acsToken = mMemberEntity.getAcs_token();
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(mNewMessageBroadCastReceiver);
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(MPThreadListFragment.ISFILEBASE, mIsFileBase);
        outState.putString(MPThreadListFragment.MEMBERID, mMemberId);
        outState.putString(MPThreadListFragment.MEMBERTYPE, mMemberType);
        outState.putString(MPThreadListFragment.LIST_TYPE, mChatListType);
    }


    public void onFragmentShown() {
        refresh();
    }

    private void initView(View rootView) {
        mPtrLayout = (PtrClassicFrameLayout) rootView.findViewById(R.id.ptr_layout);
        mThreadListView = (ListViewFinal) rootView.findViewById(R.id.thread_list);
        mProgressbar = (ProgressBar) rootView.findViewById(R.id.threadlist_progressbar);
        //  mThreadListView.setOnScrollListener(this);
        mThreadListView.setOnItemClickListener(this);

        mThreadListAdapter = new ThreadListAdapter(mActivity, this, mIsFileBase);
        mThreadListView.setAdapter(mThreadListAdapter);
        setSwipeRefreshInfo();
        //施工端 顶部topBar menu 设置
        if (mActivity.getPackageName().contains("enterprise")) {
            setHasOptionsMenu(true);
            mThreadListView.setDividerHeight(1);
            mThreadListView.setDivider(ContextCompat.getDrawable(mActivity, R.drawable.linearlayout_divider_hor_shape));
        }
    }


    private void initData(Bundle savedInstanceState) {
        Bundle bundle;
        if (savedInstanceState != null)
            bundle = savedInstanceState;
        else
            bundle = this.getArguments();

        mIsFileBase = bundle.getBoolean(MPThreadListFragment.ISFILEBASE, false);
        mMemberId = bundle.getString(MPThreadListFragment.MEMBERID, "");
        mMemberType = bundle.getString(MPThreadListFragment.MEMBERTYPE, "");
        mChatListType = bundle.getString(MPThreadListFragment.LIST_TYPE, "");

    }


    private int getLayoutResId() {
        return R.layout.fragment_thread_list;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.nav_right_button) {
            openFileThreadActivity();

        }
    }

    // ThreadListAdapterInterface
    @Override
    public int getThreadCount() {
        return mThreadList.size();
    }

    @Override
    public MPChatThread getThreadObjectForIndex(int index) {
        if (index < mThreadList.size())
            return mThreadList.get(index);

        return null;
    }

    @Override
    public Boolean isUserConsumer() {
        return Constant.UerInfoKey.CONSUMER_TYPE.equals(mMemberType);
    }

    @Override
    public int getLoggedInUserId() {
        return Integer.parseInt(mMemberId);
    }

    @Override
    public String getChatListType() {
        return mChatListType;
    }

    //
//    //OnScrollListener
//    @Override
//    public void onScrollStateChanged(AbsListView view, int scrollState) {
//        switch (scrollState) {
//            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
//
//                // if no more threads are to be fetched, let's not make another request for same
//                if (view.getLastVisiblePosition() == (mThreadList.size() - 1) && !mThreadsExhausted)
//                  //  getMemberThreadsForOffset(mThreadList.size(), LIMIT);
//                break;
//        }
//    }

//    @Override
//    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//    }

    //AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (view.getClass().toString().equalsIgnoreCase(DefaultLoadMoreView.class.toString()))
            return;

        Intent intent;
        if (mIsFileBase)
            intent = new Intent(mActivity, ImageChatRoomActivity.class);
        else
            intent = new Intent(mActivity, ChatRoomActivity.class);

        mThreadListAdapter.hideUnreadCountForRow(view);
        MPChatThread thread = getThreadObjectForIndex(position);
        MPChatUser user = MPChatUtility.getComplimentryUserFromThread(thread, mMemberId);
        intent.putExtra(ChatRoomActivity.THREAD_ID, thread.thread_id);
        intent.putExtra(ChatRoomActivity.RECIEVER_USER_NAME, user.name);
        intent.putExtra(ChatRoomActivity.RECIEVER_USER_ID, user.user_id);
        intent.putExtra(ChatRoomActivity.ACS_MEMBER_ID, mMemberId);
        intent.putExtra(ChatRoomActivity.MEMBER_TYPE, mMemberType);
        if (mChatListType.equalsIgnoreCase(MPChatConstants.BUNDLE_VALUE_GROUP_CHAT_LIST)){
            intent.putExtra(ChatRoomActivity.CHAT_TYPE,MPChatConstants.BUNDLE_VALUE_GROUP_CHAT);
        }

        int assetId = MPChatUtility.getAssetIdFromThread(thread);
        intent.putExtra(ChatRoomActivity.ASSET_ID, String.valueOf(assetId));

        String mediaType = MPChatUtility.getMediaTypeFromThread(thread);
        intent.putExtra(ChatRoomActivity.MEDIA_TYPE, mediaType);
        intent.putExtra(ChatRoomActivity.PROJECT_TITLE, MPChatUtility.getAssetNameFromThread(thread));
        if (mIsFileBase) {
            intent.putExtra(ImageChatRoomActivity.SERVERFILEURL, MPChatUtility.getFileUrlFromThread(thread));

            if (thread.entity != null && thread.entity.entityInfos != null && thread.entity.entityInfos.size() > 0) {
                MPChatEntityInfo entityInfo = thread.entity.entityInfos.get(0);
                String fileID = entityInfo.entity_id;
                int xCoordinate = entityInfo.x_coordinate;
                int yCoordinate = entityInfo.y_coordinate;
                intent.putExtra(ImageChatRoomActivity.ABSOLUTEX, xCoordinate);
                intent.putExtra(ImageChatRoomActivity.ABSOLUTEY, yCoordinate);
                intent.putExtra(ImageChatRoomActivity.FILEID, fileID);
            }

        }
        mActivity.startActivity(intent);
    }


    private void onNewMessageReceived(Intent intent) {
        refresh();
    }


    private void openFileThreadActivity() {
        Intent intent = new Intent(mActivity, MPFileThreadListActivity.class);

        intent.putExtra(MPFileThreadListActivity.MEMBERID, mMemberId);
        intent.putExtra(MPFileThreadListActivity.MEMBERTYPE, mMemberType);

        mActivity.startActivity(intent);
    }

    private void getMemberThreadsForOffset(final int offset, int limit) {
        if (mIsNextPageRequestRunning)
            return;

        mIsNextPageRequestRunning = true;

        OkStringRequest.OKResponseCallback callback = new OkStringRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mPtrLayout.onRefreshComplete();
                mThreadListView.onLoadMoreComplete();
                MPNetworkUtils.logError("MPThreadListFragment", error);
                mIsNextPageRequestRunning = false;
                mProgressbar.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(String response) {
                mPtrLayout.onRefreshComplete();
                mThreadListView.onLoadMoreComplete();
                mIsNextPageRequestRunning = false;
                if (offset == 0) {
                    mThreadList.clear();
                    mThreadListAdapter.notifyDataSetChanged();
                }

                MPChatThreads threads = MPChatThreads.fromJSONString(response);
                onThreadsReceived(threads);

                mProgressbar.setVisibility(View.GONE);
            }
        };
        if (AdskApplication.getInstance().getMemberEntity() != null) {
            mMemberId = AdskApplication.getInstance().getMemberEntity().getAcs_member_id();
            mMemberType = AdskApplication.getInstance().getMemberEntity().getMember_type();
        }
        MPChatHttpManager.getInstance().retrieveMemberThreads(mMemberId, mIsFileBase, offset, limit, callback);
    }

    private void onThreadsReceived(MPChatThreads threads) {
        ArrayList<MPChatThread> threadArray = threads.threads;
        insertThreads(threadArray);

        mThreadsExhausted = threadArray.size() < LIMIT;
    }


    private void insertThreads(ArrayList<MPChatThread> threads) {
        mThreadList.addAll(threads);
        mThreadListAdapter.notifyDataSetChanged();
    }


    private void registerBroadCast() {
        mNewMessageBroadCastReceiver = new NewMessageBroadCastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadCastInfo.RECEVIER_RECEIVERMESSAGE);
        getActivity().registerReceiver(mNewMessageBroadCastReceiver, filter);
    }

    private void refresh() {
        if (mMemberId != null) {

            getMemberThreadsForOffset(0, LIMIT);
        }
    }


    class NewMessageBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BroadCastInfo.RECEVIER_RECEIVERMESSAGE))
                onNewMessageReceived(intent);
        }
    }

    /**
     * 刷新,加载,自动刷新.
     */
    private void setSwipeRefreshInfo() {
        mPtrLayout.setOnRefreshListener(new OnDefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
//                mPtrLayout.onRefreshComplete();
//                mThreadListView.onLoadMoreComplete();
                Log.d("loadMore", "onRefreshBegin");
                refresh();

            }
        });
        mPtrLayout.setLastUpdateTimeRelateObject(this);
        mThreadListView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                mThreadListView.setHasLoadMore(true);
                Log.d("loadMore", "loadMore");
                getMemberThreadsForOffset(mThreadList.size(), LIMIT);
            }
        });
        mPtrLayout.autoRefresh();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.chat_hotspot_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_chat_hotspot) {
            MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
            Intent intent = new Intent(mActivity, MPFileThreadListActivity.class);
            intent.putExtra(MPFileThreadListActivity.MEMBERID, memberEntity.getAcs_member_id());
            intent.putExtra(MPFileThreadListActivity.MEMBERTYPE, memberEntity.getMember_type());
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private PtrClassicFrameLayout mPtrLayout;
    private static final int LIMIT = 20;

    private String mMemberId;
    private String mMemberType;
    private String mChatListType;
    private boolean mIsFileBase;

    private ArrayList<MPChatThread> mThreadList = new ArrayList<MPChatThread>();
    private boolean mThreadsExhausted;
    private NewMessageBroadCastReceiver mNewMessageBroadCastReceiver;
    private Activity mActivity;
    private boolean mIsNextPageRequestRunning = false;

    private ThreadListAdapter mThreadListAdapter;
    private ListViewFinal mThreadListView;
    private ProgressBar mProgressbar;

    private MemberEntity mMemberEntity;
}
