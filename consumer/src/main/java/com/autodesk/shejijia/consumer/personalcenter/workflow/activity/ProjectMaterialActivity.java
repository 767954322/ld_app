package com.autodesk.shejijia.consumer.personalcenter.workflow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;

/**
 * @author Malidong .
 * @version 1.0 .
 * @date 16-6-7
 * @file ProjectMaterialActivity.java  .
 * @brief 项目资料类 .
 */
public class ProjectMaterialActivity extends BaseWorkFlowActivity implements View.OnClickListener {

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ll_project_material_measure_house:
                Intent mIntent = new Intent(ProjectMaterialActivity.this, FlowMeasureFormActivity.class);
                mIntent.putExtra(Constant.ProjectMaterialKey.IM_TO_FLOW_NEEDS_ID, needs_id);
                mIntent.putExtra(Constant.ProjectMaterialKey.IM_TO_FLOW_DESIGNER_ID, designer_id);
                mIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_MATERIAL);
                startActivity(mIntent);
                break;
            case R.id.ll_project_material_measure_house_delivery:
                Intent dIntent = new Intent(ProjectMaterialActivity.this, FlowUploadDeliveryActivity.class);
                dIntent.putExtra(Constant.ProjectMaterialKey.IM_TO_FLOW_NEEDS_ID, needs_id);
                dIntent.putExtra(Constant.ProjectMaterialKey.IM_TO_FLOW_DESIGNER_ID, designer_id);
                dIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_MATERIAL);
                startActivity(dIntent);
                break;
            case R.id.ll_project_material_contract:
                Intent cIntent = new Intent(ProjectMaterialActivity.this, FlowEstablishContractActivity.class);
                cIntent.putExtra(Constant.ProjectMaterialKey.IM_TO_FLOW_NEEDS_ID, needs_id);
                cIntent.putExtra(Constant.ProjectMaterialKey.IM_TO_FLOW_DESIGNER_ID, designer_id);
                cIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_MATERIAL);
                startActivity(cIntent);
                break;
            case R.id.ll_project_material_project:
                Intent pIntent = new Intent(ProjectMaterialActivity.this, FlowUploadDeliveryActivity.class);
                pIntent.putExtra(Constant.ProjectMaterialKey.IM_TO_FLOW_NEEDS_ID, needs_id);
                pIntent.putExtra(Constant.ProjectMaterialKey.IM_TO_FLOW_DESIGNER_ID, designer_id);
                pIntent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_MATERIAL);
                startActivity(pIntent);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_project_material;
    }

    @Override
    protected void initView() {
        super.initView();
        ll_project_material_measure_house = (LinearLayout) findViewById(R.id.ll_project_material_measure_house);
        ll_project_material_measure_house_delivery = (LinearLayout) findViewById(R.id.ll_project_material_measure_house_delivery);
        ll_project_material_contract = (LinearLayout) findViewById(R.id.ll_project_material_contract);
        ll_project_material_project = (LinearLayout) findViewById(R.id.ll_project_material_project);

        ll_project_material_measure_house.setOnClickListener(this);
        ll_project_material_measure_house_delivery.setOnClickListener(this);
        ll_project_material_contract.setOnClickListener(this);
        ll_project_material_project.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleForNavbar(getResources().getString(R.string.flow_project_data));
    }

    @Override
    protected void onWorkFlowData() {
        super.onWorkFlowData();
        wk_cur_sub_node_idi = Integer.valueOf(wk_cur_sub_node_id);
        if (wk_cur_sub_node_idi >= 11) {
            ll_project_material_measure_house.setVisibility(View.VISIBLE);
        }
        if (wk_cur_sub_node_idi == 33) {
            ll_project_material_measure_house_delivery.setVisibility(View.VISIBLE);
        }
        if (wk_cur_sub_node_idi >= 41) {
            ll_project_material_contract.setVisibility(View.VISIBLE);
        }
        if (wk_cur_sub_node_idi >= 51) {
            ll_project_material_project.setVisibility(View.VISIBLE);
        }
    }

    private LinearLayout ll_project_material_measure_house;
    private LinearLayout ll_project_material_measure_house_delivery;
    private LinearLayout ll_project_material_contract;
    private LinearLayout ll_project_material_project;
    private int wk_cur_sub_node_idi;
}
