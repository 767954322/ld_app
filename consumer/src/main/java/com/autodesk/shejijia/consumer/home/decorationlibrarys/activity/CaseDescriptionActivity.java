package com.autodesk.shejijia.consumer.home.decorationlibrarys.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.components.common.appglobal.ApiManager;
import com.autodesk.shejijia.shared.components.common.appglobal.UrlMessagesContants;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.activity.SeekDesignerDetailActivity;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.CaseDetailBean;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.im.activity.ChatRoomActivity;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThread;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThreads;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatUtility;
import com.autodesk.shejijia.shared.components.im.manager.MPChatHttpManager;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.common.uielements.viewgraph.PolygonImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 2016/3/17 0017 14:58 .
 * @file CaseDescriptionActivity  .
 * @brief 作品介绍 .
 */
public class CaseDescriptionActivity extends NavigationBarActivity implements View.OnClickListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_look_more_case_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        mPolygonImageView = (PolygonImageView) findViewById(R.id.piv_look_more_detail_head);
        mTvName = (TextView) findViewById(R.id.tv_look_more_detail_name);
        mTvStyle = (TextView) findViewById(R.id.tv_look_more_detail_style);
        mTvLivingRoom = (TextView) findViewById(R.id.tv_look_more_detail_living_room);
        mTvArea = (TextView) findViewById(R.id.tv_look_more_detail_area);
        mTvIntroduction = (TextView) findViewById(R.id.tv_look_more_detail_introduction);
        mIvCase = (ImageView) findViewById(R.id.img_look_more_detail_case);
        mIvFinish = (ImageView) findViewById(R.id.img_look_more_detail_finish);
        mIvChat = (ImageView) findViewById(R.id.img_look_more_detail_chat);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        caseDetailBean = (CaseDetailBean) getIntent().getSerializableExtra(Constant.CaseLibraryDetail.CASE_DETAIL_BEAN);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        showOrHideChatBtn();

        if (caseDetailBean != null && caseDetailBean.getDesigner_info() != null) {
            designer_id = caseDetailBean.getDesigner_info().getDesigner().getAcs_member_id();
        }
        if (caseDetailBean != null){
            hs_uid = caseDetailBean.getHs_designer_uid();
            /// 设置title .
            String title = caseDetailBean.getTitle();
            title = TextUtils.isEmpty(title) ? UIUtils.getString(R.string.data_null) : title;
            setTitleForNavbar(title);
            updateViewFromDate();
        }



    }

    /// 监听　.
    @Override
    protected void initListener() {
        super.initListener();
        mIvFinish.setOnClickListener(this);
        mIvCase.setOnClickListener(this);
        mPolygonImageView.setOnClickListener(this);
        mIvChat.setOnClickListener(this);
    }

    /// 监听事件.
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_look_more_detail_finish:  /// 查看完成.
                finish();
                overridePendingTransition(0, R.anim.activity_close);
                break;

            case R.id.img_look_more_detail_case:/// 查看大图.
                String url = null;
                if (null != caseDetailBean && null != caseDetailBean.getImages() && null != caseDetailBean.getImages().get(0)) {
                    url = caseDetailBean.getImages().get(0).getFile_url() + Constant.CaseLibraryDetail.JPG;
                }
                intent = new Intent(CaseDescriptionActivity.this, GalleryUrlActivity.class);
                intent.putExtra(Constant.CaseLibraryDetail.CASE_URL, url);
                startActivity(intent);
                overridePendingTransition(R.anim.my_scale_action, R.anim.my_alpha_action);
                break;

            case R.id.piv_look_more_detail_head:    /// 进入设计师详情页面.
                intent = new Intent(CaseDescriptionActivity.this, SeekDesignerDetailActivity.class);
                intent.putExtra(Constant.ConsumerDecorationFragment.designer_id, designer_id);
                intent.putExtra(Constant.ConsumerDecorationFragment.hs_uid, hs_uid);
                startActivity(intent);
                break;

            case R.id.img_look_more_detail_chat:    /// 进入聊天页面,如果没有登陆则进入登陆注册页面.
                if (memberEntity != null) {
                    member_id = memberEntity.getAcs_member_id();
                    mMemberType = memberEntity.getMember_type();
                    final String designer_id = caseDetailBean.getDesigner_info().getDesigner().getAcs_member_id();
                    final String hs_uid = caseDetailBean.getHs_designer_uid();
                    final String receiver_name = caseDetailBean.getDesigner_info().getNick_name();
                    final String recipient_ids = member_id + "," + designer_id + "," + ApiManager.getAdmin_User_Id(ApiManager.RUNNING_DEVELOPMENT);

                    MPChatHttpManager.getInstance().retrieveMultipleMemberThreads(recipient_ids, 0, 10, new OkStringRequest.OKResponseCallback() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            MPNetworkUtils.logError(TAG, volleyError);
                        }

                        @Override
                        public void onResponse(String s) {
                            MPChatThreads mpChatThreads = MPChatThreads.fromJSONString(s);

                            final Intent intent = new Intent(CaseDescriptionActivity.this, ChatRoomActivity.class);
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
                                CaseDescriptionActivity.this.startActivity(intent);

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
                                            CaseDescriptionActivity.this.startActivity(intent);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }

                        }

                    });
                } else {
                    AdskApplication.getInstance().doLogin(this);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 根据传递的数据，填充布局
     */
    private void updateViewFromDate() {
        String project_style = caseDetailBean.getProject_style();
        String room_type = caseDetailBean.getRoom_type();
        String room_area = caseDetailBean.getRoom_area();
        Map<String, String> style = AppJsonFileReader.getStyle(this);
        Map<String, String> roomMap = AppJsonFileReader.getRoomHall(this);
        Map<String, String> area = AppJsonFileReader.getArea(this);

        if (null == caseDetailBean || caseDetailBean.getImages().size() == 0) {
            mIvCase.setImageResource(R.drawable.common_case_icon);
            mIvCase.setEnabled(false);
        } else {
            ImageUtils.loadImage(mIvCase, caseDetailBean.getImages().get(0).getFile_url() + Constant.CaseLibraryDetail.JPG);
        }

        if (null != caseDetailBean && null != caseDetailBean.getDesigner_info() && null != caseDetailBean.getDesigner_info().getAvatar()) {
            ImageUtils.displayAvatarImage(caseDetailBean.getDesigner_info().getAvatar(), mPolygonImageView);
        } else {
            mIvCase.setImageResource(R.drawable.icon_commen_head_normal);
        }

        if (null != caseDetailBean.getDesigner_info() && null != caseDetailBean.getDesigner_info().getNick_name()) {
            mTvName.setText(caseDetailBean.getDesigner_info().getNick_name());
        } else {
            mTvName.setText(UIUtils.getString(R.string.data_null));
        }

        if (style.containsKey(project_style)) {
            mTvStyle.setText(style.get(project_style));
        } else {
            mTvStyle.setText(caseDetailBean.getProject_style());
        }

        if (null != room_type) {
            if (roomMap.containsKey(room_type)) {
                mTvLivingRoom.setText(roomMap.get(room_type));
            } else {
                mTvLivingRoom.setText(caseDetailBean.getRoom_type());
            }
        } else {
            mTvLivingRoom.setText(UIUtils.getString(R.string.data_null));
        }

        if (area.containsKey(room_area)) {
            mTvArea.setText(area.get(room_area) + "m²");
        } else {
            mTvArea.setText(caseDetailBean.getRoom_area() + "m²");
        }

        if (null != caseDetailBean.getDescription()) {
            mTvIntroduction.setText("\u3000\u3000" + caseDetailBean.getDescription());
        } else {
            mTvIntroduction.setText("\u3000\u3000" + UIUtils.getString(R.string.data_null));
        }
    }

    /**
     * 根据登录用户，显示还是隐藏聊天按钮
     */
    private void showOrHideChatBtn() {
        memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (null != memberEntity) {
            member_type = memberEntity.getMember_type();
            if (member_type.equals(Constant.UerInfoKey.CONSUMER_TYPE)) {
                mIvChat.setVisibility(View.VISIBLE);
            } else {
                mIvChat.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showOrHideChatBtn();
        updateViewFromDate();
    }

    /// 控件.
    private ImageView mIvCase;
    private PolygonImageView mPolygonImageView;
    private TextView mTvName;
    private TextView mTvLivingRoom;
    private TextView mTvStyle;
    private TextView mTvArea;
    private TextView mTvIntroduction;
    private ImageView mIvChat;
    private ImageView mIvFinish;

    /// 变量.
    private String member_type;
    private Intent intent;
    private String hs_uid;
    private String designer_id;
    private String member_id;
    private String mMemberType;

    /// 集合,类.
    private MemberEntity memberEntity;
    private CaseDetailBean caseDetailBean;
}
