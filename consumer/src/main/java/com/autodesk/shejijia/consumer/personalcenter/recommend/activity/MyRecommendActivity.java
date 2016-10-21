package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.utils.ToastUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

/**
 * @User: 蜡笔小新
 * @date: 16-10-21
 * @GitHub: https://github.com/meikoz
 */

public class MyRecommendActivity extends NavigationBarActivity implements View.OnClickListener {
    public static int CONSUMER = 0;
    public static int DESIGNER = 1;
    private TextView mRightTextView;

    public static void jumpTo(Context context, int type) {
        Intent intent = new Intent(context, MyRecommendActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_my_recommend;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleForNavbar(UIUtils.getString(R.string.personal_recommend));
        mRightTextView = (TextView) findViewById(R.id.nav_right_textView);
        mRightTextView.setVisibility(View.VISIBLE);
        mRightTextView.setTextColor(UIUtils.getColor(R.color.color_blue_0888ff));
        mRightTextView.setText("新建清单");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mRightTextView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_right_textView:
                ToastUtil.showCustomToast(this, "新建清单");
                break;
        }
    }
}
