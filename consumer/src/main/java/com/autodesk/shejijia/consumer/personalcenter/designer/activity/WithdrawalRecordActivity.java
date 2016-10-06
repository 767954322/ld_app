package com.autodesk.shejijia.consumer.personalcenter.designer.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.consumer.personalcenter.designer.adapter.WithdrawalRecordAdapter;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.WithdrawaRecoldBean;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullToRefreshLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Malidong .
 * @version 1.0 .
 * @date 16-6-7
 * @file WithdrawalRecordActivity.java  .
 * @brief 提现记录页面  .
 */
public class WithdrawalRecordActivity extends NavigationBarActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_withdrawal_recold;
    }

    @Override
    protected void initView() {
        super.initView();
        ptrl = ((PullToRefreshLayout) findViewById(R.id.lv_withdrawal_recol_refresh_view));
        mListView = (ListView) findViewById(R.id.lv_withdrawal_recold);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleForNavbar(UIUtils.getString(R.string.my_property_withdrawal_record));
        memberEntity = AdskApplication.getInstance().getMemberEntity();
        designer_id = (memberEntity != null) ? memberEntity.getAcs_member_id() : null;
        mAdapter = new WithdrawalRecordAdapter(this, mList);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        ptrl.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (isFirstIn) {
            ptrl.autoRefresh();
            isFirstIn = false;
        }
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        OFFSET = 0;
        if (null != memberEntity) {
            getWithdrawalRecordData(designer_id, OFFSET, LIMIT, 0);
            return;
        }
        ptrl.loadmoreFinish(PullToRefreshLayout.FAIL);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        if (null != memberEntity) {
            getWithdrawalRecordData(designer_id, OFFSET, LIMIT, 1);
            return;
        }
        ptrl.loadmoreFinish(PullToRefreshLayout.FAIL);
    }

    private void notifyAdapter(JSONObject jsonObject, int state) {
        int isSucceed = PullToRefreshLayout.SUCCEED;
        try {
            String jsonString = GsonUtil.jsonToString(jsonObject);
            withdrawaRecoldBean = GsonUtil.jsonToBean(jsonString, WithdrawaRecoldBean.class);
            switch (state) {
                case 0:
                    OFFSET = 10;
                    mList.clear();
                    break;
                case 1:
                    OFFSET += 10;
                    break;
                default:
                    break;
            }
            if (withdrawaRecoldBean.getTranslog_list() != null && withdrawaRecoldBean.getTranslog_list().size() > 0) {
                mList.addAll(withdrawaRecoldBean.getTranslog_list());
            }
            mAdapter.notifyDataSetChanged();
            if (mList != null && mList.size() <= 0) {
                openAlertView(UIUtils.getString(R.string.tip_withdrawal_record));
            }

        } catch (Exception e) {
            e.printStackTrace();
            isSucceed = PullToRefreshLayout.FAIL;

        } finally {
            ptrl.loadmoreFinish(isSucceed);
        }
    }

    private void openAlertView(String content) {
        new AlertView(UIUtils.getString(R.string.tip), content, null, null, new String[]{UIUtils.getString(R.string.sure)}, this,
                AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                WithdrawalRecordActivity.this.finish();
            }
        }).show();
    }

    /**
     * 提现记录数据
     *
     * @param designer_id
     * @param offset
     * @param limit
     * @param state
     */
    public void getWithdrawalRecordData(String designer_id, int offset, int limit, final int state) {
        OkJsonRequest.OKResponseCallback callback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                notifyAdapter(jsonObject, state);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                ptrl.loadmoreFinish(PullToRefreshLayout.FAIL);
            }
        };
        MPServerHttpManager.getInstance().getWithdrawalRecordData(designer_id, offset, limit, callback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mList = null;
    }

    /// 控件.
    private PullToRefreshLayout ptrl;
    private ListView mListView;

    /// 变量　.
    private int LIMIT = 10;
    private int OFFSET = 0;
    private String designer_id;
    private boolean isFirstIn = true;

    /// 集合，类.
    private WithdrawaRecoldBean withdrawaRecoldBean;
    private WithdrawalRecordAdapter mAdapter;
    private MemberEntity memberEntity;
    private List<WithdrawaRecoldBean.TranslogListEntity> mList = new ArrayList<>();
}
