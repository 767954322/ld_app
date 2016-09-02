package com.autodesk.shejijia.consumer.home.homepage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.consumer.activity.AttentionActivity;
import com.autodesk.shejijia.consumer.personalcenter.consumer.activity.ConsumerEssentialInfoActivity;
import com.autodesk.shejijia.consumer.personalcenter.consumer.activity.IssueDemandActivity;
import com.autodesk.shejijia.consumer.personalcenter.consumer.activity.MessageCenterActivity;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.ConsumerEssentialInfoEntity;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.tools.about.MPMoreSettingActivity;
import com.autodesk.shejijia.shared.components.common.uielements.viewgraph.PolygonImageView;
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
 * @brief 消费者个人中心 .
 */
public class ConsumerPersonalCenterActivity extends NavigationBarActivity implements View.OnClickListener {
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_personal_center_consumer;
    }

    @Override
    protected void initView() {
        super.initView();
        mLlPersonalDemand = (LinearLayout) findViewById(R.id.ll_personal_b_demand);
        mRlPersonalCollect = (RelativeLayout) findViewById(R.id.rl_personal_b_collect);
        mRlPersonalSetting = (RelativeLayout) findViewById(R.id.rl_personal_b_setting);
        mPolygonImageView = (PolygonImageView) findViewById(R.id.ib_personal_b_photo);
        mTvLoginOrRegister = (TextView) findViewById(R.id.tv_loginOrRegister);
        mDesignerAttention = (LinearLayout) findViewById(R.id.ll_personal_designer_attention);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleForNavbar(UIUtils.getString(R.string.designer_personal));
        mTvLoginOrRegister.setText(R.string.no_data);
        mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        if (mMemberEntity == null) {
            return;
        }
        member_id = mMemberEntity.getAcs_member_id();
    }



    @Override
    protected void initListener() {
        super.initListener();
        mLlPersonalDemand.setOnClickListener(this);
        mRlPersonalCollect.setOnClickListener(this);
        mRlPersonalSetting.setOnClickListener(this);
        mPolygonImageView.setOnClickListener(this);
        mDesignerAttention.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_personal_b_demand:     /// 发布装修需求.
                nick_name = TextUtils.isEmpty(nick_name) ? UIUtils.getString(R.string.anonymity) : nick_name;
                Intent intent = new Intent(ConsumerPersonalCenterActivity.this, IssueDemandActivity.class);
                intent.putExtra(Constant.ConsumerPersonCenterFragmentKey.NICK_NAME, nick_name);
                startActivity(intent);
                break;

            case R.id.rl_personal_b_collect:    /// 消息中心.
                Intent intent_messagecenter = new Intent(this, MessageCenterActivity.class);
                startActivity(intent_messagecenter);
                break;

            case R.id.rl_personal_b_setting:    /// 查看更多设置.
                /**
                 * 更多
                 */
                Intent intent1 = new Intent(ConsumerPersonalCenterActivity.this, MPMoreSettingActivity.class);
                this.startActivityForResult(intent1, MORE_LOGOUT);
                break;

            case R.id.ib_personal_b_photo: /// 个人中心基本信息 .
                if (null != mConsumerEssentialInfoEntity) {
                    intent = new Intent(ConsumerPersonalCenterActivity.this, ConsumerEssentialInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constant.ConsumerPersonCenterFragmentKey.CONSUMER_PERSON, mConsumerEssentialInfoEntity);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;

            case R.id.ll_personal_designer_attention: /// 我的关注 .
                if (mMemberEntity != null) {
                    Intent intent2 = new Intent(ConsumerPersonalCenterActivity.this,AttentionActivity.class);
                    startActivity(intent2);
                }else{
                    AdskApplication.getInstance().doLogin(this);
                }
                break;
        }
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
                mConsumerEssentialInfoEntity = GsonUtil.jsonToBean(jsonString, ConsumerEssentialInfoEntity.class);

                updateViewFromData();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                if (ConsumerPersonalCenterActivity.this != null) {
//                    new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, ConsumerPersonalCenterActivity.this,
//                            AlertView.Style.Alert, null).show();
                    ApiStatusUtil.getInstance().apiStatuError(volleyError,ConsumerPersonalCenterActivity.this);
                }
            }
        });
    }

    /**
     * 网络获取数据并且更新
     */
    private void updateViewFromData() {
        nick_name = mConsumerEssentialInfoEntity.getNick_name();
        nick_name = TextUtils.isEmpty(nick_name) ? UIUtils.getString(R.string.anonymity) : nick_name;
        EventBus.getDefault().postSticky(nick_name);
        Message msg = Message.obtain();
        msg.obj = mConsumerEssentialInfoEntity;
        mHandler.sendMessage(msg);
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            mConsumerEssentialInfoEntity = (ConsumerEssentialInfoEntity) msg.obj;
            if (mConsumerEssentialInfoEntity == null) {
                return;
            }
            mTvLoginOrRegister.setText(mConsumerEssentialInfoEntity.getNick_name());
            if (!TextUtils.isEmpty(mConsumerEssentialInfoEntity.getAvatar()) && ConsumerPersonalCenterActivity.this != null) {
                ImageUtils.displayAvatarImage(mConsumerEssentialInfoEntity.getAvatar(), mPolygonImageView);
            }
        }
    };

    /**
     * 接收返回来的数据
     *
     * @param resultCode 条件码
     * @param data       返回来的数据
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ConsumerPersonalCenterActivity.this.RESULT_OK && requestCode == MORE_LOGOUT && data != null) {
            finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        if (mMemberEntity == null) {
            return;
        }
        getConsumerInfoData(member_id);
    }

    /// 静态常量.
    private static final int MORE_LOGOUT = 0;

    /// 控件.
    private LinearLayout mLlPersonalDemand;
    private RelativeLayout mRlPersonalCollect, mRlPersonalSetting, mRlPersonalFitment;
    private PolygonImageView mPolygonImageView;
    private TextView mTvLoginOrRegister;

    /// 变量.
    private String member_id;
    private String nick_name;
    private ConsumerEssentialInfoEntity mConsumerEssentialInfoEntity;
    private LinearLayout mDesignerAttention;
    private MemberEntity mMemberEntity;
}
