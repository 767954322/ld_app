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
    private Button mSure;
    private String designer_id;
    private List<SelectProjectEntity.DesignerProjectsBean> designerProjects = new ArrayList<>();
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
        // fix by liuhe．
        designerProjectsBean = designerProjects.get(position);
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
                CustomProgress.cancelDialog();

                Log.d("SelectProjectActivity", jsonObject.toString());
                SelectProjectEntity entity = GsonUtil.jsonToBean(jsonObject.toString(), SelectProjectEntity.class);
                List<SelectProjectEntity.DesignerProjectsBean> projects = entity.getDesignerProjects();
                if (projects != null && projects.size() > 0) {
                    //fix zjl init first bean
                    designerProjectsBean = projects.get(0);
                    designerProjects.addAll(entity.getDesignerProjects());
                    mAdapter.notifyDataSetChanged();
                }
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
