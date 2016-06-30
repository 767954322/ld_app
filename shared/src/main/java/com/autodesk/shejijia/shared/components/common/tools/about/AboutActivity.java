package com.autodesk.shejijia.shared.components.common.tools.about;

import com.autodesk.shejijia.shared.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 16-6-7 下午1:08
 * @file AboutActivity.java  .
 * @brief 关于设计家.
 */
public class AboutActivity extends NavigationBarActivity implements View.OnClickListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView() {
        super.initView();
        tv_version = (TextView) findViewById(R.id.tv_version);
        rl_about_designer_introduced = (RelativeLayout) findViewById(R.id.rl_about_designer_introduced);
        rl_about_designer_contact = (RelativeLayout) findViewById(R.id.rl_about_designer_contact);
        rl_about_version_introduced = (RelativeLayout) findViewById(R.id.rl_about_version_introduced);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        setTitleForNavbar(UIUtils.getString(R.string.designer_home_about));
        tv_version.setText(Constant.VERSION_NUMBER);
    }

    @Override
    protected void initListener() {
        super.initListener();
        rl_about_designer_introduced.setOnClickListener(this);
        rl_about_designer_contact.setOnClickListener(this);
        rl_about_version_introduced.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int i = v.getId();
        if (i == R.id.rl_about_designer_introduced)
        {
            intent = new Intent(AboutActivity.this, AboutDesignerIntroducedActivity.class);
            startActivity(intent);

        }
        else if (i == R.id.rl_about_designer_contact)
        {
            intent = new Intent(AboutActivity.this, AboutDesignerContactActivity.class);
            startActivity(intent);

        }
        else if (i == R.id.rl_about_version_introduced)
        {
            intent = new Intent(AboutActivity.this, AboutVersionIntroducedActivity.class);
            startActivity(intent);

        }
    }

    ///　控件.
    private TextView tv_version;
    private RelativeLayout rl_about_designer_introduced;
    private RelativeLayout rl_about_designer_contact;
    private RelativeLayout rl_about_version_introduced;

}
