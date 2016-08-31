package com.autodesk.shejijia.consumer.personalcenter.designer.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.manager.constants.JsonConstants;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.MyPropertyBean;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.RealName;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.uielements.reusewheel.utils.OptionsPickerView;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.RegexUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DongXueQue .
 * @version 1.0 .
 * @date 16-6-7
 * @file WithdrawalActivity.java  .
 * @brief 我的提现页面  .
 */
public class WithdrawalActivity extends NavigationBarActivity implements View.OnClickListener, OnItemClickListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_withdrawal;
    }

    @Override
    protected void initView() {
        super.initView();
        tv_withdrawal_account_balance = (TextView) findViewById(R.id.tv_withdrawal_account_balance);
        tv_withdrawal_account = (TextView) findViewById(R.id.tv_withdrawal_account);
        tv_withdrawal_cardholder_name = (TextView) findViewById(R.id.tv_withdrawal_cardholder_name);
        tv_withdrawal_open_account_bank = (TextView) findViewById(R.id.tv_withdrawal_open_account_bank);
        tv_withdrawal_bank_card_number = (TextView) findViewById(R.id.tv_withdrawal_bank_card_number);
        ll_withdrawal_open_account_bank = (LinearLayout) findViewById(R.id.ll_withdrawal_open_account_bank);
        et_withdrawal_bank_card_number = (EditText) findViewById(R.id.et_withdrawal_bank_card_number);
        tv_withdrawal_branch_bank = (TextView) findViewById(R.id.tv_withdrawal_branch_bank);
        et_withdrawal_branch_bank = (EditText) findViewById(R.id.et_withdrawal_branch_bank);
        btn_withdrawal_true = (Button) findViewById(R.id.btn_withdrawal_true);
        ll_withdrawal_replace_bank_card = (LinearLayout) findViewById(R.id.ll_withdrawal_replace_bank_card);

        mStopDemandAlertView = new AlertView(UIUtils.getString(R.string.un_bind_rank_card), UIUtils.getString(R.string.are_you_sure_unbundling), UIUtils.getString(R.string.cancel), null, new String[]{UIUtils.getString(R.string.sure)}, this, AlertView.Style.Alert, this).setCancelable(true);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
//        myPropertyBean = (MyPropertyBean) getIntent().getSerializableExtra(Constant.DesignerMyPropertyKey.MY_PROPERTY_BEAN);
//        account_user_name = getIntent().getStringExtra("real_name");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        memberEntity = AdskApplication.getInstance().getMemberEntity();
        mBankName = filledData(getResources().getStringArray(R.array.mBank));
        setTitleForNavbar(UIUtils.getString(R.string.my_property_withdrawal));
        getMyPropertyData(memberEntity.getAcs_member_id());
        getRealNameAuditStatus(memberEntity.getAcs_member_id(), memberEntity.getHs_uid());
        setBackName();

    }

    @Override
    protected void initListener() {
        super.initListener();
        btn_withdrawal_true.setOnClickListener(this);
        ll_withdrawal_open_account_bank.setOnClickListener(this);
        ll_withdrawal_replace_bank_card.setOnClickListener(this);
        et_withdrawal_bank_card_number.addTextChangedListener(new MyWatcher());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_withdrawal_open_account_bank:
                pvBankNameOptions.show();
                bank_name = item_back_name;
                break;
            case R.id.ll_withdrawal_replace_bank_card:
                designer_id = Long.parseLong(memberEntity.getAcs_member_id());

                mStopDemandAlertView.show();

                break;
            case R.id.btn_withdrawal_true:
                bank_name = getText(tv_withdrawal_open_account_bank);
                String tv_branch_bank_ = getText(tv_withdrawal_branch_bank);
                branch_bank_name = (tv_branch_bank_ == null) ? getText(et_withdrawal_branch_bank) : tv_branch_bank_;
                String tv_card_number = getText(tv_withdrawal_bank_card_number);
                deposit_card = (tv_card_number == null) ? getText(et_withdrawal_bank_card_number) : tv_card_number;
                if (!"".equals(deposit_card)) {
                    deposit_card = deposit_card.replace(" ", "").trim();
                }
                String bank_name = tv_withdrawal_open_account_bank.getText().toString().trim();

                if (TextUtils.isEmpty(bank_name)) {
                    Toast.makeText(WithdrawalActivity.this, "请选择您的开户银行", Toast.LENGTH_SHORT).show();
                    break;
                }
                String regex_name = "[a-zA-Z\\u4e00-\\u9fa5]{2,10}";
                boolean isBankNum = deposit_card.trim().matches(RegexUtil.PHONE_BLANK);
                boolean isName = account_user_name.trim().matches(regex_name);
                boolean isBranchBankName = branch_bank_name.trim().matches(RegexUtil.ADDRESS_ZHONGWEN);
                if (myPropertyBean == null) {
                    if (!isName) {
                        Toast.makeText(WithdrawalActivity.this, "持卡人姓名只能包含2-10位汉字或英文", Toast.LENGTH_SHORT).show();
                        break;
                    }
//                    if (!checkNameChese(branch_bank_name)) {
                    if (!isBranchBankName) {
                        Toast.makeText(WithdrawalActivity.this, "支行名称只能包含2-32位汉字", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    if (!isBankNum) {
                        Toast.makeText(WithdrawalActivity.this, "银行卡号请输入16到19位数字", Toast.LENGTH_SHORT).show();
                        break;
                    }
                } else {
                    if (TextUtils.isEmpty(myPropertyBean.getBranch_bank_name())) {
                        if (!checkNameChese(branch_bank_name)) {
                            if (!isBranchBankName) {
                                Toast.makeText(WithdrawalActivity.this, "支行名称只能包含2-32位汉字", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                        if (TextUtils.isEmpty(myPropertyBean.getDeposit_card())) {
                            if (!isBankNum) {
                                Toast.makeText(WithdrawalActivity.this, "银行卡号请输入16到19位数字", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                    }
//
                    boolean flag = validateEditText(account_user_name, branch_bank_name, deposit_card);
//
                    if (flag && null != memberEntity) {
                        designer_id = Long.parseLong(memberEntity.getAcs_member_id());
                        getWithdrawareBalanceData(designer_id, account_user_name, bank_name, branch_bank_name, deposit_card);
                    }
//
                    break;
                }
        }
    }
    /**
     * 检测String是否全是中文
     *
     * @param name
     * @return
     */
    public boolean checkNameChese(String name) {
        boolean res = true;
        char[] cTemp = name.toCharArray();
        for (int i = 0; i < name.length(); i++) {
            if (!isChinese(cTemp[i])) {
                res = false;
                break;
            }
        }
        return res;
    }

    /**
     * 判定输入汉字
     *
     * @param c
     * @return
     */
    public boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 获取我的资产信息
     *
     * @param designer_id
     */
    public void getMyPropertyData(String designer_id) {
        MPServerHttpManager.getInstance().getMyPropertyData(designer_id, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String jsonString = GsonUtil.jsonToString(jsonObject);
                myPropertyBean = GsonUtil.jsonToBean(jsonString, MyPropertyBean.class);
                if (TextUtils.isEmpty(myPropertyBean.getBank_name())) {
                    et_withdrawal_bank_card_number.setVisibility(View.VISIBLE);
                    et_withdrawal_bank_card_number.setText("");
                    et_withdrawal_branch_bank.setVisibility(View.VISIBLE);
                    et_withdrawal_branch_bank.setText("");
                    tv_withdrawal_bank_card_number.setVisibility(View.GONE);
                    tv_withdrawal_branch_bank.setVisibility(View.GONE);
                    ll_withdrawal_replace_bank_card.setVisibility(View.GONE);
                } else {
                    tv_withdrawal_bank_card_number.setVisibility(View.VISIBLE);
                    tv_withdrawal_branch_bank.setVisibility(View.VISIBLE);
                    ll_withdrawal_replace_bank_card.setVisibility(View.VISIBLE);
                    et_withdrawal_bank_card_number.setVisibility(View.GONE);
                    et_withdrawal_branch_bank.setVisibility(View.GONE);
                }
                tv_withdrawal_open_account_bank.setText(myPropertyBean.getBank_name());
//                et_withdrawal_branch_bank.setText(myPropertyBean.getBranch_bank_name());
//                et_withdrawal_bank_card_number.setText(myPropertyBean.getDeposit_card());
                tv_withdrawal_account_balance.setText("￥：" + myPropertyBean.getAmount());
                tv_withdrawal_account.setText("￥：" + myPropertyBean.getAmount());
                showBindingIDCardState();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
            }
        });
    }


    /**
     * @param isNumber
     * @return
     * @brief 验证银行卡号 .
     */
    private String isValidateNumber(String isNumber) {
        KLog.d(TAG, isNumber);
        return isNumber;
    }

    private List<String> filledData(String[] date) {
        List<String> mSortList = new ArrayList<>();
        for (String str : date) {
            mSortList.add(str);
        }
        return mSortList;
    }

    /**
     * @param account_user_name
     * @param branch_bank_name
     * @param deposit_card
     * @return
     * @brief 验证数据得合法性 .
     */
    private boolean validateEditText(String account_user_name, String branch_bank_name, String deposit_card) {
        if (account_user_name == null || "".equals(account_user_name)) {
            String content = UIUtils.getString(R.string.tip_content_one);
            openAlertView(content);

            return false;
        }

        if (branch_bank_name == null || "".equals(branch_bank_name)) {
            String content = UIUtils.getString(R.string.tip_content_two);
            openAlertView(content);
            return false;
        }

        if (TextUtils.isEmpty(deposit_card)) {
            String content = UIUtils.getString(R.string.tip_content_three);
            openAlertView(content);
            return false;
        }
        return true;
    }

    private void openAlertView(String content) {
        new AlertView(UIUtils.getString(R.string.tip), content, null, new String[]{UIUtils.getString(R.string.sure)}, null, WithdrawalActivity.this,
                AlertView.Style.Alert, null).show();
    }

    private String getText(View view) {
        int key = view.getVisibility();
        String textString = null;

        switch (key) {
            case 0:
                if (view instanceof TextView) {
                    TextView textView = (TextView) view;
                    textString = textView.getText().toString();
                } else if (view instanceof EditText) {
                    EditText editText = (EditText) view;
                    textString = editText.getText().toString();
                }
                break;
            default:
                break;
        }
        return textString;

    }

    /**
     * @param designer_id
     * @param account_user_name
     * @param bank_name
     * @param branch_bank_name
     * @param deposit_card
     * @brief 获取提现数据 .
     */
    public void getWithdrawareBalanceData(long designer_id, String account_user_name, String bank_name, String branch_bank_name, String deposit_card) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(JsonConstants.WITHDRAWARE_BALANCE_ACCOUNT_USER_NAME, account_user_name);
            jsonObject.put(JsonConstants.WITHDRAWARE_BALANCE_BANK_NAME, bank_name);
            jsonObject.put(JsonConstants.WITHDRAWARE_BALANCE_BRANCH_BANK_NAME, branch_bank_name);
            jsonObject.put(JsonConstants.WITHDRAWARE_BALANCE_DEPOSIT_CARD, isValidateNumber(deposit_card));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkJsonRequest.OKResponseCallback callback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                updateViewFromData();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                new AlertView(UIUtils.getString(R.string.application_failure), UIUtils.getString(R.string.application_detail), null, new String[]{UIUtils.getString(R.string.sure)}, null, WithdrawalActivity.this,
                        AlertView.Style.Alert, null).show();
            }
        };
        MPServerHttpManager.getInstance().getWithDrawBalanceData(designer_id, jsonObject, callback);
    }


    /**
     * 解除银行卡绑定
     *
     * @param designer_id
     * @param account_user_name
     * @param bank_name
     * @param branch_bank_name
     * @param deposit_card
     */
    public void sendUnBindBankCard(long designer_id, String account_user_name, String bank_name, String branch_bank_name, String deposit_card) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(JsonConstants.WITHDRAWARE_BALANCE_ACCOUNT_USER_NAME, account_user_name);
            jsonObject.put(JsonConstants.WITHDRAWARE_BALANCE_BANK_NAME, bank_name);
            jsonObject.put(JsonConstants.WITHDRAWARE_BALANCE_BRANCH_BANK_NAME, branch_bank_name);
            jsonObject.put(JsonConstants.WITHDRAWARE_BALANCE_DEPOSIT_CARD, isValidateNumber(deposit_card));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkJsonRequest.OKResponseCallback callback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                tv_withdrawal_branch_bank.setVisibility(View.GONE);
                tv_withdrawal_bank_card_number.setVisibility(View.GONE);

                et_withdrawal_bank_card_number.setText("");
                et_withdrawal_branch_bank.setText("");
                tv_withdrawal_open_account_bank.setText("");

                /// 解除绑定后，解决不可点击 .
                ll_withdrawal_open_account_bank.setEnabled(true);


                et_withdrawal_bank_card_number.setVisibility(View.VISIBLE);
                et_withdrawal_branch_bank.setVisibility(View.VISIBLE);
                ll_withdrawal_replace_bank_card.setVisibility(View.GONE);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                new AlertView(UIUtils.getString(R.string.application_failure), UIUtils.getString(R.string.application_unbing_fail), null, new String[]{UIUtils.getString(R.string.sure)}, null, WithdrawalActivity.this,
                        AlertView.Style.Alert, null).show();
            }
        };
        MPServerHttpManager.getInstance().sendUnBindBankCard(designer_id, jsonObject, callback);
    }

    /**
     * house type
     */
    private void setBackName() {
        pvBankNameOptions = new OptionsPickerView(this);
        for (String item : mBankName) {
            bankNameItems.add(item);
        }
        pvBankNameOptions.setTitle("选择银行");
        pvBankNameOptions.setPicker(bankNameItems);
        pvBankNameOptions.setSelectOptions(0);
        pvBankNameOptions.setCyclic(false);
        pvBankNameOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                item_back_name = bankNameItems.get(options1);
                tv_withdrawal_open_account_bank.setText(item_back_name);
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
                RealName realNameBean = GsonUtil.jsonToBean(auditInfo, RealName.class);
                account_user_name = realNameBean.getReal_name();
                tv_withdrawal_cardholder_name.setText(account_user_name);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                if (WithdrawalActivity.this != null) {
//                    new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, WithdrawalActivity.this,
//                            AlertView.Style.Alert, null).show();
                    ApiStatusUtil.getInstance().apiStatuError(volleyError, WithdrawalActivity.this);
                }
            }
        });
    }


    //控件，内容，状态判断
    private void showBindingIDCardState() {

        if (null == myPropertyBean || myPropertyBean.equals("")) {

            return;
        } else {

            tv_withdrawal_account_balance.setText("¥" + myPropertyBean.getAmount() + "");
            tv_withdrawal_account.setText("¥" + myPropertyBean.getAmount());
            bank_name = myPropertyBean.getBank_name();
            deposit_card = myPropertyBean.getDeposit_card();
            branch_bank_name = myPropertyBean.getBranch_bank_name();

            if (TextUtils.isEmpty(bank_name)) {
                ll_withdrawal_replace_bank_card.setVisibility(View.GONE); // 没有绑定银行卡隐藏解绑按钮
            } else {
                ll_withdrawal_replace_bank_card.setVisibility(View.VISIBLE); // 绑定后显示解绑按钮
                et_withdrawal_bank_card_number.setVisibility(View.GONE);
                et_withdrawal_branch_bank.setVisibility(View.GONE);
                ll_withdrawal_open_account_bank.setEnabled(false);
            }

            if (null == account_user_name || account_user_name.equals("")) {
            } else {
                tv_withdrawal_cardholder_name.setVisibility(View.VISIBLE);
                tv_withdrawal_cardholder_name.setText(account_user_name);
            }
            tv_withdrawal_open_account_bank.setText(bank_name);

            if (null == branch_bank_name || branch_bank_name.equals("")) {
                et_withdrawal_branch_bank.setVisibility(View.VISIBLE);
            } else {
                tv_withdrawal_branch_bank.setVisibility(View.VISIBLE);
                tv_withdrawal_branch_bank.setText(branch_bank_name);
            }
            if (null == deposit_card || deposit_card.equals("")) {
                et_withdrawal_bank_card_number.setVisibility(View.VISIBLE);
                deposit_card = et_withdrawal_bank_card_number.getText().toString();
            } else {
                tv_withdrawal_bank_card_number.setVisibility(View.VISIBLE);
                tv_withdrawal_bank_card_number.setText(deposit_card);
                if (TextUtils.isEmpty(bank_name)) {
                    tv_withdrawal_bank_card_number.setVisibility(View.GONE);
                }
            }
        }

        et_withdrawal_branch_bank.setText(branch_bank_name);
        et_withdrawal_bank_card_number.setText(deposit_card);
    }

    //获取数据后操作
    private void updateViewFromData() {
        new AlertView(UIUtils.getString(R.string.application_successful), UIUtils.getString(R.string.application_detail), null, null, new String[]{UIUtils.getString(R.string.sure)}, WithdrawalActivity.this,
                AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                if (position != AlertView.CANCELPOSITION) {
                    Intent intent = new Intent();
                    intent.putExtra(Constant.DesignerWithDraw.AMOUNT, "0.00");
                    intent.putExtra(Constant.DesignerWithDraw.IS_SUCCESS, true);
                    WithdrawalActivity.this.setResult(Activity.RESULT_OK, intent);
                    WithdrawalActivity.this.finish();
                }
            }
        }).show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(Object object, int position) {
        if (object == mStopDemandAlertView && position != AlertView.CANCELPOSITION) {
            sendUnBindBankCard(designer_id, account_user_name, bank_name, branch_bank_name, deposit_card);
            return;
        }
    }


    class MyWatcher implements TextWatcher {
        int beforeTextLength = 0;
        int onTextLength = 0;
        boolean isChanged = false;
        int location = 0;// 记录光标的位置
        private char[] tempChar;
        private StringBuffer buffer = new StringBuffer();
        int konggeNumberB = 0;

        @Override
        public void beforeTextChanged(CharSequence text, int start, int count, int after) {

            // TODO Auto-generated method stub
            beforeTextLength = text.length();
            if (buffer.length() > 0) {
                buffer.delete(0, buffer.length());
            }
            konggeNumberB = 0;
            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) == ' ') {
                    konggeNumberB++;
                }
            }

        }

        @Override
        public void onTextChanged(CharSequence text, int start, int before, int count) {

            // TODO Auto-generated method stub
            onTextLength = text.length();
            buffer.append(text.toString());
            if (onTextLength == beforeTextLength || onTextLength <= 3 || isChanged) {
                isChanged = false;
                return;
            }
            isChanged = true;
        }

        @Override
        public void afterTextChanged(Editable s) {

            if (isChanged) {
                location = et_withdrawal_bank_card_number.getSelectionEnd();
                int index = 0;
                while (index < buffer.length()) {
                    if (buffer.charAt(index) == ' ') {
                        buffer.deleteCharAt(index);
                    } else {
                        index++;
                    }
                }
                index = 0;
                int konggeNumberC = 0;
                while (index < buffer.length()) {

                    if ((index + 1) % 5 == 0) {// if ((index == 4 || index == 9 || index == 14 || index// == 19)) {

                        buffer.insert(index, ' ');
                        konggeNumberC++;
                    }
                    index++;
                }

                if (konggeNumberC > konggeNumberB) {

                    location += (konggeNumberC - konggeNumberB);
                }

                tempChar = new char[buffer.length()];

                buffer.getChars(0, buffer.length(), tempChar, 0);

                String str = buffer.toString();

                if (location > str.length()) {

                    location = str.length();

                } else if (location < 0) {

                    location = 0;

                }
                et_withdrawal_bank_card_number.setText(str);
                Editable etable = et_withdrawal_bank_card_number.getText();
                Selection.setSelection(etable, location);
                isChanged = false;
            }

        }
    }

    /// 控件.
    private TextView tv_withdrawal_account_balance;
    private TextView tv_withdrawal_account;
    private TextView tv_withdrawal_branch_bank;
    private TextView tv_withdrawal_cardholder_name;
    private TextView tv_withdrawal_open_account_bank;
    private TextView tv_withdrawal_bank_card_number;
    private Button btn_withdrawal_true;
    private EditText et_withdrawal_branch_bank;
    private EditText et_withdrawal_bank_card_number;
    private LinearLayout ll_withdrawal_open_account_bank;
    private OptionsPickerView pvBankNameOptions;
    private LinearLayout ll_withdrawal_replace_bank_card;
    private MemberEntity memberEntity;

    /// 变量.
    private String bank_name;
    private String deposit_card;
    private String branch_bank_name;
    private String item_back_name;
    private long designer_id;
    private String account_user_name;

    /// 集合，类.
    private List<String> mBankName;
    private MyPropertyBean myPropertyBean;
    private ArrayList<String> bankNameItems = new ArrayList<>();
    private AlertView mStopDemandAlertView;
}
