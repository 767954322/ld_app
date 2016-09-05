package com.autodesk.shejijia.consumer.home.decorationlibrarys.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.activity.SeekDesignerDetailActivity;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.FollowingDesignerBean;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter.List3DLibraryAdapter;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.Case3DDetailBean;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.Case3DLibraryListBean;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.utils.AnimationUtil;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.consumer.utils.ToastUtil;
import com.autodesk.shejijia.consumer.wxapi.SendWXShared;
import com.autodesk.shejijia.shared.components.common.appglobal.ApiManager;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.WXSharedPopWin;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.PictureProcessingUtil;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CaseLibraryDetail3DActivity extends NavigationBarActivity implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener, View.OnTouchListener, View.OnClickListener {

    private ListView caseLibraryNew;
    private LinearLayout llThumbUp;
    private String case_id;
    private Case3DDetailBean case3DDetailBean;
    private ArrayList<Case3DDetailBean> case3DDetailList;

    private List3DLibraryAdapter mCase3DLibraryAdapter;
    private RelativeLayout rlCaseLibraryHead;
    private View viewHead;
    private RelativeLayout rlCaseLibraryBottom;
    private ImageView mdesignerAvater;
    private TextView mCaseLibraryText;
    private PolygonImageView pivImgCustomerHomeHeader;
    private ImageView ivCustomerIm;
    private TextView ivConsumeHomeDesigner;
    private TextView mIvFollowedDesigner;
    private TextView tvCustomerHomeStyle;
    private TextView tvCustomerHomeRoom;
    private TextView tvCustomerHomeArea;
    private TextView tvThumbUp;
    private Map<String, String> roomHall;
    private Map<String, String> style;
    private LinearLayout ll_fenxiang_up;
    private LinearLayout rlThumbUp;
    private TextView tvheadThumbUp;
    private LinearLayout ll_fenxiang_down;
    private WXSharedPopWin takePhotoPopWin;
    private boolean ifIsSharedToFriends = true;
    private PictureProcessingUtil pictureProcessingUtil;
    private String designer_id;
    private String hs_uid;
    private MemberEntity memberEntity;
    private String member_type;
    private String member_id;
    private String mMemberType;
    private boolean isMemberLike;
    private ImageView ivThumbUp;
    private ImageView ivHeadThumbUp;
    private String firstCaseLibraryImageUrl;

    private float mPosY = 0;
    private float mCurPosY = 0;

    private AlertView unFollowedAlertView;
    private Case3DLibraryListBean.CasesBean.DesignerInfoBean mDesignerInfo;
    private String mHs_uid;
    private String mNickName;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_case_linrary_new;
    }

    @Override
    protected void initView() {
        super.initView();
        caseLibraryNew = (ListView) findViewById(R.id.case_library_new);
        llThumbUp = (LinearLayout) findViewById(R.id.rl_thumb_up);
        ll_fenxiang_up = (LinearLayout) findViewById(R.id.ll_fenxiang);
        viewHead = findViewById(R.id.case_head);
        rlCaseLibraryBottom = (RelativeLayout) findViewById(R.id.rl_case_library_bottom);

        ivThumbUp = (ImageView) findViewById(R.id.iv_thumb_up);

        pivImgCustomerHomeHeader = (PolygonImageView) findViewById(R.id.piv_img_customer_home_header);
        ivCustomerIm = (ImageView) findViewById(R.id.img_look_more_detail_chat);
        ivConsumeHomeDesigner = (TextView) findViewById(R.id.iv_consume_home_designer);
        mIvFollowedDesigner = (TextView) findViewById(R.id.iv_follow_designer);
        tvCustomerHomeStyle = (TextView) findViewById(R.id.tv_customer_home_style);
        tvCustomerHomeRoom = (TextView) findViewById(R.id.tv_customer_home_room);
        tvCustomerHomeArea = (TextView) findViewById(R.id.tv_customer_home_area);
        tvThumbUp = (TextView) findViewById(R.id.tv_thumb_up);

        View view = LayoutInflater.from(this).inflate(R.layout.case_library_new_item, null);
        mdesignerAvater = (ImageView) view.findViewById(R.id.case_library_item_iv);

        View viewHead = LayoutInflater.from(this).inflate(R.layout.caselibrary_head, null);
        ll_fenxiang_down = (LinearLayout) viewHead.findViewById(R.id.ll_fenxiang);
        rlThumbUp = (LinearLayout) viewHead.findViewById(R.id.rl_thumb_up);
        tvheadThumbUp = (TextView) viewHead.findViewById(R.id.tv_thumb_up);
        ivHeadThumbUp = (ImageView) viewHead.findViewById(R.id.iv_thumb_up);

        rlCaseLibraryHead = (RelativeLayout) viewHead.findViewById(R.id.rl_case_library_head);
        rlCaseLibraryHead.setVisibility(View.VISIBLE);
        View viewText = LayoutInflater.from(this).inflate(R.layout.case_library_text, null);
        mCaseLibraryText = (TextView) viewText.findViewById(R.id.case_library_text);
        caseLibraryNew.addHeaderView(view);
        caseLibraryNew.addHeaderView(viewHead);
        caseLibraryNew.addHeaderView(viewText);
        case3DDetailList = new ArrayList<>();
    }


    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        case_id = getIntent().getStringExtra(Constant.CaseLibraryDetail.CASE_ID);   /// 获取发过来的ID.
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        roomHall = AppJsonFileReader.getRoomHall(this);
        style = AppJsonFileReader.getStyle(this);
        getCase3DDetailData(case_id);
//        getCase3DDetailData("1589057");
        pictureProcessingUtil = new PictureProcessingUtil();
    }

    @Override
    protected void initListener() {
        super.initListener();
        caseLibraryNew.setOnScrollListener(this);
        caseLibraryNew.setOnTouchListener(this);
        llThumbUp.setOnClickListener(this);
        ll_fenxiang_down.setOnClickListener(this);
        ll_fenxiang_up.setOnClickListener(this);
        pivImgCustomerHomeHeader.setOnClickListener(this);
        ivCustomerIm.setOnClickListener(this);
        rlThumbUp.setOnClickListener(this);
        llThumbUp.setOnClickListener(this);
        mIvFollowedDesigner.setOnClickListener(this);
        caseLibraryNew.setOnItemClickListener(this);

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int top2 = rlCaseLibraryHead.getTop();
        if (top2 <= 0 || firstVisibleItem >= 1) {
            viewHead.setVisibility(View.VISIBLE);
        } else {
            viewHead.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_thumb_up://点赞
                if (null != memberEntity) {
                    if (!isMemberLike) {
                        sendThumbUp(case3DDetailBean.getDesign_asset_id());
                    } else {
                        //已经点过赞
                    }

                } else {
                    AdskApplication.getInstance().doLogin(this);
                }
                break;

            case R.id.iv_follow_designer://关注

                if (null != memberEntity) {
                    if (null != case3DDetailBean && null != case3DDetailBean.getDesigner_info()) {
                        Case3DDetailBean.DesignerInfoBean designer_info = case3DDetailBean.getDesigner_info();
                        boolean is_following = designer_info.getIs_following();
                        if (TextUtils.isEmpty(member_id)) {
                            AdskApplication.getInstance().doLogin(this);
                        } else {
                            if (is_following) {
                                unFollowedAlertView.show();
                            } else {
                                followingOrUnFollowedDesigner(true);
                            }
                        }
                    }
                } else {
                    AdskApplication.getInstance().doLogin(this);
                }
                break;

            case R.id.ll_fenxiang://分享
                if (null != memberEntity) {

                    if (isWeixinAvilible(CaseLibraryDetail3DActivity.this)) {
                        if (takePhotoPopWin == null) {
                            takePhotoPopWin = new WXSharedPopWin(this, onClickListener);
                        }
                        takePhotoPopWin.showAtLocation(findViewById(R.id.main_library), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    } else {
                        ToastUtil.showCustomToast(CaseLibraryDetail3DActivity.this, getString(R.string.anzhuangweixin));
                    }

                } else {
                    AdskApplication.getInstance().doLogin(this);
                }
                break;
            case R.id.piv_img_customer_home_header:    /// 进入设计师详情页面.
                Intent intent = new Intent(CaseLibraryDetail3DActivity.this, SeekDesignerDetailActivity.class);
                intent.putExtra(Constant.ConsumerDecorationFragment.designer_id, designer_id);
                intent.putExtra(Constant.ConsumerDecorationFragment.hs_uid, hs_uid);
                startActivity(intent);
                break;

            case R.id.img_look_more_detail_chat:    /// 进入聊天页面,如果没有登陆则进入登陆注册页面.
                if (memberEntity != null) {
                    member_id = memberEntity.getAcs_member_id();
                    mMemberType = memberEntity.getMember_type();
                    final String designer_id = case3DDetailBean.getDesigner_info().getDesigner().getAcs_member_id();
                    final String hs_uid = case3DDetailBean.getHs_designer_uid();
                    final String receiver_name = case3DDetailBean.getDesigner_info().getNick_name();
                    final String recipient_ids = member_id + "," + designer_id + "," + ApiManager.getAdmin_User_Id();

                    MPChatHttpManager.getInstance().retrieveMultipleMemberThreads(recipient_ids, 0, 10, new OkStringRequest.OKResponseCallback() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            MPNetworkUtils.logError(TAG, volleyError);
                        }

                        @Override
                        public void onResponse(String s) {
                            MPChatThreads mpChatThreads = MPChatThreads.fromJSONString(s);

                            Intent intent = new Intent(CaseLibraryDetail3DActivity.this, ChatRoomActivity.class);
                            intent.putExtra(ChatRoomActivity.RECIEVER_USER_ID, designer_id);
                            intent.putExtra(ChatRoomActivity.RECIEVER_USER_NAME, receiver_name);
                            intent.putExtra(ChatRoomActivity.ACS_MEMBER_ID, member_id);
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
                } else {
                    AdskApplication.getInstance().doLogin(this);
                }

            default:
                break;
        }
    }

    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }

        return false;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String webUrl = ApiManager.getHtml5Url(case_id);
            switch (v.getId()) {

                case R.id.tv_wx_shared_tofriends:

                    ifIsSharedToFriends = true;
                    try {
                        SendWXShared.sendProjectToWX(CaseLibraryDetail3DActivity.this, webUrl, case3DDetailBean.getDesign_name(), case3DDetailBean.getConception() + " ", ifIsSharedToFriends, firstCaseLibraryImageUrl);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.tv_wx_shared_tocircleof_friends:
                    ifIsSharedToFriends = false;
                    try {
                        SendWXShared.sendProjectToWX(CaseLibraryDetail3DActivity.this, webUrl, case3DDetailBean.getDesign_name(), case3DDetailBean.getConception() + " ", ifIsSharedToFriends, firstCaseLibraryImageUrl);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
            }
            if (takePhotoPopWin != null) {
                takePhotoPopWin.dismiss();
            }
        }
    };

    /**
     * 发送点赞请求
     *
     * @param assetId
     */

    public void sendThumbUp(String assetId) {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                rlThumbUp.setOnClickListener(null);
                llThumbUp.setOnClickListener(null);

                ToastUtil.showCustomToast(CaseLibraryDetail3DActivity.this, getString(R.string.dianzhangchenggong));
                ivThumbUp.setBackgroundResource(R.mipmap.yidianzan_ico);
                ivHeadThumbUp.setBackgroundResource(R.mipmap.yidianzan_ico);
                tvThumbUp.setText(getString(R.string.thumbup_conunt) + (case3DDetailBean.getFavorite_count() + 1));
                tvheadThumbUp.setText(getString(R.string.thumbup_conunt) + (case3DDetailBean.getFavorite_count() + 1));
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                new AlertView(UIUtils.getString(R.string.tip),
                        UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null,
                        CaseLibraryDetail3DActivity.this,
                        AlertView.Style.Alert, null).show();
            }
        };
        MPServerHttpManager.getInstance().sendThumbUpRequest(assetId, okResponseCallback);
    }

    /**
     * 获得是否已经点赞接口
     *
     * @param assetId
     */
    public void getThumbUp(String assetId) {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("yxw", jsonObject.toString());
                try {
                    isMemberLike = jsonObject.getBoolean("is_member_like");
                    if (isMemberLike) {
                        rlThumbUp.setOnClickListener(null);
                        llThumbUp.setOnClickListener(null);
                        ivThumbUp.setBackgroundResource(R.mipmap.yidianzan_ico);
                        ivHeadThumbUp.setBackgroundResource(R.mipmap.yidianzan_ico);
                    }

                    // int count = jsonObject.getInt("count");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, CaseLibraryDetail3DActivity.this,
                        AlertView.Style.Alert, null).show();
            }
        };
        MPServerHttpManager.getInstance().getThumbUpRequest(assetId, okResponseCallback);
    }

    /**
     * 获取案例的数据
     *
     * @param case_id 该案例的ID
     */
    public void getCase3DDetailData(final String case_id) {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                String info = GsonUtil.jsonToString(jsonObject);
                case3DDetailBean = GsonUtil.jsonToBean(info, Case3DDetailBean.class);
                case3DDetailList.add(case3DDetailBean);
                setTitleForNavbar(case3DDetailBean.getDesign_name());

                updateViewFromCaseDetailData();

            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, CaseLibraryDetail3DActivity.this,
                        AlertView.Style.Alert, null).show();
            }
        };
        MPServerHttpManager.getInstance().getCaseList3DDetail(case_id, okResponseCallback);
    }

    /**
     * 获取案例的数据，更新页面
     */
    private void updateViewFromCaseDetailData() {
        if (null == case3DDetailBean) {
            return;
        }
        Case3DDetailBean.DesignerInfoBean mDesignerInfo = case3DDetailBean.getDesigner_info();
        hs_uid = case3DDetailBean.getHs_designer_uid();
        if (mDesignerInfo == null) {
            return;
        }
        designer_id = mDesignerInfo.getDesigner().getAcs_member_id();
        mHs_uid = mDesignerInfo.getHs_uid();
        mNickName = mDesignerInfo.getNick_name();
        boolean is_following = mDesignerInfo.getIs_following();
        /**
         * 如果当前没有acs_member_id,就是没有登录，点击跳转到登录页面
         */
        if (null != memberEntity) {
            member_id = memberEntity.getAcs_member_id();
        }
        if (!TextUtils.isEmpty(member_id)) {
            /**
             * 如果是当前设计师，就不显示关注按钮
             */
            if (!member_id.equals(designer_id)) {
                setFollowedTitle(is_following);
            } else {
                mIvFollowedDesigner.setVisibility(View.GONE);
            }
        } else {
            setFollowedTitle(false);
        }
        initUnFollowedAlertView();

        //登录状态判断是否点赞
        if (null != memberEntity) {
            getThumbUp(case3DDetailBean.getDesign_asset_id());
        }

        List<Case3DDetailBean.DesignFileBean> images = case3DDetailBean.getDesign_file();

        //查找是否是封面图片  若是就添加到头部
        for (int i = 0; i < images.size(); i++) {
            if (images.get(i).isIs_primary() == true) {
                firstCaseLibraryImageUrl = images.get(i).getLink() + Constant.CaseLibraryDetail.JPG;
                ImageUtils.displayIconImage(images.get(i).getLink() + Constant.CaseLibraryDetail.JPG, mdesignerAvater);
            }
        }

        mCase3DLibraryAdapter = new List3DLibraryAdapter(CaseLibraryDetail3DActivity.this, case3DDetailList);

        caseLibraryNew.setAdapter(mCase3DLibraryAdapter);
        //设置简介
        String introduction = case3DDetailBean.getConception();
        if (introduction != null) {
            mCaseLibraryText.setText("          " + introduction);
        } else {
            mCaseLibraryText.setText(R.string.nodata);
        }

        tvCustomerHomeArea.setText(case3DDetailBean.getRoom_area() + "m²");
        String room_type = case3DDetailBean.getRoom_type();
        if (roomHall.containsKey(room_type)) {
            tvCustomerHomeRoom.setText(roomHall.get(room_type));
        }

        String project_style = case3DDetailBean.getProject_style();
        if (style.containsKey(project_style)) {
            tvCustomerHomeStyle.setText(style.get(project_style));
        }

        tvThumbUp.setText(getString(R.string.thumbup_conunt) + case3DDetailBean.getFavorite_count());
        tvheadThumbUp.setText(getString(R.string.thumbup_conunt) + case3DDetailBean.getFavorite_count());
        ivConsumeHomeDesigner.setText(case3DDetailBean.getDesigner_info().getFirst_name()
        );
        ImageUtils.displayIconImage(case3DDetailBean.getDesigner_info().getAvatar(), pivImgCustomerHomeHeader);
    }

    /**
     * 关注或者取消关注设计师
     *
     * @param followsType true 为关注，false 取消关注
     */
    private void followingOrUnFollowedDesigner(final boolean followsType) {
        CustomProgress.show(this, "", false, null);
        String followed_member_id = designer_id;
        String followed_member_uid = mHs_uid;
        MPServerHttpManager.getInstance().followingOrUnFollowedDesigner(member_id, followed_member_id, followed_member_uid, followsType, new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                String followingOrUnFollowedDesignerString = GsonUtil.jsonToString(jsonObject);
                FollowingDesignerBean followingDesignerBean = GsonUtil.jsonToBean(followingOrUnFollowedDesignerString, FollowingDesignerBean.class);

                setFollowedTitle(followsType);

//                if (followsType) {
//                    MyToast.show(CaseLibraryDetail3DActivity.this, UIUtils.getString(R.string.attention_success));
//                    /// TODO 临时处理，正常情况下，当点击关注时候，后台这个字段变成true .
//                    mDesignerInfo.setIs_following(true);
//                } else {
//                    mDesignerInfo.setIs_following(false);
//                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CustomProgress.cancelDialog();
                setFollowedTitle(true);
                MPNetworkUtils.logError(TAG, volleyError);
            }
        });
    }

    /**
     * 取消关注提示框
     */
    private void initUnFollowedAlertView() {
        unFollowedAlertView = new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.attention_tip_message_first) + mNickName + UIUtils.getString(R.string.attention_tip_message_last),
                UIUtils.getString(R.string.following_cancel), null,
                new String[]{UIUtils.getString(R.string.following_sure)},
                CaseLibraryDetail3DActivity.this,
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
     * 设置title栏，关注的显示问题
     * true :关注状态-->对应取消关注
     * false : 取消关注-->对应关注
     */
    private void setFollowedTitle(boolean is_following) {
        if (is_following) {
            mIvFollowedDesigner.setTextColor(UIUtils.getColor(R.color.actionsheet_blue));
            mIvFollowedDesigner.setBackground(UIUtils.getDrawable(R.drawable.textview_unfollow_bg));
            mIvFollowedDesigner.setText(UIUtils.getString(R.string.attention_cancel));

        } else {
            mIvFollowedDesigner.setTextColor(UIUtils.getColor(R.color.white));
            mIvFollowedDesigner.setBackground(UIUtils.getDrawable(R.drawable.textview_follow_bg));
            mIvFollowedDesigner.setText(UIUtils.getString(R.string.attention_sure_add));
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        memberEntity = AdskApplication.getInstance().getMemberEntity();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (null != memberEntity) {
            getThumbUp(case3DDetailBean.getDesign_asset_id());
        }
        showOrHideChatBtn();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPosY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mCurPosY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                if (mCurPosY - mPosY > 0 && (Math.abs(mCurPosY - mPosY) > 18)) {
                    rlCaseLibraryBottom.setAnimation(AnimationUtil.moveToViewLocation());
                } else if (mCurPosY - mPosY < 0 && (Math.abs(mCurPosY - mPosY) > 18)) {
                    rlCaseLibraryBottom.setAnimation(AnimationUtil.moveToViewBottom());
                }
                break;
        }
        return false;
    }


    /**
     * 根据登录用户，显示还是隐藏聊天按钮
     */
    private void showOrHideChatBtn() {

        if (null != memberEntity) {
            member_type = memberEntity.getMember_type();
            if (member_type.equals(Constant.UerInfoKey.CONSUMER_TYPE)) {
                ivCustomerIm.setVisibility(View.VISIBLE);
            } else {
                ivCustomerIm.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position != 2) {
            Intent intent = new Intent(this, CaseLibraryDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constant.CaseLibraryDetail.CASE_DETAIL_BEAN, case3DDetailBean);
            bundle.putInt(Constant.CaseLibraryDetail.CASE_DETAIL_POSTION, position);
            bundle.putInt("moveState",1); // 代表从3D传过去的
            intent.putExtras(bundle);
            this.startActivity(intent);
        }
    }
}
