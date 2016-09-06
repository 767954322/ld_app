package com.autodesk.shejijia.consumer.codecorationBase.grandmaster.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.codecorationBase.grandmaster.entity.DatailCase;
import com.autodesk.shejijia.consumer.codecorationBase.grandmaster.entity.MasterDetail;
import com.autodesk.shejijia.consumer.codecorationBase.grandmaster.view.OrderDialogMaster;
import com.autodesk.shejijia.consumer.codecorationBase.packages.view.MyListView;
import com.autodesk.shejijia.consumer.codecorationBase.packages.view.VerticalViewPager;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.utils.ToastUtil;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by allengu on 16-8-23.
 */
public class GrandMasterDetailActivity extends BaseActivity implements View.OnClickListener {


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

        animationIn = AnimationUtils.loadAnimation(this, R.anim.voice_button_in_anim);

    }

    @Override
    protected void initListener() {
        nav_left_imageButton.setOnClickListener(this);
        bt_grand_reservation.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        Intent intent = getIntent();
        hs_uid = intent.getStringExtra("hs_uid");
        isLoginUserJust = isLoginUser();
        getGrandMasterInfo();
        initAnimation();
        ib_grand_detail_ico.startAnimation(animationIn);
    }

    //获取大师详情信息
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

    //初始化详情信息
    private void initDetailData(String detailInfo) {

        masterDetail = GsonUtil.jsonToBean(detailInfo, MasterDetail.class);
        cases_list = masterDetail.getCases_list();

        initPageData();

        initListData();

    }

    //初始化pager页
    private void initPageData() {

        LayoutInflater lf = LayoutInflater.from(this);
        view1 = lf.inflate(R.layout.viewpager_grandmaster_one, null);
        view2 = lf.inflate(R.layout.viewpager_grandmaster_two, null);
        viewList = new ArrayList<View>();
        viewList.add(view1);
        viewList.add(view2);

        vvp_viewpager.setAdapter(pagerAdapter);

    }

    //为pager页添加数据（目前案例列表为假数据）
    private void initListData() {

        nav_title_textView.setText("套餐详情");
        tv_detail_cn_name = (TextView) view1.findViewById(R.id.tv_detail_cn_name);
        tv_detail_en_name = (TextView) view1.findViewById(R.id.tv_detail_en_name);
        tv_detail_content = (TextView) view1.findViewById(R.id.tv_detail_content);
        iv_detail_desiner = (ImageView) view1.findViewById(R.id.iv_detail_desiner);
        tv_detail_cn_position = (TextView) view1.findViewById(R.id.tv_detail_cn_position);
        tv_detail_en_position = (TextView) view1.findViewById(R.id.tv_detail_en_position);
        ll_grand_master_detail = (MyListView) view2.findViewById(R.id.ll_grand_master_detail);
        //添加ＬistＶiew的Ａdapter
        ll_grand_master_detail.setAdapter(new MasterAdapter());
        //设置第一页的数据
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_left_imageButton://返回键

                finish();

                break;
            case R.id.bt_grand_reservation://提交大师预约

                OrderDialogMaster orderDialog = new OrderDialogMaster(GrandMasterDetailActivity.this, R.style.add_dialog, R.drawable.tital_yuyue);
                orderDialog.setListenser(new OrderDialogMaster.CommitListenser() {
                    @Override
                    public void commitListener(String name, String phoneNumber) {

                        JSONObject jsonObject = new JSONObject();
                        String nicke_name = masterDetail.getNick_name();
                        String member_id = masterDetail.getDesigner().getAcs_member_id();
                        String hs_uid = masterDetail.getHs_uid();
                        String login_member_id = "", login_hs_uid = "";

                        if (isLoginUserJust) {
                            login_member_id = AdskApplication.getInstance().getMemberEntity().getAcs_member_id();
                            login_hs_uid = AdskApplication.getInstance().getMemberEntity().getHs_uid();
                        } else {
                            login_member_id = "";
                            login_hs_uid = "";
                        }
                        try {
                            jsonObject.put("consumer_name", name);//姓名
                            jsonObject.put("consumer_mobile", phoneNumber);//电话
                            jsonObject.put("type", 1);//大师类型
                            jsonObject.put("customer_id", login_member_id);//消费者ID
                            jsonObject.put("consumer_uid", login_hs_uid);
                            jsonObject.put("name", nicke_name);
                            jsonObject.put("member_id", member_id);
                            jsonObject.put("hs_uid", hs_uid);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        CustomProgress.show(GrandMasterDetailActivity.this, "提交中...", false, null);
                        upOrderDataForService(jsonObject);
                    }
                });
                orderDialog.show();


                break;
        }
    }

    /**
     * 上传立即预约信息
     */
    public void upOrderDataForService(JSONObject jsonObject) {

        MPServerHttpManager.getInstance().upWorkRoomOrderData(jsonObject, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                CustomProgress.cancelDialog();
                Toast.makeText(GrandMasterDetailActivity.this, R.string.work_room_commit_fail, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(JSONObject jsonObject) {

                CustomProgress.cancelDialog();
                Toast.makeText(GrandMasterDetailActivity.this, R.string.work_room_commit_successful, Toast.LENGTH_SHORT).show();
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

            if (position == 0) {//Ｐager第一页设置
                nav_title_textView.setText("套餐详情");
                vvp_viewpager.setBackground(null);
            } else if (position == 1) {//Ｐager第二页设置
                nav_title_textView.setText("大师详情页");
                vvp_viewpager.setBackgroundResource(R.drawable.master_bgone);
                vvp_viewpager.setBackground(null);
            }
            return viewList.get(position);
        }

    };

    //案例列表Ａdapter
    class MasterAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return cases_list.size();
        }

        @Override
        public Object getItem(int position) {
            return cases_list.get(position);
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

            //设置案例数据（假数据）
//            mhHolder.iv_detail.setImageResource(cases_list);
            if (cases_list.get(position).getImages().size() > 0) {
                ImageLoader.getInstance().displayImage(cases_list.get(position).getImages().get(0).getFile_url() + "HD.png", mhHolder.iv_detail);
            }

            mhHolder.tv_cn_name.setText(cases_list.get(position).getTitle());
            mhHolder.tv_en_name.setText(cases_list.get(position).getCommunity_name());

            return convertView;
        }

        class MyHolder {

            TextView tv_cn_name;
            TextView tv_en_name;
            ImageView iv_detail;

        }
    }


    private void initAnimation() {
        animationVoice = AnimationUtils.loadAnimation(this, R.anim.voice_button_anim);
        animationIn = AnimationUtils.loadAnimation(this, R.anim.voice_button_in_anim);
        //设置动画执行的回调函数
        animationIn.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ib_grand_detail_ico.startAnimation(animationVoice);    //开始呼吸动画
                ib_grand_detail_ico.setVisibility(View.VISIBLE);
            }
        });
    }

    //大师详情下滑案例假数据
    Integer[] list_Detail = {R.drawable.common_case_icon, R.drawable.common_case_icon, R.drawable.common_case_icon,
            R.drawable.common_case_icon, R.drawable.common_case_icon, R.drawable.common_case_icon};
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
    private boolean isLoginUserJust = false;

    private View view1;
    private View view2;
    private String hs_uid;
    private List<DatailCase> cases_list;
    private ArrayList<View> viewList;
    private MasterDetail masterDetail;
    private VerticalViewPager vvp_viewpager;
    private MyListView ll_grand_master_detail;
    private Animation animationIn, animationVoice;


}
