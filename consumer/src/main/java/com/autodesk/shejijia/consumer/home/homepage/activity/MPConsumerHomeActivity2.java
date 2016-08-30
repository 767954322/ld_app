package com.autodesk.shejijia.consumer.home.homepage.activity;

public class MPConsumerHomeActivity2 /*extends BaseHomeActivity implements View.OnClickListener ,OnTabSelectListener*/{

//    private static String THREAD_FRAGMENT_TAG = "THREAD_FRAGMENT_TAG";
//    private String[] mTitles_consumer = {"案例", "设计师", "我要装修", "聊天", "我的项目"};
//    private String[] mTitles_designer = {"案例", "设计师", "应标大厅", "聊天", "我的订单"};
//    private int[] mIconUnselectIds_consumer = {
//            R.drawable.icon_case_normal,
//            R.drawable.icon_case_normal,
//            R.drawable.icon_decorate,
//            R.drawable.icon_communication_normal,
//            R.drawable.icon_person_normal};
//    private int[] mIconSelectIds_consumer = {
//            R.drawable.icon_case_pressed,
//            R.drawable.icon_case_pressed,
//            R.drawable.icon_decorate,
//            R.drawable.icon_communication_pressed,
//            R.drawable.icon_person_pressed};
//    private int[] mIconUnselectIds_designer = {
//            R.drawable.icon_case_normal,
//            R.drawable.icon_case_normal,
//            R.drawable.icon_bid_normal,
//            R.drawable.icon_communication_normal,
//            R.drawable.icon_person_normal};
//    private int[] mIconSelectIds_designer = {
//            R.drawable.icon_case_pressed,
//            R.drawable.icon_case_pressed,
//            R.drawable.icon_bid_pressed,
//            R.drawable.icon_communication_pressed,
//            R.drawable.icon_person_pressed};
//    private ArrayList<TabEntity> mTabEntities = new ArrayList<>();
//
//    private Map<Integer, Fragment> mFragments_consumer = new HashMap<>();
//    private Map<Integer, Fragment> mFragments_designer = new HashMap<>();
//    private FragmentManager mFragmentManager;
//
//    private CommonTabLayout mBottomTabLayout;
//
//    @Override
//    protected int getLayoutResId() {
//        return R.layout.activity_designer_main2;
//    }

//    @Override
//    protected void initView() {
//        super.initView();
//
//        setVisibilityForNavButton(ButtonType.LEFT, false);
//
//
//        mBottomTabLayout = (CommonTabLayout) findViewById(R.id.mp_consumer_home_activity_ctl);
//
//        MaterialTabs materialTabs = (MaterialTabs) findViewById(com.autodesk.shejijia.shared.R.id.case_library_head);
//        materialTabs.setVisibility(View.VISIBLE);
//        contain_layout = LayoutInflater.from(this).inflate(R.layout.contain_choose_layout, null);
//        chooseViewPointer = (ChooseViewPointer) contain_layout.findViewById(R.id.choose_point);
//        bidding = (TextView) contain_layout.findViewById(R.id.bidding);
//        design = (TextView) contain_layout.findViewById(R.id.design);
//        construction = (TextView) contain_layout.findViewById(R.id.construction);
//
//
//        contain = (LinearLayout) findViewById(R.id.ll_contain);
//
//        setMyProjectTitleColorChange(design, bidding, construction);
//
//        user_avatar = (ImageView) findViewById(R.id.user_avatar);
//
//        //获取节点信息
//
//        getWkFlowStatePointInformation();
//
//    }
//
//    @Override
//    protected void initData(Bundle savedInstanceState) {
//        mFragmentManager = getSupportFragmentManager();
////        setTitleForNavbar("haha");
//        setCaseLIbraryTitle();
//        setVisibilityForNavButton(ButtonType.middlecontain, false);
//        setVisibilityForNavButton(ButtonType.middle, true);
//
//        initBottom();
//        initFragment();
//        mBottomTabLayout.setCurrentTab(0);
//    }
//
//    /**
//     * 聊天界面
//     * @param position
//     */
//    private void chatFragment(int position) {
//
//        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
//        MPThreadListFragment mMPThreadListFragment = new MPThreadListFragment();
//
//        if (memberEntity == null) {
//            AdskApplication.getInstance().doLogin(this);
//        }
//
//        getFileThreadUnreadCount();
//
//        Bundle bundle = new Bundle();
//        bundle.putString(MPThreadListFragment.MEMBERID, memberEntity.getAcs_member_id());
//        bundle.putString(MPThreadListFragment.MEMBERTYPE, memberEntity.getMember_type());
//        bundle.putBoolean(MPThreadListFragment.ISFILEBASE, false);
//        mMPThreadListFragment.setArguments(bundle);
//
////                            loadMainFragment(mMPThreadListFragment, THREAD_FRAGMENT_TAG);
//        mMPThreadListFragment.onFragmentShown();
//
//        setVisibilityForNavButton(ButtonType.middlecontain, false);
//        setVisibilityForNavButton(ButtonType.middle, true);
//        String acs_Member_Type = AdskApplication.getInstance().getMemberEntity().getMember_type();
//        Boolean ifIsDesiner = Constant.UerInfoKey.DESIGNER_TYPE.equals(acs_Member_Type);
//        setImageForNavButton(ButtonType.RIGHT, R.drawable.msg_file);
//
//        if (ifIsDesiner) {
//            String hs_uid = AdskApplication.getInstance().getMemberEntity().getHs_uid();
//            String acs_Member_Id = AdskApplication.getInstance().getMemberEntity().getMember_id();
//            ifIsLohoDesiner(acs_Member_Id, hs_uid);
//        } else {
//            setVisibilityForNavButton(ButtonType.SECONDARY, false);
//        }
//
//        addFragmentToList(mFragments_designer, mMPThreadListFragment, position);
//    }
//
//    /**
//     * 添加fragment到集合
//     *
//     * @param fragments
//     * @param f
//     */
//    private void addFragmentToList(Map<Integer, Fragment> fragments, Fragment f, int position) {
//        if (!fragments.containsValue(f)) {
//            fragments.put(position, f);
////            LinearLayout layout = (LinearLayout) findViewById(R.id.main_content);
////            layout.removeAllViews();
//            mFragmentManager.beginTransaction().replace(R.id.mp_consumer_home_activity_fl, f).hide(f).commitAllowingStateLoss();
//        }
//    }
//
//    /**
//     * 判断是否登录
//     *
//     * @return
//     */
//    private boolean isLogin() {
//        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
//        if (memberEntity == null) {
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * 判断并登录
//     */
//    private void doLogin() {
//        if (!isLogin()) {
//            AdskApplication.getInstance().doLogin(MPConsumerHomeActivity2.this);
//            return;
//        }
//    }
//
//    /**
//     * 初始化fragment界面
//     */
//    private void initFragment() {
//        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
//        //判断是消费者，还是设计师，，从而区分消费者和设计师
//        if (memberEntity != null && Constant.UerInfoKey.DESIGNER_TYPE.equals(memberEntity.getMember_type())) {
//            mFragments_designer.clear();
//            mFragments_designer.put(0, UserHomeFragment.getInstance());//案例
////            mFragments_designer.add(UserHomeFragment.getInstance());//设计师
////            mFragments_designer.add(BidHallFragment.getInstance());//应标大厅
////            mFragments_designer.add(UserHomeFragment.getInstance());//聊天
////            mFragments_designer.add(UserHomeFragment.getInstance());//我的订单
////            for (Fragment fragment : mFragments_designer) {
////            mFragmentManager.beginTransaction().add(R.id.main_content, mFragments_designer.get(0)).hide(mFragments_designer.get(0)).commitAllowingStateLoss();
////            }
//            addFragmentToList(mFragments_designer, UserHomeFragment.getInstance(), 0);
//            setFragments(0, mFragments_designer);
//        } else {
//            mFragments_consumer.clear();
//            mFragments_consumer.put(0, UserHomeFragment.getInstance());//案例
////            mFragments_consumer.add(UserHomeFragment.getInstance());//设计师
////            mFragments_consumer.add(UserHomeFragment.getInstance());//用设计师占位
////            mFragments_consumer.add(UserHomeFragment.getInstance());//聊天
////            mFragments_consumer.add(DecorationConsumerFragment.getInstance());//我的项目
////            for (Fragment fragment : mFragments_consumer) {
////            mFragmentManager.beginTransaction().add(R.id.main_content, mFragments_consumer.get(0)).hide(mFragments_consumer.get(0)).commitAllowingStateLoss();
////            }
//            addFragmentToList(mFragments_consumer, UserHomeFragment.getInstance(), 0);
//            setFragments(0, mFragments_consumer);
//
//        }
//    }
//
//    /**
//     * 界面切换控制
//     **/
//    public void setFragments(int index, Map<Integer, Fragment> fragments) {
//        for (int i : fragments.keySet()) {
//            FragmentTransaction ft = mFragmentManager.beginTransaction();
//            Fragment fragment = fragments.get(i);
//            if (i == index) {
//                ft.show(fragment);
//            } else {
//                if (fragment.isAdded()) {
//                    ft.remove(fragment);
//                }
//            }
//
//            ft.commitAllowingStateLoss();
//        }
//    }
//
//    /**
//     * 刷新底部
//     */
//
//    private void initBottom() {
//        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
//        //判断是消费者，还是设计师，，从而区分消费者和设计师
//        if (memberEntity != null && Constant.UerInfoKey.DESIGNER_TYPE.equals(memberEntity.getMember_type())) {
//            mTabEntities.clear();
//            for (int i = 0; i < mTitles_designer.length; i++) {
//                mTabEntities.add(new TabEntity(mTitles_designer[i], mIconSelectIds_designer[i], mIconUnselectIds_designer[i]));
//            }
//            mBottomTabLayout.setTabData(mTabEntities);
//        } else {
//            mTabEntities.clear();
//            for (int i = 0; i < mTitles_consumer.length; i++) {
//                if (i == 2) {
//                    mTabEntities.add(new TabEntity(mTitles_consumer[i], mIconSelectIds_consumer[i], mIconUnselectIds_consumer[i], 120, 120));
//                } else {
//                    mTabEntities.add(new TabEntity(mTitles_consumer[i], mIconSelectIds_consumer[i], mIconUnselectIds_consumer[i]));
//                }
//            }
//            mBottomTabLayout.setTabData(mTabEntities);
//        }
//    }
//
//    @Override
//    protected void initListener() {
//        super.initListener();
//        mBottomTabLayout.setOnTabSelectListener(this);
//        //注册未读消息广播
//        getmMemberUnreadCountManager().registerForMessageUpdates(this, new MPMemberUnreadCountManager.MPMemberUnreadCountInterface2() {
//            @Override
//            public void getUnreadBadgeLabel(int num) {
//                refreshUnreadCount(num);
//            }
//        });
//    }
//
//    @Override
//    protected void onResume() {
//        Intent intent = getIntent();
////        setChooseViewWidth(true);
//        int id = intent.getIntExtra(Constant.DesignerBeiShuMeal.SKIP_DESIGNER_PERSONAL_CENTER, -1);
//        if (id > 0) {
//            switch (id) {
//                case 1:
//                    /// 回到个人中心 .
//                    break;
//                default:
//                    break;
//            }
//        }
//        initBottom();
//        initFragment();
//        mBottomTabLayout.setCurrentTab(0);
//        setConsumerOrDesignerPicture();
////        refreshUnreadCount();
//        getMemberThreadUnreadCount();
//        super.onResume();
//    }
//
//    /**
//     * 刷新未读消息
//     */
//    private void refreshUnreadCount(int num) {
//        if (num > 0) {
//            mBottomTabLayout.showMsg(3, num);
//            mBottomTabLayout.setMsgMargin(3, -10, -3);
//        } else {
//            mBottomTabLayout.hideMsg(3);
//        }
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        initBottom();
//        initFragment();
//        setConsumerOrDesignerPicture();//设置头像
//
////        MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
////        //登陆设计师时，会进入；
////        if (mMemberEntity != null && Constant.UerInfoKey.DESIGNER_TYPE.equals(mMemberEntity.getMember_type())) {
////            designer_main_radio_group.check(index);
////
////        }
////        //登陆消费者时，会进入
////        if (mMemberEntity != null && Constant.UerInfoKey.CONSUMER_TYPE.equals(mMemberEntity.getMember_type())) {
////            designer_main_radio_group.check(index);
////        }
////
////        //未登录状态，会自动进入案例fragment
////
////        if (mMemberEntity == null) {
////            designer_main_radio_btn.setChecked(true);
////        }
//    }
//
//
//    @Override
//    protected boolean needLoginOnRadiobuttonTap(int id) {
//        if ((super.needLoginOnRadiobuttonTap(id)) ||
//                (id == R.id.designer_indent_list_btn) ||
//                (id == R.id.designer_person_center_radio_btn))
//            return true;
//        else
//            return false;
//    }
//
//    //将每个fragment添加
//    @Override
//    protected void initAndAddFragments(int index) {
//        super.initAndAddFragments(index);
////        this.index = index;
//
//        if (mUserHomeFragment == null && index == getDesignerMainRadioBtnId()) {
//            mUserHomeFragment = new UserHomeFragment();
//            loadMainFragment(mUserHomeFragment, HOME_FRAGMENT_TAG);
//        }
//
//        if (mBidHallFragment == null && index == R.id.designer_indent_list_btn) {
//            mBidHallFragment = new BidHallFragment();
//            loadMainFragment(mBidHallFragment, BID_FRAGMENT_TAG);
//        }
//
//        if (index == R.id.designer_person_center_radio_btn) {
//            MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
//            //判断是消费者，还是设计师，，从而区分消费者和设计师
//            if (memberEntity != null && Constant.UerInfoKey.DESIGNER_TYPE.equals(memberEntity.getMember_type())) {
//                if (mDesignerPersonalCenterFragment == null) {
//                    mDesignerPersonalCenterFragment = new MyDecorationProjectDesignerFragment();
//                    loadMainFragment(mDesignerPersonalCenterFragment, DESIGNER_PERSONAL_FRAGMENT_TAG);
//
//                    mTabEntities.clear();
//                    for (int i = 0; i < mTitles_designer.length; i++) {
//                        if (i == 2) {
//                            mTabEntities.add(new TabEntity(mTitles_designer[i], mIconSelectIds_designer[i], mIconUnselectIds_designer[i], 120, 120));
//                        } else {
//                            mTabEntities.add(new TabEntity(mTitles_designer[i], mIconSelectIds_designer[i], mIconUnselectIds_designer[i]));
//                        }
//                    }
//                    mBottomTabLayout.setTabData(mTabEntities);
//                }
//            } else {
//                if (mConsumerPersonalCenterFragment == null) {
////                    mConsumerPersonalCenterFragment = new MyDecorationProjectFragment();
//                    mConsumerPersonalCenterFragment = new DecorationConsumerFragment();
//                    loadMainFragment(mConsumerPersonalCenterFragment, CONSUMER_PERSONAL_FRAGMENT_TAG);
//
//                    mTabEntities.clear();
//                    for (int i = 0; i < mTitles_consumer.length; i++) {
//                        if (i == 2) {
//                            mTabEntities.add(new TabEntity(mTitles_consumer[i], mIconSelectIds_consumer[i], mIconUnselectIds_consumer[i], 120, 120));
//                        } else {
//                            mTabEntities.add(new TabEntity(mTitles_consumer[i], mIconSelectIds_consumer[i], mIconUnselectIds_consumer[i]));
//                        }
//                    }
//                    mBottomTabLayout.setTabData(mTabEntities);
//                }
//            }
//        }
//    }
//
//    @Override
//    protected Fragment getFragmentByButtonId(int id) {
//        Fragment f = super.getFragmentByButtonId(id);
//        if (id == R.id.designer_indent_list_btn)
//            f = mBidHallFragment;
//        else if (id == R.id.designer_person_center_radio_btn) {
//            MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
//            if (memberEntity != null && Constant.UerInfoKey.DESIGNER_TYPE.equals(memberEntity.getMember_type()))
//                f = mDesignerPersonalCenterFragment;
//            else
//                f = mConsumerPersonalCenterFragment;
//        } else if (id == getDesignerMainRadioBtnId()) {
//            f = mUserHomeFragment;
//        }
//        return f;
//    }
//
//    //监听筛选按钮，，，
//    @Override
//    protected void rightNavButtonClicked(View view) {
//        if (isActiveFragment(BidHallFragment.class)) {
//
//            mBidHallFragment.handleFilterOption();
//        }
//
//        if (isActiveFragment(MPThreadListFragment.class))
//            openFileThreadActivity();
//
//        if (isActiveFragment(MyDecorationProjectFragment.class) || isActiveFragment(DecorationConsumerFragment.class)) {
//            Intent intent = new Intent(this, IssueDemandActivity.class);
//            mNickNameConsumer = TextUtils.isEmpty(mNickNameConsumer) ? UIUtils.getString(R.string.anonymity) : mNickNameConsumer;
//            intent.putExtra(Constant.ConsumerPersonCenterFragmentKey.NICK_NAME, mNickNameConsumer);
//            startActivity(intent);
//        }
//    }
//
//    //判断圆形按钮跳入不同的个人中心界面
//    @Override
//    protected void leftCircleUserAvarClicked(View view) {
//        super.leftCircleUserAvarClicked(view);
//        Intent circleIntent = new Intent(MPConsumerHomeActivity2.this, RegisterOrLoginActivity.class);
//
//
//        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
//        if (memberEntity != null && Constant.UerInfoKey.DESIGNER_TYPE.equals(memberEntity.getMember_type())) {
//
//            circleIntent = new Intent(MPConsumerHomeActivity2.this, DesignerPersonalCenterActivity.class);
//
//        }
//
//        if (memberEntity != null && Constant.UerInfoKey.CONSUMER_TYPE.equals(memberEntity.getMember_type())) {
//
//            circleIntent = new Intent(MPConsumerHomeActivity2.this, ConsumerPersonalCenterActivity.class);
//        }
//
//
//        startActivity(circleIntent);
//
//    }
//
////    //设置指针控件宽度
////    public void setChooseViewWidth(final boolean just) {
////
////        ViewTreeObserver vto = contain.getViewTreeObserver();
////        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
////
////            @Override
////            public void onGlobalLayout() {
//////                btWidth = contain.getMeasuredWidth();
//////                btHeight = contain.getMeasuredHeight();
////                if (btWidth != 0) {
////
////                    chooseViewPointer.setInitChooseVoewPoint(btWidth, just);
////                }
////
////            }
////
////        });
////    }
//
//    protected void configureNavigationBar(int index) {
//
//        super.configureNavigationBar(index);
//
//        setConsumerOrDesignerPicture();//设置头像
//        setVisibilityForNavButton(ButtonType.LEFTCIRCLE, true);
//
//        switch (index) {
//            case R.id.designer_main_radio_btn:
//                setTitleForNavbar(UIUtils.getString(R.string.app_name));
//                setVisibilityForNavButton(ButtonType.middlecontain, false);
//                setVisibilityForNavButton(ButtonType.middle, true);
//                break;
//            case R.id.designer_indent_list_btn:    /// 应标大厅按钮.
//                setVisibilityForNavButton(ButtonType.middlecontain, false);
//                setVisibilityForNavButton(ButtonType.middle, true);
//                setImageForNavButton(ButtonType.RIGHT, R.drawable.filtratenew);
//
////                TextView textView = (TextView) findViewById(R.id.nav_right_textView);
////                textView.setVisibility(View.VISIBLE);
////                Drawable drawable = UIUtils.getDrawable(R.drawable.shanjiao_ico);
////                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
////                textView.setCompoundDrawables(null, null, drawable, null);
////                textView.setText(UIUtils.getString(R.string.bid_filter));
//
//
//                setTitleForNavbar(UIUtils.getString(R.string.tab_hall));
//                break;
//
//            case R.id.designer_person_center_radio_btn:  /// 个人中心按钮.
//                //判断登陆的是设计师还是消费者，，，我的项目加载不同的信息
//                MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
////                setChooseViewWidth(true);
//                if (memberEntity != null && Constant.UerInfoKey.DESIGNER_TYPE.equals(memberEntity.getMember_type())) {
//
//
//                    setVisibilityForNavButton(ButtonType.middle, false);
//
////                    contain.setVisibility(View.VISIBLE);
////                    if (contain.getChildCount() == 0) {
////
////                        contain.addView(contain_layout);
////                    }
//                }
//
//                if (memberEntity != null && Constant.UerInfoKey.CONSUMER_TYPE.equals(memberEntity.getMember_type())) {
//
//                    setTitleForNavbar(UIUtils.getString(R.string.consumer_decoration));
//                }
//
//
//                break;
//
//            case R.id.designer_session_radio_btn:  /// 会話聊天.
//
//
//
//                setVisibilityForNavButton(ButtonType.middlecontain, false);
//                setVisibilityForNavButton(ButtonType.middle, true);
//                String acs_Member_Type = AdskApplication.getInstance().getMemberEntity().getMember_type();
//                Boolean ifIsDesiner = Constant.UerInfoKey.DESIGNER_TYPE.equals(acs_Member_Type);
//                setImageForNavButton(ButtonType.RIGHT, R.drawable.msg_file);
//                if (ifIsDesiner) {
//                    String hs_uid = AdskApplication.getInstance().getMemberEntity().getHs_uid();
//                    String acs_Member_Id = AdskApplication.getInstance().getMemberEntity().getMember_id();
//                    ifIsLohoDesiner(acs_Member_Id, hs_uid);
//                } else {
//                    setVisibilityForNavButton(ButtonType.SECONDARY, false);
//                }
//                getFileThreadUnreadCount();
//
//            default:
//                break;
//        }
//    }
//
//    /**
//     * 获取屏幕的宽
//     */
//    public static int getScreenWidth(Context context) {
//
//        WindowManager wm = (WindowManager) context
//                .getSystemService(Context.WINDOW_SERVICE);
//        int width = wm.getDefaultDisplay().getWidth();
//
//        return width;
//    }
//
//    //切换fragment 改变指针
//    @Override
//    public void onClick(View v) {
//
//        switch (v.getId()) {
//
//            case R.id.bidding:
//                //指针
////                setMyProjectTitleColorChange(bidding, design, construction);
////                chooseViewPointer.setWidthOrHeight(btWidth, btHeight, POINTER_START_NUMBER, POINTER_START_END_NUMBER);
////                mDesignerPersonalCenterFragment.setBidingFragment();
//                break;
//            case R.id.design:
////                setMyProjectTitleColorChange(design, bidding, construction);
////                chooseViewPointer.setWidthOrHeight(btWidth, btHeight, POINTER_START_END_NUMBER, POINTER_MIDDLE_END_NUMBER);
////                //判断进入北舒套餐，，还是进入普通订单页面
////                if (null != designerInfoDetails && null != designerInfoDetails.getDesigner()) {
////                    if (designerInfoDetails.getDesigner().getIs_loho() == IS_BEI_SHU) {
////                        /// 北舒 .
////                        mDesignerPersonalCenterFragment.setDesignBeiShuFragment();
////                    } else {
////                        mDesignerPersonalCenterFragment.setDesignFragment();
////                    }
////                } else {
////                    mDesignerPersonalCenterFragment.setDesignFragment();
////                }
//
//                break;
//            case R.id.construction:
////                setMyProjectTitleColorChange(construction, design, bidding);
////                chooseViewPointer.setWidthOrHeight(btWidth, btHeight, POINTER_MIDDLE_END_NUMBER, POINTER_END_NUMBER);
////
////                mDesignerPersonalCenterFragment.setConstructionFragment();
//                break;
//
//        }
//
//    }
//
//    protected void setMyProjectTitleColorChange(TextView titleCheck, TextView textUnckeck, TextView titleUncheck) {
//
//        titleCheck.setTextColor(getResources().getColor(R.color.my_project_title_pointer_color));
//        textUnckeck.setTextColor(getResources().getColor(R.color.my_project_title_text_color));
//        titleUncheck.setTextColor(getResources().getColor(R.color.my_project_title_text_color));
//
//    }
//
//    private void ifIsLohoDesiner(String desiner_id, String hs_uid) {
//
//        MPServerHttpManager.getInstance().ifIsLohoDesiner(desiner_id, hs_uid, new OkJsonRequest.OKResponseCallback() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//            }
//
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//                try {
//                    JSONObject jsonObject1 = jsonObject.getJSONObject("designer");
//                    int is_loho = jsonObject1.getInt("is_loho");
//                    //2：乐屋设计师添加扫描二维码功能（其他几种未判断）
//                    if (2 == is_loho) {
//                        setImageForNavButton(ButtonType.SECONDARY, com.autodesk.shejijia.shared.R.drawable.scan);
//                        setVisibilityForNavButton(ButtonType.SECONDARY, true);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//
//
//    }
//
//    @Override
//    protected void secondaryNavButtonClicked(View view) {
//        super.secondaryNavButtonClicked(view);
//
//        Intent intent = new Intent(MPConsumerHomeActivity2.this, CaptureQrActivity.class);
//        startActivityForResult(intent, CHAT);
//
//    }
//
////    @Override
////    public void onCheckedChanged(RadioGroup group, int checkedId) {
////
////        if (checkedId == getDesignerMainRadioBtnId())
////            showFragment(getDesignerMainRadioBtnId());
////
////        super.onCheckedChanged(group, checkedId);
////    }
//
//    protected int getDesignerMainRadioBtnId() {
//        return R.id.designer_main_radio_btn;
//    }
//
//    protected int getIMButtonId() {
//        return R.id.designer_session_radio_btn;
//    }
//
//    protected int getRadioGroupId() {
//        return R.id.designer_main_radio_group;
//    }
//
//
//    protected int getMainContentId() {
//        return R.id.mp_consumer_home_activity_fl;
//    }
//
//
//    /**
//     * 网络获取数据并且更新
//     */
//    private void updateViewFromData() {
//
//        user_avatar.setVisibility(View.VISIBLE);
//        if (mConsumerEssentialInfoEntity != null && !TextUtils.isEmpty(mConsumerEssentialInfoEntity.getAvatar()) && MPConsumerHomeActivity2.this != null) {
//            mNickNameConsumer = mConsumerEssentialInfoEntity.getNick_name();
//            ImageUtils.displayAvatarImage(mConsumerEssentialInfoEntity.getAvatar(), user_avatar);
//        }
//
//
//        if (designerInfoDetails != null && !TextUtils.isEmpty(designerInfoDetails.getAvatar()) && MPConsumerHomeActivity2.this != null) {
//            ImageUtils.displayAvatarImage(designerInfoDetails.getAvatar(), user_avatar);
//        }
//
//    }
//
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
//                if (MPConsumerHomeActivity2.this != null) {
//                    new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, MPConsumerHomeActivity2.this,
//                            AlertView.Style.Alert, null).show();
//                }
//            }
//        });
//    }
//
//    /**
//     * 获取全流程节点信息；
//     * 登陆一次获取一次
//     */
//
//    public void getWkFlowStatePointInformation() {
//
//
//        MPServerHttpManager.getInstance().getWkFlowStatePointInformation(new OkJsonRequest.OKResponseCallback() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//
//            }
//
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//
//                String jsonString = GsonUtil.jsonToString(jsonObject);
//                Gson gson = new Gson();
//                WkFlowStateContainsBean wkFlowStateContainsBean = gson.fromJson(jsonString, WkFlowStateContainsBean.class);
//
//                Map<String, WkFlowStateBean> mapWkFlowStateBean = wkFlowStateContainsBean.getNodes_message();
//                WkFlowStateMap.map = mapWkFlowStateBean;
//
//            }
//        });
//    }
//
//
//    /**
//     * 设计师个人信息
//     *
//     * @param designer_id
//     * @param hs_uid
//     */
//    public void getDesignerInfoData(String designer_id, String hs_uid) {
//        MPServerHttpManager.getInstance().getDesignerInfoData(designer_id, hs_uid, new OkJsonRequest.OKResponseCallback() {
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//                String jsonString = GsonUtil.jsonToString(jsonObject);
//                designerInfoDetails = GsonUtil.jsonToBean(jsonString, DesignerInfoDetails.class);
//                updateViewFromData();
//            }
//
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                MPNetworkUtils.logError(TAG, volleyError);
//                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, MPConsumerHomeActivity2.this,
//                        AlertView.Style.Alert, null).show();
//            }
//        });
//    }
//
//    //设置头像
//    private void setConsumerOrDesignerPicture() {
//        MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
//        if (mMemberEntity != null && Constant.UerInfoKey.CONSUMER_TYPE.equals(mMemberEntity.getMember_type())) {
//
//            getConsumerInfoData(mMemberEntity.getAcs_member_id());
//
//            return;
//
//        }
//
//        if (mMemberEntity != null && Constant.UerInfoKey.DESIGNER_TYPE.equals(mMemberEntity.getMember_type())) {
//
//            getDesignerInfoData(mMemberEntity.getAcs_member_id(), mMemberEntity.getHs_uid());
//
//            return;
//
//        }
//
//        setImageForNavCircleView(ButtonType.LEFTCIRCLE, R.drawable.icon_default_avator);
//
//    }
//
//
//    //判断是否聊过天，跳转到之前聊天室或新聊天室
//    private void jumpToChatRoom(String scanResult) {
//
//        if (scanResult.contains(Constant.ConsumerDecorationFragment.hs_uid)
//                && scanResult.contains(Constant.DesignerCenterBundleKey.MEMBER)) {
//
//            IMQrEntity consumerQrEntity = GsonUtil.jsonToBean(scanResult, IMQrEntity.class);
//            if (null != consumerQrEntity && !TextUtils.isEmpty(consumerQrEntity.getName())) {
//
//                final String hs_uid = consumerQrEntity.getHs_uid();
//                final String member_id = consumerQrEntity.getMember_id();
//                final String receiver_name = consumerQrEntity.getName();
//                final String designer_id = AdskApplication.getInstance().getMemberEntity().getAcs_member_id();
//                final String mMemberType = AdskApplication.getInstance().getMemberEntity().getMember_type();
//                final String recipient_ids = member_id + "," + designer_id + "," + ApiManager.getAdmin_User_Id(ApiManager.RUNNING_DEVELOPMENT);
//
//                MPChatHttpManager.getInstance().retrieveMultipleMemberThreads(recipient_ids, 0, 10, new OkStringRequest.OKResponseCallback() {
//                    @Override
//                    public void onErrorResponse(VolleyError volleyError) {
//                        MPNetworkUtils.logError(TAG, volleyError);
//                    }
//
//                    @Override
//                    public void onResponse(String s) {
//                        MPChatThreads mpChatThreads = MPChatThreads.fromJSONString(s);
//
//                        Intent intent = new Intent(MPConsumerHomeActivity2.this, ChatRoomActivity.class);
//                        intent.putExtra(ChatRoomActivity.RECIEVER_USER_ID, member_id);
//                        intent.putExtra(ChatRoomActivity.RECIEVER_USER_NAME, receiver_name);
//                        intent.putExtra(ChatRoomActivity.ACS_MEMBER_ID, designer_id);
//                        intent.putExtra(ChatRoomActivity.MEMBER_TYPE, mMemberType);
//
//                        if (mpChatThreads != null && mpChatThreads.threads.size() > 0) {
//                            MPChatThread mpChatThread = mpChatThreads.threads.get(0);
//                            int assetId = MPChatUtility.getAssetIdFromThread(mpChatThread);
//                            intent.putExtra(ChatRoomActivity.THREAD_ID, mpChatThread.thread_id);
//                            intent.putExtra(ChatRoomActivity.ASSET_ID, assetId + "");
//                            intent.putExtra(ChatRoomActivity.MEDIA_TYPE, UrlMessagesContants.mediaIdProject);
//                        } else {
//                            intent.putExtra(ChatRoomActivity.RECIEVER_HS_UID, hs_uid);
//                            intent.putExtra(ChatRoomActivity.ASSET_ID, "");
//                        }
//
//                        startActivity(intent);
//                    }
//
//                });
//
//            }
//
//        } else {
//
//            new AlertView(UIUtils.getString(com.autodesk.shejijia.shared.R.string.tip)
//                    , UIUtils.getString(com.autodesk.shejijia.shared.R.string.unable_create_beishu_meal)
//                    , null, null, new String[]{UIUtils.getString(com.autodesk.shejijia.shared.R.string.sure)}
//                    , MPConsumerHomeActivity2.this
//                    , AlertView.Style.Alert, null).show();
//
//        }
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == Activity.RESULT_OK && data != null) {
//            switch (requestCode) {
//                case CHAT:
//
//                    Bundle bundle = data.getExtras();
//                    String scanResult = bundle.getString(Constant.QrResultKey.SCANNER_RESULT);
//                    if (null != scanResult) {
//                        jumpToChatRoom(scanResult);
//                    }
//
//                    break;
//            }
//        }
//
//    }
//
//    private void getMemberThreadUnreadCount() {
//        // TODO: Correct this if we figure out a better way to inform
//        // clients of login available on 2nd launch
//        MemberEntity entity = AdskApplication.getInstance().getMemberEntity();
//
//        if (entity == null) {
//            return;
//        } else {
//
//            MPChatHttpManager.getInstance().retrieveMemberUnreadMessageCount(entity.getAcs_member_id(),
//                    true, new OkStringRequest.OKResponseCallback() {
//                        @Override
//                        public void onErrorResponse(VolleyError volleyError) {
//                            MPNetworkUtils.logError(TAG, volleyError);
//                        }
//
//                        @Override
//                        public void onResponse(String s) {
//                            JSONObject jObj = null;
//                            int unread_message_count = 0;
//
//                            try {
//                                jObj = new JSONObject(s);
//                                unread_message_count = jObj.optInt("unread_message_count");
//                            } catch (JSONException e) {
//                                Log.e(TAG, "Exception while parsing unread count from JSON");
//                            }
//                            refreshUnreadCount(unread_message_count);
//                        }
//                    });
//        }
//    }
//
//    private final int CHAT = 0;
//    private static final int IS_BEI_SHU = 1;
//
//
//    //    private MyDecorationProjectFragment mConsumerPersonalCenterFragment;
//    private DecorationConsumerFragment mConsumerPersonalCenterFragment;
//
//    private static final String HOME_FRAGMENT_TAG = "HOME_FRAGMENT_TAG";
//    private static final String BID_FRAGMENT_TAG = "BID_FRAGMENT_TAG";
//    private static final String DESIGNER_PERSONAL_FRAGMENT_TAG = "DESIGNER_FRAGMENT_TAG";
//    private static final String CONSUMER_PERSONAL_FRAGMENT_TAG = "CONSUMER_FRAGMENT_TAG";
//
//    private ImageView user_avatar;
//    private TextView bidding;
//    private TextView design;
//    private TextView construction;
//    private View contain_layout;
//    private ChooseViewPointer chooseViewPointer;
//    private String mNickNameConsumer;
//    final float POINTER_START_NUMBER = 0F;
//    final float POINTER_START_END_NUMBER = 1 / 3F;
//    final float POINTER_MIDDLE_END_NUMBER = 2 / 3F;
//    final float POINTER_END_NUMBER = 1F;
//
//    private UserHomeFragment mUserHomeFragment;
//
//    private ConsumerEssentialInfoEntity mConsumerEssentialInfoEntity;
//    private WkFlowStateBean wkFlowStateBean;
//
//    private BidHallFragment mBidHallFragment;
//    private DesignerInfoDetails designerInfoDetails;
//    private MyDecorationProjectDesignerFragment mDesignerPersonalCenterFragment;
//
//
//    /**
//     * 点击底部button
//     * @param position
//     */
//    @Override
//    public void onTabSelect(int position) {
//        CustomProgress.cancelDialog();
//        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
//            //判断是消费者，还是设计师，，从而区分消费者和设计师
//        if (memberEntity != null && Constant.UerInfoKey.DESIGNER_TYPE.equals(memberEntity.getMember_type())) {
//            switch (position) {
//                    case 0://案例
//                        setVisibilityForNavButton(ButtonType.LEFTCIRCLE,true);
//                        setCaseLIbraryTitle();
//                        setVisibilityForNavButton(ButtonType.middlecontain, false);
//                        setVisibilityForNavButton(ButtonType.middle, true);
//                        addFragmentToList(mFragments_designer, UserHomeFragment.getInstance(), position);
//                        break;
//                    case 1://设计师
//                        hideCaseLIbraryTitle();
//                        addFragmentToList(mFragments_designer, UserHomeFragment.getInstance(), position);
//                        break;
//                    case 2://应标大厅
//                        hideCaseLIbraryTitle();
//                        doLogin();
//                        if (isLogin())
//                            setVisibilityForNavButton(ButtonType.middlecontain, false);
//                            setVisibilityForNavButton(ButtonType.middle, true);
//                            setImageForNavButton(ButtonType.RIGHT, R.drawable.filtratenew);
//                            setTitleForNavbar(UIUtils.getString(R.string.tab_hall));
//                            addFragmentToList(mFragments_designer, BidHallFragment.getInstance(), position);
//                        break;
//                    case 3://聊天
//                        hideCaseLIbraryTitle();
//                        doLogin();
//                        if (isLogin())
//                            setTitleForNavbar(UIUtils.getString(R.string.mychat));
//                            chatFragment(position);
//                        break;
//                    case 4://我的订单
//                        doLogin();
//                        hideCaseLIbraryTitle();
//                        if (isLogin())
//                            setChooseViewWidth(true);
//                            setVisibilityForNavButton(ButtonType.middle, false);
//                           contain.setVisibility(View.VISIBLE);
//                           if (contain.getChildCount() == 0) {
//
//                             contain.addView(contain_layout);
//                           }
//                            addFragmentToList(mFragments_designer, MyDecorationProjectDesignerFragment.getInstance(), position);
//                        break;
//                    default:
//                        break;
//                }
//                setFragments(position, mFragments_designer);
//            } else {
//                switch (position) {
//                    case 0://案例
//                        hideCaseLIbraryTitle();
//                        setVisibilityForNavButton(ButtonType.LEFTCIRCLE,true);
//                        setCaseLIbraryTitle();
//                        setVisibilityForNavButton(ButtonType.middlecontain, false);
//                        setVisibilityForNavButton(ButtonType.middle, true);
//                        addFragmentToList(mFragments_consumer, UserHomeFragment.getInstance(), position);
//                        break;
//                    case 1://设计师
//                        hideCaseLIbraryTitle();
//                        addFragmentToList(mFragments_consumer, UserHomeFragment.getInstance(), position);
//                        break;
//                    case 2://我要装修
//                        doLogin();
//                        hideCaseLIbraryTitle();
//                        if (isLogin()){
//                            startActivity(new Intent(this,SixProductsActivity.class));
//                        }
////                            addFragmentToList(mFragments_consumer, BidHallFragment.getInstance(), position);
//                        break;
//                    case 3://聊天
//                        doLogin();
//                        hideCaseLIbraryTitle();
//                        if (isLogin())
//                            setTitleForNavbar(UIUtils.getString(R.string.mychat));
//                            chatFragment(position);
//                        break;
//                    case 4://我的项目
//                        doLogin();
//                        hideCaseLIbraryTitle();
//                        if (isLogin())
//                            setChooseViewWidth(true);
//                            setTitleForNavbar(UIUtils.getString(R.string.consumer_decoration));
//                            addFragmentToList(mFragments_consumer, DecorationConsumerFragment.getInstance(), position);
//                        break;
//                    default:
//                        break;
//                }
//                setFragments(position, mFragments_consumer);
//            }
//        }
//
//    @Override
//    public void onTabReselect(int position) {
//
//    }
//    //设置指针控件宽度
//    public void setChooseViewWidth(final boolean just) {
//
//        ViewTreeObserver vto = contain.getViewTreeObserver();
//        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//
//            @Override
//            public void onGlobalLayout() {
//                btWidth = contain.getMeasuredWidth();
//                btHeight = contain.getMeasuredHeight();
//                if (btWidth != 0) {
//
//                    chooseViewPointer.setInitChooseVoewPoint(btWidth, just);
//                }
//
//            }
//
//        });
//    }
//
//    private int btWidth;
//    private int btHeight;
//    private LinearLayout contain;

}
