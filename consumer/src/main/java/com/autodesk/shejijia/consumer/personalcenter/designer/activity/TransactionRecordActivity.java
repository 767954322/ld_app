package com.autodesk.shejijia.consumer.personalcenter.designer.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.consumer.personalcenter.designer.adapter.TransactionRecordAdapter;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.TransactionRecordBean;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullToRefreshLayout;
import com.google.gson.Gson;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 16-6-7
 * @file TransactionRecordActivity.java  .
 * @brief 交易记录页面  .
 */
public class TransactionRecordActivity extends NavigationBarActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_transaction_record;
    }

    @Override
    protected void initView() {
        super.initView();
        ptrl = ((PullToRefreshLayout) findViewById(R.id.lv_transaction_refresh_view));
        mListView = (ListView) findViewById(R.id.lv_transaction_record);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleForNavbar(UIUtils.getString(R.string.my_property_transaction_record));
        memberEntity = AdskApplication.getInstance().getMemberEntity();
        designer_id = (memberEntity!=null)?memberEntity.getAcs_member_id():null;
        mAdapter = new TransactionRecordAdapter(this, mList);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        ptrl.setOnRefreshListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
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
            getTransactionRecordData(designer_id, OFFSET, LIMIT, 0);
            return;
        }
        ptrl.loadmoreFinish(PullToRefreshLayout.FAIL);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        if (null != memberEntity) {
            getTransactionRecordData(designer_id, OFFSET, LIMIT, 1);
            return;
        }
        ptrl.loadmoreFinish(PullToRefreshLayout.FAIL);
    }

    /**
     * @param jsonObject
     * @param state
     * @brief 刷新数据 .
     */
    private void notifyAdapter(JSONObject jsonObject, int state) {
        int isSucceed = PullToRefreshLayout.SUCCEED;
        try {
            String jsonString = GsonUtil.jsonToString(jsonObject);
            KLog.json(TAG, jsonString);
            transactionRecordBean = new Gson().fromJson(jsonString, TransactionRecordBean.class);
            switch (state) {
                case 0:
                    OFFSET = 10;
                    if (null != mList) {
                        mList.clear();
                    }
                    break;
                case 1:
                    OFFSET += 10;
                    break;
                default:
                    break;
            }
            if (transactionRecordBean.getDesigner_trans_list() != null && transactionRecordBean.getDesigner_trans_list().size() > 0) {
                mList.addAll(transactionRecordBean.getDesigner_trans_list());

            }
            mAdapter.notifyDataSetChanged();
            if (mList != null && mList.size() <= 0) {
                openAlertView(UIUtils.getString(R.string.tip_transaction_record));
            }

        }catch ( Exception e){
            e.printStackTrace();
            isSucceed = PullToRefreshLayout.FAIL;

        }finally {
            ptrl.loadmoreFinish(isSucceed);
        }
    }

    /**
     * @param content
     * @brief 打开AlertView .
     */
    private void openAlertView(String content) {
        new AlertView(UIUtils.getString(R.string.tip), content, null, new String[]{UIUtils.getString(R.string.sure)}, null, this,
                AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                TransactionRecordActivity.this.finish();
            }
        }).show();
    }

    /**
     * @param designer_id
     * @param offset
     * @param limit
     * @param state
     * @brief 获取交易记录数据 .
     */
    public void getTransactionRecordData(String designer_id, int offset, int limit, final int state) {
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
        MPServerHttpManager.getInstance().getTransactionRecordData(designer_id, offset, limit, callback);
    }

    @Override
    protected void onDestroy() {
//        mList = null;
        super.onDestroy();
    }

    /// 变量.
    private int LIMIT = 10;
    private int OFFSET = 0;
    private String designer_id;
    private boolean isFirstIn = true;

    ///　控件.
    private PullToRefreshLayout ptrl;
    private ListView mListView;

    /// 集合，类.
    private TransactionRecordAdapter mAdapter;
    private TransactionRecordBean transactionRecordBean;
    private MemberEntity memberEntity;
    private List<TransactionRecordBean.DesignerTransListEntity> mList = new ArrayList<>();


}
