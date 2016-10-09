package com.autodesk.shejijia.consumer.tools.about;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.LinearLayout;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 16-6-7 下午1:09
 * @file AboutDesignerContactActivity.java  .
 * @brief 关于设计家-联系方式.
 */

public class AboutDesignerContactActivity extends NavigationBarActivity implements View.OnClickListener, OnItemClickListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_about_designer_contact;
    }

    @Override
    protected void initView() {
        super.initView();
        ll_about_designer_contact = (LinearLayout) findViewById(R.id.ll_about_designer_contact);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        setTitleForNavbar(UIUtils.getString(R.string.contact));

        showDialog();
    }

    /**
     * 提示框
     */
    private void showDialog() {
        mAlertView = new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.call_number), UIUtils.getString(R.string.cancel), new String[]{UIUtils.getString(R.string.call_phone)}, null, this, AlertView.Style.Alert, this).setCancelable(false);
    }

    @Override
    protected void initListener() {
        super.initListener();
        ll_about_designer_contact.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ll_about_designer_contact) {
            mAlertView.show();

        }
    }

    /**
     * 提示框监听
     *
     * @param o
     * @param position
     */
    @Override
    public void onItemClick(Object o, int position) {
        if (o == mAlertView && position != AlertView.CANCELPOSITION) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "400-650-3333"));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(intent);
        }
    }

    ///　控件.
    private LinearLayout ll_about_designer_contact;
    private AlertView mAlertView;
}
