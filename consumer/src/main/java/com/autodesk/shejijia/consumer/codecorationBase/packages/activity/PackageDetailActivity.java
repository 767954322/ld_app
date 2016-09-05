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

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.codecorationBase.packages.adapter.BaseCommonAdapter;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

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
        lv_package_detail = (ListView) findViewById(R.id.lv_package_detail);
        nav_left_imageButton = (ImageButton) findViewById(R.id.nav_left_imageButton);
        bt_packages_yuyue = (ImageButton) findViewById(R.id.bt_packages_yuyue);
        nav_title_textView = (TextView) findViewById(R.id.nav_title_textView);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        Intent intent = getIntent();
        item_num = intent.getIntExtra("item_num", -1);
        item_name = intent.getStringExtra("item_name");
        nav_title_textView.setText("套餐详情");
        mData = new ArrayList<>();
        mAdapter = new BaseCommonAdapter<Bitmap>(this, mData) {
            @Override
            public void convert(ViewHolder holder, Bitmap item, int position, View convertView, ViewGroup parent) {
                ImageView imageview = holder.getView(R.id.item_package_detail_activity_iv);
                imageview.setImageBitmap(item);
            }

            @Override
            public ViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
                return ViewHolder.get(PackageDetailActivity.this, convertView, parent, R.layout.item_package_detail_activity, position);
            }
        };
        mData.clear();
        mData.addAll(setBitmap(this, pic_detail[item_num], lv_package_detail, 10));
        lv_package_detail.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

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

                Intent intent_yuyue = new Intent(PackageDetailActivity.this, ReservationFormActivity.class);
                intent_yuyue.putExtra("item_num", item_num);
                intent_yuyue.putExtra("item_name", item_name);
                startActivity(intent_yuyue);

                break;
            default:
                break;
        }
    }

    //设置显示的图片
    public List<Bitmap> setBitmap(Context context, int resource, View view, int number) {
        int viewWidth = view.getWidth();
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;   //只去读图片的附加信息,不去解析真实的位图
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), resource, opts);
        int picWidth = opts.outWidth;// 得到图片宽度
        int picHeight = opts.outHeight;// 得到图片高度
        opts.outHeight = picHeight * viewWidth / picWidth;
        opts.outWidth = viewWidth;
        opts.inJustDecodeBounds = false;//真正的去解析位图
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resource, opts);
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

    private Integer[] pic_detail = {R.drawable.xijing, R.drawable.dongjie, R.drawable.xingfujia, R.drawable.beishu,
            R.drawable.yishujia, R.drawable.nanyun, R.drawable.pinzhijia};//套餐详情集合

    private int item_num;
    private String item_name;
    private List<Bitmap> mData;
    private ListView lv_package_detail;
    private TextView nav_title_textView;
    private ImageButton bt_packages_yuyue;
    private ImageButton nav_left_imageButton;
    private BaseCommonAdapter<Bitmap> mAdapter;

}
