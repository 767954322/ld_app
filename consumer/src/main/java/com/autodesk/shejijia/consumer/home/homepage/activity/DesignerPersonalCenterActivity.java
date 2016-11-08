package com.autodesk.shejijia.consumer.home.homepage.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.activity.SeekDesignerDetailActivity;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.consumer.activity.AttentionListActivity;
import com.autodesk.shejijia.consumer.personalcenter.consumer.activity.MessageCenterActivity;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.ConsumerEssentialInfoEntity;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.ConsumerQrEntity;
import com.autodesk.shejijia.consumer.personalcenter.designer.activity.AttestationInfoActivity;
import com.autodesk.shejijia.consumer.personalcenter.designer.activity.BeiShuMealActivity;
import com.autodesk.shejijia.consumer.personalcenter.designer.activity.CertificationActivity;
import com.autodesk.shejijia.consumer.personalcenter.designer.activity.DesignerEssentialInfoActivity;
import com.autodesk.shejijia.consumer.personalcenter.designer.activity.MyBidActivity;
import com.autodesk.shejijia.consumer.personalcenter.designer.activity.MyPropertyActivity;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.DesignerInfoDetails;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.RealName;
import com.autodesk.shejijia.consumer.personalcenter.recommend.activity.DcRecommendActivity;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.tools.CaptureQrActivity;
import com.autodesk.shejijia.consumer.tools.about.MPMoreSettingActivity;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.consumer.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.shared.components.common.utility.CommonUtils;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.LoginUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONObject;

import de.greenrobot.event.EventBus;

/**
 * @author yaoxuehua .
 * @version 1.0 .
 * @date 16-7-12
 * @file DesignerOrderBeiShuActivity.java  .
 * @brief 设计师个人中心 .
 */
public class DesignerPersonalCenterActivity extends NavigationBarActivity implements View.OnClickListener {

    private static final int IS_BEI_SHU = 1;
    private int mIsLoho;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_personal_center_designer;
    }

    @Override
    protected void initView() {
        super.initView();
        mLlSetMeal = (LinearLayout) findViewById(R.id.ll_set_meal);
        mLlPersonalDesignerInfo = (LinearLayout) findViewById(R.id.ll_personal_designer_info);
        mLlPersonalDesignerManage = (LinearLayout) findViewById(R.id.ll_personal_designer_manage);
        mLlPersonalDesignerProperty = (LinearLayout) findViewById(R.id.ll_personal_designer_property);
        mLlPersonalDesignerMore = (LinearLayout) findViewById(R.id.ll_personal_designer_more);
        mLlPersonalDesignerMsgCenter = (LinearLayout) findViewById(R.id.ll_personal_designer_msg_center);
        mLlNoAttestation = (LinearLayout) findViewById(R.id.ll_no_attestation);
        mPolygonImageView = (PolygonImageView) findViewById(R.id.ib_designer_photo);
        mTvAuditStatusAgo = (TextView) findViewById(R.id.tv_audit_status_ago);
        mImgCertificateIcon = (ImageView) findViewById(R.id.img_autonym_icon);
        mTvDesignerNickname = (TextView) findViewById(R.id.tv_designer_nickname);

        mTvDesignerAttention = (LinearLayout) findViewById(R.id.ll_personal_designer_attention);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleForNavbar(UIUtils.getString(R.string.designer_personal));
        memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (null == memberEntity) {
            return;
        }
        if (Constant.UerInfoKey.DESIGNER_TYPE.equals(memberEntity.getMember_type())) {
            designer_id = memberEntity.getAcs_member_id();
            hs_uid = memberEntity.getHs_uid();

            getDesignerInfoData(designer_id, hs_uid);
            getMemberInfoData(designer_id);

            /// TODO 暂时去掉实名认证功能，后续模块增加 .
            getRealNameAuditStatus(designer_id, hs_uid);
        } else {
            mTvDesignerNickname.setText(R.string.no_data);
            mPolygonImageView.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_default_avator));
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mPolygonImageView.setOnClickListener(this);
        mLlSetMeal.setOnClickListener(this);
        mLlNoAttestation.setOnClickListener(this);
        mLlPersonalDesignerInfo.setOnClickListener(this);
        mLlPersonalDesignerManage.setOnClickListener(this);
        mLlPersonalDesignerProperty.setOnClickListener(this);
        mLlPersonalDesignerMore.setOnClickListener(this);
        mTvDesignerNickname.setOnClickListener(this);
        mLlPersonalDesignerMsgCenter.setOnClickListener(this);

        mTvDesignerAttention.setOnClickListener(this);
        findViewById(R.id.ll_personal_recommend).setOnClickListener(this);
    }

    /**
     * 设计师个人信息
     *
     * @param designer_id
     * @param hs_uid
     */
    public void getDesignerInfoData(String designer_id, String hs_uid) {
        final long start = System.currentTimeMillis();
        MPServerHttpManager.getInstance().getDesignerInfoData(designer_id, hs_uid, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String jsonString = GsonUtil.jsonToString(jsonObject);
                Log.e("----------", jsonString);
                designerInfoDetails = GsonUtil.jsonToBean(jsonString, DesignerInfoDetails.class);
                /// 判断设计师的类型,快屋设计师有北舒,乐屋设计师IM有扫一扫 .
                mIsLoho = designerInfoDetails.getDesigner().getIs_loho();

                if (mIsLoho == IS_BEI_SHU) {
                    mLlSetMeal.setVisibility(View.VISIBLE);
                } else {
                    mLlSetMeal.setVisibility(View.GONE);
                }
                Log.i("DesignerCenter", "设计师个人信息接口时间：" + (System.currentTimeMillis() - start));
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                ApiStatusUtil.getInstance().apiStatuError(volleyError, DesignerPersonalCenterActivity.this);
            }
        });
    }

    /**
     * 获取设计师会员信息
     *
     * @param member_id
     */
    public void getMemberInfoData(String member_id) {
        MPServerHttpManager.getInstance().getConsumerInfoData(member_id, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String jsonString = GsonUtil.jsonToString(jsonObject);
                mConsumerEssentialInfoEntity = GsonUtil.jsonToBean(jsonString, ConsumerEssentialInfoEntity.class);

                if (null != mConsumerEssentialInfoEntity) {
                    nick_name = mConsumerEssentialInfoEntity.getNick_name();
                    mTvDesignerNickname.setText(nick_name);
                    if (mConsumerEssentialInfoEntity.getAvatar().isEmpty()) {
                        mPolygonImageView.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_default_avator));
                    } else {
                        ImageUtils.displayAvatarImage(mConsumerEssentialInfoEntity.getAvatar(), mPolygonImageView);
                    }
                } else {
                    nick_name = "";
                }


            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                if (DesignerPersonalCenterActivity.this != null) {
                    ApiStatusUtil.getInstance().apiStatuError(volleyError, DesignerPersonalCenterActivity.this);
                }
            }
        });
    }

    /**
     * 是否进行了实名认证
     *
     * @param designer_id
     * @param hs_uid
     */
    public void getRealNameAuditStatus(String designer_id, String hs_uid) {
        MPServerHttpManager.getInstance().getRealNameAuditStatus(designer_id, hs_uid, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String auditInfo = GsonUtil.jsonToString(jsonObject);
                RealName realName = GsonUtil.jsonToBean(auditInfo, RealName.class);

                if (null != realName) {
                    audit_status = realName.getAudit_status();
                    if ("2".equals(audit_status)) {
                        mLlNoAttestation.setVisibility(View.GONE);
                        mImgCertificateIcon.setVisibility(View.VISIBLE);
                    } /*else {
                    mLlNoAttestation.setVisibility(View.VISIBLE);
                    if (null == audit_status) {
                        mTvAuditStatusAgo.setText(UIUtils.getString(R.string.please_go_to_certification));
                    } else if ("0".equals(audit_status)) {
                        mTvAuditStatusAgo.setText(UIUtils.getString(R.string.please_patient));
                    } else if ("1".equals(audit_status)) {
                        mTvAuditStatusAgo.setText(UIUtils.getString(R.string.authentication_failed));
                    } else if ("3".equals(audit_status)) {
                        mTvAuditStatusAgo.setText(UIUtils.getString(R.string.authentication_failed));
                    }
                }*/
                }

            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                if (DesignerPersonalCenterActivity.this != null) {
                    ApiStatusUtil.getInstance().apiStatuError(volleyError, DesignerPersonalCenterActivity.this);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_designer_photo:    /// 进入设计师个人信息页面.
                if (null != memberEntity && Constant.UerInfoKey.DESIGNER_TYPE.equals(memberEntity.getMember_type()) && designerInfoDetails != null) {
                    Intent intent = new Intent(DesignerPersonalCenterActivity.this, DesignerEssentialInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.DesignerCenterBundleKey.DESIGNER_ID, designer_id);
                    bundle.putString(Constant.DesignerCenterBundleKey.HS_UID, hs_uid);
                    bundle.putSerializable(Constant.DesignerCenterBundleKey.MEMBER_INFO, mConsumerEssentialInfoEntity);
                    bundle.putSerializable(Constant.DesignerCenterBundleKey.HOUSE_COST, designerInfoDetails);

                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
            case R.id.ll_no_attestation:    /// 进入是实名认证页面.
                if (audit_status == null) {
                    CommonUtils.launchActivity(DesignerPersonalCenterActivity.this, CertificationActivity.class);
                } else if (audit_status.equals(Constant.NumKey.ZERO)) {
                    Intent intent = new Intent(DesignerPersonalCenterActivity.this, AttestationInfoActivity.class);
                    intent.putExtra(Constant.DesignerCenterBundleKey.AUDIT_STATUS, audit_status);
                    startActivity(intent);
                } else {
                    CommonUtils.launchActivity(DesignerPersonalCenterActivity.this, CertificationActivity.class);
                }
                break;

            case R.id.ll_set_meal:  /// 北舒套餐,扫描二维码页面 .
                scanner();
                break;

            case R.id.ll_personal_designer_info:    /// 查看设计师详情页面.
                Intent intent = new Intent(DesignerPersonalCenterActivity.this, SeekDesignerDetailActivity.class);
                if (designerInfoDetails != null) {
                    String hs_uid = designerInfoDetails.getHs_uid();
                    String designer_id = memberEntity.getAcs_member_id();
                    intent.putExtra(Constant.ConsumerDecorationFragment.designer_id, designer_id);
                    intent.putExtra(Constant.ConsumerDecorationFragment.hs_uid, hs_uid);
                    startActivity(intent);
                }
                break;

            case R.id.ll_personal_designer_manage:/// 查看我的应标页面.
                Intent bidIntent = new Intent(DesignerPersonalCenterActivity.this, MyBidActivity.class);
                startActivity(bidIntent);
                break;


            case R.id.ll_personal_designer_property:    /// 查看我的资产页面.
                CommonUtils.launchActivity(DesignerPersonalCenterActivity.this, MyPropertyActivity.class);
                break;

            case R.id.ll_personal_designer_more:    /// 查看更多设置页面.
                Intent intent1 = new Intent(DesignerPersonalCenterActivity.this, MPMoreSettingActivity.class);
                startActivityForResult(intent1, MORE_LOGOUT);
                break;

            case R.id.tv_designer_nickname: /// 跳转到登陆注册页面.
                if (null == memberEntity) {
                    LoginUtils.doLogin(DesignerPersonalCenterActivity.this);
                }
                break;

            case R.id.ll_personal_designer_attention: /// 我的关注 .

                if (memberEntity != null) {
                    Intent intent2 = new Intent(DesignerPersonalCenterActivity.this, AttentionListActivity.class);
                    startActivity(intent2);
                } else {
                    LoginUtils.doLogin(this);
                }
                break;

            case R.id.ll_personal_designer_msg_center:/// 消息中心页面.
                Intent intent_messagecenter = new Intent(this, MessageCenterActivity.class);
                intent_messagecenter.putExtra(Constant.MessageCenterActivityKey.MESSAGE_TYPE, Constant.MessageCenterActivityKey.SYSTEM_MSG);
                startActivity(intent_messagecenter);
                break;

            case R.id.ll_personal_recommend:/// 消息中心页面.
                DcRecommendActivity.jumpTo(this, true);
                break;
        }
    }

    /**
     * Scan the qr code related operations .
     */
    private void scanner() {
        if (checkCameraHardware(this)) {
            Intent intent = new Intent(DesignerPersonalCenterActivity.this, CaptureQrActivity.class);
            startActivityForResult(intent, QR);/// Jump to the qr code scanning page.
        } else {
            new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.camera_is_not_available), null, null, new String[]{UIUtils.getString(R.string.sure)}, DesignerPersonalCenterActivity.this, AlertView.Style.Alert, null).show();
        }
    }

    /**
     * 检查相机是否可用
     *
     * @param context 上下文对象
     * @return 相机可用与否
     */
    private boolean checkCameraHardware(Context context) {
        // this device has a camera
        // no camera on this device
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    /**
     * 接收返回来的数据
     *
     * @param requestCode 请求码
     * @param resultCode  　结果码
     * @param data        　数据
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case QR:
                    /// [1]Access to scan the information.
                    Bundle bundle = data.getExtras();
                    String scanResult = bundle.getString(Constant.QrResultKey.SCANNER_RESULT);
                    if (scanResult.contains(Constant.ConsumerDecorationFragment.hs_uid) && scanResult.contains(Constant.DesignerCenterBundleKey.MEMBER)) {
                        ConsumerQrEntity consumerQrEntity = GsonUtil.jsonToBean(scanResult, ConsumerQrEntity.class);
                        /// [2]send data to where need .
                        if (null != consumerQrEntity && !TextUtils.isEmpty(consumerQrEntity.getName())) {
                            EventBus.getDefault().postSticky(consumerQrEntity);
                            Intent intent = new Intent(DesignerPersonalCenterActivity.this, BeiShuMealActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.unable_create_beishu_meal), null, null, new String[]{UIUtils.getString(R.string.sure)}, DesignerPersonalCenterActivity.this, AlertView.Style.Alert, null).show();
                    }
                    break;

                case MORE_LOGOUT:
                    finish();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != memberEntity && Constant.UerInfoKey.DESIGNER_TYPE.equals(memberEntity.getMember_type())) {
            designer_id = memberEntity.getAcs_member_id();
            hs_uid = memberEntity.getHs_uid();
            getDesignerInfoData(designer_id, hs_uid);
            getMemberInfoData(designer_id);
            /// TODO 暂时去掉实名认证功能，后续模块增加 .
            getRealNameAuditStatus(designer_id, hs_uid);
        } else {
            mTvDesignerNickname.setText(R.string.no_data);
            mPolygonImageView.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_default_avator));
        }
    }

    private static final int QR = 1;
    private static final int MORE_LOGOUT = 0;

    private LinearLayout mLlSetMeal;
    private LinearLayout mLlPersonalDesignerManage;
    private LinearLayout mLlPersonalDesignerProperty;
    private LinearLayout mLlPersonalDesignerMore;
    private LinearLayout mLlPersonalDesignerInfo;
    private LinearLayout mLlPersonalDesignerMsgCenter;
    private LinearLayout mLlNoAttestation;
    private TextView mTvDesignerNickname;
    private TextView mTvAuditStatusAgo;
    private ImageView mImgCertificateIcon;
    private PolygonImageView mPolygonImageView;
    private LinearLayout mTvDesignerAttention;

    private String designer_id, hs_uid;
    private String nick_name;
    private String audit_status;
    private MemberEntity memberEntity;
    private DesignerInfoDetails designerInfoDetails;
    private ConsumerEssentialInfoEntity mConsumerEssentialInfoEntity;
}
