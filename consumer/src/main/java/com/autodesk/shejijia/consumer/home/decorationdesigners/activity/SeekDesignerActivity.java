package com.autodesk.shejijia.consumer.home.decorationdesigners.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.adapter.SeekDesignerAdapter;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.DesignerFiltrateBean;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.SeekDesignerBean;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.shared.components.common.appglobal.ApiManager;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullToRefreshLayout;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
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

import java.util.ArrayList;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 2016/2/25 0025 14:46 .
 * @file SeekDesignerActivity  .
 * @brief 查看设计师 .
 */
public class SeekDesignerActivity extends NavigationBarActivity implements SeekDesignerAdapter.OnItemChatClickListener, PullToRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_seek_designer;
    }

    @Override
    protected void initView() {
        super.initView();
        mListView = (ListView) findViewById(R.id.xlv_seek_designer);
        mPullToRefreshLayout = ((PullToRefreshLayout) findViewById(R.id.refresh_view));

        setImageForNavButton(ButtonType.RIGHT, R.drawable.icon_search);
        setImageForNavButton(ButtonType.SECONDARY, R.drawable.icon_filtrate_normal);
        
        /// TODO 九月份内容，暂时屏蔽 .
//        setVisibilityForNavButton(ButtonType.RIGHT, true);
//        setVisibilityForNavButton(ButtonType.SECONDARY, true);

        setVisibilityForNavButton(ButtonType.RIGHT, false);
        setVisibilityForNavButton(ButtonType.SECONDARY, false);
    }

    /// 数据逻辑.
    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleForNavbar(UIUtils.getString(R.string.find_designer));
        mSeekDesignerAdapter = new SeekDesignerAdapter(this, mDesignerListEntities);
        mListView.setAdapter(mSeekDesignerAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mSeekDesignerAdapter.setOnItemChatClickListener(this);
        mListView.setOnItemClickListener(this);
        mPullToRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void secondaryNavButtonClicked(View view) {
        super.secondaryNavButtonClicked(view);
        intent = new Intent(this, DesignerFiltrateActivity.class);
        intent.putExtra(Constant.CaseLibrarySearch.YEAR_INDEX, mDesignerFiltrateBean == null ? 0 : mDesignerFiltrateBean.getYearIndex());
        intent.putExtra(Constant.CaseLibrarySearch.STYLEL_INDEX, mDesignerFiltrateBean == null ? 0 : mDesignerFiltrateBean.getStyleIndex());
        intent.putExtra(Constant.CaseLibrarySearch.PRICE_INDEX, mDesignerFiltrateBean == null ? 0 : mDesignerFiltrateBean.getPriceIndex());
        this.startActivityForResult(intent, FILTRATE_REQUEST_CODE);
    }

    @Override
    protected void rightNavButtonClicked(View view) {
        super.rightNavButtonClicked(view);
        intent = new Intent(this, DesignerSearchActivity.class);
        startActivity(intent);
    }


    /**
     * 选中某一个设计师进入详情页面
     *
     * @param adapterView 　某一个ListView
     * @param view        　item的view的句柄
     * @param position    　position是适配器里的位置
     * @param l           　在listview里的第几行的位置
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        String designer_id = mDesignerListEntities.get(position).getDesigner().getAcs_member_id();
        String hs_uid = mDesignerListEntities.get(position).getHs_uid();
        Intent intent = new Intent(SeekDesignerActivity.this, SeekDesignerDetailActivity.class);
        intent.putExtra(Constant.ConsumerDecorationFragment.designer_id, designer_id);
        intent.putExtra(Constant.ConsumerDecorationFragment.hs_uid, hs_uid);
        startActivity(intent);
    }

    /**
     * 单击进入聊天页面，如没有登陆进入登陆注册页面
     */
    @Override
    public void OnItemChatClick(final int position) {
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (memberEntity != null) {
            member_id = memberEntity.getAcs_member_id();
            final String designer_id = mDesignerListEntities.get(position).getDesigner().getAcs_member_id();
            final String hs_uid = mDesignerListEntities.get(position).getHs_uid();
            final String mMemberType = memberEntity.getMember_type();
            final String receiver_name = mDesignerListEntities.get(position).getNick_name();
            final String recipient_ids = member_id + "," + designer_id + "," + ApiManager.getAdmin_User_Id(ApiManager.RUNNING_DEVELOPMENT);

            MPChatHttpManager.getInstance().retrieveMultipleMemberThreads(recipient_ids, 0, 10, new OkStringRequest.OKResponseCallback() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    MPNetworkUtils.logError(TAG, volleyError);
                }

                @Override
                public void onResponse(final String s) {

                    MPChatThreads mpChatThreads = MPChatThreads.fromJSONString(s);

                    final Intent intent = new Intent(SeekDesignerActivity.this, ChatRoomActivity.class);
                    intent.putExtra(ChatRoomActivity.RECIEVER_USER_ID, designer_id);
                    intent.putExtra(ChatRoomActivity.RECIEVER_USER_NAME, receiver_name);
                    intent.putExtra(ChatRoomActivity.MEMBER_TYPE, mMemberType);
                    intent.putExtra(ChatRoomActivity.ACS_MEMBER_ID, member_id);

                    if (mpChatThreads != null && mpChatThreads.threads.size() > 0) {

                        MPChatThread mpChatThread = mpChatThreads.threads.get(0);
                        int assetId = MPChatUtility.getAssetIdFromThread(mpChatThread);
                        intent.putExtra(ChatRoomActivity.THREAD_ID, mpChatThread.thread_id);
                        intent.putExtra(ChatRoomActivity.ASSET_ID, assetId + "");
                        intent.putExtra(ChatRoomActivity.RECIEVER_HS_UID, hs_uid);
                        SeekDesignerActivity.this.startActivity(intent);

                    } else {
                        MPChatHttpManager.getInstance().getThreadIdIfNotChatBefore(member_id, designer_id, new OkStringRequest.OKResponseCallback() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                MPNetworkUtils.logError(TAG, volleyError);
                            }

                            @Override
                            public void onResponse(String s) {
                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    String thread_id = jsonObject.getString("thread_id");
                                    intent.putExtra(ChatRoomActivity.ASSET_ID, "");
                                    intent.putExtra(ChatRoomActivity.RECIEVER_HS_UID, hs_uid);
                                    intent.putExtra(ChatRoomActivity.THREAD_ID, thread_id);
                                    SeekDesignerActivity.this.startActivity(intent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            });
        } else {
            AdskApplication.getInstance().doLogin(this);
        }
    }

    /**
     * 获取设计师列表.
     *
     * @param offset 页数
     * @param limit  　每页数据条数
     * @param state  　刷新的状态
     */
    public void getDesignerListData(int offset, int limit, final int state) {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    String info = GsonUtil.jsonToString(jsonObject);
                    mSeekDesignerBean = GsonUtil.jsonToBean(info, SeekDesignerBean.class);

                    updateViewFromData(state);
                } finally {
                    mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, SeekDesignerActivity.this,
                        AlertView.Style.Alert, null).show();
            }
        };
        MPServerHttpManager.getInstance().getFindDesignerData(offset, limit, okResponseCallback);
    }

    /**
     * 获取数据，更新页面
     *
     * @param state 　刷新用到的状态
     */
    private void updateViewFromData(int state) {
        switch (state) {
            case 0:
                OFFSET = 0;
                mDesignerListEntities.clear();
                break;
            case 1:
                OFFSET = 10;
                mDesignerListEntities.clear();
                break;
            case 2:
                OFFSET += 10;
                break;
            default:
                break;
        }
        mDesignerListEntities.addAll(this.mSeekDesignerBean.getDesigner_list());
        mSeekDesignerAdapter.notifyDataSetChanged();
    }

    /**
     * 第一次进入刷新页面
     *
     * @param hasFocus 判断是否刷新
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // 第一次进入自动刷新
        if (isFirstIn) {
            //changeState
            mPullToRefreshLayout.autoRefresh();
            isFirstIn = false;
        }
    }

    /// 下拉刷新.
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        getDesignerListData(0, LIMIT, 1);
    }

    /// 上拉加载.
    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        getDesignerListData(OFFSET, LIMIT, 2);
    }

    /// 刷新.
    public void updateNotify(DesignerFiltrateBean content) {
        this.mDesignerFiltrateBean = content;
        mPullToRefreshLayout.autoRefresh();
    }

    /**
     * 接收返回来的数据，并做出操作
     *
     * @param resultCode 条件码
     * @param data       回来的数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null == data) {
            return;
        }
        Bundle bundle = data.getExtras();
        switch (resultCode) {
            case DesignerFiltrateActivity.DF_RESULT_CODE:
                DesignerFiltrateBean designerFiltrateBean = (DesignerFiltrateBean) bundle.getSerializable(Constant.CaseLibrarySearch.DESIGNER_FILTRATE);
                updateNotify(designerFiltrateBean);
                break;
        }
    }

    /// 静态常量,常量,静态上下文.
    public static final int FILTRATE_REQUEST_CODE = 0x92;
    public static final String BLANK = "";
    /// 控件.
    private ListView mListView;
    private PullToRefreshLayout mPullToRefreshLayout;

    /// 变量.
    private int LIMIT = 10;
    private int OFFSET = 0;
    private boolean isFirstIn = true;
    private String member_id;
    private Intent intent;


    /// 集合，类.
    private SeekDesignerBean mSeekDesignerBean;
    private SeekDesignerAdapter mSeekDesignerAdapter;
    private DesignerFiltrateBean mDesignerFiltrateBean;
    private ArrayList<SeekDesignerBean.DesignerListEntity> mDesignerListEntities = new ArrayList<>();
}
