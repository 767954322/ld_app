package com.autodesk.shejijia.consumer.codecorationBase.average.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.consumer.activity.IssueDemandActivity;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.ConsumerEssentialInfoEntity;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import org.json.JSONObject;

/**
 * 竞优
 */
public class AverageFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout mRlAverage;
    private ImageView mIvAverageTip;
    private ImageView mIvSendDemand;
    private String mNick_name;

    public AverageFragment() {
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_average;
    }

    @Override
    protected void initView() {
        mRlAverage = (RelativeLayout) rootView.findViewById(R.id.rl_average);
        mIvAverageTip = (ImageView) rootView.findViewById(R.id.iv_average_tip);
        mIvSendDemand = (ImageView) rootView.findViewById(R.id.iv_send_demand);
    }

    @Override
    protected void initData() {
        MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        if (mMemberEntity != null && Constant.UerInfoKey.CONSUMER_TYPE.equals(mMemberEntity.getMember_type())) {
            getConsumerInfoData(mMemberEntity.getAcs_member_id());
            return;
        }

    }

    @Override
    protected void initListener() {
        super.initListener();
        mIvAverageTip.setOnClickListener(this);
        mIvSendDemand.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_send_demand: /// .
                MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
                if (null == memberEntity){
                    AdskApplication.getInstance().doLogin(activity);
                }else {
                    Intent intent = new Intent(activity, IssueDemandActivity.class);
                    intent.putExtra(Constant.SixProductsFragmentKey.SELECTION, false);
                    intent.putExtra(Constant.ConsumerPersonCenterFragmentKey.NICK_NAME, mNick_name);
                    startActivity(intent);
                }
                break;

            case R.id.iv_average_tip:
                showAlertView();
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
                ConsumerEssentialInfoEntity mConsumerEssentialInfoEntity = GsonUtil.jsonToBean(jsonString, ConsumerEssentialInfoEntity.class);
                mNick_name = mConsumerEssentialInfoEntity.getNick_name();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
            }
        });
    }

    private void showAlertView() {
        AlertView alertViewExt = new AlertView(UIUtils.getString(R.string.title_average_rule),
                null, null, null, new String[]{UIUtils.getString(R.string.close_alert)}, activity, AlertView.Style.Alert, null);

        ViewGroup extView = (ViewGroup) LayoutInflater.from(activity).inflate(R.layout.alert_average, null);
        TextView tvAlertMsg = (TextView) extView.findViewById(R.id.tv_alert_msg);
        tvAlertMsg.setText(UIUtils.getString(R.string.alert_average_rule_1) + "\n" +
                UIUtils.getString(R.string.alert_average_rule_2) + "\n" +
                UIUtils.getString(R.string.alert_average_rule_3) + "\n"
        );
        alertViewExt.addExtView(extView);
        alertViewExt.show();
    }
}
