package com.autodesk.shejijia.shared.framework.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
        mIMRadioButton = (RadioButton) findViewById(getIMButtonId());
        mRadioGroup = (RadioGroup) findViewById(getRadioGroupId());

        addRadioButtons(mIMRadioButton);
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        // retrieve the fragment handle from fragmentmanager
        if (savedInstanceState != null) {
            mMPThreadListFragment = (MPThreadListFragment) getSupportFragmentManager().findFragmentByTag(THREAD_FRAGMENT_TAG);
            mFragmentArrayList.add(mMPThreadListFragment);

            showFragment(getCurrentCheckedRadioButtonId());
        }
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
    protected void onStart() {
        super.onStart();
        AdskApplication.getInstance().openChatConnection();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isActiveFragment(MPThreadListFragment.class)) {
            getFileThreadUnreadCount();
        }

        mMemberUnreadCountManager.refreshCount();

        setRadioButtonChecked(getCurrentCheckedRadioButtonId());
    }


    @Override
    protected void onDestroy() {

        if (mBroadcastReceiver != null) {
            this.unregisterReceiver(mBroadcastReceiver);
        }

        mMemberUnreadCountManager.unregisterForMessageUpdates(this);
        mMemberUnreadCountManager = null;

        super.onDestroy();
    }

    protected void addRadioButtons(RadioButton button) {
        mRadioButtons.add(button);
    }

    protected int getCurrentCheckedRadioButtonId() {
        return mRadioGroup.getCheckedRadioButtonId();
    }

    protected RadioButton getRadioButtonById(int id) {
        RadioButton button = null;
        if (id == getIMButtonId()) {
            button = mIMRadioButton;
        }
        return button;
    }

    protected boolean needLoginOnRadioButtonTap(int id) {
        if (id == getIMButtonId() || id == getDesignerButtonId())
            return true;
        else
            return false;
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        setRadioButtonChecked(checkedId);

        if (needLoginOnRadioButtonTap(checkedId)) {
            startRegisterOrLoginActivity(checkedId);
        }
    }

    protected void showFragment(int index) {
        initAndAddFragments(index);
        final Fragment f = getFragmentByButtonId(index);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showFragment(f.getClass());
            }
        }, 200);


        configureNavigationBar(index);
    }


    protected void initAndAddFragments(int index) {
        if (mMPThreadListFragment == null && index == getIMButtonId()) {

            MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
            mMPThreadListFragment = new MPThreadListFragment();

            Bundle bundle = new Bundle();
            bundle.putString(MPThreadListFragment.MEMBERID, memberEntity.getAcs_member_id());
            bundle.putString(MPThreadListFragment.MEMBERTYPE, memberEntity.getMember_type());
            mMPThreadListFragment.setArguments(bundle);

            loadMainFragment(mMPThreadListFragment, THREAD_FRAGMENT_TAG);
        }
    }


    protected void loadMainFragment(Fragment fragment, String tag) {
        mFragmentArrayList.add(fragment);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(getMainContentId(), fragment, tag);
        fragmentTransaction.commitAllowingStateLoss();
    }


    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    protected void configureNavigationBar(int index) {
        hideAllNavButtons();

        if (index == getIMButtonId()) {
            setTitleForNavbar(UIUtils.getString(R.string.mychat));
            setVisibilityForNavButton(ButtonType.RIGHT, true);
            getFileThreadUnreadCount();
        }
    }

    @Override
    protected void rightNavButtonClicked(View view) {


        if (isActiveFragment(MPThreadListFragment.class))
            openFileThreadActivity();
    }


    protected int getIMButtonId() {
        assert (false);
        return -1;
    }

    protected int getDesignerButtonId() {
        assert (false);
        return -1;
    }

    protected int getRadioGroupId() {
        assert (false);
        return -1;
    }

    protected int getMainContentId() {
        assert (false);
        return -1;
    }

    public void openFileThreadActivity() {
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        Intent intent = new Intent(this, MPFileThreadListActivity.class);
        intent.putExtra(MPFileThreadListActivity.MEMBERID, memberEntity.getAcs_member_id());
        intent.putExtra(MPFileThreadListActivity.MEMBERTYPE, memberEntity.getMember_type());
        startActivity(intent);
    }

    private void setRadioButtonChecked(int id) {
        RadioButton checkedButton = getRadioButtonById(id);
        int size = mRadioButtons.size();
        for (int i = 0; i < size; ++i) {
            RadioButton button = mRadioButtons.get(i);
            if (checkedButton == button)
                button.setTextColor(UIUtils.getColor(R.color.bg_tab_pressed));
            else
                button.setTextColor(UIUtils.getColor(R.color.bg_tab_normal));
        }
    }

    private void showFragment(Class clazz) {
        //This is kind of a hack here to avoid  getting recreated but
        //still gets refreshed when needed
        MPThreadListFragment threadListFragment = null;
        BaseFragment f = null;

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        for (Fragment fragment : mFragmentArrayList) {
            if (null != fragment && fragment.getClass().equals(clazz)) {
                fragmentTransaction.show(fragment);

                if (fragment.getClass().equals(MPThreadListFragment.class))
                    threadListFragment = (MPThreadListFragment) fragment;
                else
                    f = (BaseFragment) fragment;
            } else {
                fragmentTransaction.hide(fragment);
            }
        }
        fragmentTransaction.commitAllowingStateLoss();

        if (threadListFragment != null) {
            threadListFragment.onFragmentShown();
        }
        if (f != null) {
            f.onFragmentShown();
        }
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
                if (isActiveFragment(MPThreadListFragment.class))
                    getFileThreadUnreadCount();
            }
        }
    };


    protected Fragment getFragmentByButtonId(int id) {
        Fragment f = null;
        if (id == getIMButtonId()) {
            f = mMPThreadListFragment;
        } else if (id == getDesignerButtonId()) {
            f = getDesignerFragment();
        }

        return f;
    }

    protected Fragment getDesignerFragment() {
        return null;
    }

    ;


    protected boolean isActiveFragment(Class clazz) {
        Fragment fragment = getFragment(clazz);
        return (fragment != null && fragment.isVisible());
    }


    private Fragment getFragment(Class clazz) {
        Fragment currentFragment = null;

        for (Fragment fragment : mFragmentArrayList) {
            if (null != fragment && fragment.getClass().equals(clazz)) {
                currentFragment = fragment;
                break;
            }
        }
        return currentFragment;
    }

    private void startRegisterOrLoginActivity(int radioBtnId) {
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (memberEntity != null || radioBtnId == getDesignerButtonId()) {
            showFragment(radioBtnId);
        } else {
            mRadioGroup.check(R.id.consumer_main_radio_btn);
            AdskApplication.getInstance().doLogin(this);
        }
    }

    public void getFileThreadUnreadCount() {
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
        if (null != memberEntity) {
            MPChatHttpManager.getInstance().retrieveMemberUnreadMessageCount(memberEntity.getAcs_member_id(), false, callback);
        }
    }

    public MPMemberUnreadCountManager getmMemberUnreadCountManager() {
        if (mMemberUnreadCountManager == null) {
            mMemberUnreadCountManager = new MPMemberUnreadCountManager();
        }
        return mMemberUnreadCountManager;
    }

    private List<RadioButton> mRadioButtons = new ArrayList<RadioButton>();
    protected List<Fragment> mFragmentArrayList = new ArrayList<>();
    protected RadioGroup mRadioGroup;

    private RadioButton mIMRadioButton;
    private TextView mTvMsgNumber;
    private MPThreadListFragment mMPThreadListFragment;
    private MPMemberUnreadCountManager mMemberUnreadCountManager;

    private static String THREAD_FRAGMENT_TAG = "THREAD_FRAGMENT_TAG";
}
