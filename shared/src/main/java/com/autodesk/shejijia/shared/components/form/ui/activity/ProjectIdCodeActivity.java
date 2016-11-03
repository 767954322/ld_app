package com.autodesk.shejijia.shared.components.form.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.form.contract.ProjectIdCodeContract;
import com.autodesk.shejijia.shared.components.form.presenter.ProjectIdCodePresenter;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

public class ProjectIdCodeActivity extends BaseActivity implements View.OnClickListener,ProjectIdCodeContract.View {

    private TextView mCenter;
    private EditText mProjectId;
    private ProjectIdCodePresenter mPresenter;
    private TextView mRight;
    private ImageView mLeft;
    private Button mConfirm;
    private RelativeLayout mNavigationBar;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_projectid_code;
    }

    @Override
    protected void initView() {
        mNavigationBar = (RelativeLayout) findViewById(R.id.rl_navigationbar);
        mCenter = (TextView)findViewById(R.id.tv_center);
        mLeft = (ImageView) findViewById(R.id.iv_left);
        mRight = (TextView) findViewById(R.id.tv_right);

        mProjectId = (EditText) findViewById(R.id.et_project_id);
        mConfirm = (Button) findViewById(R.id.btn_confirm);
    }

    @Override
    protected void initExtraBundle() {
        mPresenter = new ProjectIdCodePresenter(this, this);  //初始化Presenter类
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {
        mRight.setOnClickListener(this);
        mConfirm.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_right) {// TODO: 16/10/18 进入扫二维码的页面,和主页面接起来的时候一起做
            mPresenter.enterCode();

        } else if (i == R.id.btn_confirm) {// TODO: 16/10/18 根据登入状态,项目编码一步
            mPresenter.ConfirmProject();

        }

    }

    @Override
    public void setNavigationBar() {
        mNavigationBar.setBackgroundResource(R.color.form_bar_bg_blue);
        mCenter.setText("输入编码");
        mCenter.setTextColor(UIUtils.getColor(R.color.form_text_bar_write));
        mRight.setText("进入扫码");
        mRight.setTextColor(UIUtils.getColor(R.color.form_text_bar_write));
        mLeft.setVisibility(View.GONE);
    }

    @Override
    public String getProjectId() {
        return mProjectId.getText().toString().trim();
    }

    @Override
    public void dismiss() {
        finish();
    }


    @Override
 public void showNetError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
