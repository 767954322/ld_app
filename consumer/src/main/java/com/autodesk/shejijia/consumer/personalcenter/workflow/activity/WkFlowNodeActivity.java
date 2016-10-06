package com.autodesk.shejijia.consumer.personalcenter.workflow.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.OrderCommonEntity;
import com.autodesk.shejijia.consumer.personalcenter.workflow.adapter.WkFlowStateAdapter;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.shared.components.common.utility.StringUtils;

import java.util.ArrayList;
import cn.finalteam.loadingviewfinal.ListViewFinal;
import cn.finalteam.loadingviewfinal.OnDefaultRefreshListener;
import cn.finalteam.loadingviewfinal.PtrClassicFrameLayout;
import cn.finalteam.loadingviewfinal.PtrFrameLayout;


public class WkFlowNodeActivity extends BaseWorkFlowActivity implements AdapterView.OnItemClickListener,View.OnClickListener{

    @Override
    protected int getLayoutResId()
    {
        return R.layout.activity_designer_common_meal_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        mListView = (ListViewFinal) findViewById(R.id.lv_designer_meal_detail);
        tvDesignerName = (TextView) findViewById(R.id.tv_designer_name);
        mPtrLayout = (PtrClassicFrameLayout) findViewById(R.id.ptr_layout);
        polygonImageView = (PolygonImageView)findViewById(R.id.piv_consumer_order_photo_01);
        btnStopDemand = (Button)findViewById(R.id.btn_stop_demand);
        ll_piv = (LinearLayout) findViewById(R.id.ll_piv);
//        rlStopContract = (RelativeLayout) findViewById(R.id.rl_stop_contract);
        ibFlowChart = (ImageButton) findViewById(R.id.ib_flow_chart);
        //右上角三个按钮设置；
        right_contain = (LinearLayout) findViewById(R.id.right_contain);
        View view = LayoutInflater.from(this).inflate(R.layout.addview_wkflow_state,null);
        right_contain.addView(view);
        right_contain.setVisibility(View.VISIBLE);

        demandDetails = (ImageView) right_contain.findViewById(R.id.demand_details);
        projectInformation = (ImageView) right_contain.findViewById(R.id.project_information);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        Bundle bundle = getIntent().getExtras();
        bid_status = bundle.getBoolean(Constant.DemandDetailBundleKey.DEMAND_BID_STATUS);
        demand_type = bundle.getString(Constant.DemandDetailBundleKey.DEMAND_TYPE);

    }
    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mPtrLayout.setLastUpdateTimeRelateObject(this);

    }
    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
    @Override
    protected void initListener() {
        super.initListener();
        projectInformation.setOnClickListener(this);
        demandDetails.setOnClickListener(this);
        btnStopDemand.setOnClickListener(this);
        mListView.setOnItemClickListener(this);
        ibFlowChart.setOnClickListener(this);
        mPtrLayout.setOnRefreshListener(new OnDefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getOrderDetailsInfo(needs_id, designer_id);

            }
        });
    }
    @Override
    protected void leftNavButtonClicked(View view) {
        refreshWkFlowState();
        super.leftNavButtonClicked(view);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mPtrLayout.autoRefresh();
    }
    private void refreshWkFlowState() {
        if (TextUtils.isEmpty(wk_cur_sub_node_id)) {
            return;
        }
        boolean numeric = StringUtils.isNumeric(wk_cur_sub_node_id);
        if (!numeric) {
            return;
        }
        intent.putExtra(Constant.BundleKey.BUNDLE_SUB_NODE_ID, wk_cur_sub_node_id);
        setResult(RESULT_OK, intent);
        finish();
    }
    private ListViewFinal mListView;
    private PtrClassicFrameLayout mPtrLayout;
    private LinearLayout right_contain;
    private ImageView demandDetails;
    private ImageView projectInformation;
    private ImageButton ibFlowChart;
    private LinearLayout ll_piv;
    private RelativeLayout rlStopContract;
    private PolygonImageView polygonImageView;
    private Button btnStopDemand;
    private TextView tvDesignerName;
    private String demand_type;
    private boolean bid_status;
    private AlertView alertView;
    private String strMemberType = null;
    private Intent intent;
    private Context context;
    private WkFlowStateAdapter mAdapter;
    private OrderCommonEntity mOrderCommonEntity;
    private ArrayList<OrderCommonEntity.OrderListEntity> commonOrderListEntities = new ArrayList<>();


}
