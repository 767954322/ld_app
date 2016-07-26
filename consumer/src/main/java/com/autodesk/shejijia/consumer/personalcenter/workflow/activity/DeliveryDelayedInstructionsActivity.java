package com.autodesk.shejijia.consumer.personalcenter.workflow.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-7-25 .
 * @file DeliveryDelayedInstructionsActivity.java .
 * @brief 交付说明 .
 */
public class DeliveryDelayedInstructionsActivity extends NavigationBarActivity {
    private TextView mTvDelayedInstructions;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_deliverydelayed_instructions;
    }

    @Override
    protected void initView() {
        super.initView();
        mTvDelayedInstructions = (TextView) findViewById(R.id.tv_delayed_instructions);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleForNavbar(UIUtils.getString(R.string.title_introduce));
        mTvDelayedInstructions.setText(UIUtils.getString(R.string.delivery_delayed_instructions));
    }
}
