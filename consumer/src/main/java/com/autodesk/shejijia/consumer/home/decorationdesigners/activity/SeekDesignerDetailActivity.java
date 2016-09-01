package com.autodesk.shejijia.consumer.home.decorationdesigners.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.adapter.SeekDesignerDetailAdapter;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.AppraiseDesignBeen;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.DesignerDetailHomeBean;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.DesignerInfoBean;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.FollowingDesignerBean;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.SeekDesignerDetailBean;
import com.autodesk.shejijia.consumer.home.decorationdesigners.fragment.DesignerAppraiseFragment;
import com.autodesk.shejijia.consumer.home.decorationdesigners.fragment.DesignerPerson3DMasterPageFragment;
import com.autodesk.shejijia.consumer.home.decorationdesigners.fragment.DesignerPersonMasterPageFragment;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.consumer.activity.MeasureFormActivity;
import com.autodesk.shejijia.shared.components.common.appglobal.ApiManager;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.MyToast;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.uielements.chooseview.ChooseViewPointer;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullToRefreshLayout;
import com.autodesk.shejijia.shared.components.common.uielements.scrollview.MyScrollView;
import com.autodesk.shejijia.shared.components.common.uielements.scrollview.MyScrollViewLayout;
import com.autodesk.shejijia.shared.components.common.uielements.scrollview.MyScrollViewListener;
import com.autodesk.shejijia.shared.components.common.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.im.activity.ChatRoomActivity;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThread;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThreads;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatUtility;
import com.autodesk.shejijia.shared.components.im.manager.MPChatHttpManager;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONException;
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

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_seek_designer_detail;
    }

    @Override
    protected void initView() {
        super.initView();

        mFooterView = View.inflate(this, R.layout.view_empty_layout, null);
        mLlChatMeasure = (LinearLayout) findViewById(R.id.ll_seek_designer_detail_chat_measure);
        mRlEmpty = (RelativeLayout) mFooterView.findViewById(R.id.rl_empty);
        mHeadIcon = (PolygonImageView) findViewById(R.id.piv_seek_designer_head);
        mIvCertification = (ImageView) findViewById(R.id.img_seek_designer_detail_certification);
        mTvEmptyMessage = (TextView) mFooterView.findViewById(R.id.tv_empty_message);
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
        getSeekDesignerDetailHomeData(mDesignerId, mHsUid);

        setDefaultFragment();

        chooseViewPointer.setDecreaseWidth(width * 1 / 20f);
        chooseViewPointer.setWidthOrHeight(width, 0, 0f, 1 / 3f);
        case_2d_btn.setTextColor(getResources().getColor(R.color.my_project_title_pointer_color));

        choose_point_replace.setDecreaseWidth(width * 1 / 20f);
        choose_point_replace.setWidthOrHeight(width, 0, 0f, 1 / 3f);
        case_2d_btn_replace_top.setTextColor(getResources().getColor(R.color.my_project_title_pointer_color));


        setChooseViewWidth();
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
                if (memberEntity != null) {

                    if (null != seekDesignerDetailHomeBean) {
                        member_id = memberEntity.getAcs_member_id();
                        final String designer_id = seekDesignerDetailHomeBean.getDesigner().getAcs_member_id();
                        final String recipient_ids = member_id + "," + designer_id + "," + ApiManager.getAdmin_User_Id(ApiManager.RUNNING_DEVELOPMENT);
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
                    AdskApplication.getInstance().doLogin(this);
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
                        final String recipient_ids = member_id + "," + designer_id + "," + ApiManager.getAdmin_User_Id(ApiManager.RUNNING_DEVELOPMENT);

                        MPChatHttpManager.getInstance().retrieveMultipleMemberThreads(recipient_ids, 0, 10, new OkStringRequest.OKResponseCallback() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                MPNetworkUtils.logError(TAG, volleyError);
                            }

                            @Override
                            public void onResponse(String s) {

                                MPChatThreads mpChatThreads = MPChatThreads.fromJSONString(s);

                                final Intent intent = new Intent(SeekDesignerDetailActivity.this, ChatRoomActivity.class);
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
                                    SeekDesignerDetailActivity.this.startActivity(intent);

                                } else {
                                    MPChatHttpManager.getInstance().getThreadIdIfNotChatBefore(member_id, designer_id, new OkStringRequest.OKResponseCallback() {
                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {
                                            MPNetworkUtils.logError(TAG, volleyError);
                                        }

                                        @Override
                                        public void onResponse(String s) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(s);
                                                String thread_id = jsonObject.getString("thread_id");
                                                intent.putExtra(ChatRoomActivity.ASSET_ID, "");
                                                intent.putExtra(ChatRoomActivity.RECIEVER_HS_UID, hs_uid);
                                                intent.putExtra(ChatRoomActivity.THREAD_ID, thread_id);
                                                SeekDesignerDetailActivity.this.startActivity(intent);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }
                            }

                        });
                    } else {
                        new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, SeekDesignerDetailActivity.this, AlertView.Style.Alert, null).show();
                    }
                } else {
                    AdskApplication.getInstance().doLogin(this);
                }
                break;

            case R.id.case_2d_btn:
                chooseViewPointer.setCase2dBtn(width);
                choose_point_replace.setCase2dBtn(width);

                if (mDesignerPersonMasterPageFragment == null) {
                    mDesignerPersonMasterPageFragment = new DesignerPersonMasterPageFragment();
                }

                switchFragment(shareFragment, mDesignerPersonMasterPageFragment);
                //设置颜色
                controlNumber = 1;
                setTextColor(controlNumber);

                break;

            case R.id.case_3d_btn:

                getSeekDesign3DDetailData(SeekDesignerDetailActivity.this.mDesignerId, 0, 50, 0);
                case_2d_btn.setClickable(true);
                case_2d_btn_replace_top.setClickable(true);
                chooseViewPointer.setCase3dBtn(width);
                choose_point_replace.setCase3dBtn(width);

                if (mDesignerPerson3DMasterPageFragment == null) {
                    mDesignerPerson3DMasterPageFragment = new DesignerPerson3DMasterPageFragment();
                }
                controlNumber = 2;
                switchFragment(shareFragment, mDesignerPerson3DMasterPageFragment);
                //设置颜色
                setTextColor(controlNumber);
                break;

            /// TODO  MERGE BUG.
//            case R.id.consumer_appraise:
//                case_2d_btn.setClickable(true);
//                case_2d_btn_replace_top.setClickable(true);
//                chooseViewPointer.setCase3dBtn(width);
//                choose_point_replace.setCase3dBtn(width);
//
//                if (mDesignerPerson3DMasterPageFragment == null) {
//                    mDesignerPerson3DMasterPageFragment = new DesignerPerson3DMasterPageFragment();
//                }
//                controlNumber = 2;
//                switchFragment(shareFragment, mDesignerPerson3DMasterPageFragment);
//                //设置颜色
//                setTextColor(controlNumber);
//
//                break;

            case R.id.consumer_appraise:
                case_2d_btn.setClickable(true);
                case_2d_btn_replace_top.setClickable(true);
                chooseViewPointer.setConsumerAppraise(width);
                choose_point_replace.setConsumerAppraise(width);
                controlNumber = 3;

                if (mDesignerAppraiseFragment == null) {
                    mDesignerAppraiseFragment = new DesignerAppraiseFragment();
                    getAppraiseData(mDesignerId, LIMIT, OFFSET);//获取评价数据

                }
                switchFragment(shareFragment, mDesignerAppraiseFragment);
                setTextColor(controlNumber);

                if (isFrist) {
                    isScrollToTop = true;
                    isFrist = false;
                    onScrollChange(myScrollView, 0, 0, 0, 0);
                }

                break;

            case R.id.case_2d_btn_replace:

                chooseViewPointer.setCase2dBtn(width);
                choose_point_replace.setCase2dBtn(width);
                controlNumber = 1;
                if (mDesignerPersonMasterPageFragment == null) {
                    mDesignerPersonMasterPageFragment = new DesignerPersonMasterPageFragment();
                }
                switchFragment(shareFragment, mDesignerPersonMasterPageFragment);

                setTextColor(controlNumber);
                break;

            case R.id.case_3d_btn_replace_top:

                getSeekDesign3DDetailData(SeekDesignerDetailActivity.this.mDesignerId, 0, 50, 0);
                case_2d_btn.setClickable(true);
                case_2d_btn_replace_top.setClickable(true);

                chooseViewPointer.setCase3dBtn(width);
                choose_point_replace.setCase3dBtn(width);
                if (mDesignerPerson3DMasterPageFragment == null) {
                    mDesignerPerson3DMasterPageFragment = new DesignerPerson3DMasterPageFragment();
                }
                controlNumber = 2;
                switchFragment(shareFragment, mDesignerPerson3DMasterPageFragment);
                //设置颜色
                setTextColor(controlNumber);

                break;
            case R.id.consumer_appraise_replace_top:
                case_2d_btn.setClickable(true);
                case_2d_btn_replace_top.setClickable(true);
                chooseViewPointer.setConsumerAppraise(width);
                choose_point_replace.setConsumerAppraise(width);
                controlNumber = 3;
                if (mDesignerAppraiseFragment == null) {
                    mDesignerAppraiseFragment = new DesignerAppraiseFragment();
                    getAppraiseData(mDesignerId, LIMIT, OFFSET);//获取评价数据
                }
                switchFragment(shareFragment, mDesignerAppraiseFragment);

                setTextColor(controlNumber);
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
                if (null == jsonObject) {
                    return;
                }
                String info = GsonUtil.jsonToString(jsonObject);
                seekDesignerDetailHomeBean = GsonUtil.jsonToBean(info, DesignerDetailHomeBean.class);

                getSeekDesignerDetailData(SeekDesignerDetailActivity.this.mDesignerId, 0, SeekDesignerDetailActivity.this.LIMIT, 0);

                updateViewFromDesignerDetailData(seekDesignerDetailHomeBean);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, SeekDesignerDetailActivity.this, AlertView.Style.Alert, null).show();
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

                String info = GsonUtil.jsonToString(jsonObject);
                mSeekDesignerDetailBean = GsonUtil.jsonToBean(info, SeekDesignerDetailBean.class);
                if (mSeekDesignerDetailBean.getCases().size() > 0) {


                    if (isRefreshOrLoad) {
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
                    Toast.makeText(SeekDesignerDetailActivity.this, "已经没有更多案例了", Toast.LENGTH_SHORT).show();
                    mMyScrollViewLayout.loadmoreFinish(mMyScrollViewLayout.SUCCEED);
                }


            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);

                if (isRefreshOrLoad) {

                    mMyScrollViewLayout.refreshFinish(mMyScrollViewLayout.FAIL);
                } else {

                    mMyScrollViewLayout.loadmoreFinish(mMyScrollViewLayout.FAIL);
                }

                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, SeekDesignerDetailActivity.this, AlertView.Style.Alert, null).show();
            }
        };
        MPServerHttpManager.getInstance().getSeekDesignerDetailData(designer_id, offset, limit, okResponseCallback);
    }


    /**
     * 获取设计师的信息,并刷新
     */
    public void getSeekDesign3DDetailData(String designer_id, int offset, int limit,
                                          final int state) {

        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                String info = GsonUtil.jsonToString(jsonObject);
                mSeekDesignerDetailBean = GsonUtil.jsonToBean(info, SeekDesignerDetailBean.class);
                if (mSeekDesignerDetailBean.getCases().size() > 0) {


                    if (isRefreshOrLoad) {
                        mDesignerPerson3DMasterPageFragment.getMore2DCaseData(mSeekDesignerDetailBean, 0);//刷新

                        mMyScrollViewLayout.refreshFinish(mMyScrollViewLayout.SUCCEED);
                    } else {
                        mDesignerPerson3DMasterPageFragment.getMore2DCaseData(mSeekDesignerDetailBean, 1);//加载

                        mMyScrollViewLayout.loadmoreFinish(mMyScrollViewLayout.SUCCEED);
                        myScrollView.setIsLoad(false);
                    }

                } else {
                    //没有更多案例时
                    mMyScrollViewLayout.refreshFinish(mMyScrollViewLayout.SUCCEED);
                    Toast.makeText(SeekDesignerDetailActivity.this, "已经没有更多案例了", Toast.LENGTH_SHORT).show();
                    mMyScrollViewLayout.loadmoreFinish(mMyScrollViewLayout.SUCCEED);
                }


            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, SeekDesignerDetailActivity.this,
                        AlertView.Style.Alert, null).show();
                if (isRefreshOrLoad) {

                    mMyScrollViewLayout.refreshFinish(mMyScrollViewLayout.FAIL);
                } else {

                    mMyScrollViewLayout.loadmoreFinish(mMyScrollViewLayout.FAIL);
                }

                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, SeekDesignerDetailActivity.this, AlertView.Style.Alert, null).show();
            }
        };
        MPServerHttpManager.getInstance().getSeekDesigner3DDetailData(designer_id, offset, limit, okResponseCallback);
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

            mTvFollowedNum.setText(" : " + follows);
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
                mTvStyle.setText(designer.getStyle_names());
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
                mTvFollowedNum.setText(" : " + followingDesignerBean.following_count);

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
        int myScrollViewChildHeight = myScrollView.getChildAt(0).getHeight();//myScrollView中子类的高度
        int myScrollViewHeight = myScrollView.getHeight();//myScrollView的高度
//        if ((myScrollViewChildHeight == myScrollViewHeight + y) || (myScrollViewChildHeight - myScrollViewHeight - y < 5) || myScrollViewChildHeight < myScrollViewHeight) {
//
//            Log.i(TAG, "YAOmyScrollView子类" + myScrollView.getChildAt(0).getHeight());
//            Log.i(TAG, "YAOmyScrollView高度" + myScrollView.getHeight());
//            Log.i(TAG, "YAOmyScrollView滑动的距离" + y);
//            myScrollView.setIsLoad(true);
//        } else {
//
//            myScrollView.setIsLoad(false);
//        }

        int height = myScrollView.getHeight();
        int scrollViewMeasuredHeight = myScrollView.getChildAt(0).getMeasuredHeight();

        if(scrollViewMeasuredHeight <= y + height){

            myScrollView.setIsLoad(true);
        }else {

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
            AdskApplication.getInstance().doLogin(this);
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
        setTextColorForRightNavButton(UIUtils.getColor(R.color.search_text_color));
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
     * 案例库为空时候显示的提示框
     */
    private AlertView getEmptyAlertView(String content) {
        return new AlertView(UIUtils.getString(R.string.tip), content, null, null, null, SeekDesignerDetailActivity.this,
                AlertView.Style.Alert, null).setCancelable(true);
    }

    /**
     * 获取设计师评价的数据
     * 第一次进入要刷新页面
     */
    public void getAppraiseData(String designer_Id, int limit, int offset) {

        MPServerHttpManager.getInstance().getEstimateList(designer_Id, limit, offset, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                if (isRefreshOrLoad) {

                    mMyScrollViewLayout.refreshFinish(mMyScrollViewLayout.FAIL);
                } else {

                    mMyScrollViewLayout.loadmoreFinish(mMyScrollViewLayout.FAIL);
                }
            }

            @Override
            public void onResponse(JSONObject jsonObject) {

                //评价页面
                String jsonString = GsonUtil.jsonToString(jsonObject);
                mAppraiseDesignBeen = GsonUtil.jsonToBean(jsonString, AppraiseDesignBeen.class);
                estimates = mAppraiseDesignBeen.getEstimates();

                if (estimates != null) {

                    if (isRefreshOrLoadAppraise) {

                        mDesignerAppraiseFragment.updateListView(estimates);
                        mMyScrollViewLayout.refreshFinish(mMyScrollViewLayout.SUCCEED);
                    } else {
                        mDesignerAppraiseFragment.loadMoreData(estimates);
                        mMyScrollViewLayout.loadmoreFinish(mMyScrollViewLayout.SUCCEED);
                    }


                }
                mMyScrollViewLayout.loadmoreFinish(mMyScrollViewLayout.SUCCEED);

            }
        });


    }

    @Override
    public void onRefresh(MyScrollViewLayout pullToRefreshLayout) {

        if (controlNumber == 1) {
            isRefreshOrLoad = true;

            getSeekDesignerDetailData(SeekDesignerDetailActivity.this.mDesignerId, 0, SeekDesignerDetailActivity.this.LIMIT, 0);
            OFFSETCOUNT = 10;
        } else if (controlNumber == 2) {
            getSeekDesign3DDetailData(SeekDesignerDetailActivity.this.mDesignerId, 0, LIMIT, 0);
            OFFSETCOUNT = 10;
        } else {
            isRefreshOrLoadAppraise = true;
            getAppraiseData(mDesignerId, LIMIT, OFFSET);//获取评价数据

        }
    }

    @Override
    public void onLoadMore(MyScrollViewLayout pullToRefreshLayout) {

        if (controlNumber == 1) {
            isRefreshOrLoad = false;
            getSeekDesignerDetailData(SeekDesignerDetailActivity.this.mDesignerId, OFFSETCOUNT, SeekDesignerDetailActivity.this.LIMIT, 0);
            OFFSETCOUNT = OFFSETCOUNT + 10;
        } else if (controlNumber == 2) {
            getSeekDesign3DDetailData(SeekDesignerDetailActivity.this.mDesignerId, OFFSETCOUNT, SeekDesignerDetailActivity.this.LIMIT, 0);
            OFFSETCOUNT = OFFSETCOUNT + 10;
        } else {
            isRefreshOrLoadAppraise = false;
            getAppraiseData(mDesignerId, LIMIT, OFFSET++);//获取评价数据
        }
    }


    //    /**
//     * 第一次进入要刷新页面
//     *
//     * @param hasFocus 判断是否刷新
//     */
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (isFirstIn) {
//            mPullToRefreshLayout.autoRefresh();
//            isFirstIn = false;
//        }
//    }
    private LinearLayout mLlChatMeasure;
    private RelativeLayout mRlEmpty;
    private PullToRefreshLayout mPullToRefreshLayout;
    private View mFooterView;
    private ListView mListView;
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
    private boolean isTitleTwoShow = true;
    private boolean isScrollToTop = false;
    private boolean isFrist = true;//只走一次
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


    private boolean isFirstIn = true;
    private boolean isRefreshOrLoad = true;
    private boolean isRefreshOrLoadAppraise = true;
    private MemberEntity memberEntity;
    private ArrayList<SeekDesignerDetailBean.CasesEntity> mCasesEntityArrayList = new ArrayList<>();
    private DesignerDetailHomeBean seekDesignerDetailHomeBean;

}
