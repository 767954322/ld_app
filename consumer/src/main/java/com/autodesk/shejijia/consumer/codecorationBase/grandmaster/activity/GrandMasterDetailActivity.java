package com.autodesk.shejijia.consumer.codecorationBase.grandmaster.activity;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.codecorationBase.grandmaster.entity.DatailCase;
import com.autodesk.shejijia.consumer.codecorationBase.grandmaster.entity.MasterDetail;
import com.autodesk.shejijia.consumer.codecorationBase.grandmaster.view.OrderDialogMaster;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter.BaseCommonRvAdapter;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.manager.constants.JsonConstants;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.consumer.utils.ToastUtil;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by allengu on 16-8-23.
 */
public class GrandMasterDetailActivity extends BaseActivity implements View.OnClickListener, AppBarLayout.OnOffsetChangedListener {




    @Override
    protected int getLayoutResId() {
        return R.layout.activity_grandmaster_detail;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void initView() {

        nav_left_imageButton = (ImageButton) findViewById(R.id.nav_left_imageButton);
        bt_grand_reservation = (ImageButton) findViewById(R.id.bt_grand_reservation);
        ib_grand_detail_ico = (ImageButton) findViewById(R.id.ib_grand_detail_ico);
        rl_navr_header = (RelativeLayout) findViewById(R.id.rl_navr_header);
        nav_title_textView = (TextView) findViewById(R.id.nav_title_textView);
        iv_detail_desiner = (ImageView) findViewById(R.id.iv_detail_desiner);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        coordinator_layout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        ll_scrolling_view_behavior = (LinearLayout) findViewById(R.id.ll_scrolling_view_behavior);
        app_bar_layout = (AppBarLayout) findViewById(R.id.app_bar_layout);

    }


    @Override
    protected void initListener() {
        super.initListener();

        nav_left_imageButton.setOnClickListener(this);
        bt_grand_reservation.setOnClickListener(this);
        app_bar_layout.addOnOffsetChangedListener(this);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {


        hs_uid = getIntent().getStringExtra("hs_uid");
        isLoginUserJust = isLoginUser();

        getGrandMasterInfo();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_left_imageButton://返回键

                finish();

                break;
            case R.id.bt_grand_reservation://提交大师预约

                onClickFromReservation();

                break;
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

        if (verticalOffset >= 0) {
            rl_navr_header.setVisibility(View.VISIBLE);
        } else {
            rl_navr_header.setVisibility(View.GONE);
        }

    }

    //获取大师详情信息
    private void getGrandMasterInfo() {

        MPServerHttpManager.getInstance().getGrandMasterDetailInfo(0, 10, hs_uid, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ApiStatusUtil.getInstance().apiStatuError(volleyError, GrandMasterDetailActivity.this);

            }

            @Override
            public void onResponse(JSONObject jsonObject) {

                masterDetail = GsonUtil.jsonToBean(GsonUtil.jsonToString(jsonObject), MasterDetail.class);

                cases_list = masterDetail.getCases_list();

                initDetailData();

            }
        });

    }

    //初始化详情信息
    private void initDetailData() {

        //初始化大师详情
        initPageTopInfo();

        //初始化大师案例展示
        initPageBottomInfo();

        //添加图标动画
        initAnimation();

    }

    //初始化第一页内容
    private void initPageTopInfo() {

        if (null != masterDetail.getDesigner() && null != masterDetail.getDesigner().getDesigner_detail_cover_app() && null != masterDetail.getDesigner().getDesigner_detail_cover_app().getPublic_url()) {
            String img_url = masterDetail.getDesigner().getDesigner_detail_cover_app().getPublic_url();
            ImageUtils.loadFileImageListenr(img_url, iv_detail_desiner, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    Bitmap bitmap = loadedImage;
                    Log.d("willson", bitmap + "");

                    int color = bitmap.getPixel(2, 8);
                    // 如果你想做的更细致的话 可以把颜色值的R G B 拿到做响应的处理 笔者在这里就不做更多解释
                    int r = Color.red(color);
                    int g = Color.green(color);
                    int b = Color.blue(color);
                    int a = Color.alpha(color);
                    Log.d("willson", "    " + r + "  " + "  " + g + "  " + b);
                    if (r > 156) {//白色设置灰色
                        nav_left_imageButton.setImageResource(R.drawable.arrow_g);
                        nav_title_textView.setTextColor(Color.GRAY);
                    } else {// 灰色 设置白色
                        nav_left_imageButton.setImageResource(R.drawable.arrow);
                        nav_title_textView.setTextColor(Color.WHITE);
                    }
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            }, new ImageLoadingProgressListener() {
                @Override
                public void onProgressUpdate(String imageUri, View view, int current, int total) {

                }
            });
        }


    }

    //初始化大师案例展示
    private void initPageBottomInfo() {

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

    //添加图标动画
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
                //开始呼吸动画
                ib_grand_detail_ico.startAnimation(animationVoice);
                ib_grand_detail_ico.setVisibility(View.VISIBLE);
            }
        });

        ib_grand_detail_ico.startAnimation(animationIn);
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


    //上传立即预约信息
    private void onClickFromReservation() {

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
                    jsonObject.put(JsonConstants.JSON_MASTER_CONSUMER_NAME, name);//姓名
                    jsonObject.put(JsonConstants.JSON_MASTER_CONSUMER_MOBILE, phoneNumber);//电话
                    jsonObject.put(JsonConstants.JSON_MASTER_TYPE, 1);//大师类型
                    jsonObject.put(JsonConstants.JSON_MASTER_CUSTOMER_ID, login_member_id);//消费者ID
                    jsonObject.put(JsonConstants.JSON_MASTER_CONSUMER_UID, login_hs_uid);
                    jsonObject.put(JsonConstants.JSON_MASTER_NAME, nicke_name);
                    jsonObject.put(JsonConstants.JSON_MASTER_MEMBER_ID, member_id);
                    jsonObject.put(JsonConstants.JSON_MASTER_HS_UID, hs_uid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                CustomProgress.show(GrandMasterDetailActivity.this, "提交中...", false, null);
                upOrderDataForService(jsonObject);
            }
        });
        orderDialog.show();

    }

    public void upOrderDataForService(JSONObject jsonObject) {

        MPServerHttpManager.getInstance().upWorkRoomOrderData(jsonObject, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ApiStatusUtil.getInstance().apiStatuError(volleyError, GrandMasterDetailActivity.this);
                CustomProgress.cancelDialog();
            }

            @Override
            public void onResponse(JSONObject jsonObject) {

                CustomProgress.cancelDialog();
                ToastUtil.showCustomToast(GrandMasterDetailActivity.this, UIUtils.getString(R.string.work_room_commit_successful));
            }
        });

    }

    private ImageButton nav_left_imageButton;
    private ImageButton bt_grand_reservation;
    private ImageButton ib_grand_detail_ico;
    private ImageView iv_detail_desiner;
    private AppBarLayout app_bar_layout;
    private RelativeLayout rl_navr_header;
    private LinearLayout ll_scrolling_view_behavior;
    private TextView nav_title_textView;
    private String hs_uid;
    private boolean isLoginUserJust = false;
    private List<DatailCase> cases_list;
    private MasterDetail masterDetail;
    private RecyclerView mRecyclerView;
    private CoordinatorLayout coordinator_layout;
    private Animation animationIn, animationVoice;


}
