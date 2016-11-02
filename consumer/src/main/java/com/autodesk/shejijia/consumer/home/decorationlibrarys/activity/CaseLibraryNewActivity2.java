package com.autodesk.shejijia.consumer.home.decorationlibrarys.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.activity.SeekDesignerDetailActivity;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.FollowingDesignerBean;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.ImagesBean;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter.CaseLibrary2DAdapter;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.CaseDetailBean;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.DesignerInfoBean;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.consumer.utils.ToastUtil;
import com.autodesk.shejijia.consumer.utils.WeiXinUtils;
import com.autodesk.shejijia.consumer.wxapi.SendWXShared;
import com.autodesk.shejijia.shared.components.common.appglobal.ApiManager;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.consumer.uielements.MyToast;
import com.autodesk.shejijia.shared.components.common.uielements.WXSharedPopWin;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.consumer.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.LoginUtils;
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

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @version :
 * @描述 :  案例库详情界面
 * @Author :lizhipeng
 * @创建日期 :2016.9.8
 */
public class CaseLibraryNewActivity2 extends NavigationBarActivity implements View.OnClickListener {
    private static final String TAG = "2d";

    private String case_id;
    private RecyclerView mRecyclerView;

    private ImageView mHeadIv;

    private LinearLayout mLlShare;

    private LinearLayout mLlPraise;
    private ImageView mPraiseIv;
    private TextView mPraiseTv;

    private PolygonImageView mDesignerProtraitIv;
    private TextView ivConsumeHomeDesigner;
    private ImageView mIvFollowedDesigner;
    private TextView tvCustomerHomeStyle;
    private TextView tvCustomerHomeRoom;
    private TextView tvCustomerHomeArea;
    private ImageView ivCustomerIm;

    private List<ImagesBean> mDatas;
    private ImagesBean mHeadImageBean;

    private CaseDetailBean mCaseDetailBean;
    private DesignerInfoBean mDesignerInfo;

    private CaseLibrary2DAdapter mAdapter;

    private MemberEntity mMemberEntity;
    private WXSharedPopWin takePhotoPopWin;

    private boolean ifIsSharedToFriends = true;
    private String firstCaseLibraryImageUrl;
    private String hs_uid;
    private String designer_id;
    private Map<String, String> roomHall;
    private Map<String, String> style;
    private String member_id;
    private boolean isMemberLike;
    private AlertView unFollowedAlertView;
    private String mNickName;
    private String mHs_uid;
    private int topPosition;
    private String member_type;
    private String mMemberType;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_case_linrary_new_2;
    }


    @Override
    protected void initView() {
        super.initView();

        mHeadIv = (ImageView) findViewById(R.id.case_library_new_2_activity_head_iv);

        mRecyclerView = (RecyclerView) findViewById(R.id.case_library_new_activity_rv);

        mLlShare = (LinearLayout) findViewById(R.id.ll_fenxiang);

        mLlPraise = (LinearLayout) findViewById(R.id.rl_thumb_up);
        mPraiseIv = (ImageView) findViewById(R.id.iv_thumb_up);
        mPraiseTv = (TextView) findViewById(R.id.tv_thumb_up);

        mDesignerProtraitIv = (PolygonImageView) findViewById(R.id.piv_img_customer_home_header);
        ivConsumeHomeDesigner = (TextView) findViewById(R.id.iv_consume_home_designer);
        tvCustomerHomeStyle = (TextView) findViewById(R.id.tv_customer_home_style);
        tvCustomerHomeRoom = (TextView) findViewById(R.id.tv_customer_home_room);
        tvCustomerHomeArea = (TextView) findViewById(R.id.tv_customer_home_area);
        mIvFollowedDesigner = (ImageView) findViewById(R.id.iv_follow_designer);
        ivCustomerIm = (ImageView) findViewById(R.id.img_look_more_detail_chat);
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

        getCaseDetailData(case_id);//获取案例数据

    }


    @Override
    protected void initListener() {
        super.initListener();
        mLlShare.setOnClickListener(this);
        mDesignerProtraitIv.setOnClickListener(this);
        mLlPraise.setOnClickListener(this);
        mIvFollowedDesigner.setOnClickListener(this);
        mHeadIv.setOnClickListener(this);
    }

    /**
     * 获取案例的数据
     *
     * @param case_id 该案例的ID
     */
    public void getCaseDetailData(final String case_id) {
        CustomProgress.show(this, "", false, null);
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String info = GsonUtil.jsonToString(jsonObject);
                mCaseDetailBean = GsonUtil.jsonToBean(info, CaseDetailBean.class);
                setTitleForNavbar(mCaseDetailBean.getTitle());
                updateViewFromCaseDetailData();

                CustomProgress.cancelDialog();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                ApiStatusUtil.getInstance().apiStatuError(volleyError, CaseLibraryNewActivity2.this);
                CustomProgress.cancelDialog();
            }
        };
        MPServerHttpManager.getInstance().getCaseListDetail(case_id, okResponseCallback);
    }

    /**
     * 获取案例的数据，更新页面
     */
    private void updateViewFromCaseDetailData() {
        if (null == mCaseDetailBean) {
            return;
        }
        //获取图片数据
        mDatas = mCaseDetailBean.getImages();
        mDesignerInfo = mCaseDetailBean.getDesigner_info();
        hs_uid = mCaseDetailBean.getHs_designer_uid();
        if (mDesignerInfo == null) {
            return;
        }

        mAdapter = new CaseLibrary2DAdapter(this, R.layout.case_library_new_item_2, mDatas, mCaseDetailBean);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置简介
        String introduction = mCaseDetailBean.getDescription();
        mAdapter.setmContent(introduction);
//        mAdapter.notifyDataSetChanged(mDatas);
        mRecyclerView.setAdapter(mAdapter);

        mNickName = mDesignerInfo.getNick_name();
        mHs_uid = mDesignerInfo.getHs_uid();
        boolean is_following = mDesignerInfo.is_following;
        /**
         * 如果当前没有acs_member_id,就是没有登录，点击跳转到登录页面
         */
        if (null != mMemberEntity) {
            member_id = mMemberEntity.getAcs_member_id();
            getThumbUp(mCaseDetailBean.getId());

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
        designer_id = mDesignerInfo.getDesigner().getAcs_member_id();
        if (mDatas.size() > 0) {
            for (int i = 0; i < mDatas.size(); i++) {
                if (mDatas.get(i).is_primary() == true) {
                    topPosition = i;
                    firstCaseLibraryImageUrl = mDatas.get(i).getFile_url() + Constant.CaseLibraryDetail.JPG;
                    ImageUtils.loadImageIcon(mHeadIv, firstCaseLibraryImageUrl);
                }
            }
            mHeadImageBean = mDatas.get(0);
//            mDatas.remove(0);
        }
        //设置head图片
//        if (mHeadImageBean != null) {
//            ImageUtils.loadImageIcon(mHeadIv, mHeadImageBean.getFile_url() + Constant.CaseLibraryDetail.JPG);
//        } else {
//            mHeadIv.setVisibility(View.GONE);
//        }

        mPraiseTv.setText(getString(R.string.thumbup_conunt)
                + mCaseDetailBean.getFavorite_count());

        //设置设计师头像
        ImageUtils.loadImageIcon(mDesignerProtraitIv, mCaseDetailBean.getDesigner_info().getAvatar());
        ivConsumeHomeDesigner.setText(mCaseDetailBean.getDesigner_info().getFirst_name());
        String project_style = mCaseDetailBean.getProject_style();
        if (style.containsKey(project_style)) {
            tvCustomerHomeStyle.setText(style.get(project_style));
        }
        tvCustomerHomeArea.setText(mCaseDetailBean.getRoom_area() + "m²");
        String room_type = mCaseDetailBean.getRoom_type();
        if (roomHall.containsKey(room_type)) {
            tvCustomerHomeRoom.setText(roomHall.get(room_type));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMemberEntity = AdskApplication.getInstance().getMemberEntity();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_fenxiang://分享
                if (null != mMemberEntity) {

                    if (WeiXinUtils.isWeixinAvilible(CaseLibraryNewActivity2.this)) {
                        if (takePhotoPopWin == null) {
                            takePhotoPopWin = new WXSharedPopWin(this, onClickListener);
                        }
                        takePhotoPopWin.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                        takePhotoPopWin.showAtLocation(findViewById(R.id.main_library), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    } else {
                        ToastUtil.showCustomToast(CaseLibraryNewActivity2.this, getString(R.string.anzhuangweixin));
                    }

                } else {
                    LoginUtils.doLogin(this);
                }
                break;
            case R.id.piv_img_customer_home_header://点击设计师头像，进入详情
                Intent intent = new Intent(CaseLibraryNewActivity2.this, SeekDesignerDetailActivity.class);
                intent.putExtra(Constant.ConsumerDecorationFragment.designer_id, designer_id);
                intent.putExtra(Constant.ConsumerDecorationFragment.hs_uid, hs_uid);
                startActivity(intent);
                break;
            case R.id.rl_thumb_up://点赞
                if (null != mMemberEntity) {
                    mLlPraise.setOnClickListener(null);
//                    llThumbUp.setOnClickListener(null);
                    CustomProgress.show(this, "", false, null);
                    if (mCaseDetailBean != null) {
                        sendThumbUp(mCaseDetailBean.getId());
                    }
                } else {
                    LoginUtils.doLogin(this);
                }
                break;
            case R.id.iv_follow_designer://关注
                if (null != mMemberEntity) {
                    if (null != mCaseDetailBean && null != mCaseDetailBean.getDesigner_info()) {
                        DesignerInfoBean designer_info = mCaseDetailBean.getDesigner_info();
                        boolean is_following = designer_info.is_following;
                        if (TextUtils.isEmpty(member_id)) {
                            LoginUtils.doLogin(this);
                        } else {
                            if (is_following) {
                                unFollowedAlertView.show();
                            } else {
                                followingOrUnFollowedDesigner(true);
                            }
                        }
                    }
                } else {
                    LoginUtils.doLogin(this);
                }
                break;
            case R.id.case_library_new_2_activity_head_iv:
                Intent intent1 = new Intent(this, CaseLibraryDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.CaseLibraryDetail.CASE_DETAIL_BEAN, mCaseDetailBean);
                bundle.putInt(Constant.CaseLibraryDetail.CASE_DETAIL_POSTION, topPosition);
                intent1.putExtras(bundle);
                this.startActivity(intent1);
                break;
            case R.id.img_look_more_detail_chat:    /// 进入聊天页面,如果没有登陆则进入登陆注册页面.
                /**
                 * 聊天,如没有登陆 也会跳入注册登陆页面
                 */
                if (mMemberEntity != null) {
                    mMemberType = mMemberEntity.getMember_type();
                    member_id = mMemberEntity.getAcs_member_id();

                    if (null != mCaseDetailBean) {
                        final String designer_id = mCaseDetailBean.getDesigner_info().getDesigner().getAcs_member_id();
                        final String hs_uid = mCaseDetailBean.getHs_designer_uid();
                        final String receiver_name = mCaseDetailBean.getDesigner_info().getNick_name();
                        final String recipient_ids = member_id + "," + designer_id + "," + ApiManager.getAdmin_User_Id();

                        MPChatHttpManager.getInstance().retrieveMultipleMemberThreads(recipient_ids, 0, 10, new OkStringRequest.OKResponseCallback() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                MPNetworkUtils.logError(TAG, volleyError);
                                ApiStatusUtil.getInstance().apiStatuError(volleyError, CaseLibraryNewActivity2.this);
                            }

                            @Override
                            public void onResponse(String s) {

                                MPChatThreads mpChatThreads = MPChatThreads.fromJSONString(s);

                                final Intent intent = new Intent(CaseLibraryNewActivity2.this, ChatRoomActivity.class);
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
                                    startActivity(intent);

                                } else {
                                    MPChatHttpManager.getInstance().getThreadIdIfNotChatBefore(designer_id,member_id , new OkStringRequest.OKResponseCallback() {
                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {
                                            MPNetworkUtils.logError(TAG, volleyError);
                                            ApiStatusUtil.getInstance().apiStatuError(volleyError, CaseLibraryNewActivity2.this);
                                        }

                                        @Override
                                        public void onResponse(String s) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(s);
                                                String thread_id = jsonObject.getString("thread_id");
                                                intent.putExtra(ChatRoomActivity.ASSET_ID, "");
                                                intent.putExtra(ChatRoomActivity.RECIEVER_HS_UID, hs_uid);
                                                intent.putExtra(ChatRoomActivity.THREAD_ID, thread_id);
                                                startActivity(intent);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }

                            }
                        });
                    } else {
                        new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, CaseLibraryNewActivity2.this,
                                AlertView.Style.Alert, null).show();
                    }
                } else {
                    LoginUtils.doLogin(this);
                }
                break;
            default:
                break;
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String webUrl = ApiManager.getHtml5Url(case_id);
            //TODO 后台现在只有这个环境的分享链接 别的环境还没有  暂时写死    by willson
//            String webUrl ="http://uat-www.gdfcx.net/share/2dcase.html?caseid="+case_id;
            switch (v.getId()) {

                case R.id.tv_wx_shared_tofriends:
                    ifIsSharedToFriends = true;
                    try {
                        SendWXShared.sendProjectToWX(CaseLibraryNewActivity2.this, webUrl, mCaseDetailBean.getTitle(), mCaseDetailBean.getDescription() + " ", ifIsSharedToFriends, firstCaseLibraryImageUrl);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.tv_wx_shared_tocircleof_friends:
                    ifIsSharedToFriends = false;
                    try {
                        SendWXShared.sendProjectToWX(CaseLibraryNewActivity2.this, webUrl, mCaseDetailBean.getTitle(), mCaseDetailBean.getDescription() + " ", ifIsSharedToFriends, firstCaseLibraryImageUrl);
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
                CustomProgress.cancelDialog();
//                rlThumbUp.setOnClickListener(null);
//                llThumbUp.setOnClickListener(null);

                ToastUtil.showCustomToast(CaseLibraryNewActivity2.this, getString(R.string.dianzhangchenggong));
                mPraiseIv.setBackgroundResource(R.mipmap.yidianzan_ico);
//                ivHeadThumbUp.setBackgroundResource(R.mipmap.yidianzan_ico);
                mPraiseTv.setText(getString(R.string.thumbup_conunt) + (mCaseDetailBean.getFavorite_count() + 1));
//                tvheadThumbUp.setText(getString(R.string.thumbup_conunt) + (caseDetailBean.getFavorite_count() + 1));
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CustomProgress.cancelDialog();
                mLlPraise.setOnClickListener(CaseLibraryNewActivity2.this);
//                llThumbUp.setOnClickListener(CaseLibraryNewActivity.this);
                MPNetworkUtils.logError(TAG, volleyError);
                ApiStatusUtil.getInstance().apiStatuError(volleyError, CaseLibraryNewActivity2.this);
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
                try {
                    isMemberLike = jsonObject.getBoolean("is_member_like");
                    if (isMemberLike) {
                        mLlPraise.setOnClickListener(null);
//                        llThumbUp.setOnClickListener(null);
                        mPraiseIv.setBackgroundResource(R.mipmap.yidianzan_ico);
//                        ivHeadThumbUp.setBackgroundResource(R.mipmap.yidianzan_ico);
                    }

                    // int count = jsonObject.getInt("count");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                ApiStatusUtil.getInstance().apiStatuError(volleyError, CaseLibraryNewActivity2.this);
            }
        };
        MPServerHttpManager.getInstance().getThumbUpRequest(assetId, okResponseCallback);
    }

    /**
     * 取消关注提示框
     */
    private void initUnFollowedAlertView() {
        unFollowedAlertView = new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.attention_tip_message_first) + mNickName + UIUtils.getString(R.string.attention_tip_message_last),
                UIUtils.getString(R.string.following_cancel), null,
                new String[]{UIUtils.getString(R.string.following_sure)},
                CaseLibraryNewActivity2.this,
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
                if (followsType) {
                    MyToast.show(CaseLibraryNewActivity2.this, UIUtils.getString(R.string.attention_success));
                    /// TODO 临时处理，正常情况下，当点击关注时候，后台这个字段变成true .
                    mDesignerInfo.is_following = true;
                } else {
                    mDesignerInfo.is_following = false;
                }
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
     * 设置title栏，关注的显示问题
     * true :关注状态-->对应取消关注
     * false : 取消关注-->对应关注
     */
    private void setFollowedTitle(boolean is_following) {

        if (is_following) {
            mIvFollowedDesigner.setBackground(UIUtils.getDrawable(R.drawable.ic_followed_cancel));

        } else {
            mIvFollowedDesigner.setBackground(UIUtils.getDrawable(R.drawable.ic_followed_sure));
        }
    }

    /**
     * 根据登录用户，显示还是隐藏聊天按钮
     */
    private void showOrHideChatBtn() {

        mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        if (null != mMemberEntity) {
            member_type = mMemberEntity.getMember_type();
            if (member_type.equals(Constant.UerInfoKey.CONSUMER_TYPE)) {
                ivCustomerIm.setVisibility(View.VISIBLE);
            } else {
                ivCustomerIm.setVisibility(View.GONE);
            }
        }
    }
}