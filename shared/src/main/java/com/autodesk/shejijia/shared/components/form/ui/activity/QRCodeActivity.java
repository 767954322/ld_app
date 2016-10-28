package com.autodesk.shejijia.shared.components.form.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.tools.CaptureQrActivity;

/**
 * Created by t_aij on 16/10/25.
 */

public class QRCodeActivity extends CaptureQrActivity {

    @Override
    protected void initListener() {
        super.initListener();
        setNavigationBar();
    }

    private void setNavigationBar() {
        ImageButton imageButton = (ImageButton) findViewById(com.autodesk.shejijia.shared.R.id.nav_left_imageButton);
        imageButton.setVisibility(View.GONE);

        TextView rightText = (TextView) findViewById(R.id.nav_right_textView);
        rightText.setVisibility(View.VISIBLE);
        rightText.setText("输入编码");
        rightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QRCodeActivity.this,ProjectIdCodeActivity.class));
                finish();
            }
        });

    }
}
