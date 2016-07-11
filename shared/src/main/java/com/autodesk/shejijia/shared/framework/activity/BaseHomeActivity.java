package com.autodesk.shejijia.shared.framework.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.im.constants.BroadCastInfo;
import com.autodesk.shejijia.shared.components.im.activity.MPFileThreadListActivity;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatUtility;
import com.autodesk.shejijia.shared.components.im.fragment.MPThreadListFragment;
import com.autodesk.shejijia.shared.components.im.manager.MPChatHttpManager;
import com.autodesk.shejijia.shared.components.im.manager.MPMemberUnreadCountManager;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
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

        addRadioButtons(mDesignerSessionRadioBtn);
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

        if (isActiveFragment(MPThreadListFragment.class)) {
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

    protected void addRadioButtons(RadioButton button) {
        mRadioButtons.add(button);
    }

    protected RadioButton getRadioButtonById(int id) {
        RadioButton button = null;
        if (id == getDesignerSessionRadioBtnId()) {
            button = mDesignerSessionRadioBtn;

        }

        return button;
    }


    protected void initAndAddFragments(int index) {

        if (mMPThreadListFragment == null && index == getDesignerSessionRadioBtnId()) {
            MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
            mMPThreadListFragment = new MPThreadListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(MPThreadListFragment.MEMBERID, memberEntity.getAcs_member_id());
            bundle.putString(MPThreadListFragment.MEMBERTYPE, memberEntity.getMember_type());
            mMPThreadListFragment.setArguments(bundle);
            loadMainFragment(mMPThreadListFragment);
        }
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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        setRadioButtonChecked(checkedId);

        if (needLoginOnRadiobuttonTap(checkedId))
            startRegisterOrLoginActivity(checkedId);
    }


    public boolean isDestroyed() {
        return isDestroyed;
    }

    protected void showFragment(int index) {
        initAndAddFragments(index);
        Fragment f = getFragmentByButtonId(index);
        showFragment(f.getClass());
        configureNavigationBar(index);
    }

    protected void loadMainFragment(Fragment fragment) {
        mFragmentArrayList.add(fragment);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(getMainContentId(), fragment);
        fragmentTransaction.commit();
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

        if (index == getDesignerSessionRadioBtnId()) {

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

    protected boolean isActiveFragment(Class clazz) {
        Fragment fragment = getFragment(clazz);
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


    private void setRadioButtonChecked(int id) {
        RadioButton checkedButton = getRadioButtonById(id);

        for (int i = 0; i < mRadioButtons.size(); ++i) {
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

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        for (Fragment fragment : mFragmentArrayList) {
            if (fragment.getClass().equals(clazz)) {
                fragmentTransaction.show(fragment);

                if (fragment.getClass().equals(MPThreadListFragment.class))
                    threadListFragment = (MPThreadListFragment) fragment;
                else
                    f = (BaseFragment) fragment;
            } else
                fragmentTransaction.hide(fragment);
        }
        fragmentTransaction.commit();

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

    private Fragment getFragment(Class clazz) {
        Fragment currentFragment = null;

        for (Fragment fragment : mFragmentArrayList) {
            if (fragment.getClass().equals(clazz)) {
                currentFragment = fragment;
                break;
            }
        }
        return currentFragment;
    }

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

    private RadioButton mDesignerSessionRadioBtn;
    private List<RadioButton> mRadioButtons = new ArrayList<RadioButton>();

    private RadioGroup mRadioGroup;
    private TextView mTvMsgNumber;

    private boolean isDestroyed = false;

    private MPThreadListFragment mMPThreadListFragment;

    private List<Fragment> mFragmentArrayList = new ArrayList<>();

    private MPMemberUnreadCountManager mMemberUnreadCountManager;
}
