package com.autodesk.shejijia.consumer.codecorationBase.grandmaster.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.codecorationBase.grandmaster.entity.DatailCase;
import com.autodesk.shejijia.consumer.codecorationBase.grandmaster.entity.MasterDetail;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter.BaseCommonRvAdapter;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

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
//        nav_title_textView.setText("大师详情");
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
    protected void initData(Bundle savedInstanceState) {
        nav_left_imageButton.setOnClickListener(this);
        bt_grand_reservation.setOnClickListener(this);


        List<DatailCase> list = new ArrayList<>();
        for (int i = 0 ; i < 50; i++){
            DatailCase datailCase = new DatailCase();
            list.add(datailCase);
        }

        BaseCommonRvAdapter<DatailCase> adapter1 = new BaseCommonRvAdapter<DatailCase>(this,R.layout.item_listview_grandmaster_detail,list) {
            @Override
            public void convert(ViewHolder holder, DatailCase item, int position) {
                ImageView imageView = holder.getView(R.id.iv_item_listview_pic);
                TextView textView = holder.getView(R.id.tv_item_listview_cn_tital);
//                ImageUtils.displayIconImage("",imageView);
//                textView.setText("");
            }
        };

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter1);

    }

    @Override
    public void onClick(View v) {

    }
}
