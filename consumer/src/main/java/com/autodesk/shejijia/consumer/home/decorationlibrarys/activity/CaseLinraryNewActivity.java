package com.autodesk.shejijia.consumer.home.decorationlibrarys.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
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
import com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter.CaseLibraryAdapter;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.CaseDetailBean;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.utils.AnimationUtil;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.consumer.utils.ToastUtil;
import com.autodesk.shejijia.consumer.wxapi.SendWXShared;
import com.autodesk.shejijia.shared.components.common.appglobal.ApiManager;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.shared.components.common.uielements.WXSharedPopWin;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.PictureProcessingUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.im.activity.ChatRoomActivity;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThread;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatThreads;
import com.autodesk.shejijia.shared.components.im.datamodel.MPChatUtility;
import com.autodesk.shejijia.shared.components.im.manager.MPChatHttpManager;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CaseLinraryNewActivity extends NavigationBarActivity implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener, View.OnTouchListener, View.OnClickListener {

    private ListView caseLibraryNew;
    private LinearLayout llThumbUp;
    private String case_id;
    private CaseDetailBean caseDetailBean;
    private CaseLibraryAdapter mCaseLibraryAdapter;
    private List<CaseDetailBean.ImagesEntity> images;
    private RelativeLayout rlCaseLibraryHead;
    private View viewHead;
    private RelativeLayout rlCaseLibraryBottom;
    private ImageView mdesignerAvater;
    private TextView mCaseLibraryText;
    private PolygonImageView pivImgCustomerHomeHeader;
    private ImageView ivCustomerIm;
    private TextView ivConsumeHomeDesigner;
    private ImageView ivGuanzu;
    private TextView tvCustomerHomeStyle;
    private TextView tvCustomerHomeRoom;
    private TextView tvCustomerHomeArea;
    private TextView tvThumbUp;
    private Map<String, String> roomHall;
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
    private ImageView ivHeadThumbUp;

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
        ivGuanzu = (ImageView) findViewById(R.id.iv_guanzu);
        tvCustomerHomeStyle = (TextView) findViewById(R.id.tv_customer_home_style);
        tvCustomerHomeRoom = (TextView) findViewById(R.id.tv_customer_home_room);
        tvCustomerHomeArea = (TextView) findViewById(R.id.tv_customer_home_area);
        tvThumbUp = (TextView) findViewById(R.id.tv_thumb_up);

        View view = LayoutInflater.from(this).inflate(R.layout.case_library_new_item, null);
        mdesignerAvater = (ImageView) view.findViewById(R.id.case_library_item_iv);

        View viewHead = LayoutInflater.from(this).inflate(R.layout.caselibrary_head, null);
        ll_fenxiang_down = (LinearLayout) viewHead.findViewById(R.id.ll_fenxiang);
        rlThumbUp = (LinearLayout) viewHead.findViewById(R.id.rl_thumb_up);
        tvheadThumbUp = (TextView) viewHead.findViewById(R.id.tv_thumb_up);
        ivHeadThumbUp = (ImageView) viewHead.findViewById(R.id.iv_thumb_up);

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
        pivImgCustomerHomeHeader.setOnClickListener(this);
        ivCustomerIm.setOnClickListener(this);
        ivGuanzu.setOnClickListener(this);
        rlThumbUp.setOnClickListener(this);
        caseLibraryNew.setOnItemClickListener(this);

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
            case R.id.rl_thumb_up://点赞
                if (null != memberEntity) {
                    if (!isMemberLike) {
                        sendThumbUp(caseDetailBean.getId());
                    } else {
                        //已经点过赞
                    }

                } else {
                    AdskApplication.getInstance().doLogin(this);
                }
                break;
            case R.id.iv_guanzu://关注
                if (null != memberEntity) {

                } else {
                    AdskApplication.getInstance().doLogin(this);
                }
                break;
            case R.id.ll_fenxiang://分享
                if (null != memberEntity) {

                    if (isWeixinAvilible(CaseLinraryNewActivity.this)) {
                        if (takePhotoPopWin == null) {
                            takePhotoPopWin = new WXSharedPopWin(this, onClickListener);
                        }
                        takePhotoPopWin.showAtLocation(findViewById(R.id.main_library), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    } else {
                        ToastUtil.showCustomToast(CaseLinraryNewActivity.this, "请先安装微信");
                    }

                } else {
                    AdskApplication.getInstance().doLogin(this);
                }
                break;
            case R.id.piv_img_customer_home_header:    /// 进入设计师详情页面.
                Intent intent = new Intent(CaseLinraryNewActivity.this, SeekDesignerDetailActivity.class);
                intent.putExtra(Constant.ConsumerDecorationFragment.designer_id, designer_id);
                intent.putExtra(Constant.ConsumerDecorationFragment.hs_uid, hs_uid);
                startActivity(intent);
                break;
            case R.id.img_look_more_detail_chat:    /// 进入聊天页面,如果没有登陆则进入登陆注册页面.
                if (memberEntity != null) {
                    member_id = memberEntity.getAcs_member_id();
                    mMemberType = memberEntity.getMember_type();
                    final String designer_id = caseDetailBean.getDesigner_info().getDesigner().getAcs_member_id();
                    final String hs_uid = caseDetailBean.getHs_designer_uid();
                    final String receiver_name = caseDetailBean.getDesigner_info().getNick_name();
                    final String recipient_ids = member_id + "," + designer_id + "," + ApiManager.getAdmin_User_Id(ApiManager.RUNNING_DEVELOPMENT);

                    MPChatHttpManager.getInstance().retrieveMultipleMemberThreads(recipient_ids, 0, 10, new OkStringRequest.OKResponseCallback() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            MPNetworkUtils.logError(TAG, volleyError);
                        }

                        @Override
                        public void onResponse(String s) {
                            MPChatThreads mpChatThreads = MPChatThreads.fromJSONString(s);

                            Intent intent = new Intent(CaseLinraryNewActivity.this, ChatRoomActivity.class);
                            intent.putExtra(ChatRoomActivity.RECIEVER_USER_ID, designer_id);
                            intent.putExtra(ChatRoomActivity.RECIEVER_USER_NAME, receiver_name);
                            intent.putExtra(ChatRoomActivity.ACS_MEMBER_ID, member_id);
                            intent.putExtra(ChatRoomActivity.MEMBER_TYPE, mMemberType);

                            if (mpChatThreads != null && mpChatThreads.threads.size() > 0) {
                                MPChatThread mpChatThread = mpChatThreads.threads.get(0);

                                int assetId = MPChatUtility.getAssetIdFromThread(mpChatThread);
                                intent.putExtra(ChatRoomActivity.THREAD_ID, mpChatThread.thread_id);
                                intent.putExtra(ChatRoomActivity.ASSET_ID, assetId + "");

                            } else {

                                intent.putExtra(ChatRoomActivity.RECIEVER_HS_UID, hs_uid);
                                intent.putExtra(ChatRoomActivity.ASSET_ID, "");
                            }
                            startActivity(intent);

                        }

                    });
                } else {
                    AdskApplication.getInstance().doLogin(this);
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
            String webUrl = "http://blog.csdn.net/qinglianluan/article/details/16847735";
            String imageUrl = "http://img1.imgtn.bdimg.com/it/u=3731860233,1648607528&fm=21&gp=0.jpg";
            switch (v.getId()) {

                case R.id.tv_wx_shared_tofriends:

                    ifIsSharedToFriends = true;
                    try {
                        SendWXShared.sendProjectToWX(webUrl, "分享标题", "分享最新内容", ifIsSharedToFriends, imageUrl);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.tv_wx_shared_tocircleof_friends:
                    ifIsSharedToFriends = false;
                    try {
                        SendWXShared.sendProjectToWX(webUrl, "分享标题", "分享最新内容", ifIsSharedToFriends, imageUrl);
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
                ToastUtil.showCustomToast(CaseLinraryNewActivity.this, "点赞成功");
                ivThumbUp.setBackgroundResource(R.mipmap.yidianzan_ico);
                ivHeadThumbUp.setBackgroundResource(R.mipmap.yidianzan_ico);
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
                    isMemberLike = jsonObject.getBoolean("is_member_like");
                    if (isMemberLike) {
                        rlThumbUp.setOnClickListener(null);
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
    public void getCaseDetailData(final String case_id) {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                String info = GsonUtil.jsonToString(jsonObject);
                caseDetailBean = GsonUtil.jsonToBean(info, CaseDetailBean.class);
                setTitleForNavbar(caseDetailBean.getTitle());

                hs_uid = caseDetailBean.getHs_designer_uid();

                if (caseDetailBean != null && caseDetailBean.getDesigner_info() != null) {
                    designer_id = caseDetailBean.getDesigner_info().getDesigner().getAcs_member_id();
                }
                //登录状态判断是否点赞
                if (null != memberEntity) {
                    getThumbUp(caseDetailBean.getId());
                }

                images = caseDetailBean.getImages();
                //查找是否是封面图片  若是就添加到头部
                for (int i = 0; i < images.size(); i++) {
                    if (images.get(i).isIs_primary() == true) {
                        ImageUtils.displayIconImage(images.get(i).getFile_url() + Constant.CaseLibraryDetail.JPG, mdesignerAvater);
                    }
                }
                mCaseLibraryAdapter = new CaseLibraryAdapter(CaseLinraryNewActivity.this, images);
                caseLibraryNew.setAdapter(mCaseLibraryAdapter);
                //设置简介
                String introduction = caseDetailBean.getDescription();
                if (introduction != null) {
                    mCaseLibraryText.setText("          " + introduction);
                } else {
                    mCaseLibraryText.setText(R.string.nodata);
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
                tvThumbUp.setText(getString(R.string.thumbup_conunt) + caseDetailBean.getFavorite_count());
                tvheadThumbUp.setText(getString(R.string.thumbup_conunt) + caseDetailBean.getFavorite_count());
                ivConsumeHomeDesigner.setText(caseDetailBean.getDesigner_info().getFirst_name());
                ImageUtils.displayIconImage(caseDetailBean.getDesigner_info().getAvatar(), pivImgCustomerHomeHeader);

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
    protected void onResume() {
        super.onResume();
        memberEntity = AdskApplication.getInstance().getMemberEntity();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
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
                if (mCurPosY - mPosY > 0 && (Math.abs(mCurPosY - mPosY) > 20)) {
                    rlCaseLibraryBottom.setAnimation(AnimationUtil.moveToViewLocation());
                } else if (mCurPosY - mPosY < 0 && (Math.abs(mCurPosY - mPosY) > 20)) {
                    rlCaseLibraryBottom.setAnimation(AnimationUtil.moveToViewBottom());
                }
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
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, CaseLibraryDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.CaseLibraryDetail.CASE_DETAIL_BEAN, caseDetailBean);
        bundle.putInt(Constant.CaseLibraryDetail.CASE_DETAIL_POSTION, position - 3);
        intent.putExtras(bundle);
        this.startActivity(intent);
    }
}
