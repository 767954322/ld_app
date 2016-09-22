package com.autodesk.shejijia.consumer.codecorationBase.packages.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.codecorationBase.packages.adapter.BaseCommonAdapter;
import com.autodesk.shejijia.consumer.codecorationBase.packages.view.ImageUrlUtils;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by allengu on 16-8-24.
 */
public class PackageDetailActivity extends BaseActivity implements View.OnClickListener {


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_package_detail;
    }

    @Override
    protected void initView() {
        nav_left_imageButton = (ImageButton) findViewById(R.id.nav_left_imageButton);
        bt_packages_yuyue = (ImageButton) findViewById(R.id.bt_packages_yuyue);
        nav_title_textView = (TextView) findViewById(R.id.nav_title_textView);
        iv_package_detail = (ImageView) findViewById(R.id.iv_package_detail);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        Intent intent = getIntent();
        item_num = intent.getIntExtra("item_num", -1);
        nav_title_textView.setText("套餐详情");
        isLoginUserJust = isLoginUser();

        ImageUtils.displaySixImage(ImageUrlUtils.getPackagesDetailImage()[item_num], iv_package_detail);
    }

    @Override
    protected void initListener() {
        nav_left_imageButton.setOnClickListener(this);
        bt_packages_yuyue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_left_imageButton:

                PackageDetailActivity.this.finish();

                break;
            case R.id.bt_packages_yuyue:


                if (isLoginUserJust) {
                    Intent intent_yuyue = new Intent(PackageDetailActivity.this, ReservationFormActivity.class);
                    intent_yuyue.putExtra("item_num", item_num + 1);
                    startActivity(intent_yuyue);
                } else {
                    AdskApplication.getInstance().doLogin(PackageDetailActivity.this);
                }

                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isLoginUserJust = isLoginUser();
    }

    //判断该用户是否登陆了
    public boolean isLoginUser() {

        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();

        if (memberEntity == null) {

            return false;//未登录
        } else {

            return true;//已登录
        }
    }

    private int item_num;
    private List<Bitmap> mData;
    private ListView lv_package_detail;
    private boolean isLoginUserJust;
    private ImageView iv_package_detail;

    private TextView nav_title_textView;
    private ImageButton bt_packages_yuyue;
    private ImageButton nav_left_imageButton;
}
