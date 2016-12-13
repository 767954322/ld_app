package com.autodesk.shejijia.consumer.codecorationBase.packages.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.codecorationBase.packages.adapter.BaseCommonAdapter;
import com.autodesk.shejijia.consumer.codecorationBase.packages.view.ImageUrlUtils;
import com.autodesk.shejijia.consumer.utils.HttpUtils;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.utility.LoginUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by allengu on 16-8-24.
 */
public class PackageDetailActivity extends BaseActivity implements View.OnClickListener {

    private ListView mListView;
    private List<Bitmap> mBitmapList;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_package_detail;
    }

    @Override
    protected void initView() {
        nav_left_imageButton = (ImageButton) findViewById(R.id.nav_left_imageButton);
        bt_packages_yuyue = (ImageButton) findViewById(R.id.bt_packages_yuyue);
        nav_title_textView = (TextView) findViewById(R.id.nav_title_textView);
//        iv_package_detail = (ImageView) findViewById(R.id.iv_package_detail);
        mListView = (ListView) findViewById(R.id.package_detail_activity_lv);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        Intent intent = getIntent();
        item_num = intent.getIntExtra("item_num", -1);
        nav_title_textView.setText("套餐详情");
        isLoginUserJust = isLoginUser();
    }

    public List<Bitmap> getBitMapList(byte[] data, View view, int number) {
        int viewWidth = view.getWidth();
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;   //只去读图片的附加信息,不去解析真实的位图
        BitmapFactory.decodeByteArray(data, 0, data.length, opts);
        int picWidth = opts.outWidth;// 得到图片宽度
        int picHeight = opts.outHeight;// 得到图片高度
        opts.inJustDecodeBounds = false;//真正的去解析位图
        opts.outHeight = picHeight * viewWidth / picWidth;
        opts.outWidth = viewWidth;
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, opts);
        //新图片的宽高
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int itemHeight = bitmapHeight / number;
        List<Bitmap> list = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            Bitmap bit = Bitmap.createBitmap(bitmap, 0, itemHeight * i, bitmapWidth, itemHeight);
            list.add(bit);
        }
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return list;
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
                    LoginUtils.doLogin(PackageDetailActivity.this);
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
        Log.i("sss", ImageUrlUtils.getPackagesDetailImage()[item_num]);
        HttpUtils.doDownload(ImageUrlUtils.getPackagesDetailImage()[item_num], new HttpUtils.DownloadImageListener() {
            @Override
            public void onStart() {
                CustomProgress.show(PackageDetailActivity.this, "", false, null);
            }

            @Override
            public void onSuccessed(byte[] data) {
                CustomProgress.cancelDialog();
                mBitmapList = getBitMapList(data, mListView, 10);
//                iv_package_detail.setImageBitmap(list.get(3));

                BaseCommonAdapter<Bitmap> adapter = new BaseCommonAdapter<Bitmap>(PackageDetailActivity.this, mBitmapList) {
                    @Override
                    public void convert(ViewHolder holder, Bitmap item, int position, View convertView, ViewGroup parent) {
                        ImageView imageView = holder.getView(R.id.item_package_detail_activity_iv);
//                        if (position == 1)
                        imageView.setImageBitmap(item);
                    }

                    @Override
                    public ViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
                        return ViewHolder.get(PackageDetailActivity.this, convertView, parent, R.layout.item_package_detail_activity, position);
                    }
                };
                mListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled() {
                CustomProgress.cancelDialog();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mBitmapList != null){
            for (Bitmap bitmap : mBitmapList){
                if (!bitmap.isRecycled()) {
                    bitmap.recycle();
                }
            }
            mBitmapList = null;
        }
        finish();
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
//    private ImageView iv_package_detail;

    private TextView nav_title_textView;
    private ImageButton bt_packages_yuyue;
    private ImageButton nav_left_imageButton;
}
