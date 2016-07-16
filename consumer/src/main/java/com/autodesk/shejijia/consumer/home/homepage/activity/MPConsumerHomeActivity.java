package com.autodesk.shejijia.consumer.home.homepage.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.homepage.fragment.BidHallFragment;
import com.autodesk.shejijia.consumer.home.homepage.fragment.ConsumerPersonalCenterFragment;
import com.autodesk.shejijia.consumer.home.homepage.fragment.DesignerPersonalCenterFragment;
import com.autodesk.shejijia.consumer.home.homepage.fragment.UserHomeFragment;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.shared.components.common.appglobal.ApiManager;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.shared.components.common.tools.CaptureQrActivity;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.im.activity.ChatRoomActivity;
import com.autodesk.shejijia.shared.components.im.datamodel.IMQrEntity;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThread;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThreads;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatUtility;
import com.autodesk.shejijia.shared.components.im.manager.MPChatHttpManager;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.BaseHomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class MPConsumerHomeActivity extends BaseHomeActivity {
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_designer_main;
    }

    @Override
    protected void initView() {
        super.initView();

        //hide navigation left button
        setVisibilityForNavButton(ButtonType.LEFT, false);

        mDesignerMainRadioBtn = (RadioButton) findViewById(getDesignerMainRadioBtnId());
        designer_main_radio_group = (RadioGroup) findViewById(R.id.designer_main_radio_group);
        designer_main_radio_btn = (RadioButton) findViewById(R.id.designer_main_radio_btn);
        mDesignerIndentListBtn = (RadioButton) findViewById(R.id.designer_indent_list_btn);
        mDesigner_session_radio_btn = (RadioButton) findViewById(R.id.designer_session_radio_btn);
        mDesignerPersonCenterRadioBtn = (RadioButton) findViewById(R.id.designer_person_center_radio_btn);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        showDesignerOrConsumerRadioGroup();
        super.initData(savedInstanceState);
    }


    @Override
    protected void onResume() {
        Intent intent = getIntent();
        int id = intent.getIntExtra(Constant.DesignerBeiShuMeal.SKIP_DESIGNER_PERSONAL_CENTER, -1);
        if (id > 0) {
            switch (id) {
                case 1:
                    /// 回到个人中心 .
                    mDesignerPersonCenterRadioBtn.performClick();
                    break;
                default:
                    break;
            }
        }
//        else {
//            mDesignerMainRadioBtn.performClick();
//            showDesignerOrConsumerRadioGroup();
//        }

        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showDesignerOrConsumerRadioGroup();
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (memberEntity == null) {
            //未登录状态，会自动进入案例fragment
            designer_main_radio_btn.setChecked(true);
        } else {
            switch (memberEntity.getMember_type()) {
                case Constant.UerInfoKey.DESIGNER_TYPE:
                case Constant.UerInfoKey.CONSUMER_TYPE:
                    designer_main_radio_group.check(mCurrentTabIndex);
                    break;
                default:
                    // TODO other types
                    break;
            }
        }
    }

    @Override
    protected RadioButton getRadioButtonById(int id) {
        RadioButton button = super.getRadioButtonById(id);
        switch (id) {
            case R.id.designer_main_radio_btn:
                button = mDesignerMainRadioBtn;
                break;
            case R.id.designer_indent_list_btn:
                button = mDesignerIndentListBtn;
                break;
            case R.id.designer_person_center_radio_btn:
                button = mDesignerPersonCenterRadioBtn;
                break;
        }

        return button;
    }

    @Override
    protected boolean needLoginOnRadiobuttonTap(int id) {
        if ((super.needLoginOnRadiobuttonTap(id)) ||
                (id == R.id.designer_indent_list_btn) ||
                (id == R.id.designer_person_center_radio_btn))
            return true;
        else
            return false;
    }

    @Override
    protected void initAndAddFragments(int index) {
        super.initAndAddFragments(index);
        if (index == getDesignerMainRadioBtnId()) {
            mUserHomeFragment = (UserHomeFragment) getExistFragment(mUserHomeFragment, TAG_CASES);
            if (mUserHomeFragment == null) {
                mUserHomeFragment = new UserHomeFragment();
                loadMainFragment(mUserHomeFragment, TAG_CASES);
            }
        } else if (index == R.id.designer_indent_list_btn) {
            mBidHallFragment = (BidHallFragment) getExistFragment(mBidHallFragment, TAG_BID_HALL);
            if (mBidHallFragment == null) {
                mBidHallFragment = new BidHallFragment();
                loadMainFragment(mBidHallFragment, TAG_BID_HALL);
            }
        } else if (index == R.id.designer_person_center_radio_btn) {
            MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
            if (memberEntity != null) {
                if (Constant.UerInfoKey.DESIGNER_TYPE.equals(memberEntity.getMember_type())) {
                    mDesignerPersonalCenterFragment = (DesignerPersonalCenterFragment) getExistFragment(mDesignerPersonalCenterFragment, TAG_PERSONAL_CENTER_DESIGNER);
                    if (mDesignerPersonalCenterFragment == null) {
                        mDesignerPersonalCenterFragment = new DesignerPersonalCenterFragment();
                        loadMainFragment(mDesignerPersonalCenterFragment, TAG_PERSONAL_CENTER_DESIGNER);
                    }
                } else {
                    mConsumerPersonalCenterFragment = (ConsumerPersonalCenterFragment) getExistFragment(mConsumerPersonalCenterFragment, TAG_PERSONAL_CENTER_CONSUMER);
                    if (mConsumerPersonalCenterFragment == null) {
                        mConsumerPersonalCenterFragment = new ConsumerPersonalCenterFragment();
                        loadMainFragment(mConsumerPersonalCenterFragment, TAG_PERSONAL_CENTER_CONSUMER);
                    }
                }
            }
        }
    }

    @Override
    protected Fragment getFragmentByButtonId(int id) {
        Fragment f = super.getFragmentByButtonId(id);
        if (id == R.id.designer_indent_list_btn)
            f = mBidHallFragment;
        else if (id == R.id.designer_person_center_radio_btn) {
            MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
            if (memberEntity != null && Constant.UerInfoKey.DESIGNER_TYPE.equals(memberEntity.getMember_type()))
                f = mDesignerPersonalCenterFragment;
            else
                f = mConsumerPersonalCenterFragment;
        } else if (id == getDesignerMainRadioBtnId()) {
            f = mUserHomeFragment;
        }
        return f;
    }


    @Override
    protected void leftNavButtonClicked(View view) {
        if (isActiveFragment(TAG_BID_HALL))
            mBidHallFragment.handleFilterOption();
    }

    protected void configureNavigationBar(int index) {

        super.configureNavigationBar(index);

        switch (index) {
            case R.id.designer_main_radio_btn:
                setTitleForNavbar(UIUtils.getString(R.string.app_name));
                break;
            case R.id.designer_indent_list_btn:    /// 应标大厅按钮.
                TextView textView = (TextView) findViewById(R.id.nav_left_textView);
                textView.setVisibility(View.VISIBLE);
                Drawable drawable = UIUtils.getDrawable(R.drawable.shanjiao_ico);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                textView.setCompoundDrawables(null, null, drawable, null);
                textView.setText(UIUtils.getString(R.string.bid_filter));
                setTitleForNavbar(UIUtils.getString(R.string.tab_hall));
                break;

            case R.id.designer_person_center_radio_btn:  /// 个人中心按钮.
                setTitleForNavbar(UIUtils.getString(R.string.designer_personal));
                break;

            case R.id.designer_session_radio_btn:  /// 会話聊天.


                String acs_Member_Type = AdskApplication.getInstance().getMemberEntity().getMember_type();
                Boolean ifIsDesiner = Constant.UerInfoKey.DESIGNER_TYPE.equals(acs_Member_Type);
                if (ifIsDesiner) {
                    String hs_uid = AdskApplication.getInstance().getMemberEntity().getHs_uid();
                    String acs_Member_Id = AdskApplication.getInstance().getMemberEntity().getMember_id();
                    ifIsLohoDesiner(acs_Member_Id, hs_uid);
//                    setImageForNavButton(ButtonType.SECONDARY, com.autodesk.shejijia.shared.R.drawable.scan);
//                    setVisibilityForNavButton(ButtonType.SECONDARY, true);
                } else {
                    setVisibilityForNavButton(ButtonType.SECONDARY, false);
                }

            default:
                break;
        }
    }

    private void ifIsLohoDesiner(String desiner_id, String hs_uid) {

        MPServerHttpManager.getInstance().ifIsLohoDesiner(desiner_id, hs_uid, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                String info = GsonUtil.jsonToString(jsonObject);
                Log.d("test", "gumenghao ");
                try {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("designer");
                    int is_loho = jsonObject1.getInt("is_loho");
                    //2：乐屋设计师添加扫描二维码功能（其他几种未判断）
                    if (2 == is_loho) {
                        setImageForNavButton(ButtonType.SECONDARY, com.autodesk.shejijia.shared.R.drawable.scan);
                        setVisibilityForNavButton(ButtonType.SECONDARY, true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("test", e.getMessage().toString());
                }

            }
        });


    }

    @Override
    protected void secondaryNavButtonClicked(View view) {
        super.secondaryNavButtonClicked(view);

        Intent intent = new Intent(MPConsumerHomeActivity.this, CaptureQrActivity.class);
        startActivityForResult(intent, REQUEST_CODE_CHAT);

    }

    protected int getDesignerMainRadioBtnId() {
        return R.id.designer_main_radio_btn;
    }

    protected int getDesignerSessionRadioBtnId() {
        return R.id.designer_session_radio_btn;
    }

    protected int getRadioGroupId() {
        return R.id.designer_main_radio_group;
    }


    protected int getMainContentId() {
        return R.id.main_content;
    }


    private void showDesignerOrConsumerRadioGroup() {
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (memberEntity != null && Constant.UerInfoKey.DESIGNER_TYPE.equals(memberEntity.getMember_type())) {
            mDesignerIndentListBtn.setVisibility(View.VISIBLE);
            TextView textView = (TextView) findViewById(R.id.tv_gron_msg_number);
            textView.setVisibility(View.VISIBLE);
        } else {
            TextView textView = (TextView) findViewById(R.id.tv_gron_msg_number);
            textView.setVisibility(View.GONE);
            mDesignerIndentListBtn.setVisibility(View.GONE);
        }
    }

    //判断是否聊过天，跳转到之前聊天室或新聊天室
    private void jumpToChatRoom(String scanResult) {

        if (scanResult.contains(Constant.ConsumerDecorationFragment.hs_uid) && scanResult.contains(Constant.DesignerCenterBundleKey.MEMBER)) {

            IMQrEntity consumerQrEntity = GsonUtil.jsonToBean(scanResult, IMQrEntity.class);
            if (null != consumerQrEntity && !TextUtils.isEmpty(consumerQrEntity.getName())) {

                final String hs_uid = consumerQrEntity.getHs_uid();
                final String member_id = consumerQrEntity.getMember_id();
                final String receiver_name = consumerQrEntity.getName();
                final String designer_id = AdskApplication.getInstance().getMemberEntity().getAcs_member_id();
                final String mMemberType = AdskApplication.getInstance().getMemberEntity().getMember_type();
                final String recipient_ids = member_id + "," + designer_id + "," + ApiManager.getAdmin_User_Id(ApiManager.RUNNING_DEVELOPMENT);

                MPChatHttpManager.getInstance().retrieveMultipleMemberThreads(recipient_ids, 0, 10, new OkStringRequest.OKResponseCallback() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        MPNetworkUtils.logError(TAG, volleyError);
                    }

                    @Override
                    public void onResponse(String s) {
                        MPChatThreads mpChatThreads = MPChatThreads.fromJSONString(s);

                        Intent intent = new Intent(MPConsumerHomeActivity.this, ChatRoomActivity.class);
                        intent.putExtra(ChatRoomActivity.RECIEVER_USER_ID, member_id);
                        intent.putExtra(ChatRoomActivity.RECIEVER_USER_NAME, receiver_name);
                        intent.putExtra(ChatRoomActivity.ACS_MEMBER_ID, designer_id);
                        intent.putExtra(ChatRoomActivity.MEMBER_TYPE, mMemberType);

                        if (mpChatThreads != null && mpChatThreads.threads.size() > 0) {
                            MPChatThread mpChatThread = mpChatThreads.threads.get(0);
                            int assetId = MPChatUtility.getAssetIdFromThread(mpChatThread);
                            intent.putExtra(ChatRoomActivity.THREAD_ID, mpChatThread.thread_id);
                            intent.putExtra(ChatRoomActivity.ASSET_ID, assetId + "");
                        } else {
                            intent.putExtra(ChatRoomActivity.RECIEVER_HS_UID, hs_uid);
                            intent.putExtra(ChatRoomActivity.ASSET_ID, "");
                        }

                        startActivity(intent);
                    }

                });

            }

        } else {

            new AlertView(UIUtils.getString(com.autodesk.shejijia.shared.R.string.tip)
                    , UIUtils.getString(com.autodesk.shejijia.shared.R.string.unable_create_beishu_meal)
                    , null, null, new String[]{UIUtils.getString(com.autodesk.shejijia.shared.R.string.sure)}
                    , MPConsumerHomeActivity.this
                    , AlertView.Style.Alert, null).show();

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case REQUEST_CODE_CHAT:

                    Bundle bundle = data.getExtras();
                    String scanResult = bundle.getString(Constant.QrResultKey.SCANNER_RESULT);
                    jumpToChatRoom(scanResult);

                    break;
            }
        }

    }

    private final static String TAG_CASES = "tag_cases";
    private final static String TAG_BID_HALL = "tag_bid_hall";
    private final static String TAG_PERSONAL_CENTER_DESIGNER = "tag_personal_center_designer";
    private final static String TAG_PERSONAL_CENTER_CONSUMER = "tag_personal_center_consumer";
    private final static int REQUEST_CODE_CHAT = 0;

    private RadioButton mDesignerMainRadioBtn;
    private RadioButton mDesignerPersonCenterRadioBtn;
    private RadioButton mDesignerIndentListBtn;
    private RadioButton designer_main_radio_btn;
    private RadioButton mDesigner_session_radio_btn;
    private RadioGroup designer_main_radio_group;

    private UserHomeFragment mUserHomeFragment;
    private BidHallFragment mBidHallFragment;
    private DesignerPersonalCenterFragment mDesignerPersonalCenterFragment;
    private ConsumerPersonalCenterFragment mConsumerPersonalCenterFragment;
}
