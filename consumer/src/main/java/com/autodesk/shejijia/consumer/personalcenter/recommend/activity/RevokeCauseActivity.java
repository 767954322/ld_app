package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.manager.constants.JsonConstants;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RefreshEvent;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;


/**
 * @User: 蜡笔小新
 * @date: 16-11-9
 * @GitHub: https://github.com/meikoz
 */

public class RevokeCauseActivity extends NavigationBarActivity implements View.OnClickListener, TextWatcher {

    private EditText mEditCause;
    private int mAssetId;
    private Button mRevokeBtn;
    private TextView mEditLength;
    private String mMsg;

    public static void jumpTo(Context context, int assetId, String msg) {
        Intent intent = new Intent(context, RevokeCauseActivity.class);
        intent.putExtra(JsonConstants.ASSET_ID, assetId);
        intent.putExtra(JsonConstants.MSG, msg);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_revoke_cause;
    }

    @Override
    protected void initView() {
        super.initView();
        mRevokeBtn = (Button) findViewById(R.id.btn_revoke_make_sure);
        mEditCause = (EditText) findViewById(R.id.edit_revoke_cause);
        mEditLength = (TextView) findViewById(R.id.tv_edit_length);

    }

    @Override
    protected void initListener() {
        super.initListener();
        mRevokeBtn.setOnClickListener(this);
        mRevokeBtn.setClickable(false);
        mEditCause.addTextChangedListener(this);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        mAssetId = getIntent().getIntExtra(JsonConstants.ASSET_ID, 0);
        mMsg = getIntent().getStringExtra(JsonConstants.MSG);
        setTitleForNavbar(mMsg + UIUtils.getString(R.string.input_case));
        mEditCause.setHint(UIUtils.getString(R.string.please_input_hint) + mMsg + UIUtils.getString(R.string.please_input_hint_case));
    }

    @Override
    public void onClick(View v) {
        CustomProgress.show(this, "", false, null);
        String revokeCause = mEditCause.getText().toString().trim();
        if (TextUtils.isEmpty(revokeCause)) {
            Toast.makeText(this, R.string.no_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        final JSONObject object1 = new JSONObject();
        try {
            object1.put("remark", revokeCause);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        revokeRecommend(mAssetId, object1);
    }

    //撤销和退回逻辑
    private void revokeRecommend(int id, JSONObject object1) {
        MPServerHttpManager.getInstance().revokeRecommend(id, object1, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CustomProgress.cancelDialog();
                Toast.makeText(RevokeCauseActivity.this, R.string.revoke_failer, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(JSONObject object) {
                CustomProgress.cancelDialog();
                onResponseSuccess();
            }
        });
    }

    private void onResponseSuccess() {
        new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.submit_successful), null, null, new String[]{UIUtils.getString(R.string.sure)}, RevokeCauseActivity.this, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object object, int position) {
                if (position != -1) {
                    EventBus.getDefault().post(new RefreshEvent());
                    finish();
                }
            }
        }).show();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        mEditLength.setText(s.length() + "/200");
        if (s.toString().length() > 0) {
            mRevokeBtn.setClickable(true);
            mRevokeBtn.setBackgroundResource(R.drawable.bg_common_btn_blue);
        } else {
            mRevokeBtn.setClickable(false);
            mRevokeBtn.setBackgroundResource(R.drawable.bg_common_btn_gray);
        }
    }


}
