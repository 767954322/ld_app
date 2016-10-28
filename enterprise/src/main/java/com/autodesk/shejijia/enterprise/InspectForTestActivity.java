package com.autodesk.shejijia.enterprise;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.autodesk.shejijia.shared.components.form.ui.activity.QRCodeActivity;

/**
 * Created by t_aij on 16/10/28.
 */

public class InspectForTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, QRCodeActivity.class));
        finish();
    }
}
