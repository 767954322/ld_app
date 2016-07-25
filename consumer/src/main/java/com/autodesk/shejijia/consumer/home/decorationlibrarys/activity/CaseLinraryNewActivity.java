package com.autodesk.shejijia.consumer.home.decorationlibrarys.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter.CaseLibraryAdapter;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.CaseDetailBean;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.utils.AnimationUtil;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.consumer.utils.ToastUtil;
import com.autodesk.shejijia.consumer.wxapi.ISendWXShared;
import com.autodesk.shejijia.consumer.wxapi.SendWXShared;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.WXSharedPopWin;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.PictureProcessingUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class CaseLinraryNewActivity extends NavigationBarActivity implements AbsListView.OnScrollListener, View.OnTouchListener, View.OnClickListener {
    private ListView caseLibraryNew;
    private LinearLayout llThumbUp;
    private String case_id;
    /// 集合,类.
    private CaseDetailBean caseDetailBean;
    private CaseLibraryAdapter mCaseLibraryAdapter;
    private List<CaseDetailBean.ImagesEntity> images;
    private RelativeLayout rlCaseLibraryHead;
    private View viewHead;
    private RelativeLayout rlCaseLibraryBottom;
    private ImageView mdesignerAvater;
    private TextView mCaseLibraryText;
    private PolygonImageView pivImgCustomerHomeHeader;
    private TextView ivConsumeHomeDesigner;
    private ImageView ivGuanzu;
    private TextView tvCustomerHomeStyle;
    private TextView tvCustomerHomeRoom;
    private TextView tvCustomerHomeArea;
    private Map<String, String> roomHall;
    private Map<String, String> style;
    private LinearLayout ll_fenxiang_up;
    private LinearLayout ll_fenxiang_down;
    private WXSharedPopWin takePhotoPopWin;
    private boolean ifIsSharedToFriends = true;
    private PictureProcessingUtil pictureProcessingUtil;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_case_linrary_new;
    }


    @Override
    protected void initView() {
        super.initView();
        caseLibraryNew = (ListView) findViewById(R.id.case_library_new);
        llThumbUp = (LinearLayout) findViewById(R.id.rl_thumb_up);
        ll_fenxiang_up = (LinearLayout) findViewById(R.id.ll_fenxiang);
        viewHead = findViewById(R.id.case_head);
        rlCaseLibraryBottom = (RelativeLayout) findViewById(R.id.rl_case_library_bottom);


        pivImgCustomerHomeHeader = (PolygonImageView) findViewById(R.id.piv_img_customer_home_header);
        ivConsumeHomeDesigner = (TextView) findViewById(R.id.iv_consume_home_designer);
        ivGuanzu = (ImageView) findViewById(R.id.iv_guanzu);
        tvCustomerHomeStyle = (TextView) findViewById(R.id.tv_customer_home_style);
        tvCustomerHomeRoom = (TextView) findViewById(R.id.tv_customer_home_room);
        tvCustomerHomeArea = (TextView) findViewById(R.id.tv_customer_home_area);

        View view = LayoutInflater.from(this).inflate(R.layout.case_library_new_item, null);
        mdesignerAvater = (ImageView) view.findViewById(R.id.case_library_item_iv);

        View viewHead = LayoutInflater.from(this).inflate(R.layout.caselibrary_head, null);
        ll_fenxiang_down = (LinearLayout) viewHead.findViewById(R.id.ll_fenxiang);
        rlCaseLibraryHead = (RelativeLayout) viewHead.findViewById(R.id.rl_case_library_head);
        rlCaseLibraryHead.setVisibility(View.VISIBLE);
        View viewText = LayoutInflater.from(this).inflate(R.layout.case_library_text, null);
        mCaseLibraryText = (TextView) viewText.findViewById(R.id.case_library_text);
        caseLibraryNew.addHeaderView(view);
        caseLibraryNew.addHeaderView(viewHead);
        caseLibraryNew.addHeaderView(viewText);

    }


    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        case_id = getIntent().getStringExtra(Constant.CaseLibraryDetail.CASE_ID);   /// 获取发过来的ID.

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleForNavbar(UIUtils.getString(R.string.case_library));
        roomHall = AppJsonFileReader.getRoomHall(this);
        style = AppJsonFileReader.getStyle(this);
        getCaseDetailData(case_id);
        pictureProcessingUtil = new PictureProcessingUtil();
    }


    @Override
    protected void initListener() {
        super.initListener();
        caseLibraryNew.setOnScrollListener(this);
        caseLibraryNew.setOnTouchListener(this);
        llThumbUp.setOnClickListener(this);
        ll_fenxiang_down.setOnClickListener(this);
        ll_fenxiang_up.setOnClickListener(this);

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int top2 = rlCaseLibraryHead.getTop();
        if (top2 <= 0 || firstVisibleItem >= 1) {
            viewHead.setVisibility(View.VISIBLE);
        } else {
            viewHead.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_thumb_up:
                MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
                if (mMemberEntity != null) {
                    sendThumbUp(caseDetailBean.getId());
                } else {
                    AdskApplication.getInstance().doLogin(this);

                }

                break;
            case R.id.ll_fenxiang:


                if (takePhotoPopWin == null) {
                    takePhotoPopWin = new WXSharedPopWin(this, onClickListener);
                }
                takePhotoPopWin.showAtLocation(findViewById(R.id.main_library), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);


                break;
            default:
                break;
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.tv_wx_shared_tofriends:
                    String webUrl = "https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=1417751808&token=&lang=zh_CN";
                    String imageUrl = "http://pic4.nipic.com/20091108/2454778_111024006409_2.jpg";
                    ifIsSharedToFriends = true;
                    try {
                        Bitmap bitmap = null;//获取bitmap
                        SendWXShared.sendProjectToWX(CaseLinraryNewActivity.this, webUrl, "分享标题", "分享最新内容", true, bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
                case R.id.tv_wx_shared_tocircleof_friends:
                    ifIsSharedToFriends = false;
                    Toast.makeText(CaseLinraryNewActivity.this, "分享朋友圈", Toast.LENGTH_SHORT).show();

                    break;
            }
            if (takePhotoPopWin != null) {
                takePhotoPopWin.dismiss();
            }
        }
    };


    /**
     * 发送点赞请求
     *
     * @param assetId
     */
    public void sendThumbUp(String assetId) {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("yxw", jsonObject.toString());
                ToastUtil.showCustomToast(CaseLinraryNewActivity.this, "点赞成功");
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, CaseLinraryNewActivity.this,
                        AlertView.Style.Alert, null).show();
            }
        };
        MPServerHttpManager.getInstance().sendThumbUpRequest(assetId, okResponseCallback);
    }

    /**
     * 获得是否已经点赞接口
     *
     * @param assetId
     */
    public void getThumbUp(String assetId) {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("yxw", jsonObject.toString());
                try {
                    boolean is_member_like = jsonObject.getBoolean("is_member_like");
                    int count = jsonObject.getInt("count");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, CaseLinraryNewActivity.this,
                        AlertView.Style.Alert, null).show();
            }
        };
        MPServerHttpManager.getInstance().getThumbUpRequest(assetId, okResponseCallback);
    }

    /**
     * 获取案例的数据
     *
     * @param case_id 该案例的ID
     */
    public void getCaseDetailData(String case_id) {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String info = GsonUtil.jsonToString(jsonObject);
                caseDetailBean = GsonUtil.jsonToBean(info, CaseDetailBean.class);
                getThumbUp(caseDetailBean.getId());
                images = caseDetailBean.getImages();
                //查找是否是封面图片  若是就添加到头部
                for (int i = 0; i < images.size(); i++) {
                    if (images.get(i).isIs_primary() == true) {
                        ImageUtils.displayIconImage(images.get(i).getFile_url() + Constant.CaseLibraryDetail.JPG, mdesignerAvater);
                    }
                    images.remove(i);
                }
                //设置简介
                String introduction = caseDetailBean.getDesigner_info().getDesigner().getIntroduction();
                if (introduction != null) {
                    mCaseLibraryText.setText(caseDetailBean.getDesigner_info().getDesigner().getIntroduction());
                }

                tvCustomerHomeArea.setText(caseDetailBean.getRoom_area() + "m²");
                String room_type = caseDetailBean.getRoom_type();
                if (roomHall.containsKey(room_type)) {
                    tvCustomerHomeRoom.setText(roomHall.get(room_type));
                }

                String project_style = caseDetailBean.getProject_style();
                if (style.containsKey(project_style)) {
                    tvCustomerHomeStyle.setText(style.get(project_style));
                }

                ivConsumeHomeDesigner.setText(caseDetailBean.getDesigner_info().getFirst_name());
                ImageUtils.displayIconImage(caseDetailBean.getDesigner_info().getAvatar(), pivImgCustomerHomeHeader);
                mCaseLibraryAdapter = new CaseLibraryAdapter(CaseLinraryNewActivity.this, images);
                caseLibraryNew.setAdapter(mCaseLibraryAdapter);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, CaseLinraryNewActivity.this,
                        AlertView.Style.Alert, null).show();
            }
        };
        MPServerHttpManager.getInstance().getCaseListDetail(case_id, okResponseCallback);
    }

    private float mPosY = 0;
    private float mCurPosY = 0;

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPosY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mCurPosY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                if (mCurPosY - mPosY > 0 && (Math.abs(mCurPosY - mPosY) > 30)) {
                    rlCaseLibraryBottom.setAnimation(AnimationUtil.moveToViewLocation());
                } else if (mCurPosY - mPosY < 0 && (Math.abs(mCurPosY - mPosY) > 30)) {
                    rlCaseLibraryBottom.setAnimation(AnimationUtil.moveToViewBottom());
                }
                break;
        }
        return false;
    }

}
