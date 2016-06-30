package com.autodesk.shejijia.consumer.home.decorationdesigners.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.adapter.SeekDesignerDetailAdapter;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.SeekDesignerDetailBean;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.SeekDesignerDetailHomeBean;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.activity.CaseLibraryDetailActivity;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.consumer.personalcenter.consumer.activity.MeasureFormActivity;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.im.activity.ChatRoomActivity;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThread;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThreads;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatUtility;
import com.autodesk.shejijia.shared.components.im.manager.MPChatHttpManager;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullToRefreshLayout;
import com.autodesk.shejijia.shared.components.common.uielements.viewgraph.PolygonImageView;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 2016/2/27 0029 17:32 .
 * @file SeekDesignerDetailActivity  .
 * @brief 查看设计师详情页面 .
 */
public class SeekDesignerDetailActivity extends NavigationBarActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener, SeekDesignerDetailAdapter.OnItemCaseLibraryClickListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_seek_designer_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        mHeader = View.inflate(this, R.layout.activity_seek_designer_detail_header, null);
        mFooterView = View.inflate(this, R.layout.view_empty_layout, null);
        mLlChatMeasure = (LinearLayout) mHeader.findViewById(R.id.ll_seek_designer_detail_chat_measure);
        mRlEmpty = (RelativeLayout) mFooterView.findViewById(R.id.rl_empty);
        mHeadIcon = (PolygonImageView) mHeader.findViewById(R.id.piv_seek_designer_head);
        mPullToRefreshLayout = ((PullToRefreshLayout) findViewById(R.id.refresh_view));
        mListView = (ListView) findViewById(R.id.lv_seek_detail_listview);
        mIvCertification = (ImageView) mHeader.findViewById(R.id.img_seek_designer_detail_certification);
        mTvEmptyMessage = (TextView) mFooterView.findViewById(R.id.tv_empty_message);
        mTvYeas = (TextView) mHeader.findViewById(R.id.tv_seek_designer_detail_yeas);
        mTvStyle = (TextView) mHeader.findViewById(R.id.tv_seek_designer_detail_style);
        mTvDesignFee = (TextView) mHeader.findViewById(R.id.tv_seek_designer_design_fee);
        mTvMeasureFee = (TextView) mHeader.findViewById(R.id.tv_seek_designer_detail_measure_fee);
        mBtnChat = (Button) mHeader.findViewById(R.id.btn_seek_designer_detail_chat);
        mBtnMeasure = (Button) mHeader.findViewById(R.id.btn_seek_designer_detail_optional_measure);

        mListView.addHeaderView(mHeader);
        mListView.addFooterView(mFooterView);
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
        mSeekDesignerDetailAdapter = new SeekDesignerDetailAdapter(SeekDesignerDetailActivity.this, mCasesEntityArrayList, this);
        mListView.setAdapter(mSeekDesignerDetailAdapter);
        getSeekDesignerDetailHomeData(mDesignerId, mHsUid);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mSeekDesignerDetailAdapter.setOnItemCaseLibraryClickListener(this);
        mBtnMeasure.setOnClickListener(this);
        mPullToRefreshLayout.setOnRefreshListener(this);
        mBtnChat.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        switch (view.getId()) {
            case R.id.btn_seek_designer_detail_optional_measure:
                /// 选择该设计师去量房,如果用户还没登陆 会进入注册登陆界面.
                if (null != memberEntity) {
                    Intent intent = new Intent(SeekDesignerDetailActivity.this, MeasureFormActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.SeekDesignerDetailKey.MEASURE_FREE, mMeasureFee);
                    bundle.putString(Constant.SeekDesignerDetailKey.DESIGNER_ID, mDesignerId);
                    bundle.putString(Constant.SeekDesignerDetailKey.SEEK_TYPE, Constant.SeekDesignerDetailKey.SEEK_DESIGNER_DETAIL);
                    bundle.putString(Constant.SeekDesignerDetailKey.HS_UID, mHsUid);
                    bundle.putInt(Constant.SeekDesignerDetailKey.FLOW_STATE, 0);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    AdskApplication.getInstance().doLogin(this);
                }
                break;
            case R.id.btn_seek_designer_detail_chat:
                /**
                 * 聊天,如没有登陆 也会跳入注册登陆页面
                 */
                if (memberEntity != null) {
                    member_id = memberEntity.getAcs_member_id();
                    final String designer_id = seekDesignerDetailHomeBean.getDesigner().getAcs_member_id();
                    final String hs_uid = seekDesignerDetailHomeBean.getHs_uid();
                    final String receiver_name = seekDesignerDetailHomeBean.getNick_name();
                    final String recipient_ids = member_id + "," + designer_id + "," + "20730165";

                    MPChatHttpManager.getInstance().retrieveMultipleMemberThreads(recipient_ids, 0, 10, new OkStringRequest.OKResponseCallback() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            MPNetworkUtils.logError(TAG, volleyError);
                        }

                        @Override
                        public void onResponse(String s) {
                            MPChatThreads mpChatThreads = MPChatThreads.fromJSONString(s);
                            if (mpChatThreads != null && mpChatThreads.threads.size() > 0) {
                                MPChatThread mpChatThread = mpChatThreads.threads.get(0);

                                int assetId = MPChatUtility.getAssetIdFromThread(mpChatThread);
                                Intent intent = new Intent(SeekDesignerDetailActivity.this, ChatRoomActivity.class);
                                intent.putExtra(ChatRoomActivity.THREAD_ID, mpChatThread.thread_id);
                                intent.putExtra(ChatRoomActivity.RECIEVER_USER_ID, designer_id);
                                intent.putExtra(ChatRoomActivity.RECIEVER_USER_NAME, receiver_name);
                                intent.putExtra(ChatRoomActivity.ASSET_ID, assetId+"");
                                intent.putExtra(ChatRoomActivity.MEMBER_TYPE, mMemberType);
                                intent.putExtra(ChatRoomActivity.ACS_MEMBER_ID, member_id);
                                startActivity(intent);

                            } else {

                                Intent intent = new Intent(SeekDesignerDetailActivity.this, ChatRoomActivity.class);
                                intent.putExtra(ChatRoomActivity.RECIEVER_HS_UID, hs_uid);
                                intent.putExtra(ChatRoomActivity.RECIEVER_USER_NAME, receiver_name);
                                intent.putExtra(ChatRoomActivity.RECIEVER_USER_ID, designer_id);
                                intent.putExtra(ChatRoomActivity.ACS_MEMBER_ID, member_id);
                                intent.putExtra(ChatRoomActivity.MEMBER_TYPE, mMemberType);
                                intent.putExtra(ChatRoomActivity.ASSET_ID, "");
                                startActivity(intent);
                            }
                        }
                    });

                } else {
                    AdskApplication.getInstance().doLogin(this);
                }
                break;
        }
    }

    /**
     * 单击某个item进入查看详情
     *
     * @param position item的位置
     */
    @Override
    public void OnItemCaseLibraryClick(int position) {
        String case_id = mCasesEntityArrayList.get(position).getId();
        Intent intent = new Intent(this, CaseLibraryDetailActivity.class);
        intent.putExtra(Constant.CaseLibraryDetail.CASE_ID, case_id);
        startActivity(intent);
    }

    /**
     * 获取设计师的信息,并刷新
     */
    public void getSeekDesignerDetailData(String designer_id, int offset, int limit, final int state) {

        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    String info = GsonUtil.jsonToString(jsonObject);
                    KLog.json(TAG, info);
                    mSeekDesignerDetailBean = GsonUtil.jsonToBean(info, SeekDesignerDetailBean.class);

                    updateViewFromDesignerData(state);
                } finally {
                    mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        };
        MPServerHttpManager.getInstance().getSeekDesignerDetailData(designer_id, offset, limit, okResponseCallback);
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
                getSeekDesignerDetailData(SeekDesignerDetailActivity.this.mDesignerId, 0, LIMIT, 0);
                String info = GsonUtil.jsonToString(jsonObject);
                seekDesignerDetailHomeBean = GsonUtil.jsonToBean(info, SeekDesignerDetailHomeBean.class);
                KLog.json(TAG, info);

                updateViewFromDesignerDetailData(seekDesignerDetailHomeBean);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, SeekDesignerDetailActivity.this,
                        AlertView.Style.Alert, null).show();
            }
        };
        MPServerHttpManager.getInstance().getSeekDesignerDetailHomeData(designer_id, hsUid, okResponseCallback);
    }

    /**
     * 隐藏底部布局
     * param 传入的信息集合
     */
    private void hideFooterView(ArrayList<SeekDesignerDetailBean.CasesEntity> list) {
        if (list != null && list.size() > 0) {
            mRlEmpty.setVisibility(View.GONE);
        } else {
            mRlEmpty.setVisibility(View.VISIBLE);
        }
        WindowManager wm = (WindowManager) SeekDesignerDetailActivity.this.getSystemService(SeekDesignerDetailActivity.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        android.view.ViewGroup.LayoutParams layoutParams = mRlEmpty.getLayoutParams();
        mRlEmpty.getLayoutParams();
        layoutParams.height = height / 2;
        mRlEmpty.setLayoutParams(layoutParams);
        mTvEmptyMessage.setText(UIUtils.getString(R.string.no_designer_case));
    }

    /**
     * 更新设计师的从业信息，及费用
     *
     * @param seekDesignerDetailHomeBean 　设计师信息实体类
     */
    private void updateViewFromDesignerDetailData(SeekDesignerDetailHomeBean seekDesignerDetailHomeBean) {
        if (null != seekDesignerDetailHomeBean) {
            if (seekDesignerDetailHomeBean.getAvatar() == null) {
                mHeadIcon.setImageResource(R.drawable.icon_default_avator);
            } else {
                ImageUtils.displayAvatarImage(seekDesignerDetailHomeBean.getAvatar(), mHeadIcon);
            }

            SeekDesignerDetailHomeBean.DesignerEntity designer = seekDesignerDetailHomeBean.getDesigner();
            if (null != designer.getIs_real_name() + "" && designer.getIs_real_name() == 2) {
                mIvCertification.setVisibility(View.VISIBLE);
            } else {
                mIvCertification.setVisibility(View.GONE);
            }
            String nick_name = seekDesignerDetailHomeBean.getNick_name();
            nick_name = TextUtils.isEmpty(nick_name) ? "" : nick_name;
            setTitleForNavbar(nick_name);

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
     * 设计师的信息更新
     *
     * @param state 　更新状态
     */
    private void updateViewFromDesignerData(int state) {
        switch (state) {
            case 0:
                OFFSET = 10;
                mCasesEntityArrayList.clear();
                break;
            case 1:
                OFFSET = 10;
                mCasesEntityArrayList.clear();
                break;
            case 2:
                OFFSET += 10;
                break;
            default:
                break;
        }
        mCasesEntityArrayList.addAll(mSeekDesignerDetailBean.getCases());
        if (mCasesEntityArrayList.size() < 1) {
            getEmptyAlertView(UIUtils.getString(R.string.case_is_empty)).show();
        }
        hideFooterView(mCasesEntityArrayList);
        mSeekDesignerDetailAdapter.notifyDataSetChanged();
    }

    /**
     * 判断是否显示在沟通按钮
     */
    private void showOrHideChatMeasure() {
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (null != memberEntity) {
            mMemberType = memberEntity.getMember_type();
            if (mMemberType.equals((Constant.UerInfoKey.CONSUMER_TYPE))) {
                mLlChatMeasure.setVisibility(View.VISIBLE);
            } else {
                mLlChatMeasure.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 案例库为空时候显示的提示框
     */
    private AlertView getEmptyAlertView(String content) {
        return new AlertView(UIUtils.getString(R.string.tip), content, null, null, null, SeekDesignerDetailActivity.this,
                AlertView.Style.Alert, null).setCancelable(true);
    }

    /**
     * 第一次进入要刷新页面
     *
     * @param hasFocus 判断是否刷新
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (isFirstIn) {
            mPullToRefreshLayout.autoRefresh();
            isFirstIn = false;
        }
    }

    /**
     * 刷新
     */
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        getSeekDesignerDetailData(mDesignerId, 0, LIMIT, 1);
    }

    /// 加载更多.
    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        getSeekDesignerDetailData(mDesignerId, OFFSET, LIMIT, 2);
    }

    /// 控件.
    private LinearLayout mLlChatMeasure;
    private RelativeLayout mRlEmpty;
    private PullToRefreshLayout mPullToRefreshLayout;
    private View mHeader, mFooterView;
    private ListView mListView;
    private TextView mTvEmptyMessage;
    private TextView mTvYeas, mTvStyle;
    private TextView mTvDesignFee, mTvMeasureFee;
    private ImageView mIvCertification;
    private PolygonImageView mHeadIcon;
    private Button mBtnChat, mBtnMeasure;

    /// 变量.
    private String mMeasureFee;
    private String member_id;
    private String mMemberType, mDesignerId, mHsUid;
    private SeekDesignerDetailAdapter mSeekDesignerDetailAdapter;
    private SeekDesignerDetailBean mSeekDesignerDetailBean;
    private int LIMIT = 10;
    private int OFFSET = 0;
    private boolean isFirstIn = true;
    private ArrayList<SeekDesignerDetailBean.CasesEntity> mCasesEntityArrayList = new ArrayList<>();
    private SeekDesignerDetailHomeBean seekDesignerDetailHomeBean;

}