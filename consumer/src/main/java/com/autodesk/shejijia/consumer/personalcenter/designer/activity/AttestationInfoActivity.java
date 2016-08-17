package com.autodesk.shejijia.consumer.personalcenter.designer.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.homepage.activity.MPConsumerHomeActivity;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.autodesk.shejijia.shared.components.common.utility.CommonUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;

/**
 * @author Malidong .
 * @version 1.0 .
 * @date 16-6-6
 * @file AttestationInfoActivity.java  .
 * @brief 实名认证查看状态界面 .
 */
public class AttestationInfoActivity extends NavigationBarActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_attestation_info;
    }

    @Override
    protected void initView() {
        tv_pic_audit_status_one = (TextView) findViewById(R.id.tv_pic_audit_status_one);
        tv_pic_audit_status_two = (TextView) findViewById(R.id.tv_pic_audit_status_two);
        tv_pic_audit_status_three = (TextView) findViewById(R.id.tv_pic_audit_status_three);
        tv_txt_audit_status_one = (TextView) findViewById(R.id.tv_txt_audit_status_one);
        tv_txt_audit_status_two = (TextView) findViewById(R.id.tv_txt_audit_status_two);
        tv_txt_audit_status_three = (TextView) findViewById(R.id.tv_txt_audit_status_three);
        img_audit_describe = (ImageView) findViewById(R.id.img_audit_describe);
        tv_audit_describe = (TextView) findViewById(R.id.tv_audit_describe);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        audit_status = (String) getIntent().getExtras().get(AUDIT_STATUS);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleForNavbar(UIUtils.getString(R.string.response_manage));

        initDrawable();

        showState();
    }

    /**
     * @brief 状态判断 .
     */
    private void showState() {
        if (Constant.NumKey.FOUR.equals(audit_status)) {
            setTitleForNavbar(UIUtils.getString(R.string.not_real_name_authentication));
            tv_pic_audit_status_one.setBackground(UIUtils.getDrawable(R.drawable.bg_attestation_info_pressed));
            tv_txt_audit_status_one.setTextColor(UIUtils.getColor(R.color.bg_ed8e00));
            img_audit_describe.setVisibility(View.GONE);
            tv_audit_describe.setText(UIUtils.getString(R.string.data_sent_please_patience_wait_audit));

        } else if (Constant.NumKey.ZERO.equals(audit_status)) {
            setTitleForNavbar(UIUtils.getString(R.string.autonym_message_moderationa));
            tv_pic_audit_status_one.setBackground(UIUtils.getDrawable(R.drawable.bg_attestation_info_pressed));
            tv_txt_audit_status_one.setTextColor(UIUtils.getColor(R.color.bg_ed8e00));
            tv_pic_audit_status_two.setBackground(UIUtils.getDrawable(R.drawable.bg_attestation_info_pressed));
            tv_txt_audit_status_two.setTextColor(UIUtils.getColor(R.color.bg_ed8e00));
            img_audit_describe.setVisibility(View.GONE);
            tv_audit_describe.setText(UIUtils.getString(R.string.information_review_please_patient));

        } else if (Constant.NumKey.ONE.equals(audit_status)) {
            setTitleForNavbar(UIUtils.getString(R.string.audit_not_pass));
            tv_txt_audit_status_three.setText(UIUtils.getString(R.string.audit_not_pass));
            tv_pic_audit_status_three.setBackground(UIUtils.getDrawable(R.drawable.bg_attestation_info_pressed));
            tv_txt_audit_status_three.setTextColor(UIUtils.getColor(R.color.bg_ed8e00));
            img_audit_describe.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_default_avator));
            tv_audit_describe.setText(UIUtils.getString(R.string.you_identity_audit_failure));

        } else if (Constant.NumKey.TWO.equals(audit_status)) {
            setTitleForNavbar(UIUtils.getString(R.string.autonym_audit_pass));
            tv_txt_audit_status_three.setText(UIUtils.getString(R.string.autonym_audit_pass));
            tv_pic_audit_status_three.setBackground(UIUtils.getDrawable(R.drawable.bg_attestation_info_pressed));
            tv_txt_audit_status_three.setTextColor(UIUtils.getColor(R.color.bg_ed8e00));
            img_audit_describe.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_default_avator));
            tv_audit_describe.setText(UIUtils.getString(R.string.congratulations_you));

        } else if (Constant.NumKey.THREE.equals(audit_status)) {
            setTitleForNavbar(UIUtils.getString(R.string.audit_not_pass));
            tv_txt_audit_status_three.setText(UIUtils.getString(R.string.audit_not_pass));
            tv_pic_audit_status_three.setBackground(UIUtils.getDrawable(R.drawable.bg_attestation_info_pressed));
            tv_txt_audit_status_three.setTextColor(UIUtils.getColor(R.color.bg_ed8e00));
            img_audit_describe.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_default_avator));
            tv_audit_describe.setText(UIUtils.getString(R.string.you_identity_audit_failure));
        }
    }

    /**
     * @brief 初始化图片 .
     */
    private void initDrawable() {

        tv_pic_audit_status_one.setBackground(UIUtils.getDrawable(R.drawable.bg_attestation_info_normal));
        tv_pic_audit_status_two.setBackground(UIUtils.getDrawable(R.drawable.bg_attestation_info_normal));
        tv_pic_audit_status_three.setBackground(UIUtils.getDrawable(R.drawable.bg_attestation_info_normal));
        tv_txt_audit_status_one.setTextColor(UIUtils.getColor(R.color.bg_ef));
        tv_txt_audit_status_two.setTextColor(UIUtils.getColor(R.color.bg_ef));
        tv_txt_audit_status_three.setTextColor(UIUtils.getColor(R.color.bg_ef));

    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    protected void leftNavButtonClicked(View view) {
        CommonUtils.launchActivity(AttestationInfoActivity.this, MPConsumerHomeActivity.class);
        super.leftNavButtonClicked(view);

    }
    private static  final String AUDIT_STATUS ="AUDIT_STATUS";


    private TextView tv_pic_audit_status_one;
    private TextView tv_pic_audit_status_two;
    private TextView tv_pic_audit_status_three;
    private TextView tv_txt_audit_status_one;
    private TextView tv_txt_audit_status_two;
    private TextView tv_txt_audit_status_three;
    private TextView tv_audit_describe;
    private ImageView img_audit_describe;

    private String audit_status;
}
