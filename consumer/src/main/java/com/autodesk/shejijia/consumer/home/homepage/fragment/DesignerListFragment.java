package com.autodesk.shejijia.consumer.home.homepage.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.activity.DesignerFiltrateActivity;
import com.autodesk.shejijia.consumer.home.decorationdesigners.activity.DesignerSearchActivity;
import com.autodesk.shejijia.consumer.home.decorationdesigners.activity.SeekDesignerDetailActivity;
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
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 2016/3/4 0007 9:42 .
 * @file BidHallFragment  .
 * @brief 应标大厅fragment, 展示可应标的事例 .
 */
public class DesignerListFragment extends BaseFragment
        implements PullToRefreshLayout.OnRefreshListener,
        AdapterView.OnItemClickListener,
        SeekDesignerAdapter.OnItemChatClickListener {

    private static final int REQUEST_FILTRATE_CODE = 0x12;
    private static final int REQUEST_SEARCH_CODE = 0x13;

    private ArrayList<SeekDesignerBean.DesignerListEntity> mDesignerListEntities = new ArrayList<>();
    private SeekDesignerAdapter mSeekDesignerAdapter;
    private FindDesignerBean mFindDesignerBean = new FindDesignerBean();

    private ListView mListView;
    private RelativeLayout mRlEmpty;
    private TextView mTvEmptyMessage;
    private PullToRefreshLayout mPullToRefreshLayout;
    private String member_id;
    private int LIMIT = 10;
    private int OFFSET = 0;

    private Intent intent;

    public static DesignerListFragment getInstance() {
        DesignerListFragment uhf = new DesignerListFragment();
        return uhf;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_designer_list;
    }

    @Override
    protected void initView() {
        mListView = (ListView) rootView.findViewById(R.id.xlv_seek_designer);
        mPullToRefreshLayout = ((PullToRefreshLayout) rootView.findViewById(R.id.refresh_view));
        mRlEmpty = (RelativeLayout) rootView.findViewById(R.id.rl_empty);
        mTvEmptyMessage = (TextView) rootView.findViewById(R.id.tv_empty_message);
    }

    @Override
    protected void initData() {
        mSeekDesignerAdapter = new SeekDesignerAdapter(getActivity(), mDesignerListEntities);
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

    /**
     * 处理
     */
    public void handleFilterOption() {
        intent = new Intent(getActivity(), DesignerFiltrateActivity.class);
        startActivityForResult(intent, REQUEST_FILTRATE_CODE);
    }

    public void handleSearchOption() {
        intent = new Intent(getActivity(), DesignerSearchActivity.class);
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

        Intent intent = new Intent(getActivity(), SeekDesignerDetailActivity.class);
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
            if (null == designer) {
                return;
            }
            final String designer_id = designer.getAcs_member_id();
            final String hs_uid = designerListEntity.getHs_uid();
            if (TextUtils.isEmpty(designer_id) || TextUtils.isEmpty(hs_uid)) {
                return;
            }

            final String receiver_name = designerListEntity.getNick_name();
            final String recipient_ids = member_id + "," + designer_id + "," + ApiManager.getAdmin_User_Id(ApiManager.RUNNING_DEVELOPMENT);

            MPChatHttpManager.getInstance().retrieveMultipleMemberThreads(recipient_ids, 0, 10, new OkStringRequest.OKResponseCallback() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    MPNetworkUtils.logError(TAG, volleyError);
                    ApiStatusUtil.getInstance().apiStatuError(volleyError, getActivity());
                }

                @Override
                public void onResponse(final String s) {

                    MPChatThreads mpChatThreads = MPChatThreads.fromJSONString(s);

                    final Intent intent = new Intent(getActivity(), ChatRoomActivity.class);
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
                                ApiStatusUtil.getInstance().apiStatuError(volleyError, getActivity());
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
            AdskApplication.getInstance().doLogin(getActivity());
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
                new AlertView(UIUtils.getString(R.string.tip),
                        UIUtils.getString(R.string.network_error), null,
                        new String[]{UIUtils.getString(R.string.sure)},
                        null, getActivity(),
                        AlertView.Style.Alert, null).show();
                CustomProgress.cancelDialog();
            }
        };
        MPServerHttpManager.getInstance().findDesignerList(findDesignerBean, offset, limit, okResponseCallback);
    }


    private void updateViewFromFindDesigner(SeekDesignerBean seekDesignerBean, int state) {
        CustomProgress.cancelDialog();

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

    @Override
    public void onResume() {
        super.onResume();
        mPullToRefreshLayout.autoRefresh();
        onRefresh(mPullToRefreshLayout);
        CustomProgress.show(getActivity(), "", false, null);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && null != data) {
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
}
