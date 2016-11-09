package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.DesignerInfoDetails;
import com.autodesk.shejijia.consumer.personalcenter.recommend.adapter.SelectProjectAdapter;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendDetailsBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.SelectProjectEntity;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 16-10-24
 * @file SelectProjectActivity.java  .
 * @brief 选择项目.
 */

public class SelectProjectActivity extends NavigationBarActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private SelectProjectAdapter mAdapter;
    private ListView mListView;
    private DesignerInfoDetails designerInfoDetails;
    private int is_loho;
    private Button mSure;
    private String designer_id;
    private String designer_uid;
    private List<SelectProjectEntity.DesignerProjectsBean> designerProjects = new ArrayList<>();
    //    private SelectProjectEntity.DesignerProjectsBean interiorProject;
    List<RecommendDetailsBean> list = new ArrayList<>();
    private RecommendDetailsBean entity;
    private SelectProjectEntity.DesignerProjectsBean designerProjectsBean;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_select_project;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleForNavbar(UIUtils.getString(R.string.select_project));
        mListView = (ListView) findViewById(R.id.lv_select_project);
        mSure = (Button) findViewById(R.id.btn_select_project_sure);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        designer_id = memberEntity.getAcs_member_id();
        designer_uid = memberEntity.getHs_uid();
//        interiorProject = new SelectProjectEntity.DesignerProjectsBean();
//        interiorProject.setCommunity_name("创建新的项目");

        getSelectProjectList(designer_id);

        mAdapter = new SelectProjectAdapter(SelectProjectActivity.this, designerProjects);
        mListView.setAdapter(mAdapter);
        mListView.setItemChecked(0, true);

    }

    @Override
    protected void initListener() {
        super.initListener();
        mSure.setOnClickListener(this);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            if(view instanceof  CheckedTextView){
//                CheckedTextView checkedTextView = (CheckedTextView)view;
//                if(checkedTextView.isChecked()){
//                    checkedTextView.setCheckMarkDrawable(R.drawable.select_project_check_press);
//                }else{
//                    checkedTextView.setCheckMarkDrawable(R.drawable.select_project_check_normal);
//                }
//            }

        if (position != 0) {
            designerProjectsBean = designerProjects.get(position);
        } else {
            designerProjectsBean = null;
        }
    }

    /**
     * 设计师个人信息
     *
     * @param designer_id
     * @param hs_uid
     */
    public void getDesignerInfoData(String designer_id, String hs_uid) {
        MPServerHttpManager.getInstance().getDesignerInfoData(designer_id, hs_uid, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String jsonString = GsonUtil.jsonToString(jsonObject);
                designerInfoDetails = GsonUtil.jsonToBean(jsonString, DesignerInfoDetails.class);
                is_loho = designerInfoDetails.getDesigner().getIs_loho();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
            }
        });
    }


    /**
     * 选择项目
     *
     * @param designer_id
     */
    public void getSelectProjectList(final String designer_id) {

        CustomProgress.showDefaultProgress(this);
        MPServerHttpManager.getInstance().getSelectProjectList(designer_id, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                //                getDesignerInfoData(designer_id, designer_uid);
//                if (is_loho != 0 && designerProjects.contains(interiorProject))
                CustomProgress.cancelDialog();

                Log.d("SelectProjectActivity", jsonObject.toString());
                SelectProjectEntity entity = GsonUtil.jsonToBean(jsonObject.toString(), SelectProjectEntity.class);

                designerProjects.addAll(entity.getDesignerProjects());
//                if (!designerProjects.contains(interiorProject)) {
//                    designerProjects.add(0, interiorProject);
                mAdapter.notifyDataSetChanged();
//                }

            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CustomProgress.cancelDialog();
                MPNetworkUtils.logError(TAG, volleyError);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_select_project_sure:
                Intent intent = new Intent();
                intent.putExtra("mSelectList", designerProjectsBean);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
