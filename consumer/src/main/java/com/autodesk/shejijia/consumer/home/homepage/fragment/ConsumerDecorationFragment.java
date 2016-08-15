package com.autodesk.shejijia.consumer.home.homepage.fragment;

import android.view.View;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.consumer.adapter.ConsumerDecorationAdapter;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.DecorationListBean;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.DecorationNeedsListBean;
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
 * @file ConsumerDecorationFragment.java .
 * @brief 消费者家装订单 .
 */
public class ConsumerDecorationFragment extends BaseFragment implements
        View.OnClickListener,
        PullToRefreshLayout.OnRefreshListener {

    ///is_beishu:0 北舒套餐 1 非北舒.
    private static final String IS_NOT_BEI_SHU = "1";

    private String TAG = getClass().getSimpleName();
    private int LIMIT = 10;
    private int OFFSET = 0;

    private PullToRefreshLayout mPtrRefreshLayout;
    private PullListView mPlvConsumerDecoration;

    private DecorationListBean mDecorationListBean;
    private ConsumerDecorationAdapter mConsumerDecorationAdapter;
    private List<DecorationNeedsListBean> mDecorationNeedsList = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_consumer_decoration_new;
    }

    @Override
    protected void initView() {
        mPtrRefreshLayout = (PullToRefreshLayout) rootView.findViewById(R.id.ptr_refresh_layout);
        mPlvConsumerDecoration = (PullListView) rootView.findViewById(R.id.plv_consumer_decoration);
    }

    @Override
    protected void initData() {
        if (null == mConsumerDecorationAdapter) {
            mConsumerDecorationAdapter = new ConsumerDecorationAdapter(getActivity(), mDecorationNeedsList);
        }

        mPlvConsumerDecoration.setAdapter(mConsumerDecorationAdapter);
        getMyDecorationData(OFFSET, LIMIT, 1);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mPtrRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View v) {
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
                updateViewFromData(state);
                KLog.json(TAG, userInfo);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                CustomProgress.cancelDialog();
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, getActivity(),
                        AlertView.Style.Alert, null).show();
            }
        });
    }

    private void updateViewFromData(int state) {
        switch (state) {
            case 1:
                OFFSET = 10;
                mDecorationNeedsList.clear();
                break;
            case 2:
                OFFSET += 10;
                break;
            default:
                break;
        }
        mDecorationNeedsList.addAll(mDecorationListBean.getNeeds_list());
        mConsumerDecorationAdapter.notifyDataSetChanged();
    }

    /// 刷新.
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        getMyDecorationData(0, LIMIT, 1);
    }

    /// 加载更多.
    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        getMyDecorationData(OFFSET, LIMIT, 2);
    }
}
