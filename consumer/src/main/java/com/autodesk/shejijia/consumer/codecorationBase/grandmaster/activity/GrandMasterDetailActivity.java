package com.autodesk.shejijia.consumer.codecorationBase.grandmaster.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.codecorationBase.grandmaster.entity.MasterDetail;
import com.autodesk.shejijia.consumer.codecorationBase.grandmaster.view.OrderDialogMaster;
import com.autodesk.shejijia.consumer.codecorationBase.packages.view.MyListView;
import com.autodesk.shejijia.consumer.codecorationBase.packages.view.VerticalViewPager;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.utils.ToastUtil;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by allengu on 16-8-23.
 */
public class GrandMasterDetailActivity extends BaseActivity implements View.OnClickListener {


    private ImageButton bt_grand_reservation;
    private TextView tv_detail_cn_name;
    private TextView tv_detail_en_name;
    private TextView tv_detail_cn_position;
    private TextView tv_detail_en_position;
    private TextView tv_detail_content;
    private ImageView iv_detail_desiner;
    private String hs_uid;
    private MasterDetail masterDetail;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_grandmaster_detail;
    }

    @Override
    protected void initView() {

        vvp_viewpager = (VerticalViewPager) findViewById(R.id.vvp_viewpager);
        nav_title_textView = (TextView) findViewById(R.id.nav_title_textView);
        nav_left_imageButton = (ImageButton) findViewById(R.id.nav_left_imageButton);
        bt_grand_reservation = (ImageButton) findViewById(R.id.bt_grand_reservation);
        ib_grand_detail_ico = (ImageButton) findViewById(R.id.ib_grand_detail_ico);

    }

    @Override
    protected void initListener() {
        nav_left_imageButton.setOnClickListener(this);
        bt_grand_reservation.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        initUpPageData();
//        initPageData();
//        initListData();

    }

    private void initUpPageData() {
        Intent intent = getIntent();
        hs_uid = intent.getStringExtra("hs_uid");

        getGrandMasterInfo();
    }

    private void getGrandMasterInfo() {

        MPServerHttpManager.getInstance().getGrandMasterDetailInfo(0, 10, hs_uid, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                ToastUtil.showCustomToast(GrandMasterDetailActivity.this, "获取大师详情信息失败");

            }

            @Override
            public void onResponse(JSONObject jsonObject) {

                String detailInfo = GsonUtil.jsonToString(jsonObject);

                initDetailData(detailInfo);
            }
        });

    }

    private void initDetailData(String detailInfo) {

        masterDetail = GsonUtil.jsonToBean(detailInfo, MasterDetail.class);

        initPageData();

        initListData();
    }

    private void initListData() {
        nav_title_textView.setText("套餐详情");

        tv_detail_cn_name = (TextView) view1.findViewById(R.id.tv_detail_cn_name);
        tv_detail_en_name = (TextView) view1.findViewById(R.id.tv_detail_en_name);
        tv_detail_cn_position = (TextView) view1.findViewById(R.id.tv_detail_cn_position);
        tv_detail_en_position = (TextView) view1.findViewById(R.id.tv_detail_en_position);
        tv_detail_content = (TextView) view1.findViewById(R.id.tv_detail_content);
        iv_detail_desiner = (ImageView) view1.findViewById(R.id.iv_detail_desiner);
        tv_detail_cn_name.setText(masterDetail.getNick_name());
        tv_detail_en_name.setText(masterDetail.getEnglish_name());

        if (TextUtils.isEmpty(masterDetail.getDesigner().getOccupational_cn())) {
            tv_detail_cn_position.setText("后台数据为null");
        } else {
            tv_detail_cn_position.setText(masterDetail.getDesigner().getOccupational_cn());
        }
        if (TextUtils.isEmpty(masterDetail.getDesigner().getOccupational_en())) {
            tv_detail_en_position.setText("后台数据为null");
        } else {
            tv_detail_en_position.setText(masterDetail.getDesigner().getOccupational_en());
        }
        if (TextUtils.isEmpty(masterDetail.getDesigner().getIntroduction())) {
            tv_detail_content.setText("后台数据为null");
        } else {
            tv_detail_content.setText(masterDetail.getDesigner().getIntroduction());
        }
        ImageUtils.displayIconImage(masterDetail.getDesigner().getDesigner_detail_cover().getPublic_url().toString(), iv_detail_desiner);

        ll_grand_master_detail = (MyListView) view2.findViewById(R.id.ll_grand_master_detail);
        ll_grand_master_detail.setAdapter(new MasterAdapter());

    }

    private void initPageData() {

        LayoutInflater lf = LayoutInflater.from(this);
        view1 = lf.inflate(R.layout.viewpager_grandmaster_one, null);
        view2 = lf.inflate(R.layout.viewpager_grandmaster_two, null);
        viewList = new ArrayList<View>();
        viewList.add(view1);
        viewList.add(view2);

        vvp_viewpager.setAdapter(pagerAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_left_imageButton:
                finish();
                break;
            case R.id.bt_grand_reservation:
                OrderDialogMaster orderDialog = new OrderDialogMaster(GrandMasterDetailActivity.this, R.style.add_dialog, R.drawable.tital_yuyue);
                orderDialog.setListenser(new OrderDialogMaster.CommitListenser() {
                    @Override
                    public void commitListener(String name, String phoneNumber) {

                    }
                });
                orderDialog.show();
                break;
        }
    }

    protected PagerAdapter pagerAdapter = new PagerAdapter() {

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {

            return arg0 == arg1;
        }

        @Override
        public int getCount() {

            return viewList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            container.removeView(viewList.get(position));

        }

        @Override
        public int getItemPosition(Object object) {

            return super.getItemPosition(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ViewGroup view_parent = (ViewGroup) viewList.get(position).getParent();
            if (view_parent != null) {
                view_parent.removeView(viewList.get(position));
            }
            container.addView(viewList.get(position));

            if (position == 0) {
                nav_title_textView.setText("套餐详情");
                vvp_viewpager.setBackground(null);
            } else if (position == 1) {
                nav_title_textView.setText("大师详情页");
                vvp_viewpager.setBackgroundResource(R.drawable.master_bgone);
                vvp_viewpager.setBackground(null);
            }
            return viewList.get(position);
        }

    };


    class MasterAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list_Detail.length;
        }

        @Override
        public Object getItem(int position) {
            return list_Detail[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            MyHolder mhHolder;
            if (convertView == null) {
                mhHolder = new MyHolder();
                convertView = LayoutInflater.from(GrandMasterDetailActivity.this).inflate(R.layout.item_listview_grandmaster_detail, null);
                mhHolder.iv_detail = (ImageView) convertView.findViewById(R.id.iv_item_listview_pic);
                mhHolder.tv_cn_name = (TextView) convertView.findViewById(R.id.tv_item_listview_cn_tital);
                mhHolder.tv_en_name = (TextView) convertView.findViewById(R.id.tv_item_listview_en_tital);

                convertView.setTag(mhHolder);
            }

            mhHolder = (MyHolder) convertView.getTag();

            mhHolder.iv_detail.setImageResource(list_Detail[position]);
            mhHolder.tv_cn_name.setText("小明" + position);
            mhHolder.tv_en_name.setText("xiaoming" + position);

            return convertView;
        }

        class MyHolder {

            TextView tv_cn_name;
            TextView tv_en_name;
            ImageView iv_detail;

        }
    }

    Integer[] list_Detail = {R.drawable.common_case_icon, R.drawable.common_case_icon, R.drawable.common_case_icon,
            R.drawable.common_case_icon, R.drawable.common_case_icon, R.drawable.common_case_icon};
    private VerticalViewPager vvp_viewpager;
    private ArrayList<View> viewList;
    private View view1;
    private View view2;
    private MyListView ll_grand_master_detail;
    private ImageButton ib_grand_detail_ico;
    private TextView nav_title_textView;
    private ImageButton nav_left_imageButton;
}
