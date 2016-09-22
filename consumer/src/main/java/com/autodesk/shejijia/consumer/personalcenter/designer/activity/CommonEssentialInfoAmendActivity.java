package com.autodesk.shejijia.consumer.personalcenter.designer.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.InfoModifyEntity;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.uielements.TextViewContent;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.utility.RegexUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import de.greenrobot.event.EventBus;

/**
 * @author Malidong .
 * @version 1.0 .
 * @date 16-6-7
 * @file CommonEssentialInfoAmendActivity.java  .
 * @brief 修改昵称、量房费 .
 */
public class CommonEssentialInfoAmendActivity extends NavigationBarActivity implements View.OnClickListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_essential_info_amend;
    }

    @Override
    protected void initView() {
        super.initView();
        tvc_content = (TextViewContent) findViewById(R.id.tvc_info_content);
        ll_clear = (LinearLayout) findViewById(R.id.ll_essential_amend_clear);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        pTag = (String) getIntent().getExtras().get(Constant.PersonCenterTagKey.ESSENTIAL_INFO_TAG);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        showState();
        setTextColorForRightNavButton(UIUtils.getColor(R.color.black));
    }

    //保存监听
    @Override
    protected void rightNavButtonClicked(View view) {
        super.rightNavButtonClicked(view);
        String msg = tvc_content.getText().toString().trim();
        boolean matches = msg.matches(RegexUtil.NICK_NAME_REGEX);
        boolean num = msg.matches(RegexUtil.MEASURE_FEE_REGEX);

        if (pTag.equals(Constant.PersonCenterTagKey.DESIGNER_INFO)) {
            if (msg.isEmpty()) {
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.nick_name_format), null, null, new String[]{UIUtils.getString(R.string.sure)}, CommonEssentialInfoAmendActivity.this, AlertView.Style.Alert, null).show();
                return;
            } else if (!matches) {
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.nick_name_format), null, null, new String[]{UIUtils.getString(R.string.sure)}, CommonEssentialInfoAmendActivity.this, AlertView.Style.Alert, null).show();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); // 此方法为了在弹出框得前提下隐藏软键盘
                imm.hideSoftInputFromWindow(tvc_content.getWindowToken(), 0);
                return;
            }
        } else if (pTag.equals(Constant.PersonCenterTagKey.MEASURE_HOUSE)) {

            if (TextUtils.isEmpty(msg)) {
                new AlertView(UIUtils.getString(R.string.tip),
                        UIUtils.getString(R.string.measure_format_tip), null, null,
                        new String[]{UIUtils.getString(R.string.sure)}, CommonEssentialInfoAmendActivity.this, AlertView.Style.Alert, null).show();
                return;
            }

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(tvc_content.getWindowToken(), 0);
            if (!num) {
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.no_measure_fee), null, null, new String[]{UIUtils.getString(R.string.sure)}, CommonEssentialInfoAmendActivity.this, AlertView.Style.Alert, null).show();
                return;
            }
        } else if (pTag.equals(Constant.PersonCenterTagKey.CONSUMER_INFO)) {
            if (msg.isEmpty()) {
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.nick_name_format), null, null, new String[]{UIUtils.getString(R.string.sure)}, CommonEssentialInfoAmendActivity.this, AlertView.Style.Alert, null).show();
                return;
            } else if (!matches) {
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.nick_name_format), null, null, new String[]{UIUtils.getString(R.string.sure)}, CommonEssentialInfoAmendActivity.this, AlertView.Style.Alert, null).show();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(tvc_content.getWindowToken(), 0);
                return;
            }
        }
        EventBus.getDefault().post(new InfoModifyEntity(pTag, msg));
        finish();
    }

    //保存文字设置
    @Override
    protected void setTextColorForRightNavButton(int color) {
        super.setTextColorForRightNavButton(color);
        TextView rightTextView = (TextView) findViewById(R.id.nav_right_textView);
        rightTextView.setVisibility(View.VISIBLE);
        rightTextView.setTextColor(color);
        rightTextView.setText(UIUtils.getString(R.string.save));
    }

    //判断控件内容,状态
    private void showState() {

        if (pTag.equals(Constant.PersonCenterTagKey.DESIGNER_INFO)) {
            mContent = (String) getIntent().getExtras().get(Constant.PersonCenterTagKey.DESIGNER_CONTENT);
            setTitleForNavbar(UIUtils.getString(R.string.nick_name));
        } else if (pTag.equals(Constant.PersonCenterTagKey.CONSUMER_INFO)) {
            mContent = (String) getIntent().getExtras().get(Constant.PersonCenterTagKey.CONSUMER_CONTENT);
            setTitleForNavbar(UIUtils.getString(R.string.nick_name));
        } else if (pTag.equals(Constant.PersonCenterTagKey.MEASURE_HOUSE)) {
            mContent = (String) getIntent().getExtras().get(Constant.PersonCenterTagKey.MEASURE_CONTENT);
            setTitleForNavbar(UIUtils.getString(R.string.measure_house_cost));
        }
        tvc_content.setText(mContent);
        tvc_content.setSelection(tvc_content.getText().length());
    }

    @Override
    protected void initListener() {
        super.initListener();
        ll_clear.setOnClickListener(this);

        edTextChangeListener();
    }

    private void edTextChangeListener() {
        tvc_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String mContent = editable.toString().trim();
                if (mContent.isEmpty()) {
                    ll_clear.setVisibility(View.GONE);
                } else {
                    ll_clear.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_essential_amend_clear:  /// 清空 .
                tvc_content.setText(null);
                break;
        }
    }

    private LinearLayout ll_clear;
    private TextViewContent tvc_content;

    private String pTag;
    private String mContent;
}
