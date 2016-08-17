package com.autodesk.shejijia.consumer.home.decorationlibrarys.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

/**
 * @author allengu .
 * @version 1.0 .
 * @date 16-7-25 0028 11:22 .
 * @file GalleryUrlActivity  .
 * @brief 迭代，查看大图页面 .
 */
public class CaseLibraryDetailsPageActivity extends BaseActivity {


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_case_library_page;
    }

    @Override
    protected void initView() {
        iv_case_library_img = (ImageView) findViewById(R.id.iv_case_library_img);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        imageUrl = (String) getIntent().getExtras().get("imageUrl");

    }

    @Override
    protected void initData(Bundle savedInstanceState) {



        ImageUtils.loadUserAvatar(iv_case_library_img, imageUrl);
    }

    private String imageUrl;
    private ImageView iv_case_library_img;
}
