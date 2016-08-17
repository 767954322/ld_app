package com.autodesk.shejijia.consumer.home.homepage.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.activity.SeekDesignerDetailActivity;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.consumer.activity.AttentionActivity;
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
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.tools.CaptureQrActivity;
import com.autodesk.shejijia.shared.components.common.tools.about.MPMoreSettingActivity;
import com.autodesk.shejijia.shared.components.common.uielements.MyToast;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.shared.components.common.utility.CommonUtils;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
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
        //  mLlPersonalDesignerDecorate = (LinearLayout) findViewById(R.id.ll_personal_designer_decorate);
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
    }

    @Override
    protected void initListener() {
        super.initListener();
        mPolygonImageView.setOnClickListener(this);
        mLlSetMeal.setOnClickListener(this);
        mLlNoAttestation.setOnClickListener(this);
        mLlPersonalDesignerInfo.setOnClickListener(this);
        mLlPersonalDesignerManage.setOnClickListener(this);
        // mLlPersonalDesignerDecorate.setOnClickListener(this);
        mLlPersonalDesignerProperty.setOnClickListener(this);
        mLlPersonalDesignerMore.setOnClickListener(this);
        mTvDesignerNickname.setOnClickListener(this);
        mLlPersonalDesignerMsgCenter.setOnClickListener(this);

        mTvDesignerAttention.setOnClickListener(this);
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
                /// 判断设计师的类型,快屋设计师有北舒,乐屋设计师IM有扫一扫 .
                int isLoho = designerInfoDetails.getDesigner().getIs_loho();

                if (isLoho == IS_BEI_SHU) {
                    mLlSetMeal.setVisibility(View.VISIBLE);
                } else {
                    mLlSetMeal.setVisibility(View.GONE);
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, DesignerPersonalCenterActivity.this,
                        AlertView.Style.Alert, null).show();
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
                nick_name = mConsumerEssentialInfoEntity.getNick_name();
                mTvDesignerNickname.setText(nick_name);
                if (mConsumerEssentialInfoEntity.getAvatar().isEmpty()) {
                    mPolygonImageView.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_default_avator));
                } else {
                    ImageUtils.displayAvatarImage(mConsumerEssentialInfoEntity.getAvatar(), mPolygonImageView);
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                if (DesignerPersonalCenterActivity.this != null) {
                    new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, DesignerPersonalCenterActivity.this,
                            AlertView.Style.Alert, null).show();
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
                audit_status = realName.getAudit_status();
                if ("2".equals(audit_status)) {
                    mLlNoAttestation.setVisibility(View.GONE);
                    mImgCertificateIcon.setVisibility(View.VISIBLE);
                } else {
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
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                if (DesignerPersonalCenterActivity.this != null) {
                    new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, DesignerPersonalCenterActivity.this,
                            AlertView.Style.Alert, null).show();
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
//                Intent aaIntent = new Intent(DesignerPersonalCenterActivity.this,CertificationActivity.class);
//                if (audit_status == null) {
//                    aaIntent.putExtra(AUDIT_STATUS, "");
//                } else {
//                    aaIntent.putExtra(AUDIT_STATUS, audit_status);
//                }
//                startActivity(aaIntent);
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
                CommonUtils.launchActivity(DesignerPersonalCenterActivity.this, MyBidActivity.class);
                break;

//            case R.id.ll_personal_designer_decorate:    /// 我的订单 .
//                if (null != designerInfoDetails && null != designerInfoDetails.getDesigner()) {
//                    if (designerInfoDetails.getDesigner().getIs_loho() == IS_BEI_SHU) {
//                        /// 北舒 .
//                        CommonUtils.launchActivity(DesignerPersonalCenterActivity.this, DesignerOrderBeiShuActivity.class);
//                    } else {
//                        CommonUtils.launchActivity(DesignerPersonalCenterActivity.this, DesignerOrderActivity.class);
//                    }
//                } else {
//                    CommonUtils.launchActivity(DesignerPersonalCenterActivity.this, DesignerOrderActivity.class);
//                }
//                break;

            case R.id.ll_personal_designer_property:    /// 查看我的资产页面.
                CommonUtils.launchActivity(DesignerPersonalCenterActivity.this, MyPropertyActivity.class);
                break;

            case R.id.ll_personal_designer_more:    /// 查看更多设置页面.
                Intent intent1 = new Intent(DesignerPersonalCenterActivity.this, MPMoreSettingActivity.class);
                startActivityForResult(intent1, MORE_LOGOUT);
                break;

            case R.id.tv_designer_nickname: /// 跳转到登陆注册页面.
                if (null == memberEntity) {
                    AdskApplication.getInstance().doLogin(DesignerPersonalCenterActivity.this);
                }
                break;

            case R.id.ll_personal_designer_attention: /// 我的关注 .

                if (memberEntity != null) {
                    Intent intent2 = new Intent(DesignerPersonalCenterActivity.this, AttentionActivity.class);
                    startActivity(intent2);
                } else {
                    AdskApplication.getInstance().doLogin(this);
                }
                break;

            case R.id.ll_personal_designer_msg_center:/// 消息中心页面.
                MyToast.show(DesignerPersonalCenterActivity.this, UIUtils.getString(R.string.functional_development));
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
                    //startActivity(new Intent(DesignerPersonalCenterActivity.this, DesignerPersonalCenterActivity.this.getClass()));
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
            getRealNameAuditStatus(designer_id, hs_uid);
        } else {
            mTvDesignerNickname.setText(R.string.no_data);
            mPolygonImageView.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_default_avator));
        }
    }

    private static final int IS_BEI_SHU = 1;
    private static final int QR = 1;
    private static final int MORE_LOGOUT = 0;

    /// 控件.
    private LinearLayout mLlSetMeal;
    private LinearLayout mLlPersonalDesignerManage;
    private LinearLayout mLlPersonalDesignerProperty;
    private LinearLayout mLlPersonalDesignerMore;
    //   private LinearLayout mLlPersonalDesignerDecorate;
    private LinearLayout mLlPersonalDesignerInfo;
    private LinearLayout mLlPersonalDesignerMsgCenter;
    private LinearLayout mLlNoAttestation;
    private TextView mTvDesignerNickname;
    private TextView mTvAuditStatusAgo;
    private ImageView mImgCertificateIcon;
    private PolygonImageView mPolygonImageView;
    private LinearLayout mTvDesignerAttention;

    /// 变量.
    private String designer_id, hs_uid;
    private String nick_name;
    private String audit_status;
    /// 类.
    private MemberEntity memberEntity;
    private DesignerInfoDetails designerInfoDetails;
    private ConsumerEssentialInfoEntity mConsumerEssentialInfoEntity;
}
