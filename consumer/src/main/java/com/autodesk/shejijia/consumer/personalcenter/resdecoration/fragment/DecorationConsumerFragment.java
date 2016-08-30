package com.autodesk.shejijia.consumer.personalcenter.resdecoration.fragment;

import android.content.Intent;
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
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
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

        mPlvConsumerDecoration.setAdapter(mDecorationConsumerAdapter);
        mTvEmptyShow.setText(UIUtils.getString(R.string.empty_order_fitment));
        mIvEmptyShow.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_order_empty));
    }

    @Override
    protected void initListener() {
        super.initListener();
        mPullToRefreshLayout.setOnRefreshListener(this);

    }

//    private void getMyDecorationData111(){
//        String userInfo = ss();
//        mDecorationListBean = GsonUtil.jsonToBean(userInfo, DecorationListBean.class);
//        if (isRefreshOrLoadMore) {
//            updateViewFromData();
//            mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
//        } else {
//            mDecorationNeedsList.clear();
//            updateViewFromData();
//            mPullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
//        }
//
//        CustomProgress.cancelDialog();
//        KLog.json(TAG, userInfo);
//    }


    /**
     * 获取消费者家装订单
     */
    public void getMyDecorationData(final int offset, final int limit, final int state) {
        CustomProgress.show(getActivity(), "", false, null);
        MPServerHttpManager.getInstance().getMyDecorationData(offset, limit, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String userInfo = GsonUtil.jsonToString(jsonObject);
                DecorationListBean mDecorationListBean = GsonUtil.jsonToBean(userInfo, DecorationListBean.class);

                CustomProgress.cancelDialog();

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
                    }
                }


                KLog.json(TAG, userInfo);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        CustomProgress.cancelDialog();
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
//        getMyDecorationData111();
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
        getMyDecorationData(OFFSET, LIMIT, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//       if(resultCode == FlowMeasureCostActivity.RESULT_CODE){
//           MyToast.show(getActivity(),"刷新数据");
//       }

    }

}
