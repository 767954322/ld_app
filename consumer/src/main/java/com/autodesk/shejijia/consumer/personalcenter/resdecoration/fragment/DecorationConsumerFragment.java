package com.autodesk.shejijia.consumer.personalcenter.resdecoration.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.DecorationListBean;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.DecorationNeedsListBean;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.adapter.DecorationConsumerAdapter;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.TipWorkFlowTemplateBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.WkFlowStateInfoBean;
import com.autodesk.shejijia.consumer.uielements.pulltorefresh.PullListView;
import com.autodesk.shejijia.consumer.uielements.pulltorefresh.PullToRefreshLayout;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.consumer.utils.WkFlowStateMap;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.im.constants.BroadCastInfo;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-8-15 .
 * @file DecorationConsumerFragment.java .
 * @brief 消费者家装订单主页面 .
 */
public class DecorationConsumerFragment extends BaseFragment implements PullToRefreshLayout.OnRefreshListener {

    public static DecorationConsumerFragment getInstance() {
        DecorationConsumerFragment uhf = new DecorationConsumerFragment();
        return uhf;
    }

    private String TAG = getClass().getSimpleName();
    private int LIMIT = 10;
    private int OFFSET = 0;
    private boolean isRefreshOrLoadMore = false;

    private PullListView mPlvConsumerDecoration;
    private PullToRefreshLayout mPullToRefreshLayout;
    private RelativeLayout mRlEmpty;
    private ImageView mIvEmptyShow;
    private TextView mTvEmptyShow;
    private RebuildFragmentReceiver mRebuildFragmentReceiver;
    private DecorationConsumerAdapter mDecorationConsumerAdapter;
    private List<DecorationNeedsListBean> mDecorationNeedsList = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_consumer_decoration_new;
    }

    @Override
    protected void initView() {
        mPlvConsumerDecoration = (PullListView) rootView.findViewById(R.id.plv_consumer_decoration);
        mPullToRefreshLayout = (PullToRefreshLayout) rootView.findViewById(R.id.refresh_view);
        mRlEmpty = (RelativeLayout) rootView.findViewById(R.id.rl_empty);
        mTvEmptyShow = (TextView) rootView.findViewById(R.id.tv_empty_message);
        mIvEmptyShow = (ImageView) rootView.findViewById(R.id.iv_default_empty);
    }

    @Override
    protected void initData() {
        if (null == mDecorationConsumerAdapter) {
            mDecorationConsumerAdapter = new DecorationConsumerAdapter(getActivity(), mDecorationNeedsList);
        }
        if (null == mRebuildFragmentReceiver) {
            registerBroadCast();
        }
        CustomProgress.show(getActivity(), "", false, null);

        mPlvConsumerDecoration.setAdapter(mDecorationConsumerAdapter);
        mTvEmptyShow.setText(UIUtils.getString(R.string.empty_order_fitment));
        mIvEmptyShow.setImageDrawable(UIUtils.getDrawable(R.drawable.bg_shejijia_emty));
    }

    @Override
    protected void initListener() {
        super.initListener();
        mPullToRefreshLayout.setOnRefreshListener(this);
    }

    /**
     * 获取消费者家装订单
     */
    public void getMyDecorationData(final int offset, final int limit, final int state) {
        CustomProgress.showDefaultProgress(getActivity());
        MPServerHttpManager.getInstance().getMyDecorationData(offset, limit, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String userInfo = GsonUtil.jsonToString(jsonObject);
                DecorationListBean mDecorationListBean = GsonUtil.jsonToBean(userInfo, DecorationListBean.class);



                if (isRefreshOrLoadMore) {
                    updateViewFromData(mDecorationListBean);
                    mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                } else {
                    mDecorationNeedsList.clear();
                    updateViewFromData(mDecorationListBean);
                    mPullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    int count = mDecorationListBean.getCount();
                    if (count == 0) {
                        mRlEmpty.setVisibility(View.VISIBLE);
                        return;
                    } else {
                        mRlEmpty.setVisibility(View.GONE);
//                        mPlvConsumerDecoration.setSelection(0);
                    }
                }
                scheduleTask();
                LogUtils.i(TAG, userInfo);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
                mPullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                CustomProgress.cancelDialog();
                ApiStatusUtil.getInstance().apiStatuError(volleyError, getActivity());

            }
        });
    }

    /**
     * 为了解决adapter刷新数据时出现的数据显示上的延迟,添加的延时取消dialog任务
     */
    private void scheduleTask(){
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                CustomProgress.cancelDialog();
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask,500);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CustomProgress.cancelDialog();
        if (mRebuildFragmentReceiver != null) {
            getActivity().unregisterReceiver(mRebuildFragmentReceiver);
        }
    }

    private void updateViewFromData(DecorationListBean mDecorationListBean) {
        List<DecorationNeedsListBean> needs_list = mDecorationListBean.getNeeds_list();
        mDecorationNeedsList.addAll(needs_list);
        mDecorationConsumerAdapter.notifyDataSetChanged();
    }

    //刷新数据
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        isRefreshOrLoadMore = false;
        getMyDecorationData(0, LIMIT, 1);
        OFFSET = 0;
    }

    //加载数据
    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

        isRefreshOrLoadMore = true;
        OFFSET = OFFSET + 10;
        getMyDecorationData(OFFSET, LIMIT, 1);
    }

    @Override
    public void onResume() {
        super.onResume();
        CustomProgress.showDefaultProgress(getActivity());
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (null != memberEntity && Constant.UerInfoKey.CONSUMER_TYPE.equals(memberEntity.getMember_type())) {
            getMyDecorationData(OFFSET, LIMIT, 1);
            getWkFlowStatePointInformation();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 获取全流程节点提示信息
     */
    public void getWkFlowStatePointInformation() {
        MPServerHttpManager.getInstance().getAll_WkFlowStatePointInformation(new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CustomProgress.cancelDialog();
                ApiStatusUtil.getInstance().apiStatuError(volleyError, getActivity());
            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                String jsonString = GsonUtil.jsonToString(jsonObject);
                WkFlowStateInfoBean WkFlowStateInfoBean = GsonUtil.jsonToBean(jsonString, WkFlowStateInfoBean.class);
                List<TipWorkFlowTemplateBean> tip_work_flow_template = WkFlowStateInfoBean.getTip_work_flow_template();
                WkFlowStateMap.sWkFlowBeans = tip_work_flow_template;
                scheduleTask();
            }
        });
    }

    public void rebuildFragment() {
        LIMIT = 10;
        OFFSET = 0;
        onRefresh(mPullToRefreshLayout);
    }

    private void registerBroadCast() {
        mRebuildFragmentReceiver = new RebuildFragmentReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadCastInfo.LOGIN_ACTIVITY_FINISHED);
        activity.registerReceiver(mRebuildFragmentReceiver, filter);
    }

    class RebuildFragmentReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equalsIgnoreCase(BroadCastInfo.LOGIN_ACTIVITY_FINISHED)) {
                MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
                if (mMemberEntity != null && Constant.UerInfoKey.CONSUMER_TYPE.equals(mMemberEntity.getMember_type())) {
                    rebuildFragment();
                }
            }
        }
    }
}
