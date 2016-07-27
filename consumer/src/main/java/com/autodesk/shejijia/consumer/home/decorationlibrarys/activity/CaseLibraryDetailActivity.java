package com.autodesk.shejijia.consumer.home.decorationlibrarys.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.CaseDetailBean;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.uielements.ActionSheetDialog;
import com.autodesk.shejijia.shared.components.common.uielements.ImageShowView;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import java.util.ArrayList;


/**
 * @version 1.0 .
 * @date 2016/3/25 0025 9:53 .
 * @filename CaseLibraryDetailActivity.
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
        intExtra = getIntent().getIntExtra(Constant.CaseLibraryDetail.CASE_DETAIL_POSTION, 0);//获得点击的位置
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
     * 长按图片可以获得图片的url
     *
     * @param position  　图片的索引
     * @param imageView 控件
     */
    @Override
    public void onImageClick(int position, View imageView) {
        Toast.makeText(this, "长按图片" + mImageUrl.get(position), Toast.LENGTH_SHORT).show();
        new ActionSheetDialog(this)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem(getResources().getString(R.string.savelocal), ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                               // cameraPhoto();
                            }
                        })
                .show();
    }


    /**
     * 获取所有图片的url地址
     */
    private void updateViewFromData() {
        for (int i = 0; i < caseDetailBean.getImages().size(); i++) {
            if (null != caseDetailBean && caseDetailBean.getImages().size() != 0) {
                imageUrl = caseDetailBean.getImages().get(i).getFile_url() + Constant.CaseLibraryDetail.JPG;
                mImageUrl.add(imageUrl);
            }
        }
        mImageShowView.setImageResources(mImageUrl, this, intExtra);
    }

    private int intExtra;
    private ImageShowView mImageShowView;
    private String imageUrl;
    private CaseDetailBean caseDetailBean;
    private ArrayList<String> mImageUrl = new ArrayList<>();
}
