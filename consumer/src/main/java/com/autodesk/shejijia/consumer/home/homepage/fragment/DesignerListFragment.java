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
import com.autodesk.shejijia.shared.components.common.tools.chatroom.JumpBean;
import com.autodesk.shejijia.shared.components.common.tools.chatroom.JumpToChatRoom;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullToRefreshLayout;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
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
 * <p>Description:设计师列表模块 </p>
 *
 * @author liuhea
 * @date 16/9/4
 *  
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
        setDefaultFindDesignerBean();
    }


    @Override
    protected void initListener() {
        super.initListener();
        mSeekDesignerAdapter.setOnItemChatClickListener(this);
        mListView.setOnItemClickListener(this);
        mPullToRefreshLayout.setOnRefreshListener(this);
    }

    /**
     * 处理筛选逻辑
     */
    public void handleFilterOption() {
        intent = new Intent(getActivity(), DesignerFiltrateActivity.class);
        startActivityForResult(intent, REQUEST_FILTRATE_CODE);
    }

    /**
     * 处理搜索逻辑
     */
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
            JumpBean jumpBean = new JumpBean();
            jumpBean.setReciever_user_name(receiver_name);
            jumpBean.setReciever_user_id(designer_id);
            jumpBean.setReciever_hs_uid(hs_uid);
            jumpBean.setMember_type(mMemberType);
            jumpBean.setAcs_member_id(member_id);
            JumpToChatRoom.getChatRoom(activity,jumpBean);

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
                ApiStatusUtil.getInstance().apiStatuError(volleyError, getActivity());
                CustomProgress.cancelDialog();
            }
        };
        MPServerHttpManager.getInstance().findDesignerList(findDesignerBean, offset, limit, okResponseCallback);
    }

    /**
     * 设置查找设计师默认值
     */
    private void setDefaultFindDesignerBean() {
        mFindDesignerBean.setNick_name("");
        mFindDesignerBean.setDesign_price_code("");
        mFindDesignerBean.setStart_experience("");
        mFindDesignerBean.setEnd_experience("");
        mFindDesignerBean.setStyle_names("");
        mFindDesignerBean.setStyle("");
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
        CustomProgress.show(getActivity(), "", false, null);
        onRefresh(mPullToRefreshLayout);
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
