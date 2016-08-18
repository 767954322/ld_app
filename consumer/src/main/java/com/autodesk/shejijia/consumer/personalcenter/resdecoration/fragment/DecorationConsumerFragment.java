package com.autodesk.shejijia.consumer.personalcenter.resdecoration.fragment;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.DecorationListBean;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.DecorationNeedsListBean;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.adapter.DecorationConsumerAdapter;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullListView;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullToRefreshLayout;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-8-15 .
 * @file DecorationConsumerFragment.java .
 * @brief 消费者家装订单主页面 .
 */
public class DecorationConsumerFragment extends BaseFragment implements PullToRefreshLayout.OnRefreshListener{

    ///is_beishu:0 北舒套餐 1 非北舒.
    private static final String DECORATION_NEEDS_ID = "DecorationConsumerFragment";

    private String TAG = getClass().getSimpleName();
    private int LIMIT = 10;
    private int OFFSET = 0;
    private boolean isRefreshOrLoadMore = true;

    private PullListView mPlvConsumerDecoration;
    private PullToRefreshLayout mPullToRefreshLayout;

    private DecorationListBean mDecorationListBean;
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
    }

    @Override
    protected void initData() {
        if (null == mDecorationConsumerAdapter) {
            mDecorationConsumerAdapter = new DecorationConsumerAdapter(getActivity(), mDecorationNeedsList);
        }

        mPlvConsumerDecoration.setAdapter(mDecorationConsumerAdapter);

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
        CustomProgress.show(getActivity(), "", false, null);
        MPServerHttpManager.getInstance().getMyDecorationData(offset, limit, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                String userInfo = GsonUtil.jsonToString(jsonObject);
                mDecorationListBean = GsonUtil.jsonToBean(userInfo, DecorationListBean.class);

                if (isRefreshOrLoadMore){
                    mDecorationNeedsList.clear();
                    updateViewFromData();
                    mPullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }else {
                    updateViewFromData();
                    mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }

                KLog.json(TAG, userInfo);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                mPullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                CustomProgress.cancelDialog();
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, getActivity(),
                        AlertView.Style.Alert, null).show();
            }
        });
    }

    private void updateViewFromData() {
        mDecorationNeedsList.addAll(mDecorationListBean.getNeeds_list());
        mDecorationConsumerAdapter.notifyDataSetChanged();
    }
    //刷新数据
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        isRefreshOrLoadMore = true;
        getMyDecorationData(0, LIMIT, 1);
        OFFSET = 0;
    }
    //加载数据
    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

        isRefreshOrLoadMore = false;
        OFFSET = OFFSET + 10;
        getMyDecorationData(OFFSET, LIMIT, 1);
    }

    @Override
    public void onResume() {
        super.onResume();
        getMyDecorationData(OFFSET, LIMIT, 1);
    }
}
