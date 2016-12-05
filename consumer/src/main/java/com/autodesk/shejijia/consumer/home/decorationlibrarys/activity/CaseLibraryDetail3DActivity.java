package com.autodesk.shejijia.consumer.home.decorationlibrarys.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.activity.SeekDesignerDetailActivity;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.FollowingDesignerBean;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter.List3DLibraryAdapter;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.Case3DDetailBean;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.Case3DDetailImageListBean;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.manager.WkTemplateConstants;
import com.autodesk.shejijia.consumer.uielements.MyToast;
import com.autodesk.shejijia.consumer.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.consumer.utils.AnimationUtil;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.consumer.utils.ToastUtil;
import com.autodesk.shejijia.consumer.wxapi.SendWXShared;
import com.autodesk.shejijia.shared.components.common.appglobal.ApiManager;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.tools.chatroom.JumpBean;
import com.autodesk.shejijia.shared.components.common.tools.chatroom.JumpToChatRoom;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.WXSharedPopWin;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.LoginUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.PictureProcessingUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CaseLibraryDetail3DActivity extends NavigationBarActivity implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener, View.OnTouchListener, View.OnClickListener {

    private ListView caseLibraryNew;
    private LinearLayout llThumbUp;
    private LinearLayout guide_dots;
    private String case_id;
    private Case3DDetailBean case3DDetailBean;
    private ArrayList<Case3DDetailBean> case3DDetailList;
    private List3DLibraryAdapter mCase3DLibraryAdapter;
    private RelativeLayout rlCaseLibraryHead;
    private View viewHead;
    private RelativeLayout rlCaseLibraryBottom;
    private ImageView mdesignerAvater;
    private TextView mCaseLibraryText;
    private PolygonImageView pivImgCustomerHomeHeader;
    private ImageView ivCustomerIm;
    private TextView ivConsumeHomeDesigner;
    private ImageView mIvFollowedDesigner;
    private TextView tvCustomerHomeStyle;
    private TextView tvCustomerHomeRoom;
    private TextView tvCustomerHomeArea;
    private TextView tvThumbUp;
    private Map<String, String> roomHall;
    private Map<String, String> style;
    private LinearLayout ll_fenxiang_up;

    private List<View> viewList;
    private LinearLayout rlThumbUp;
    private TextView tvheadThumbUp;
    private LinearLayout ll_fenxiang_down;
    private WXSharedPopWin takePhotoPopWin;
    private boolean ifIsSharedToFriends = true;
    private PictureProcessingUtil pictureProcessingUtil;
    private String designer_id;
    private String hs_uid;
    private MemberEntity memberEntity;
    private String member_type;
    private String member_id;
    private String mMemberType;
    private boolean isMemberLike;
    private ImageView ivThumbUp;
    private ViewPager viewPage;
    private ImageView ivHeadThumbUp;
    private String firstCaseLibraryImageUrl;

    private float mPosY = 0;
    private float mCurPosY = 0;

    private AlertView unFollowedAlertView;
    private Case3DDetailBean.DesignerInfoBean mDesignerInfo;
    private String mHs_uid;
    private String mNickName;
    private ImageView mIvCertification;
    private boolean isfromGuanZhu = false;
    private ArrayList<String> mImageListsManYou;
    private Map<String, String> livingRoom;
    private Map<String, String> toilet;
    private TextView tvCustomerHomeLivingRoom;
    private TextView tvCustomerHomeToilet;

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

        ivThumbUp = (ImageView) findViewById(R.id.iv_thumb_up);


        pivImgCustomerHomeHeader = (PolygonImageView) findViewById(R.id.piv_img_customer_home_header);
        ivCustomerIm = (ImageView) findViewById(R.id.img_look_more_detail_chat);
        ivConsumeHomeDesigner = (TextView) findViewById(R.id.iv_consume_home_designer);
        mIvFollowedDesigner = (ImageView) findViewById(R.id.iv_follow_designer);
        tvCustomerHomeStyle = (TextView) findViewById(R.id.tv_customer_home_style);
        tvCustomerHomeRoom = (TextView) findViewById(R.id.tv_customer_home_room);
        tvCustomerHomeArea = (TextView) findViewById(R.id.tv_customer_home_area);
        tvCustomerHomeLivingRoom = (TextView) findViewById(R.id.tv_customer_home_living_room);
        tvCustomerHomeToilet = (TextView) findViewById(R.id.tv_customer_home_toilet);
        tvThumbUp = (TextView) findViewById(R.id.tv_thumb_up);
        mIvCertification = (ImageView) findViewById(R.id.iv_designer_certification);


        View view = LayoutInflater.from(this).inflate(R.layout.case_library_new_item, null);
        mdesignerAvater = (ImageView) view.findViewById(R.id.case_library_item_iv);

        View viewHead = LayoutInflater.from(this).inflate(R.layout.caselibrary_head, null);
        ll_fenxiang_down = (LinearLayout) viewHead.findViewById(R.id.ll_fenxiang);
        rlThumbUp = (LinearLayout) viewHead.findViewById(R.id.rl_thumb_up);
        tvheadThumbUp = (TextView) viewHead.findViewById(R.id.tv_thumb_up);
        ivHeadThumbUp = (ImageView) viewHead.findViewById(R.id.iv_thumb_up);

        rlCaseLibraryHead = (RelativeLayout) viewHead.findViewById(R.id.rl_case_library_head);
        rlCaseLibraryHead.setVisibility(View.VISIBLE);
        View viewText = LayoutInflater.from(this).inflate(R.layout.case_library_text_3d, null);
        mCaseLibraryText = (TextView) viewText.findViewById(R.id.case_library_text);

        View viewpage = LayoutInflater.from(this).inflate(R.layout.viewpager_item, null);
        viewPage = (ViewPager) viewpage.findViewById(R.id.viewPage);
        guide_dots = (LinearLayout) viewpage.findViewById(R.id.guide_dots);

        caseLibraryNew.addHeaderView(view);
        caseLibraryNew.addHeaderView(viewHead, null, false);
        caseLibraryNew.addHeaderView(viewText, null, false);
        caseLibraryNew.addFooterView(viewpage, null, false);

        case3DDetailList = new ArrayList<>();
        showOrHideChatBtn();
    }


    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        case_id = getIntent().getStringExtra(Constant.CaseLibraryDetail.CASE_ID);   /// 获取发过来的ID.
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        tvCustomerHomeLivingRoom.setVisibility(View.VISIBLE);
        tvCustomerHomeToilet.setVisibility(View.VISIBLE);
        //tvCustomerHomePrice.setVisibility(View.VISIBLE);

        roomHall = AppJsonFileReader.getRoomHall(this);
        livingRoom = AppJsonFileReader.getLivingRoom(this);
        toilet = AppJsonFileReader.getToilet(this);
        style = AppJsonFileReader.getStyle(this);

        CustomProgress.show(this, "", false, null);
        getCase3DDetailData(case_id);
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
        pivImgCustomerHomeHeader.setOnClickListener(this);
        ivCustomerIm.setOnClickListener(this);
        rlThumbUp.setOnClickListener(this);
        llThumbUp.setOnClickListener(this);
        mIvFollowedDesigner.setOnClickListener(this);
        caseLibraryNew.setOnItemClickListener(this);

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case SCROLL_STATE_IDLE:
                TranslateAnimation moveToViewLocationAnimation = AnimationUtil.moveToViewLocation();
                rlCaseLibraryBottom.startAnimation(moveToViewLocationAnimation);
                moveToViewLocationAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        rlCaseLibraryBottom.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        rlCaseLibraryBottom.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });


                break;
            case SCROLL_STATE_TOUCH_SCROLL:
                TranslateAnimation moveToViewBottomAnimation = AnimationUtil.moveToViewBottom();
                rlCaseLibraryBottom.startAnimation(moveToViewBottomAnimation);
                moveToViewBottomAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        rlCaseLibraryBottom.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                //正在滚动时调用
                break;
            case SCROLL_STATE_FLING:
                //手指快速滑动时,在离开ListView由于惯性滑动
                break;
        }
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
            case R.id.rl_thumb_up://点赞
                if (null != memberEntity) {
                    if (!isMemberLike) {
                        String design_asset_id = case3DDetailBean.getDesign_asset_id();
                        if (design_asset_id != null) {
                            llThumbUp.setOnClickListener(null);
                            rlThumbUp.setOnClickListener(null);
                            sendThumbUp(design_asset_id);
                        }
                    } else {
                        //已经点过赞
                    }

                } else {
                    LoginUtils.doLogin(this);
                }
                break;

            case R.id.iv_follow_designer://关注

                if (null != memberEntity) {
                    member_id = memberEntity.getAcs_member_id();
                    if (null != case3DDetailBean && null != case3DDetailBean.getDesigner_info()) {
                        Case3DDetailBean.DesignerInfoBean designer_info = case3DDetailBean.getDesigner_info();
                        boolean is_following = designer_info.is_following;
                        if (TextUtils.isEmpty(member_id)) {
                            LoginUtils.doLogin(this);
                        } else {
                            if (is_following) {
                                unFollowedAlertView.show();
                            } else {
                                followingOrUnFollowedDesigner(true);
                            }
                        }
                    }
                } else {
                    isfromGuanZhu = true;
                    LoginUtils.doLogin(this);
                }
                break;

            case R.id.ll_fenxiang://分享
                if (null != memberEntity) {

                    if (isWeixinAvilible(CaseLibraryDetail3DActivity.this)) {
                        if (takePhotoPopWin == null) {
                            takePhotoPopWin = new WXSharedPopWin(this, onClickListener);
                        }
                        takePhotoPopWin.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                        takePhotoPopWin.showAtLocation(findViewById(R.id.main_library), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    } else {
                        ToastUtil.showCustomToast(CaseLibraryDetail3DActivity.this, getString(R.string.anzhuangweixin));
                    }

                } else {
                    LoginUtils.doLogin(this);
                }
                break;
            case R.id.piv_img_customer_home_header:    /// 进入设计师详情页面.
                Intent intent = new Intent(CaseLibraryDetail3DActivity.this, SeekDesignerDetailActivity.class);
                intent.putExtra(Constant.ConsumerDecorationFragment.designer_id, designer_id);
                intent.putExtra(Constant.ConsumerDecorationFragment.hs_uid, hs_uid);
                startActivity(intent);
                break;

            case R.id.img_look_more_detail_chat:    /// 进入聊天页面,如果没有登陆则进入登陆注册页面.
                if (memberEntity != null) {
                    member_id = memberEntity.getAcs_member_id();
                    mMemberType = memberEntity.getMember_type();
                    String designer_id = case3DDetailBean.getDesigner_info().getDesigner().getAcs_member_id();
                    String hs_uid = case3DDetailBean.getHs_designer_uid();
                    String receiver_name = case3DDetailBean.getDesigner_info().getNick_name();
                    JumpBean jumpBean = new JumpBean();
                    jumpBean.setAcs_member_id(member_id);
                    jumpBean.setMember_type(mMemberType);
                    jumpBean.setReciever_user_name(receiver_name);
                    jumpBean.setReciever_user_id(designer_id);
                    jumpBean.setReciever_hs_uid(hs_uid);
                    JumpToChatRoom.getChatRoom(CaseLibraryDetail3DActivity.this, jumpBean);
                } else {
                    LoginUtils.doLogin(this);
                }

            default:
                break;
        }
    }

    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }

        return false;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO 后台现在只有这个环境的分享链接 别的环境还没有  暂时写死    by willson
//            String webUrl ="http://alpha-www.gdfcx.net/share/3dcase.html?caseid="+case_id;
            String webUrl = ApiManager.getHtml53Url(case_id);
            switch (v.getId()) {

                case R.id.tv_wx_shared_tofriends:

                    ifIsSharedToFriends = true;
                    try {
                        if (null == case3DDetailBean) {
                            Toast.makeText(CaseLibraryDetail3DActivity.this, "正在获取数据，请稍后分享", Toast.LENGTH_LONG).show();
                        } else {
                            SendWXShared.sendProjectToWX(CaseLibraryDetail3DActivity.this, webUrl, case3DDetailBean.getDesign_name(), case3DDetailBean.getConception() + " ", ifIsSharedToFriends, firstCaseLibraryImageUrl);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.tv_wx_shared_tocircleof_friends:
                    ifIsSharedToFriends = false;
                    try {
                        if (null == case3DDetailBean) {
                            Toast.makeText(CaseLibraryDetail3DActivity.this, "正在获取数据，请稍后分享", Toast.LENGTH_LONG).show();
                        } else {
                            SendWXShared.sendProjectToWX(CaseLibraryDetail3DActivity.this, webUrl, case3DDetailBean.getDesign_name(), case3DDetailBean.getConception() + " ", ifIsSharedToFriends, firstCaseLibraryImageUrl);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

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
                rlThumbUp.setOnClickListener(null);
                llThumbUp.setOnClickListener(null);

                ToastUtil.showCustomToast(CaseLibraryDetail3DActivity.this, getString(R.string.dianzhangchenggong));
                ivThumbUp.setBackgroundResource(R.mipmap.yidianzan_ico);
                ivHeadThumbUp.setBackgroundResource(R.mipmap.yidianzan_ico);
                tvThumbUp.setText(getString(R.string.thumbUp) + (case3DDetailBean.getFavorite_count() + 1) + "");
                tvheadThumbUp.setText(getString(R.string.thumbUp) + (case3DDetailBean.getFavorite_count() + 1) + "");
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                rlThumbUp.setOnClickListener(CaseLibraryDetail3DActivity.this);
                llThumbUp.setOnClickListener(CaseLibraryDetail3DActivity.this);
                MPNetworkUtils.logError(TAG, volleyError);
                new AlertView(UIUtils.getString(R.string.tip),
                        UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null,
                        CaseLibraryDetail3DActivity.this,
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
                try {
                    isMemberLike = jsonObject.getBoolean("is_member_like");
                    if (isMemberLike) {
                        rlThumbUp.setOnClickListener(null);
                        llThumbUp.setOnClickListener(null);
                        ivThumbUp.setBackgroundResource(R.mipmap.yidianzan_ico);
                        ivHeadThumbUp.setBackgroundResource(R.mipmap.yidianzan_ico);
                    }

                    // int count = jsonObject.getInt("count");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, CaseLibraryDetail3DActivity.this,
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
    public void getCase3DDetailData(final String case_id) {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                String info = GsonUtil.jsonToString(jsonObject);
                case3DDetailBean = GsonUtil.jsonToBean(info, Case3DDetailBean.class);
                case3DDetailList.add(case3DDetailBean);

                //set tital
                String str_tital = case3DDetailBean.getDesign_name();
                boolean isTitalToLong = str_tital.length() > 8;
                str_tital = isTitalToLong ? str_tital.substring(0, 8) + "..." : str_tital;
                setTitleForNavbar(str_tital);

                updateViewFromCaseDetailData();

                CustomProgress.cancelDialog();

            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                CustomProgress.cancelDialog();
                int statusCode = volleyError.networkResponse.statusCode;
                if (statusCode==400){
                    new AlertView(UIUtils.getString(R.string.tip), getString(R.string.case3dnot), null, null, new String[]{UIUtils.getString(R.string.sure)},CaseLibraryDetail3DActivity.this,
                            AlertView.Style.Alert, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            if (position != AlertView.CANCELPOSITION) {
                                finish();
                            }
                        }
                    }).setCancelable(true).show();
                }


                ApiStatusUtil.getInstance().apiStatuError(volleyError, CaseLibraryDetail3DActivity.this);
            }
        };
        MPServerHttpManager.getInstance().getCaseList3DDetail(case_id, okResponseCallback);
    }

    List<Case3DDetailImageListBean> imageListBean;

    /**
     * 获取案例的数据，更新页面
     */
    private void updateViewFromCaseDetailData() {
        if (null == case3DDetailBean) {
            return;
        }
        mDesignerInfo = case3DDetailBean.getDesigner_info();
        hs_uid = case3DDetailBean.getHs_designer_uid();
        if (mDesignerInfo == null) {
            return;
        }
        designer_id = mDesignerInfo.getDesigner().getAcs_member_id();
        mHs_uid = mDesignerInfo.getHs_uid();
        mNickName = mDesignerInfo.getNick_name();

        boolean is_following = mDesignerInfo.getIs_following();
        /**
         * 如果当前没有acs_member_id,就是没有登录，点击跳转到登录页面
         */
        if (null != memberEntity) {
            member_id = memberEntity.getAcs_member_id();
        }
        if (!TextUtils.isEmpty(member_id)) {
            /**
             * 如果是当前设计师，就不显示关注按钮
             */
            if (!member_id.equals(designer_id)) {
                setFollowedTitle(is_following);
            } else {
                mIvFollowedDesigner.setVisibility(View.GONE);
            }
        } else {
            setFollowedTitle(false);
        }
        initUnFollowedAlertView();

        //登录状态判断是否点赞
        if (null != memberEntity) {
            getThumbUp(case3DDetailBean.getDesign_asset_id());
        }

        if (case3DDetailBean.getDesign_file() == null) {
            return;
        }
        List<Case3DDetailBean.DesignFileBean> images = case3DDetailBean.getDesign_file();


        //后台逻辑修改  顶部图片改动
        String thumbnailMainPath = case3DDetailBean.getThumbnailMainPath() + Constant.CaseLibraryDetail.JPG;
        firstCaseLibraryImageUrl = thumbnailMainPath;
        if (thumbnailMainPath != null) {
            ImageUtils.displaySixImage(thumbnailMainPath, mdesignerAvater);
        }

        List<Case3DDetailImageListBean> imageListBeanList = getImageLists(images);
        imageListBean = imageListBeanList;
        //设置 listView 中间部分的图片列表
        mCase3DLibraryAdapter = new List3DLibraryAdapter(CaseLibraryDetail3DActivity.this, imageListBeanList);

        caseLibraryNew.setAdapter(mCase3DLibraryAdapter);
        //设置简介
        String introduction = case3DDetailBean.getConception();
        if (introduction != null) {
            mCaseLibraryText.setText("\u3000\u3000" + introduction);
        } else {
            mCaseLibraryText.setText("\u3000\u3000" + getString(R.string.noinfo));
        }

        tvCustomerHomeArea.setText(case3DDetailBean.getRoom_area() + UIUtils.getString(R.string.m2));
        String room_type = case3DDetailBean.getRoom_type();
        if (roomHall.containsKey(room_type)) {
            tvCustomerHomeRoom.setText(roomHall.get(room_type));
        } else {
            tvCustomerHomeRoom.setText(R.string.other_qita);
        }

        //厅
        String bedroom = case3DDetailBean.getBedroom();
        if (livingRoom.containsKey(bedroom)) {
            tvCustomerHomeLivingRoom.setText(livingRoom.get(bedroom));
        } else {
            tvCustomerHomeLivingRoom.setText(bedroom);
        }

        //卫
        String restroom = case3DDetailBean.getRestroom();
        if (toilet.containsKey(restroom)) {
            tvCustomerHomeToilet.setText(toilet.get(restroom));
        } else {
            tvCustomerHomeToilet.setText(restroom);
        }

        String project_style = case3DDetailBean.getProject_style();
        if (style.containsKey(project_style)) {
            tvCustomerHomeStyle.setText(style.get(project_style));
        } else {
            tvCustomerHomeStyle.setText(R.string.other_qita);
        }

        tvThumbUp.setText(getString(R.string.thumbUp) + case3DDetailBean.getFavorite_count() + "");
        tvheadThumbUp.setText(getString(R.string.thumbUp) + case3DDetailBean.getFavorite_count() + "");

        Case3DDetailBean.DesignerInfoBean designer_info = case3DDetailBean.getDesigner_info();
        if (designer_info.getNick_name() != null) {
            ivConsumeHomeDesigner.setText(designer_info.getNick_name());
        } else {
            ivConsumeHomeDesigner.setText(designer_info.getFirst_name());
        }

        ImageUtils.displayAvatarImage(case3DDetailBean.getDesigner_info().getAvatar(), pivImgCustomerHomeHeader);

        Case3DDetailBean.DesignerInfoBean.DesignerBean designer = mDesignerInfo.getDesigner();
        if (null != designer) {
            int is_real_name = designer.getIs_real_name();
            if (WkTemplateConstants.CERHIGH_TYPE_AUTH_PASSED.equalsIgnoreCase(String.valueOf(is_real_name))) {
                mIvCertification.setVisibility(View.VISIBLE);
            }
        } else {
            mIvCertification.setVisibility(View.GONE);
        }
    }


    /*
    * 通过design_file"对应的列表重新组装listView中的数据源
    * return 返回含有类型及图片列表的集合
    * */
    private List<Case3DDetailImageListBean> getImageLists(List<Case3DDetailBean.DesignFileBean> imageBeanLists) {
        List<Case3DDetailImageListBean> detailImageListBeanList = new ArrayList<>();
        Case3DDetailImageListBean case3DDetailImageListBeanXuanRan = new Case3DDetailImageListBean();
        Case3DDetailImageListBean case3DDetailImageListBeanManYou = new Case3DDetailImageListBean();
        Case3DDetailImageListBean case3DDetailImageListBeanHuXing = new Case3DDetailImageListBean();
        ArrayList<String> imageListsXuanRan = new ArrayList<>();
        ArrayList<String> imageListsManYou = new ArrayList<>();
        ArrayList<String> imageListsHuXing = new ArrayList<>();

        //遍历服务器接口返回的DesignFile列表,重新组装不同类型对应的图片列表(目前支持,"0"/"4"/"10"类型对应的图片列表)
        for (int i = 0; i < imageBeanLists.size(); i++) {
            String type = imageBeanLists.get(i).getType();
            if (type.equalsIgnoreCase("0")) {
                imageListsXuanRan.add(imageBeanLists.get(i).getLink() + "HD.jpg");
            } else if (type.equalsIgnoreCase("4")) {
                String cover = imageBeanLists.get(i).getCover();
                String coverAndLink;
                if (cover != null) {
                    coverAndLink = cover + "HD.jpg" + "COVERANDLINK" + imageBeanLists.get(i).getLink();

                } else {
                    coverAndLink = "HD.jpg" + "COVERANDLINK" + imageBeanLists.get(i).getLink();
                }
                imageListsManYou.add(coverAndLink);

            } else if (type.equalsIgnoreCase("9")) {
                imageListsHuXing.add(imageBeanLists.get(i).getLink() + "HD.jpg");
            }
        }
        //设置重新组装的bean内的类型
        case3DDetailImageListBeanXuanRan.setType("0");
        case3DDetailImageListBeanManYou.setType("4");
        case3DDetailImageListBeanHuXing.setType("10");
        if (imageListsXuanRan.size() > 0) {
            case3DDetailImageListBeanXuanRan.setImageList(imageListsXuanRan);
            case3DDetailImageListBeanXuanRan.setLocal(false);
        } else { //给定默认图片路径,ImageLoader加载本地图片的路径规则
            imageListsXuanRan.add("drawable://" + R.drawable.default_3d_details);
            case3DDetailImageListBeanXuanRan.setImageList(imageListsXuanRan);
            case3DDetailImageListBeanXuanRan.setLocal(true);
        }
        detailImageListBeanList.add(case3DDetailImageListBeanXuanRan);

        if (imageListsHuXing.size() > 0) {
            case3DDetailImageListBeanHuXing.setImageList(imageListsHuXing);
            case3DDetailImageListBeanHuXing.setLocal(false);
        } else { //给定默认图片路径,ImageLoader加载本地图片的路径规则
            imageListsHuXing.add("drawable://" + R.drawable.default_3d_details);
            case3DDetailImageListBeanHuXing.setImageList(imageListsHuXing);
            case3DDetailImageListBeanHuXing.setLocal(true);
        }
        detailImageListBeanList.add(case3DDetailImageListBeanHuXing);

        if (imageListsManYou.size() > 0) {
            case3DDetailImageListBeanManYou.setImageList(imageListsManYou);
            case3DDetailImageListBeanManYou.setLocal(false);
        } else { //给定默认图片路径,ImageLoader加载本地图片的路径规则
            imageListsManYou.add("drawable");
            case3DDetailImageListBeanManYou.setImageList(imageListsManYou);
            case3DDetailImageListBeanManYou.setLocal(true);
        }
        detailImageListBeanList.add(case3DDetailImageListBeanManYou);
        mImageListsManYou = imageListsManYou;
        initPager();
        return detailImageListBeanList;
    }


    /**
     * 关注或者取消关注设计师
     *
     * @param followsType true 为关注，false 取消关注
     */
    private void followingOrUnFollowedDesigner(final boolean followsType) {
        CustomProgress.show(this, "", false, null);
        String followed_member_id = designer_id;
        String followed_member_uid = mHs_uid;
        MPServerHttpManager.getInstance().followingOrUnFollowedDesigner(member_id, followed_member_id, followed_member_uid, followsType, new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                String followingOrUnFollowedDesignerString = GsonUtil.jsonToString(jsonObject);
                FollowingDesignerBean followingDesignerBean = GsonUtil.jsonToBean(followingOrUnFollowedDesignerString, FollowingDesignerBean.class);

                setFollowedTitle(followsType);
                if (followsType) {
                    MyToast.show(CaseLibraryDetail3DActivity.this, UIUtils.getString(R.string.attention_success));
                    mDesignerInfo.is_following = true;
                } else {
                    mDesignerInfo.is_following = false;
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CustomProgress.cancelDialog();
                setFollowedTitle(true);
                MPNetworkUtils.logError(TAG, volleyError);
            }
        });
    }

    /**
     * 取消关注提示框
     */
    private void initUnFollowedAlertView() {
        unFollowedAlertView = new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.attention_tip_message_first) + mNickName + UIUtils.getString(R.string.attention_tip_message_last),
                UIUtils.getString(R.string.following_cancel), null,
                new String[]{UIUtils.getString(R.string.following_sure)},
                CaseLibraryDetail3DActivity.this,
                AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object object, int position) {
                if (position != AlertView.CANCELPOSITION) {
                    followingOrUnFollowedDesigner(false);
                }
            }
        }).setCancelable(true);
    }

    /**
     * 设置title栏，关注的显示问题
     * true :关注状态-->对应取消关注
     * false : 取消关注-->对应关注
     */
    private void setFollowedTitle(boolean is_following) {

        if (is_following) {
            mIvFollowedDesigner.setBackground(UIUtils.getDrawable(R.drawable.ic_followed_cancel));

        } else {
            mIvFollowedDesigner.setBackground(UIUtils.getDrawable(R.drawable.ic_followed_sure));
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        memberEntity = AdskApplication.getInstance().getMemberEntity();
        showOrHideChatBtn();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (isfromGuanZhu && null != memberEntity) {
            getCase3DDetailData(case_id);
        } else {
            isfromGuanZhu = false;
        }


        if (null != memberEntity) {
            getThumbUp(case3DDetailBean.getDesign_asset_id());
        }
        showOrHideChatBtn();
    }

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
                break;
        }
        return false;
    }


    /**
     * 根据登录用户，显示还是隐藏聊天按钮
     */
    private void showOrHideChatBtn() {

        if (null != memberEntity) {
            member_type = memberEntity.getMember_type();
            if (member_type.equals(Constant.UerInfoKey.CONSUMER_TYPE)) {
                ivCustomerIm.setVisibility(View.VISIBLE);
            } else {
                ivCustomerIm.setVisibility(View.GONE);
            }
        } else {
            ivCustomerIm.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            List<String> imageList = new ArrayList<>();
            imageList.add(firstCaseLibraryImageUrl);
            Intent intent = new Intent(this, CaseLibraryDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constant.CaseLibraryDetail.CASE_DETAIL_BEAN, (Serializable) imageList);
            bundle.putInt(Constant.CaseLibraryDetail.CASE_DETAIL_POSTION, position);
            bundle.putInt("moveState", 1); // 代表从3D传过去的
            intent.putExtras(bundle);
            this.startActivity(intent);
        }
    }


    /**
     * 初始化点
     *
     * @return
     */
    private View initDot() {
        return LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_dot, null);
    }


    /**
     * 初始化所有的点
     *
     * @param count
     */
    private void initDots(int count) {
        if (count>0){
            for (int j = 0; j < count; j++) {
                guide_dots.addView(initDot());
            }
            guide_dots.getChildAt(0).setSelected(true);
        }


    }

    /**
     * 初始化单个图片的
     *
     * @param url
     * @return
     */
    private View initView(String url) {
        String drawabel = "drawable://" + R.drawable.default_3d_details;
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_guide, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iguide_img);
        TextView textView = (TextView) view.findViewById(R.id.tvzanwutupian);
        ImageView image_quanjing = (ImageView) view.findViewById(R.id.image_quanjing);
        ImageView image_max = (ImageView) view.findViewById(R.id.image_max);
        if (url.equals(drawabel)) {
            image_quanjing.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
            image_max.setVisibility(View.VISIBLE);
        }

        ImageUtils.loadFileImage(imageView, url);
        return view;
    }


    public String initImage(String url) {
        if (url.equals("drawable")) {
            return "drawable://" + R.drawable.default_3d_details;
        } else if (url.contains("COVERANDLINK")) {
            String cover = url.substring(0, url.indexOf("COVERANDLINK"));
            if (!cover.equals("HD.jpg")) {
                return cover;
            } else {
                return "drawable://" + R.drawable.images_3d;
            }
        } else {
            return "drawable://" + R.drawable.images_3d;
        }
    }


    private void initPager() {
        viewList = new ArrayList<View>();
        for (int i = 0; i < mImageListsManYou.size(); i++) {
            viewList.add(initView(initImage(mImageListsManYou.get(i))));
        }
        if (mImageListsManYou.size()!=1){
            initDots(mImageListsManYou.size());
        }

        viewPage.setAdapter(new ViewPagerAdapter(viewList));

        viewPage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method
                for (int i = 0; i < guide_dots.getChildCount(); i++) {
                    if (i == arg0) {
                        guide_dots.getChildAt(i).setSelected(true);
                    } else {
                        guide_dots.getChildAt(i).setSelected(false);
                    }
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }


    class ViewPagerAdapter extends PagerAdapter {
        private List<View> data;


        public ViewPagerAdapter(List<View> data) {
            super();
            this.data = data;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return data.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            // TODO Auto-generated method stub
            View view = data.get(position);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String newUrl = mImageListsManYou.get(position);
                    if (!newUrl.equals("drawable")) {
                        String link = newUrl.substring(newUrl.indexOf("COVERANDLINK") + 12);
                        Intent intent = new Intent(CaseLibraryDetail3DActivity.this, CaseLibraryRoamingWebView.class);
                        intent.putExtra("roaming", link);
                        CaseLibraryDetail3DActivity.this.startActivity(intent);
                    }
                }
            });
            container.addView(data.get(position));
            return data.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(data.get(position));
        }

    }


}
