package com.autodesk.shejijia.consumer.personalcenter.workflow.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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
        mTvDelayedInstructions.setText(getFromAssets("delivery_delayed_instructions.txt"));
    }

    /**
     * 读取资源文件
     *
     * @param fileName 　文件路径
     * @return 返回的文本的内容
     */
    public String getFromAssets(String fileName) {
        String result = "";
        try {
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
