package com.autodesk.shejijia.consumer.personalcenter.consumer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.workflow.activity.FlowUploadDeliveryActivity;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPBidderBean;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-8-1 .
 * @file AppraiseDesignerActivity.java .
 * @brief 设计师评价页面 .
 */
public class AppraiseDesignerActivity extends NavigationBarActivity implements
        RatingBar.OnRatingBarChangeListener,
        View.OnClickListener, OnItemClickListener {

    public static final String IS_EVALUATE = "IS_EVALUATE"; /// 用于判断是否评价完成，方便回显 .

    private RatingBar mRatingBarStar;
    private Button mBtnSubmitEvaluation;
    private EditText mEditEvaluationContent;
    private TextView mTvDesignerName;
    private AlertView mAppraiseDesignerAlertView;
    private AlertView mAppraiseDesignerSuccessAlertView;
    private PolygonImageView mPolygonImageView;

    private MPBidderBean mMPBidderBean;
    private String designer_id;
    private String needs_id;
    private float mRatingProgress;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_appraise;
    }

    @Override
    protected void initView() {
        super.initView();
        mRatingBarStar = (RatingBar) findViewById(R.id.rating_star);
        mPolygonImageView = (PolygonImageView) findViewById(R.id.piv_designer_avatar);
        mBtnSubmitEvaluation = (Button) findViewById(R.id.btn_submit_appraisement);
        mEditEvaluationContent = (EditText) findViewById(R.id.et_appraisement_content);
        mTvDesignerName = (TextView) findViewById(R.id.tv_designer_name);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (null != bundle) {
            mMPBidderBean = (MPBidderBean) bundle.getSerializable(FlowUploadDeliveryActivity.BIDDER_ENTITY);
            designer_id = bundle.getString(Constant.SeekDesignerDetailKey.DESIGNER_ID);
            if (TextUtils.isEmpty(designer_id)) {
                designer_id = mMPBidderBean.getDesigner_id();
            }
            needs_id = bundle.getString(Constant.SeekDesignerDetailKey.NEEDS_ID);
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleForNavbar(UIUtils.getString(R.string.appraise_designer_title));
        if (null != mMPBidderBean) {
            String avatar = mMPBidderBean.getAvatar();
            String user_name = mMPBidderBean.getUser_name();

            ImageUtils.displayAvatarImage(avatar, mPolygonImageView);
            mTvDesignerName.setText(user_name);
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mRatingBarStar.setOnRatingBarChangeListener(this);
        mBtnSubmitEvaluation.setOnClickListener(this);
        mAppraiseDesignerAlertView = new AlertView("",
                UIUtils.getString(R.string.evaluation_abandon_msg),
                UIUtils.getString(R.string.evaluation_continue_edit),
                null, new String[]{UIUtils.getString(R.string.evaluation_abandon)}, this, AlertView.Style.Alert, this);
        mAppraiseDesignerSuccessAlertView = new AlertView(UIUtils.getString(R.string.evaluation_success_title),
                UIUtils.getString(R.string.evaluation_success_msg),
                null, null, new String[]{UIUtils.getString(R.string.following_sure)}, this, AlertView.Style.Alert, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit_appraisement:
                String appeasementsContent = mEditEvaluationContent.getText().toString();
                boolean regex_address_right = appeasementsContent.matches("^.{15,200}$");

                boolean isValidateSubmitContent = validateSubmitContent(appeasementsContent, regex_address_right);
                if (isValidateSubmitContent) {
                    CustomProgress.show(AppraiseDesignerActivity.this, "", false, null);
                    MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
                    String hs_uid = memberEntity.getHs_uid();
                    JSONObject jsonObject = new JSONObject();

                    try {
                        jsonObject.put("designer_uid", hs_uid);             /// 消费者编号 .
                        jsonObject.put("member_grade", mRatingProgress);        /// 消费者评分 .
                        jsonObject.put("member_estimate", appeasementsContent); /// 消费者评价，最大200中文字符 .
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    KLog.d(TAG, "jsonObject:" + jsonObject);
                    submitEvaluation(jsonObject);
                }
                break;
        }
    }

    /**
     * 取消编辑或者保存评价执行的操作
     */
    private void abandonAppraiseDesigner() {
        mAppraiseDesignerAlertView.show();
    }


    /**
     * 校验输入的评价内荣是否符合规范
     *
     * @param appeasementsContent 评价内容，
     *                            [1]可以为空;
     *                            [2]如果有数据时候最大200中文字符
     */
    private boolean validateSubmitContent(String appeasementsContent, boolean regex_address_right) {

        float rating = mRatingBarStar.getRating();
        if ((int) rating == 0) {
            showAlertView(R.string.you_have_not_score);
            return false;
        }

        if (!TextUtils.isEmpty(appeasementsContent) && !regex_address_right) {
            showAlertView(R.string.please_enter_words);
            return false;
        }

        if (TextUtils.isEmpty(appeasementsContent)) {
            return true;
        }
        return true;
    }

    private void showAlertView(int content) {
        new AlertView(UIUtils.getString(R.string.tip),
                UIUtils.getString(content), null, null,
                new String[]{UIUtils.getString(R.string.sure)},
                AppraiseDesignerActivity.this, AlertView.Style.Alert, null).show();
    }

    /**
     * 发送评价内容
     * demands_id  项目编号
     * designer_id 设计师编号
     */
    private void submitEvaluation(JSONObject jsonObject) {
        MPServerHttpManager.getInstance().submitAppraisement(needs_id, designer_id, jsonObject,
                new OkJsonRequest.OKResponseCallback() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        CustomProgress.cancelDialog();
                        mAppraiseDesignerSuccessAlertView.show();
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) { /// 204 .
                        CustomProgress.cancelDialog();
                        MPNetworkUtils.logError(TAG, volleyError, true);
                        ApiStatusUtil.getInstance().apiStatuError(volleyError, AppraiseDesignerActivity.this);
                    }
                });
    }

    /**
     * 选择星星的监听时间
     */
    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        mRatingProgress = rating;
    }

    @Override
    public void onItemClick(Object object, int position) {
        /**
         * 放弃或者继续编辑
         */
        if (mAppraiseDesignerAlertView == object && position != AlertView.CANCELPOSITION) {
            Intent intent = new Intent();
            intent.putExtra(IS_EVALUATE, false);
            setResult(RESULT_OK, intent);
            finish();
        } else if (mAppraiseDesignerAlertView == object && position == AlertView.CANCELPOSITION) {
            return;
        }
        /**
         * 评论成功执行的操作
         */
        if (mAppraiseDesignerSuccessAlertView == object && position != AlertView.CANCELPOSITION) {
            Intent intent = new Intent();
            intent.putExtra(IS_EVALUATE, true);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    protected void leftNavButtonClicked(View view) {
        abandonAppraiseDesigner();
    }

    @Override
    public void onBackPressed() {
        abandonAppraiseDesigner();
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
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    /**
     * 是否隐藏View
     */
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
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
}
