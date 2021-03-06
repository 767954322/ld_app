package com.autodesk.shejijia.consumer.home.decorationlibrarys.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
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
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.ImagesBean;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter.CaseLibraryAdapter;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.CaseDetailBean;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.DesignerInfoBean;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.manager.WkTemplateConstants;
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
import com.autodesk.shejijia.consumer.uielements.MyToast;
import com.autodesk.shejijia.shared.components.common.uielements.WXSharedPopWin;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.consumer.uielements.viewgraph.PolygonImageView;
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
import java.util.List;
import java.util.Map;

/**
 * @version : 0.0.0.30;
 * @描述 :  案例库详情界面
 * @Author :willson
 * @创建日期 :2016.8.29
 */
public class CaseLibraryNewActivity extends NavigationBarActivity implements AbsListView.OnItemClickListener, AbsListView.OnScrollListener, View.OnTouchListener, View.OnClickListener {


    private ImageView mIvCertification;
    private TextView mCaseCommunityName;
    private TextView tvCustomerHomePrice;
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
        rlCaseLibraryBottom = (LinearLayout) findViewById(R.id.rl_case_library_bottom);

        ivThumbUp = (ImageView) findViewById(R.id.iv_thumb_up);
        mIvCertification = (ImageView) findViewById(R.id.iv_designer_certification);


        pivImgCustomerHomeHeader = (PolygonImageView) findViewById(R.id.piv_img_customer_home_header);
        ivCustomerIm = (ImageView) findViewById(R.id.img_look_more_detail_chat);
        ivConsumeHomeDesigner = (TextView) findViewById(R.id.iv_consume_home_designer);
        tvCustomerHomeStyle = (TextView) findViewById(R.id.tv_customer_home_style);
        tvCustomerHomeRoom = (TextView) findViewById(R.id.tv_customer_home_room);
        tvCustomerHomeLivingRoom = (TextView) findViewById(R.id.tv_customer_home_living_room);
        tvCustomerHomeToilet = (TextView) findViewById(R.id.tv_customer_home_toilet);
        tvCustomerHomeArea = (TextView) findViewById(R.id.tv_customer_home_area);

        tvCustomerHomePrice = (TextView) findViewById(R.id.tv_customer_home_price);

        tvThumbUp = (TextView) findViewById(R.id.tv_thumb_up);
        mIvFollowedDesigner = (ImageView) findViewById(R.id.iv_follow_designer);

        //顶部图片head
        View view = LayoutInflater.from(this).inflate(R.layout.case_library_new_item, null);
        mdesignerAvater = (ImageView) view.findViewById(R.id.case_library_item_iv);

        //分享 点赞的head
        View viewHead = LayoutInflater.from(this).inflate(R.layout.caselibrary_head, null);
        ll_fenxiang_down = (LinearLayout) viewHead.findViewById(R.id.ll_fenxiang);
        ll_fenxiang_down.setClickable(true);
        rlThumbUp = (LinearLayout) viewHead.findViewById(R.id.rl_thumb_up);
        tvheadThumbUp = (TextView) viewHead.findViewById(R.id.tv_thumb_up);
        ivHeadThumbUp = (ImageView) viewHead.findViewById(R.id.iv_thumb_up);
        //描述文字的head
        View viewText = LayoutInflater.from(this).inflate(R.layout.case_library_text_2d, null);
        rlCaseLibraryHead = (RelativeLayout) viewHead.findViewById(R.id.rl_case_library_head);
        rlCaseLibraryHead.setVisibility(View.VISIBLE);
        mCaseLibraryText = (TextView) viewText.findViewById(R.id.case_library_text);
        mCaseCommunityName = (TextView) viewText.findViewById(R.id.tv_case_community_name);
        caseLibraryNew.addHeaderView(view);
        caseLibraryNew.addHeaderView(viewHead);
        caseLibraryNew.addHeaderView(viewText);

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
        tvCustomerHomePrice.setVisibility(View.VISIBLE);

        roomHall = AppJsonFileReader.getRoomHall(this);
        livingRoom = AppJsonFileReader.getLivingRoom(this);
        toilet = AppJsonFileReader.getToilet(this);
        style = AppJsonFileReader.getStyle(this);
        CustomProgress.show(this, "", false, null);
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
        pivImgCustomerHomeHeader.setOnClickListener(this);
        ivCustomerIm.setOnClickListener(this);
        rlThumbUp.setOnClickListener(this);
        llThumbUp.setOnClickListener(this);
        caseLibraryNew.setOnItemClickListener(this);
        mIvFollowedDesigner.setOnClickListener(this);

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
                    rlThumbUp.setOnClickListener(null);
                    llThumbUp.setOnClickListener(null);
                    CustomProgress.show(this, "", false, null);
                    String id = caseDetailBean.getId();
                    if (id != null) {
                        sendThumbUp(id);
                    }

                } else {
                    LoginUtils.doLogin(this);
                }
                break;

            case R.id.iv_follow_designer://关注
                if (null != memberEntity) {
                    member_id = memberEntity.getAcs_member_id();
                    if (null != caseDetailBean && null != caseDetailBean.getDesigner_info()) {
                        DesignerInfoBean designer_info = caseDetailBean.getDesigner_info();
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

                    if (isWeixinAvilible(CaseLibraryNewActivity.this)) {
                        if (takePhotoPopWin == null) {
                            takePhotoPopWin = new WXSharedPopWin(this, onClickListener);
                        }
                        takePhotoPopWin.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                        takePhotoPopWin.showAtLocation(findViewById(R.id.main_library), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    } else {
                        ToastUtil.showCustomToast(CaseLibraryNewActivity.this, getString(R.string.anzhuangweixin));
                    }

                } else {
                    LoginUtils.doLogin(this);
                }
                break;

            case R.id.piv_img_customer_home_header:    /// 进入设计师详情页面.
                Intent intent = new Intent(CaseLibraryNewActivity.this, SeekDesignerDetailActivity.class);
                intent.putExtra(Constant.ConsumerDecorationFragment.designer_id, designer_id);
                intent.putExtra(Constant.ConsumerDecorationFragment.hs_uid, hs_uid);
                startActivity(intent);
                break;

            case R.id.img_look_more_detail_chat:    /// 进入聊天页面,如果没有登陆则进入登陆注册页面.
                /**
                 * 聊天,如没有登陆 也会跳入注册登陆页面
                 */
                if (memberEntity != null) {
                    mMemberType = memberEntity.getMember_type();
                    member_id = memberEntity.getAcs_member_id();

                    if (null != caseDetailBean) {
                        String designer_id = caseDetailBean.getDesigner_info().getDesigner().getAcs_member_id();
                        String hs_uid = caseDetailBean.getHs_designer_uid();
                        String receiver_name = caseDetailBean.getDesigner_info().getNick_name();

                        JumpBean jumpBean = new JumpBean();
                        jumpBean.setAcs_member_id(member_id);
                        jumpBean.setMember_type(mMemberType);
                        jumpBean.setReciever_user_name(receiver_name);
                        jumpBean.setReciever_user_id(designer_id);
                        jumpBean.setReciever_hs_uid(hs_uid);
                        JumpToChatRoom.getChatRoom(CaseLibraryNewActivity.this, jumpBean);
                    } else {
                        new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, CaseLibraryNewActivity.this,
                                AlertView.Style.Alert, null).show();
                    }
                } else {
                    LoginUtils.doLogin(this);
                }
                break;

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
            String webUrl = ApiManager.getHtml5Url(case_id);
            //TODO 后台现在只有这个环境的分享链接 别的环境还没有  暂时写死    by willson
//            String webUrl ="http://uat-www.gdfcx.net/share/2dcase.html?caseid="+case_id;
            switch (v.getId()) {

                case R.id.tv_wx_shared_tofriends:
                    ifIsSharedToFriends = true;
                    try {
                        if (null == caseDetailBean) {
                            Toast.makeText(CaseLibraryNewActivity.this, "正在获取数据，请稍后分享", Toast.LENGTH_LONG).show();
                        } else {
                            SendWXShared.sendProjectToWX(CaseLibraryNewActivity.this, webUrl, caseDetailBean.getTitle(), caseDetailBean.getDescription() + " ", ifIsSharedToFriends, firstCaseLibraryImageUrl);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.tv_wx_shared_tocircleof_friends:
                    ifIsSharedToFriends = false;
                    try {
                        if (null == caseDetailBean) {
                            Toast.makeText(CaseLibraryNewActivity.this, "正在获取数据，请稍后分享", Toast.LENGTH_LONG).show();
                        } else {
                            SendWXShared.sendProjectToWX(CaseLibraryNewActivity.this, webUrl, caseDetailBean.getTitle(), caseDetailBean.getDescription() + " ", ifIsSharedToFriends, firstCaseLibraryImageUrl);
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
                CustomProgress.cancelDialog();
                rlThumbUp.setOnClickListener(null);
                llThumbUp.setOnClickListener(null);

                ToastUtil.showCustomToast(CaseLibraryNewActivity.this, getString(R.string.dianzhangchenggong));
                ivThumbUp.setBackgroundResource(R.mipmap.yidianzan_ico);
                ivHeadThumbUp.setBackgroundResource(R.mipmap.yidianzan_ico);
                tvThumbUp.setText("点赞" + (caseDetailBean.getFavorite_count() + 1));
                tvheadThumbUp.setText("点赞" + (caseDetailBean.getFavorite_count() + 1));
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CustomProgress.cancelDialog();
                rlThumbUp.setOnClickListener(CaseLibraryNewActivity.this);
                llThumbUp.setOnClickListener(CaseLibraryNewActivity.this);
                MPNetworkUtils.logError(TAG, volleyError);
                ApiStatusUtil.getInstance().apiStatuError(volleyError, CaseLibraryNewActivity.this);
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
                ApiStatusUtil.getInstance().apiStatuError(volleyError, CaseLibraryNewActivity.this);
            }
        };
        MPServerHttpManager.getInstance().getThumbUpRequest(assetId, okResponseCallback);
    }

    /**
     * 获取案例的数据
     *
     * @param case_id 该案例的ID
     */
    public void getCaseDetailData(final String case_id) {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                String info = GsonUtil.jsonToString(jsonObject);
                caseDetailBean = GsonUtil.jsonToBean(info, CaseDetailBean.class);
                //set tital
                String str_tital = caseDetailBean.getTitle();
                boolean isTitalToLong = str_tital.length() > 8;
                str_tital = isTitalToLong ? str_tital.substring(0, 8) + "..." : str_tital;
                setTitleForNavbar(str_tital);

                updateViewFromCaseDetailData();

                CustomProgress.cancelDialog();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                ApiStatusUtil.getInstance().apiStatuError(volleyError, CaseLibraryNewActivity.this);
                CustomProgress.cancelDialog();
            }
        };
        MPServerHttpManager.getInstance().getCaseListDetail(case_id, okResponseCallback);
    }

    /**
     * 获取案例的数据，更新页面
     */
    private void updateViewFromCaseDetailData() {
        if (null == caseDetailBean) {
            return;
        }
        mDesignerInfo = caseDetailBean.getDesigner_info();
        hs_uid = caseDetailBean.getHs_designer_uid();
        if (mDesignerInfo == null) {
            return;
        }
        designer_id = mDesignerInfo.getDesigner().getAcs_member_id();
        mHs_uid = mDesignerInfo.getHs_uid();
        mNickName = mDesignerInfo.getNick_name();
        boolean is_following = mDesignerInfo.is_following;
        /**
         * 如果当前没有acs_member_id,就是没有登录，点击跳转到登录页面
         */
        if (null != memberEntity) {
            member_id = memberEntity.getAcs_member_id();
            getThumbUp(caseDetailBean.getId());

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
        images = caseDetailBean.getImages();
        //查找是否是封面图片  若是就添加到头部
        for (int i = 0; i < images.size(); i++) {
            if (images.get(i).is_primary() == true) {
                topPosition = i;
                firstCaseLibraryImageUrl = images.get(i).getFile_url() + Constant.CaseLibraryDetail.JPG;
//                ImageUtils.displayIconImage(images.get(i).getFile_url() + Constant.CaseLibraryDetail.JPG, mdesignerAvater);
                ImageUtils.loadImageIcon(mdesignerAvater, images.get(i).getFile_url() + Constant.CaseLibraryDetail.JPG);
            }
        }

        mCaseLibraryAdapter = new CaseLibraryAdapter(CaseLibraryNewActivity.this, images);
        //mCaseLibraryAdapter.setShareOrPraiseListener(this);
        caseLibraryNew.setAdapter(mCaseLibraryAdapter);
        //设置简介
        String introduction = caseDetailBean.getDescription();
        if (introduction == null || introduction.equals("")) {
            mCaseLibraryText.setText(getString(R.string.nodescription));
        } else {
            mCaseLibraryText.setText(introduction);
        }
        String community_name = caseDetailBean.getCommunity_name();

        //小区名称
        if (community_name == null || community_name.equals("")) {
            mCaseCommunityName.setText(getString(R.string.nodescription));
        } else {
            mCaseCommunityName.setText(community_name);
        }

        //造价

        String prj_price = caseDetailBean.getPrj_price();

        if (prj_price == null || prj_price.equals("")) {
            tvCustomerHomePrice.setText(getString(R.string.nodescription_price));
        } else {
            tvCustomerHomePrice.setText(prj_price + "万元");
        }

        tvCustomerHomeArea.setText(caseDetailBean.getRoom_area() + UIUtils.getString(R.string.m2));

        //室
        String room_type = caseDetailBean.getRoom_type();
        if (roomHall.containsKey(room_type)) {
            tvCustomerHomeRoom.setText(roomHall.get(room_type));
        }

        //厅
        String bedroom = caseDetailBean.getBedroom();
        if (livingRoom.containsKey(bedroom)) {
            tvCustomerHomeLivingRoom.setText(livingRoom.get(bedroom));
        } else {
            tvCustomerHomeLivingRoom.setText(bedroom);
        }

        //卫
        String restroom = caseDetailBean.getRestroom();
        if (toilet.containsKey(restroom)) {
            tvCustomerHomeToilet.setText(toilet.get(restroom));
        } else {
            tvCustomerHomeToilet.setText(restroom);
        }

        String project_style = caseDetailBean.getProject_style();
        if (style.containsKey(project_style)) {
            tvCustomerHomeStyle.setText(style.get(project_style));
        }

        tvThumbUp.setText("点赞" + caseDetailBean.getFavorite_count() + "");
        tvheadThumbUp.setText("点赞" + caseDetailBean.getFavorite_count() + "");


        DesignerInfoBean designer_info = caseDetailBean.getDesigner_info();
        if (designer_info.getNick_name() != null) {
            ivConsumeHomeDesigner.setText(designer_info.getNick_name());
        } else {
            ivConsumeHomeDesigner.setText(designer_info.getNick_name());
        }

        //ivConsumeHomeDesigner.setText(caseDetailBean.getDesigner_info().getFirst_name());
//        ImageUtils.displayIconImage(caseDetailBean.getDesigner_info().getAvatar(), pivImgCustomerHomeHeader);
        ImageUtils.displayAvatarImage(caseDetailBean.getDesigner_info().getAvatar(), pivImgCustomerHomeHeader);
        com.autodesk.shejijia.consumer.home.decorationdesigners.entity.DesignerInfoBean designer = mDesignerInfo.getDesigner();

        if (null != designer) {
            int is_real_name = designer.getIs_real_name();
            if (WkTemplateConstants.CERHIGH_TYPE_AUTH_PASSED.equalsIgnoreCase(String.valueOf(is_real_name))) {
                mIvCertification.setVisibility(View.VISIBLE);
            }
        } else {
            mIvCertification.setVisibility(View.GONE);
        }
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
                    MyToast.show(CaseLibraryNewActivity.this, UIUtils.getString(R.string.attention_success));
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
                CaseLibraryNewActivity.this,
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
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (isfromGuanZhu && null != memberEntity) {
            getCaseDetailData(case_id);
        } else {
            isfromGuanZhu = false;
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
//                if (mCurPosY - mPosY > 0 && (Math.abs(mCurPosY - mPosY) > 18)) {
//                    rlCaseLibraryBottom.setAnimation(AnimationUtil.moveToViewLocation());
//                } else if (mCurPosY - mPosY < 0 && (Math.abs(mCurPosY - mPosY) > 18)) {
//                    rlCaseLibraryBottom.setAnimation(AnimationUtil.moveToViewBottom());
//                }
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

        memberEntity = AdskApplication.getInstance().getMemberEntity();
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
        if (position != 2) {
            Intent intent = new Intent(this, CaseLibraryDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constant.CaseLibraryDetail.CASE_DETAIL_BEAN, caseDetailBean);
            bundle.putInt(Constant.CaseLibraryDetail.CASE_DETAIL_POSTION, position == 0 ? topPosition : position - 3);
            intent.putExtras(bundle);
            this.startActivity(intent);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // mTimer.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //  mTimer.cancel();
    }

    private ListView caseLibraryNew;
    private LinearLayout llThumbUp;
    private String case_id;
    private CaseDetailBean caseDetailBean;
    private CaseLibraryAdapter mCaseLibraryAdapter;
    private List<ImagesBean> images;
    private RelativeLayout rlCaseLibraryHead;
    private View viewHead;
    private LinearLayout rlCaseLibraryBottom;
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
    private Map<String, String> livingRoom;
    private Map<String, String> toilet;
    private Map<String, String> style;
    private LinearLayout ll_fenxiang_up;
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
    private boolean isfromGuanZhu = false;
    private ImageView ivHeadThumbUp;
    private String firstCaseLibraryImageUrl;
    private int topPosition;

    private float mPosY = 0;
    private float mCurPosY = 0;

    private AlertView unFollowedAlertView;
    private DesignerInfoBean mDesignerInfo;
    private String mHs_uid;
    private String mNickName;
//
//    @Override
//    public void onShareClick() {
//        if (null != memberEntity) {
//
//            if (isWeixinAvilible(CaseLibraryNewActivity.this)) {
//                if (takePhotoPopWin == null) {
//                    takePhotoPopWin = new WXSharedPopWin(this, onClickListener);
//                }
//                takePhotoPopWin.showAtLocation(findViewById(R.id.main_library), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//            } else {
//                ToastUtil.showCustomToast(CaseLibraryNewActivity.this, getString(R.string.anzhuangweixin));
//            }
//
//        } else {
//            LoginUtils.doLogin(this);
//        }
//    }
//
//    @Override
//    public void onPraiseClick() {
//
//    }
//    private CountDownTimer mTimer;
}