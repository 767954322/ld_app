package com.autodesk.shejijia.shared.components.common.uielements.commentview.cropimage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.photoselect.base.PhotoSelectBaseActivity;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.utils.ActivityUtils;

public class CropActivity extends PhotoSelectBaseActivity implements CropFragment.CropImageListener {

    private CropFragment mCropFragment;

    public static final String CROP_RESULT = "cropResult";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        if (savedInstanceState == null) {
            String iamgePath = getIntent().getStringExtra(CropFragment.ARG_IMAGE_PATH);
            mCropFragment = CropFragment.newInstance(iamgePath);
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    mCropFragment,
                    CropFragment.TAG,
                    false
            );
        }

        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCropFragment != null)
                    mCropFragment.cropImage();
            }
        });
    }



    @Override
    public void onCropCompleted(String path) {
        Intent intent = new Intent();
        intent.putExtra(CROP_RESULT, path);
        setResult(RESULT_OK, intent);
        finish();
    }

}
