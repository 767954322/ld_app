package com.autodesk.shejijia.consumer.home.decorationlibrarys.activity;

import android.os.Bundle;
import android.view.View;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.CaseDetailBean;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.uielements.ImageShowView;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import java.util.ArrayList;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 2016/3/25 0025 9:53 .
 * @filename CaseLibraryDetailActivity.
 * @brief 案例详情页面.
 */
public class CaseLibraryDetailActivity extends NavigationBarActivity implements ImageShowView.ImageShowViewListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_case_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        mImageShowView = (ImageShowView) findViewById(R.id.ad_view);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        caseDetailBean = (CaseDetailBean) getIntent().getSerializableExtra(Constant.CaseLibraryDetail.CASE_DETAIL_BEAN);
        intExtra = getIntent().getIntExtra(Constant.CaseLibraryDetail.CASE_DETAIL_POSTION, 0);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        updateViewFromData();
    }

    @Override
    protected void initListener() {
        super.initListener();
    }


    /**
     * 查看大图监听
     *
     * @param position  　图片的索引
     * @param imageView 控件
     */
    @Override
    public void onImageClick(int position, View imageView) {
//        String url = (String) imageView.getTag();
//        Intent intent = new Intent(CaseLibraryDetailActivity.this, GalleryUrlActivity.class);
//        intent.putExtra(Constant.CaseLibraryDetail.CASE_URL, url);
//        startActivity(intent);
//        overridePendingTransition(R.anim.my_scale_action, R.anim.my_alpha_action);
    }


    private void updateViewFromData() {
        for (int i = 0; i < caseDetailBean.getImages().size(); i++) {
            if (null != caseDetailBean && caseDetailBean.getImages().size() != 0) {
                imageUrl = caseDetailBean.getImages().get(i).getFile_url() + Constant.CaseLibraryDetail.JPG;
                mImageUrl.add(imageUrl);
            }
        }
        mImageShowView.setImageResources(mImageUrl, this,intExtra);
    }
    private int intExtra;
    private ImageShowView mImageShowView;
    private String imageUrl;
    private CaseDetailBean caseDetailBean;
    private ArrayList<String> mImageUrl = new ArrayList<>();
}
