package com.autodesk.shejijia.shared.framework.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.im.activity.MPFileThreadListActivity;
import com.autodesk.shejijia.shared.components.im.constants.BroadCastInfo;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatUtility;
import com.autodesk.shejijia.shared.components.im.fragment.MPThreadListFragment;
import com.autodesk.shejijia.shared.components.im.manager.MPChatHttpManager;
import com.autodesk.shejijia.shared.components.im.manager.MPMemberUnreadCountManager;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BaseHomeActivity extends NavigationBarActivity implements RadioGroup.OnCheckedChangeListener {

    @Override
    protected void initView() {
        super.initView();
        mTvMsgNumber = (TextView) findViewById(R.id.tv_msg_number);
        mDesignerSessionRadioBtn = (RadioButton) findViewById(getDesignerSessionRadioBtnId());
        mRadioGroup = (RadioGroup) findViewById(getRadioGroupId());
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }


    @Override
    protected void initListener() {
        super.initListener();
        mRadioGroup.setOnCheckedChangeListener(this);
        registerBroadcastReceiver();

        mMemberUnreadCountManager = new MPMemberUnreadCountManager();

        mMemberUnreadCountManager.registerForMessageUpdates(this, new MPMemberUnreadCountManager.MPMemberUnreadCountInterface() {
            @Override
            public TextView getUnreadBadgeLabel() {
                return mTvMsgNumber;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isActiveFragment(TAG_CHAT)) {
            getFileThreadUnreadCount();
        }

        mMemberUnreadCountManager.refreshCount();
    }


    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {

        if (mBroadcastReceiver != null) {
            this.unregisterReceiver(mBroadcastReceiver);
        }

        mMemberUnreadCountManager.unregisterForMessageUpdates(this);
        mMemberUnreadCountManager = null;

        isDestroyed = true;
        super.onDestroy();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (needLoginOnRadiobuttonTap(checkedId))
            startRegisterOrLoginActivity(checkedId);
    }

    @Override
    protected void rightNavButtonClicked(View view) {
        if (isActiveFragment(TAG_CHAT))
            openFileThreadActivity();
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    protected RadioButton getRadioButtonById(int id) {
        RadioButton button = null;
        if (id == getDesignerSessionRadioBtnId()) {
            button = mDesignerSessionRadioBtn;

        }

        return button;
    }

    protected boolean needLoginOnRadiobuttonTap(int id) {
        if (id == getDesignerSessionRadioBtnId())
            return true;
        else
            return false;
    }


    protected Fragment getFragmentByButtonId(int id) {
        Fragment f = null;
        if (id == getDesignerSessionRadioBtnId())
            f = mMPThreadListFragment;

        return f;
    }

    protected void initAndAddFragments(int index) {
        mMPThreadListFragment = (MPThreadListFragment) getFragmentManager().findFragmentByTag(TAG_CHAT);

        if (mMPThreadListFragment == null && index == getDesignerSessionRadioBtnId()) {
            MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
            mMPThreadListFragment = new MPThreadListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(MPThreadListFragment.MEMBERID, memberEntity.getAcs_member_id());
            bundle.putString(MPThreadListFragment.MEMBERTYPE, memberEntity.getMember_type());
            mMPThreadListFragment.setArguments(bundle);
            loadMainFragment(mMPThreadListFragment, TAG_CHAT);
        }
    }

    protected void showFragment(int index) {
        initAndAddFragments(index);
        Fragment currentFragment = getFragmentByButtonId(mCurrentTabIndex);
        Fragment nextFragment = getFragmentByButtonId(index);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }

        fragmentTransaction.show(nextFragment);
        fragmentTransaction.commit();
        mCurrentTabIndex = index;
        configureNavigationBar(index);
    }

    protected void loadMainFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(getMainContentId(), fragment);
        fragmentTransaction.commit();
    }

    protected void loadMainFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(getMainContentId(), fragment, tag);
        fragmentTransaction.commit();
    }

    protected void configureNavigationBar(int index) {
        hideAllNavButtons();

        if (index == getDesignerSessionRadioBtnId()) {

            setTitleForNavbar(UIUtils.getString(R.string.mychat));
            setVisibilityForNavButton(ButtonType.RIGHT, true);
            getFileThreadUnreadCount();

        }
    }

    protected boolean isActiveFragment(String tag) {
        Fragment fragment = getFragmentManager().findFragmentByTag(TAG_CHAT);
        return (fragment != null && fragment.isVisible());
    }

    protected int getDesignerSessionRadioBtnId() {
        return -1;
    }

    protected int getRadioGroupId() {
        return -1;
    }

    protected int getMainContentId() {
        return -1;
    }


    private void openFileThreadActivity() {
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        Intent intent = new Intent(this, MPFileThreadListActivity.class);
        intent.putExtra(MPFileThreadListActivity.MEMBERID, memberEntity.getAcs_member_id());
        intent.putExtra(MPFileThreadListActivity.MEMBERTYPE, memberEntity.getMember_type());
        startActivity(intent);
    }

    private void registerBroadcastReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BroadCastInfo.RECEVIER_RECEIVERMESSAGE);
        this.registerReceiver(mBroadcastReceiver, intentFilter);
    }


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equalsIgnoreCase(BroadCastInfo.RECEVIER_RECEIVERMESSAGE)) {
                if (isActiveFragment(TAG_CHAT))
                    getFileThreadUnreadCount();
            }
        }
    };

    private void startRegisterOrLoginActivity(int radioBtnId) {
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (memberEntity != null) {
            showFragment(radioBtnId);
        } else {
            AdskApplication.getInstance().doLogin(this);
        }
    }

    private void getFileThreadUnreadCount() {
        OkStringRequest.OKResponseCallback callback = new OkStringRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MPNetworkUtils.logError(TAG, error);
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject myJsonObject = new JSONObject(response);
                    int unread_message_count = myJsonObject.getInt("unread_message_count");
                    if (unread_message_count != 0) {
                        String badge = MPChatUtility.getFormattedBadgeString(unread_message_count);

                        if (isActiveFragment(MPThreadListFragment.class))
                            showBadgeOnNavBar(badge);
                    } else
                        setVisibilityForNavButton(ButtonType.BADGE, false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        MPChatHttpManager.getInstance().retrieveMemberUnreadMessageCount(memberEntity.getAcs_member_id(), false, callback);
    }
    
    protected int mCurrentTabIndex = -1;
    private boolean isDestroyed = false;

    protected int mCurrentTabIndex = -1;

    private boolean isDestroyed = false;

    private final static String TAG_CHAT = "tag_chat";
    protected int mCurrentTabIndex = -1;

    private RadioButton mDesignerSessionRadioBtn;
    private RadioGroup mRadioGroup;
    private TextView mTvMsgNumber;

    private MPThreadListFragment mMPThreadListFragment;
    
    private boolean isDestroyed = false;
    private MPMemberUnreadCountManager mMemberUnreadCountManager;
}
