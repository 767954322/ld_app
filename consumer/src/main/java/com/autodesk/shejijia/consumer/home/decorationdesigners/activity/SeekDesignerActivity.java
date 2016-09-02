package com.autodesk.shejijia.consumer.home.decorationdesigners.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.adapter.SeekDesignerAdapter;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.DesignerInfoBean;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.FindDesignerBean;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.SeekDesignerBean;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.shared.components.common.appglobal.ApiManager;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.network.OkStringRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
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

    private static final int REQUEST_FILTRATE_CODE = 0x12;
    private static final int REQUEST_SEARCH_CODE = 0x13;

    private RelativeLayout mRlEmpty;
    private ImageView mIvDefaultEmpty;
    private TextView mTvEmptyMessage;


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

        mRlEmpty = (RelativeLayout) findViewById(R.id.rl_empty);
        mIvDefaultEmpty = (ImageView) findViewById(R.id.iv_default_empty);
        mTvEmptyMessage = (TextView) findViewById(R.id.tv_empty_message);

        /// TODO 九月份内容 .
        setVisibilityForNavButton(ButtonType.RIGHT, true);
        setVisibilityForNavButton(ButtonType.SECONDARY, true);

    }

    /// 数据逻辑.
    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleForNavbar(UIUtils.getString(R.string.find_designer));
        mSeekDesignerAdapter = new SeekDesignerAdapter(this, mDesignerListEntities);
        mListView.setAdapter(mSeekDesignerAdapter);
        mFindDesignerBean.setNick_name("");
        mFindDesignerBean.setDesign_price_code("");
        mFindDesignerBean.setStart_experience("");
        mFindDesignerBean.setEnd_experience("");
        mFindDesignerBean.setStyle_names("");

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
        startActivityForResult(intent, REQUEST_FILTRATE_CODE);
    }

    @Override
    protected void rightNavButtonClicked(View view) {
        super.rightNavButtonClicked(view);
        intent = new Intent(this, DesignerSearchActivity.class);
        startActivityForResult(intent, REQUEST_SEARCH_CODE);
    }


    /**
     * 选中某一个设计师进入详情页面
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        SeekDesignerBean.DesignerListEntity designerListEntity = mDesignerListEntities.get(position);
        if (null == designerListEntity) {
            return;
        }
        DesignerInfoBean designer = designerListEntity.getDesigner();

        String designer_id = designer.getAcs_member_id();
        String hs_uid = designerListEntity.getHs_uid();
        if (TextUtils.isEmpty(designer_id) || TextUtils.isEmpty(hs_uid)) {
            return;
        }

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
            final String mMemberType = memberEntity.getMember_type();
            SeekDesignerBean.DesignerListEntity designerListEntity = mDesignerListEntities.get(position);
            if (null == designerListEntity) {
                return;
            }
            DesignerInfoBean designer = designerListEntity.getDesigner();
            if (null==designer){
                return;
            }
            final String designer_id = designer.getAcs_member_id();
            final String hs_uid = designerListEntity.getHs_uid();
            if (TextUtils.isEmpty(designer_id) || TextUtils.isEmpty(hs_uid)) {
                return;
            }

            final String receiver_name = designerListEntity.getNick_name();
            final String recipient_ids = member_id + "," + designer_id + "," + ApiManager.getAdmin_User_Id();

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
                        startActivity(intent);

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
                                    startActivity(intent);
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
     * 筛选设计师
     */
    private void findDesignerList(FindDesignerBean findDesignerBean, int offset, int limit, final int state) {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String filterDesignerString = GsonUtil.jsonToString(jsonObject);
                SeekDesignerBean seekDesignerBean = GsonUtil.jsonToBean(filterDesignerString, SeekDesignerBean.class);
                updateViewFromFindDesigner(seekDesignerBean, state);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
                ApiStatusUtil.getInstance().apiStatuError(volleyError,SeekDesignerActivity.this);
                CustomProgress.cancelDialog();
            }
        };
        MPServerHttpManager.getInstance().findDesignerList(findDesignerBean, offset, limit, okResponseCallback);

    }


    private void updateViewFromFindDesigner(SeekDesignerBean seekDesignerBean, int state) {

        if (null != seekDesignerBean && seekDesignerBean.getCount() <= 0) {
            mRlEmpty.setVisibility(View.VISIBLE);
            mPullToRefreshLayout.setVisibility(View.GONE);
            mTvEmptyMessage.setText("暂无结果");
        } else {
            mPullToRefreshLayout.setVisibility(View.VISIBLE);
            mRlEmpty.setVisibility(View.GONE);
        }

        switch (state) {
            case 0:
                OFFSET = 10;
                mDesignerListEntities.clear();
                break;
            case 1:
                OFFSET += 10;
                break;
            default:
                break;
        }
        mDesignerListEntities.addAll(seekDesignerBean.getDesigner_list());
        mSeekDesignerAdapter.notifyDataSetChanged();
        mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
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
//            mPullToRefreshLayout.autoRefresh();
            onRefresh(mPullToRefreshLayout);
            CustomProgress.show(this,"",false,null);
            isFirstIn = false;
        }
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        findDesignerList(mFindDesignerBean, 0, LIMIT, 0);
    }


    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        findDesignerList(mFindDesignerBean, OFFSET, LIMIT, 1);
    }

    public void updateNotify(FindDesignerBean findDesignerBean) {
        this.mFindDesignerBean = findDesignerBean;
        findDesignerList(mFindDesignerBean, 0, LIMIT, 0);
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
        if (resultCode == RESULT_OK && null != data) {
            switch (requestCode) {
                case REQUEST_FILTRATE_CODE:
                case REQUEST_SEARCH_CODE:
                    FindDesignerBean findDesignerBean =
                            (FindDesignerBean) data.getSerializableExtra(Constant.CaseLibrarySearch.DESIGNER_FILTRATE);
                    Log.d("SeekDesignerActivity", "findDesignerBean:" + findDesignerBean);
                    updateNotify(findDesignerBean);
                    break;

                default:
                    break;
            }
        }
    }

    private ListView mListView;
    private PullToRefreshLayout mPullToRefreshLayout;

    private int LIMIT = 10;
    private int OFFSET = 0;
    private boolean isFirstIn = true;
    private String member_id;
    private Intent intent;

//    private SeekDesignerBean mSeekDesignerBean;
    private SeekDesignerAdapter mSeekDesignerAdapter;
    private FindDesignerBean mFindDesignerBean = new FindDesignerBean();
    private ArrayList<SeekDesignerBean.DesignerListEntity> mDesignerListEntities = new ArrayList<>();
}
