package com.autodesk.shejijia.consumer.home.decorationdesigners.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.adapter.SeekDesignerDetailAdapter;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.AppraiseDesignBeen;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.Case3DBeen;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.DesignerDetailHomeBean;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.DesignerInfoBean;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.FollowingDesignerBean;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.SeekDesignerDetailBean;
import com.autodesk.shejijia.consumer.home.decorationdesigners.fragment.DesignerAppraiseFragment;
import com.autodesk.shejijia.consumer.home.decorationdesigners.fragment.DesignerPerson3DMasterPageFragment;
import com.autodesk.shejijia.consumer.home.decorationdesigners.fragment.DesignerPersonMasterPageFragment;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.consumer.activity.MeasureFormActivity;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.shared.components.common.appglobal.ApiManager;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.shared.components.common.tools.chatroom.JumpBean;
import com.autodesk.shejijia.shared.components.common.tools.chatroom.JumpToChatRoom;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.consumer.uielements.MyToast;
import com.autodesk.shejijia.consumer.uielements.SingleClickUtils;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.consumer.uielements.chooseview.ChooseViewPointer;
import com.autodesk.shejijia.consumer.uielements.scrollview.MyScrollView;
import com.autodesk.shejijia.consumer.uielements.scrollview.MyScrollViewLayout;
import com.autodesk.shejijia.consumer.uielements.scrollview.MyScrollViewListener;
import com.autodesk.shejijia.shared.components.common.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.LoginUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.StringUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThreads;
import com.autodesk.shejijia.shared.components.im.manager.MPChatHttpManager;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 2016/2/27 0029 17:32 .
 * @file SeekDesignerDetailActivity  .
 * @brief 查看设计师详情页面 .
 */
public class SeekDesignerDetailActivity extends NavigationBarActivity implements View.OnClickListener, MyScrollViewListener, MyScrollViewLayout.OnRefreshListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_seek_designer_detail;
    }

    @Override
    protected void initView() {
        super.initView();

        mLlChatMeasure = (LinearLayout) findViewById(R.id.ll_seek_designer_detail_chat_measure);
        mHeadIcon = (PolygonImageView) findViewById(R.id.piv_seek_designer_head);
        mIvCertification = (ImageView) findViewById(R.id.img_seek_designer_detail_certification);
        mTvYeas = (TextView) findViewById(R.id.tv_seek_designer_detail_yeas);
        mTvStyle = (TextView) findViewById(R.id.tv_seek_designer_detail_style);
        mTvDesignFee = (TextView) findViewById(R.id.tv_seek_designer_design_fee);
        mTvMeasureFee = (TextView) findViewById(R.id.tv_seek_designer_detail_measure_fee);
        mBtnChat = (Button) findViewById(R.id.btn_seek_designer_detail_chat);
        mTvFollowedNum = (TextView) findViewById(R.id.tv_followed_num);
        case_2d_btn = (TextView) findViewById(R.id.case_2d_btn);
        case_3d_btn = (TextView) findViewById(R.id.case_3d_btn);
        consumer_appraise = (TextView) findViewById(R.id.consumer_appraise);
        case_2d_btn_replace_top = (TextView) findViewById(R.id.case_2d_btn_replace);
        case_3d_btn_replace_top = (TextView) findViewById(R.id.case_3d_btn_replace_top);
        consumer_appraise_replace_top = (TextView) findViewById(R.id.consumer_appraise_replace_top);
        chooseViewPointer = (ChooseViewPointer) findViewById(R.id.choose_point);
        choose_point_replace = (ChooseViewPointer) findViewById(R.id.choose_point_replace);
        myScrollView = (MyScrollView) findViewById(R.id.myScrollView);
        mMyScrollViewLayout = (MyScrollViewLayout) findViewById(R.id.myScrollViewLayout);


        piv_seek_designer_head_linearlayout = (LinearLayout) findViewById(R.id.piv_seek_designer_head_linearlayout);
        ll_case_choose_contain_replace = (LinearLayout) findViewById(R.id.ll_case_choose_contain_replace);
        ll_case_choose_contain = (LinearLayout) findViewById(R.id.ll_case_choose_contain);
        mTvYeas = (TextView) findViewById(R.id.tv_seek_designer_detail_yeas);
        mTvStyle = (TextView) findViewById(R.id.tv_seek_designer_detail_style);
        mTvDesignFee = (TextView) findViewById(R.id.tv_seek_designer_design_fee);
        mTvMeasureFee = (TextView) findViewById(R.id.tv_seek_designer_detail_measure_fee);
        mBtnChat = (Button) findViewById(R.id.btn_seek_designer_detail_chat);
        mBtnMeasure = (Button) findViewById(R.id.btn_seek_designer_detail_optional_measure);

        chooseViewPointer = (ChooseViewPointer) findViewById(R.id.choose_point);
        default_ll_bg = (LinearLayout) findViewById(R.id.default_ll_bg);
        empty_Text = (TextView) findViewById(R.id.empty_Text);


        width = getWindowWidth(1);
        height = getWindowWidth(0);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        Bundle extras = getIntent().getExtras();
        mDesignerId = (String) extras.get(Constant.ConsumerDecorationFragment.designer_id);
        mHsUid = (String) extras.get(Constant.ConsumerDecorationFragment.hs_uid); // consume_name
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        showOrHideChatMeasure();

//        setTitleForNavButton(ButtonType.RIGHT, UIUtils.getString(R.string.attention_sure));
        setTextColorForRightNavButton(UIUtils.getColor(R.color.search_text_color));


        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what) {

                    case 0:

                        justControlNumberCount();

                        break;

                }
            }
        };
        CustomProgress.show(this, "", false, null);

        getSeekDesignerDetailHomeData(mDesignerId, mHsUid);
        getSeekDesignerDetailData(SeekDesignerDetailActivity.this.mDesignerId, 0, SeekDesignerDetailActivity.this.LIMIT, 0);

        setDefaultFragment();

        chooseViewPointer.setCase2dBtn(width);
        case_2d_btn.setTextColor(getResources().getColor(R.color.my_project_title_pointer_color));

        choose_point_replace.setCase2dBtn(width);
        case_2d_btn_replace_top.setTextColor(getResources().getColor(R.color.my_project_title_pointer_color));

        if (getAppraiseCount) {
            getAppraiseData(mDesignerId, LIMIT, OFFSET);//获取评价数据

        }
        setChooseViewWidth();
        appraiseCountSet();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBtnMeasure.setOnClickListener(this);
        mBtnChat.setOnClickListener(this);
        case_2d_btn.setOnClickListener(this);
        case_3d_btn.setOnClickListener(this);
        consumer_appraise.setOnClickListener(this);
        case_2d_btn_replace_top.setOnClickListener(this);
        case_3d_btn_replace_top.setOnClickListener(this);
        consumer_appraise_replace_top.setOnClickListener(this);
        mMyScrollViewLayout.setOnRefreshListener(this);
        myScrollView.setMyScrollViewListener(this);

        case_2d_btn.setClickable(false);
        case_2d_btn_replace_top.setClickable(false);
    }

    //设置控件宽度
    public void setChooseViewWidth() {

        ViewTreeObserver vto = piv_seek_designer_head_linearlayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                btHeight = piv_seek_designer_head_linearlayout.getMeasuredHeight();
            }
        });
    }




    @Override
    public void onClick(View view) {
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        transaction = fragmentManager.beginTransaction();
        switch (view.getId()) {

            case R.id.btn_seek_designer_detail_optional_measure:
                /// 选择该设计师去量房,如果用户还没登陆 会进入注册登陆界面.
                if (SingleClickUtils.isFastDoubleClick()) {


                } else {

                    if (memberEntity != null) {

                        if (null != seekDesignerDetailHomeBean) {
                            member_id = memberEntity.getAcs_member_id();
                            final String designer_id = seekDesignerDetailHomeBean.getDesigner().getAcs_member_id();
                            final String recipient_ids = member_id + "," + designer_id + "," + ApiManager.getAdmin_User_Id();
                            MPChatHttpManager.getInstance().retrieveMultipleMemberThreads(recipient_ids, 0, 10, new OkStringRequest.OKResponseCallback() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    MPNetworkUtils.logError(TAG, volleyError);
                                }

                                @Override
                                public void onResponse(String s) {

                                    MPChatThreads mpChatThreads = MPChatThreads.fromJSONString(s);
                                    final Intent intent = new Intent(SeekDesignerDetailActivity.this, MeasureFormActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString(Constant.SeekDesignerDetailKey.MEASURE_FREE, mMeasureFee);
                                    bundle.putString(Constant.SeekDesignerDetailKey.DESIGNER_ID, mDesignerId);
                                    bundle.putString(Constant.SeekDesignerDetailKey.SEEK_TYPE, Constant.SeekDesignerDetailKey.SEEK_DESIGNER_DETAIL);
                                    bundle.putString(Constant.SeekDesignerDetailKey.HS_UID, mHsUid);
                                    bundle.putInt(Constant.SeekDesignerDetailKey.FLOW_STATE, 0);
                                    String styleAll = seekDesignerDetailHomeBean.getDesigner().getStyle_names();
                                    bundle.putString(Constant.SeekDesignerDetailKey.DESIGNER_STYLE_ALL, styleAll);

                                    if (mpChatThreads != null && mpChatThreads.threads.size() > 0) {

                                        bundle.putString(Constant.ProjectMaterialKey.IM_TO_FLOW_THREAD_ID, mpChatThreads.threads.get(0).thread_id);

                                    } else {
                                        bundle.putString(Constant.ProjectMaterialKey.IM_TO_FLOW_THREAD_ID, "");
                                    }
                                    intent.putExtras(bundle);
                                    SeekDesignerDetailActivity.this.startActivity(intent);
                                }
                            });
                        } else {
                            new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, SeekDesignerDetailActivity.this, AlertView.Style.Alert, null).show();
                        }
                    } else {
                        LoginUtils.doLogin(this);
                    }

                }
                break;

            case R.id.btn_seek_designer_detail_chat:
                /**
                 * 聊天,如没有登陆 也会跳入注册登陆页面
                 */
                if (memberEntity != null) {

                    if (null != seekDesignerDetailHomeBean) {
                        member_id = memberEntity.getAcs_member_id();
                        final String designer_id = seekDesignerDetailHomeBean.getDesigner().getAcs_member_id();
                        final String hs_uid = seekDesignerDetailHomeBean.getHs_uid();
                        final String receiver_name = seekDesignerDetailHomeBean.getNick_name();

                        JumpBean jumpBean = new JumpBean();
                        jumpBean.setAcs_member_id(member_id);
                        jumpBean.setMember_type(mMemberType);
                        jumpBean.setReciever_hs_uid(hs_uid);
                        jumpBean.setReciever_user_id(designer_id);
                        jumpBean.setReciever_user_name(receiver_name);
                        JumpToChatRoom.getChatRoom(SeekDesignerDetailActivity.this, jumpBean);

                    } else {
                        new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, SeekDesignerDetailActivity.this, AlertView.Style.Alert, null).show();
                    }
                } else {
                    LoginUtils.doLogin(this);
                }
                break;

            case R.id.case_2d_btn:
                controlNumber = 1;
                chooseViewPointer.setCase2dBtn(width);
                choose_point_replace.setCase2dBtn(width);

                if (mDesignerPersonMasterPageFragment == null) {
                    mDesignerPersonMasterPageFragment = new DesignerPersonMasterPageFragment();
                } else {

                    justRefreshAndLoadMore();//判断刷新加载
                }

                switchFragment(shareFragment, mDesignerPersonMasterPageFragment);
                //设置颜色
                setTextColor(controlNumber);
                myScrollView.smoothScrollTo(0, scrollLastMoveDistance);

                if (default_2d_picture_count == 1){

                    default_ll_bg.setVisibility(View.GONE);
                }else {
                    default_ll_bg.setVisibility(View.VISIBLE);
                }

                setEmptyText();//判断是评价还是其他空白页面



                break;

            case R.id.case_3d_btn:

                controlNumber = 2;
                case_2d_btn.setClickable(true);
                case_2d_btn_replace_top.setClickable(true);
                chooseViewPointer.setCase3dBtn(width);
                choose_point_replace.setCase3dBtn(width);

                if (mDesignerPerson3DMasterPageFragment == null) {
                    CustomProgress.show(this, "", false, null);
                    mDesignerPerson3DMasterPageFragment = new DesignerPerson3DMasterPageFragment();
                    getSeekDesign3DDetailData(SeekDesignerDetailActivity.this.mDesignerId, 0, 10, "data", "desc");
                } else {

                    justRefreshAndLoadMore();//判断刷新加载
                }
                switchFragment(shareFragment, mDesignerPerson3DMasterPageFragment);
                //设置颜色
                setTextColor(controlNumber);
                myScrollView.smoothScrollTo(0, scrollLastMoveDistance);

                if (default_3d_picture_count == 1){

                    default_ll_bg.setVisibility(View.GONE);
                }else {
                    default_ll_bg.setVisibility(View.VISIBLE);
                }

                setEmptyText();//判断是评价还是其他空白页面
                break;

            case R.id.consumer_appraise:
                controlNumber = 3;
                case_2d_btn.setClickable(true);
                case_2d_btn_replace_top.setClickable(true);
                chooseViewPointer.setConsumerAppraise(width);
                choose_point_replace.setConsumerAppraise(width);
                getAppraiseCount = false;

                if (mDesignerAppraiseFragment == null) {
                    CustomProgress.show(this, "", false, null);
                    mDesignerAppraiseFragment = new DesignerAppraiseFragment();
                    getAppraiseData(mDesignerId, LIMIT, OFFSET);//获取评价数据

                } else {

                    justRefreshAndLoadMore();//判断刷新加载
                }
                switchFragment(shareFragment, mDesignerAppraiseFragment);
                setTextColor(controlNumber);

                myScrollView.smoothScrollTo(0, scrollLastMoveDistance);//避免点击该按钮时，页面自动向上滑动
                setEmptyText();//判断是评价还是其他空白页面


                break;

            case R.id.case_2d_btn_replace:

                controlNumber = 1;
                chooseViewPointer.setCase2dBtn(width);
                choose_point_replace.setCase2dBtn(width);
                if (mDesignerPersonMasterPageFragment == null) {
                    CustomProgress.show(this, "", false, null);
                    mDesignerPersonMasterPageFragment = new DesignerPersonMasterPageFragment();
                }
                switchFragment(shareFragment, mDesignerPersonMasterPageFragment);

                setTextColor(controlNumber);
                setEmptyText();//判断是评价还是其他空白页面
                break;

            case R.id.case_3d_btn_replace_top:

                controlNumber = 2;
                case_2d_btn.setClickable(true);
                case_2d_btn_replace_top.setClickable(true);

                chooseViewPointer.setCase3dBtn(width);
                choose_point_replace.setCase3dBtn(width);
                if (mDesignerPerson3DMasterPageFragment == null) {
                    CustomProgress.show(this, "", false, null);
                    getSeekDesign3DDetailData(SeekDesignerDetailActivity.this.mDesignerId, 0, 10, "data", "desc");
                    mDesignerPerson3DMasterPageFragment = new DesignerPerson3DMasterPageFragment();
                }
                switchFragment(shareFragment, mDesignerPerson3DMasterPageFragment);
                //设置颜色
                setTextColor(controlNumber);
                setEmptyText();//判断是评价还是其他空白页面

                break;
            case R.id.consumer_appraise_replace_top:
                controlNumber = 3;
                case_2d_btn.setClickable(true);
                case_2d_btn_replace_top.setClickable(true);
                chooseViewPointer.setConsumerAppraise(width);
                choose_point_replace.setConsumerAppraise(width);
                getAppraiseCount = false;
                if (mDesignerAppraiseFragment == null) {
                    CustomProgress.show(this, "", false, null);
                    mDesignerAppraiseFragment = new DesignerAppraiseFragment();
                    getAppraiseData(mDesignerId, LIMIT, OFFSET);//获取评价数据
                }
                switchFragment(shareFragment, mDesignerAppraiseFragment);
                setTextColor(controlNumber);
                setEmptyText();//判断是评价还是其他空白页面
                break;


        }
    }

    private void setTextColor(int number) {

        if (number == 1) {

            case_2d_btn.setTextColor(getResources().getColor(R.color.my_project_title_pointer_color));
            case_2d_btn_replace_top.setTextColor(getResources().getColor(R.color.my_project_title_pointer_color));
            consumer_appraise_replace_top.setTextColor(getResources().getColor(R.color.bg_00));
            consumer_appraise.setTextColor(getResources().getColor(R.color.bg_00));
            case_3d_btn_replace_top.setTextColor(getResources().getColor(R.color.bg_00));
            case_3d_btn.setTextColor(getResources().getColor(R.color.bg_00));
        } else if (number == 2) {

            case_2d_btn_replace_top.setTextColor(getResources().getColor(R.color.bg_00));
            case_2d_btn.setTextColor(getResources().getColor(R.color.bg_00));
            case_3d_btn_replace_top.setTextColor(getResources().getColor(R.color.my_project_title_pointer_color));
            case_3d_btn.setTextColor(getResources().getColor(R.color.my_project_title_pointer_color));
            consumer_appraise_replace_top.setTextColor(getResources().getColor(R.color.bg_00));
            consumer_appraise.setTextColor(getResources().getColor(R.color.bg_00));
        } else {

            case_2d_btn_replace_top.setTextColor(getResources().getColor(R.color.bg_00));
            case_2d_btn.setTextColor(getResources().getColor(R.color.bg_00));
            case_3d_btn_replace_top.setTextColor(getResources().getColor(R.color.bg_00));
            case_3d_btn.setTextColor(getResources().getColor(R.color.bg_00));
            consumer_appraise_replace_top.setTextColor(getResources().getColor(R.color.my_project_title_pointer_color));
            consumer_appraise.setTextColor(getResources().getColor(R.color.my_project_title_pointer_color));

        }
    }

    /**
     * @brief 默认北舒套餐页面 .
     */
    public void setDefaultFragment() {
        mDesignerPersonMasterPageFragment = new DesignerPersonMasterPageFragment();
        shareFragment = mDesignerPersonMasterPageFragment;
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, mDesignerPersonMasterPageFragment)
                .commit();
    }

    /**
     * @param from
     * @param to
     * @brief 切换fragment .
     */
    public void switchFragment(Fragment from, Fragment to) {
        if (!to.isAdded()) {    // judge the fragment
            transaction.hide(from).add(R.id.container, to).commit(); // hide current fragment，add next Fragment to Activity
        } else {
            transaction.hide(from).show(to).commit(); // hide current fragment，show next fragmetn
        }

        shareFragment = to;

    }


    //获取屏幕宽度
    public int getWindowWidth(int choose) {

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int height = metric.heightPixels;   // 屏幕高度（像素）
        if (choose == 1) {

            return width;
        } else {
            return height;
        }
    }

    /**
     * 获取设计师的从业信息，及费用
     *
     * @param designer_id 设计师的id
     * @param hsUid
     */
    public void getSeekDesignerDetailHomeData(String designer_id, String hsUid) {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                if (null != jsonObject) {
                    String info = GsonUtil.jsonToString(jsonObject);
                    seekDesignerDetailHomeBean = GsonUtil.jsonToBean(info, DesignerDetailHomeBean.class);
                    updateViewFromDesignerDetailData(seekDesignerDetailHomeBean);
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CustomProgress.cancelDialog();
                MPNetworkUtils.logError(TAG, volleyError);
                ApiStatusUtil.getInstance().apiStatuError(volleyError, SeekDesignerDetailActivity.this);
            }
        };
        MPServerHttpManager.getInstance().getSeekDesignerDetailHomeData(designer_id, hsUid, okResponseCallback);
    }

    /**
     * 获取设计师的信息,并刷新
     */
    public void getSeekDesignerDetailData(String designer_id, int offset, int limit,
                                          final int state) {

        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
//                getSeekDesignerDetailHomeData(mDesignerId, mHsUid);
                if (mDesignerPersonMasterPageFragment != null) {

                    mDesignerPersonMasterPageFragment.setHandler(handler);
                }
                String info = GsonUtil.jsonToString(jsonObject);
                mSeekDesignerDetailBean = GsonUtil.jsonToBean(info, SeekDesignerDetailBean.class);
                if (mSeekDesignerDetailBean.getCases().size() > 0) {

                    if (isFirstIn2D) {
                        default_ll_bg.setVisibility(View.GONE);
                        default_2d_picture_count = 1;
                    } else {
                        default_ll_bg.setVisibility(View.VISIBLE);
                    }




                    if (isRefreshOrLoad2D) {
                        mDesignerPersonMasterPageFragment.getMore2DCaseData(mSeekDesignerDetailBean, 0);//刷新

                        mMyScrollViewLayout.refreshFinish(mMyScrollViewLayout.SUCCEED);
                    } else {
                        mDesignerPersonMasterPageFragment.getMore2DCaseData(mSeekDesignerDetailBean, 1);//加载

                        mMyScrollViewLayout.loadmoreFinish(mMyScrollViewLayout.SUCCEED);
                        myScrollView.setIsLoad(false);
                    }


                } else {
                    //没有更多案例时
                    mMyScrollViewLayout.refreshFinish(mMyScrollViewLayout.SUCCEED);
//                    Toast.makeText(SeekDesignerDetailActivity.this, "已经没有更多案例了", Toast.LENGTH_SHORT).show();
                    mMyScrollViewLayout.loadmoreFinish(mMyScrollViewLayout.SUCCEED);
                }
                CustomProgress.cancelDialog();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);

                if (isRefreshOrLoad2D) {

                    mMyScrollViewLayout.refreshFinish(mMyScrollViewLayout.FAIL);
                } else {

                    mMyScrollViewLayout.loadmoreFinish(mMyScrollViewLayout.FAIL);
                }

                CustomProgress.cancelDialog();
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, SeekDesignerDetailActivity.this, AlertView.Style.Alert, null).show();
            }

        };
        MPServerHttpManager.getInstance().getSeekDesignerDetailData(designer_id, offset, limit, okResponseCallback);
    }


    /**
     * 获取3d案例数据
     */
    public void getSeekDesign3DDetailData(String designer_id, int offset, int limit,
                                          String date, String desc) {

        MPServerHttpManager.getInstance().get3DCaseData(Integer.parseInt(designer_id), limit, offset, date, desc, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                if (isRefreshOrLoad3D) {

                    mMyScrollViewLayout.refreshFinish(mMyScrollViewLayout.FAIL);
                } else {

                    mMyScrollViewLayout.loadmoreFinish(mMyScrollViewLayout.FAIL);
                }
                CustomProgress.cancelDialog();
                ApiStatusUtil.getInstance().apiStatuError(volleyError, SeekDesignerDetailActivity.this);
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, SeekDesignerDetailActivity.this, AlertView.Style.Alert, null).show();

            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();

                mDesignerPerson3DMasterPageFragment.setHandler(handler);
                String info = GsonUtil.jsonToString(jsonObject);
                case3DBeen = GsonUtil.jsonToBean(info, Case3DBeen.class);

                if (isFirstIn3D) {
                    isFirstIn3D = false;

                    if (case3DBeen.getCases().size() > 0) {

                        default_ll_bg.setVisibility(View.GONE);
                        default_3d_picture_count = 1;
                    } else {
                        default_ll_bg.setVisibility(View.VISIBLE);
                    }
                }

                if (case3DBeen.getCases().size() > 0) {

                    if (isRefreshOrLoad3D) {
                        mDesignerPerson3DMasterPageFragment.getMore3DCase(case3DBeen.getCases(), 0);//刷新
                        mMyScrollViewLayout.refreshFinish(mMyScrollViewLayout.SUCCEED);
                    } else {
                        mDesignerPerson3DMasterPageFragment.getMore3DCase(case3DBeen.getCases(), 1);//加载

                        mMyScrollViewLayout.loadmoreFinish(mMyScrollViewLayout.SUCCEED);
                    }

                } else {
                    //没有更多案例时
                    mMyScrollViewLayout.refreshFinish(mMyScrollViewLayout.SUCCEED);
//                    Toast.makeText(SeekDesignerDetailActivity.this, "已经没有更多案例了", Toast.LENGTH_SHORT).show();
                    mMyScrollViewLayout.loadmoreFinish(mMyScrollViewLayout.SUCCEED);
                }


            }
        });
    }

    /**
     * 更新设计师的从业信息，及费用
     *
     * @param seekDesignerDetailHomeBean 　设计师信息实体类
     */
    private void updateViewFromDesignerDetailData(DesignerDetailHomeBean seekDesignerDetailHomeBean) {
        if (null != seekDesignerDetailHomeBean) {
            if (seekDesignerDetailHomeBean.getAvatar() == null) {
                mHeadIcon.setImageResource(R.drawable.icon_default_avator);
            } else {
                ImageUtils.displayAvatarImage(seekDesignerDetailHomeBean.getAvatar(), mHeadIcon);
            }

            DesignerInfoBean designer;
            designer = seekDesignerDetailHomeBean.getDesigner();
            if (null != designer.getIs_real_name() + "" && designer.getIs_real_name() == 2) {
                mIvCertification.setVisibility(View.VISIBLE);
            } else {
                mIvCertification.setVisibility(View.GONE);
            }

            /**
             * 如果当前没有acs_member_id,就是没有登录
             */
            if (!TextUtils.isEmpty(mSelfAcsMemberId)) {
                /**
                 * 如果是当前设计师，就不显示关注按钮
                 */
                if (!mSelfAcsMemberId.equals(designer.getAcs_member_id())) {
                    setRightTitle(seekDesignerDetailHomeBean.is_following);
                } else {
                    setVisibilityForNavButton(ButtonType.RIGHT, false);
                }
            } else {
                setRightTitle(false);
            }
            String follows = seekDesignerDetailHomeBean.getFollows();
            follows = TextUtils.isEmpty(follows) ? "0" : follows;

            mTvFollowedNum.setText(follows);
            mNickName = seekDesignerDetailHomeBean.getNick_name();
            mNickName = TextUtils.isEmpty(mNickName) ? "" : mNickName;
            setTitleForNavbar(mNickName);

            unFollowedAlertView();

            if (null == designer.getExperience() + "" || designer.getExperience() == 0) {
                mTvYeas.setText(UIUtils.getString(R.string.has_yet_to_fill_out));
            } else {
                mTvYeas.setText(designer.getExperience() + "年");
            }
            if (null != designer && null != designer.getStyle_names()) {

                String style = designer.getStyle_names();
                style = style.replaceAll(",", " ");
                style = style.replaceAll("，", " ");

                mTvStyle.setText(style);
            } else {
                mTvStyle.setText(UIUtils.getString(R.string.has_yet_to_fill_out));
            }
            if (null != designer && null != designer.getDesign_price_min() && null != designer.getDesign_price_max()) {
                mTvDesignFee.setText(designer.getDesign_price_min() + "-" + designer.getDesign_price_max() + "元/m²");
            } else {
                mTvDesignFee.setText(UIUtils.getString(R.string.has_yet_to_fill_out));
            }
            if (null != designer && null != designer.getMeasurement_price()) {
                mMeasureFee = designer.getMeasurement_price();
                if (mMeasureFee.equals(Constant.NumKey.ZERO)) {
                    mTvMeasureFee.setText(UIUtils.getString(R.string.has_yet_to_fill_out));
                } else {
                    mTvMeasureFee.setText(mMeasureFee + "元");
                }
            } else {
                mMeasureFee = UIUtils.getString(R.string.has_yet_to_fill_out);
                mTvMeasureFee.setText(UIUtils.getString(R.string.has_yet_to_fill_out));
            }
        } else {
            mHeadIcon.setImageResource(R.drawable.icon_default_avator);
            mTvStyle.setText(UIUtils.getString(R.string.has_yet_to_fill_out));
            mTvYeas.setText(UIUtils.getString(R.string.has_yet_to_fill_out));
            mTvDesignFee.setText(UIUtils.getString(R.string.has_yet_to_fill_out));
            mTvMeasureFee.setText(UIUtils.getString(R.string.has_yet_to_fill_out));
        }
    }

    /**
     * 判断是否显示在沟通按钮
     */
    private void showOrHideChatMeasure() {
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();

        if (null != memberEntity) {
            mSelfAcsMemberId = memberEntity.getAcs_member_id();
            mMemberType = memberEntity.getMember_type();
            if (mMemberType.equals((Constant.UerInfoKey.CONSUMER_TYPE))) {
                mLlChatMeasure.setVisibility(View.VISIBLE);
            } else {
                mLlChatMeasure.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 关注或者取消关注设计师
     *
     * @param followsType true 为关注，false 取消关注
     */
    private void followingOrUnFollowedDesigner(final boolean followsType) {
        CustomProgress.show(this, "", false, null);
        String followed_member_id = mDesignerId;
        String followed_member_uid = mHsUid;
        MPServerHttpManager.getInstance().followingOrUnFollowedDesigner(member_id, followed_member_id, followed_member_uid, followsType, new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                String followingOrUnFollowedDesignerString = GsonUtil.jsonToString(jsonObject);
                FollowingDesignerBean followingDesignerBean = GsonUtil.jsonToBean(followingOrUnFollowedDesignerString, FollowingDesignerBean.class);
                String follows = followingDesignerBean.follows;
                follows = StringUtils.isEmpty(follows) ? "0" : follows;
                mTvFollowedNum.setText(follows);

                setRightTitle(followsType);
                if (followsType) {
                    MyToast.show(SeekDesignerDetailActivity.this, UIUtils.getString(R.string.attention_success));
                    seekDesignerDetailHomeBean.is_following = true;
                } else {
                    seekDesignerDetailHomeBean.is_following = false;
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CustomProgress.cancelDialog();
                setRightTitle(true);
                MPNetworkUtils.logError(TAG, volleyError);
            }
        });
    }

    //监听scrollview的滚动状态
    @Override
    public void onScrollChange(MyScrollView scrollView, int x, int y, int oldx, int oldy) {

        if (btHeight != 0 && y > 500) {

        }
        if (y > btHeight) {
            ll_case_choose_contain_replace.setVisibility(View.VISIBLE);
        }
        if (y < btHeight) {

            ll_case_choose_contain_replace.setVisibility(View.GONE);
            isTitleTwoShow = false;
        }
        //判断是否可以刷新
        if (y == 0) {

            myScrollView.setIsRefresh(true);

        } else {

            myScrollView.setIsRefresh(false);

        }
        //判断是否可以加载更多
        int myScrollViewChildHeight = myScrollView.getChildAt(0).getMeasuredHeight();//myScrollView中子类的高度
        int myScrollViewHeight = myScrollView.getHeight();//myScrollView的高度

        int height = myScrollView.getHeight();
        int scrollViewMeasuredHeight = myScrollView.getChildAt(0).getMeasuredHeight();


//        Log.i(TAG, "YAOmyScrollView子类" + myScrollView.getChildAt(0).getMeasuredHeight());
//        Log.i(TAG, "YAOmyScrollView高度" + myScrollView.getHeight());
//        Log.i(TAG, "YAOmyScrollView滑动的距离" + y);
        if (scrollViewMeasuredHeight <= y + height) {

            myScrollView.setIsLoad(true);
        } else {

            myScrollView.setIsLoad(false);
        }


        //判断回到顶部
        if (myScrollViewChildHeight < myScrollViewHeight) {

            isScrollToTop = true;
        }

        if (isScrollToTop) {

            myScrollView.scrollTo(1, 1);
        }

        if (myScrollViewChildHeight > myScrollViewHeight) {
            isScrollToTop = false;
        }

        scrollLastMoveDistance = y;

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showOrHideChatMeasure();
        getSeekDesignerDetailHomeData(mDesignerId, mHsUid);
    }

    @Override
    protected void onResume() {


        super.onResume();

    }

    /**
     * [1]是否登录状态，未登录，先登录
     * [2]如果判断字段is_following为true，就是关注中，点击取消关注
     */
    @Override
    protected void rightNavButtonClicked(View view) {
        super.rightNavButtonClicked(view);
        if (TextUtils.isEmpty(mSelfAcsMemberId)) {
            LoginUtils.doLogin(this);
        } else {
            if (seekDesignerDetailHomeBean.is_following) {
                unFollowedAlertView.show();
            } else {
                followingOrUnFollowedDesigner(true);
            }
        }
    }

    /**
     * 设置title栏，关注的显示问题
     * true :关注状态-->对应取消关注
     * false : 取消关注-->对应关注
     */
    private void setRightTitle(boolean follows_type) {

        if (follows_type) {
            setTitleForNavButton(ButtonType.RIGHT, UIUtils.getString(R.string.attention_cancel));

        } else {
            setTitleForNavButton(ButtonType.RIGHT, UIUtils.getString(R.string.attention_sure));
        }
    }

    /**
     * 取消关注提示框
     */
    private void unFollowedAlertView() {
        unFollowedAlertView = new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.attention_tip_message_first) + mNickName + UIUtils.getString(R.string.attention_tip_message_last),
                UIUtils.getString(R.string.following_cancel), null,
                new String[]{UIUtils.getString(R.string.following_sure)},
                SeekDesignerDetailActivity.this,
                AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object object, int position) {
                if (position != AlertView.CANCELPOSITION) {
                    followingOrUnFollowedDesigner(false);
                }
            }
        }).setCancelable(true);
    }


    /**
     * 获取设计师评价的数据
     * 第一次进入要刷新页面
     */
    public void getAppraiseData(String designer_Id, int limit, int offset) {

        MPServerHttpManager.getInstance().getEstimateList(designer_Id, limit, offset, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                if (isRefreshOrLoadAppraise) {

                    mMyScrollViewLayout.refreshFinish(mMyScrollViewLayout.FAIL);
                } else {

                    mMyScrollViewLayout.loadmoreFinish(mMyScrollViewLayout.FAIL);
                }
                appraiseCountSet();//设置评价数量
                CustomProgress.cancelDialog();
                ApiStatusUtil.getInstance().apiStatuError(volleyError, SeekDesignerDetailActivity.this);
            }

            @Override
            public void onResponse(JSONObject jsonObject) {

                //评价页面
                String jsonString = GsonUtil.jsonToString(jsonObject);
                mAppraiseDesignBeen = GsonUtil.jsonToBean(jsonString, AppraiseDesignBeen.class);
                estimates = mAppraiseDesignBeen.getEstimates();
                if (mDesignerAppraiseFragment != null) {

                    mDesignerAppraiseFragment.setHandler(handler);
                }

                if (estimates != null) {
                    if (isRefreshOrLoadAppraise) {
                        if (getAppraiseCount) {

                            appraiseCount = appraiseCount + estimates.size();
                            appraiseCountSet();//设置评价数量
                        } else {

                            mDesignerAppraiseFragment.updateListView(estimates, seekDesignerDetailHomeBean);
                            mMyScrollViewLayout.refreshFinish(mMyScrollViewLayout.SUCCEED);
                            CustomProgress.cancelDialog();
                            getAppraiseCount = false;

                        }
                    } else {
                        mDesignerAppraiseFragment.loadMoreData(estimates);
                        appraiseCount = appraiseCount + estimates.size();
                        appraiseCountSet();//设置评价数量
                        mMyScrollViewLayout.loadmoreFinish(mMyScrollViewLayout.SUCCEED);
                    }


                } else {

//                    Toast.makeText(SeekDesignerDetailActivity.this, "没有更多数据了", Toast.LENGTH_SHORT).show();
                }

                if (appraiseCount == 0) {

                    default_ll_bg.setVisibility(View.VISIBLE);
                } else {

                    default_ll_bg.setVisibility(View.GONE);
                }
                mMyScrollViewLayout.loadmoreFinish(mMyScrollViewLayout.SUCCEED);

                CustomProgress.cancelDialog();


            }
        });


    }

    @Override
    public void onRefresh(MyScrollViewLayout pullToRefreshLayout) {

        CustomProgress.show(this, "", false, null);
        if (controlNumber == 1) {
            isRefreshOrLoad2D = true;

            getSeekDesignerDetailData(SeekDesignerDetailActivity.this.mDesignerId, 0, SeekDesignerDetailActivity.this.LIMIT, 0);
            OFFSETCOUNT = 10;
        } else if (controlNumber == 2) {
            isRefreshOrLoad3D = true;
            getSeekDesign3DDetailData(SeekDesignerDetailActivity.this.mDesignerId, 0, LIMIT, "data", "desc");
            OFFSETCOUNT = 10;
        } else {
            isRefreshOrLoadAppraise = true;

            getAppraiseData(mDesignerId, LIMIT, 0);//获取评价数据
            OFFSETCOUNT = 10;

        }
    }

    @Override
    public void onLoadMore(MyScrollViewLayout pullToRefreshLayout) {

        CustomProgress.show(this, "", false, null);
        if (controlNumber == 1) {
            isRefreshOrLoad2D = false;
            getSeekDesignerDetailData(SeekDesignerDetailActivity.this.mDesignerId, OFFSETCOUNT, SeekDesignerDetailActivity.this.LIMIT, 0);
            OFFSETCOUNT = OFFSETCOUNT + 10;
        } else if (controlNumber == 2) {
            isRefreshOrLoad3D = false;
            getSeekDesign3DDetailData(SeekDesignerDetailActivity.this.mDesignerId, OFFSETCOUNT, SeekDesignerDetailActivity.this.LIMIT, "data", "desc");
            OFFSETCOUNT = OFFSETCOUNT + 10;
        } else {
            isRefreshOrLoadAppraise = false;
            getAppraiseData(mDesignerId, LIMIT, OFFSETCOUNT);//获取评价数据
            OFFSETCOUNT = OFFSETCOUNT + 10;
        }
    }

    //评价数量
    public void appraiseCountSet() {

        consumer_appraise.setText("评价" + "(" + appraiseCount + ")");
        consumer_appraise_replace_top.setText("评价" + "(" + appraiseCount + ")");
    }

    //根据数值判断实在那个选项下
    public void justControlNumberCount() {

        if (controlNumber == 1) {
            justSubClassHeightCount();

        } else if (controlNumber == 2) {

            justSubClassHeightCount();
        } else {

            justSubClassHeightCount();
        }
    }

    //判断当前子类高度是否已经达到可以加载更多的底部
    public void justSubClassHeightCount() {

        int scrollViewMeasuredHeight = myScrollView.getChildAt(0).getMeasuredHeight();
        int height = myScrollView.getHeight();
        if (scrollViewMeasuredHeight <= scrollLastMoveDistance + height) {

            myScrollView.setIsLoad(true);
        } else {

            myScrollView.setIsLoad(false);
        }

        readSubClassHeightCount(scrollViewMeasuredHeight);

        //只走一次，避免默认加载数据时,出现少数据误差显示
        if (is2DFrist) {

            justRefreshAndLoadMore();
            is2DFrist = false;

        }

    }

    //判断当该页面的内容很少时，加载的计算会重复上一页面，所以需要记录
    public void readSubClassHeightCount(int heightSubClass) {

        if (controlNumber == 1) {

            TWO_D_CASE_COUNT = heightSubClass;

        } else if (controlNumber == 2) {

            THREE_D_CASE_COUNT = heightSubClass;
        } else {

            APPRAISE_COUNT = heightSubClass;
        }


        if (controlNumber == 1){//2Dcase
            if (is2DFrist) {

                justRefreshAndLoadMore();
                is2DFrist = false;

            }

        }else if (controlNumber == 2){


            if (is3DFrist) {

                justRefreshAndLoadMore();
                is3DFrist = false;

            }
        }else {

            if (isAppraiseFirst) {

                justRefreshAndLoadMore();
                isAppraiseFirst = false;

            }

        }


    }

    //获取高度
    public int getSubClassHeightCount() {
        int count = 0;
        if (controlNumber == 1) {

            count = TWO_D_CASE_COUNT;

        } else if (controlNumber == 2) {

            count = THREE_D_CASE_COUNT;
        } else {

            count = APPRAISE_COUNT;
        }

        return count;
    }

    //判断当数据很少时，判断是否可以刷新,加载
    public void justRefreshAndLoadMore() {

        if (getSubClassHeightCount() < myScrollView.getHeight()) {
            myScrollView.setIsLoad(true);
        } else {

            myScrollView.setIsLoad(false);
        }
    }

    public void setEmptyText() {
        if (controlNumber == 3) {

            empty_Text.setText("暂无评价");
        } else {

            empty_Text.setText("暂无案例");
        }
    }


    private LinearLayout mLlChatMeasure;
    private View mFooterView;
    private LinearLayout default_ll_bg;
    private TextView mTvEmptyMessage;
    private TextView mTvYeas, mTvStyle;
    private TextView mTvDesignFee, mTvMeasureFee;
    private ImageView mIvCertification;
    private PolygonImageView mHeadIcon;
    private Button mBtnChat, mBtnMeasure;
    private AlertView unFollowedAlertView;
    private TextView mTvFollowedNum;    /// 关注人数 .
    private LinearLayout ll_case_choose_contain;//容器
    private TextView case_2d_btn;//2d案例
    private TextView case_3d_btn;//3d案例
    private TextView case_2d_btn_replace_top;//2d案例代替
    private TextView case_3d_btn_replace_top;//3d案例代替
    private TextView consumer_appraise_replace_top;//评价按钮代替
    private TextView consumer_appraise;//评价按钮
    private TextView empty_Text;//空白页面文字
    private ChooseViewPointer chooseViewPointer;//滚动条
    private ChooseViewPointer choose_point_replace;//滚动条代替
    private MyScrollView myScrollView;
    private MyScrollViewLayout mMyScrollViewLayout;

    private String mNickName;
    private String mSelfAcsMemberId;
    private String mMeasureFee;
    private String member_id;
    private String mMemberType, mDesignerId, mHsUid;
    private SeekDesignerDetailAdapter mSeekDesignerDetailAdapter;
    private SeekDesignerDetailBean mSeekDesignerDetailBean;
    private int LIMIT = 10;
    private int OFFSET = 0;
    private int width;
    private int height;
    private int btHeight = 0;
    private int controlNumber = 1;//控制2D案例的评价的颜色，以及要加载或刷新的fragment
    private int OFFSETCOUNT = 10;
    private int appraiseCount = 0;
    private int TWO_D_CASE_COUNT = 0;
    private int THREE_D_CASE_COUNT = 0;
    private int APPRAISE_COUNT = 0;
    private int default_2d_picture_count = 0;
    private int default_3d_picture_count = 0;
    private Handler handler;
    private boolean isTitleTwoShow = true;
    private boolean isScrollToTop = true;//判断第一次是否滑动置顶
    private boolean is2DFrist = true;//2d只走一次
    private boolean is3DFrist = true;//3d只走一次
    private boolean isAppraiseFirst = true;//只走一次
    private boolean getAppraiseCount = true;//获取评价数量
    private int scrollLastMoveDistance = 0;//ScrollView的最后一次纵坐标记录
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private DesignerPersonMasterPageFragment mDesignerPersonMasterPageFragment;
    private DesignerPerson3DMasterPageFragment mDesignerPerson3DMasterPageFragment;
    private DesignerAppraiseFragment mDesignerAppraiseFragment;
    private Fragment shareFragment;
    private LinearLayout piv_seek_designer_head_linearlayout;
    private LinearLayout ll_case_choose_contain_replace;
    private AppraiseDesignBeen mAppraiseDesignBeen;
    private List<AppraiseDesignBeen.EstimatesBean> estimates;


    private boolean isFirstIn3D = true;//判断3D案例背景显示与否
    private boolean isFirstIn2D = true;//判断2D案例背景显示与否
    private boolean isRefreshOrLoad3D = true;
    private boolean isRefreshOrLoadAppraise = true;
    private boolean isRefreshOrLoad2D = true;
    private MemberEntity memberEntity;
    private ArrayList<SeekDesignerDetailBean.CasesEntity> mCasesEntityArrayList = new ArrayList<>();
    private DesignerDetailHomeBean seekDesignerDetailHomeBean;
    private Case3DBeen case3DBeen;
    private CustomProgress customProgress;

}
