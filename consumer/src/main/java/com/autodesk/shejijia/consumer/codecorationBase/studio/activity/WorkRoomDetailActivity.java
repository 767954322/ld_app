package com.autodesk.shejijia.consumer.codecorationBase.studio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.codecorationBase.studio.adapter.WorkRoomDesignerAdapter;
import com.autodesk.shejijia.consumer.codecorationBase.studio.dialog.OrderDialog;
import com.autodesk.shejijia.consumer.codecorationBase.studio.entity.DesignerRetrieveRsp;
import com.autodesk.shejijia.consumer.codecorationBase.studio.entity.WorkRoomDetailsBeen;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.utils.HeightUtils;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yaoxuehua .
 * @version 1.0 .
 * @date 2016/8/22 0025 14:46 .
 * @file WorkRoomDetailActivity  .
 * @brief 查看工作室详情页面 .
 */
public class WorkRoomDetailActivity extends NavigationBarActivity implements View.OnClickListener {
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_work_room;
    }

    @Override
    protected void initView() {

        workRoomDetailHeader = LayoutInflater.from(this).inflate(R.layout.header_work_room_detail, null);
        back = (ImageView) workRoomDetailHeader.findViewById(R.id.work_room_back);
        work_room_detail_content = (LinearLayout) findViewById(R.id.work_room_detail_content);
        work_room_detail_content.addView(workRoomDetailHeader);
        listView = (ListView) findViewById(R.id.listview);
//        work_room_design_imageView = (ImageView) workRoomDetailHeader.findViewById(R.id.work_room_design_imageView);
        header_work_room_name = (TextView) workRoomDetailHeader.findViewById(R.id.header_work_room_name);
        header_work_room_design_year = (TextView) workRoomDetailHeader.findViewById(R.id.header_work_room_design_year);
        work_room_introduce = (TextView) workRoomDetailHeader.findViewById(R.id.work_room_introduce);
        work_room_name_title = (TextView) workRoomDetailHeader.findViewById(R.id.work_room_name_title);
        work_room_detail_six_imageView = (ImageView) workRoomDetailHeader.findViewById(R.id.work_room_detail_six_imageView);

        now_order_details = (TextView) findViewById(R.id.now_order_details);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        Intent intent = getIntent();
        hs_uid = intent.getStringExtra("hs_uid");

    }

    @Override
    protected void initListener() {
        super.initListener();
        back.setOnClickListener(this);
        now_order_details.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        getWorkRoomDetailData(acs_member_id, 0, 10, hs_uid);

        isLoginUserJust = isLoginUser();
    }

    public void upDataForView(WorkRoomDetailsBeen workRoomDetailsBeen) {

            work_room_name_title.setText(workRoomDetailsBeen.getNick_name());
            header_work_room_name.setText(workRoomDetailsBeen.getNick_name());
            if (workRoomDetailsBeen.getDesigner() != null && workRoomDetailsBeen.getDesigner().getDesigner_profile_cover() != null) {

                ImageUtils.loadImage(work_room_detail_six_imageView, workRoomDetailsBeen.getDesigner().getDesigner_profile_cover().getPublic_url().replace(" ", ""));
            }
            work_room_introduce.setText("工作室介绍：" + workRoomDetailsBeen.getDesigner().getIntroduction());

            if (workRoomDetailsBeen.getDesigner() != null) {
                //目前没有年限，但是PD还没有确定以后有没有，所以暂时GONE掉，需要时直接解开
                header_work_room_design_year.setText("设计年限 ：" + workRoomDetailsBeen.getDesigner().getExperience() + "年");
                header_work_room_design_year.setVisibility(View.GONE);
            }
            if (workRoomDetailsBeen.getCases_list() != null) {

                WorkRoomDesignerAdapter workRoomAdapter = new WorkRoomDesignerAdapter(this, listMain, workRoomDetailsBeen.getCases_list());
                listView.setAdapter(workRoomAdapter);
                HeightUtils.setListViewHeightBasedOnChildren(listView);
            }


    }

    //获取主案设计师
    public void getDesignerDataForView(WorkRoomDetailsBeen workRoomDetailsBeen) {

        if (workRoomDetailsBeen.getMain_designers() != null && workRoomDetailsBeen.getMain_designers().size() != 0) {


            int mainLength = workRoomDetailsBeen.getMain_designers().size();//判断主案设计师item数量
            if (mainLength % 3 == 0) {

                mainLength = mainLength / 3;
            } else {

                mainLength = mainLength / 3 + 1;
            }
            WorkRoomDetailsBeen.MainDesignersBean[] mainDesigner;
            for (int i = 0; i < mainLength; i++) {//将主案设计师按数量添加不同的组，方便显示
                int count = 0;
                if (count < workRoomDetailsBeen.getMain_designers().size()) {


                    if (mainLength == 1) {//只有一行的时候判断满不满三个


                        if (workRoomDetailsBeen.getMain_designers().size() == 3) {

                            mainDesigner = new WorkRoomDetailsBeen.MainDesignersBean[3];
                            for (int j = 0; j < 3; j++) {
                                mainDesigner[j] = workRoomDetailsBeen.getMain_designers().get(count);
                                count++;
                            }
                        } else {


                            mainDesigner = new WorkRoomDetailsBeen.MainDesignersBean[workRoomDetailsBeen.getMain_designers().size() % 3];

                            for (int j = 0; j < workRoomDetailsBeen.getMain_designers().size() % 3; j++) {
                                mainDesigner[j] = workRoomDetailsBeen.getMain_designers().get(count);
                                count++;

                            }

                        }


                    } else {//多行的时候判断是否是满行，还是最后一行；

                        if (i + 1 < mainLength) {

                            mainDesigner = new WorkRoomDetailsBeen.MainDesignersBean[3];
                            for (int j = 0; j < 3; j++) {
                                mainDesigner[j] = workRoomDetailsBeen.getMain_designers().get(count);
                                count++;
                            }

                        } else {

                            mainDesigner = new WorkRoomDetailsBeen.MainDesignersBean[workRoomDetailsBeen.getMain_designers().size() % 3];

                            for (int j = 0; j < workRoomDetailsBeen.getMain_designers().size() % 3; j++) {
                                mainDesigner[j] = workRoomDetailsBeen.getMain_designers().get(count);
                                count++;

                            }

                        }


                    }
                    listMain.add(mainDesigner);
                }
            }

            if (workRoomDetailsBeen != null) {

                upDataForView(workRoomDetailsBeen);
            }

        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.work_room_back:

                finish();
                break;
            case R.id.now_order_details:

                //工作室用户信息弹框
                OrderDialog orderDialog = new OrderDialog(WorkRoomDetailActivity.this, R.style.add_dialog);
                //工作室用户信息弹框
                orderDialog.setListenser(new OrderDialog.CommitListenser() {
                    @Override
                    public void commitListener(String name, String phoneNumber) {

                        JSONObject jsonObject = new JSONObject();
                        if (isLoginUserJust) {
                            String member_id = AdskApplication.getInstance().getMemberEntity().getAcs_member_id();
                            String hs_uid = AdskApplication.getInstance().getMemberEntity().getHs_uid();
                            try {
                                jsonObject.put("consumer_name", name);//姓名
                                jsonObject.put("consumer_mobile", phoneNumber);//电话
                                jsonObject.put("type", 2);//工作室类型
                                jsonObject.put("customer_id", member_id);//消费者ID
                                jsonObject.put("consumer_uid", hs_uid);
                                jsonObject.put("name", workRoomDetailsBeen.getNick_name());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {

                            try {
                                jsonObject.put("consumer_name", name);
                                jsonObject.put("name", workRoomDetailsBeen.getNick_name());
                                jsonObject.put("consumer_mobile", phoneNumber);
                                jsonObject.put("type", 2);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        upOrderDataForService(jsonObject);
                    }
                });
                orderDialog.show();
                break;

        }

    }

    /**
     * 获取工作室详情信息
     */

    public void getWorkRoomDetailData(int designer_id, int offset, int limit, final String hs_uid) {

        MPServerHttpManager.getInstance().getWorkRoomOrderData(designer_id, offset, limit, hs_uid, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                MPNetworkUtils.logError(TAG, volleyError);
                new AlertView(UIUtils.getString(R.string.tip), volleyError.toString() /*UIUtils.getString(R.string.network_error)*/, null, new String[]{UIUtils.getString(R.string.sure)}, null, WorkRoomDetailActivity.this,
                        AlertView.Style.Alert, null).show();
            }

            @Override
            public void onResponse(JSONObject jsonObject) {

                String infoTwo;
                infoTwo = GsonUtil.jsonToString(jsonObject);
                workRoomDetailsBeen = GsonUtil.jsonToBean(infoTwo, WorkRoomDetailsBeen.class);
                main_designers = workRoomDetailsBeen.getMain_designers();
                getDesignerDataForView(workRoomDetailsBeen);
            }
        });


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


    /**
     * 上传立即预约信息
     */
    public void upOrderDataForService(JSONObject jsonObject) {

        MPServerHttpManager.getInstance().upWorkRoomOrderData(jsonObject, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Toast.makeText(WorkRoomDetailActivity.this, R.string.work_room_commit_fail, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(JSONObject jsonObject) {

                Toast.makeText(WorkRoomDetailActivity.this, R.string.work_room_commit_successful, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private View workRoomDetailHeader;
    private LinearLayout work_room_detail_content;
    private ImageView back;
    //    private ImageView work_room_design_imageView;
    private ImageView work_room_detail_six_imageView;
    private TextView now_order;
    private int acs_member_id;
    private String hs_uid;
    private TextView header_work_room_name;
    private TextView header_work_room_design_year;
    private TextView work_room_introduce;
    private TextView work_room_name_title;
    private List<WorkRoomDetailsBeen.MainDesignersBean[]> listMain = new ArrayList<WorkRoomDetailsBeen.MainDesignersBean[]>();
    private DesignerRetrieveRsp designerListBean;
    private boolean isLoginUserJust = false;
    private WorkRoomDetailsBeen workRoomDetailsBeen;
    private List<WorkRoomDetailsBeen.MainDesignersBean> main_designers;

    private ListView listView;
    private TextView now_order_details;
    private GridView gridView;
}
