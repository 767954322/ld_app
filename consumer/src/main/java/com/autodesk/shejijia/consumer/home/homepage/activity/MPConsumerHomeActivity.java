package com.autodesk.shejijia.consumer.home.homepage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.activity.FiltrateActivity;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.FiltrateContentBean;
import com.autodesk.shejijia.consumer.home.homepage.fragment.BidHallFragment;
import com.autodesk.shejijia.consumer.home.homepage.fragment.DesignerListFragment;
import com.autodesk.shejijia.consumer.home.homepage.fragment.MyDecorationProjectDesignerFragment;
import com.autodesk.shejijia.consumer.home.homepage.fragment.MyDecorationProjectFragment;
import com.autodesk.shejijia.consumer.home.homepage.fragment.UserHomeFragment;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.consumer.activity.IssueDemandActivity;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.ConsumerEssentialInfoEntity;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.DesignerInfoDetails;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.fragment.DecorationConsumerFragment;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.TipWorkFlowTemplateBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.WkFlowStateBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.WkFlowStateInfoBean;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.consumer.utils.UserPictureUtil;
import com.autodesk.shejijia.consumer.utils.WkFlowStateMap;
import com.autodesk.shejijia.shared.components.common.appglobal.ApiManager;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.appglobal.UrlMessagesContants;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.shared.components.common.tools.CaptureQrActivity;
import com.autodesk.shejijia.shared.components.common.tools.login.RegisterOrLoginActivity;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.chooseview.ChooseViewPointer;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.im.activity.ChatRoomActivity;
import com.autodesk.shejijia.shared.components.im.constants.BroadCastInfo;
import com.autodesk.shejijia.shared.components.im.datamodel.IMQrEntity;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThread;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThreads;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatUtility;
import com.autodesk.shejijia.shared.components.im.fragment.MPThreadListFragment;
import com.autodesk.shejijia.shared.components.im.manager.MPChatHttpManager;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.BaseHomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class MPConsumerHomeActivity extends BaseHomeActivity implements View.OnClickListener {

    protected static final String TAB_HOME_CASE = "TAB_HOME_CASE";  /// 案例 .
    protected static final String TAB_DESIGNER = "TAB_DESIGNER";    /// 设计师列表 .
    protected static final String TAB_BID_HALL = "TAB_BID_HALL";    /// 应标大厅 .
    protected static final String TAB_IM = "TAB_IM";                /// 聊天 .
    protected static final String TAB_PROJECT = "TAB_PROJECT";      /// 我的订单 .

    private MemberEntity memberEntity;

    public int is_loho;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_designer_main;
    }

    @Override
    protected void initView() {
        super.initView();

        setVisibilityForNavButton(ButtonType.LEFT, false);

        designer_main_radio_group = (RadioGroup) findViewById(R.id.designer_main_radio_group);
        designer_main_radio_btn = (RadioButton) findViewById(R.id.designer_main_radio_btn);
        rbCustomerElite = (RadioButton) findViewById(R.id.rb_customer_elite);

        radioBtnDesigner = (RadioButton) findViewById(R.id.radio_btn_designer);


        mDesignerMainRadioBtn = (RadioButton) findViewById(getDesignerMainRadioBtnId());
        mDesignerIndentListBtn = (RadioButton) findViewById(R.id.designer_indent_list_btn);
        mDesignerPersonCenterRadioBtn = (RadioButton) findViewById(R.id.designer_person_center_radio_btn);

        contain = (LinearLayout) findViewById(R.id.ll_contain);
        tvGronMmsgNumber = (TextView) findViewById(R.id.tv_gron_msg_number);


        contain_layout = LayoutInflater.from(this).inflate(R.layout.contain_choose_layout, null);
        chooseViewPointer = (ChooseViewPointer) contain_layout.findViewById(R.id.choose_point);
        bidding = (TextView) contain_layout.findViewById(R.id.bidding);
        design = (TextView) contain_layout.findViewById(R.id.design);

//        construction = (TextView) contain_layout.findViewById(R.id.construction);

        setMyProjectTitleColorChange(design, bidding/*, construction*/);
        user_avatar = (ImageView) findViewById(R.id.user_avatar);

        addRadioButtons(mDesignerMainRadioBtn);
        addRadioButtons(mDesignerIndentListBtn);
        addRadioButtons(mDesignerPersonCenterRadioBtn);
        //获取节点信息
        getALLWkFlowStatePointInformation();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // retrieve the fragment handle from fragmentmanager
            mUserHomeFragment = (UserHomeFragment) getSupportFragmentManager().findFragmentByTag(HOME_FRAGMENT_TAG);
            if (mUserHomeFragment != null)
                mFragmentArrayList.add(mUserHomeFragment);

            designerListFragment = (DesignerListFragment) getSupportFragmentManager().findFragmentByTag(TAB_DESIGNER);
            if (designerListFragment != null)
                mFragmentArrayList.add(designerListFragment);

            mDesignerPersonalCenterFragment = (MyDecorationProjectDesignerFragment)
                    getSupportFragmentManager().findFragmentByTag(DESIGNER_PERSONAL_FRAGMENT_TAG);
            if (mDesignerPersonalCenterFragment != null)
                mFragmentArrayList.add(mDesignerPersonalCenterFragment);

            mConsumerPersonalCenterFragment =
                    (DecorationConsumerFragment) getSupportFragmentManager().findFragmentByTag(CONSUMER_PERSONAL_FRAGMENT_TAG);
            if (mConsumerPersonalCenterFragment != null) {
                mFragmentArrayList.add(mConsumerPersonalCenterFragment);
            }
            mBidHallFragment = (BidHallFragment) getSupportFragmentManager().findFragmentByTag(BID_FRAGMENT_TAG);
            if (mBidHallFragment != null) {
                mFragmentArrayList.add(mBidHallFragment);
            }
        }

        super.initData(savedInstanceState);
        if (savedInstanceState == null) {
            showFragment(getDesignerMainRadioBtnId());
        }
        isShowBidHallFragment();


    }

    @Override
    protected void initListener() {
        super.initListener();
        bidding.setOnClickListener(this);
        design.setOnClickListener(this);
        rbCustomerElite.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        UserPictureUtil.setConsumerOrDesignerPicture(this, getUserAvatar());
        Intent intent = getIntent();
        setChooseViewWidth(true);
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
        super.onResume();

        //获取设计师信息
        MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        if (null == mMemberEntity){
            return;
        }
        String member_id = mMemberEntity.getAcs_member_id();
        String hs_uid = mMemberEntity.getHs_uid();
        getDesignerInfoData(member_id, hs_uid);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isShowBidHallFragment();
        setConsumerOrDesignerPicture();//设置头像

        MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        //登陆设计师时，会进入；
        if (mMemberEntity != null && Constant.UerInfoKey.DESIGNER_TYPE.equals(mMemberEntity.getMember_type())) {
            designer_main_radio_group.check(index);

        }
        //登陆消费者时，会进入
        if (mMemberEntity != null && Constant.UerInfoKey.CONSUMER_TYPE.equals(mMemberEntity.getMember_type())) {
            designer_main_radio_group.check(index);
        }

        //未登录状态，会自动进入案例fragment

        if (mMemberEntity == null) {
            designer_main_radio_btn.setChecked(true);
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

            case R.id.radio_btn_designer:
                button = radioBtnDesigner;
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

    //将每个fragment添加
    @Override
    protected void initAndAddFragments(int index) {
        super.initAndAddFragments(index);
        this.index = index;

        if (mUserHomeFragment == null && index == getDesignerMainRadioBtnId()) {
            mUserHomeFragment = new UserHomeFragment();
            loadMainFragment(mUserHomeFragment, HOME_FRAGMENT_TAG);
        }

        if (designerListFragment == null && index == getDesignerButtonId()) {
            designerListFragment = new DesignerListFragment();
            loadMainFragment(designerListFragment, TAB_DESIGNER);
        }

        if (mBidHallFragment == null && index == R.id.designer_indent_list_btn) {
            mBidHallFragment = new BidHallFragment();
            loadMainFragment(mBidHallFragment, BID_FRAGMENT_TAG);
        }

        if (index == R.id.designer_person_center_radio_btn) {
            MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
            //判断是消费者，还是设计师，，从而区分消费者和设计师
            if (memberEntity != null && Constant.UerInfoKey.DESIGNER_TYPE.equals(memberEntity.getMember_type())) {
                if (mDesignerPersonalCenterFragment == null) {
                    mDesignerPersonalCenterFragment = new MyDecorationProjectDesignerFragment();

                    loadMainFragment(mDesignerPersonalCenterFragment, DESIGNER_PERSONAL_FRAGMENT_TAG);
                }
            } else {
                if (mConsumerPersonalCenterFragment == null) {
                    mConsumerPersonalCenterFragment = new DecorationConsumerFragment();
                    loadMainFragment(mConsumerPersonalCenterFragment, CONSUMER_PERSONAL_FRAGMENT_TAG);
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

    //监听筛选按钮，，，
    @Override
    protected void rightNavButtonClicked(View view) {
        if (isActiveFragment(BidHallFragment.class)) {
            mBidHallFragment.handleFilterOption();
        }

        if (isActiveFragment(MPThreadListFragment.class)) {
            openFileThreadActivity();
        }

        /// 处理设计师搜索逻辑 .
        if (isActiveFragment(DesignerListFragment.class)) {
            designerListFragment.handleSearchOption();
        }

        if (isActiveFragment(MyDecorationProjectFragment.class) || isActiveFragment(DecorationConsumerFragment.class)) {
            Intent intent = new Intent(this, IssueDemandActivity.class);
            mNickNameConsumer = TextUtils.isEmpty(mNickNameConsumer) ? UIUtils.getString(R.string.anonymity) : mNickNameConsumer;
            intent.putExtra(Constant.ConsumerPersonCenterFragmentKey.NICK_NAME, mNickNameConsumer);
            startActivity(intent);
        }
    }

    //判断圆形按钮跳入不同的个人中心界面
    @Override
    protected void leftCircleUserAvarClicked(View view) {
        super.leftCircleUserAvarClicked(view);
        Intent circleIntent = new Intent(MPConsumerHomeActivity.this, RegisterOrLoginActivity.class);


        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();

        if (null != memberEntity && Constant.UerInfoKey.DESIGNER_TYPE.equals(memberEntity.getMember_type())) {
            circleIntent = new Intent(MPConsumerHomeActivity.this, DesignerPersonalCenterActivity.class);
        }

        if (null != memberEntity && Constant.UerInfoKey.CONSUMER_TYPE.equals(memberEntity.getMember_type())) {

            circleIntent = new Intent(MPConsumerHomeActivity.this, ConsumerPersonalCenterActivity.class);
        }

        startActivity(circleIntent);

    }

    //设置指针控件宽度

    public void setChooseViewWidth(final boolean just) {

        ViewTreeObserver vto = contain.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                btWidth = contain.getMeasuredWidth();
                btHeight = contain.getMeasuredHeight();
                if (btWidth != 0) {
                    chooseViewPointer.setInitChooseVoewPoint(btWidth, just);
                }

            }

        });
    }

    protected void configureNavigationBar(int index) {

        super.configureNavigationBar(index);

        setConsumerOrDesignerPicture();//设置头像
        setVisibilityForNavButton(ButtonType.LEFTCIRCLE, true);

        switch (index) {
            case R.id.designer_main_radio_btn:
                // setTitleForNavbar(UIUtils.getString(R.string.app_name));
                FLAG_CLICK = TAB_HOME_CASE;
                setCaseLIbraryTitle();
                setVisibilityForNavButton(ButtonType.middlecontain, false);
                setVisibilityForNavButton(ButtonType.middle, true);
                break;

            case R.id.radio_btn_designer:
                FLAG_CLICK = TAB_DESIGNER;
                setDesignerListTitle();
                setVisibilityForNavButton(ButtonType.middlecontain, false);
                setVisibilityForNavButton(ButtonType.middle, true);
                setTitleForNavbar(UIUtils.getString(R.string.tab_designer));
                break;

            case R.id.designer_indent_list_btn:    /// 应标大厅按钮.
                setVisibilityForNavButton(ButtonType.middlecontain, false);
                setVisibilityForNavButton(ButtonType.middle, true);
                setImageForNavButton(ButtonType.RIGHT, R.drawable.filtratenew);

                setTitleForNavbar(UIUtils.getString(R.string.tab_hall));
                Intent mIntent = new Intent(BidHallFragment.ACTION_NAME);
                sendBroadcast(mIntent);
                break;

            case R.id.designer_person_center_radio_btn:  /// 个人中心按钮.
                hideCaseLIbraryTitle();
                //判断登陆的是设计师还是消费者，，，我的项目加载不同的信息
                MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
                setChooseViewWidth(true);
                if (memberEntity != null && Constant.UerInfoKey.DESIGNER_TYPE.equals(memberEntity.getMember_type())) {
                    setVisibilityForNavButton(ButtonType.middle, false);
                    contain.setVisibility(View.VISIBLE);
                    if (contain.getChildCount() == 0) {
                        contain.addView(contain_layout);
                    }
                }
                if (memberEntity != null && Constant.UerInfoKey.CONSUMER_TYPE.equals(memberEntity.getMember_type())) {

                    setImageForNavButton(ButtonType.RIGHT, R.drawable.icon_title_add);
                    setTitleForNavbar(UIUtils.getString(R.string.consumer_decoration));
                }


                break;

            case R.id.designer_session_radio_btn:  /// 会話聊天.
                FLAG_CLICK = TAB_IM;
                setVisibilityForNavButton(ButtonType.middlecontain, false);
                setVisibilityForNavButton(ButtonType.middle, true);
                String acs_Member_Type = AdskApplication.getInstance().getMemberEntity().getMember_type();
                Boolean ifIsDesiner = Constant.UerInfoKey.DESIGNER_TYPE.equals(acs_Member_Type);
                setImageForNavButton(ButtonType.RIGHT, R.drawable.msg_file);
                if (ifIsDesiner) {
                    setImageForNavButton(ButtonType.SECONDARY, R.drawable.scan);
                    String hs_uid = AdskApplication.getInstance().getMemberEntity().getHs_uid();
                    String acs_Member_Id = AdskApplication.getInstance().getMemberEntity().getMember_id();
                    ifIsLohoDesiner(acs_Member_Id, hs_uid);
                } else {
                    setVisibilityForNavButton(ButtonType.SECONDARY, false);
                }
                getFileThreadUnreadCount();

            default:
                break;
        }
    }

    private void setDesignerListTitle() {
        setImageForNavButton(ButtonType.RIGHT, com.autodesk.shejijia.shared.R.drawable.icon_search);
        setImageForNavButton(ButtonType.SECONDARY, com.autodesk.shejijia.shared.R.drawable.icon_filtrate_normal);
        setVisibilityForNavButton(ButtonType.RIGHT, true);
        setVisibilityForNavButton(ButtonType.SECONDARY, true);
    }

    //切换fragment 改变指针
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rb_customer_elite:
                MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
                if (memberEntity != null) {
                    startActivity(new Intent(this, SixProductsActivity.class));
                } else {
                    mRadioGroup.check(R.id.consumer_main_radio_btn);
                    AdskApplication.getInstance().doLogin(this);
                }
                break;

            case R.id.bidding:
                //指针
                setMyProjectTitleColorChange(bidding, design/*, construction*/);
                chooseViewPointer.setWidthOrHeight(btWidth, btHeight, POINTER_START_NUMBER + POINTER_MIDDLE_END_NUMBER, POINTER_START_END_NUMBER - POINTER_MIDDLE_END_NUMBER);
                mDesignerPersonalCenterFragment.setBidingFragment();
                break;
            case R.id.design:
                setMyProjectTitleColorChange(design, bidding/*, construction*/);
                chooseViewPointer.setWidthOrHeight(btWidth, btHeight, POINTER_START_END_NUMBER + POINTER_MIDDLE_END_NUMBER, POINTER_END_NUMBER - POINTER_MIDDLE_END_NUMBER);
                //判断进入北舒套餐，，还是进入普通订单页面
                if (null != designerInfoDetails) {
                    if (designerInfoDetails.getDesigner().getIs_loho() == IS_BEI_SHU) {
                        /// 北舒 .
                        mDesignerPersonalCenterFragment.setDesignBeiShuFragment();
                    } else {
                        mDesignerPersonalCenterFragment.setDesignFragment();
                    }
                } else {
                    mDesignerPersonalCenterFragment.setDesignFragment();
                }

                break;

//            case R.id.construction:
//                setMyProjectTitleColorChange(construction, design, bidding);
//                chooseViewPointer.setWidthOrHeight(btWidth, btHeight, POINTER_MIDDLE_END_NUMBER, POINTER_END_NUMBER);
//
//                mDesignerPersonalCenterFragment.setConstructionFragment();
//                break;

        }

    }

    protected void setMyProjectTitleColorChange(TextView titleCheck, TextView textUnckeck/*, TextView titleUncheck*/) {

        titleCheck.setTextColor(getResources().getColor(R.color.my_project_title_pointer_color));
        textUnckeck.setTextColor(getResources().getColor(R.color.my_project_title_text_color));
        // titleUncheck.setTextColor(getResources().getColor(R.color.my_project_title_text_color));

    }

    private void ifIsLohoDesiner(String desiner_id, String hs_uid) {

        MPServerHttpManager.getInstance().ifIsLohoDesiner(desiner_id, hs_uid, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("designer");
                    int is_loho = jsonObject1.getInt("is_loho");
                    //2：乐屋设计师添加扫描二维码功能（其他几种未判断）
                    setImageForNavButton(ButtonType.SECONDARY, com.autodesk.shejijia.shared.R.drawable.scan);
                    setVisibilityForNavButton(ButtonType.SECONDARY, true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    protected void secondaryNavButtonClicked(View view) {
        super.secondaryNavButtonClicked(view);
        Intent intent;
        switch (FLAG_CLICK) {
            case TAB_HOME_CASE:
                intent = new Intent(this, FiltrateActivity.class);
                intent.putExtra(Constant.CaseLibrarySearch.SEARCH_TYPE, 1);
                intent.putExtra(Constant.CaseLibrarySearch.AREA_INDEX, filtrateContentBean == null ? 0 : filtrateContentBean.getAreaIndex());
                intent.putExtra(Constant.CaseLibrarySearch.HOUSING_INDEX, filtrateContentBean == null ? 0 : filtrateContentBean.getHouseIndex());
                intent.putExtra(Constant.CaseLibrarySearch.STYLE_INDEX, filtrateContentBean == null ? 0 : filtrateContentBean.getStyleIndex());
                this.startActivityForResult(intent, CASE_CODE);
                break;

            case TAB_IM:
                intent = new Intent(MPConsumerHomeActivity.this, CaptureQrActivity.class);
                startActivityForResult(intent, CHAT_CODE);
                break;

            case TAB_DESIGNER:
                designerListFragment.handleFilterOption();
                break;

            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == getDesignerMainRadioBtnId())
            showFragment(getDesignerMainRadioBtnId());

        super.onCheckedChanged(group, checkedId);
    }

    protected int getDesignerMainRadioBtnId() {
        return R.id.designer_main_radio_btn;
    }

    protected int getDesignerButtonId() {
        return R.id.radio_btn_designer;
    }

    protected int getIMButtonId() {
        return R.id.designer_session_radio_btn;
    }

    protected int getRadioGroupId() {
        return R.id.designer_main_radio_group;
    }


    protected int getMainContentId() {
        return R.id.main_content;
    }

    protected Fragment getDesignerFragment() {
        return designerListFragment;
    }


    /**
     * 网络获取数据并且更新
     */
    private void updateViewFromData() {

        if (mConsumerEssentialInfoEntity != null && !TextUtils.isEmpty(mConsumerEssentialInfoEntity.getAvatar()) && MPConsumerHomeActivity.this != null) {
            mNickNameConsumer = mConsumerEssentialInfoEntity.getNick_name();
            ImageUtils.displayAvatarImage(mConsumerEssentialInfoEntity.getAvatar(), user_avatar);
        }

        if (designerInfoDetails != null && !TextUtils.isEmpty(designerInfoDetails.getAvatar()) && MPConsumerHomeActivity.this != null) {
            ImageUtils.displayAvatarImage(designerInfoDetails.getAvatar(), user_avatar);
        }

    }

//    /**
//     * 获取个人基本信息
//     *
//     * @param member_id
//     * @brief For details on consumers .
//     */
//    public void getConsumerInfoData(String member_id) {
//        MPServerHttpManager.getInstance().getConsumerInfoData(member_id, new OkJsonRequest.OKResponseCallback() {
//
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//                String jsonString = GsonUtil.jsonToString(jsonObject);
//                mConsumerEssentialInfoEntity = GsonUtil.jsonToBean(jsonString, ConsumerEssentialInfoEntity.class);
//
//                updateViewFromData();
//            }
//
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                MPNetworkUtils.logError(TAG, volleyError);
//                if (MPConsumerHomeActivity.this != null) {
//                    new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, MPConsumerHomeActivity.this,
//                            AlertView.Style.Alert, null).show();
//                }
//            }
//        });
//    }

    public void getALLWkFlowStatePointInformation() {

        MPServerHttpManager.getInstance().getAll_WkFlowStatePointInformation(new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                String jsonString = GsonUtil.jsonToString(jsonObject);
                WkFlowStateInfoBean WkFlowStateInfoBean = GsonUtil.jsonToBean(jsonString, WkFlowStateInfoBean.class);
                List<TipWorkFlowTemplateBean> tip_work_flow_template = WkFlowStateInfoBean.getTip_work_flow_template();
                WkFlowStateMap.sWkFlowBeans = tip_work_flow_template;
            }
        });
    }


    /**
     * 设计师个人信息
     *
     * @param designer_id
     * @param hs_uid
     */
    public void getDesignerInfoData(String designer_id, String hs_uid) {
        MPServerHttpManager.getInstance().getDesignerInfoData(designer_id, hs_uid, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String jsonString = GsonUtil.jsonToString(jsonObject);
                designerInfoDetails = GsonUtil.jsonToBean(jsonString, DesignerInfoDetails.class);
                updateViewFromData();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);

                ApiStatusUtil.getInstance().apiStatuError(volleyError, MPConsumerHomeActivity.this);
            }
        });
    }

    //设置头像
    private void setConsumerOrDesignerPicture() {
        MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        if (mMemberEntity != null && Constant.UerInfoKey.CONSUMER_TYPE.equals(mMemberEntity.getMember_type())) {

            getConsumerInfoData(mMemberEntity.getAcs_member_id());

            return;

        }

        if (mMemberEntity != null && Constant.UerInfoKey.DESIGNER_TYPE.equals(mMemberEntity.getMember_type())) {

            getDesignerInfoData(mMemberEntity.getAcs_member_id(), mMemberEntity.getHs_uid());

            return;

        }

        setImageForNavCircleView(ButtonType.LEFTCIRCLE, R.drawable.icon_default_avator);

    }

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
                designerInfoDetails = GsonUtil.jsonToBean(jsonString, DesignerInfoDetails.class);
                if (designerInfoDetails!=null){
                    is_loho = designerInfoDetails.getDesigner().getIs_loho();
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                if (MPConsumerHomeActivity.this != null) {
                    ApiStatusUtil.getInstance().apiStatuError(volleyError, MPConsumerHomeActivity.this);
                }
            }
        });
    }


    private void isShowBidHallFragment() {
        MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        if (mMemberEntity != null && Constant.UerInfoKey.DESIGNER_TYPE.equals(mMemberEntity.getMember_type())) {
            mDesignerIndentListBtn.setVisibility(View.VISIBLE);
            rbCustomerElite.setVisibility(View.GONE);
            tvGronMmsgNumber.setVisibility(View.VISIBLE);
            return;
        }
        rbCustomerElite.setVisibility(View.VISIBLE);
        tvGronMmsgNumber.setVisibility(View.GONE);
        mDesignerIndentListBtn.setVisibility(View.GONE);

    }


    //判断是否聊过天，跳转到之前聊天室或新聊天室
    private void jumpToChatRoom(String scanResult) {

        if (scanResult.contains(Constant.ConsumerDecorationFragment.hs_uid)
                && scanResult.contains(Constant.DesignerCenterBundleKey.MEMBER)) {

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
                            intent.putExtra(ChatRoomActivity.MEDIA_TYPE, UrlMessagesContants.mediaIdProject);
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
            Bundle bundle = data.getExtras();
            switch (resultCode) {
                case RESULT_OK:
                    String scanResult = bundle.getString(Constant.QrResultKey.SCANNER_RESULT);
                    if (null != scanResult) {
                        jumpToChatRoom(scanResult);
                    }
                    break;
            }
        } else if (resultCode == FiltrateActivity.HC_RESULT_CODE && data != null) {
            Bundle bundle = data.getExtras();
            FiltrateContentBean filtrateContentBean = (FiltrateContentBean) bundle.getSerializable(Constant.CaseLibrarySearch.CONTENT_BEAN);
            this.filtrateContentBean = filtrateContentBean;
            Intent intent = new Intent();
            intent.setAction(BroadCastInfo.MPFITER_CASES);
            intent.putExtra("FiltrateContentBean", filtrateContentBean);
            sendBroadcast(intent);
        }
    }

    private final int CHAT_CODE = 0;
    private static final int IS_BEI_SHU = 1;
    private RadioButton mDesignerMainRadioBtn;
    private RadioButton mDesignerPersonCenterRadioBtn;
    private RadioButton mDesignerIndentListBtn;
    private RadioButton designer_main_radio_btn;
    private RadioButton rbCustomerElite;
    private RadioButton radioBtnDesigner;
    private RadioGroup designer_main_radio_group;

    private DecorationConsumerFragment mConsumerPersonalCenterFragment;

    private static final String HOME_FRAGMENT_TAG = "HOME_FRAGMENT_TAG";
    private static final String BID_FRAGMENT_TAG = "BID_FRAGMENT_TAG";
    private static final String DESIGNER_PERSONAL_FRAGMENT_TAG = "DESIGNER_FRAGMENT_TAG";
    private static final String CONSUMER_PERSONAL_FRAGMENT_TAG = "CONSUMER_FRAGMENT_TAG";

    private ImageView user_avatar;

    private TextView bidding;
    private TextView design;
    private TextView tvGronMmsgNumber;
    private TextView construction;
    //  private TextView construction;
    private LinearLayout contain;
    private LinearLayout contain_point;
    private View contain_layout;
    private ChooseViewPointer chooseViewPointer;
    private int index;//判断所在fragment
    private int lastIndex;
    private String mNickNameConsumer;
    private boolean isRefush = false;
    final int RESULT_CODE = 101;
    final float POINTER_START_NUMBER = 0F;
    final float POINTER_START_END_NUMBER = 1 / 2F;
    final float POINTER_MIDDLE_END_NUMBER = 1 / 9F;
    final float POINTER_END_NUMBER = 1F;
    private int btWidth;
    private int btHeight;
    private int screenWidth;

    private String FLAG_CLICK = TAB_HOME_CASE;
    public static final int CASE_CODE = 0x92;

    private UserHomeFragment mUserHomeFragment;

    private ConsumerEssentialInfoEntity mConsumerEssentialInfoEntity;
    private WkFlowStateBean wkFlowStateBean;
    private FiltrateContentBean filtrateContentBean;

    private DesignerListFragment designerListFragment;
    private BidHallFragment mBidHallFragment;
    private DesignerInfoDetails designerInfoDetails;
    private MyDecorationProjectDesignerFragment mDesignerPersonalCenterFragment;
}
