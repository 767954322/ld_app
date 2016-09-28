package com.autodesk.shejijia.shared.components.im.fragment;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.im.constants.BroadCastInfo;
import com.autodesk.shejijia.shared.components.im.activity.ChatRoomActivity;
import com.autodesk.shejijia.shared.components.im.activity.ImageChatRoomActivity;
import com.autodesk.shejijia.shared.components.im.activity.MPFileThreadListActivity;
import com.autodesk.shejijia.shared.components.im.adapter.ThreadListAdapter;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatEntityInfo;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThread;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThreads;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatUser;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatUtility;
import com.autodesk.shejijia.shared.components.im.manager.MPChatHttpManager;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;

import java.util.ArrayList;


public class MPThreadListFragment extends Fragment implements View.OnClickListener,
        ThreadListAdapter.ThreadListAdapterInterface,
        AbsListView.OnScrollListener,
        AdapterView.OnItemClickListener {
    public static final String ISFILEBASE = "IsFileBase";
    public static final String MEMBERID = "Memeber_Id";
    public static final String MEMBERTYPE = "Memeber_Type";


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
        clearAllPushNotificationsFromStatusbar();
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
    }


    public void onFragmentShown() {
        clearAllPushNotificationsFromStatusbar();
        refresh();
    }

    private void initView(View rootView) {
        mThreadListView = (ListView) rootView.findViewById(R.id.thread_list);
        mProgressbar = (ProgressBar) rootView.findViewById(R.id.threadlist_progressbar);
        mThreadListView.setOnScrollListener(this);
        mThreadListView.setOnItemClickListener(this);

        mThreadListAdapter = new ThreadListAdapter(mActivity, this, mIsFileBase);
        mThreadListView.setAdapter(mThreadListAdapter);
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
    public int getThreadCount() {
        return mThreadList.size();
    }


    public MPChatThread getThreadObjectForIndex(int index) {
        if (index < mThreadList.size())
            return mThreadList.get(index);

        return null;
    }

    public Boolean isUserConsumer() {
        return Constant.UerInfoKey.CONSUMER_TYPE.equals(mMemberType);
    }

    public int getLoggedInUserId() {
        return Integer.parseInt(mMemberId);
    }


    //OnScrollListener
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:

                // if no more threads are to be fetched, let's not make another request for same
                if (view.getLastVisiblePosition() == (mThreadList.size() - 1) && !mThreadsExhausted)
                    getMemberThreadsForOffset(mThreadList.size(), LIMIT);
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }

    //AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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

        int assetId = MPChatUtility.getAssetIdFromThread(thread);
        intent.putExtra(ChatRoomActivity.ASSET_ID, String.valueOf(assetId));

        String mediaType = MPChatUtility.getMediaTypeFromThread(thread);
        intent.putExtra(ChatRoomActivity.MEDIA_TYPE, mediaType);

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

    private void onThreadsReceived(MPChatThreads threads) {
        ArrayList<MPChatThread> threadArray = threads.threads;
        insertThreads(threadArray);

        mThreadsExhausted = threadArray.size() < LIMIT;
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
                MPNetworkUtils.logError("MPThreadListFragment", error);
                mIsNextPageRequestRunning = false;
                mProgressbar.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(String response) {
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

        if (mMemberId != null)
            getMemberThreadsForOffset(0, LIMIT);
    }


    class NewMessageBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BroadCastInfo.RECEVIER_RECEIVERMESSAGE))
                onNewMessageReceived(intent);
        }
    }

    private void clearAllPushNotificationsFromStatusbar() {

        NotificationManager nm = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        if (nm != null)
            nm.cancelAll();

        SharedPreferences sharedpreferences =  getActivity().getSharedPreferences(AdskApplication.JPUSH_STORE_KEY, Context.MODE_PRIVATE);

        if (sharedpreferences != null)
        {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.commit();
        }
    }

    private static final int LIMIT = 20;

    private String mMemberId;
    private String mMemberType;
    private boolean mIsFileBase;

    private ArrayList<MPChatThread> mThreadList = new ArrayList<MPChatThread>();
    private boolean mThreadsExhausted;
    private NewMessageBroadCastReceiver mNewMessageBroadCastReceiver;
    private Activity mActivity;
    private boolean mIsNextPageRequestRunning = false;

    private ThreadListAdapter mThreadListAdapter;
    private ListView mThreadListView;
    private ProgressBar mProgressbar;

    private MemberEntity mMemberEntity;
}
