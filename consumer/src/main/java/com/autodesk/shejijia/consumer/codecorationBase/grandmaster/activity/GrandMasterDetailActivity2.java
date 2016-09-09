package com.autodesk.shejijia.consumer.codecorationBase.grandmaster.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.codecorationBase.grandmaster.entity.DatailCase;
import com.autodesk.shejijia.consumer.codecorationBase.grandmaster.entity.MasterDetail;
import com.autodesk.shejijia.consumer.codecorationBase.packages.view.MyListView;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter.BaseCommonRvAdapter;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by allengu on 16-8-23.
 */
public class GrandMasterDetailActivity2 extends BaseActivity implements View.OnClickListener {

    private ImageButton nav_left_imageButton;
    private ImageButton bt_grand_reservation;
    private ImageButton ib_grand_detail_ico;
    private ImageView iv_detail_desiner;
    private TextView tv_detail_cn_name;
    private TextView tv_detail_en_name;
    private TextView tv_detail_cn_position;
    private TextView tv_detail_en_position;
    private TextView tv_detail_content;
    private TextView nav_title_textView;
    private RelativeLayout rl_navr_header;
    private boolean isLoginUserJust = false;

    private String hs_uid;
    private List<DatailCase> cases_list;
    private ArrayList<View> viewList;
    private MasterDetail masterDetail;


    private ImageView mHeadImageView;

    private RecyclerView mRecyclerView;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_grandmaster_detail2;
    }

    @Override
    protected void initView() {

        nav_title_textView = (TextView) findViewById(R.id.nav_title_textView);
        nav_left_imageButton = (ImageButton) findViewById(R.id.nav_left_imageButton);
        bt_grand_reservation = (ImageButton) findViewById(R.id.bt_grand_reservation);
        ib_grand_detail_ico = (ImageButton) findViewById(R.id.ib_grand_detail_ico);
        rl_navr_header = (RelativeLayout) findViewById(R.id.rl_navr_header);
        tv_detail_cn_name = (TextView) findViewById(R.id.tv_detail_cn_name);
        tv_detail_en_name = (TextView) findViewById(R.id.tv_detail_en_name);
        tv_detail_content = (TextView) findViewById(R.id.tv_detail_content);
        iv_detail_desiner = (ImageView) findViewById(R.id.iv_detail_desiner);
        tv_detail_cn_position = (TextView) findViewById(R.id.tv_detail_cn_position);
        tv_detail_en_position = (TextView) findViewById(R.id.tv_detail_en_position);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

    }


    @Override
    protected void initListener() {
        super.initListener();

        nav_left_imageButton.setOnClickListener(this);
        bt_grand_reservation.setOnClickListener(this);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {


        Intent intent = getIntent();
        hs_uid = intent.getStringExtra("hs_uid");
        nav_title_textView.setText("大师详情");
        isLoginUserJust = isLoginUser();

        getGrandMasterInfo();

    }

    @Override
    public void onClick(View v) {

    }

    //获取大师详情信息
    private void getGrandMasterInfo() {

        MPServerHttpManager.getInstance().getGrandMasterDetailInfo(0, 10, hs_uid, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ApiStatusUtil.getInstance().apiStatuError(volleyError, GrandMasterDetailActivity2.this);

            }

            @Override
            public void onResponse(JSONObject jsonObject) {

                String detailInfo = GsonUtil.jsonToString(jsonObject);

                initDetailData(detailInfo);
            }
        });

    }

    //初始化详情信息
    private void initDetailData(String detailInfo) {

        masterDetail = GsonUtil.jsonToBean(detailInfo, MasterDetail.class);
        cases_list = masterDetail.getCases_list();


        //初始化大师详情
        initPageTopInfo();

        //初始化大师案例展示
        initPageBottomInfo();

    }

    //初始化第一页内容
    private void initPageTopInfo() {

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
        if (null != masterDetail.getDesigner() && null != masterDetail.getDesigner().getDesigner_detail_cover_app() && null != masterDetail.getDesigner().getDesigner_detail_cover_app().getPublic_url()) {
            String img_url = masterDetail.getDesigner().getDesigner_detail_cover_app().getPublic_url();
            ImageUtils.displayIconImage(img_url, iv_detail_desiner);
        }

    }


    private void initPageBottomInfo(){

        BaseCommonRvAdapter<DatailCase> adapter1 = new BaseCommonRvAdapter<DatailCase>(this, R.layout.item_listview_grandmaster_detail, cases_list) {
            @Override
            public void convert(ViewHolder holder, DatailCase item, int position) {
                ImageView imageView = holder.getView(R.id.iv_item_listview_pic);
                TextView textView = holder.getView(R.id.tv_item_listview_cn_tital);
                ImageUtils.displayIconImage(cases_list.get(position).getImages().get(0).getFile_url() + "HD.png", imageView);
                textView.setText(cases_list.get(position).getTitle());
            }
        };

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter1);


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

}
